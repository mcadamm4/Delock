pragma solidity^0.4.21;

contract Rental {
    address public owner;
    address public renter;

    uint public depositAmount;
    uint public pricePerHour;
    string public ipfsHashes;
    bool public available;

    uint public rental_StartTime;
    uint public total_CostOfRental;

    function Rental(string _ipfsHash,  uint _depositAmount, uint _pricePerHour) public {
        owner = msg.sender;
        ipfsHashes = _ipfsHash;
        depositAmount = _depositAmount;
        pricePerHour = _pricePerHour;
    }

    //MODIFIERS -- Set requirements and permissions for function execution
    modifier onlyOwner(){
        require(msg.sender==owner);
        _;
    }
    modifier onlyRenter(){
        require(msg.sender==renter);
        _;
    }
    modifier isRented(){
        require(available==false);
        _;
    }
    modifier notRented(){
        require(available==true);
        _;
    }

    //SETTERS
    function setDepositAmount(uint _depositAmount) public onlyOwner {
        depositAmount = _depositAmount;
    }
    function setPricePerHour(uint _pricePerHour) public onlyOwner {
        pricePerHour = _pricePerHour;
    }
    function setAvailable(bool _available) public {
        available = _available;
    }


    //EVENTS
    event event_rentItem(address indexed _renter);
    event event_returnItem(address indexed _renter, uint revenue);
    event event_unlockItem(); // ?
    event event_lockItem(); // ?

    //FUNCTIONS
    function rentItem() public notRented payable {
         assert(msg.value == depositAmount);
         renter = msg.sender;

         rental_StartTime = now;
         available = false;

         //Transfer depositAmount to contract
         msg.sender.transfer(depositAmount);
         emit event_rentItem(msg.sender);
         // -- Owner listens for this event?
    }

    function calcElapsedTime() public constant isRented returns (uint) {
        return (now - rental_StartTime);
    }

    function calcTotalCostOfRental() public onlyRenter isRented {
        uint totalCost = (pricePerHour * calcElapsedTime());

        //Renter may return the item before they have used up the deposit amount
        if(totalCost > depositAmount) {
            totalCost = (totalCost - depositAmount);
        }
        total_CostOfRental = totalCost;
    }

    function returnItem() public onlyRenter isRented payable {
        assert(msg.value>total_CostOfRental);

        owner.transfer(total_CostOfRental);
        emit event_returnItem(msg.sender, total_CostOfRental);
        resetRental();
    }

    function resetRental() private {
        total_CostOfRental = 0;
        available = true;
        renter = address(0);
    }
}
