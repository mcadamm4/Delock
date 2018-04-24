pragma solidity^0.4.21;

import "./BaseItem.sol";

contract ItemListing is BaseItem {
// OWNER
    // Create listings
    // Update listings
    // Delete listings

// RENTER
    // Browse listing
    // Create bookings
    // Cancel bookings

    address public owner;
    address public renter;

    uint public depositAmount;
    uint public pricePerHour;
    bytes32 public ipfsHash;

    function ItemListing(bytes32 _ipfsHash, uint _pricePerHour, uint _depositAmount) BaseItem(_ipfsHash, _pricePerHour, _depositAmount) public {
        owner = msg.sender;
        ipfsHash = _ipfsHash;
        pricePerHour = _pricePerHour;
        depositAmount = _depositAmount;
    }
}
