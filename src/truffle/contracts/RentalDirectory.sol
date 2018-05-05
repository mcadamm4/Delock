pragma solidity^0.4.21;

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
    event event_NewRental(uint _index);

    //MODIFIERS
    modifier onlyOwner() {
        require(msg.sender==owner);
        _;
    }
    function addNewRental(address newRentalAddress) public returns (uint) {
        if(numElements == rentals.length) {
            rentals.length += 1;
        }
        rentals[numElements++] = newRentalAddress;
        /* emit event_NewRental(rentals.length-1); */
        return numElements;
    }

    function clearRentals() public {
        numElements = 0;
    }
}
