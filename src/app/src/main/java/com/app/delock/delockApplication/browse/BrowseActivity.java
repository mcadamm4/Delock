package com.app.delock.delockApplication.browse;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

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

import java.io.File;
import java.util.ArrayList;

public class BrowseActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {
    private ItemsAdapter adapter;
    MaterialSearchBar searchBar;

    private ArrayList<Item> itemsList;
    private ItemsAdapter.ItemsAdapterListener listener;
    private DrawerLayout mDrawerLayout;
    private String address;

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

        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.SHARED_PREFS, 0);
        address = sharedPreferences.getString(Constants.ACCOUNT_ADDRESS_SHARED_PREF, "No address found");

        setupDrawer(address);

        //RECYCLER VIEW
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        itemsList = new ArrayList<>();
        //ADAPTER
        adapter = new ItemsAdapter(this, itemsList, listener);

        //RECYCLER VIEW
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //SEARCH BAR
        searchBar = findViewById(R.id.searchBar);
        searchBar.setHint("Search listings..");
        //Enable searchbar callbacks
        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        //SEARCH BAR TEXT CHANGE LISTENER
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //SEARCH FILTER
                adapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        searchBar.setHapticFeedbackEnabled(true);


// FOR DEV PURPOSES, CLEAR RENTALS IN RENTAL DIRECTORY
        FloatingActionButton clearListings = findViewById(R.id.clearListings);
        clearListings.setOnClickListener((View v) -> {
                    AsyncUtil.execute(new AsyncClearListingsTask(BrowseActivity.this));
                    // Delete all cached listing items
                    File Listings_Folder = new File(BrowseActivity.this.getCacheDir(), "Listings_Folder");
                    final File[] listings = Listings_Folder.listFiles();
                    for (File listing : listings)
                        listing.delete();
                }
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

    private void setupDrawer(String address) {
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

                        Toast.makeText(BrowseActivity.this, "Address copied to clipboard", Toast.LENGTH_LONG).show();
                    });

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
    }


    @Override
    public void onBackPressed() {
        AsyncUtil.execute(new AsyncRetrieveListingsTask(findViewById(R.id.animation_view), BrowseActivity.this, adapter));
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

    //SearchBar
    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
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
