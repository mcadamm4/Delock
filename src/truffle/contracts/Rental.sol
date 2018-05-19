pragma solidity^0.4.21;

contract Rental {
    address public owner;
    address public renter;

    uint public depositAmount;
    uint public pricePerHour;
    string public ipfsHashes;
    bool public available;

    uint public rental_StartTime;

    uint public amountDue = 0;
    bool private overpaid = false;
    uint public total_CostOfRental = 0;

    function Rental(string _ipfsHash,  uint _depositAmount, uint _pricePerHour, bool _available) public {
        owner = msg.sender;
        ipfsHashes = _ipfsHash;
        depositAmount = _depositAmount;
        pricePerHour = _pricePerHour;
        available = _available;
    }

    // Access modifiers
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

    function setDepositAmount(uint _depositAmount) public onlyOwner {
        depositAmount = _depositAmount;
    }
    function setPricePerHour(uint _pricePerHour) public onlyOwner {
        pricePerHour = _pricePerHour;
    }
    function ownerSetAvailable(bool _available) public onlyOwner {
        available = _available;
        renter = owner;
        emit event_OwnerSetAvailable(_available);
    }

    //Events

    event event_OwnerSetAvailable(bool _available);
    event event_rentItem(bool _available);
    event event_CostCalculation(uint _totalCostOfRental);
    event event_returnItem(bool _available);

    //Functions

    // Payable methods take payments provided in msg.value
    function rentItem() public notRented payable {
         assert(msg.value == depositAmount);
         renter = msg.sender;

         rental_StartTime = now;
         available = false;

         emit event_rentItem(available);
    }

    // Constant functions are read-only (do not cost gas to execute)
    function calcElapsedTime() public constant isRented returns (uint) {
        return (now - rental_StartTime);
    }

    function calcTotalCostOfRental() public onlyRenter isRented returns (uint) {
        // Get total cost of rental for the given period
        total_CostOfRental = ((pricePerHour/3600) * calcElapsedTime());

        // Deposit was lower than overall cost so KEEP deposit and return amountDue = (total_cost - depositAmount)
        if(depositAmount < total_CostOfRental) {
            // This is the amount outstanding for the renter
            amountDue = (total_CostOfRental - depositAmount);
            emit event_CostCalculation(amountDue);
        }
        // Deposit was more than actual cost so take total_cost from the deposit and RETURN surplus to renter
        else {
            overpaid = true;
            amountDue = 0;
            emit event_CostCalculation(amountDue);
        }
    }

    function returnItem() public onlyRenter isRented payable {
        assert(msg.value>=amountDue);

        // Return the renters surplus deposit
        if(overpaid) {
            renter.transfer(depositAmount - total_CostOfRental);
        }

        // Send the owner the revenue from rental
        owner.transfer(total_CostOfRental);
        resetRental();
        emit event_returnItem(available);
    }

    function resetRental() private {
        total_CostOfRental = 0;
        available = true;
        renter = address(0);
    }
}
