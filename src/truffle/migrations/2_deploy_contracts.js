var BaseItem = artifacts.require("./BaseItem.sol");
var ItemListing = artifacts.require("./ItemListing.sol");
var Listings = artifacts.require("./Listings.sol");

module.exports = function(deployer) {
  deployer.deploy(BaseItem, "hash", 1, 2);
  deployer.link(BaseItem, ItemListing);
  deployer.deploy(ItemListing, "hash", 1, 2);
  deployer.link(ItemListing, Listings);
  deployer.deploy(Listings);
};
