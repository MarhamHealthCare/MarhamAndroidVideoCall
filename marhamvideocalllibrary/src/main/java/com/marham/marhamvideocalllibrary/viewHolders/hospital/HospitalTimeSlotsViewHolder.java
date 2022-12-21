package com.marham.marhamvideocalllibrary.viewHolders.hospital;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.doctor.BookVideoConsultationActivity;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class HospitalTimeSlotsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final ConstraintLayout parentLayout;
    private final BodyText timingTextView;
    private AdapterViewItemClickedListener listener;

    public HospitalTimeSlotsViewHolder(@NonNull View itemView, AdapterViewItemClickedListener listener) {
        super(itemView);
        parentLayout = itemView.findViewById(R.id.parent_layout);
        timingTextView = itemView.findViewById(R.id.timing_slot_text_view);
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
            listener.onAdatviewItemClicked(getAdapterPosition(), BookVideoConsultationActivity.HOSPITAL_TIME);
        }
    }

    public BodyText getTimingTextView() {
        return timingTextView;
    }

    public ConstraintLayout getParentLayout() {
        return parentLayout;
    }

}
