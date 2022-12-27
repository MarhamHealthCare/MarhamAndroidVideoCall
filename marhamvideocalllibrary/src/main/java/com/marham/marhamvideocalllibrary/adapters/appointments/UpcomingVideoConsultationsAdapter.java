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
import com.marham.marhamvideocalllibrary.viewHolders.appointments.UpcomingVideoConsultationsViewHolder;

import java.util.List;

public class UpcomingVideoConsultationsAdapter extends BaseAllVideoConsultationsAdapter {

    public UpcomingVideoConsultationsAdapter(Context context, AdapterViewItemClickedListener adapterViewItemClickedListener, List<Appointment> appointmentList, int adapterType) {
        super(context, adapterViewItemClickedListener, appointmentList, adapterType);
    }

    @NonNull
    @Override
    public UpcomingVideoConsultationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_upcoming_appointment, parent, false);
        return new UpcomingVideoConsultationsViewHolder(view, adpaterViewItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAllVideoConsultationsViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Appointment appointment = appointmentList.get(position);
        UpcomingVideoConsultationsViewHolder upcomingVideoConsultationsViewHolder = (UpcomingVideoConsultationsViewHolder) holder;
        upcomingVideoConsultationsViewHolder.doctorDegreesTextView.setText(appointment.getDocDegree());

    }

}
