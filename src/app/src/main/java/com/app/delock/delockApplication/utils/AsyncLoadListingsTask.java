package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.item.ItemsAdapter;

import java.io.File;
import java.util.ArrayList;

import static com.app.delock.delockApplication.utils.listingsCachedState.listingsCached;

/**
 * Created by Marky on 03/05/2018.
 */

public class AsyncLoadListingsTask extends AsyncTask<Void, Void, ArrayList<Item>> {

    private final ItemsAdapter adapter;
    @SuppressLint("StaticFieldLeak")
    private LottieAnimationView lottie;
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public AsyncLoadListingsTask(LottieAnimationView lottie, Activity _mContext, ItemsAdapter adapter) {
        this.lottie = lottie;
        this.mContext = _mContext;
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<Item> doInBackground(Void... voids) {
        ArrayList<Item> itemList = new ArrayList<>();
        while(!listingsCached){
            int stayHereForAWhile = 1;
        }

        File cacheDir = mContext.getCacheDir();
        File cacheListings = new File(cacheDir, "Listings_Folder");

        File[] listings = cacheListings.listFiles();

        Item item;
        for (File cachedListing : listings) {
            item = new Item();
            itemList.add(item.getObject(cachedListing));
        }
        return itemList;
    }

    @Override
    protected void onPostExecute(ArrayList<Item> items) {
        super.onPostExecute(items);
        if(!items.isEmpty()) {
            adapter.setItemsList(items);
            adapter.notifyDataSetChanged();
            lottie.setVisibility(View.INVISIBLE);
        }
    }
}
