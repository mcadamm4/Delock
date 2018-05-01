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

import org.json.JSONObject;

import java.io.File;

public class PublishItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_new_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        File[] imageFiles = (File[]) intent.getSerializableExtra("ImageFiles");
        JSONObject jsonData = (JSONObject) intent.getSerializableExtra("JsonData");

        AsyncUtil.execute(new AsyncCreateNewItemTask(PublishItemActivity.this, imageFiles, jsonData));

        Button button = findViewById(R.id.lock_publish_button);
        button.setOnClickListener(view -> {
            Intent intent1 = new Intent(PublishItemActivity.this, LockNewItemActivity.class);
            startActivity(intent1);
        });
    }

//    JSONObject obj = new JSONObject();
//        try {
//        obj.put("Name", "crunchify.com");
//        obj.put("Author", "App Shah");
//
//        JSONArray company = new JSONArray();
//        company.put("Compnay: eBay");
//        company.put("Compnay: Paypal");
//        company.put("Compnay: Google");
//        obj.put("Company List", company);
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//
//    File path = this.getFilesDir();
//
//    // try-with-resources statement based on post comment below :)
//        try (FileWriter file = new FileWriter(path+"/itemDetails.json")) {
//        file.write(obj.toString());
//        System.out.println("Successfully Copied JSON Object to File...");
//        System.out.println("\nJSON Object: " + obj);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }

    //==================================

}
