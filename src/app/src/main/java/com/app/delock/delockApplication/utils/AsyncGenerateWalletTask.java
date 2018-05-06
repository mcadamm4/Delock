package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.SplashActivity;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static android.widget.Toast.makeText;
import static com.app.delock.delockApplication.WalletReadyStatus.walletReady;
import static com.app.delock.delockApplication.utils.GetBalanceUtils.getAccountBalanceEther;

/**
 * Created by Marky on 18/04/2018.
 */

@SuppressLint("StaticFieldLeak")
public class AsyncGenerateWalletTask extends AsyncTask<Void, Void, String[]>
{
    private final SplashActivity splashActivity;
    private String password;
    private File path;

    public AsyncGenerateWalletTask(SplashActivity splashActivity, String password, File path){
        this.splashActivity = splashActivity;
        this.password = password;
        this.path = path;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast toast = makeText(splashActivity, "Generating wallet..", Toast.LENGTH_LONG);
        toast.show();
    }
    @Override
    protected String[] doInBackground(Void... params) {
        String result = null;
        String walletPath = null;
        try {
            String s = WalletUtils.generateLightNewWalletFile(password, path);
            walletPath = path.getPath()+"/"+s;
            Credentials cred = WalletUtils.loadCredentials(password, walletPath);
            result = cred.getAddress();
            // Request test ether from Ethereum faucet
            getTestEtherFromFaucet(result);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | CipherException | IOException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return new String[]{result, walletPath};
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
        //this method will be running on UI thread
        saveAddress(result[0]);
        savePassword(); //For dev convenience, remove this and prompt user every time in reality
        saveWalletPath(result[1]);
        walletReady = true;
    }

    private void getTestEtherFromFaucet(String address) {
        try {
            URL url = new URL("http://faucet.ropsten.be:3001/donate/" + address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(60000);
            int responseCode = con.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveAddress(String address) {
        SharedPreferences sharedPreferences = splashActivity.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.ACCOUNT_ADDRESS_SHARED_PREF, address);
        editor.apply();
    }
    //Remove this and prompt for user input in reality
    private void savePassword() {
        SharedPreferences sharedPreferences = splashActivity.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PASSWORD_SHARED_PREF, password);
        editor.apply();
    }
    private void saveWalletPath(String walletPath) {
        SharedPreferences sharedPreferences = splashActivity.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.WALLET_PATH_SHARED_PREF, walletPath);
        editor.apply();
    }
}
