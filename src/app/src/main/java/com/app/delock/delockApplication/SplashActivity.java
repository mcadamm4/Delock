package com.app.delock.delockApplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.delock.delockApplication.browse.BrowseActivity;
import com.app.delock.delockApplication.utils.AsyncDownloadTask;
import com.app.delock.delockApplication.utils.AsyncGenerateWalletTask;
import com.app.delock.delockApplication.utils.AsyncUtil;

import org.ligi.kaxt.ContextExtensionsKt;

import java.io.File;

import io.ipfs.kotlin.IPFS;
import io.ipfs.kotlin.model.VersionInfo;

public class SplashActivity extends AppCompatActivity {
    public IPFS ipfs = new IPFS();
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        path = this.getFilesDir();

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, 0);
        boolean firstStart = sharedPreferences.getBoolean(Constants.FIRST_START_SHARED_PREF, true);

        if(firstStart) {

            AsyncUtil.execute(new AsyncDownloadTask(null, this));

            TextView enterPasswordBox = findViewById(R.id.password_input1);
            TextView confirmPasswordBox = findViewById(R.id.password_input2);
            Button generateButton = findViewById((R.id.generateButton));

            generateButton.setOnClickListener((View it) -> {
                String input1 = enterPasswordBox.getText().toString();
                String input2 = confirmPasswordBox.getText().toString();
                comparePasswords(input1, input2);
            });
        } else {
            //Hide items and start daemon
            findViewById(R.id.password_input1).setVisibility(View.INVISIBLE);
            findViewById(R.id.password_input2).setVisibility(View.INVISIBLE);
            findViewById(R.id.generateButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView2).setVisibility(View.INVISIBLE);
            findViewById(R.id.animation_view).setVisibility(View.VISIBLE);
            startIPFSDaemon();
        }
    }

    private void comparePasswords(String input1, String input2) {
        if(input1.equals(input2)){
            //Take password and make new wallet
            AsyncUtil.execute(new AsyncGenerateWalletTask(this, input1, path));
            startIPFSDaemon();
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        SplashActivity.this.stopService(new Intent(SplashActivity.this, IPFSDaemonService.class));

        // Delete all cached listing items
        File Listings_Folder = new File(SplashActivity.this.getCacheDir(), "Listings_Folder");
        final File[] listings = Listings_Folder.listFiles();
        for(File listing : listings) {
            listing.delete();
        }

/*
        Delete all cached Rental wrapper files
        Rental wrapper caching not working

        File Rental_Wrappers_Folder = new File(SplashActivity.this.getCacheDir(), "Rental_Wrappers_Folder");
        final File[] rentals = Rental_Wrappers_Folder.listFiles();
        for(File rental : rentals) {
        rental.delete();
        }
*/
    }

    public void startIPFSDaemon() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SplashActivity.this.startService(new Intent(SplashActivity.this, IPFSDaemonService.class));
        State.INSTANCE.setDaemonRunning(true);
        (new Thread(() -> {
            VersionInfo version = null;

            while(version == null) try {
                version = ipfs.getInfo().version();
            } catch (Exception ignored) {
            }
            SplashActivity.this.runOnUiThread(() -> ContextExtensionsKt.startActivityFromClass(SplashActivity.this, BrowseActivity.class));
        })).start();
    }
}
