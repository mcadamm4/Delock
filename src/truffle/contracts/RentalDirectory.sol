pragma solidity^0.4.18;

/* import "./UserDirectory.sol"; */

//Repository of all rentals

contract RentalDirectory {

    //FIELDS
    address public owner;
    address[] public rentals;

    //CONSTRUCTOR
    function RentalDirectory() public {
        owner = msg.sender;
    }

    //EVENTS
    event event_NewRental(uint _index);

    //MODIFIERS
    modifier onlyOwner() {
        require(msg.sender==owner);
        _;
    }
    modifier rentalOwner(uint _index) {
        require(msg.sender==rentals[_index].owner());
        _;
    }
    modifier rentalExists(uint _index) {
        require(_index < rentals.length);
        _;
    }

    //FUNCTIONS

    function addNewRental(address newRentalAddress) public returns (uint) {
        rentals.push(newRentalAddress);
        /* emit event_NewRental(rentals.length-1); */

        // ....
        // Have to add this new rental to Users list of owned rentals
        // ....

        return rentals.length;
    }

    function numberOfRentals() public constant returns (uint) {
        return rentals.length;
    }
/*
    function getRental(uint _index) public constant returns (address) {
        Rental rental = rentals[_index].getDetails();
        return rental;
    } */

    function returnListings() public return (address[]) {
        return rentals;
    }

    /* function deleteRental(uint _index) public rentalOwner(_index) rentalExists(_index) {
        delete rentals[_index];
    }

    function updateRentalDetails(uint _index, string _ipfsHash, uint _deposit, uint _price) public {
        Rental rental = rentals[_index];
        rental.setIpfsHash(_ipfsHash);
        rental.setPricePerHour(_price);
        rental.setDepositAmount(_deposit);
    } */
}
