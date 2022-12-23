package com.marham.marhamvideocalllibrary.activities.medicalrecord;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.BaseActivity;
import com.marham.marhamvideocalllibrary.customviews.MyButton;

import java.util.HashMap;

import retrofit2.Call;

public class PrescriptionActivity extends BaseActivity {

    private RecyclerView patientReportsRecyclerView;
    private ConstraintLayout patientrReportsLayout;
    private CardView orderMedicineCardView;
    private MyButton centralRetryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        initializeViews();
    }

    protected void initializeViews() {
        super.initializeViews();
        patientReportsRecyclerView = findViewById(R.id.patient_reports_recycler_view);
        patientrReportsLayout = findViewById(R.id.patient_reports_layout);
    }

    public void getPatientPastPrescriptions(String patientId) {
        view.setViewsBeforeCallingAPI();
        HashMap<String, String> hashMap = new HashMap<>();
        APIClient apiClient = new APIClient();
        hashMap.put(context.getString(R.string.patient_id), appointment.getPatientID());
        hashMap.put(context.getString(R.string.doctorId), appointment.getDID());
//        hashMap.put(context.getString(R.string.appointmentId), appointment.getApptID());
        Call<PatientHistoryResponse> call = apiClient.getPastPrescription(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, context, AppConstants.GET_PAST_PRESCRIPTION);
        call.enqueue(retroFit2Callback);
    }

}