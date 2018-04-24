pragma solidity^0.4.21;

contract UserDirectory {

// OWNER
    // Create listings
    // Update listings
    // Delete listings

// RENTER
    // Browse listing
    // Create bookings
    // Cancel bookings
    
    address public owner;

    function UserDirectory() public {
        owner = msg.sender;
    }

}
