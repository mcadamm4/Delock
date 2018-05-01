package com.app.delock.delockApplication.utils;

import android.content.Context;

import com.app.delock.delockApplication.Constants;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import io.ipfs.kotlin.IPFS;

/**
 * Created by Marky on 01/05/2018.
 */

class IpfsUtils {

    static String[] publishToIPFS(Context mContext, File[] imageFiles, JSONObject jsonData){
        String[] ipfsHashes = new String[imageFiles.length+1];
        int i=0;

        FileOutputStream outputStream;
        try {
            String jsonAsText = jsonData.toString();

            File newFile = new File(mContext.getCacheDir() + "/" + Constants.IPFS_JSON_FILE_NAME);
            newFile.createNewFile();

//            outputStream = mContext.openFileOutput(String.valueOf(newFile), Context.MODE_PRIVATE);
//            outputStream.write(jsonAsText.getBytes());
//            outputStream.close();
            ipfsHashes[i] = new IPFS().getAdd().file(newFile).getHash();
            newFile.delete();
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

    static String retrieveFromIPFS(String ipfsHashes){
//        for(String str : ipfsHashes)
        final String cat = new IPFS().getGet().cat(ipfsHashes);
        int i =1;
        return cat;
    }
}
