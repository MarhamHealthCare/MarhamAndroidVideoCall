package com.marham.marhamvideocalllibrary.activities.general;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.adapters.general.ImageViewerPagerAdapter;

import java.util.List;

public class ImageViewerActivity extends BaseActivity {

    private ViewPager viewPager;

    private int position;
    private List<String> imageURL;
    private String intentType;

    public static final String POSITION = "position";
    public static final String IMAGE_URL = "imageURL";
    public static final String INTENT_TYPE = "intentType";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        initializeViews();
        receiveIntent();
        setViewPager();
    }

    protected void initializeViews(){
        viewPager = findViewById(R.id.view_pager);
    }

    private void receiveIntent() {
        position = getIntent().getIntExtra(ImageViewerActivity.POSITION, 0);
        imageURL = getIntent().getStringArrayListExtra(ImageViewerActivity.IMAGE_URL);
        if (getIntent().getStringExtra(ImageViewerActivity.INTENT_TYPE) != null) {
            intentType = getIntent().getStringExtra(ImageViewerActivity.INTENT_TYPE);
        }
    }

    private void setViewPager() {
        viewPager.setAdapter(new ImageViewerPagerAdapter(this, imageURL,intentType));
        viewPager.setCurrentItem(position);
    }


}