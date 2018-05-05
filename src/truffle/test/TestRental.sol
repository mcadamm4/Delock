pragma solidity^0.4.21;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "../contracts/Rental.sol";

contract TestRental {

    function testSetDepositAmount() public {
        Rental rental = new Rental("Hash", 1, 1);

        uint depositAmount = 30;
        rental.setDepositAmount(depositAmount);

        Assert.equal(rental.depositAmount(), depositAmount, "Deposit amount set successfully.");
    }

    function testSetPricePerHour() public {
        Rental rental = new Rental("Hash", 1, 1);

        uint price = 30;
        rental.setPricePerHour(price);

        Assert.equal(rental.pricePerHour(), price, "Price set successfully.");
    }

    function testSetAvailable() public {
        Rental rental = new Rental("Initial Hash", 1, 1);

        rental.setAvailable(true);

        Assert.equal(rental.available(), true, "Rental availability set successfully");
    }
}
