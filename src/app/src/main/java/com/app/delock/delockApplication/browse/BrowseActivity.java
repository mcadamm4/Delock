package com.app.delock.delockApplication.browse;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.utils.AsyncGetBalanceTask;
import com.app.delock.delockApplication.utils.AsyncUtil;
import com.app.delock.delockApplication.add_item.AddItemActivity;
import com.app.delock.delockApplication.dashboard.DashboardActivity;
import com.app.delock.delockApplication.details.AccountDetailsActivity;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.item.ItemsAdapter;
import com.app.delock.delockApplication.my_notifications.MyNotificationsActivity;
import com.app.delock.delockApplication.settings.SettingsActivity;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.web3j.protocol.Web3j;

import java.util.ArrayList;
import java.util.HashMap;

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
        mDrawerLayout.addDrawerListener(
            new DrawerLayout.DrawerListener() {
                @SuppressLint("ObsoleteSdkInt")
                @Override
                public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                    // Respond when the drawer's position changes
                    AsyncUtil.execute(new AsyncGetBalanceTask(drawerView, getApplicationContext()));
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

    //-----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    //Add a few items for testing
//    private void prepareItems() {
//        int[] covers = new int[]{
//                R.drawable.vintage_bicycle,
//                R.drawable.one_bed_house,
//                R.drawable.penthouse,
//                R.drawable.audi,
//                R.drawable.skateboard,
//                R.drawable.coffee,
//                R.drawable.road_bike,
//                R.drawable.study,
//                R.drawable.four_bed_house,
//                R.drawable.motorbike};
//
//        HashMap<String,String> url_maps = new HashMap<String, String>();
//        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
//        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
//        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
//        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
//
//        Item item = new Item("Vintage Bicycle", "Bicycle",  13, covers[0]);
//        itemsList.add(item);
//        item = new Item("One-Bed House", "Housing", 10, covers[1]);
//        itemsList.add(item);
//        item = new Item("Penthouse", "Housing", 100, covers[2]);
//        itemsList.add(item);
//        item = new Item("Audi", "Motor", 25, covers[3]);
//        itemsList.add(item);
//        item = new Item("Skateboard", "Sport", 2, covers[4]);
//        itemsList.add(item);
//        item = new Item("Coffee Machine", "Kitchen", 3, covers[5]);
//        itemsList.add(item);
//        item = new Item("Road Bike", "Bicycle", 15, covers[6]);
//        itemsList.add(item);
//        item = new Item("Study Space", "Spaces", 10, covers[7]);
//        itemsList.add(item);
//        item = new Item("Four Bed House", "Housing",34, covers[8]);
//        itemsList.add(item);
//        item = new Item("Kawaski Motorbike", "Motor",13, covers[9]);
//        itemsList.add(item);
//
//        adapter.notifyDataSetChanged();
//    }


}
