package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.delock.delockApplication.item.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.app.delock.delockApplication.utils.ContractUtils.deployContract;
import static com.app.delock.delockApplication.utils.IpfsUtils.publishToIPFS;
import static com.app.delock.delockApplication.utils.IpfsUtils.retrieveImagesFromIPFS;
import static com.app.delock.delockApplication.utils.IpfsUtils.retrieveItemDetailsFromIPFS;

/**
 * Created by Marky on 01/05/2018.
 */


public class AsyncCreateNewItemTask extends AsyncTask<Void, Void, Boolean> {
    private String newRentalAddress = null;
    @SuppressLint("StaticFieldLeak")
    private final Context mContext;
    private final File[] imageFiles;
    private final JSONObject jsonData;
    private Item item;
    private TextView view;
    private ImageView im;

    public AsyncCreateNewItemTask(Context _mContext, File[] _imageFiles, JSONObject _jsonData, Item _item, TextView view, ImageView im){
        this.mContext = _mContext;
        this.imageFiles = _imageFiles;
        this.jsonData = _jsonData;
        this.item = _item;
        this.view = view;
        this.im = im;
    }

    @Override
    protected void onPreExecute() {
        /* this method will be running on UI thread */
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        String[] ipfsHashes = publishToIPFS(mContext, imageFiles, jsonData);
        //SET ITEM IPFS HASHES BEFORE DEPLOYING CONTRACT
        return deployContract(mContext, ipfsHashes, item);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        String message;
        if(result) message = "Contract deployed successfully";
        else message = "Contract was not deployed";

        Toast toast = Toast.makeText(mContext.getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
//        view.setText(result[0]);

    }

}

