package com.marham.marhamvideocalllibrary.viewHolders.appointments;

import android.view.View;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.adapters.appointments.BaseAllVideoConsultationsAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class UpcomingVideoConsultationsViewHolder extends BaseAllVideoConsultationsViewHolder {

    public BodyText doctorDegreesTextView;

    public UpcomingVideoConsultationsViewHolder(View view, AdapterViewItemClickedListener adapterViewItemClickedListener) {
        super(view, adapterViewItemClickedListener);
        doctorDegreesTextView = view.findViewById(R.id.doctor_degrees_text_view);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (viewId == R.id.parent_layout) {
            adapterViewItemClickedListener.onAdatviewItemClicked(getAdapterPosition(), BaseAllVideoConsultationsAdapter.UPCOMING_APPOINTMENTS);
        }
    }

}
