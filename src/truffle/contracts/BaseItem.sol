pragma solidity^0.4.21;

contract BaseItem {

    address public owner;
    address public renter;

    uint public depositAmount;
    uint public pricePerHour = 1;
    bytes32 public ipfsHash;

    bool public currentlyRented;

    uint public startDateCurrentRental;
    uint public endDateCurrentRental;

    function BaseItem(bytes32 _ipfsHash, uint _pricePerHour, uint _depositAmount) public {
        owner = msg.sender;
        ipfsHash = _ipfsHash;
        pricePerHour = _pricePerHour;
        depositAmount = _depositAmount;
    }

    //MODIFIERS -- Set requirements and permissions for function execution
    modifier onlyOwner(){
        //The code of a function using a modifier is inserted at the underscores location
        require(msg.sender==owner);
        _;
    }
    modifier onlyRenter(){
        require(msg.sender==renter);
        _;
    }
    modifier isRented(){
        require(currentlyRented==true);
        _;
    }
    modifier notRented(){
        require(currentlyRented==false);
        _;
    }

    //SETTERS
    function setDepositAmount(uint _depositAmount) public onlyOwner {
        depositAmount = _depositAmount;
    }
    function setPricePerHour(uint _pricePerHour) public onlyOwner {
        pricePerHour = _pricePerHour;
    }
    function setIpfsHash(bytes32 _ipfsHash) public onlyOwner {
      ipfsHash = _ipfsHash;
    }

    //GETTERS
    function getDepositAmount() public view returns (uint) {
        return depositAmount;
    }
    function getPricePerHour() public view returns (uint) {
        return pricePerHour;
    }
    function getIpfsHash() public view returns (bytes32) {
      return ipfsHash;
    }
    function getCurrentRenter() public view isRented returns (address) {
        return renter;
    }
    function getCurrentCost() public view returns (uint) {
        // (now - startTime) * pricePerHour
        //pricePerHour * (now - (currentRentalStartDate + timeBlock)) / timeBlock;
        return pricePerHour;
    }

    //EVENTS
    event rentItem(address indexed _renter);
    event returnItem();
    event unlockItem();
    event lockItem();

    //FUNCTIONS
    function rent() public notRented payable {
        require(msg.value>=0);
    }
}
