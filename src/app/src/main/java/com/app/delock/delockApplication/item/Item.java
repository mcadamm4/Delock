package com.app.delock.delockApplication.item;

import java.io.Serializable;

/**
 * Created by Marky on 15/03/2018.
 */

public class Item implements Serializable {
    private String name;

    //Category needs to be an array of strings as items may have many categories
    private String category;
    private int itemCost;
    private int thumbnail;

    public Item() {
    }

    public Item(String name, String category, int itemCost, int thumbnail){
        this.name = name;
        this.category = category;
        this.itemCost = itemCost;
        this.thumbnail = thumbnail;
    }

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getItemCost() {
        return itemCost;
    }
    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }

    public int getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
