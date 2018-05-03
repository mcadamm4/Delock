package com.app.delock.delockApplication.item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.smartcontract_wrappers.Rental;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.graphics.BitmapFactory.*;
import static com.app.delock.delockApplication.R.*;
import static org.junit.Assert.*;

/**
 * Created by Marky on 03/05/2018.
 */
public class ItemsAdapterTest {
    private List<Item> itemsList;

    @Before
    public void setUp() throws Exception {
        ArrayList<Bitmap> covers = new ArrayList<>();
        covers.add(decodeFile("drawable://" + drawable.vintage_bicycle));
        ArrayList<Bitmap> covers1 = new ArrayList<>();
        covers1.add(decodeFile("drawable://" + drawable.one_bed_house));
        ArrayList<Bitmap> covers2 = new ArrayList<>();
        covers2.add(decodeFile("drawable://" + drawable.penthouse));
        ArrayList<Bitmap> covers3 = new ArrayList<>();
        covers3.add(decodeFile("drawable://" + drawable.audi));
        ArrayList<Bitmap> covers4 = new ArrayList<>();
        covers4.add(decodeFile("drawable://" + drawable.skateboard));
        ArrayList<Bitmap> covers5 = new ArrayList<>();
        covers5.add(decodeFile("drawable://" + drawable.coffee));

        Item item = new Item(null, covers, "vintage_bicycle", 10, 20, "Desc");
        itemsList.add(item);
        item = new Item(null, covers1, "one_bed_house", 10, 20, "Desc");
        itemsList.add(item);
        item = new Item(null, covers2, "penthouse", 10, 20, "Desc");
        itemsList.add(item);
        item = new Item(null, covers3, "audi", 10, 20, "Desc");
        itemsList.add(item);
        item = new Item(null, covers4, "skateboard", 10, 20, "Desc");
        itemsList.add(item);
        item = new Item(null, covers5, "coffee", 10, 20, "Desc");
        itemsList.add(item);

//        adapter.notifyDataSetChanged();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreateViewHolder() throws Exception {
    }

    @Test
    public void onBindViewHolder() throws Exception {
    }

    @Test
    public void getItemCount() throws Exception {
    }

    @Test
    public void setItemsList() throws Exception {
    }

    @Test
    public void getFilter() throws Exception {
    }

}