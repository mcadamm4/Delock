var Rental = artifacts.require("./Rental.sol");
var RentalDirectory = artifacts.require("./RentalDirectory.sol");

module.exports = function(deployer) {
    deployer.deploy(Rental, "hash", 1, 2);
    deployer.link(Rental, RentalDirectory);
    deployer.deploy(RentalDirectory);
};
