package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.smartcontract_wrappers.Rental;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;

/**
 * Created by Marky on 01/05/2018.
 */

class ContractUtils {

    static void deployContract(Context mContext, String[] ipfsHashes, Item item) {
        Web3j web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFS, 0);
        String walletPath = sharedPreferences.getString(Constants.WALLET_PATH_SHARED_PREF, "No address found");

        //SHOULD PROMPT USER FOR PASSWORD, REMOVE SHARED PREF FOR SECURITY
        String password = sharedPreferences.getString(Constants.PASSWORD_SHARED_PREF, "No address found");

        BigInteger deposit = BigInteger.valueOf((long) item.itemDeposit);
        BigInteger price = BigInteger.valueOf((long) item.itemCost);
        int i = 1;

//        try {
//            Credentials cred = WalletUtils.loadCredentials(password, walletPath);
//            Rental newRental = Rental.deploy(web3, cred, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,"", deposit, price)
//                    .send();
//
//            Will need to be added to rental directory and users list of owned rentals
//
//            String newRentalAddress = newRental.getContractAddress();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return newRentalAddress;
    }
}
