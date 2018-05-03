package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.smartcontract_wrappers.Rental;
import com.app.delock.delockApplication.smartcontract_wrappers.RentalDirectory;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.app.delock.delockApplication.utils.IpfsUtils.retrieveImagesFromIPFS;
import static com.app.delock.delockApplication.utils.IpfsUtils.retrieveItemDetailsFromIPFS;

/**
 * Created by Marky on 01/05/2018.
 */

class ContractUtils {

    @SuppressLint("Assert")
    static Boolean deployContract(Context mContext, String[] ipfsHashes, Item item) {
        Boolean valid = false;
        BigInteger deposit = BigInteger.valueOf((long) item.itemDeposit);
        BigInteger price = BigInteger.valueOf((long) item.itemPrice);
        String newRentalAddress = null;
        Rental newRental = null;

        try {
            Web3j web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));

            SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFS, 0);
            String walletPath = sharedPreferences.getString(Constants.WALLET_PATH_SHARED_PREF, "No address found");

            //SHOULD PROMPT USER FOR PASSWORD, REMOVE SHARED PREF FOR SECURITY
            String password = sharedPreferences.getString(Constants.PASSWORD_SHARED_PREF, "No address found");
            Credentials cred = WalletUtils.loadCredentials(password, walletPath);

            EthGasPrice getGasPrice = web3.ethGasPrice().send();
            BigInteger gasPrice = getGasPrice.getGasPrice();
            List<String> _ipfsHashes = Arrays.asList(ipfsHashes);

            newRental = Rental.deploy(web3, cred, gasPrice, Contract.GAS_LIMIT, _ipfsHashes, deposit, price).send();

            newRentalAddress = newRental.getContractAddress();

//          -- NEED WRAPPER --
            RentalDirectory rentalDirectory = RentalDirectory.load(Constants.RENTAL_DIRECTORY_ADDRESS,
                    web3, cred, gasPrice, Contract.GAS_LIMIT);

            assert (rentalDirectory.isValid());
            TransactionReceipt transactionReceipt = rentalDirectory.addNewRental(newRentalAddress).send();
            String status = transactionReceipt.getStatus();

        } catch (Exception e ) {
            if (Arrays.toString(e.getStackTrace()).contains("java.lang.RuntimeException: java.lang.RuntimeException: Error processing transaction request: insufficient funds for gas * price + value")) {
                Toast toast = Toast.makeText(mContext, "Insufficient funds", Toast.LENGTH_LONG);
                toast.show();
            } else
                e.printStackTrace();
        }
        return valid;
    }

    static ArrayList<Item> retrieveListings(Context mContext){
        String address = "";
        RentalDirectory rentalDirectory;
        ArrayList<String> addresses = null;
        ArrayList<Item> itemlist = null;

        try {
            Web3j web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));

            SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFS, 0);
            String walletPath = sharedPreferences.getString(Constants.WALLET_PATH_SHARED_PREF, "No address found");

            //SHOULD PROMPT USER FOR PASSWORD, REMOVE SHARED PREF FOR SECURITY
            String password = sharedPreferences.getString(Constants.PASSWORD_SHARED_PREF, "No address found");
            Credentials cred = WalletUtils.loadCredentials(password, walletPath);
            EthGasPrice getGasPrice = web3.ethGasPrice().send();
            BigInteger gasPrice = getGasPrice.getGasPrice();

            rentalDirectory = RentalDirectory.load(Constants.RENTAL_DIRECTORY_ADDRESS, web3, cred, gasPrice, Contract.GAS_LIMIT);

            //Retrieve all rental contract addresses from Rental Directory
            addresses = (ArrayList<String>) rentalDirectory.returnListings().send();
            if(addresses != null)
                itemlist = fetchListingData(web3, cred, gasPrice, addresses);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemlist;

    }

    private static ArrayList<Item> fetchListingData(Web3j web3, Credentials cred, BigInteger gasPrice, ArrayList<String> addresses) {
        ArrayList<Item> items = new ArrayList<>();
        for(String address : addresses){
            //Rental wrapper needed
            Rental rental = Rental.load(Constants.RENTAL_DIRECTORY_ADDRESS, web3, cred, gasPrice, Contract.GAS_LIMIT);
            try {
                List<String> ipfsData = (List<String>) rental.getIpfsHashes().send();

                JSONObject rentalDetails = retrieveItemDetailsFromIPFS(ipfsData.get(0));
                /* Remove hash pointing to item details, rest are for images. */
                ipfsData.remove(0);
                ArrayList<Bitmap> bitmaps = retrieveImagesFromIPFS(ipfsData);

                String title = (String) rentalDetails.get("Title");
                double deposit = (double) rentalDetails.get("Deposit");
                double price = (double) rentalDetails.get("Price");
                String description = (String) rentalDetails.get("Desc");

                Item item = new Item(rental, bitmaps, title, deposit, price, description);
                items.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return items;
    }
}
