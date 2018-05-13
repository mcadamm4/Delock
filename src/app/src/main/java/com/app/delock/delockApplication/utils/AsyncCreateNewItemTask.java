package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.item.Item;

import org.json.JSONObject;

import java.io.File;

import static com.app.delock.delockApplication.utils.ContractUtils.deployContract;
import static com.app.delock.delockApplication.utils.IpfsUtils.publishToIPFS;

/**
 * Created by Marky on 01/05/2018.
 */


public class AsyncCreateNewItemTask extends AsyncTask<Void, Void, String> {
    private String newRentalAddress = null;
    @SuppressLint("StaticFieldLeak")
    private final Activity mContext;
    private final File[] imageFiles;
    private final JSONObject jsonData;
    private Item item;

    public AsyncCreateNewItemTask(Activity _mContext, File[] _imageFiles, JSONObject _jsonData, Item _item) {
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
    protected String doInBackground(Void... params) {

        String[] ipfsHashes = publishToIPFS(mContext, imageFiles, jsonData);
        //SET ITEM IPFS HASHES BEFORE DEPLOYING CONTRACT
        return deployContract(mContext, ipfsHashes, item);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mContext.findViewById(R.id.deploying_contract).setVisibility(View.INVISIBLE);
        mContext.findViewById(R.id.deployed_contract_success).setVisibility(View.VISIBLE);
        mContext.findViewById(R.id.gen_password_hint).setVisibility(View.VISIBLE);
        mContext.findViewById(R.id.gen_password_input).setVisibility(View.VISIBLE);
        mContext.findViewById(R.id.lock_new_item_button).setVisibility(View.VISIBLE);

        String message;
        if(result.compareTo("")==0) message = "Contract was not deployed";
        else message = "Contract deployed successfully to address: " + result;

        Toast toast = Toast.makeText(mContext.getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}

