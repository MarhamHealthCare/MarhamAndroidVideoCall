package com.marham.marhamvideocalllibrary.activities.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.marham.marhamvideocalllibrary.activities.doctor.DoctorListingActivity;
import com.marham.marhamvideocalllibrary.adapters.disease.AllDiseaseAdapter;
import com.marham.marhamvideocalllibrary.adapters.disease.BaseDiseaseAdapter;
import com.marham.marhamvideocalllibrary.adapters.speciality.AllSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.adapters.speciality.BaseSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.adapters.speciality.RecentlySearchedSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyEditText;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.disease.DashboardDiseasesServerResponse;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
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

public class SearchSpecialityAndDiseaseActivity extends BaseActivity implements ServerConnectListener {

    private BodyEditText searchView;

    private ConstraintLayout recentlySearchedSpecialitiesViewsContainer;
    private RecyclerView recentlySearchedSpecialitiesRecyclerView;

    private ConstraintLayout allSpecialitiesViewsContainer;
    private RecyclerView allSpecialitiesRecyclerView;

    private ConstraintLayout viewAllSpecialitiesViewsContainer;

    private MyButton specialitiesRetryButton;
    private ProgressBar specialitiesProgressBar;
    private BodyText specialitiesNoRecordFoundTextView;

    private List<Speciality> recentlySearchedSpecialitiesArrayList = new ArrayList<>();
    private List<Speciality> allSpecialitiesArrayList = new ArrayList<>();
    private AllSpecialitiesAdapter allSpecialitiesAdapter;


    private ConstraintLayout allDiseasesViewsContainer;
    private RecyclerView allDiseasesRecyclerView;

    private MyButton diseasesRetryButton;
    private ProgressBar diseasesProgressBar;
    private BodyText diseasesNoRecordFoundTextView;

    private List<Diseases> allDiseasesArrayList = new ArrayList<>();
    private AllDiseaseAdapter allDiseaseAdapter;

