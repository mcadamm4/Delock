pragma solidity^0.4.21;

import "truffle/Assert.sol";
import "../contracts/Rental.sol";

contract TestRental {

    uint public initialBalance = 1 ether;
    uint public inputDeposit = 0.01 ether;
    uint public inputPrice = 0.001 ether;
    bool public inputAvailable = true;
    Rental public rental;

    function beforeEach() {
        rental = new Rental("Hash", inputDeposit, inputPrice, inputAvailable);
    }

    function testRentalConstructor() public {
        uint depositAmount = rental.depositAmount();
        uint pricePerHour = rental.pricePerHour();
        bool available = rental.available();
        /* Assert.equal("Hash", rental.ipfsHashes(), "Ipfshash set successfully"); */
        Assert.equal(inputDeposit, depositAmount, "Deposit set successfully");
        Assert.equal(inputPrice, pricePerHour, "Price set successfully");
        Assert.equal(inputAvailable, available, "Availibility set successfully");
    }

    function testSetDepositAmount() public {
        uint depositAmount = 0.02 ether;
        rental.setDepositAmount(depositAmount);
        Assert.equal(rental.depositAmount(), depositAmount, "Deposit amount set successfully.");
    }

    function testSetPricePerHour() public {
        uint price = 0.00345 ether;
        rental.setPricePerHour(price);
        Assert.equal(rental.pricePerHour(), price, "Price set successfully.");
    }

    function testOwnerSetAvailableTrue() public {
        rental.ownerSetAvailable(false);
        rental.ownerSetAvailable(true);
        Assert.equal(rental.available(), true, "Rental availability set successfully");
    }

    function testOwnerSetAvailableFalse() public {
        rental.ownerSetAvailable(false);
        Assert.equal(rental.available(), false, "Rental availability set successfully");
    }

    function testRentItem() public {
        rental.rentItem.value(inputDeposit);
        Assert.equal(address(this), address(rental.renter()), "Rented Successfully");
    }

    function testCalcTotalCostOfRental() public {
        rental.rentItem.value(inputDeposit);
        rental.calcTotalCostOfRental();
        Assert.notEqual(0, rental.total_CostOfRental(), "Cost calculated");
    }

    function testReturnItem() public {
        rental.rentItem.value(inputDeposit);
        rental.calcTotalCostOfRental();
        rental.returnItem.value(rental.total_CostOfRental());
        Assert.notEqual(address(this), address(rental.renter()), "Returned Successfully");
    }
}
