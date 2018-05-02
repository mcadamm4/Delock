package com.app.delock.delockApplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.app.delock.delockApplication.Constants;
import com.fasterxml.jackson.core.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.ipfs.kotlin.IPFS;
import io.ipfs.kotlin.IPFSConnection;

/**
 * Created by Marky on 01/05/2018.
 */

public class IpfsUtils {

    public static String[] publishToIPFS(Context mContext, File[] imageFiles, JSONObject jsonData){
        String[] ipfsHashes = new String[imageFiles.length+1];
        int i=0;

        FileOutputStream outputStream;
        try {
            String jsonAsText = jsonData.toString();

//            File newFile = new File(mContext.getCacheDir() + "/" + Constants.IPFS_JSON_FILE_NAME);
//            newFile.createNewFile();

//            outputStream = mContext.openFileOutput(String.valueOf(newFile), Context.MODE_PRIVATE);
//            outputStream.write(jsonAsText.getBytes());
//            outputStream.close();
            ipfsHashes[i] = new IPFS().getAdd().string(jsonAsText).getHash();
//            newFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(File file : imageFiles){
            i++;
            String multihash = new IPFS().getAdd().file(file).getHash();
            ipfsHashes[i] = multihash;
        }
        return ipfsHashes;
    }

    public static JSONObject retrieveItemDetailsFromIPFS(String ipfsHashes){
//        for(String str : ipfsHashes)
        final String cat = new IPFS().getGet().cat(ipfsHashes);
        JSONObject json = null;
        try {
            json = new JSONObject(cat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static ArrayList<Bitmap> retrieveImagesFromIPFS(String[] ipfsHash) {
        ArrayList<Bitmap> bmpList = new ArrayList<>();
        try {
            for(String hash : ipfsHash){
                URL url = new URL("https://ipfs.io/ipfs/" + hash);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                bmpList.add(bitmap);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    return bmpList;
    }
}