    private RetroFit2Callback<ServerResponseOld> retroFit2Callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_speciality_and_disease);
        initializeViews();
        setListeners();
        fetchData();
    }

    private void fetchData() {
        getTopSpecialities();
        getTopDiseases();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (R.id.specialities_retry_button == viewId) {
            getTopSpecialities();
        }else if(R.id.diseases_retry_button == viewId){
            getTopDiseases();
        }else if(R.id.view_all_specialities_views_container == viewId){
            viewAllSpecialitiesViewsContainer.setVisibility(View.GONE);
            allSpecialitiesAdapter.setShowAll(true);
            allSpecialitiesAdapter.notifyDataSetChanged();
        }
    }

    protected void initializeViews() {
        super.initializeViews();

        searchView = findViewById(R.id.search_edit_text);

        recentlySearchedSpecialitiesViewsContainer = findViewById(R.id.recently_searched_specialities_views_container);
        recentlySearchedSpecialitiesRecyclerView = findViewById(R.id.recently_searched_specialities_recycler_view);

        allSpecialitiesViewsContainer = findViewById(R.id.all_specialities_views_container);
        allSpecialitiesRecyclerView = findViewById(R.id.all_specialities_recycler_view);
        viewAllSpecialitiesViewsContainer = findViewById(R.id.view_all_specialities_views_container);

        specialitiesRetryButton = findViewById(R.id.specialities_retry_button);
        specialitiesProgressBar = findViewById(R.id.specialities_progress_bar);
        specialitiesNoRecordFoundTextView = findViewById(R.id.specialities_no_record_found_text_view);

        allDiseasesViewsContainer = findViewById(R.id.all_diseases_views_container);
        allDiseasesRecyclerView = findViewById(R.id.all_diseases_recycler_view);

        diseasesRetryButton = findViewById(R.id.diseases_retry_button);
        diseasesProgressBar = findViewById(R.id.diseases_progress_bar);
        diseasesNoRecordFoundTextView = findViewById(R.id.diseases_no_record_found_text_view);


    }

    private void setListeners() {
        specialitiesRetryButton.setOnClickListener(this);
        diseasesRetryButton.setOnClickListener(this);
        viewAllSpecialitiesViewsContainer.setOnClickListener(this);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                allSpecialitiesAdapter.getFilter().filter(s);
                allDiseaseAdapter.getFilter().filter(s);
            }
        });

    }

    public void setRecentlySearchedSpecialitiesRecyclerView(List<Speciality> specialityList) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recentlySearchedSpecialitiesRecyclerView.setLayoutManager(gridLayoutManager);
        RecentlySearchedSpecialitiesAdapter recentlySearchedSpecialitiesAdapter = new RecentlySearchedSpecialitiesAdapter(this, specialityList, adpaterViewItemClickedListener);
        recentlySearchedSpecialitiesRecyclerView.setAdapter(recentlySearchedSpecialitiesAdapter);
    }

    public void setAllSpeciaitiesRecyclerView(List<Speciality> specialityList) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allSpecialitiesRecyclerView.setLayoutManager(gridLayoutManager);
        allSpecialitiesAdapter = new AllSpecialitiesAdapter(this, specialityList, adpaterViewItemClickedListener);
        allSpecialitiesAdapter.setShowAll(false);
        allSpecialitiesRecyclerView.setAdapter(allSpecialitiesAdapter);
    }

    private void setSpecialitiesList(NewAllSpecialitiesServerResponse newAllSpecialitiesServerResponse) {
        recentlySearchedSpecialitiesArrayList.addAll(newAllSpecialitiesServerResponse.getData().getSpecialities());
        setRecentlySearchedSpecialitiesRecyclerView(recentlySearchedSpecialitiesArrayList);

        allSpecialitiesArrayList.addAll(newAllSpecialitiesServerResponse.getData().getSpecialities());
        setAllSpeciaitiesRecyclerView(allSpecialitiesArrayList);
    }

    public void setViewsBeforeGettingSpecialitiesData() {
        allSpecialitiesRecyclerView.setVisibility(View.INVISIBLE);
        specialitiesProgressBar.setVisibility(View.VISIBLE);
        specialitiesRetryButton.setVisibility(View.GONE);
        specialitiesNoRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingSpecialitiesData() {
        allSpecialitiesRecyclerView.setVisibility(View.VISIBLE);
        specialitiesProgressBar.setVisibility(View.GONE);
        specialitiesRetryButton.setVisibility(View.GONE);
        specialitiesNoRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingSpecialitiesData() {
        allSpecialitiesRecyclerView.setVisibility(View.INVISIBLE);
        specialitiesProgressBar.setVisibility(View.GONE);
        specialitiesRetryButton.setVisibility(View.GONE);
        specialitiesNoRecordFoundTextView.setVisibility(View.VISIBLE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingSpecialitiesData() {
        allSpecialitiesRecyclerView.setVisibility(View.INVISIBLE);
        specialitiesProgressBar.setVisibility(View.GONE);
        specialitiesRetryButton.setVisibility(View.VISIBLE);
        specialitiesNoRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setAllDiseaseRecyclerView(List<Diseases> diseasesList) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allDiseasesRecyclerView.setLayoutManager(linearLayoutManager);
        allDiseaseAdapter = new AllDiseaseAdapter(this, diseasesList, adpaterViewItemClickedListener);
        allDiseasesRecyclerView.setAdapter(allDiseaseAdapter);
    }

    private void setDiseasesList(DashboardDiseasesServerResponse dashboardDiseasesServerResponse) {
        allDiseasesArrayList.addAll(dashboardDiseasesServerResponse.getData());
        setAllDiseaseRecyclerView(allDiseasesArrayList);
    }

    public void setViewsBeforeGettingDiseasesData() {
        allDiseasesRecyclerView.setVisibility(View.INVISIBLE);
        diseasesProgressBar.setVisibility(View.VISIBLE);
        diseasesRetryButton.setVisibility(View.GONE);
        diseasesNoRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingTopDiseasesData() {
        allDiseasesRecyclerView.setVisibility(View.VISIBLE);
        diseasesProgressBar.setVisibility(View.GONE);
        diseasesRetryButton.setVisibility(View.GONE);
        diseasesNoRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingDiseasesData() {
        allDiseasesRecyclerView.setVisibility(View.INVISIBLE);
        diseasesProgressBar.setVisibility(View.GONE);
        diseasesRetryButton.setVisibility(View.GONE);
        diseasesNoRecordFoundTextView.setVisibility(View.VISIBLE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDiseasesData() {
        allDiseasesRecyclerView.setVisibility(View.INVISIBLE);
        diseasesProgressBar.setVisibility(View.GONE);
        diseasesRetryButton.setVisibility(View.VISIBLE);
        diseasesNoRecordFoundTextView.setVisibility(View.GONE);
    }


    private AdapterViewItemClickedListener adpaterViewItemClickedListener = new AdapterViewItemClickedListener() {
        @Override
        public void onAdatviewItemClicked(int position) {

        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID) {
            Bundle bundle = new Bundle();
            bundle.putInt(DoctorListingActivity.specialityListTypeString, DoctorListingActivity.DOCTOR_LISTING_TYPE_SPECIALITY);

            switch (requestID) {
                case BaseSpecialitiesAdapter.RECENTLY_SEARCHED_SPECIALITIES:
                    Toast.makeText(SearchSpecialityAndDiseaseActivity.this, "Tapped Recently Searched:" + position, Toast.LENGTH_SHORT).show();
                    bundle.putParcelable(Speciality.class.getCanonicalName(), recentlySearchedSpecialitiesArrayList.get(position));
                    break;
                case BaseSpecialitiesAdapter.ALL_SPECIALITIES:
                    Toast.makeText(SearchSpecialityAndDiseaseActivity.this, "Tapped All :" + position, Toast.LENGTH_SHORT).show();
                    bundle.putParcelable(Speciality.class.getCanonicalName(), recentlySearchedSpecialitiesArrayList.get(position));
                    break;
                case BaseDiseaseAdapter.ALL_DISEASES:
                    bundle.putParcelable(Diseases.class.getCanonicalName(), allDiseasesArrayList.get(position));
                    break;
            }
            MarhamUtils.getInstance().startActivity(SearchSpecialityAndDiseaseActivity.this, DoctorListingActivity.class, false, bundle);
        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID, String s) {

        }
    };

    private void getTopSpecialities() {
        setViewsBeforeGettingSpecialitiesData();
        APIClient apiClient = new APIClient("sdk");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.TOP_ONLY, "0");
        Call<NewAllSpecialitiesServerResponse> call = apiClient.getSpecialities(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES);
        call.enqueue(retroFit2Callback);
    }

    private void getTopDiseases() {
        setViewsBeforeGettingDiseasesData();
        APIClient apiClient = new APIClient("sdk");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.TOP_ONLY, "0");
        Call<DashboardDiseasesServerResponse> call;
        call = apiClient.getDashboardSpecialitiesWithDiseases(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_ALL_DISEASES);
        call.enqueue(retroFit2Callback);

    }

    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES:
                NewAllSpecialitiesServerResponse newAllSpecialitiesServerResponse = (NewAllSpecialitiesServerResponse) response;
                if (newAllSpecialitiesServerResponse.getSuccess().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    setViewsAfterGettingSpecialitiesData();
                    setSpecialitiesList(newAllSpecialitiesServerResponse);
                } else {
                    setViewsIncaseNoRecordFoundWhileGettingSpecialitiesData();
                }
                break;

            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_DISEASES:
                DashboardDiseasesServerResponse dashboardDiseasesServerResponse = (DashboardDiseasesServerResponse) response;
                if (dashboardDiseasesServerResponse.getSuccess().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    setViewsAfterGettingTopDiseasesData();
                    setDiseasesList(dashboardDiseasesServerResponse);
                } else {
                    setViewsIncaseNoRecordFoundWhileGettingDiseasesData();
                }
                break;

        }
    }

    @Override
    public void onFailure(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingSpecialitiesData();
                break;

            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_DISEASES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDiseasesData();
                break;
        }

    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingSpecialitiesData();
                break;
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_DISEASES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDiseasesData();
                break;
        }

    }
}