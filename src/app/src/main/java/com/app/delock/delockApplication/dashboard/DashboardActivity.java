package com.app.delock.delockApplication.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
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
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.Utils.AsyncGetBalanceTask;
import com.app.delock.delockApplication.Utils.AsyncUtil;
import com.app.delock.delockApplication.browse.BrowseActivity;
import com.app.delock.delockApplication.details.AccountDetailsActivity;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.item.ItemsAdapter;
import com.app.delock.delockApplication.my_notifications.MyNotificationsActivity;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class DashboardActivity extends AppCompatActivity {
    //INFURA URL
    String url = "https://ropsten.infura.io/";
    String token = "kv4a42NG93ZwJ9h0lZqK";

    private DrawerLayout mDrawerLayout;
    private String address;

    private ItemsAdapter adapter;
    private ArrayList<Item> itemsList;
    private ItemsAdapter.ItemsAdapterListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //ADDRESS
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 0);
        address = sharedPreferences.getString("accountAddress", "No address found");

        //DRAWER
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                (MenuItem menuItem) -> {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();
                    // Add code here to update the UI based on the item selected
                    // For example, swap UI fragments here
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
                        Button detailsButton = findViewById(R.id.tap_for_details);
                        detailsButton.setOnClickListener(view -> {
                            Intent intent1 = new Intent(DashboardActivity.this, AccountDetailsActivity.class);
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
        recyclerView.addItemDecoration(new DashboardActivity.GridSpacingItemDecoration(2, dpToPx(), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        BottomNavigationView navMenu = findViewById(R.id.navigation);
        navMenu.setSelectedItemId(R.id.navigation_dashboard);
        navMenu.setOnNavigationItemSelectedListener(
                menuItem -> {
                    Intent intent;
                    // set item as selected to persist highlight
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            intent = new Intent(DashboardActivity.this, BrowseActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.navigation_dashboard:
                            break;
                        case R.id.navigation_notifications:
                            intent = new Intent(DashboardActivity.this, MyNotificationsActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
                });

        prepareItems();

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
    private void prepareItems() {
        int[] covers = new int[]{
                R.drawable.vintage_bicycle,
                R.drawable.one_bed_house,
                R.drawable.penthouse,
                R.drawable.audi,
                R.drawable.skateboard,
                R.drawable.coffee,
                R.drawable.road_bike,
                R.drawable.study,
                R.drawable.four_bed_house,
                R.drawable.motorbike};

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        Item item = new Item("Vintage Bicycle", "Bicycle",  13, covers[0]);
        itemsList.add(item);
        item = new Item("One-Bed House", "Housing", 10, covers[1]);
        itemsList.add(item);
        item = new Item("Penthouse", "Housing", 100, covers[2]);
        itemsList.add(item);
        item = new Item("Audi", "Motor", 25, covers[3]);
        itemsList.add(item);
        item = new Item("Skateboard", "Sport", 2, covers[4]);
        itemsList.add(item);
        item = new Item("Coffee Machine", "Kitchen", 3, covers[5]);
        itemsList.add(item);
        item = new Item("Road Bike", "Bicycle", 15, covers[6]);
        itemsList.add(item);
        item = new Item("Study Space", "Spaces", 10, covers[7]);
        itemsList.add(item);
        item = new Item("Four Bed House", "Housing",34, covers[8]);
        itemsList.add(item);
        item = new Item("Kawaski Motorbike", "Motor",13, covers[9]);
        itemsList.add(item);

        adapter.notifyDataSetChanged();
    }

}
