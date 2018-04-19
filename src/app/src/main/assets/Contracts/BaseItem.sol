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

    //EVENTS
        //Return()
        //Unlock()?
        //Lock()?

    constructor() {
        owner = msg.sender;
    }

    //FUNCTIONS
}