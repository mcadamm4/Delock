package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.app.delock.delockApplication.item.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.app.delock.delockApplication.utils.IpfsUtils.retrieveItemDetailsFromIPFS;
import static com.app.delock.delockApplication.utils.IpfsUtils.retrieveImagesFromIPFS;

/**
 * Created by Marky on 18/04/2018.
 */
public class AsyncRetrieveListingsTask extends AsyncTask<Void, Void, Bitmap> {
    /*
    Call getListings from RentalDirectory
    Returns String[] of Rental contract addresses
    Iterate over addresses and extract ipfs data for each + (price & deposit info from contract)
    Populate cards
    */

    @SuppressLint("StaticFieldLeak")
    private final Context mContext;

    public AsyncRetrieveListingsTask(Context _mContext) throws JSONException {
        mContext = _mContext;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        //Call Rental Directory and get all listing contract addresses
        //Iterate over addresses and get details hash, image hashes

        /*
        String[] rentalAddresses = rentalDirectory.getListings();
        ArrayList<Item> items = new ArrayList<>();
        for(address in rentaldirectory){
            //Rental wrapper needed
            Rental rental = new Rental(address);
            ArrayList<Bitmap> rentalImages = retrieveImagesFromIPFS(rental.imageHash());
            JSONObject rentalDetails = retrieveItemDetailsFromIPFS(rental.detailsHash());
            String title = null;
            try {
                String title = (String) rentalDetails.get("Title");
                String deposit = (String) rentalDetails.get("Deposit");
                String price = (String) rentalDetails.get("Price");
                String description = (String) rentalDetails.get("Desc");

                Item item = new Item(rental???, bitmaps, title, deposit, price, description);
                items.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return items;
        */



        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result){
        //        im.setImageBitmap(result);
        //Alert adaptor to change?

    }
}
