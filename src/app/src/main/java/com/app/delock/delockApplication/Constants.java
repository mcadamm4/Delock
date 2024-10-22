package com.app.delock.delockApplication;

import java.math.BigInteger;

/**
 * Created by Marky on 01/05/2018.
 */

public class Constants {

    public static final String SHARED_PREFS = "prefs";
    public static final String FIRST_START_SHARED_PREF = "firstStart";
    public static final String ACCOUNT_ADDRESS_SHARED_PREF = "accountAddress";
    public static final String PASSWORD_SHARED_PREF = "Password";
    public static final String WALLET_PATH_SHARED_PREF = "Wallet_Path";

    public static final String IPFS_JSON_FILE_NAME = "detailsJSON.txt";

    public static final String INFURA_URL = "https://ropsten.infura.io/kv4a42NG93ZwJ9h0lZqK";
    public static final String ETH_LATEST_VALUE_URL = "https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=EUR";

    // Using custom gas price for demo purposes as test net is slow
    public static final BigInteger CUSTOM_GAS_PRICE = BigInteger.valueOf(50000000000L);

    //DEPLOYED CONTRACT ADDRESSES
    public static final String RENTAL_DIRECTORY_ADDRESS = "0xDbba0201a541e35404F93839A4A1160bdD24bD54";
    public static final String USER_DIRECTORY_ADDRESS = "";
}
