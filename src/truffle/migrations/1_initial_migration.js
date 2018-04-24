var Migrations = artifacts.require("./Migrations.sol");

module.exports = function(deployer) {
    deployer.deploy(BaseItem).then{
        deployer.deploy(ItemListing).then{
            deployer.deploy(Listings);
        };
    };
    deployer.deploy(Migrations);
};
