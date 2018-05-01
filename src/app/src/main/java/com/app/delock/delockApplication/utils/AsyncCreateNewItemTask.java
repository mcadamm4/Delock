package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.app.delock.delockApplication.item.Item;

import org.json.JSONObject;

import java.io.File;

import static com.app.delock.delockApplication.utils.ContractUtils.deployContract;
import static com.app.delock.delockApplication.utils.IpfsUtils.publishToIPFS;

/**
 * Created by Marky on 01/05/2018.
 */


public class AsyncCreateNewItemTask extends AsyncTask<Void, Void, String[]> {
    private String newRentalAddress = null;
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    private final File[] imageFiles;
    private final JSONObject jsonData;
    private Item item;

    public AsyncCreateNewItemTask(Context _mContext, File[] _imageFiles, JSONObject _jsonData, Item _item){
        this.mContext = _mContext;
        this.imageFiles = _imageFiles;
        this.jsonData = _jsonData;
        this.item = _item;
    }

    @Override
    protected void onPreExecute() {
        /* this method will be running on UI thread */
        super.onPreExecute();
    }

    @Override
    protected String[] doInBackground(Void... params) {
        String[] imageHashes = publishToIPFS(imageFiles, jsonData);
        //SET ITEM IPFS HASHES BEFORE DEPLOYING CONTRACT
        deployContract(mContext, item, imageHashes);

        return new String[]{newRentalAddress};
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
//      Toast toast = Toast.makeText(AddItemActivity.this, result[0], Toast.LENGTH_LONG);
//      toast.show();

    }

}

