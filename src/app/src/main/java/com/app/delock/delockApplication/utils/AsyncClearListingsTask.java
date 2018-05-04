package com.app.delock.delockApplication.utils;

/**
 * Created by Marky on 03/05/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.smartcontract_wrappers.RentalDirectory;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.math.BigInteger;

public class AsyncClearListingsTask extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public AsyncClearListingsTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //Call Rental Directory and get all listing contract addresses
        //Iterate over addresses and get details hash, image hashes
        RentalDirectory rentalDirectory;
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
            rentalDirectory.clearRentals().send();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}