pragma solidity^0.4.21;

import "./ItemListing.sol";

//Repository of all listings

contract Listings {

    //FIELDS
    address public owner;
    ItemListing[] public listings;

    function Listings() public {
        owner = msg.sender;
    }

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

    function numberOfListings() public constant returns (uint) {
        return listings.length;
    }

    function getItemListing(uint _index) public constant returns (ItemListing) {
        ItemListing listing = listings[_index];
        return listing;
    }

    function createNewListing(bytes32 _ipfsHash, uint _pricePerHour, uint _depositAmount) public returns (uint) {
        listings.push(new ItemListing(_ipfsHash, _pricePerHour, _depositAmount));
        emit NewItemListing(listings.length-1);
        return listings.length;
    }

    function deleteListing(uint _index) public listingOwner(_index) listingExists(_index) {
        delete listings[_index];
    }

    function updateListingDetails(uint _index, bytes32 _ipfsHash, uint _pricePerHour, uint _depositAmount) public {
        ItemListing listing = listings[_index];
        listing.setIpfsHash(_ipfsHash);
        listing.setPricePerHour(_pricePerHour);
        listing.setDepositAmount(_depositAmount);
    }
}
