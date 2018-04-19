package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.app.delock.delockApplication.IPFSDaemon;
import com.app.delock.delockApplication.SplashActivity;

import static android.widget.Toast.makeText;

/**
 * Created by Marky on 18/04/2018.
 */

@SuppressLint("StaticFieldLeak")
public class AsyncDownloadTask extends AsyncTask<Void, Void, String[]>
{

    private final View view;
    private final SplashActivity splashActivity;
    private final IPFSDaemon daemon;

    public AsyncDownloadTask(View view, SplashActivity splashActivity, IPFSDaemon daemon){
        this.view = view;
        this.splashActivity = splashActivity;
        this.daemon = daemon;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
            /* this method will be running on UI thread */
        Toast toast = makeText(splashActivity, "Downloading...", Toast.LENGTH_LONG);
        toast.show();
    }
    @Override
    protected String[] doInBackground(Void... params) {
        //this method will be running on background thread so don't update UI frome here
        //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
        String result = daemon.download(splashActivity);
        return new String[]{result};
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
        //this method will be running on UI thread
        Toast toast = makeText(splashActivity, result[0], Toast.LENGTH_LONG);
        toast.show();

        SharedPreferences prefs = splashActivity.getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}

