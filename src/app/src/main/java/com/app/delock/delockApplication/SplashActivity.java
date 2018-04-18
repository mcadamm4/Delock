package com.app.delock.delockApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.delock.delockApplication.browse.BrowseActivity;

import org.ligi.kaxt.ContextExtensionsKt;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;

import io.ipfs.kotlin.IPFS;
import io.ipfs.kotlin.model.VersionInfo;

import static android.widget.Toast.makeText;

public class SplashActivity extends AppCompatActivity {
    public IPFS ipfs = new IPFS();
    IPFSDaemon daemon;
    Activity activity = this;
    private TextView addressInput;
    String address;
    boolean validAddress = true;

    String token = "kv4a42NG93ZwJ9h0lZqK";
    String url = "https://ropsten.infura.io/";
    Web3j web3 = Web3jFactory.build(new HttpService(url + token));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 0);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);

        if(firstStart) {
            daemon = new IPFSDaemon(this.getApplicationContext());
            new AsyncDownloadTask().execute();
        }
        addressInput = (TextView) findViewById(R.id.address_input);
        Button confirmButton = (Button) findViewById(R.id.confirmButton);
        address = addressInput.getText().toString();

        String address_exists = sharedPreferences.getString("accountAddress", null);
        //User already has an address
        if(address_exists!=null){
             addressInput.setVisibility(View.INVISIBLE);
             confirmButton.setVisibility(View.INVISIBLE);

             startIPFSDaemon();
        } else { //User adds new address
            confirmButton.setOnClickListener((View it) -> {
                String accountAddress = addressInput.getText().toString();
//                new AsyncAddressValidator().execute();

                //If address is valid
                if(validAddress) {
                    saveAddress(accountAddress);
                    startIPFSDaemon();
                } else {
                    Toast toast = makeText(activity, "Please enter a valid address", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }
    }

    private void startIPFSDaemon() {
        SplashActivity.this.startService(new Intent(SplashActivity.this, IPFSDaemonService.class));
        State.INSTANCE.setDaemonRunning(true);
        (new Thread(() -> {
            VersionInfo version = null;

            while(version == null) {
                try {
                    version = ipfs.getInfo().version();
                } catch (Exception ignored) {
                    ;
                }
            }
            SplashActivity.this.runOnUiThread(() -> {
                ContextExtensionsKt.startActivityFromClass(SplashActivity.this, BrowseActivity.class);
            });
        })).start();
    }

    private void saveAddress(String address) {
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accountAddress", address);
        editor.apply();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        //STOP IPFS DAEMON SERVICE
        SplashActivity.this.stopService(new Intent(SplashActivity.this, IPFSDaemonService.class));
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncDownloadTask extends AsyncTask<Void, Void, String[]>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /* this method will be running on UI thread */
            Toast toast = makeText(activity, "Downloading...", Toast.LENGTH_LONG);
            toast.show();
        }
        @Override
        protected String[] doInBackground(Void... params) {
            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            String result = daemon.download(activity);
            return new String[]{result};
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            //this method will be running on UI thread
            Toast toast = makeText(activity, result[0], Toast.LENGTH_LONG);
            toast.show();

            SharedPreferences prefs = getSharedPreferences("prefs", 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
        }
    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class AsyncAddressValidator extends AsyncTask<Void, Void, String[]>
//    {
//        private Web3j web3;
//
//        @Override
//        protected void onPreExecute() {
//            /* this method will be running on UI thread */
//            super.onPreExecute();
//        }
//        @Override
//        protected String[] doInBackground(Void... params) {
//            //this method will be running on background thread so don't update UI frome here
//            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
//            web3 = Web3jFactory.build(new HttpService(url + token));
//            EthGetBalance ethGetBalance = null;
//            try {
//                ethGetBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            return new String[]{};
//        }
//
//        @Override
//        protected void onPostExecute(String[] result) {
//            super.onPostExecute(result);
//            //this method will be running on UI thread
//            validAddress = result[0].compareTo("true") == 0;
//        }
//    }
}
