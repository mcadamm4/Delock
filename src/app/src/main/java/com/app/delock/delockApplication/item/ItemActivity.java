package com.app.delock.delockApplication.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.unlock_item.UnlockActivity;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;

import java.util.Calendar;

/**
 * Created by Marky on 16/03/2018.
 */

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity"; //Help identify activity while debugging
    private SliderLayout mSlider;
    public TextView itemTitle, itemCost;
    private Button nfcButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //INTENT
        Intent intent = getIntent();
        Item item= (Item) intent.getSerializableExtra("Item");
        mSlider = (SliderLayout)findViewById(R.id.slider);

        //ITEM DETAILS
        itemTitle = (TextView) findViewById(R.id.item_title);
        itemCost = (TextView) findViewById(R.id.item_deposit);

        CalendarView clndr = findViewById(R.id.calendarView2);
        String date = "24/4/2018";
        String parts[] = date.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();
        clndr.setDate (milliTime, true, true);

        nfcButton = findViewById(R.id.nfcButton);
        nfcButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(ItemActivity.this, UnlockActivity.class);
            startActivity(intent1);
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
