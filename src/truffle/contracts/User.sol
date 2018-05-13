pragma solidity^0.4.21;

import "./UserDirectory.sol";

//Repository of all listings

contract User { //NOT IMPLEMENTED
    address public owner;
    bytes32 public ipfsHash;
    string ownedRentals;
    address[] renting;
    address[] favorites;

    function User(bytes32 _ipfsHash) public {
        owner = msg.sender;
        ipfsHash = _ipfsHash;
    }

    // Access modifiers
    modifier onlyOwner() {
        require(msg.sender==owner);
        _;
    }

    // ---
    function setIpfsHash(bytes32 _ipfsHash) public onlyOwner {
        ipfsHash = _ipfsHash;
    }

    // Events
    // ??

    // Functions
    function addNewOwnedRental() public onlyOwner {
         /* ownedRentals.push(); */
    }

    function deleteOwnedRental() public onlyOwner {
        /* delete ownedRentals[_index] */
    }

    function addNewRentedItem() public {
        // Would listen for rental event for owner
    }

    function deleteRentedItem() public {
        // Would listen for return event for owner
    }
}
