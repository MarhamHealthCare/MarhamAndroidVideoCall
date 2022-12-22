package com.marham.marhamvideocalllibrary.adapters.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.doctor.DoctorInfo;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.BaseDoctorViewHolder;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.DoctorDashboardViewHolder;

import java.util.List;

public class DoctorDasboardAdapter extends BaseDoctorsAdapter{

    public DoctorDasboardAdapter(Context context, List<DoctorInfo> doctorInfoList, AdapterViewItemClickedListener listener) {
        super(context, doctorInfoList, listener);
    }

    @NonNull
    @Override
    public BaseDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_top_doctor, viewGroup, false);
        return new DoctorDashboardViewHolder(view, listener);
    }



}
