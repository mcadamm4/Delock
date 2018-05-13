pragma solidity^0.4.21;

contract RentalDirectory {
    address public owner;
    address[] public rentals;
    uint public numElements = 0;

    //Constructer
    function RentalDirectory() public {
        owner = msg.sender;
    }

    //Events - can be listened for on Front-end to trigger changes
    event event_NewRental(address indexed _rentalOwner, address indexed _address);
    event event_Return(address indexed _rentalOwner, address indexed _address);

    // Access modifiers
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

    // For dev purposes, would be marked onlyOwner as this contract is owned by Delock itself
    function clearRentals() public {
        numElements = 0;
    }

    // These would be used to trigger push notifications in app to alert owners to state changes (NOT IMPLEMENTED)
    function triggerRentalEvent(address _rentalOwner, address _rentalAddress) public {
        emit event_NewRental(_rentalOwner, _rentalAddress);
    }

    function triggerReturnEvent(address _rentalOwner, address _rentalAddress) public {
        emit event_Return(_rentalOwner, _rentalAddress);
    }
}
