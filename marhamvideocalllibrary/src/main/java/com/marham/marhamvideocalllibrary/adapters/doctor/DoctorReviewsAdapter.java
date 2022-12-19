package com.marham.marhamvideocalllibrary.adapters.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.model.review.Reviews;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.DoctorReviewViewHolder;

import java.util.List;

public class DoctorReviewsAdapter extends RecyclerView.Adapter<DoctorReviewViewHolder> {

    private List<Reviews> reviewAboutDoctorList;
    private Context context;

    public DoctorReviewsAdapter(Context context, List<Reviews> reviewAboutDoctorList) {
        this.context = context;
        this.reviewAboutDoctorList = reviewAboutDoctorList;
    }

    @NonNull
    @Override
    public DoctorReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_doctor_review, parent, false);
        return new DoctorReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorReviewViewHolder holder, int position) {

        if (reviewAboutDoctorList.get(position).getExperience() != null) {
            holder.doctorReviewsTextView.setText(reviewAboutDoctorList.get(position).getExperience());
        }
        if (reviewAboutDoctorList.get(position).getReviewerName() != null) {
            holder.patientNameTextView.setText(reviewAboutDoctorList.get(position).getReviewerName());
        }

    }

    @Override
    public int getItemCount() {
        return reviewAboutDoctorList.size();
    }


}
