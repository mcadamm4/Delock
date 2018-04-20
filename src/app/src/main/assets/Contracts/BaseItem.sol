pragma solidity^0.4.18;

contract BaseItem {
    
    address public owner;
    address public renter;

    uint public depositAmount;
    uint public pricePerHour;

    boolean public currentlyRented;

    uint public startDateCurrentRental;
    uint public endDateCurrentRental;

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
    function setDepositAmount(uint _deposit) onlyOwner {
        depositAmount = _deposit;
    }
    function setPricePerHour(uint _pricePerHour) onlyOwner {
        pricePerHour = _pricePerHour;
    }
    //GETTERS
    function getDepositAmount() {
        return depositAmount;
    }
    function getPrice() {
        return pricePerHour;
    }
    function getCurrentRenter() isRented {
        return renter;
    }
    function getCurrentCost(){
        // (now - startTime) * pricePerHour
        //pricePerHour * (now - (currentRentalStartDate + timeBlock)) / timeBlock;
        return 0.1;
    }

    //EVENTS
    event rentItem(address indexed _renter);
    event returnItem();
    event unlockItem();
    event lockItem();

    constructor() public {
        owner = msg.sender;
    }

    //FUNCTIONS
    function rent() public notRented {
        require(msg.value>=0);
    }
}