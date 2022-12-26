package com.marham.marhamvideocalllibrary.activities.patientrecord;

import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.adapters.patientrecord.PrescriptionAdapter;
import com.marham.marhamvideocalllibrary.listeners.AdpaterViewItemClickedListenerForPrescription;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.patientrecord.PatientHistory;
import com.marham.marhamvideocalllibrary.model.patientrecord.PatientHistoryResponse;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;
import com.marham.marhamvideocalllibrary.utils.PhotoModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class PrescriptionActivity extends BaseActivity implements ServerConnectListener {

    private RecyclerView patientReportsRecyclerView;
    private ConstraintLayout patientrReportsLayout;

    private List<PatientHistory> prescriptionList = new ArrayList<>();

    private RetroFit2Callback<ServerResponseOld> retroFit2Callback;

    public static final int PRESCRIPTION_CARD = 1;
    public static final int SHARE_PRESCRIPTION = 2;
    public static final int SAVE_PRESCRIPTION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        initializeViews();
        fetchData();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if(R.id.retry_button==viewId){
            getPatientPastPrescriptions("");
        }
    }

    private void fetchData() {
        getPatientPastPrescriptions("");
    }

    protected void initializeViews() {
        super.initializeViews();
        patientReportsRecyclerView = findViewById(R.id.patient_reports_recycler_view);
        patientrReportsLayout = findViewById(R.id.patient_reports_layout);
    }


    public void setUpPrescriptionRecyclerView(List<PatientHistory> prescriptionList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        patientReportsRecyclerView.setLayoutManager(linearLayoutManager);
        PrescriptionAdapter pastPrescriptionAdapter = new PrescriptionAdapter(this, prescriptionList, adpaterViewItemClickedListenerForPrescription);
        patientReportsRecyclerView.setAdapter(pastPrescriptionAdapter);
    }

    private void setViewsBeforeCallingAPI() {
        noRecordFoundTextView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        patientrReportsLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }


    private void setViewsAfterCallingPastPrescriptionApi() {
        noRecordFoundTextView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        patientrReportsLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void setViewsIfNoRecordFound() {
        noRecordFoundTextView.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
        patientrReportsLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private void setViewsIfFailed() {
        noRecordFoundTextView.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
        patientrReportsLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private AdpaterViewItemClickedListenerForPrescription adpaterViewItemClickedListenerForPrescription = new AdpaterViewItemClickedListenerForPrescription() {
        @Override
        public void onItemClicked(int position, int requestId, String data) {

        }

        @Override
        public void onItemClicked(View view, int position, int requestId) {
            PhotoModule photoModule = new PhotoModule();
            switch (requestId) {
                case PrescriptionActivity.SHARE_PRESCRIPTION:
                    photoModule.shareBitmap(PrescriptionActivity.this, view);
                    break;

                case PrescriptionActivity.SAVE_PRESCRIPTION:
                    photoModule.saveBitmap(PrescriptionActivity.this, view);
                    break;
            }
        }
    };


    //TODO: Replace API Here
    public void getPatientPastPrescriptions(String patientId) {
        setViewsBeforeCallingAPI();
        HashMap<String, String> hashMap = new HashMap<>();
        APIClient apiClient = new APIClient();

        //TODO: Replace After implementing Appointment Listing
        //Dev
        hashMap.put(AppConstants.API.API_KEYS.PATIENT_ID_KEY, "511");
        hashMap.put(AppConstants.API.API_KEYS.DOCTOR_ID_KEY_2, "6596");
        hashMap.put(AppConstants.API.API_KEYS.APPOINTMENT_ID_KEY, "1022171913");

        //Live
//        hashMap.put(AppConstants.API.API_KEYS.PATIENT_ID_KEY, "366024");
//        hashMap.put(AppConstants.API.API_KEYS.DOCTOR_ID_KEY_2, "6596");
//        hashMap.put(AppConstants.API.API_KEYS.APPOINTMENT_ID_KEY, "102217633818");
        Call<PatientHistoryResponse> call = apiClient.getPastPrescription(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_PATIENT_PRESCRIPTIONS);
        call.enqueue(retroFit2Callback);
    }

    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_PATIENT_PRESCRIPTIONS:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_OLD)) {
                    setViewsAfterCallingPastPrescriptionApi();
                    PatientHistoryResponse patientHistoryResponse = (PatientHistoryResponse) response;
                    prescriptionList.clear();
                    prescriptionList.addAll(patientHistoryResponse.getData());
                    setUpPrescriptionRecyclerView(prescriptionList);
                } else {
                    setViewsIfNoRecordFound();
                }
                break;
        }

    }

    @Override
    public void onFailure(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_PATIENT_PRESCRIPTIONS:
                setViewsIfFailed();
                break;
        }

    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_PATIENT_PRESCRIPTIONS:
                setViewsIfFailed();
                break;
        }
    }
}