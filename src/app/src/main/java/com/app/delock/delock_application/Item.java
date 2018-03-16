package com.app.delock.delock_application;

/**
 * Created by Marky on 15/03/2018.
 */

public class Item {
    private String name;
    private int itemCost;
    private int thumbnail;

    public Item() {
    }

    public Item(String name, int itemCost, int thumbnail){
        this.name = name;
        this.itemCost = itemCost;
        this.thumbnail = thumbnail;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemCost() {
        return itemCost;
    }

    public void setNumOfSongs(int itemCost) {
        this.itemCost = itemCost;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
