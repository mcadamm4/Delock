package com.app.delock.delockApplication.add_item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.utils.AsyncCreateNewItemTask;
import com.app.delock.delockApplication.utils.AsyncUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class PublishItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_new_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            Intent intent = getIntent();
            File[] imageFiles = (File[]) intent.getSerializableExtra("ImageFiles");
            JSONObject jsonData = new JSONObject(getIntent().getStringExtra("JsonData"));
            Item newItem = (Item) intent.getSerializableExtra("NewItem");

            AsyncUtil.execute(new AsyncCreateNewItemTask(PublishItemActivity.this, imageFiles, jsonData, newItem));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button button = findViewById(R.id.lock_publish_button);
        button.setOnClickListener(view -> {
            Intent intent1 = new Intent(PublishItemActivity.this, LockNewItemActivity.class);
            startActivity(intent1);
        });
    }
}
