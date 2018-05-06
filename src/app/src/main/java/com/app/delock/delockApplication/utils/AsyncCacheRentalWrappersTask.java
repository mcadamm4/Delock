package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

import com.app.delock.delockApplication.smartcontract_wrappers.Rental;

import java.util.ArrayList;

/**
 * Created by Marky on 04/05/2018.
 */

// IGNORE THIS TASK - DOES NOT WORK AT THE MOMENT

public class AsyncCacheRentalWrappersTask extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private Activity mContext;
    private ArrayList<Rental> rentalList;

    AsyncCacheRentalWrappersTask(Activity _mContext, ArrayList<Rental> rentalList) {
        this.mContext = _mContext;
        this.rentalList = rentalList;
    }

    @Override
    protected Void doInBackground(Void... voids) {
//        cacheRentalWrappers(mContext, rentalList);
        return null;
    }
}