package com.app.delock.delockApplication.add_item;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.andremion.louvre.Louvre;
import com.andremion.louvre.home.GalleryActivity;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.smartcontract_wrappers.Rental;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static com.app.delock.delockApplication.R.id;
import static com.app.delock.delockApplication.R.layout;
import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

public class AddItemActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final int LOUVRE_REQUEST_CODE = 0;
    private List<Uri> mSelection;
    SliderLayout mSlider;
    Rental newRental;
    String token = "kv4a42NG93ZwJ9h0lZqK";
    String url = "https://ropsten.infura.io/";
    File path;
    Web3j web3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_add_item);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        path = this.getFilesDir();

        mSlider = findViewById(id.slider);
        //Set placeholder
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView.image(R.drawable.image_placeholder)
                .setScaleType(BaseSliderView.ScaleType.CenterCrop);
        mSlider.setDuration(6000);
        mSlider.addSlider(textSliderView);

        FloatingActionButton addImagesButton = findViewById(id.addImagesButton);
        addImagesButton.setOnClickListener(v -> Louvre.init(this)
                .setMaxSelection(3)
                .setMediaTypeFilter(Louvre.IMAGE_TYPE_JPEG, Louvre.IMAGE_TYPE_PNG)
                .setRequestCode(LOUVRE_REQUEST_CODE)
                .open());

        FloatingActionButton confirm = findViewById(R.id.Confirm);
        confirm.setOnClickListener(v -> new AsyncInfo().execute());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOUVRE_REQUEST_CODE && resultCode == RESULT_OK) {
            mSlider.removeAllSliders();
            mSelection = GalleryActivity.getSelection(data);
            Object[] selections = mSelection.toArray();
            for (Object uri : selections) {
                Uri newUri = (Uri) uri;
                TextSliderView textSliderView = new TextSliderView(this);
                textSliderView.image(new File(newUri.getPath()))
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener(this);
                mSlider.addSlider(textSliderView);
            }
            mSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
            mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSlider.setCustomAnimation(new DescriptionAnimation());
            mSlider.setDuration(6000);
            mSlider.addOnPageChangeListener(this);
        }
    }


    @Override
    protected void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }

    //SLIDER METHODS
    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncInfo extends AsyncTask<Void, Void, String[]> {
        private Web3j web3;
        String newRentalAddress = null;

        @Override
        protected void onPreExecute() {
            /* this method will be running on UI thread */
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(Void... params) {
            web3 = Web3jFactory.build(new HttpService(url + token));
            SharedPreferences sharedPreferences = AddItemActivity.this.getSharedPreferences("prefs", 0);
            String walletPath = sharedPreferences.getString("Wallet_Path", "No address found");
            String password = sharedPreferences.getString("Password", "No address found");
            try {
                Credentials cred = WalletUtils.loadCredentials(password, walletPath);
                String ipfshash = "QmWmyoMoctfbAaiEs2G46gpeUmhqFRDW6KWo64y5r581Vz";
                byte[] b = ipfshash.getBytes(Charset.forName("UTF-8"));
                newRental = Rental.deploy(web3, cred, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, null, 1, 1).send();
                newRentalAddress = newRental.getContractAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new String[]{newRentalAddress};
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            Toast toast = Toast.makeText(AddItemActivity.this, result[0], Toast.LENGTH_LONG);
            toast.show();
        }

    }
}


