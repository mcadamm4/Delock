package com.app.delock.delockApplication.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.app.delock.delockApplication.utils.utils.round;
import static org.web3j.utils.Convert.Unit.ETHER;

/**
 * Created by Marky on 30/04/2018.
 */

public class GetBalanceUtils {

    //RAW BALANCE FOR CALCULATIONS
    static double getAccountBalanceEther(String url, String token, String address) {
        Web3j web3 = Web3jFactory.build(new HttpService(url + token));
        EthGetBalance ethGetBalance = null;
        try {
            ethGetBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert ethGetBalance != null;
        BigInteger wei = ethGetBalance.getBalance();

        //CONVERT FROM WEI TO ETHER
        BigDecimal ether =  Convert.fromWei(wei.toString(), ETHER);
        return ether.doubleValue();
    }

    //ROUND BALANCE FOR PRESENTATION
    static double getAccountBalanceEther(String url, String token, String address, int precision) {
        Web3j web3 = Web3jFactory.build(new HttpService(url + token));
        EthGetBalance ethGetBalance = null;
        try {
            ethGetBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert ethGetBalance != null;
        BigInteger wei = ethGetBalance.getBalance();

        //CONVERT FROM WEI TO ETHER
        BigDecimal ether =  Convert.fromWei(wei.toString(), ETHER);
        return round(ether.doubleValue(), precision);
    }

    static double getLatestEuroValue() {
        double latestEuroValue = 0;
        try {
            URL obj = new URL("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=EUR");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //Read JSON response
            JSONObject myResponse = new JSONObject(response.toString());
            latestEuroValue = Math.floor(myResponse.getDouble("EUR"));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return latestEuroValue;
    }
}
