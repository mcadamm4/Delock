pragma solidity^0.4.21;

contract Rental {
    address public owner;
    address public renter;

    uint public depositAmount;
    uint public pricePerHour;
    string public ipfsHashes;
    bool public available;

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
    event rentItem(address indexed _renter);
    event returnItem();
    event unlockItem(); // ?
    event lockItem(); // ?

    //FUNCTIONS
    function rent() public notRented payable {
         assert(msg.value > 0);
    }
}
