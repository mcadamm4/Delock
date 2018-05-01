package com.app.delock.delockApplication.item;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Marky on 15/03/2018.
 */

public class Item implements Serializable {

    public File[] itemImages;
    public String detailsIpfsHash;
    public double itemDeposit;
    public double itemCost;

    public String title;
    public String itemDescription;

    public Item() {
    }

    public Item(File[] _itemImages, String _detailsIpfsHash, String _title, double _itemDeposit, double _itemCost, String _itemDescription){
        this.itemImages = _itemImages;
        this.detailsIpfsHash = _detailsIpfsHash;
        this.title = _title;
        this.itemDeposit = _itemDeposit;
        this.itemCost = _itemCost;
        this.itemDescription = _itemDescription;
    }
}
