package com.marham.marhamvideocalllibrary.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.adapters.DashboardDoctorsAdapter;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.ServerResponse;
import com.marham.marhamvideocalllibrary.model.doctor.DashboardDoctorServerResponse;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;
import com.marham.marhamvideocalllibrary.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class MarhamDashboardActivity extends BaseActivity implements View.OnClickListener, ServerConnectListener {

    public static final String TAG = "Marham Video Call: ";
    private CardView searchCardView;
    private ConstraintLayout myAppointmentsViewsContainer;

    private ConstraintLayout dashboardDoctorsViewsContainer;
    private RecyclerView dashboardDoctorsRecyclerView;
    private ProgressBar dashboardDoctorsProgressBar;
    private MyButton dashboardDoctorsRetryButton;

    private List<DoctorInfo> doctorInfoList = new ArrayList<>();

    public static final int DASHBOARD_DOCTORS_RECYCLER_VIEW = 1;

    private RetroFit2Callback<ServerResponse> retroFit2Callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marham_dashboard);
        initializeViews();
        setListeners();
        fetchData();
    }

    private void fetchData() {
        getDashboardDoctors();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.search_card_view) {
            Toast.makeText(this, "Tapped Search Bar", Toast.LENGTH_SHORT).show();
        } else if (viewId == R.id.my_appointments_views_container) {
            Toast.makeText(this, "My Appointments", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews() {
        searchCardView = findViewById(R.id.search_card_view);
        myAppointmentsViewsContainer = findViewById(R.id.my_appointments_views_container);

        dashboardDoctorsViewsContainer = findViewById(R.id.dashboard_doctor_views_container);
        dashboardDoctorsRecyclerView = findViewById(R.id.dashboard_doctors_recycler_view);
        dashboardDoctorsProgressBar = findViewById(R.id.dashboard_doctors_progress_bar);
        dashboardDoctorsRetryButton = findViewById(R.id.dashboard_doctors_retry_button);

    }

    private void setListeners() {
        searchCardView.setOnClickListener(this);
        myAppointmentsViewsContainer.setOnClickListener(this);
        dashboardDoctorsRetryButton.setOnClickListener(this);
    }

    public void setTopDoctorsRecyclerView(List<DoctorInfo> doctorInfoList) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dashboardDoctorsRecyclerView.setLayoutManager(gridLayoutManager);
        DashboardDoctorsAdapter dashboardDoctorsAdapter = new DashboardDoctorsAdapter(this, doctorInfoList, adpaterViewItemClickedListener);
        dashboardDoctorsRecyclerView.setAdapter(dashboardDoctorsAdapter);
    }

    private AdapterViewItemClickedListener adpaterViewItemClickedListener= new AdapterViewItemClickedListener() {
        @Override
        public void onAdatviewItemClicked(int position) {

        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID) {
            switch (requestID) {
                case MarhamDashboardActivity.DASHBOARD_DOCTORS_RECYCLER_VIEW:
                    Toast.makeText(MarhamDashboardActivity.this,"Tapped Doctor: "+position,Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID, String s) {

        }
    };

    public void setViewsBeforeGettingDasboardDoctorsData() {
        dashboardDoctorsViewsContainer.setVisibility(View.VISIBLE);
        dashboardDoctorsRecyclerView.setVisibility(View.INVISIBLE);
        dashboardDoctorsProgressBar.setVisibility(View.VISIBLE);
        dashboardDoctorsRetryButton.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingDasboardDoctorsData() {
        dashboardDoctorsViewsContainer.setVisibility(View.VISIBLE);
        dashboardDoctorsRecyclerView.setVisibility(View.VISIBLE);
        dashboardDoctorsProgressBar.setVisibility(View.GONE);
        dashboardDoctorsRetryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingDasboardDoctorsData() {
        dashboardDoctorsViewsContainer.setVisibility(View.GONE);
        dashboardDoctorsRecyclerView.setVisibility(View.INVISIBLE);
        dashboardDoctorsProgressBar.setVisibility(View.GONE);
        dashboardDoctorsRetryButton.setVisibility(View.GONE);
    }


    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDasboardDoctorsData() {
        dashboardDoctorsViewsContainer.setVisibility(View.VISIBLE);
        dashboardDoctorsRecyclerView.setVisibility(View.INVISIBLE);
        dashboardDoctorsProgressBar.setVisibility(View.GONE);
        dashboardDoctorsRetryButton.setVisibility(View.VISIBLE);
    }

    public void getDashboardDoctors() {
        setViewsBeforeGettingDasboardDoctorsData();
        APIClient apiClient = new APIClient(this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.CITY, "Lahore");
        Call<DashboardDoctorServerResponse> call = apiClient.getDashboardDoctos(hashMap);
        retroFit2Callback = new RetroFit2Callback<ServerResponse>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_DOCTORS);
        call.enqueue(retroFit2Callback);
    }


    @Override
    public void onSuccess(ServerResponse response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_DOCTORS:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    setViewsAfterGettingDasboardDoctorsData();
                    DashboardDoctorServerResponse topDoctorServerResponse = (DashboardDoctorServerResponse) response;
                    doctorInfoList.clear();
                    doctorInfoList.addAll(topDoctorServerResponse.getData());
                    setTopDoctorsRecyclerView(doctorInfoList);

                } else {
                    setViewsIncaseNoRecordFoundWhileGettingDasboardDoctorsData();
                }
                break;
        }
    }

    @Override
    public void onFailure(ServerResponse response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_DOCTORS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDasboardDoctorsData();
                Utils.getInstance().showAPIResponseMessage(this,response.getMessage());
                break;
        }
    }

    @Override
    public void onSessionExpiry(ServerResponse response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_DOCTORS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDasboardDoctorsData();
                Utils.getInstance().showAPIResponseMessage(this,response.getMessage());
                break;
        }
    }
}