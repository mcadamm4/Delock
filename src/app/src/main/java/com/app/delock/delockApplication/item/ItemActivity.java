package com.app.delock.delockApplication.item;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.smartcontract_wrappers.Rental;
import com.app.delock.delockApplication.utils.AsyncUtil;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;

import static com.app.delock.delockApplication.R.id.item_description;
import static com.app.delock.delockApplication.R.id.item_cost;
import static com.app.delock.delockApplication.R.id.item_deposit;
import static com.app.delock.delockApplication.R.id.item_title;

/**
 * Created by Marky on 16/03/2018.
 */

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity"; //Help identify activity while debugging
    private SliderLayout mSlider;
    private Item item;
    public TextView itemTitle, itemDeposit, itemCost, itemDescription;
    private Button nfcButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        //INTENT
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");
        mSlider = findViewById(R.id.slider);

        // Get cached rental wrapper - feature not currently possible
        // getCachedRentalWrapper(item);

        AsyncUtil.execute(new AsyncGetAvailability());
        setSliderImages(item);

        //ITEM DETAILS
        itemTitle = findViewById(item_title);
        itemDeposit = findViewById(item_deposit);
        itemCost = findViewById(item_cost);
        itemDescription = findViewById(item_description);

        itemTitle.setText(item.title);
        itemDeposit.setText(String.valueOf(item.itemDeposit));
        itemCost.setText(String.valueOf(item.itemPrice));
        itemDescription.setText(item.itemDescription);

        CalendarView clndr = findViewById(R.id.calendarView2);
        String date = "24/4/2018";
        String parts[] = date.split("/");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();
        clndr.setDate (milliTime, true, true);

//        nfcButton = findViewById(R.id.nfcButton);
//        nfcButton.setOnClickListener(view -> {
//            Intent intent1 = new Intent(ItemActivity.this, UnlockActivity.class);
//            startActivity(intent1);
//        });
    }

    public class AsyncGetAvailability extends AsyncTask<Void, Void, Boolean>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Set loader visibility
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            return getRentalWrapper();
        }


        @Override
        protected void onPostExecute(Boolean strings) {
            super.onPostExecute(strings);
            //Set loader visibility
        }
    }

    private boolean getRentalWrapper() {
        Boolean available = false;
        try {
            Web3j web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, 0);
            String walletPath = sharedPreferences.getString(Constants.WALLET_PATH_SHARED_PREF, "No address found");

            //SHOULD PROMPT USER FOR PASSWORD, REMOVE SHARED PREF FOR SECURITY
            String password = sharedPreferences.getString(Constants.PASSWORD_SHARED_PREF, "No address found");
            Credentials cred = WalletUtils.loadCredentials(password, walletPath);

            Rental rental = Rental.load(item.address, web3, cred, Contract.GAS_PRICE, Contract.GAS_LIMIT);
            if(rental.isValid())
                available = rental.available().send();
            else
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return available;
    }


    private void setSliderImages(Item item) {
        ArrayList<File> imageFiles = item.imageFiles;
        for (File image : imageFiles) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.image(image)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .description("Item images");
            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    Caching of wrappers not currently possible

//    private void getCachedRentalWrapper(Item item) {
//        FileInputStream fileInputStream;
//        ObjectInputStream objectInputStream;File[] cachedRentals = new File(getCacheDir(), "Rental_Wrappers_Folder").listFiles();
//        for(File rentalFile : cachedRentals){
//            if(rentalFile.getName().compareTo(item.address)==0){
//                try {
//                    fileInputStream = new FileInputStream(rentalFile);
//                    objectInputStream = new ObjectInputStream(fileInputStream);
//                    rental = (Rental) objectInputStream.readObject();
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
