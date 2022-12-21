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
import com.marham.marhamvideocalllibrary.model.hospital.Days;
import com.marham.marhamvideocalllibrary.viewHolders.hospital.HospitalDayAndDateViewHolder;

import java.text.MessageFormat;
import java.util.List;

public class HospitalDayAndDateAdapter extends RecyclerView.Adapter<HospitalDayAndDateViewHolder>{

    private Context context;
    private final List<Days> daysList;
    private AdapterViewItemClickedListener listener;

    public HospitalDayAndDateAdapter(Context context, List<Days> daysList, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.daysList = daysList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HospitalDayAndDateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_hospital_day_and_date, viewGroup, false);
        return new HospitalDayAndDateViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final HospitalDayAndDateViewHolder appointmentDateAndTimeViewHolder, final int position) {
        appointmentDateAndTimeViewHolder.appointmentDay.setText(daysList.get(position).getDay());
        appointmentDateAndTimeViewHolder.appointmentDateTextView.setText(MessageFormat.format("{0} {1}", daysList.get(position).getMonth(), daysList.get(position).getDayOfMonth().toUpperCase()));

        if (daysList.get(position).isSelected()) {
            MarhamUtils.getInstance().setBackground(context,appointmentDateAndTimeViewHolder.parentLayout,R.drawable.rounder_corner_light_blue_with_blue_stroke);
            appointmentDateAndTimeViewHolder.appointmentDay.setTypeface(appointmentDateAndTimeViewHolder.appointmentDay.getTypeface(), Typeface.BOLD);
            appointmentDateAndTimeViewHolder.appointmentDateTextView.setTypeface(appointmentDateAndTimeViewHolder.appointmentDateTextView.getTypeface(), Typeface.BOLD);
        } else {
            MarhamUtils.getInstance().setBackground(context,appointmentDateAndTimeViewHolder.parentLayout,R.drawable.rounder_corner_light_blue);
            appointmentDateAndTimeViewHolder.appointmentDay.setTypeface(appointmentDateAndTimeViewHolder.appointmentDay.getTypeface(), Typeface.NORMAL);
            appointmentDateAndTimeViewHolder.appointmentDateTextView.setTypeface(appointmentDateAndTimeViewHolder.appointmentDateTextView.getTypeface(), Typeface.NORMAL);
        }

    }

    @Override
    public int getItemCount() {
        if (daysList == null)
            return 0;
        return daysList.size();
    }

}
