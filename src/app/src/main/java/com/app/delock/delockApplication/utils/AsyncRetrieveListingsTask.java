package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.item.ItemsAdapter;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.app.delock.delockApplication.utils.ContractUtils.retrieveListings;
import static com.app.delock.delockApplication.utils.listingsCachedState.listingsCached;

/**
 * Created by Marky on 18/04/2018.
 */
public class AsyncRetrieveListingsTask extends AsyncTask<Void, Void, ArrayList<Item>> {

    @SuppressLint("StaticFieldLeak")
    private LottieAnimationView lottie;
    @SuppressLint("StaticFieldLeak")
    private final Activity mContext;
    private ItemsAdapter adapter;
    private Web3j web3;
    private Credentials cred;

    public AsyncRetrieveListingsTask(LottieAnimationView lottie, Activity _mContext, ItemsAdapter adapter) {
        this.lottie = lottie;
        this.mContext = _mContext;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        lottie.setAnimation(R.raw.loader_4, LottieAnimationView.CacheStrategy.Strong);
        lottie.setVisibility(View.VISIBLE);

        web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Constants.SHARED_PREFS, 0);
        String walletPath = sharedPreferences.getString(Constants.WALLET_PATH_SHARED_PREF, "No address found");
        String password = sharedPreferences.getString(Constants.PASSWORD_SHARED_PREF, "No address found");
        try {
            cred = WalletUtils.loadCredentials(password, walletPath);
        } catch (IOException | CipherException e) {
            e.printStackTrace();
        }
        File cacheDir = mContext.getCacheDir();
        File cachedFolder;

        // Create cache folders
        if(!new File(cacheDir, "Listings_Folder").exists()){
            cachedFolder = new File(cacheDir, "Listings_Folder");
            cachedFolder.mkdir();
        }
/*
        Rental Wrapper caching not working - problem elaborated on in ContractUtils.fetchListingData()

        if(!new File(cacheDir, "Rental_Wrappers_Folder").exists()){
        cachedFolder = new File(cacheDir, "Rental_Wrappers_Folder");
        cachedFolder.mkdir();
        }
*/
    }

    @Override
    protected ArrayList<Item> doInBackground(Void... voids) {
        ArrayList<Item> itemlist = null;
        //If listings are not already in cache
        if(!listingsCached) {
            itemlist = retrieveListings(mContext, web3, cred);
            for (Item item : itemlist) {
                item.saveObject(mContext);
            }
            listingsCached = true;
        }
        return itemlist;
    }

    @Override
    protected void onPostExecute(ArrayList<Item> itemList) {
        //Set back to false if user hits refresh button
        if(itemList==null)
            AsyncUtil.execute(new AsyncLoadListingsTask(lottie, mContext, adapter));
        else {
            adapter.setItemsList(itemList);
            adapter.notifyDataSetChanged();
            lottie.setVisibility(View.INVISIBLE);
        }
    }
}
