package com.app.delock.delockApplication.add_item;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.andremion.louvre.Louvre;
import com.andremion.louvre.home.GalleryActivity;
import com.app.delock.delockApplication.R;
import com.app.delock.delockApplication.item.Item;
import com.app.delock.delockApplication.smartcontract_wrappers.Rental;
import com.app.delock.delockApplication.utils.AddItemFormUtils;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.thejuki.kformmaster.helper.FormBuildHelper;
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener;
import com.thejuki.kformmaster.model.BaseFormElement;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.web3j.protocol.Web3j;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.app.delock.delockApplication.R.id;
import static com.app.delock.delockApplication.R.id.slider;
import static com.app.delock.delockApplication.R.layout;
import static com.app.delock.delockApplication.utils.JsonUtils.collectDataIntoJSON;

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
    Item newItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_add_item);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);

        setupSlider();

        setupForm();

        //CONFIRM BUTTON
        FloatingActionButton confirm = findViewById(id.confirm);
        confirm.setOnClickListener(view -> {

            File[] imageFiles = gatherImageFiles();
            if(imageFiles==null){
                Toast toast = Toast.makeText(AddItemActivity.this, "No images attached", Toast.LENGTH_LONG);
                toast.show();
            } else {
                JSONObject jsonData = collectDataIntoJSON(formBuilder);
                collectDataIntoItem();

                Intent intent = new Intent(AddItemActivity.this, PublishItemActivity.class);
                intent.putExtra("ImageFiles", imageFiles);
                intent.putExtra("JsonData", jsonData.toString());
                intent.putExtra("NewItem", newItem);
                startActivity(intent);
            }
        });
    }

    private void collectDataIntoItem() {
            //Gather up JSON data
            final ArrayList<BaseFormElement<?>> elements = formBuilder.getElements();

            String title = elements.get(1).getValueAsString();
            double depositAmount = Double.valueOf(elements.get(2).getValueAsString());
            double price = Double.valueOf(elements.get(3).getValueAsString());
            String desc = elements.get(4).getValueAsString();

            newItem = new Item(title, depositAmount, price, desc);
    }

    private File[] gatherImageFiles() {
        File[] imagesMap = null;

        if (mSelection != null) {
            int i=0;
            Object[] selections = mSelection.toArray();
            imagesMap = new File[selections.length];

            for (Object uri : selections) {
                Uri newUri = (Uri) uri;
                File newFile = new File(newUri.getPath());
                imagesMap[i] = newFile;
                i++;
            }
        }
        return imagesMap;
    }

    private void setupForm() {
        formBuilder = new FormBuildHelper(this, this, findViewById(R.id.recyclerView), true);
        List<BaseFormElement<?>> elements = new ArrayList<>();
        elements = AddItemFormUtils.addEditTexts(this.getApplicationContext(), elements);

        formBuilder.addFormElements(elements);
    }

    @Override
    protected void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }

    //Slider
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

    //Form
    @Override
    public void onValueChanged(@NotNull BaseFormElement<?> formElement) {
    }

    //Slider
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
}


