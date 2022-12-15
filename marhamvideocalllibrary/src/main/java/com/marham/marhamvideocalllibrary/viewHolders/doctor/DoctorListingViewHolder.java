package com.marham.marhamvideocalllibrary.viewHolders.doctor;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class DoctorListingViewHolder extends BaseDoctorViewHolder{

    public BodyText doctorFeeTextView;

    public DoctorListingViewHolder(@NonNull View view, AdapterViewItemClickedListener listener, Context context) {
        super(view, listener, context);
        doctorFeeTextView = view.findViewById(R.id.doctor_fee_text_view);
    }



}
