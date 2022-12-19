package com.marham.marhamvideocalllibrary.activities.disease;

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
import com.marham.marhamvideocalllibrary.activities.MarhamDashboardActivity;
import com.marham.marhamvideocalllibrary.activities.doctor.DoctorListingActivity;
import com.marham.marhamvideocalllibrary.adapters.disease.AllDiseaseAdapter;
import com.marham.marhamvideocalllibrary.adapters.disease.BaseDiseaseAdapter;
import com.marham.marhamvideocalllibrary.adapters.disease.RecentlySearchedDiseaseAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyEditText;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.ServerResponse;
import com.marham.marhamvideocalllibrary.model.disease.DashboardDiseasesServerResponse;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class SearchDiseaseActivity extends BaseActivity implements ServerConnectListener {

    private ConstraintLayout parentLayout;

    private BodyEditText searchView;

    private ConstraintLayout recentlySearchedDiseasesViewsContainer;
    private RecyclerView recentlySearchedDiseasesRecyclerView;

    private ConstraintLayout allDiseasesViewsContainer;
    private RecyclerView allDiseasesRecyclerView;

    private MyButton diseasesRetryButton;
    private ProgressBar diseasesProgressBar;
    private BodyText noRecordFoundTextView;

    private List<Diseases> recentlySearchedDiseasesArrayList = new ArrayList<>();
    private List<Diseases> allDiseasesArrayList = new ArrayList<>();
    private AllDiseaseAdapter allDiseaseAdapter;

    private RetroFit2Callback<ServerResponse> retroFit2Callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease);
        initializeViews();
        setListeners();
        getTopDiseases();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int viewId = v.getId();
        if (R.id.diseases_retry_button == viewId) {
            getTopDiseases();
        }
    }

    protected void initializeViews() {
        super.initializeViews();
        parentLayout = findViewById(R.id.parent_layout);

        searchView = findViewById(R.id.search_edit_text);

        recentlySearchedDiseasesViewsContainer = findViewById(R.id.recently_searched_diseases_views_container);
        recentlySearchedDiseasesRecyclerView = findViewById(R.id.recently_searched_diseases_recycler_view);

        allDiseasesViewsContainer = findViewById(R.id.all_diseases_views_container);
        allDiseasesRecyclerView = findViewById(R.id.all_diseases_recycler_view);

        diseasesRetryButton = findViewById(R.id.diseases_retry_button);
        diseasesProgressBar = findViewById(R.id.diseases_progress_bar);
        noRecordFoundTextView = findViewById(R.id.no_record_found_text_view);
    }

    private void setListeners() {
        diseasesRetryButton.setOnClickListener(this);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                allDiseaseAdapter.getFilter().filter(s);
            }
        });

    }

    public void setRecentlySearchedDiseaseRecyclerView(List<Diseases> diseasesList) {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recentlySearchedDiseasesRecyclerView.setLayoutManager(gridLayoutManager);
        RecentlySearchedDiseaseAdapter recentlySearchedDiseaseAdapter = new RecentlySearchedDiseaseAdapter(this, diseasesList, adpaterViewItemClickedListener);
        recentlySearchedDiseasesRecyclerView.setAdapter(recentlySearchedDiseaseAdapter);
    }

    public void setAllDiseaseRecyclerView(List<Diseases> diseasesList) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allDiseasesRecyclerView.setLayoutManager(linearLayoutManager);
        allDiseaseAdapter = new AllDiseaseAdapter(this, diseasesList, adpaterViewItemClickedListener);
        allDiseasesRecyclerView.setAdapter(allDiseaseAdapter);
    }

    private void setDiseasesList(DashboardDiseasesServerResponse dashboardDiseasesServerResponse) {
        recentlySearchedDiseasesArrayList.addAll(dashboardDiseasesServerResponse.getData());
        setRecentlySearchedDiseaseRecyclerView(recentlySearchedDiseasesArrayList);

        allDiseasesArrayList.addAll(dashboardDiseasesServerResponse.getData());
        setAllDiseaseRecyclerView(allDiseasesArrayList);
    }

    public void setViewsBeforeGettingDiseasesData() {
        parentLayout.setVisibility(View.GONE);
        diseasesProgressBar.setVisibility(View.VISIBLE);
        diseasesRetryButton.setVisibility(View.GONE);
        noRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingTopDiseasesData() {
        parentLayout.setVisibility(View.VISIBLE);
        diseasesProgressBar.setVisibility(View.GONE);
        diseasesRetryButton.setVisibility(View.GONE);
        noRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingDiseasesData() {
        parentLayout.setVisibility(View.GONE);
        diseasesProgressBar.setVisibility(View.GONE);
        diseasesRetryButton.setVisibility(View.GONE);
        noRecordFoundTextView.setVisibility(View.VISIBLE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDiseasesData() {
        parentLayout.setVisibility(View.GONE);
        diseasesProgressBar.setVisibility(View.GONE);
        diseasesRetryButton.setVisibility(View.VISIBLE);
        noRecordFoundTextView.setVisibility(View.GONE);
    }


    private AdapterViewItemClickedListener adpaterViewItemClickedListener = new AdapterViewItemClickedListener() {
        @Override
        public void onAdatviewItemClicked(int position) {

        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID) {
            Bundle bundle = new Bundle();
            bundle.putInt(DoctorListingActivity.specialityListTypeString, DoctorListingActivity.DOCTOR_LISTING_TYPE_DISEASE);
            switch (requestID) {
                case BaseDiseaseAdapter.RECENTLY_SEARCHED_DISEASES:
                    bundle.putParcelable(Diseases.class.getCanonicalName(), recentlySearchedDiseasesArrayList.get(position));
                    break;
                case BaseDiseaseAdapter.ALL_DISEASES:
                    bundle.putParcelable(Diseases.class.getCanonicalName(), allDiseasesArrayList.get(position));
                    break;
            }
            MarhamUtils.getInstance().startActivity(SearchDiseaseActivity.this, DoctorListingActivity.class, false,bundle);
        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID, String s) {

        }
    };


    public void getTopDiseases() {
        setViewsBeforeGettingDiseasesData();
        APIClient apiClient = new APIClient();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.TOP_ONLY, "1");
        Call<DashboardDiseasesServerResponse> call;
        call = apiClient.getDashboardSpecialitiesWithDiseases(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_ALL_DISEASES);
        call.enqueue(retroFit2Callback);

    }

    @Override
    public void onSuccess(ServerResponse response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_DISEASES:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    setViewsAfterGettingTopDiseasesData();
                    DashboardDiseasesServerResponse dashboardDiseasesServerResponse = (DashboardDiseasesServerResponse) response;
                    setDiseasesList(dashboardDiseasesServerResponse);

                } else {
                    setViewsIncaseNoRecordFoundWhileGettingDiseasesData();
                }
                break;
        }
    }

    @Override
    public void onFailure(ServerResponse response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_DISEASES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDiseasesData();
                break;
        }
    }

    @Override
    public void onSessionExpiry(ServerResponse response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_DISEASES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDiseasesData();
                break;
        }
    }
}