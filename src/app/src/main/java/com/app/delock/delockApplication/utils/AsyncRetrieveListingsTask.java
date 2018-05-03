package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.item.ItemsAdapter;

import java.util.ArrayList;

import static com.app.delock.delockApplication.utils.ContractUtils.retrieveListings;

/**
 * Created by Marky on 18/04/2018.
 */
public class AsyncRetrieveListingsTask extends AsyncTask<Void, Void, ArrayList<Item>> {
    /*
    Call getListings from RentalDirectory
    Returns String[] of Rental contract addresses
    Iterate over addresses and extract ipfs data for each + (price & deposit info from contract)
    Populate cards
    */

    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    private ItemsAdapter adapter;

    public AsyncRetrieveListingsTask(Context _mContext, ItemsAdapter adapter) {
        this.mContext = _mContext;
        this.adapter = adapter;
    }

    @Override
    protected ArrayList<Item> doInBackground(Void... voids) {
        //Call Rental Directory and get all listing contract addresses
        //Iterate over addresses and get details hash, image hashes

        ArrayList<Item> items = retrieveListings(mContext);

        return items;
    }

    @Override
    protected void onPostExecute(ArrayList<Item> result){
//        Alert adaptor to change?
        adapter.setItemsList(result);
    }
}
