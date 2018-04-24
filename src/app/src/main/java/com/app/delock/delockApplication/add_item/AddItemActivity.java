package com.app.delock.delockApplication.add_item;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.louvre.Louvre;
import com.andremion.louvre.home.GalleryActivity;
import com.app.delock.delockApplication.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.app.delock.delockApplication.R.*;

public class AddItemActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private static final int LOUVRE_REQUEST_CODE = 0;
    private List<Uri> mSelection;
    SliderLayout mSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_add_item);
        Toolbar toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);

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

        FloatingActionButton fab = findViewById(id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOUVRE_REQUEST_CODE && resultCode == RESULT_OK) {
            mSlider.removeAllSliders();
            mSelection = GalleryActivity.getSelection(data);
            Object[] selections = mSelection.toArray();
            for(Object uri : selections){
                Uri newUri = (Uri) uri;
                TextSliderView  textSliderView = new TextSliderView(this);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}
}

