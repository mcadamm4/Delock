package com.app.delock.delockApplication.add_item;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.thejuki.kformmaster.helper.DateBuilder;
import com.thejuki.kformmaster.helper.DateTimeBuilder;
import com.thejuki.kformmaster.helper.DropDownBuilder;
import com.thejuki.kformmaster.helper.EmailEditTextBuilder;
import com.thejuki.kformmaster.helper.FormBuildHelper;
import com.thejuki.kformmaster.helper.HeaderBuilder;
import com.thejuki.kformmaster.helper.PhoneEditTextBuilder;
import com.thejuki.kformmaster.helper.TextViewBuilder;
import com.thejuki.kformmaster.helper.TimeBuilder;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.Charset;

import static com.app.delock.delockApplication.R.id;
import static com.app.delock.delockApplication.R.id.*;
import static com.app.delock.delockApplication.R.layout;
import static com.google.android.gms.common.util.CollectionUtils.mutableListOf;

import com.thejuki.kformmaster.helper.MultiLineEditTextBuilder;
import com.thejuki.kformmaster.helper.NumberEditTextBuilder;
import com.thejuki.kformmaster.helper.SingleLineEditTextBuilder;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddItemActivity extends AppCompatActivity implements OnFormElementValueChangedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final int LOUVRE_REQUEST_CODE = 0;
    private List<Uri> mSelection;
    SliderLayout mSlider;
    Rental newRental;
    String token = "kv4a42NG93ZwJ9h0lZqK";
    String url = "https://ropsten.infura.io/";
    File path;
    Web3j web3;
    private FormBuildHelper formBuilder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_add_item);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        path = this.getFilesDir();

        setupSlider();

        setupForm();
        
        //CONFIRM BUTTON
        FloatingActionButton confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(v -> new AsyncInfo().execute());
    }

    private void setupSlider() {
        mSlider = findViewById(slider);
        //IMAGE ADDER
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
    }


    //==============================================================

    private enum Tag {
        Email,
        Title,
        Location,
        Deposit,
        Price,
        Description,
        Date,
        Time,
        DateTime
    }

    private void setupForm() {
        formBuilder = new FormBuildHelper(this, this, findViewById(R.id.recyclerView), true);
        List<BaseFormElement<?>> elements = new ArrayList<>();

        elements = addEditTexts(elements);

        formBuilder.addFormElements(elements);
    }

    private List<BaseFormElement<?>> addEditTexts(List<BaseFormElement<?>> elements) {
        elements.add(new HeaderBuilder(getString(R.string.ItemDetails)).build());

        SingleLineEditTextBuilder title = new SingleLineEditTextBuilder((Tag.Title.ordinal()));
        title.setTitle(getString(R.string.Title));
        title.setHint(getString(R.string.EnterTitle));
        elements.add(title.build());

        EmailEditTextBuilder email = new EmailEditTextBuilder(Tag.Email.ordinal());
        email.setTitle(getString(R.string.email));
        email.setHint(getString(R.string.email_hint));
        elements.add(email.build());

        NumberEditTextBuilder deposit = new NumberEditTextBuilder(Tag.Deposit.ordinal());
        deposit.setTitle(getString(R.string.Deposit));
        deposit.setValue("0");
        elements.add(deposit.build());

        NumberEditTextBuilder price = new NumberEditTextBuilder(Tag.Price.ordinal());
        price.setTitle(getString(R.string.Price));
        price.setValue("0");
        elements.add(price.build());;

        MultiLineEditTextBuilder textArea = new MultiLineEditTextBuilder(Tag.Description.ordinal());
        textArea.setTitle(getString(R.string.Description));
        textArea.setValue("");
        textArea.setHint(getString(R.string.EnterDescription));
        elements.add(textArea.build());
        return elements;
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

    @Override
    public void onValueChanged(@NotNull BaseFormElement<?> formElement) {

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
                String ipfshash = "QmWmyoMoctfbAaiEs2G46gpeUmhqFRDW";
                byte[] b = ipfshash.getBytes(Charset.forName("UTF-8"));
                BigInteger deposit = BigInteger.ONE;
                BigInteger price = BigInteger.ONE;
                newRental = Rental.deploy(web3, cred, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, ipfshash, deposit, price)
                        .send();
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


