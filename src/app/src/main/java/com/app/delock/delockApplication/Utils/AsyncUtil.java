package com.app.delock.delockApplication.Utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by Marky on 19/04/2018.
 */

public class AsyncUtil {

    @SafeVarargs
    @SuppressLint("ObsoleteSdkInt")
    public static <P, T extends AsyncTask<P,?, ?>> void execute(T task, P... params){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        else
            task.execute(params);
    }
}
