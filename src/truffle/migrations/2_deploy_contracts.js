var Rental = artifacts.require("./Rental.sol");
var RentalDirectory = artifacts.require("./RentalDirectory.sol");

module.exports = function(deployer) {
    deployer.deploy(RentalDirectory);
};
