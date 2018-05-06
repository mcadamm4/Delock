package com.app.delock.delockApplication.my_notifications;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.smartcontract_wrappers.RentalDirectory;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import static android.content.ContentValues.TAG;
import static org.web3j.protocol.core.DefaultBlockParameterName.EARLIEST;
import static org.web3j.protocol.core.DefaultBlockParameterName.LATEST;

/**
 * Created by Marky on 06/05/2018.
 */

public class NotificationService extends Service {

    RentalDirectory rentalDirectory;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        try {
            Web3j web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, 0);
            String address = sharedPreferences.getString(Constants.ACCOUNT_ADDRESS_SHARED_PREF, "No address found");
            String walletPath = sharedPreferences.getString(Constants.WALLET_PATH_SHARED_PREF, "No address found");

            //SHOULD PROMPT USER FOR PASSWORD, REMOVE SHARED PREF FOR SECURITY
            String password = sharedPreferences.getString(Constants.PASSWORD_SHARED_PREF, "No address found");
            Credentials cred = WalletUtils.loadCredentials(password, walletPath);

            rentalDirectory = RentalDirectory.load(Constants.RENTAL_DIRECTORY_ADDRESS, web3, cred, Contract.GAS_PRICE, Contract.GAS_LIMIT);
            rentalDirectory.event_NewRentalEventObservable(EARLIEST, LATEST).subscribe(event -> {
                if(address.compareTo(event._rentalOwner)==0){
                    //trigger notification
                    int i = 0;
                }
            });
            rentalDirectory.event_ReturnEventObservable(EARLIEST, LATEST).subscribe(event -> {
                if(address.compareTo(event._rentalOwner)==0){
                    //trigger notification
                    int i = 0;
                    // Attach
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();


    }
}
