pragma solidity^0.4.18;

import "./UserDirectory.sol";

//Repository of all listings

contract User {

// OWNER
    // Create listings
    // Update listings
    // Delete listings

// RENTER
    // Browse listing
    // Create bookings
    // Cancel bookings

    address public owner;
    
    function User() public {
        owner = msg.sender;
    }

}
