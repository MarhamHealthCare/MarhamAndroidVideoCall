package com.marham.marhamvideocalllibrary.adapters.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.doctor.DoctorExperience;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.DoctorWorkExperienceViewHolder;

import java.util.List;

public class DoctorExperienceAdapter extends RecyclerView.Adapter<DoctorWorkExperienceViewHolder> {


    private Context context;
    private List<DoctorExperience> doctorExperiences;
    private AdapterViewItemClickedListener listener;

    public DoctorExperienceAdapter(Context context, List<DoctorExperience> doctorExperiences, AdapterViewItemClickedListener adpaterViewItemClickedListener) {
        this.context = context;
        this.doctorExperiences = doctorExperiences;
    }

    @NonNull
    @Override
    public DoctorWorkExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_doctor_experience, parent, false);
        return new DoctorWorkExperienceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorWorkExperienceViewHolder holder, int position) {

        if (doctorExperiences.get(position).getDesignation() != null && doctorExperiences.get(position).getInstitute() != null) {
            holder.doctorExperienceTextView.setText(doctorExperiences.get(position).getDesignation() + " " + doctorExperiences.get(position).getInstitute());
        } else if (doctorExperiences.get(position).getDesignation() != null) {
            holder.doctorExperienceTextView.setText(doctorExperiences.get(position).getDesignation());
        } else if (doctorExperiences.get(position).getInstitute() != null) {
            holder.doctorExperienceTextView.setText(doctorExperiences.get(position).getInstitute());
        }

    }

    @Override
    public int getItemCount() {
        return doctorExperiences.size();
    }

}
