package com.marham.marhamvideocalllibrary.viewHolders.patientrecord;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;

public class WrittenPrescriptionViewHolder extends RecyclerView.ViewHolder{

    public MyImageView writtenPrescription;
    public ConstraintLayout parentLayout;

    public WrittenPrescriptionViewHolder(@NonNull View itemView) {
        super(itemView);
        writtenPrescription = itemView.findViewById(R.id.written_prescription);
        parentLayout = itemView.findViewById(R.id.parent_layout);
    }

}
