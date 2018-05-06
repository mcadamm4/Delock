package com.app.delock.delockApplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import io.ipfs.kotlin.IPFS;

/**
 * Created by Marky on 01/05/2018.
 */

public class IpfsUtils {

    public static String[] publishToIPFS(Activity mContext, File[] imageFiles, JSONObject jsonData){
        String[] ipfsHashes = new String[imageFiles.length+1];
        int i=0;

        FileOutputStream outputStream;
        try {
            String jsonAsText = jsonData.toString();
            ipfsHashes[i] = new IPFS().getAdd().string(jsonAsText).getHash();
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

    public static JSONObject retrieveItemDetailsFromIPFS(String hash){
        JSONObject json = null;
        try {
            // Use IPFS Daemon to get file from peers
            final String result = new IPFS().getGet().cat(hash);
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static ArrayList<File> retrieveImagesFromIPFS(Activity mContext, String[] ipfsHash) {
        ArrayList<File> bmpList = new ArrayList<>();
        URL url;
        URLConnection connection;
        InputStream input = null;
        OutputStream output = null;
        File cacheDir = mContext.getCacheDir();
        byte data[] = new byte[1024];
        try {
            for(String hash : ipfsHash){
                url = new URL("https://ipfs.io/ipfs/" + hash);
                connection = url.openConnection();
                connection.connect();
                input = new BufferedInputStream(url.openStream(), 8192);
                output = new FileOutputStream(cacheDir + hash);
                connection.setConnectTimeout(60000);
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                bmpList.add(new File(cacheDir + hash));
            }
            assert output != null;
            output.flush();
            output.close();
            input.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return bmpList;
    }
}
