package com.app.delock.delockApplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.app.delock.delockApplication.R;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.web3j.utils.Convert.Unit.ETHER;

/**
 * Created by Marky on 18/04/2018.
 */

//GET ADDRESS BALANCE AND BLOCK NUMBER FROM WEB3J ASYNCHRONOUSLY
@SuppressLint("StaticFieldLeak")
public class AsyncGetBalanceTask extends AsyncTask<Void, BigDecimal, BigDecimal> {
    private final View view;
    private String address;

    //INFURA URL
    private String url = "https://ropsten.infura.io/";
    private String token = "kv4a42NG93ZwJ9h0lZqK";

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
    protected BigDecimal doInBackground(Void... params) {
        //this method will be running on background thread so don't update UI frome here
        //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

        Web3j web3 = Web3jFactory.build(new HttpService(url + token));
        EthGetBalance ethGetBalance = null;
        try {
            ethGetBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert ethGetBalance != null;
        BigInteger wei = ethGetBalance.getBalance();
        return Convert.fromWei(wei.toString(), ETHER);
    }

    @Override
    protected void onPostExecute(BigDecimal result) {
        super.onPostExecute(result);
        //this method will be running on UI thread
        TextView ether_balance = view.findViewById(R.id.ether_value);
        ether_balance.setText(String.valueOf(result));
    }
}


