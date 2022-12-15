package com.marham.marhamvideocalllibrary.activities.doctor;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.BaseActivity;
import com.marham.marhamvideocalllibrary.activities.disease.SearchDiseaseActivity;
import com.marham.marhamvideocalllibrary.adapters.disease.AllDiseaseAdapter;
import com.marham.marhamvideocalllibrary.adapters.disease.BaseDiseaseAdapter;
import com.marham.marhamvideocalllibrary.adapters.doctor.DoctorFiltersAdapter;
import com.marham.marhamvideocalllibrary.adapters.speciality.TopSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.ServerResponse;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.model.doctor.DashboardDoctorServerResponse;
import com.marham.marhamvideocalllibrary.model.filter.DoctorListingFilter;
import com.marham.marhamvideocalllibrary.model.speciality.NewAllSpecialitiesServerResponse;
import com.marham.marhamvideocalllibrary.model.speciality.Speciality;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class DoctorListingActivity extends BaseActivity implements ServerConnectListener {

    private ConstraintLayout filtersViewsContainer;
    private RecyclerView filterRecyclerView;
    private MyButton filtersTryAgainButton;
    private ProgressBar filtersProgressBar;

    private List<DoctorListingFilter> doctorListingFilters;

    private RetroFit2Callback<ServerResponse> retroFit2Callback;

    public static final int FILTERS_RECYCLER_VIEW = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_listing);
        initializeViews();
        initVariables();
        setListeners();
        getDoctorListingFilters();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (R.id.filters_retry_button == viewId) {
            Toast.makeText(this,"Filters Retry Button",Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews(){
        initializeTopBar();
        filtersViewsContainer = findViewById(R.id.filters_views_container);
        filterRecyclerView = findViewById(R.id.filters_recycler_view);
        filtersTryAgainButton = findViewById(R.id.filters_retry_button);
        filtersProgressBar = findViewById(R.id.filters_progress_bar);
    }

    private void initVariables(){
        DoctorListingFilter allDoctorsFilter = new DoctorListingFilter();
        allDoctorsFilter.setId("1");
        allDoctorsFilter.setTitle("All");
        allDoctorsFilter.setSelected(true);

        DoctorListingFilter availableTodayFilter = new DoctorListingFilter();
        availableTodayFilter.setId("2");
        availableTodayFilter.setTitle("Available Today");

        DoctorListingFilter mostExperiencedFilter = new DoctorListingFilter();
        mostExperiencedFilter.setId("3");
        mostExperiencedFilter.setTitle("Most Experienced");

        DoctorListingFilter lowestFeeFilter = new DoctorListingFilter();
        lowestFeeFilter.setId("4");
        lowestFeeFilter.setTitle("Lowest Fee");

        DoctorListingFilter femaleDoctor = new DoctorListingFilter();
        femaleDoctor.setId("5");
        femaleDoctor.setTitle("Female Doctor");

        doctorListingFilters = new ArrayList<>();
        doctorListingFilters.add(allDoctorsFilter);
        doctorListingFilters.add(availableTodayFilter);
        doctorListingFilters.add(mostExperiencedFilter);
        doctorListingFilters.add(lowestFeeFilter);
        doctorListingFilters.add(femaleDoctor);

    }

    private void setListeners(){
        filtersTryAgainButton.setOnClickListener(this);
    }

    public void setFiltersRecyclerView(List<DoctorListingFilter> doctorListingFilterList) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        filterRecyclerView.setLayoutManager(gridLayoutManager);
        DoctorFiltersAdapter doctorFiltersAdapter = new DoctorFiltersAdapter(this, doctorListingFilterList, adpaterViewItemClickedListener);
        filterRecyclerView.setAdapter(doctorFiltersAdapter);
    }

    public void setViewsBeforeGettingDoctorListingFilters() {
        filtersViewsContainer.setVisibility(View.VISIBLE);
        filterRecyclerView.setVisibility(View.INVISIBLE);
        filtersProgressBar.setVisibility(View.VISIBLE);
        filtersTryAgainButton.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingDoctorListingFilters() {
        filtersViewsContainer.setVisibility(View.VISIBLE);
        filterRecyclerView.setVisibility(View.VISIBLE);
        filtersProgressBar.setVisibility(View.GONE);
        filtersTryAgainButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingDoctorListingFilters() {
        filtersViewsContainer.setVisibility(View.GONE);
        filterRecyclerView.setVisibility(View.GONE);
        filtersProgressBar.setVisibility(View.GONE);
        filtersTryAgainButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorListingFilters() {
        filtersViewsContainer.setVisibility(View.VISIBLE);
        filterRecyclerView.setVisibility(View.GONE);
        filtersProgressBar.setVisibility(View.GONE);
        filtersTryAgainButton.setVisibility(View.VISIBLE);
    }

    private AdapterViewItemClickedListener adpaterViewItemClickedListener = new AdapterViewItemClickedListener() {
        @Override
        public void onAdatviewItemClicked(int position) {

        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID) {
            switch (requestID) {
                case DoctorListingActivity.FILTERS_RECYCLER_VIEW:
                    Toast.makeText(DoctorListingActivity.this, "Selected Filter " + position, Toast.LENGTH_SHORT).show();
                    setFilter(position);
                    break;

            }
        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID, String s) {

        }
    };

    private void setFilter(int position){
        for(int i = 0; i<doctorListingFilters.size();i++){
            doctorListingFilters.get(i).setSelected(position == i);
        }

        filterRecyclerView.getAdapter().notifyDataSetChanged();

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                // Do something after 5s = 5000ms
                filterRecyclerView.smoothScrollToPosition(position);
//            }
//        }, 1000);


    }

    public void getDoctorListingFilters() {
        setViewsBeforeGettingDoctorListingFilters();
        APIClient apiClient = new APIClient();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.TOP_ONLY, "1");
        hashMap.put(AppConstants.API.API_KEYS.NEW_ID, "1");
        Call<NewAllSpecialitiesServerResponse> call = apiClient.getDoctorListingFilters(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_DOCTOR_LISTING_FILTERS);
        call.enqueue(retroFit2Callback);
    }

    @Override
    public void onSuccess(ServerResponse response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_DOCTOR_LISTING_FILTERS:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    setViewsAfterGettingDoctorListingFilters();
                    NewAllSpecialitiesServerResponse newAllSpecialitiesServerResponse = (NewAllSpecialitiesServerResponse) response;
//                    doctorInfoList.clear();
//                    doctorInfoList.addAll(topDoctorServerResponse.getData());
                   setFiltersRecyclerView(doctorListingFilters);

                } else {
                    setViewsIncaseNoRecordFoundWhileGettingDoctorListingFilters();
                }
                break;
        }
    }

    @Override
    public void onFailure(ServerResponse response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_DOCTOR_LISTING_FILTERS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorListingFilters();
                break;
        }
    }

    @Override
    public void onSessionExpiry(ServerResponse response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_DOCTOR_LISTING_FILTERS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorListingFilters();
                break;
        }
    }
}