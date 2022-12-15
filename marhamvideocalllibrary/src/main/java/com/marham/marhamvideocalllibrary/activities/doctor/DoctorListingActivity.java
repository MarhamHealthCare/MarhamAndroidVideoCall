package com.marham.marhamvideocalllibrary.activities.doctor;

import android.os.Bundle;
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
import com.marham.marhamvideocalllibrary.adapters.doctor.DoctorFiltersAdapter;
import com.marham.marhamvideocalllibrary.adapters.doctor.DoctorListingAdapter;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.listeners.EndlessRecyclerOnScrollListener;
import com.marham.marhamvideocalllibrary.model.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.ServerResponse;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.model.doctor.AllDoctorResponse;
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
import java.util.SortedMap;

import retrofit2.Call;

public class DoctorListingActivity extends BaseActivity implements ServerConnectListener {

    private ConstraintLayout filtersViewsContainer;
    private RecyclerView filterRecyclerView;
    private MyButton filtersTryAgainButton;
    private ProgressBar filtersProgressBar;
    private List<DoctorListingFilter> doctorListingFilters;

    private ConstraintLayout doctorListingViewsContainer;
    private RecyclerView doctorsRecyclerView;
    private MyButton doctorsRetryButton;
    private ProgressBar doctorsProgressBar;

    private RetroFit2Callback<ServerResponse> retroFit2Callback;

    private Speciality speciality;
    private Diseases diseases;
    private int listingType;
    private String objectId;
    private String objectName;
    private String listingTypeString;

    boolean isCallInProgress = false;
    boolean isEndReachSearch = false;
    private int currentPage = 1;

    public static final int FILTERS_RECYCLER_VIEW = 0;
    public static final int DOCTORS_RECYCLER_VIEW = 1;

    public static final String specialityListTypeString = "listType";
    public static final int DOCTOR_LISTING_TYPE_SPECIALITY = 0;
    public static final int DOCTOR_LISTING_TYPE_DISEASE = 1;

    private List<DoctorInfo> doctorInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_listing);
        initializeViews();
        initVariables();
        setListeners();
        fetchData();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (R.id.filters_retry_button == viewId) {
            getDoctorListingFilters();
        }else if(R.id.doctors_recycler_view == viewId){
            getVCDoctors(currentPage);
        }
    }

    private void initializeViews() {
        initializeTopBar();
        filtersViewsContainer = findViewById(R.id.filters_views_container);
        filterRecyclerView = findViewById(R.id.filters_recycler_view);
        filtersTryAgainButton = findViewById(R.id.filters_retry_button);
        filtersProgressBar = findViewById(R.id.filters_progress_bar);

        doctorListingViewsContainer = findViewById(R.id.dr_listing_views_container);
        doctorsRecyclerView = findViewById(R.id.doctors_recycler_view);
        doctorsRetryButton = findViewById(R.id.doctors_retry_button);
        doctorsProgressBar = findViewById(R.id.doctors_progress_bar);
    }

    private void initVariables() {
        receiveIntent();

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

    private void receiveIntent(){
        Bundle bundle = getIntent().getExtras();
        listingType = bundle.getInt(DoctorListingActivity.specialityListTypeString);

        switch (listingType){
            case DoctorListingActivity.DOCTOR_LISTING_TYPE_SPECIALITY:
                speciality = bundle.getParcelable(Speciality.class.getCanonicalName());
                objectId = speciality.getSpID();
                objectName = speciality.getSpecialityName();
                listingTypeString = "";
                break;

            case DoctorListingActivity.DOCTOR_LISTING_TYPE_DISEASE:
                diseases = bundle.getParcelable(Diseases.class.getCanonicalName());
                objectId = diseases.getId();
                objectName = diseases.getDisease();
                listingTypeString = "Disease";
                break;
        }

    }

    private void fetchData(){
        getDoctorListingFilters();
        getVCDoctors(currentPage);
    }

    private void setListeners() {
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


    public void setDoctorsRecyclerView(List<DoctorInfo> doctorInfoList) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        doctorsRecyclerView.setLayoutManager(linearLayoutManager);
        DoctorListingAdapter doctorListingAdapter = new DoctorListingAdapter(this, doctorInfoList, adpaterViewItemClickedListener);
        doctorsRecyclerView.setAdapter(doctorListingAdapter);

        doctorsRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                getVCDoctors(current_page);
            }
        });

    }

    public void setViewsBeforeGettingDoctorListing() {
        doctorListingViewsContainer.setVisibility(View.VISIBLE);
        doctorsRecyclerView.setVisibility(View.INVISIBLE);
        doctorsProgressBar.setVisibility(View.VISIBLE);
        doctorsRetryButton.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingDoctorListing() {
        doctorListingViewsContainer.setVisibility(View.VISIBLE);
        doctorsRecyclerView.setVisibility(View.VISIBLE);
        doctorsProgressBar.setVisibility(View.GONE);
        doctorsRetryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingDoctorListing() {
        doctorListingViewsContainer.setVisibility(View.GONE);
        doctorsRecyclerView.setVisibility(View.GONE);
        doctorsProgressBar.setVisibility(View.GONE);
        doctorsRetryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorListing() {
        doctorListingViewsContainer.setVisibility(View.VISIBLE);
        doctorsRecyclerView.setVisibility(View.GONE);
        doctorsProgressBar.setVisibility(View.GONE);
        doctorsRetryButton.setVisibility(View.VISIBLE);
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

                case DoctorListingActivity.DOCTORS_RECYCLER_VIEW:
                    Toast.makeText(DoctorListingActivity.this,"Selected Doctor: "+position, Toast.LENGTH_SHORT).show();
                    break;

            }
        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID, String s) {

        }
    };

    private void setFilter(int position) {
        for (int i = 0; i < doctorListingFilters.size(); i++) {
            doctorListingFilters.get(i).setSelected(position == i);
        }
        filterRecyclerView.getAdapter().notifyDataSetChanged();
        filterRecyclerView.smoothScrollToPosition(position);
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

    public void getVCDoctors(int current_page) {
        if (isCallInProgress||isEndReachSearch) {
            return;
        }
        isCallInProgress = true;
        Call<AllDoctorResponse> call = null;
        if (current_page == 1) {
            setViewsBeforeGettingDoctorListing();
        }
        currentPage = current_page + 1;
        APIClient apiClient = new APIClient();
        call = apiClient.getAllDoctors("", "", "", "", "", objectId, current_page, "", "", "", "", "1", listingTypeString, "0", "", "", "2", "1", "en", "0");
        retroFit2Callback = new RetroFit2Callback<>(DoctorListingActivity.this, this, AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING);
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
            case AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING:
                isCallInProgress = false;
                doctorInfoList.clear();
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    AllDoctorResponse allDoctorResponse = (AllDoctorResponse) response;
                    doctorInfoList.addAll(allDoctorResponse.getData().getDoctors());
                    if (currentPage == 1) {
                        setViewsAfterGettingDoctorListing();
                        setDoctorsRecyclerView(doctorInfoList);
                    }else{
                        doctorsRecyclerView.getAdapter().notifyDataSetChanged();
                    }

                } else {
                    if (currentPage == 1) {
                        setViewsIncaseNoRecordFoundWhileGettingDoctorListing();
                    } else {
                        isEndReachSearch = true;
                    }
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

            case AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorListing();
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

            case AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorListing();
                break;
        }
    }

}