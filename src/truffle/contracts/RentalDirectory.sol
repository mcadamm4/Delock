pragma solidity^0.4.18;

contract RentalDirectory {

    //FIELDS
    address public owner;
    address[] public rentals;
    uint public numElements = 0;


    //CONSTRUCTOR
    function RentalDirectory() public {
        owner = msg.sender;
    }

    //EVENTS
    event event_NewRental();

    //MODIFIERS
    modifier onlyOwner() {
        require(msg.sender==owner);
        _;
    }
    // modifier rentalOwner(uint _index) {
    //     require(msg.sender==rentals[_index].owner());
    //     _;
    // }
    // modifier rentalExists(uint _index) {
    //     require(_index < rentals.length);
    //     _;
    // }

    //FUNCTIONS

    function addNewRental(address newRentalAddress) public returns (uint) {
        if(numElements == rentals.length) {
            rentals.length += 1;
        }
        rentals[numElements++] = newRentalAddress;
        emit event_NewRental();

        // ....
        // Have to add this new rental to Users list of owned rentals
        // ....

        return rentals.length;
    }

    function clearRentals() public onlyOwner {
        //Overwriting instead of deleting the array will save gas
        numElements = 0;
    }

    function numberOfRentals() public constant returns (uint) {
        return rentals.length;
    }
/*
    function getRental(uint _index) public constant returns (address) {
        Rental rental = rentals[_index].getDetails();
        return rental;
    } */
/*
    function returnListings() public constant returns (address[]) {
        return rentals(0, numElements);
    } */

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
