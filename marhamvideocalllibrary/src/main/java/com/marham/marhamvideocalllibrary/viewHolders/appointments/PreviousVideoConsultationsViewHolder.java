package com.marham.marhamvideocalllibrary.viewHolders.appointments;

import android.view.View;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.adapters.appointments.BaseAllVideoConsultationsAdapter;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class PreviousVideoConsultationsViewHolder extends BaseAllVideoConsultationsViewHolder {
    public MyButton prescriptionButton;

    public PreviousVideoConsultationsViewHolder(View view, AdapterViewItemClickedListener adapterViewItemClickedListener) {
        super(view, adapterViewItemClickedListener);
        prescriptionButton = view.findViewById(R.id.view_prescription_button);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (viewId == R.id.parent_layout) {
            adapterViewItemClickedListener.onAdatviewItemClicked(getAdapterPosition(), BaseAllVideoConsultationsAdapter.PREVIOUS_APPOINTMENTS);
        }
    }

}
