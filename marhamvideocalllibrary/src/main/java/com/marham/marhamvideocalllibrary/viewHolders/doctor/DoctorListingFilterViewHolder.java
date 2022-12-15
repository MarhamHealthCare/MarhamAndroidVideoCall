package com.marham.marhamvideocalllibrary.viewHolders.doctor;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.doctor.DoctorListingActivity;
import com.marham.marhamvideocalllibrary.adapters.doctor.BaseDoctorsAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class DoctorListingFilterViewHolder extends BaseDoctorViewHolder {

    public ConstraintLayout parentLayout;
    public BodyText filterTextView;
    protected AdapterViewItemClickedListener listener;

    public DoctorListingFilterViewHolder(@NonNull View view, AdapterViewItemClickedListener listener) {
        super(view,listener);
        this.parentLayout = view.findViewById(R.id.parent_layout);
        this.filterTextView = view.findViewById(R.id.doctor_listing_filter_text_view);
        this.listener = listener;
        setListeners();
    }

    private void setListeners(){
        parentLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == parentLayout.getId()) {
            listener.onAdatviewItemClicked(getAdapterPosition(), BaseDoctorsAdapter.DOCTOR_LISTING_RECYCLER_VIEW);
        }
    }
}
