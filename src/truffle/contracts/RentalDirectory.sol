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
    event event_NewRental(address indexed _rentalOwner, address indexed _address);
    event event_Return(address indexed _rentalOwner, address indexed _address);

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
        emit event_NewRental(msg.sender, newRentalAddress);
        return numElements;
    }

    function clearRentals() public {
        numElements = 0;
    }

    function triggerRentalEvent(address _rentalOwner, address _rentalAddress) public {
        emit event_NewRental(_rentalOwner, _rentalAddress);
    }

    function triggerReturnEvent(address _rentalOwner, address _rentalAddress) public {
        emit event_Return(_rentalOwner, _rentalAddress);
    }
}
