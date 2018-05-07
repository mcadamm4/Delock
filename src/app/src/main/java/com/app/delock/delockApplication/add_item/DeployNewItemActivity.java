package com.app.delock.delockApplication.add_item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.utils.AsyncCreateNewItemTask;
import com.app.delock.delockApplication.utils.AsyncUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.app.delock.delockApplication.R.id.lock_new_item_button;
import static com.app.delock.delockApplication.R.id.password_input;

public class DeployNewItemActivity extends AppCompatActivity {
    TextView enterPasswordBox;
    Button lockItemButton;
    LottieAnimationView deployingContraact, contractDeployed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_new_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            Intent intent = getIntent();
            File[] imageFiles = (File[]) intent.getSerializableExtra("ImageFiles");
            JSONObject jsonData = new JSONObject(getIntent().getStringExtra("JsonData"));
            Item newItem = (Item) intent.getSerializableExtra("NewItem");

            // Deploy contract
            AsyncUtil.execute(new AsyncCreateNewItemTask(DeployNewItemActivity.this, imageFiles, jsonData, newItem));

            // Generate hash password for Item
            enterPasswordBox = findViewById(password_input);
            lockItemButton = findViewById(lock_new_item_button);
            lockItemButton.setOnClickListener(v -> {
                String password = enterPasswordBox.getText().toString();
                generateHashedPassword(password);
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button button = findViewById(R.id.lock_new_item_button);
        button.setOnClickListener(view -> {
            Intent intent1 = new Intent(DeployNewItemActivity.this, LockNewItemActivity.class);
            startActivity(intent1);
        });
    }

    private void generateHashedPassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            byte[] bytes = md.digest();

            // Convert bytes[] from decimal format to hex
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
