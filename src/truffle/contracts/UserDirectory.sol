pragma solidity^0.4.21;


contract UserDirectory { //NOT IMPLEMENTED
    address public owner;
    address[] public users;

    // Events
    event event_NewUser(address _address);

    // Access modifiers
    modifier onlyOwner() {
        require(msg.sender==owner);
        _;
    }
    modifier userExists(uint _index) {
        require(msg.sender==users[_index]);
        _;
    }

    // Functions
    function UserDirectory() public {
        owner = msg.sender;
    }

    function createNewUser(address user) public {
        users.push(user);
    }

    function deleteUser(uint _index) public userExists(0) {
        delete users[_index];
    }
}
