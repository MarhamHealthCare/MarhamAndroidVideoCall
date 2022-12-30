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
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.adapters.doctor.BaseDoctorsAdapter;
import com.marham.marhamvideocalllibrary.adapters.doctor.DoctorFiltersAdapter;
import com.marham.marhamvideocalllibrary.adapters.doctor.DoctorListingAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.listeners.EndlessRecyclerOnScrollListener;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.model.doctor.AllDoctorResponse;
import com.marham.marhamvideocalllibrary.model.doctor.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.filter.DoctorListingFilter;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.speciality.NewAllSpecialitiesServerResponse;
import com.marham.marhamvideocalllibrary.model.speciality.Speciality;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class DoctorListingActivity extends BaseActivity implements ServerConnectListener {

    private ConstraintLayout filtersViewsContainer;
    private RecyclerView filterRecyclerView;
    private List<DoctorListingFilter> doctorListingFilters;

    private ConstraintLayout doctorListingViewsContainer;
    private BodyText drCountAndSpecialityTextView;
    private RecyclerView doctorsRecyclerView;
    private MyButton doctorsRetryButton;
    private ProgressBar doctorsProgressBar;
    private BodyText doctorsNoRecordFoundTextView;

    private RetroFit2Callback<ServerResponseOld> retroFit2Callback;

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

    public static final String specialityListTypeString = "listType";
    public static final int DOCTOR_LISTING_TYPE_SPECIALITY = 0;
    public static final int DOCTOR_LISTING_TYPE_DISEASE = 1;

    private List<DoctorInfo> doctorInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_listing);
        initVariables();
        initializeViews();
        setListeners();
        fetchData();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (R.id.doctors_retry_button == viewId) {
            resetVariablesAndGetDoctorInfoAgain();
        }
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

    private void resetVariablesAndGetDoctorInfoAgain() {
        resetVariables();
        getVCDoctors(currentPage);
    }

    private void resetVariables() {
        isCallInProgress = false;
        currentPage = 1;
        doctorInfoList.clear();
    }

    protected void initializeViews() {
        super.initializeViews();
        filtersViewsContainer = findViewById(R.id.filters_views_container);
        filterRecyclerView = findViewById(R.id.filters_recycler_view);

        doctorListingViewsContainer = findViewById(R.id.dr_listing_views_container);
        drCountAndSpecialityTextView = findViewById(R.id.dr_count_and_speciality_text_view);
        doctorsRecyclerView = findViewById(R.id.doctors_recycler_view);
        doctorsRetryButton = findViewById(R.id.doctors_retry_button);
        doctorsProgressBar = findViewById(R.id.doctors_progress_bar);
        doctorsNoRecordFoundTextView = findViewById(R.id.doctors_no_record_found_text_view);

        //TODO: Replace with speciality or disease name and count
        drCountAndSpecialityTextView.setText("-121 Leading " + objectName);
        setDoctorsRecyclerView(doctorInfoList);
    }

    private void receiveIntent() {
        Bundle bundle = getIntent().getExtras();
        listingType = bundle.getInt(DoctorListingActivity.specialityListTypeString);

        switch (listingType) {
            case DoctorListingActivity.DOCTOR_LISTING_TYPE_SPECIALITY:
                speciality = bundle.getParcelable(Speciality.class.getCanonicalName());
                objectId = speciality.getSpID();
                objectName = speciality.getSpeciality();
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

    private void fetchData() {
        getVCDoctors(currentPage);
    }

    private void setListeners() {
        doctorsRetryButton.setOnClickListener(this);
    }

    public void setFiltersRecyclerView(List<DoctorListingFilter> doctorListingFilterList) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        filterRecyclerView.setLayoutManager(gridLayoutManager);
        DoctorFiltersAdapter doctorFiltersAdapter = new DoctorFiltersAdapter(this, doctorListingFilterList, adpaterViewItemClickedListener);
        filterRecyclerView.setAdapter(doctorFiltersAdapter);
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
        doctorsNoRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingDoctorListing() {
        doctorListingViewsContainer.setVisibility(View.VISIBLE);
        doctorsRecyclerView.setVisibility(View.VISIBLE);
        doctorsProgressBar.setVisibility(View.GONE);
        doctorsRetryButton.setVisibility(View.GONE);
        doctorsNoRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingDoctorListing() {
        doctorListingViewsContainer.setVisibility(View.VISIBLE);
        doctorsRecyclerView.setVisibility(View.INVISIBLE);
        doctorsProgressBar.setVisibility(View.GONE);
        doctorsRetryButton.setVisibility(View.GONE);
        doctorsNoRecordFoundTextView.setVisibility(View.VISIBLE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorListing() {
        doctorListingViewsContainer.setVisibility(View.VISIBLE);
        doctorsRecyclerView.setVisibility(View.INVISIBLE);
        doctorsProgressBar.setVisibility(View.GONE);
        doctorsRetryButton.setVisibility(View.VISIBLE);
        doctorsNoRecordFoundTextView.setVisibility(View.GONE);
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
                    resetVariablesAndGetDoctorInfoAgain();
                    break;

                case BaseDoctorsAdapter.DOCTOR_LISTING_RECYCLER_VIEW:
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DoctorInfo.class.getCanonicalName(), doctorInfoList.get(position));
                    MarhamUtils.getInstance().startActivity(DoctorListingActivity.this, DoctorProfileActivity.class, false, bundle);

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

    public void getVCDoctors(int current_page) {
        if (isCallInProgress || isEndReachSearch) {
            return;
        }
        isCallInProgress = true;
        Call<AllDoctorResponse> call = null;
        if (current_page == 1) {
            setViewsBeforeGettingDoctorListing();
        }
        APIClient apiClient = new APIClient();
        call = apiClient.getAllDoctors("", "", "", "", "", objectId, current_page, "", "", "", "", "1", listingTypeString, "0", "", "", "2", "1", "en", "0");
        retroFit2Callback = new RetroFit2Callback<>(DoctorListingActivity.this, this, AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING);
        call.enqueue(retroFit2Callback);
    }

    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING:
                isCallInProgress = false;
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_OLD)) {
                    AllDoctorResponse allDoctorResponse = (AllDoctorResponse) response;
                    doctorInfoList.addAll(allDoctorResponse.getData().getDoctors());
                    if (doctorInfoList.size() > 0) {
                        if (currentPage <= 1) {
                            setViewsAfterGettingDoctorListing();
                            setDoctorsRecyclerView(doctorInfoList);
                        } else {
                            doctorsRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    } else {
                        handleNoRecordFound();
                    }
                } else {
                    handleNoRecordFound();
                }
                currentPage = currentPage + 1;
                break;
        }
    }

    private void handleNoRecordFound() {
        if (currentPage == 1) {
            setViewsIncaseNoRecordFoundWhileGettingDoctorListing();
        } else {
            isEndReachSearch = true;
        }
    }

    @Override
    public void onFailure(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING:
                isCallInProgress = false;
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorListing();
                break;
        }
    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING:
                isCallInProgress = false;
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorListing();
                break;
        }
    }

}