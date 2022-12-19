package com.marham.marhamvideocalllibrary.viewHolders.doctor;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class DoctorWorkExperienceViewHolder extends RecyclerView.ViewHolder {

    public BodyText doctorExperienceTextView;

    public DoctorWorkExperienceViewHolder(@NonNull View itemView) {
        super(itemView);
        doctorExperienceTextView = itemView.findViewById(R.id.doctor_experience_text_view);
    }

}
