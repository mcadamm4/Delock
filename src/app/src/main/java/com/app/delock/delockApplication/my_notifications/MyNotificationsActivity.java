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
import android.widget.ArrayAdapter;
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
    private DrawerLayout mDrawerLayout;
    private String address;
    LottieAnimationView lottieAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notifications);

        String[] StringArray = {"Item1 has been rented", "Item2 has been returned"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_my_notifications, StringArray);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


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
}
