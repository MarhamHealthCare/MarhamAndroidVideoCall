package com.marham.marhamvideocalllibrary.adapters.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.DoctorInfo;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.BaseDoctorViewHolder;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.DoctorDashboardViewHolder;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.DoctorListingFilterViewHolder;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.DoctorListingViewHolder;

import java.text.MessageFormat;
import java.util.List;

public class DoctorListingAdapter extends BaseDoctorsAdapter{

    public DoctorListingAdapter(Context context, List<DoctorInfo> doctorInfoList, AdapterViewItemClickedListener listener) {
        super(context, doctorInfoList, listener);
    }

    @NonNull
    @Override
    public BaseDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_dr_listing, viewGroup, false);
        return new DoctorListingViewHolder(view, listener, context);
    }

    @Override
    public void onBindViewHolder(BaseDoctorViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        DoctorListingViewHolder doctorListingFilterViewHolder = (DoctorListingViewHolder) holder;

        doctorListingFilterViewHolder.doctorFeeTextView.setText(MessageFormat.format("Rs. {0}", doctorInfoList.get(position).getDocFee()));
    }

}
