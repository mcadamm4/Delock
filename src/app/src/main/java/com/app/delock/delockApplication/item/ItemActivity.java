package com.app.delock.delockApplication.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.unlock_item.UnlockActivity;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;

/**
 * Created by Marky on 16/03/2018.
 */

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity"; //Help identify activity while debugging
    private SliderLayout mDemoSlider;
    public TextView itemTitle, itemCost;
    private Button nfcButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //INTENT
        Intent intent = getIntent();
        Item item= (Item) intent.getSerializableExtra("Item");
//        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        Glide.with(this).load(item.getThumbnail()).into((ImageView) findViewById(R.id.imageView));
        //ITEM DETAILS
        itemTitle = (TextView) findViewById(R.id.item_title);
        itemCost = (TextView) findViewById(R.id.item_cost);
        itemTitle.setText(String.valueOf(item.getName()));
        itemCost.setText(String.valueOf("€" + item.getItemCost()));

        nfcButton = findViewById(R.id.nfcButton);
        nfcButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(ItemActivity.this, UnlockActivity.class);
            startActivity(intent1);
        });
    }


}
