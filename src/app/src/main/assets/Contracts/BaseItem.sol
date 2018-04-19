pragma solidity^0.4.18;

contract BaseItem {
    
    address public owner;
    address public renter;

    bool public isRented;

    //Start & End dates for rental
    uint public startDateCurrentRental;
    uint public endDateCurrentRental;

    constructor() {
        owner = msg.sender;
    }
}