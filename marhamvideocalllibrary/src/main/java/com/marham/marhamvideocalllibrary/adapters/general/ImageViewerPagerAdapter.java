package com.marham.marhamvideocalllibrary.adapters.general;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.customviews.TouchImageView;
import com.marham.marhamvideocalllibrary.utils.AppConstants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageViewerPagerAdapter extends PagerAdapter {

    private AppCompatActivity activity;
    private List<String> imageURL;
    private String intentType;

    public ImageViewerPagerAdapter(AppCompatActivity activity, List<String> imageURL) {
        this.activity = activity;
        this.imageURL = imageURL;
    }

    public ImageViewerPagerAdapter(AppCompatActivity activity, List<String> imageURL,String intentType) {
        this.activity = activity;
        this.imageURL = imageURL;
        this.intentType =intentType;
    }


    @Override
    public int getCount() {
        if(imageURL!=null){
            return imageURL.size();
        }else{
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView imgDisplay;
        MyImageView btnClose;
        final ProgressBar progressBar;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.single_row_image_viewer, container, false);

        imgDisplay = viewLayout.findViewById(R.id.imgDisplay);
        btnClose = viewLayout.findViewById(R.id.btnClose);
        progressBar = viewLayout.findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        if (imageURL.get(position) != null) {
            if (!imageURL.get(position).isEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    setImageOnAndroidGreaterThanAndroidTen(position, imgDisplay, progressBar);
                } else {
                    setImageOnAndroidLessThanVersionTen(position, imgDisplay, progressBar);
                }
            } else {
                progressBar.setVisibility(View.GONE);
                Picasso.get().load(R.drawable.m_doctor_placholder).fit().centerInside().into(imgDisplay);
            }
        } else {
            progressBar.setVisibility(View.GONE);
            Picasso.get().load(R.drawable.m_doctor_placholder).fit().centerInside().into(imgDisplay);
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        container.addView(viewLayout);

        return viewLayout;
    }

    private void setImageOnAndroidGreaterThanAndroidTen(int position, TouchImageView imgDisplay, ProgressBar progressBar) {
        if(intentType!=null) {
            if (intentType.equals(AppConstants.PERMISSIONS.CAMERA_REQUEST)) {
                Picasso.get().load(imageURL.get(position)).fit().centerCrop().into(imgDisplay, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } else {
                Picasso.get().load(imageURL.get(position)).into(imgDisplay, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }else{
            Picasso.get().load(imageURL.get(position)).into(imgDisplay, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    private void setImageOnAndroidLessThanVersionTen(int position, TouchImageView imgDisplay, ProgressBar progressBar) {
        Picasso.get().load(imageURL.get(position)).into(imgDisplay, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

}
