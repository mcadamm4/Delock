package com.app.delock.delock_application.my_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.delock.delock_application.R;
import com.app.delock.delock_application.browse.BrowseActivity;
import com.app.delock.delock_application.dashboard.DashboardActivity;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;

public class MyProfileActivity extends AppCompatActivity {
    Web3j web3;
    String infuraToken = "kv4a42NG93ZwJ9h0lZqK";
    String clientVersion;
    String gasPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView textView = (TextView) findViewById(R.id.textView);

        web3 = Web3jFactory.build(new HttpService("https://ropsten.infura.io/" + infuraToken));
        web3.web3ClientVersion().observable().subscribe(x -> {
            clientVersion = x.getWeb3ClientVersion();
        });
//        web3.ethGasPrice().observable().subscribe(x -> {
//            gasPrice = x.getGasPrice().toString();
//        });
        String response = ("Version: "+clientVersion);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(response);

        BottomNavigationView navMenu = findViewById(R.id.navigation);
        navMenu.setSelectedItemId(R.id.navigation_myProfile);
        navMenu.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
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
                    }
                });
    }
}
