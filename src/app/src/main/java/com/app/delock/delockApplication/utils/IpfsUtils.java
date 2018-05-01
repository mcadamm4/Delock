package com.app.delock.delockApplication.utils;

import com.app.delock.delockApplication.item.Item;

import org.json.JSONObject;

import java.io.File;

import io.ipfs.kotlin.IPFS;

/**
 * Created by Marky on 01/05/2018.
 */

public class IpfsUtils {

    public static String[] publishToIPFS(File[] imageFiles, JSONObject jsonData){
        String[] ipfsHashes = new String[3];
        int i=0;
        for(File file : imageFiles){
            String multihash = new IPFS().getAdd().file(file).getHash();
            ipfsHashes[i] = multihash;
            i++;
        }

        //CAREFUL, WILL GET A NULL POINTER HERE IF USER DOESN'T SEND 3 IMAGE FILES
        ipfsHashes[i] = new IPFS().getAdd().file(new File(String.valueOf(jsonData))).getHash();
        return ipfsHashes;
    }
}
