pragma solidity^0.4.21;

import "./User.sol";

contract UserDirectory {

// OWNER
    // Create listings
    // Update listings
    // Delete listings

// RENTER
    // Browse listing
    // Create bookings
    // Cancel bookings

    //FIELDS
    address public owner;
    mapping (address => User) public userMapping;
    User[] public users;

    //EVENTS
    event event_NewUser(address _address);

    //MODIFIERS
    modifier onlyOwner() {
        require(msg.sender==owner);
        _;
    }
    modifier isUser(uint _index) {
        require(msg.sender==users[_index].owner());
        _;
    }
    modifier userExists(uint _index) {
        require(_index < users.length);
        _;
    }

    //FUNCTIONS
    function UserDirectory() public {
        owner = msg.sender;
    }

    /* function createNewUser(bytes32 _ipfsHash) public {
        userMapping[msg.sender] = new User(_ipfsHash);
        emit event_NewUser(msg.sender);
    }

    function getUser() public returns (User) {
        return userMapping[msg.sender];
    }

    function updateUserDetails(bytes32 _ipfsHash) public {
        userMapping[msg.sender].setIpfsHash(_ipfsHash);
    }

    function deleteUser() public {
        delete userMapping[msg.sender];
    } */
}
