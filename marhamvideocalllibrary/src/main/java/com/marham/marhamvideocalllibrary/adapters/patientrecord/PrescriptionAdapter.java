package com.marham.marhamvideocalllibrary.adapters.patientrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdpaterViewItemClickedListenerForPrescription;
import com.marham.marhamvideocalllibrary.model.patientrecord.PatientHistory;
import com.marham.marhamvideocalllibrary.viewHolders.patientrecord.PrescriptionViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionViewHolder>{

    public List<PatientHistory> prescriptionList;
    private Context context;
    private List<String> images = new ArrayList<>();
    private AdpaterViewItemClickedListenerForPrescription adpaterViewItemClickedListenerForPrescription;

    public PrescriptionAdapter(Context context, List<PatientHistory> appointments_list, AdpaterViewItemClickedListenerForPrescription adpaterViewItemClickedListener) {
        this.context = context;
        this.prescriptionList = appointments_list;
        this.adpaterViewItemClickedListenerForPrescription = adpaterViewItemClickedListener;

    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_prescription, parent, false);
        return new PrescriptionViewHolder(itemView, adpaterViewItemClickedListenerForPrescription);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {

        PatientHistory patientSummaryData = prescriptionList.get(position);

        Picasso.get().load(patientSummaryData.getDoctorImageUrl()).into(holder.getDoctorImageView(), new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                holder.getDoctorImageView().setImageResource(R.drawable.m_doctor_placholder);
            }
        });


        holder.doctorNameField.setText(patientSummaryData.getDocName());
        holder.doctorNameField2.setText(patientSummaryData.getDocName());
        holder.doctorSpeciality.setText(patientSummaryData.getSpeciality());
        holder.doctorDegree.setText(patientSummaryData.getDocDegree());
        holder.patientName.setText(patientSummaryData.getPatientName());
        holder.consultationDate.setText(patientSummaryData.getFormattedDate() + "  " + patientSummaryData.getFormattedTime());

        if (patientSummaryData.getTests() != null) {
            holder.testsField.setText(patientSummaryData.getTests());
        } else {
            holder.testsTextView.setVisibility(View.GONE);
            holder.testsField.setVisibility(View.GONE);
        }
        if (patientSummaryData.getMedicines() != null) {
            holder.medicinesField.setText(patientSummaryData.getMedicines());
        } else {
            holder.medicinesField.setVisibility(View.GONE);
            holder.medicinesTextView.setVisibility(View.GONE);
        }
        if (patientSummaryData.getInstructions() != null) {
            holder.instructionsField.setText(patientSummaryData.getInstructions());
        } else {
            holder.instructionsField.setVisibility(View.GONE);
            holder.instructionsTextView.setVisibility(View.GONE);
        }

        if ((patientSummaryData.getFollowUpDate() == null) && (patientSummaryData.getFollowUpTime() == null)) {
            holder.followUpTextView.setVisibility(View.GONE);
            holder.getFollowUpTime().setVisibility(View.GONE);
            holder.followUpField.setVisibility(View.GONE);
        } else {
            holder.followUpTextView.setVisibility(View.VISIBLE);
            holder.getFollowUpTime().setVisibility(View.VISIBLE);
            holder.followUpField.setText(patientSummaryData.getFollowUpDate());
            holder.followUpTime.setText(patientSummaryData.getFollowUpTime());
        }

        if (prescriptionList.get(position).getFiles() != null && prescriptionList.get(position).getFiles().size() > 0) {
            holder.getPrescriptionsTextView().setVisibility(View.GONE);
            holder.getWrittenPrescriptionRecyclerview().setVisibility(View.VISIBLE);
            setWrittenPrescriptionRecyclerView(holder, position);
        } else {
            holder.getPrescriptionsTextView().setVisibility(View.GONE);
            holder.getWrittenPrescriptionRecyclerview().setVisibility(View.GONE);
        }

        if (patientSummaryData.getGender() != null) {
            holder.lineView1.setVisibility(View.VISIBLE);
            holder.gender.setVisibility(View.VISIBLE);
            holder.gender.setText(patientSummaryData.getGender());
        } else {
            holder.lineView1.setVisibility(View.GONE);
            holder.gender.setVisibility(View.VISIBLE);
        }

        if (patientSummaryData.getAge() != null && !patientSummaryData.getAge().equals("0")) {
            holder.lineView2.setVisibility(View.VISIBLE);
            holder.age.setVisibility(View.VISIBLE);
            holder.age.setText(patientSummaryData.getAge());
        } else {
            holder.lineView2.setVisibility(View.GONE);
            holder.age.setVisibility(View.VISIBLE);
        }

    }

    private void setWrittenPrescriptionRecyclerView(PrescriptionViewHolder holder, int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.getWrittenPrescriptionRecyclerview().setLayoutManager(linearLayoutManager);
        WrittenPrescriptionAdapter writtenPrescriptionAdapter = new WrittenPrescriptionAdapter(context, prescriptionList.get(position).getFiles());
        holder.getWrittenPrescriptionRecyclerview().setAdapter(writtenPrescriptionAdapter);
    }


    @Override
    public int getItemCount() {
        return handleLogicToShowOnRecordOnly();
    }

    private int handleLogicToShowOnRecordOnly() {
        if (prescriptionList.size() > 0) {
            return 1;
        } else {
            return prescriptionList.size();
        }
    }


}
