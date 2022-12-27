package com.marham.marhamvideocalllibrary.adapters.appointments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.appointment.Appointment;
import com.marham.marhamvideocalllibrary.viewHolders.appointments.BaseAllVideoConsultationsViewHolder;
import com.marham.marhamvideocalllibrary.viewHolders.appointments.PreviousVideoConsultationsViewHolder;

import java.util.List;

public class PreviousVideoConsultationsAdapter extends BaseAllVideoConsultationsAdapter {

    public PreviousVideoConsultationsAdapter(Context context, AdapterViewItemClickedListener adapterViewItemClickedListener, List<Appointment> appointmentList, int adapterType) {
        super(context, adapterViewItemClickedListener, appointmentList, adapterType);
    }

    @NonNull
    @Override
    public PreviousVideoConsultationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_previous_appointment, parent, false);
        return new PreviousVideoConsultationsViewHolder(view, adpaterViewItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAllVideoConsultationsViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

    }

}
