package com.app.delock.delockApplication.Utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.app.delock.delockApplication.IPFSDaemon;
import com.app.delock.delockApplication.SplashActivity;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static android.widget.Toast.makeText;

/**
 * Created by Marky on 18/04/2018.
 */

@SuppressLint("StaticFieldLeak")
public class AsyncGenerateWalletTask extends AsyncTask<Void, Void, String[]>
{
    private final View view;
    private final SplashActivity splashActivity;
    private String password;
    private File path;

    public AsyncGenerateWalletTask(View view, SplashActivity splashActivity, String password, File path){
        this.view = view;
        this.splashActivity = splashActivity;
        this.password = password;
        this.path = path;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
            /* this method will be running on UI thread */
        Toast toast = makeText(splashActivity, "Generating wallet..", Toast.LENGTH_LONG);
        toast.show();
    }
    @Override
    protected String[] doInBackground(Void... params) {
        //this method will be running on background thread so don't update UI frome here
        //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

        String result = null;
        String s = null;
        try {
            s = WalletUtils.generateLightNewWalletFile(password, path);
            Credentials cred = WalletUtils.loadCredentials(password, path+"/"+s);
            result = cred.getAddress();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | CipherException | IOException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return new String[]{result};
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
        //this method will be running on UI thread
        saveAddress(result[0]);
    }

    private void saveAddress(String address) {
        SharedPreferences sharedPreferences = splashActivity.getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accountAddress", address);
        editor.apply();
    }
}
