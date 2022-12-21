package com.marham.marhamvideocalllibrary.adapters.hospital;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.doctor.TimeSlotOfHospital;
import com.marham.marhamvideocalllibrary.viewHolders.hospital.HospitalTimeSlotsViewHolder;

import java.util.List;

public class HospitalTimeSlotsAdapter extends RecyclerView.Adapter<HospitalTimeSlotsViewHolder> {

    private final Context context;
    private List<TimeSlotOfHospital> timeSlotOfHospitals;
    private AdapterViewItemClickedListener listener;

    public HospitalTimeSlotsAdapter(Context context, List<TimeSlotOfHospital> timeSlotOfHospitals, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.timeSlotOfHospitals = timeSlotOfHospitals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HospitalTimeSlotsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view4 = LayoutInflater.from(context).inflate(R.layout.single_row_hospital_time_slot, viewGroup, false);
        return new HospitalTimeSlotsViewHolder(view4, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final HospitalTimeSlotsViewHolder timeSlotsViewHolder, final int position) {
        handleOnBindStateViewsWhenAreNonVariants(timeSlotsViewHolder, position);
    }

    private void handleOnBindStateViewsWhenAreNonVariants(@NonNull HospitalTimeSlotsViewHolder timeSlotsViewHolder, int position) {
        if (timeSlotOfHospitals != null && !timeSlotOfHospitals.isEmpty()) {
            timeSlotsViewHolder.getTimingTextView().setText(timeSlotOfHospitals.get(position).getSlot());
        }

        if (timeSlotOfHospitals.get(position).isSelected()) {
            MarhamUtils.getInstance().setBackground(context,timeSlotsViewHolder.getParentLayout(),R.drawable.rounder_corner_light_blue_with_blue_stroke);
            timeSlotsViewHolder.getTimingTextView().setTypeface(timeSlotsViewHolder.getTimingTextView().getTypeface(), Typeface.BOLD);
        } else {
            MarhamUtils.getInstance().setBackground(context,timeSlotsViewHolder.getParentLayout(),R.drawable.rounder_corner_light_blue);
            timeSlotsViewHolder.getTimingTextView().setTypeface(timeSlotsViewHolder.getTimingTextView().getTypeface(), Typeface.NORMAL);

        }

    }

    @Override
    public int getItemCount() {
        return timeSlotOfHospitals.size();
    }

}
