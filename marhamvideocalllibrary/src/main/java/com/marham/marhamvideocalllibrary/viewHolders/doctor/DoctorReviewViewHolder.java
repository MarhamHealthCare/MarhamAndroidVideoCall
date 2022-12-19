package com.marham.marhamvideocalllibrary.viewHolders.doctor;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.model.review.Reviews;

import java.util.List;

public class DoctorReviewViewHolder extends RecyclerView.ViewHolder{

    public BodyText doctorReviewsHeadingTextView;
    public BodyText doctorReviewsTextView;
    public BodyText patientNameTextView;

    public DoctorReviewViewHolder(@NonNull View itemView){
        super(itemView);
        doctorReviewsHeadingTextView = itemView.findViewById(R.id.doctor_reviews_heading_text_view);
        doctorReviewsTextView = itemView.findViewById(R.id.doctor_reviews_text_view);
        patientNameTextView = itemView.findViewById(R.id.patient_name_text_view);

    }



}
