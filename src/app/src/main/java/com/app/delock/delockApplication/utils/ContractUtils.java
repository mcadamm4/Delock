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

public class ContractUtils {

    public static void deployContract(Context mContext, Item item, String[] imageHashes) {
        Web3j web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("prefs", 0);
        String walletPath = sharedPreferences.getString("Wallet_Path", "No address found");
        String password = sharedPreferences.getString("Password", "No address found");

        try {
            Credentials cred = WalletUtils.loadCredentials(password, walletPath);
            Rental newRental = Rental.deploy(web3, cred, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, item.itemDeposit, item.itemCost)
                    .send();
            String newRentalAddress = newRental.getContractAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
