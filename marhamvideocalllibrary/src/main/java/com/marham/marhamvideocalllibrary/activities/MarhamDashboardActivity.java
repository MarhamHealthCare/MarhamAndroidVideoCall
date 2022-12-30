package com.marham.marhamvideocalllibrary.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.appointments.AllVideoConsultationsScreenMainActivity;
import com.marham.marhamvideocalllibrary.activities.disease.SearchDiseaseActivity;
import com.marham.marhamvideocalllibrary.activities.doctor.DoctorListingActivity;
import com.marham.marhamvideocalllibrary.activities.doctor.DoctorProfileActivity;
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.activities.search.SearchSpecialityAndDiseaseActivity;
import com.marham.marhamvideocalllibrary.activities.speciality.SearchSpecialityActivity;
import com.marham.marhamvideocalllibrary.adapters.disease.BaseDiseaseAdapter;
import com.marham.marhamvideocalllibrary.adapters.disease.TopDiseaseAdapter;
import com.marham.marhamvideocalllibrary.adapters.doctor.BaseDoctorsAdapter;
import com.marham.marhamvideocalllibrary.adapters.doctor.DoctorDasboardAdapter;
import com.marham.marhamvideocalllibrary.adapters.speciality.BaseSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.adapters.speciality.TopSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.disease.DashboardDiseasesServerResponse;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.model.doctor.DashboardDoctorServerResponse;
import com.marham.marhamvideocalllibrary.model.doctor.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.speciality.NewAllSpecialitiesServerResponse;
import com.marham.marhamvideocalllibrary.model.speciality.Speciality;
import com.marham.marhamvideocalllibrary.model.user.MarhamUserServerResponse;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class MarhamDashboardActivity extends BaseActivity implements ServerConnectListener {

    public static final String TAG = "Marham Video Call: ";
    private CardView searchCardView;
    private ConstraintLayout myAppointmentsViewsContainer;

    private ScrollView scrollView;

    //Top Doctor Views
    private ConstraintLayout dashboardDoctorsViewsContainer;
    private RecyclerView dashboardDoctorsRecyclerView;
    private ProgressBar dashboardDoctorsProgressBar;
    private MyButton dashboardDoctorsRetryButton;

    private List<DoctorInfo> doctorInfoList = new ArrayList<>();

    //Top Diseases Views
    private ConstraintLayout topDiseasesViewsContainer;
    private RecyclerView topDiseasesRecyclerView;
    private MyButton topDiseasesRetryButton;
    private ProgressBar topDiseasesProgressBar;
    private ConstraintLayout viewAllDiseasesViewsContainer;
    private List<Diseases> diseasesArrayList = new ArrayList<>();

    //Top Specialities Views
    private ConstraintLayout topSpecialitiesViewsContainer;
    private RecyclerView topSpecialitiesRecyclerView;
    private MyButton topSpecialitiesRetryButton;
    private ProgressBar topSpecialitiesProgressBar;
    private ConstraintLayout viewAllSpecialitiesViewsContainer;
    private List<Speciality> specialityList = new ArrayList<>();


    private RetroFit2Callback<ServerResponseOld> retroFit2Callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marham_dashboard);
        initializeViews();
        setListeners();
        getUserDetails();
    }

    private void fetchData() {
        getDashboardDoctors();
        getTopDiseases();
        getTopSpecialities();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int viewId = v.getId();
        if (R.id.search_card_view == viewId) {
            MarhamUtils.getInstance().startActivity(this, SearchSpecialityAndDiseaseActivity.class, false);
        } else if (R.id.my_appointments_views_container == viewId) {
            MarhamUtils.getInstance().startActivity(this, AllVideoConsultationsScreenMainActivity.class, false);
        } else if (R.id.retry_button == viewId) {
            getUserDetails();
        } else if (R.id.dashboard_doctors_retry_button == viewId) {
            getDashboardDoctors();
        } else if (R.id.top_diseases_retry_button == viewId) {
            getTopDiseases();
        } else if (R.id.top_specialities_retry_button == viewId) {
            getTopSpecialities();
        } else if (R.id.view_all_diseases_views_container == viewId) {
            MarhamUtils.getInstance().startActivity(this, SearchDiseaseActivity.class, false);
        } else if (R.id.view_all_specialities_views_container == viewId) {
            MarhamUtils.getInstance().startActivity(this, SearchSpecialityActivity.class, false);
        }
    }

    protected void initializeViews() {
        super.initializeViews();

        scrollView = findViewById(R.id.scroll_view);

        searchCardView = findViewById(R.id.search_card_view);
        myAppointmentsViewsContainer = findViewById(R.id.my_appointments_views_container);

        dashboardDoctorsViewsContainer = findViewById(R.id.dashboard_doctor_views_container);
        dashboardDoctorsRecyclerView = findViewById(R.id.dashboard_doctors_recycler_view);
        dashboardDoctorsProgressBar = findViewById(R.id.dashboard_doctors_progress_bar);
        dashboardDoctorsRetryButton = findViewById(R.id.dashboard_doctors_retry_button);

        topDiseasesViewsContainer = findViewById(R.id.top_diseases_views_container);
        topDiseasesRecyclerView = findViewById(R.id.top_diseases_recycler_view);
        topDiseasesRetryButton = findViewById(R.id.top_diseases_retry_button);
        topDiseasesProgressBar = findViewById(R.id.top_diseases_progress_bar);
        viewAllDiseasesViewsContainer = findViewById(R.id.view_all_diseases_views_container);

        topSpecialitiesViewsContainer = findViewById(R.id.top_specialities_views_container);
        topSpecialitiesRecyclerView = findViewById(R.id.top_specialities_recycler_view);
        topSpecialitiesRetryButton = findViewById(R.id.top_specialities_retry_button);
        topSpecialitiesProgressBar = findViewById(R.id.top_specialities_progress_bar);
        viewAllSpecialitiesViewsContainer = findViewById(R.id.view_all_specialities_views_container);

    }

    private void setListeners() {
        searchCardView.setOnClickListener(this);
        myAppointmentsViewsContainer.setOnClickListener(this);
        dashboardDoctorsRetryButton.setOnClickListener(this);
        topDiseasesRetryButton.setOnClickListener(this);
        topSpecialitiesRetryButton.setOnClickListener(this);
        viewAllDiseasesViewsContainer.setOnClickListener(this);
        viewAllSpecialitiesViewsContainer.setOnClickListener(this);
    }

    public void getUserDetails() {
        setViewsBeforeGettingUserDetails();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.USER_NAME, MarhamVideoCallHelper.getInstance().getUserName());
        hashMap.put(AppConstants.API.API_KEYS.USER_PHONE, MarhamVideoCallHelper.getInstance().getUserPhoneNumber());


        Call<MarhamUserServerResponse> call;
        APIClient apiClient = new APIClient("sdk");
        call = apiClient.getUserDetails(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_USER_DETAILS);
        call.enqueue(retroFit2Callback);
    }

    public void setViewsBeforeGettingUserDetails() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingUserDetails() {
        scrollView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingUserDetails() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingUserDetails() {
        scrollView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }


    public void setTopDoctorsRecyclerView(List<DoctorInfo> doctorInfoList) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dashboardDoctorsRecyclerView.setLayoutManager(gridLayoutManager);
        DoctorDasboardAdapter doctorDasboardAdapter = new DoctorDasboardAdapter(this, doctorInfoList, adpaterViewItemClickedListener);
        dashboardDoctorsRecyclerView.setAdapter(doctorDasboardAdapter);
    }

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

    public void setTopDiseaseRecyclerView(List<Diseases> diseasesList) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topDiseasesRecyclerView.setLayoutManager(gridLayoutManager);
        TopDiseaseAdapter topDiseaseAdapter = new TopDiseaseAdapter(this, diseasesList, adpaterViewItemClickedListener);
        topDiseasesRecyclerView.setAdapter(topDiseaseAdapter);
    }

    public void setViewsBeforeGettingTopDiseasesData() {
        topDiseasesViewsContainer.setVisibility(View.VISIBLE);
        topDiseasesRecyclerView.setVisibility(View.INVISIBLE);
        topDiseasesProgressBar.setVisibility(View.VISIBLE);
        topDiseasesRetryButton.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingTopDiseasesData() {
        topDiseasesViewsContainer.setVisibility(View.VISIBLE);
        topDiseasesRecyclerView.setVisibility(View.VISIBLE);
        topDiseasesProgressBar.setVisibility(View.GONE);
        topDiseasesRetryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingTopDiseasesData() {
        topDiseasesViewsContainer.setVisibility(View.GONE);
        topDiseasesRecyclerView.setVisibility(View.INVISIBLE);
        topDiseasesProgressBar.setVisibility(View.GONE);
        topDiseasesRetryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingTopDiseasesData() {
        topDiseasesViewsContainer.setVisibility(View.VISIBLE);
        topDiseasesRecyclerView.setVisibility(View.INVISIBLE);
        topDiseasesProgressBar.setVisibility(View.GONE);
        topDiseasesRetryButton.setVisibility(View.VISIBLE);
    }

    public void setTopSpecialitiesRecyclerView(List<Speciality> specialityList) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topSpecialitiesRecyclerView.setLayoutManager(gridLayoutManager);
        TopSpecialitiesAdapter topSpecialitiesAdapter = new TopSpecialitiesAdapter(this, specialityList, adpaterViewItemClickedListener);
        topSpecialitiesRecyclerView.setAdapter(topSpecialitiesAdapter);
    }

    public void setViewsBeforeGettingTopSpecialitiesData() {
        topSpecialitiesViewsContainer.setVisibility(View.VISIBLE);
        topSpecialitiesRecyclerView.setVisibility(View.INVISIBLE);
        topSpecialitiesProgressBar.setVisibility(View.VISIBLE);
        topSpecialitiesRetryButton.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingTopSpecialitiesData() {
        topSpecialitiesViewsContainer.setVisibility(View.VISIBLE);
        topSpecialitiesRecyclerView.setVisibility(View.VISIBLE);
        topSpecialitiesProgressBar.setVisibility(View.GONE);
        topSpecialitiesRetryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingTopSpecialitiesData() {
        topSpecialitiesViewsContainer.setVisibility(View.GONE);
        topSpecialitiesRecyclerView.setVisibility(View.INVISIBLE);
        topSpecialitiesProgressBar.setVisibility(View.GONE);
        topSpecialitiesRetryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingTopSpecialitiesData() {
        topSpecialitiesViewsContainer.setVisibility(View.VISIBLE);
        topSpecialitiesRecyclerView.setVisibility(View.INVISIBLE);
        topSpecialitiesProgressBar.setVisibility(View.GONE);
        topSpecialitiesRetryButton.setVisibility(View.VISIBLE);
    }

    private AdapterViewItemClickedListener adpaterViewItemClickedListener = new AdapterViewItemClickedListener() {
        @Override
        public void onAdatviewItemClicked(int position) {

        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID) {
            switch (requestID) {
                case BaseDoctorsAdapter.DOCTOR_DASBHBOARD_RECYCLER_VIEW:
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DoctorInfo.class.getCanonicalName(), doctorInfoList.get(position));
                    MarhamUtils.getInstance().startActivity(MarhamDashboardActivity.this, DoctorProfileActivity.class, false, bundle);
                    break;
                case BaseDiseaseAdapter.TOP_DISEASES:
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt(DoctorListingActivity.specialityListTypeString, DoctorListingActivity.DOCTOR_LISTING_TYPE_DISEASE);
                    bundle1.putParcelable(Diseases.class.getCanonicalName(), diseasesArrayList.get(position));
                    MarhamUtils.getInstance().startActivity(MarhamDashboardActivity.this, DoctorListingActivity.class, false, bundle1);
                    break;
                case BaseSpecialitiesAdapter.TOP_SPECIALITIES:
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(DoctorListingActivity.specialityListTypeString, DoctorListingActivity.DOCTOR_LISTING_TYPE_SPECIALITY);
                    bundle2.putParcelable(Speciality.class.getCanonicalName(), specialityList.get(position));
                    MarhamUtils.getInstance().startActivity(MarhamDashboardActivity.this, DoctorListingActivity.class, false, bundle2);
                    break;
            }
        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID, String s) {

        }
    };


    public void getDashboardDoctors() {
        setViewsBeforeGettingDasboardDoctorsData();
        APIClient apiClient = new APIClient("sdk");
        HashMap<String, String> hashMap = new HashMap<>();
        Call<DashboardDoctorServerResponse> call = apiClient.getDashboardDoctos(hashMap);
        retroFit2Callback = new RetroFit2Callback<ServerResponseOld>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_DOCTORS);
        call.enqueue(retroFit2Callback);
    }

    public void getTopDiseases() {
        setViewsBeforeGettingTopDiseasesData();
        APIClient apiClient = new APIClient("sdk");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.TOP_ONLY, "1");
        Call<DashboardDiseasesServerResponse> call;
        call = apiClient.getDashboardSpecialitiesWithDiseases(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_TOP_DISEASES);
        call.enqueue(retroFit2Callback);

    }

    public void getTopSpecialities() {
        setViewsBeforeGettingTopSpecialitiesData();
        APIClient apiClient = new APIClient("sdk");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.TOP_ONLY, "1");
        hashMap.put(AppConstants.API.API_KEYS.NEW_ID, "1");
        Call<NewAllSpecialitiesServerResponse> call = apiClient.getSpecialities(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES);
        call.enqueue(retroFit2Callback);
    }

    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_USER_DETAILS:
                MarhamUserServerResponse marhamUserServerResponse = (MarhamUserServerResponse) response;
                if (marhamUserServerResponse.getSuccess().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    setViewsAfterGettingUserDetails();
                    MarhamVideoCallHelper.getInstance().setMarhamUser(marhamUserServerResponse.getData());
                    fetchData();
                } else {
                    MarhamUtils.getInstance().showAPIResponseMessage(this, marhamUserServerResponse.getMessage());
                    finish();
                }
                break;

            case AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_DOCTORS:
                DashboardDoctorServerResponse topDoctorServerResponse = (DashboardDoctorServerResponse) response;
                if (topDoctorServerResponse.getSuccess().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    setViewsAfterGettingDasboardDoctorsData();
                    doctorInfoList.clear();
                    doctorInfoList.addAll(topDoctorServerResponse.getData());
                    setTopDoctorsRecyclerView(doctorInfoList);

                } else {
                    setViewsIncaseNoRecordFoundWhileGettingDasboardDoctorsData();
                }
                break;

            case AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_TOP_DISEASES:
//                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_OLD)) {
//                    setViewsAfterGettingTopDiseasesData();
//                    DashboardDiseasesServerResponse dashboardDiseasesServerResponse = (DashboardDiseasesServerResponse) response;
//                    diseasesArrayList.clear();
//                    diseasesArrayList.addAll(dashboardDiseasesServerResponse.getData());
//                    setTopDiseaseRecyclerView(diseasesArrayList);
//                } else {
//                    setViewsIncaseNoRecordFoundWhileGettingTopDiseasesData();
//                }

                DashboardDiseasesServerResponse dashboardDiseasesServerResponse = (DashboardDiseasesServerResponse) response;
                if (dashboardDiseasesServerResponse.getSuccess().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    setViewsAfterGettingTopDiseasesData();
                    diseasesArrayList.clear();
                    diseasesArrayList.addAll(dashboardDiseasesServerResponse.getData());
                    setTopDiseaseRecyclerView(diseasesArrayList);
                } else {
                    setViewsIncaseNoRecordFoundWhileGettingTopDiseasesData();
                }

                break;

            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES:
                NewAllSpecialitiesServerResponse newAllSpecialitiesServerResponse = (NewAllSpecialitiesServerResponse) response;
                if (newAllSpecialitiesServerResponse.getSuccess().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    setViewsAfterGettingTopSpecialitiesData();
                    specialityList.addAll(newAllSpecialitiesServerResponse.getData().getTopSpecialities());
                    setTopSpecialitiesRecyclerView(specialityList);
                } else {
                    setViewsIncaseNoRecordFoundWhileGettingTopSpecialitiesData();
                }


                break;

        }
    }

    @Override
    public void onFailure(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_USER_DETAILS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingUserDetails();
                break;
            case AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_DOCTORS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDasboardDoctorsData();
                break;
            case AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_TOP_DISEASES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingTopDiseasesData();
                break;
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingTopSpecialitiesData();
                break;
        }
    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_USER_DETAILS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingUserDetails();
                break;
            case AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_DOCTORS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDasboardDoctorsData();
                break;
            case AppConstants.API.API_END_POINT_NUMBER.GET_DASHBOARD_TOP_DISEASES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingTopDiseasesData();
                break;
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingTopSpecialitiesData();
                break;
        }
    }
}