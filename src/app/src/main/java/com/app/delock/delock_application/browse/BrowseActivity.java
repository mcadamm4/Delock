package com.app.delock.delock_application.browse;

import android.content.res.Resources;
import android.graphics.Rect;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import java.lang.reflect.Field;

import java.util.List;
import java.util.ArrayList;
import android.util.TypedValue;

import com.app.delock.delock_application.item.Item;
import com.app.delock.delock_application.item.ItemsAdapter;
import com.app.delock.delock_application.R;
import com.app.delock.delock_application.settings.SettingsActivity;
import com.bumptech.glide.Glide;
import de.petendi.ethereum.android.EthereumAndroid;
import de.petendi.ethereum.android.EthereumAndroidFactory;
import de.petendi.ethereum.android.EthereumNotInstalledException;

public class BrowseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private List<Item> itemsList;
    private ItemsAdapter.ItemsAdapterListener listener;

    private TextView mTextMessage;
    private EthereumAndroid ethereumAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
            
            Field devField = EthereumAndroidFactory.class.getDeclaredField("DEV");
            devField.setAccessible(true);
            devField.set(null, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        initialize();
//        setContentView(R.layout.activity_browse);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        //Card view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        itemsList = new ArrayList<>();

        adapter = new ItemsAdapter(this, itemsList, listener);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareItems();
    }

    // Init ethereum or warn if wallet not installed
    private void initialize() {
        EthereumAndroidFactory ethereumAndroidFactory = new EthereumAndroidFactory(this);
        try {
            ethereumAndroid = ethereumAndroidFactory.create();
        } catch (EthereumNotInstalledException e) {
            Toast.makeText(this, R.string.ethereum_ethereum_not_installed, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    //Init collapsing toolbar - Will show\hide on scroll
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        //hiding & showing the title when toolbar expanded and collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

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
        
        Item item = new Item("Vintage Bicycle", 13, covers[0]);
        itemsList.add(item);
        item = new Item("One-Bed House", 10, covers[1]);
        itemsList.add(item);
        item = new Item("Penthouse", 100, covers[2]);
        itemsList.add(item);
        item = new Item("Audi", 25, covers[3]);
        itemsList.add(item);
        item = new Item("Skateboard", 2, covers[4]);
        itemsList.add(item);
        item = new Item("Coffee Machine", 3, covers[5]);
        itemsList.add(item);
        item = new Item("Road Bike", 15, covers[6]);
        itemsList.add(item);
        item = new Item("Study Space", 10, covers[7]);
        itemsList.add(item);
        item = new Item("Four Bed House", 34, covers[8]);
        itemsList.add(item);
        item = new Item("Kawaski Motorbike", 13, covers[9]);
        itemsList.add(item);

        adapter.notifyDataSetChanged();
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

}
