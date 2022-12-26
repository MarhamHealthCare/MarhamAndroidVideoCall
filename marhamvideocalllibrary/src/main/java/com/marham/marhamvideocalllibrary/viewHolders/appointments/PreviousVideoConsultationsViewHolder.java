package com.marham.marhamvideocalllibrary.viewHolders.appointments;

import android.view.View;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class PreviousVideoConsultationsViewHolder extends BaseAllVideoConsultationsViewHolder {
    public MyButton prescriptionButton;

    public PreviousVideoConsultationsViewHolder(View view, AdapterViewItemClickedListener adapterViewItemClickedListener) {
        super(view, adapterViewItemClickedListener);
        prescriptionButton = view.findViewById(R.id.view_prescription_button);
    }
}
