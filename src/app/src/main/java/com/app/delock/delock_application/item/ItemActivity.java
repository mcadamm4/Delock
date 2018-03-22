package com.app.delock.delock_application.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.app.delock.delock_application.R;
import com.app.delock.delock_application.item.Item;
import com.bumptech.glide.Glide;

/**
 * Created by Marky on 16/03/2018.
 */

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity"; //Help identify activity while debugging


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Log.d(TAG, "onCreate: started.");
        Intent intent = getIntent();
        Item item= (Item) intent.getSerializableExtra("Item");

        Glide.with(this).load(item.getThumbnail()).into((ImageView) findViewById(R.id.image));

    }


}
