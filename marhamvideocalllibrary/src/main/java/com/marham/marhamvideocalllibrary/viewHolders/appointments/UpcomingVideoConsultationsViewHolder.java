package com.marham.marhamvideocalllibrary.viewHolders.appointments;

import android.view.View;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class UpcomingVideoConsultationsViewHolder extends BaseAllVideoConsultationsViewHolder {

    public BodyText doctorDegreesTextView;

    public UpcomingVideoConsultationsViewHolder(View view, AdapterViewItemClickedListener adapterViewItemClickedListener) {
        super(view, adapterViewItemClickedListener);
        doctorDegreesTextView = view.findViewById(R.id.doctor_degrees_text_view);
    }

}
