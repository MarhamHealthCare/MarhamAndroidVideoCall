package com.marham.marhamvideocalllibrary.viewHolders.appointments;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseAllVideoConsultationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ConstraintLayout parentLayout;
    public CircleImageView doctorPictureImageView;
    public BodyText doctorNameTextView;
    public BodyText doctorSpecialityTextView;
    public BodyText patientNameTextView;
    public BodyText dateTextView;
    public BodyText timeTextView;
    public BodyText appointmentStatusTextView;

    private AdapterViewItemClickedListener adapterViewItemClickedListener;

    public BaseAllVideoConsultationsViewHolder(View view, AdapterViewItemClickedListener adapterViewItemClickedListener) {
        super(view);

        parentLayout = view.findViewById(R.id.parent_layout);
        doctorPictureImageView = view.findViewById(R.id.doctor_picture_image_view);
        doctorNameTextView = view.findViewById(R.id.doctor_name_text_view);
        doctorSpecialityTextView = view.findViewById(R.id.doctor_speciality_text_view);
        patientNameTextView = view.findViewById(R.id.patient_name_text_view);
        dateTextView = view.findViewById(R.id.date_text_view);
        timeTextView = view.findViewById(R.id.time_text_view);
        appointmentStatusTextView = view.findViewById(R.id.appointment_status_text_view);

        setListener(parentLayout);

        this.adapterViewItemClickedListener = adapterViewItemClickedListener;

    }

    private void setListener(ConstraintLayout parentLayout) {
        parentLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.parent_layout) {
            adapterViewItemClickedListener.onAdatviewItemClicked(getAdapterPosition());
        }
    }

}
