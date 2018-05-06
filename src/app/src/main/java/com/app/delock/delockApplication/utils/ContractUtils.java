package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Toast;

import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.smartcontract_wrappers.Rental;
import com.app.delock.delockApplication.smartcontract_wrappers.RentalDirectory;

import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.Arrays;

import static com.app.delock.delockApplication.utils.IpfsUtils.retrieveImagesFromIPFS;
import static com.app.delock.delockApplication.utils.IpfsUtils.retrieveItemDetailsFromIPFS;

/**
 * Created by Marky on 01/05/2018.
 */

class ContractUtils {

    @SuppressLint({"Assert", "NewApi"})
    static String deployContract(Activity mContext, String[] ipfsHashes, Item item) {
        BigInteger deposit = BigInteger.valueOf((long) item.itemDeposit);
        BigInteger price = BigInteger.valueOf((long) item.itemPrice);
        String newRentalAddress = null;
        Rental newRental = null;
        String status = "";
        try {
            Web3j web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));

            SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFS, 0);
            String walletPath = sharedPreferences.getString(Constants.WALLET_PATH_SHARED_PREF, "No address found");

            //SHOULD PROMPT USER FOR PASSWORD, REMOVE SHARED PREF FOR SECURITY
            String password = sharedPreferences.getString(Constants.PASSWORD_SHARED_PREF, "No address found");
            Credentials cred = WalletUtils.loadCredentials(password, walletPath);


/*          Using default high gas price to increase execution times i.e. "Contract.GAS_PRICE" instead of
            the following because the test-net is very slow.
            In reality we would get a "best value" gas price from some API in order to save money.

            EthGasPrice getAverageGasPrice = web3.ethGasPrice().send(); */

            String str = TextUtils.join(",", ipfsHashes);

            // Deploy new rental contract
            newRental = Rental.deploy(web3, cred, Contract.GAS_PRICE, Contract.GAS_LIMIT, str, deposit, price).send();
            newRentalAddress = newRental.getContractAddress();

            // Add new rental to rental directory
            RentalDirectory rentalDirectory = RentalDirectory.load(Constants.RENTAL_DIRECTORY_ADDRESS,
                    web3, cred, Contract.GAS_PRICE, Contract.GAS_LIMIT);

            assert (rentalDirectory.isValid());
            TransactionReceipt transactionReceipt = rentalDirectory.addNewRental(newRentalAddress).send();
            status = transactionReceipt.getStatus();

        } catch (Exception e ) {
            if (Arrays.toString(e.getStackTrace()).contains("java.lang.RuntimeException: java.lang.RuntimeException: Error processing transaction request: insufficient funds for gas * price + value")) {
                Toast toast = Toast.makeText(mContext, "Insufficient funds", Toast.LENGTH_LONG);
                toast.show();
            } else
                e.printStackTrace();
        }
        return status;
    }

    static ArrayList<Item> retrieveListings(Activity mContext, Web3j web3, Credentials cred){
        RentalDirectory rentalDirectory;
        ArrayList<String> addresses = new ArrayList<>();
        ArrayList<Item> itemList = new ArrayList<>();

        try {
            EthGasPrice getGasPrice = web3.ethGasPrice().send();
            BigInteger gasPrice = getGasPrice.getGasPrice();

            rentalDirectory = RentalDirectory.load(Constants.RENTAL_DIRECTORY_ADDRESS, web3, cred, Contract.GAS_PRICE, Contract.GAS_LIMIT);

            //Retrieve all rental contract addresses from Rental Directory
            BigInteger tempNumElements = rentalDirectory.numElements().send();
            int numListings = tempNumElements.intValue();

            for(int i=0; i<numListings; i++) {
                String adr = rentalDirectory.rentals(BigInteger.valueOf((long)i)).send();
                if (adr == null)
                    break;
                addresses.add(adr);
            }
            if(!addresses.isEmpty()) {
                itemList = fetchListingData(mContext, web3, cred, gasPrice, addresses);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    private static ArrayList<Item> fetchListingData(Activity mContext, Web3j web3, Credentials cred, BigInteger gasPrice, ArrayList<String> addresses) {
        ArrayList<Item> itemList = new ArrayList<>();
//      ArrayList<Rental> rentalList = new ArrayList<>();

        // Generate new java Rental wrapper for every address found in the Rental Directory
        for(String address : addresses){
            Rental rental = Rental.load(address, web3, cred, Contract.GAS_PRICE, Contract.GAS_LIMIT);
            try {
                //Retrieve IPFS hashes as string and split
                String[] ipfsData = rental.ipfsHashes().send().split(",");

                //First hash will always be the item details
                JSONObject rentalDetails = retrieveItemDetailsFromIPFS(ipfsData[0]);
                //Any remaining hashes are images
                ArrayList<File> imageFiles = retrieveImagesFromIPFS(mContext, Arrays.copyOfRange(ipfsData, 1, ipfsData.length));

                String title = rentalDetails.getString("Title");
                double deposit = rentalDetails.getDouble("Deposit");
                double price = rentalDetails.getDouble("Price");
                String description = rentalDetails.getString("Description");

                Item item = new Item(rental.getContractAddress(), imageFiles, title, deposit, price, description);
                itemList.add(item);
//              rentalList.add(rental);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*
        -- INTENTION:
        Want to cache Rental wrappers somewhere for later as loading them costs Ether
        Make Async so it does not hold up the retrieval thread

        -- UPDATE:
        Caching of Rental wrappers is not possible at the moment. The wrapper extends class Contract which resides
        in the Web3j library, as the Contract class is not serializable and I cannot edit it. I cannot cache the
        Rentals to files. The following method call fails with java.io.NotSerializableException, this may be solved
        in the future by extending Rental and implementing custom read, writeObject methods.

        -- WORK-AROUND
        For the moment, deployed contract addresses will be saved to Item objects and these will be
        used to load new Rental Wrappers when a user tries to rent an Item.

        AsyncUtil.execute(new AsyncCacheRentalWrappersTask(mContext, rentalList));
        */

        return itemList;
    }

//    public static void cacheRentalWrappers(Activity mContext, ArrayList<Rental> rentalList) {
//        File cacheDir = mContext.getCacheDir();
//        File cachedRentals = new File(cacheDir, "Rental_Wrappers_Folder");
//        FileOutputStream fileOutputStream = null;
//        ObjectOutputStream objectOutputStream = null;
//        File rentalFile;
//
//        try {
//            for(Rental rental : rentalList) {
//                rentalFile = new File(cachedRentals, rental.getContractAddress());
//
//                fileOutputStream = new FileOutputStream(rentalFile);
//                objectOutputStream = new ObjectOutputStream(fileOutputStream);
//                objectOutputStream.writeObject(rental);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fileOutputStream != null) fileOutputStream.close();
//                if (objectOutputStream != null) objectOutputStream.close();
//            } catch (Exception ignored) {
//            }
//        }
//    }
}
