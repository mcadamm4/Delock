package com.app.delock.delockApplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.delock.delockApplication.browse.BrowseActivity;

import org.ligi.kaxt.ContextExtensionsKt;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.concurrent.Future;

import io.ipfs.kotlin.IPFS;
import io.ipfs.kotlin.model.VersionInfo;

public class SplashActivity extends AppCompatActivity {
    public IPFS ipfs = new IPFS();
    private TextView addressInput;
    String token = "kv4a42NG93ZwJ9h0lZqK";
    String url = "https://ropsten.infura.io/";
    Web3j web3 = Web3jFactory.build(new HttpService(url + token));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //READ AND VALIDATE ADDRESS
        addressInput = (TextView) findViewById(R.id.address_input);
        Button confirmButton = (Button) findViewById(R.id.confirmButton);
        final boolean[] addressValidity = {false};

        TextView bal = findViewById(R.id.account_balance);
        bal.setText(web3.shhVersion().toString());

        confirmButton.setOnClickListener((View it) -> {
            String accountAddress = addressInput.getText().toString();
            addressValidity[0] = validateAddress(accountAddress);

            if(addressValidity[0]) {
                saveAddress(accountAddress);

                //START IPFS DAEMON
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
                        if(addressValidity[0])
                            ContextExtensionsKt.startActivityFromClass(SplashActivity.this, BrowseActivity.class);
                    });
                })).start();
            }
        });
    }

    private boolean validateAddress(String accountAddress){
        boolean valid = true;
        //Check account has balance

        return valid;
    }

    private void saveAddress(String address) {
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Account Address", address);
        editor.apply();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        //STOP IPFS DAEMON SERVICE
        SplashActivity.this.stopService(new Intent(SplashActivity.this, IPFSDaemonService.class));
    }
}
