package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.app.delock.delockApplication.R;

import static com.app.delock.delockApplication.utils.GetBalanceUtils.*;
import static com.app.delock.delockApplication.utils.utils.round;

/**
 * Created by Marky on 18/04/2018.
 */

//GET ADDRESS BALANCE AND BLOCK NUMBER FROM WEB3J ASYNCHRONOUSLY
@SuppressLint("StaticFieldLeak")
public class AsyncGetBalanceTask extends AsyncTask<Void, String[], String[]> {
    private final View view;
    private String address;

    public AsyncGetBalanceTask(View view, Context mContext){
        this.view = view;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("prefs", 0);
        address = sharedPreferences.getString("accountAddress", "No address found");
    }

    @Override
    protected void onPreExecute() {
            /* this method will be running on UI thread */
        super.onPreExecute();
        TextView address_value = view.findViewById(R.id.address_value);
        address_value.setText(address);
    }
    @Override
    protected String[] doInBackground(Void... params) {
        //GET ETHER MARKET VALUE
        double latestEuroValue = getLatestEuroValue();
        //GET ACCOUNT ETHER BALANCE
        double ether = getAccountBalanceEther(address, 5);
        //CALCULATE EURO VALUE OF BALANCE
        double balanceInEuro = round((latestEuroValue * ether), 2);

        return new String[]{Double.toString(balanceInEuro), Double.toString(ether)};
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);

        TextView euro_balance = view.findViewById(R.id.euro_value);
        euro_balance.setText(result[0]);
        TextView ether_balance = view.findViewById(R.id.ether_value);
        ether_balance.setText(result[1]);
    }
}


