pragma solidity^0.4.21;

import "./UserDirectory.sol";

//Repository of all listings

contract User {
    //FILEDS
    address public owner;
    bytes32 public ipfsHash;
    address[] ownedRentals;
    address[] renting;
    address[] favorites;

    function User(bytes32 _ipfsHash) public {
        owner = msg.sender;
        ipfsHash = _ipfsHash;
    }

    //MODIFIERS
    modifier onlyOwner() {
        require(msg.sender==owner);
        _;
    }

    //SETTERS
    function setIpfsHash(bytes32 _ipfsHash) public { //onlyOwner
        ipfsHash = _ipfsHash;
    }

    //EVENTS

    //FUNCTIONS
    function addNewOwnedRental() public onlyOwner {
        /* ownedRentals.push(); */
    }

}
