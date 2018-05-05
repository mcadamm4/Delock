pragma solidity^0.4.21;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "../contracts/RentalDirectory.sol";
import "../contracts/Rental.sol";

contract TestRentalDirectory {

    function testCreateRental() public {
        RentalDirectory dir = new RentalDirectory();
        address rentalAddress = new Rental("Hash", 1, 1);
        //Act
        uint numRentals = dir.addNewRental(rentalAddress);
        //Assert
        Assert.equal(numRentals, 1, "Correct number of rentals added");
    }

    function testCorrectNumberOfRentals() public {
        //Arrange
        RentalDirectory dir = new RentalDirectory();
        // -- ONE
        address rentalAddress = new Rental("Hash", 1, 1);
        dir.addNewRental(rentalAddress);
        // -- TWO
        rentalAddress = new Rental("Hash", 2, 2);
        dir.addNewRental(rentalAddress);
        //Act
        uint numElements = dir.numElements();
        //Assert
        Assert.equal(numElements, 2 , "Correct number of rentals created");
    }

    function testIncorrectNumberOfRentals() public {
        //Arrange
        RentalDirectory dir = new RentalDirectory();
        address rentalAddress = new Rental("Hash", 1, 1);
        dir.addNewRental(rentalAddress);
        rentalAddress = new Rental("Hash", 2, 2);
        dir.addNewRental(rentalAddress);
        //Act
        uint numElements = dir.numElements();
        //Assert
        Assert.notEqual(31, numElements, "Control case for previous test - testCorrectNumberOfRentals");
    }

    /* function testGetRentalAddresses() public {
        //Arrange
        RentalDirectory dir = new RentalDirectory();

        address rentalAddress1 = new Rental("Hash", 1, 1);
        dir.addNewRental(rentalAddress1);
        address rentalAddress2 = new Rental("Hash", 2, 2);
        uint numElements = dir.addNewRental(rentalAddress2);
        //Act
        address[] retrievedRental = dir.getRentals();
        //Assert
        Assert.equal(numElements, 2, "Correct number of rentals added");
        Assert.equal(rentalAddress1, retrievedRental[0],  "New Rental Address Successfully added");
        Assert.equal(rentalAddress2, retrievedRental[1],  "New Rental Address Successfully added");
    } */

    function testClearRentals() public {
        //Arrange
        RentalDirectory dir = new RentalDirectory();

        address rentalAddress1 = new Rental("Hash", 1, 1);
        dir.addNewRental(rentalAddress1);
        Rental rentalAddress2 = new Rental("Hash", 2, 2);
        dir.addNewRental(rentalAddress2);

        dir.addNewRental(rentalAddress1);
        uint numRentals = dir.addNewRental(rentalAddress2);
        Assert.equal(numRentals, 2, "Correct number of rentals added");

        //Act
        dir.clearRentals();
        //Assert
        uint numElements = dir.numElements();
        Assert.equal(numElements, 0, "Rentals successfull cleared");
    }
}
