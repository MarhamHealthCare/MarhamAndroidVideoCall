package com.marham.marhamvideocalllibrary.viewHolders.doctor;

import android.view.View;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.adapters.doctor.BaseDoctorsAdapter;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class DoctorDashboardViewHolder extends BaseDoctorViewHolder{
    public DoctorDashboardViewHolder(@NonNull View view, AdapterViewItemClickedListener listener) {
        super(view, listener);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == parentLayout.getId()) {
            listener.onAdatviewItemClicked(getAdapterPosition(), BaseDoctorsAdapter.DOCTOR_DASBHBOARD_RECYCLER_VIEW);
        }
    }

}
