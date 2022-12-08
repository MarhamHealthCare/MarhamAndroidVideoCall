package com.marham.marhamvideocalllibrary.viewHolders;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.MarhamDashboardActivity;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardDoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CardView parentLayout;
    public CircleImageView doctorPictureImageView;
    public BodyText doctorNameTextView;
    public BodyText doctorDegreesTextView;
    public BodyText doctorExperienceTextView;

    public BodyText doctorReviewsTextView;
    public MyImageView doctorRatingsStar;


    private AdapterViewItemClickedListener listener;
    private Context context;

    public DashboardDoctorViewHolder(@NonNull View view, AdapterViewItemClickedListener listener, Context context) {
        super(view);
        initGui(view);
        initVariables(listener, context);
        setListeners();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == parentLayout.getId()) {
            listener.onAdatviewItemClicked(getAdapterPosition(), MarhamDashboardActivity.DASHBOARD_DOCTORS_RECYCLER_VIEW);
        }
    }

    private void initGui(View view) {
        parentLayout = view.findViewById(R.id.parent_layout);
        doctorPictureImageView = view.findViewById(R.id.doctor_picture_image_view);
        doctorNameTextView = view.findViewById(R.id.doctor_name_text_view);
        doctorDegreesTextView = view.findViewById(R.id.doctor_degrees_text_view);
        doctorExperienceTextView = view.findViewById(R.id.doctor_experience_text_view);
        doctorReviewsTextView = view.findViewById(R.id.doctor_reviews_text_view);
        doctorRatingsStar = view.findViewById(R.id.doctor_ratings_stars);
    }

    private void initVariables(AdapterViewItemClickedListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    private void setListeners() {
        parentLayout.setOnClickListener(this);
    }

}
