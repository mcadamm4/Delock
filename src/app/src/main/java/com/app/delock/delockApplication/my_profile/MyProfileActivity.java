package com.app.delock.delockApplication.my_profile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.browse.BrowseActivity;
import com.app.delock.delockApplication.dashboard.DashboardActivity;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class MyProfileActivity extends AppCompatActivity {
    String token = "kv4a42NG93ZwJ9h0lZqK";
    String url = "https://ropsten.infura.io/";
    TextView textView;
    ImageView imageView;
    LottieAnimationView lottieAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView);

        //Retrieve information from ethereum
        new AsyncCaller().execute();

        BottomNavigationView navMenu = findViewById(R.id.navigation);
        navMenu.setSelectedItemId(R.id.navigation_myProfile);
        navMenu.setOnNavigationItemSelectedListener(
                menuItem -> {
                    Intent intent;
                    // set item as selected to persist highlight
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            intent = new Intent(MyProfileActivity.this, BrowseActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.navigation_dashboard:
                            intent = new Intent(MyProfileActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.navigation_myProfile:
                            break;
                    }
                    return true;
                });
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncCaller extends AsyncTask<Void, Void, String[]>
    {
        ProgressDialog pdLoading = new ProgressDialog(MyProfileActivity.this);
        private Web3j web3;
        private Web3ClientVersion web3ClientVersion = null;
        private EthBlockNumber Web3BlockNumber = null;
        private EthGasPrice Web3GasPrice = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lottieAnimation = findViewById(R.id.animation_view);
            lottieAnimation.setAnimation(R.raw.loading, LottieAnimationView.CacheStrategy.Strong);
            /* this method will be running on UI thread */
        }
        @Override
        protected String[] doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            web3 = Web3jFactory.build(new HttpService(url + token));
            String clientVersion = getClientVersion();
            BigInteger gasPrice = getGasPrice();
            BigInteger blockNumber = getBlockNumber();
            return new String[]{clientVersion, gasPrice.toString(), blockNumber.toString()};
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            //this method will be running on UI thread
            String response = ("Version:\n\t" + result[0] + "\n\nGas Price:\n\t" + result[1] +  "\n\nBlock:\n\t" + result[2]);
            textView = (TextView) findViewById(R.id.textView);
            textView.setText(response);
            lottieAnimation.setVisibility(View.INVISIBLE);
        }

        private String getClientVersion() {
            try {
                web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            assert web3ClientVersion != null;
            return web3ClientVersion.getWeb3ClientVersion();
        }
        private BigInteger getGasPrice() {
            try {
                Web3GasPrice = web3.ethGasPrice().sendAsync().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            assert Web3GasPrice  != null;
            return Web3GasPrice.getGasPrice();
        }
        private BigInteger getBlockNumber() {
            try {
                Web3BlockNumber = web3.ethBlockNumber().sendAsync().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            assert Web3BlockNumber  != null;
            return Web3BlockNumber.getBlockNumber();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putDouble("myDouble", 1.9);
        savedInstanceState.putInt("MyInt", 1);
        savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
    }
}
