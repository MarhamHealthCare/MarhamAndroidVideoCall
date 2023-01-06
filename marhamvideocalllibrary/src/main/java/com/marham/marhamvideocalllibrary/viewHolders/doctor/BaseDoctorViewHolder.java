package com.marham.marhamvideocalllibrary.viewHolders.doctor;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseDoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CardView parentLayout;
    public CircleImageView doctorPictureImageView;
    public BodyText doctorNameTextView;
    public BodyText doctorSpecialityTextView;
    public BodyText doctorExperienceTextView;

    public BodyText doctorReviewsTextView;
    public MyImageView doctorRatingsStar;

    protected AdapterViewItemClickedListener listener;

    public BaseDoctorViewHolder(@NonNull View view, AdapterViewItemClickedListener listener) {
        super(view);
        initGui(view);
        initVariables(listener);
        setListeners();
    }

    @Override
    public void onClick(View view) {

    }

    private void initGui(View view) {
        parentLayout = view.findViewById(R.id.parent_layout);
        doctorPictureImageView = view.findViewById(R.id.doctor_picture_image_view);
        doctorNameTextView = view.findViewById(R.id.doctor_name_text_view);
        doctorSpecialityTextView = view.findViewById(R.id.doctor_speciality_text_view);
        doctorExperienceTextView = view.findViewById(R.id.doctor_experience_text_view);
        doctorReviewsTextView = view.findViewById(R.id.doctor_reviews_text_view);
        doctorRatingsStar = view.findViewById(R.id.doctor_ratings_stars);
    }

    private void initVariables(AdapterViewItemClickedListener listener) {
        this.listener = listener;
    }

    private void setListeners() {
        parentLayout.setOnClickListener(this);
    }

}
