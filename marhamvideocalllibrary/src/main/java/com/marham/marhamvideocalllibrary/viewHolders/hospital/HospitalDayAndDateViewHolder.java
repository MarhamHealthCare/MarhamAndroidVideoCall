package com.marham.marhamvideocalllibrary.viewHolders.hospital;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.doctor.BookVideoConsultationActivity;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class HospitalDayAndDateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ConstraintLayout parentLayout;
    public BodyText appointmentDay;
    public BodyText appointmentDateTextView;

    private AdapterViewItemClickedListener listener;

    public HospitalDayAndDateViewHolder(@NonNull View itemView, AdapterViewItemClickedListener listener) {
        super(itemView);
        parentLayout = itemView.findViewById(R.id.parent_layout);
        appointmentDay = itemView.findViewById(R.id.appointment_day);
        appointmentDateTextView = itemView.findViewById(R.id.appointment_date_text_view);
        setListener(parentLayout,listener);

    }

    private void setListener(ConstraintLayout parentLayout,AdapterViewItemClickedListener listener){
        parentLayout.setOnClickListener(this);
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(R.id.parent_layout == id){
            listener.onAdatviewItemClicked(getAdapterPosition(), BookVideoConsultationActivity.HOSPITAL_DAY_AND_DATE_ADAPTER);
        }
    }
}
