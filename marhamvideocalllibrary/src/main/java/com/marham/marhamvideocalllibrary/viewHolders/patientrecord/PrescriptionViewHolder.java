package com.marham.marhamvideocalllibrary.viewHolders.patientrecord;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.patientrecord.PrescriptionActivity;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdpaterViewItemClickedListenerForPrescription;

import de.hdodenhof.circleimageview.CircleImageView;

public class PrescriptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ConstraintLayout parentLayout;
    public ImageButton backButton;
    public BodyText headingTextview;
    //    public RelativeLayout noRecordLayout;
//    public RelativeLayout layoutProgressBar;
    public BodyText doctorNameField;
    public BodyText followUpTime;
    public BodyText doctorSpeciality;
    public BodyText doctorDegree;
    public BodyText patientName;
    public View lineView1;
    public BodyText gender;
    public View lineView2;
    public BodyText age;

    public CircleImageView doctorImageView;

    public BodyText consultationDate;
    public BodyText testsField;
    public BodyText medicinesField;
    public BodyText instructionsField;
    public BodyText followUpField;

    public BodyText testsTextView;
    public BodyText medicinesTextView;
    public BodyText instructionsTextView;
    public BodyText followUpTextView;

    private RecyclerView writtenPrescriptionRecyclerview;
    private BodyText prescriptionsTextView;
    private AdpaterViewItemClickedListenerForPrescription adpaterViewItemClickedListenerForPrescription;

    public BodyText doctorNameField2;

    public ConstraintLayout sharePrecriptionViewsContainer;
    public ConstraintLayout savePrecriptionViewsContainer;

    public PrescriptionViewHolder(@NonNull View itemView, AdpaterViewItemClickedListenerForPrescription adpaterViewItemClickedListener) {
        super(itemView);
        initViews(itemView);
        this.adpaterViewItemClickedListenerForPrescription = adpaterViewItemClickedListener;
        setListener();

    }

    private void setListener() {
        parentLayout.setOnClickListener(this);
        sharePrecriptionViewsContainer.setOnClickListener(this);
        savePrecriptionViewsContainer.setOnClickListener(this);
    }

    private void initViews(View itemView) {
        doctorImageView = itemView.findViewById(R.id.doctor_image_view);
        parentLayout = itemView.findViewById(R.id.parent_layout);
//        noRecordLayout = itemView.findViewById(R.id.no_record_layout);
//        layoutProgressBar = itemView.findViewById(R.id.progress_bar_layout);
        backButton = itemView.findViewById(R.id.back_button);
        headingTextview = itemView.findViewById(R.id.heading_textview);
        followUpTextView = itemView.findViewById(R.id.follow_up_text_view);
        followUpTextView.setTypeface(null, Typeface.BOLD);
        instructionsTextView = itemView.findViewById(R.id.instructions_text_view);
        instructionsTextView.setTypeface(null, Typeface.BOLD);
        medicinesTextView = itemView.findViewById(R.id.medicines_text_view);
        medicinesTextView.setTypeface(null, Typeface.BOLD);
        testsTextView = itemView.findViewById(R.id.tests_text_view);
        testsTextView.setTypeface(null, Typeface.BOLD);
        parentLayout = itemView.findViewById(R.id.parent_layout);
        followUpTime = itemView.findViewById(R.id.follow_up_time);
        followUpField = itemView.findViewById(R.id.follow_up_field);
        instructionsField = itemView.findViewById(R.id.instructions_field);
        medicinesField = itemView.findViewById(R.id.medicines_field);
        testsField = itemView.findViewById(R.id.tests_field);
        consultationDate = itemView.findViewById(R.id.consultation_date);
        patientName = itemView.findViewById(R.id.patient_name);
        lineView1 = itemView.findViewById(R.id.line_view_1);
        gender = itemView.findViewById(R.id.gender_text_view);
        lineView2 = itemView.findViewById(R.id.line_view_2);
        age = itemView.findViewById(R.id.age_text_view);
        doctorDegree = itemView.findViewById(R.id.doctor_degree);
        doctorSpeciality = itemView.findViewById(R.id.doctor_speciality);
        doctorNameField = itemView.findViewById(R.id.doctor_name_field);
        writtenPrescriptionRecyclerview = itemView.findViewById(R.id.written_prescription_recyclerview);
        prescriptionsTextView = itemView.findViewById(R.id.prescriptions_text_view);
        doctorNameField2 = itemView.findViewById(R.id.doctor_name_text_view_2);
        sharePrecriptionViewsContainer = itemView.findViewById(R.id.share_prescription_views_container);
        savePrecriptionViewsContainer = itemView.findViewById(R.id.save_prescription_views_container);
    }

    public ConstraintLayout getParentLayout() {
        return parentLayout;
    }

    public ImageButton getBackButton() {
        return backButton;
    }

    public BodyText getHeadingTextview() {
        return headingTextview;
    }

//    public RelativeLayout getNoRecordLayout() {
//        return noRecordLayout;
//    }
//
//    public RelativeLayout getLayoutProgressBar() {
//        return layoutProgressBar;
//    }

    public BodyText getDoctorNameField() {
        return doctorNameField;
    }

    public BodyText getFollowUpTime() {
        return followUpTime;
    }

    public BodyText getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public BodyText getDoctorDegree() {
        return doctorDegree;
    }

    public BodyText getPatientName() {
        return patientName;
    }

    public BodyText getConsultationDate() {
        return consultationDate;
    }

    public BodyText getTestsField() {
        return testsField;
    }

    public BodyText getMedicinesField() {
        return medicinesField;
    }

    public BodyText getInstructionsField() {
        return instructionsField;
    }

    public BodyText getFollowUpField() {
        return followUpField;
    }

    public BodyText getTestsTextView() {
        return testsTextView;
    }

    public BodyText getMedicinesTextView() {
        return medicinesTextView;
    }

    public BodyText getInstructionsTextView() {
        return instructionsTextView;
    }

    public BodyText getFollowUpTextView() {
        return followUpTextView;
    }

    public RecyclerView getWrittenPrescriptionRecyclerview() {
        return writtenPrescriptionRecyclerview;
    }

    public CircleImageView getDoctorImageView() {
        return doctorImageView;
    }

    public BodyText getPrescriptionsTextView() {
        return prescriptionsTextView;
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.parent_layout) {
            adpaterViewItemClickedListenerForPrescription.onItemClicked(getAdapterPosition(), PrescriptionActivity.PRESCRIPTION_CARD, "0");
        } else if (viewId == R.id.share_prescription_views_container) {
            adpaterViewItemClickedListenerForPrescription.onItemClicked(parentLayout, getAdapterPosition(), PrescriptionActivity.SHARE_PRESCRIPTION);
        } else if (viewId == R.id.save_prescription_views_container) {
            adpaterViewItemClickedListenerForPrescription.onItemClicked(parentLayout, getAdapterPosition(), PrescriptionActivity.SAVE_PRESCRIPTION);
        }
    }
}



