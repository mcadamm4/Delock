package com.app.delock.delockApplication.browse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.app.delock.delockApplication.IPFSDaemon;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.dashboard.DashboardActivity;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.item.ItemsAdapter;
import com.app.delock.delockApplication.maps.MapsActivity;
import com.app.delock.delockApplication.my_profile.MyProfileActivity;
import com.app.delock.delockApplication.settings.SettingsActivity;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import de.petendi.ethereum.android.EthereumAndroidFactory;

import static android.widget.Toast.makeText;

public class BrowseActivity extends AppCompatActivity {
    //IPFS Init
    IPFSDaemon daemon;
    Activity activity = this;

    private ItemsAdapter adapter;
    MaterialSearchBar searchBar;
    FloatingActionButton actionButton;
    private ArrayList<Item> itemsList;
    private ItemsAdapter.ItemsAdapterListener listener;
    LottieAnimationView disconnectedLottie, connectedLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);

        if(firstStart) {
            daemon = new IPFSDaemon(this.getApplicationContext());
            new AsyncDownloadTask().execute();
        }

        disconnectedLottie = (LottieAnimationView) findViewById(R.id.ipfs_status_disconnected);
        disconnectedLottie.setVisibility(View.INVISIBLE);

        connectedLottie = (LottieAnimationView) findViewById(R.id.ipfs_status_connected);
        connectedLottie.setVisibility(View.VISIBLE);

        //RECYCLER VIEW
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        itemsList = new ArrayList<>();
        //ADAPTER
        adapter = new ItemsAdapter(this, itemsList, listener);
        //RECYCLER VIEW
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //SEARCH BAR
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
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
                        case R.id.navigation_myProfile:
                            intent = new Intent(BrowseActivity.this, MyProfileActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
                });

        //ACTION BUTTON
        actionButton = findViewById(R.id.actionButton);
        actionButton.setOnClickListener(view -> {
            Intent intent = new Intent(BrowseActivity.this, MapsActivity.class);
            startActivity(intent);
        });
        //SAMPLE ITEMS
        prepareItems();
    }

    //RecyclerView item decoration - give equal margin around grid item
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge){
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
    private int dpToPx(int dp){
        Resources res = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics()));
    }

    //Open settings
    public void openSettings(View view){
        Intent myIntent = new Intent(this, SettingsActivity.class);
        startActivity(myIntent);
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncDownloadTask extends AsyncTask<Void, Void, String[]>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /* this method will be running on UI thread */
            Toast toast = makeText(activity, "Downloading...", Toast.LENGTH_LONG);
            toast.show();
        }
        @Override
        protected String[] doInBackground(Void... params) {
            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            String result = daemon.download(activity);
            return new String[]{result};
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            //this method will be running on UI thread
            Toast toast = makeText(activity, result[0], Toast.LENGTH_LONG);
            toast.show();

            SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
        }
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
