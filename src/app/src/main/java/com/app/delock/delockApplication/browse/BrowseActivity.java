package com.app.delock.delockApplication.browse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.add_item.AddItemActivity;
import com.app.delock.delockApplication.dashboard.DashboardActivity;
import com.app.delock.delockApplication.details.AccountDetailsActivity;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.item.ItemsAdapter;
import com.app.delock.delockApplication.my_notifications.MyNotificationsActivity;
import com.app.delock.delockApplication.settings.SettingsActivity;
import com.app.delock.delockApplication.utils.AsyncClearListingsTask;
import com.app.delock.delockApplication.utils.AsyncGetBalanceTask;
import com.app.delock.delockApplication.utils.AsyncRetrieveListingsTask;
import com.app.delock.delockApplication.utils.AsyncUtil;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

public class BrowseActivity extends AppCompatActivity {
    private ItemsAdapter adapter;
    MaterialSearchBar searchBar;

    private ArrayList<Item> itemsList;
    private ItemsAdapter.ItemsAdapterListener listener;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

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
                    case R.id.nav_identity:{
//                      myIntent = new Intent(this, SettingsActivity.class);
//                      startActivity(myIntent);
                        break;
                    }
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
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.SHARED_PREFS, 0);
        String address = sharedPreferences.getString(Constants.ACCOUNT_ADDRESS_SHARED_PREF, "No address found");

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

                    //ADD COPY BUTTON FOR ADDRESS
//                    Object var10000 = AddItemActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
//                    ClipboardManager clipboardManager = (ClipboardManager)var10000;
//                    ClipData clip = ClipData.newPlainText("hash", result[0]);
//                    assert clipboardManager != null;
//                    clipboardManager.setPrimaryClip(clip);

                    Button detailsButton = findViewById(R.id.tap_for_details);
                    detailsButton.setOnClickListener(view -> {
                        Intent intent1 = new Intent(BrowseActivity.this, AccountDetailsActivity.class);
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

        //RECYCLER VIEW
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        itemsList = new ArrayList<>();
        //ADAPTER
        adapter = new ItemsAdapter(this, itemsList, listener);

//        Web3j web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));
//        String walletPath = sharedPreferences.getString(Constants.WALLET_PATH_SHARED_PREF, "No address found");

        //SHOULD PROMPT USER FOR PASSWORD, REMOVE SHARED PREF FOR SECURITY
//        String password = sharedPreferences.getString(Constants.PASSWORD_SHARED_PREF, "No address found");
//        Credentials cred = null;
//        try {
//            cred = WalletUtils.loadCredentials(password, walletPath);
//        } catch (IOException | CipherException e) {
//            e.printStackTrace();
//        }
//
//        RentalDirectory rentalDirectory =
//                RentalDirectory.load(Constants.RENTAL_DIRECTORY_ADDRESS, web3, cred, Contract.GAS_PRICE, Contract.GAS_LIMIT);

        //RECYCLER VIEW
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //SEARCH BAR
        searchBar = findViewById(R.id.searchBar);
        searchBar.setHint("Search..");
        searchBar.setCardViewElevation(10);
        //SEARCH BAR TEXT CHANGE LISTENER
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //SEARCH FILTER
                adapter.setItemsList(itemsList);
                adapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        FloatingActionButton clearListings = findViewById(R.id.clearListings);
        clearListings.setOnClickListener(v ->
            AsyncUtil.execute(new AsyncClearListingsTask(BrowseActivity.this))
        );

        AsyncUtil.execute(new AsyncRetrieveListingsTask(findViewById(R.id.animation_view), BrowseActivity.this, adapter));

        //BOTTOM NAVIGATION BAR
        BottomNavigationView navMenu = findViewById(R.id.navigation);
        navMenu.setSelectedItemId(R.id.navigation_home);
        navMenu.setOnNavigationItemSelectedListener(
                menuItem -> {
                    Intent intent;
                    // set item as selected to persist highlight
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            break;
                        case R.id.navigation_dashboard:
                            intent = new Intent(BrowseActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.navigation_notifications:
                            intent = new Intent(BrowseActivity.this, MyNotificationsActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
                });

        //ACTION BUTTON
//        actionButton = findViewById(R.id.actionButton);
//        actionButton.setOnClickListener(view -> {
//            Intent intent = new Intent(BrowseActivity.this, MapsActivity.class);
//            startActivity(intent);
//        });
        //SAMPLE ITEMS
//        prepareItems();
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
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

    //RecyclerView item decoration - give equal margin around grid item
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge){
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // Item position
            int column = position % spanCount; //Item column

            if(includeEdge){
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
                
                if(position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if(position >= spanCount)
                    outRect.top = spacing; // item top
            }
        }
    }

    // Convert dp to pixels
    private int dpToPx(){
        Resources res = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, res.getDisplayMetrics()));
    }
}
