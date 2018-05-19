package com.app.delock.delockApplication.my_notifications;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.add_item.AddItemActivity;
import com.app.delock.delockApplication.browse.BrowseActivity;
import com.app.delock.delockApplication.dashboard.DashboardActivity;
import com.app.delock.delockApplication.details.AccountDetailsActivity;
import com.app.delock.delockApplication.settings.SettingsActivity;
import com.app.delock.delockApplication.utils.AsyncGetBalanceTask;
import com.app.delock.delockApplication.utils.AsyncUtil;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class MyNotificationsActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    private DrawerLayout mDrawerLayout;
    private String address;
    LottieAnimationView lottieAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notifications);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        imageView = findViewById(R.id.imageView);

        //Retrieve information from ethereum
        new AsyncInfo().execute();

        //WALLET ADDRESS
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.SHARED_PREFS, 0);
        address = sharedPreferences.getString(Constants.ACCOUNT_ADDRESS_SHARED_PREF, "No address found");

        //DRAWER
        setupDrawer();

        BottomNavigationView navMenu = findViewById(R.id.navigation);
        navMenu.setSelectedItemId(R.id.navigation_notifications);
        navMenu.setOnNavigationItemSelectedListener(
            menuItem -> {
                Intent intent;
                // set item as selected to persist highlight
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(MyNotificationsActivity.this, BrowseActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_dashboard:
                        intent = new Intent(MyNotificationsActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_notifications:
                        break;
                }
                return true;
            });
    }

    private void setupDrawer() {
        //DRAWER
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                (MenuItem menuItem) -> {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();

                    Intent myIntent;
                    switch(menuItem.getItemId()) {
                        case R.id.nav_add_item: {
                            myIntent = new Intent(this, AddItemActivity.class);
                            startActivity(myIntent);
                            break;
                        }
                        case R.id.nav_settings: {
                            myIntent = new Intent(this, SettingsActivity.class);
                            startActivity(myIntent);
                            break;
                        }
                    }
                    return true;
                });
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @SuppressLint("ObsoleteSdkInt")
                    @Override
                    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                        AsyncUtil.execute(new AsyncGetBalanceTask(drawerView, address));
                    }

                    @Override
                    public void onDrawerOpened(@NonNull View drawerView) {
                        // Respond when the drawer is opened
                        ImageView copy = findViewById(R.id.copy_address);
                        copy.setOnClickListener(v -> {
                            // Gets a handle to the clipboard service.
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            // Creates a new text clip to put on the clipboard
                            ClipData clip = ClipData.newPlainText("Ethereum Address", address);
                            // Set the clipboard's primary clip.
                            assert clipboard != null;
                            clipboard.setPrimaryClip(clip);

                            Toast.makeText(MyNotificationsActivity.this, "Address copied to clipboard", Toast.LENGTH_LONG).show();
                        });
                        Button detailsButton = findViewById(R.id.tap_for_details);
                        detailsButton.setOnClickListener(view -> {
                            Intent intent1 = new Intent(MyNotificationsActivity.this, AccountDetailsActivity.class);
                            startActivity(intent1);
                        });
                    }

                    @Override
                    public void onDrawerClosed(@NonNull View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StaticFieldLeak")
    private class AsyncInfo extends AsyncTask<Void, Void, String[]>
    {
        private Web3j web3;
        private Web3ClientVersion web3ClientVersion = null;
        private EthBlockNumber Web3BlockNumber = null;
        private EthGasPrice Web3GasPrice = null;

        @Override
        protected void onPreExecute() {
            /* this method will be running on UI thread */
            super.onPreExecute();
            lottieAnimation = findViewById(R.id.animation_view);
            lottieAnimation.setAnimation(R.raw.loading, LottieAnimationView.CacheStrategy.Strong);
//            myContract.setDeployedAddress("3", "0x0351fd78c0ecb443c6671b66a730372034b4faf9");
        }
        @Override
        protected String[] doInBackground(Void... params) {
            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));
            String clientVersion = getClientVersion();
            BigInteger gasPrice = getGasPrice();
            BigInteger blockNumber = getBlockNumber();
            Boolean valid = false;
//            try {
//                valid = myContract.isValid();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return new String[]{clientVersion, gasPrice.toString(), blockNumber.toString(), valid.toString()};
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            //this method will be running on UI thread
            String response = ("Version:\n\t" + result[0] + "\n\nGas Price:\n\t" + result[1] +  "\n\nBlock:\n\t" + result[2]  +  "\n\nValid Contract:\n\t" + result[3]);
            textView = (TextView) findViewById(R.id.item_description);
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
