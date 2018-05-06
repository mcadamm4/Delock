package com.app.delock.delockApplication.item;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.app.delock.delockApplication.Constants;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.smartcontract_wrappers.Rental;
import com.app.delock.delockApplication.smartcontract_wrappers.RentalDirectory;
import com.app.delock.delockApplication.unlock_item.UnlockActivity;
import com.app.delock.delockApplication.utils.AsyncUtil;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.app.delock.delockApplication.R.id.item_description;
import static com.app.delock.delockApplication.R.id.eth_price;
import static com.app.delock.delockApplication.R.id.eth_deposit;
import static com.app.delock.delockApplication.R.id.owner_address;
import static com.app.delock.delockApplication.utils.CurrencyUtils.getLatestEuroValue;
import static com.app.delock.delockApplication.utils.utils.round;
import static org.web3j.protocol.core.DefaultBlockParameterName.*;

/**
 * Created by Marky on 16/03/2018.
 */

public class ItemActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "ItemActivity"; //Help identify activity while debugging

    private TextView itemTitle, ownerAddressView, ethDeposit, eurDeposit, ethPrice, eurPrice, itemDescription;
    private ImageView ethDepositSymbol, ethPriceSymbol, eurDepositSymbol, eurPriceSymbol;
    private Button rentItemButton, returnItemButton;
    private LottieAnimationView availableLottie, notAvailableLottie;
    private ImageView convert_currency;
    private SliderLayout mSlider;
    private String userAddress;
    private Item item;
    private String currency = "ETH";

    RentalDirectory rentalDirectory;
    //CONTRACT DETAILS
    private Rental rental;
    private String listingOwner = "";
    private String currentRenter = "";
    private BigInteger deposit;
    private Boolean available = false;

    private double depositEuroValue;
    private double priceEuroValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.SHARED_PREFS, 0);
        userAddress = sharedPreferences.getString(Constants.ACCOUNT_ADDRESS_SHARED_PREF, "No address found");

        //INTENT
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("Item");
        mSlider = findViewById(R.id.slider);

        setAvailableStatus();
        setSliderImages(item);
        setItemDetailsViews();

        addMapFragment();
        // setMapLocationMarker();
        setRentButton();
        setReturnButton();
    }

    private void setAvailableStatus() {
        availableLottie = findViewById(R.id.available_lock_lottie);
        notAvailableLottie = findViewById(R.id.not_available_lock_lottie);
        AsyncUtil.execute(new AsyncGetDetailsFromContractTask());
    }

    private void setItemDetailsViews() {
        findItemViews();

        itemTitle.setText(item.title);
        ethDeposit.setText(String.valueOf(item.itemDeposit));
        ethPrice.setText(String.valueOf(item.itemPrice));
        itemDescription.setText(item.itemDescription);

        new Thread(() -> {
            double latestEuroValue = getLatestEuroValue();
            depositEuroValue = round((item.itemDeposit * latestEuroValue), 2);
            priceEuroValue = round((item.itemPrice * latestEuroValue), 2);
        }).start();

        convert_currency = findViewById(R.id.convert_currency);
        convert_currency.setOnClickListener(v -> {

            eurDeposit.setText(String.valueOf(depositEuroValue));
            eurPrice.setText(String.valueOf(priceEuroValue));

            //Convert currency and switch symbols
            if(currency.compareTo("EUR")==0){
                // Hide ether
                ethDeposit.setVisibility(View.INVISIBLE);
                ethPrice.setVisibility(View.INVISIBLE);
                ethDepositSymbol.setVisibility(View.INVISIBLE);
                ethPriceSymbol.setVisibility(View.INVISIBLE);
                // Show euro
                eurDeposit.setVisibility(View.VISIBLE);
                eurPrice.setVisibility(View.VISIBLE);
                eurDepositSymbol.setVisibility(View.VISIBLE);
                eurPriceSymbol.setVisibility(View.VISIBLE);

                currency="ETH";
            } else {
                // Hide euro
                eurDeposit.setVisibility(View.INVISIBLE);
                eurPrice.setVisibility(View.INVISIBLE);
                eurDepositSymbol.setVisibility(View.INVISIBLE);
                eurPriceSymbol.setVisibility(View.INVISIBLE);
                // Show ether
                ethDeposit.setVisibility(View.VISIBLE);
                ethPrice.setVisibility(View.VISIBLE);
                ethDepositSymbol.setVisibility(View.VISIBLE);
                ethPriceSymbol.setVisibility(View.VISIBLE);

                currency="EUR";
            }
        });
    }

    private void findItemViews() {
        itemTitle = findViewById(owner_address);
        ownerAddressView = findViewById(R.id.owner_address);
        ethDeposit = findViewById(eth_deposit);
        eurDeposit = findViewById(R.id.eur_deposit);
        ethPrice = findViewById(eth_price);
        eurPrice = findViewById(R.id.eur_price);
        itemDescription = findViewById(item_description);

        ethDepositSymbol = findViewById(R.id.eth_deposit_symbol);
        ethPriceSymbol = findViewById(R.id.eth_price_symbol);
        eurDepositSymbol = findViewById(R.id.eur_deposit_symbol);
        eurPriceSymbol = findViewById(R.id.eur_price_symbol);
    }

    // This method adds map fragment to the container.
    private void addMapFragment() {
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        mMapFragment.getMapAsync(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.map, mMapFragment)
                .commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getMinZoomLevel();
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
        mSlider.setDuration(8000);
    }

    private void setRentButton() {
        rentItemButton = findViewById(R.id.rentItemButton);
        rentItemButton.setOnClickListener(view -> {
            AsyncUtil.execute(new AsyncRentItemTask());

            Intent intent1 = new Intent(ItemActivity.this, UnlockActivity.class);
            startActivity(intent1);
        });
    }

    private void setReturnButton() {
        returnItemButton = findViewById(R.id.returnItemButton);
        returnItemButton.setOnClickListener(view -> {
            AsyncUtil.execute(new AsyncReturnItemTask());
//            Intent intent1 = new Intent(ItemActivity.this, LockActivity.class);
//            startActivity(intent1);
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class AsyncGetDetailsFromContractTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            getDetailsFromContract();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ownerAddressView.setText(listingOwner);

            // Does this user own this item
            boolean isOwner = (userAddress.compareTo(listingOwner)==0);
            // Did this owner disable this item
            boolean ownerDisabled = (userAddress.compareTo(currentRenter)==0);
            // Is renter but not owner
            boolean isRenter = (userAddress.compareTo(currentRenter)==0);

            if(available){
                availableLottie.setVisibility(VISIBLE);
                if(isOwner) {
                   availableLottie.setOnClickListener(
                           v -> disableItem());
                } else {
                    rentItemButton.setVisibility(VISIBLE);
                }
            } else { // Item is being rented
                notAvailableLottie.setVisibility(VISIBLE);
                // If owner has previously disabled item and is set as the currentRenter , owner can enable item
                if(isOwner && ownerDisabled){
                    notAvailableLottie.pauseAnimation();
                    notAvailableLottie.setOnClickListener(
                            v -> enableItem());
                } else if(isRenter) {
                    returnItemButton.setVisibility(VISIBLE);
                }
            }
        }
    }

    private void disableItem() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    rental.ownerSetAvailable(false).send();
                    availableLottie.setVisibility(INVISIBLE);
                    rental.event_OwnerSetAvailableEventObservable(EARLIEST, LATEST).subscribe(event -> {
                        AsyncUtil.execute(new AsyncGetDetailsFromContractTask());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        AsyncUtil.execute(new AsyncGetDetailsFromContractTask());
    }

    private void enableItem() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    rental.ownerSetAvailable(true).send();
                    notAvailableLottie.setVisibility(INVISIBLE);
                    rental.event_OwnerSetAvailableEventObservable(EARLIEST, LATEST).subscribe(event -> {
                       AsyncUtil.execute(new AsyncGetDetailsFromContractTask());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void getDetailsFromContract() {
        try {
            Web3j web3 = Web3jFactory.build(new HttpService(Constants.INFURA_URL));
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, 0);
            String walletPath = sharedPreferences.getString(Constants.WALLET_PATH_SHARED_PREF, "No address found");

            //SHOULD PROMPT USER FOR PASSWORD, REMOVE SHARED PREF FOR SECURITY
            String password = sharedPreferences.getString(Constants.PASSWORD_SHARED_PREF, "No address found");
            Credentials cred = WalletUtils.loadCredentials(password, walletPath);

            rentalDirectory = RentalDirectory.load(Constants.RENTAL_DIRECTORY_ADDRESS, web3, cred, Contract.GAS_PRICE, Contract.GAS_LIMIT);
            rental = Rental.load(item.address, web3, cred, Contract.GAS_PRICE, Contract.GAS_LIMIT);

            if(rental.isValid()) {
                listingOwner = rental.owner().send();
                currentRenter = rental.renter().send();
                available = rental.available().send();
                deposit = rental.depositAmount().send();
            }
            else
                throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class AsyncRentItemTask extends AsyncTask<Void, Void, Boolean[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Boolean[] doInBackground(Void... voids) {
            Boolean[] successfulRent = new Boolean[]{false};
            try {
                rental.rentItem(deposit);
                rental.event_rentItemEventObservable(EARLIEST, LATEST).subscribe(event -> {
                    successfulRent[0] = true;
                    rentalDirectory.triggerRentalEvent(listingOwner, rental.getContractAddress());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return successfulRent;
        }

        @Override
        protected void onPostExecute(Boolean[] successfulRent) {
            super.onPostExecute(successfulRent);
            if(!successfulRent[0]){
                Toast toast = Toast.makeText(ItemActivity.this, "Renting encountered a problem", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Intent intent = new Intent(ItemActivity.this, UnlockActivity.class);
                startActivity(intent);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class AsyncReturnItemTask extends AsyncTask<Void, Void, Boolean[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Boolean[] doInBackground(Void... voids) {
            Boolean[] successfulReturn = new Boolean[]{false};
            try {
                rental.calcTotalCostOfRental().send();
                rental.event_CostCalculationEventObservable(EARLIEST, LATEST).subscribe(costCalcEvent -> {
                    // Receive calculation event and return item with payment
                    final BigInteger cost = costCalcEvent._totalCostOfRental;
                    rental.returnItem(cost);
                    rental.event_returnItemEventObservable(EARLIEST, LATEST).subscribe(returnItemEvent -> {
                        successfulReturn[0] = true;
                        rentalDirectory.triggerReturnEvent(listingOwner, rental.getContractAddress());
                    });
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return successfulReturn;
        }

        @Override
        protected void onPostExecute(Boolean[] successfulReturn) {
            super.onPostExecute(successfulReturn);
            if(!successfulReturn[0]){
                Toast toast = Toast.makeText(ItemActivity.this, "Return event encountered a problem", Toast.LENGTH_LONG);
                toast.show();
            } else {
                // Start lock activity here **********************
                Intent intent = new Intent(ItemActivity.this, UnlockActivity.class);
                startActivity(intent);
            }
        }
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
