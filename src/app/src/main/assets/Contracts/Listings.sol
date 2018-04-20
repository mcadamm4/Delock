pragma solidity^0.4.18;

import "./ItemListing.sol";

//Repository of all listings

contract Listings {

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
    ItemListing[] public listings;

    //EVENTS
    event NewItemListing(uint _index);

    //MODIFIERS
    modifier onlyOwner() {
        require(msg.sender==owner);
        _;
    }
    modifier listingOwner(uint _index) {
        require(msg.sender==listings[_index].owner());
        _;
    }
    modifier listingExists(uint _index) {
        require(_index < listings.length);
        _;
    }

    //FUNCTIONS
    constructor() public {
        owner = msg.sender;
    }

    function numberOfListings() public constant returns (uint) {
        return listings.length;
    }

    function getItemListing(uint _index) public constant returns (Listing, address, bytes32, uint, uint, bool) {
        ItemListing listing = listings[_index];
        return (listings, listing.owner(), listing.ipfsHash(), listing.depositPrice(), listing.price(), listing.isRented());
    }

    function createNewListing(bytes32 ipfsHash, uint price, uint deposit) public returns(uint) {
        listings.push(new ItemListing(msg.sender, _ipfsHash, _price, _deposit));
        emit NewListing(listings.length-1);
        return listings.length;
    }

    function deleteListing(uint _index) public listingOwner listingExists {
        delete listings[_index];
    }

    function updateListingDetails(uint _index, bytes32 _ipfsHash, uint _price, uint _deposit) public {
        ItemListing listing = listings[_index];
        listing.setIpfsHash(msg.sender, _ipfsHash);
        listing.setPrice(msg.sender, _price);
        listing.setDeposit(msg.sender, _deposit);
    }
}