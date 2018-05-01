package com.app.delock.delockApplication.item;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Marky on 15/03/2018.
 */

public class Item implements Serializable {
    public String title;
    public double itemDeposit;
    public double itemCost;
    public String itemDescription;

    public Item() {
    }

    public Item(String _title, double _itemDeposit, double _itemCost, String _itemDescription){
        this.title = _title;
        this.itemDeposit = _itemDeposit;
        this.itemCost = _itemCost;
        this.itemDescription = _itemDescription;
    }
}
