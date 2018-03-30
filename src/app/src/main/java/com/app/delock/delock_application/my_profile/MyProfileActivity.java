package com.app.delock.delock_application.my_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.delock.delock_application.R;
import com.app.delock.delock_application.browse.BrowseActivity;
import com.app.delock.delock_application.dashboard.DashboardActivity;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class MyProfileActivity extends AppCompatActivity {
    Web3j web3;
    String infuraToken = "kv4a42NG93ZwJ9h0lZqK";
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);

        web3 = Web3jFactory.build(new HttpService("https://ropsten.infura.io/kv4a42NG93ZwJ9h0lZqK"));
        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert web3ClientVersion != null;
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();

        EthGasPrice Web3GasPrice = null;
        try {
            Web3GasPrice = web3.ethGasPrice().sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert Web3GasPrice  != null;
        BigInteger gasPrice = Web3GasPrice.getGasPrice();


        EthBlockNumber Web3BlockNumber = null;
        try {
            Web3BlockNumber = web3.ethBlockNumber().sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert Web3BlockNumber  != null;
        BigInteger blockNumber = Web3BlockNumber.getBlockNumber();

        String response = ("Version:\n\t" + clientVersion + "\n\nGas Price:\n\t" + gasPrice + "\n\nBlock:\n\t" + blockNumber);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(response);

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
}
