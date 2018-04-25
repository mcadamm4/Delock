pragma solidity^0.4.21;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "../contracts/RentalDirectory.sol";

contract TestRentalDirectory {
    function testCreateRental() public {
        RentalDirectory dir = new RentalDirectory();
        bytes32 ipfsHash = "Rental Hash";
        uint deposit = 2;
        uint price = 2;
        //Act
        dir.createNewRental(ipfsHash, deposit, price);
        //Assert
        Rental retrievedRental = dir.getRental(0);
        Assert.equal(retrievedRental.ipfsHash(), ipfsHash, "Rental created with correct IpfsHash");
        Assert.equal(retrievedRental.depositAmount(), deposit, "Rental created with correct deposit");
        Assert.equal(retrievedRental.pricePerHour(), price, "Rental created with correct price");
    }

    function testCorrectNumberOfRentals() public {
        //Arrange
        RentalDirectory dir = new RentalDirectory();
        dir.createNewRental("1st Rental Hash", 1, 1);
        dir.createNewRental("2nd Rental Hash", 2, 2);
        //Act
        uint numberOfRentals = dir.numberOfRentals();
        //Assert
        Assert.equal(2, numberOfRentals, "Correct number of rentals create and returned");
    }

    function testIncorrectNumberOfRentals() public {
        //Arrange
        RentalDirectory dir = new RentalDirectory();
        dir.createNewRental("1st Rental Hash", 1, 1);
        dir.createNewRental("2nd Rental Hash", 2, 2);
        //Act
        uint numberOfRentals = dir.numberOfRentals();
        //Assert
        Assert.notEqual(31, numberOfRentals, "Control case for previous test - testCorrectNumberOfRentals");
    }

    function testGetRental() public {
        //Arrange
        RentalDirectory dir = new RentalDirectory();
        dir.createNewRental("1st Rental Hash", 1, 1);

        bytes32 ipfsHash = "2nd Rental Hash";
        uint deposit = 2;
        uint price = 2;
        dir.createNewRental(ipfsHash, deposit, price);

        //Act
        Rental retrievedRental = dir.getRental(1);
        //Assert
        Assert.equal(retrievedRental.ipfsHash(), ipfsHash, "Rental created with correct IpfsHash");
        Assert.equal(retrievedRental.depositAmount(), deposit, "Rental created with correct deposit");
        Assert.equal(retrievedRental.pricePerHour(), price, "Rental created with correct price");
    }
    function testUpdateRentalDetails() public {
        //Arrange
        RentalDirectory dir = new RentalDirectory();
        bytes32 oldIpfsHash = "Rental Hash";
        uint oldDeposit = 1;
        uint oldPrice = 1;
        uint newRentalIndex = dir.createNewRental(oldIpfsHash, oldDeposit, oldPrice)-1;
        //Act
        bytes32 newIpfsHash = "New Hash";
        uint newDeposit = 23;
        uint newPrice = 32;
        dir.updateRentalDetails(newRentalIndex, newIpfsHash, newDeposit, newPrice);
        //Assert
        Rental retrievedRental = dir.getRental(0);
        Assert.equal(retrievedRental.ipfsHash(), newIpfsHash, "Rental Ipfshash updated");
        Assert.equal(retrievedRental.depositAmount(), newDeposit, "Rental deposit updated");
        Assert.equal(retrievedRental.pricePerHour(), newPrice, "Rental price updated");
    }
}
