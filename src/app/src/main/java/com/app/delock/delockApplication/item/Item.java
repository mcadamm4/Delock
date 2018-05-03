package com.app.delock.delockApplication.item;

import android.graphics.Bitmap;

import com.app.delock.delockApplication.smartcontract_wrappers.Rental;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Marky on 15/03/2018.
 */

public class Item implements Serializable {
    public Rental rental = null;
    public ArrayList<Bitmap> imageBitmaps;
    public String title;
    public double itemDeposit;
    public double itemPrice;
    public String itemDescription;

    public Item() {
    }

    public Item(Rental _rental, ArrayList<Bitmap> _imageBitmaps, String _title, double _itemDeposit, double _itemPrice, String _itemDescription){
        this.rental = _rental;
        this.imageBitmaps = _imageBitmaps;
        this.title = _title;
        this.itemDeposit = _itemDeposit;
        this.itemPrice = _itemPrice;
        this.itemDescription = _itemDescription;
    }
}
