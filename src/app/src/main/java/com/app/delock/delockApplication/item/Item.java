package com.app.delock.delockApplication.item;

import android.content.Context;
import android.graphics.Bitmap;

import com.app.delock.delockApplication.smartcontract_wrappers.Rental;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Marky on 15/03/2018.
 */

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    public String address;
    public ArrayList<File> imageFiles;
    public String title;
    public double itemDeposit;
    public double itemPrice;
    public String itemDescription;

    public Item() {
    }

    public Item(String _address, ArrayList<File> _imageFiles, String _title, double _itemDeposit, double _itemPrice, String _itemDescription) {
        this.address = _address;
        this.imageFiles = _imageFiles;
        this.title = _title;
        this.itemDeposit = _itemDeposit;
        this.itemPrice = _itemPrice;
        this.itemDescription = _itemDescription;
    }

    public void saveObject(Context mContext) {
        File cacheDir = mContext.getCacheDir();
        File cachedListings = new File(cacheDir, "Listings_Folder");

        // Save Item to cache under its Contract address
        File listing = new File(cachedListings, address);

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(listing);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
                if (objectOutputStream != null) objectOutputStream.close();
            } catch (Exception ignored) {
            }
        }
    }

    public Item getObject(File file) {
        Item item = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            item = (Item) objectInputStream.readObject();
        } catch (Exception e) {
            String val = e.getMessage();
        } finally {
            try {
                if (fileInputStream != null) fileInputStream.close();
                if (objectInputStream != null) objectInputStream.close();
            } catch (Exception ignored) {
            }
        }
        return item;
    }
}
