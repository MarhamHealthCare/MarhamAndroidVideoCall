package com.marham.marhamvideocalllibrary.activities.speciality;

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
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.activities.doctor.DoctorListingActivity;
import com.marham.marhamvideocalllibrary.adapters.speciality.AllSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.adapters.speciality.BaseSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.adapters.speciality.RecentlySearchedSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyEditText;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
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

public class SearchSpecialityActivity extends BaseActivity implements ServerConnectListener {

    private ConstraintLayout parentLayout;

    private BodyEditText searchView;

    private ConstraintLayout recentlySearchedSpecialitiesViewsContainer;
    private RecyclerView recentlySearchedSpecialitiesRecyclerView;

    private ConstraintLayout allSpecialitiesViewsContainer;
    private RecyclerView allSpecialitiesRecyclerView;

    private MyButton specialitiesRetryButton;
    private ProgressBar specialitiesProgressBar;
    private BodyText noRecordFoundTextView;

    private List<Speciality> recentlySearchedSpecialitiesArrayList = new ArrayList<>();
    private List<Speciality> allSpecialitiesArrayList = new ArrayList<>();
    private AllSpecialitiesAdapter allSpecialitiesAdapter;

    private RetroFit2Callback<ServerResponseOld> retroFit2Callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_speciality);
        initializeViews();
        setListeners();
        getTopSpecialities();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int viewId = v.getId();
        if (R.id.specialities_retry_button == viewId) {
            getTopSpecialities();
        }
    }

    protected void initializeViews() {
        super.initializeViews();
        parentLayout = findViewById(R.id.parent_layout);

        searchView = findViewById(R.id.search_edit_text);

        recentlySearchedSpecialitiesViewsContainer = findViewById(R.id.recently_searched_specialities_views_container);
        recentlySearchedSpecialitiesRecyclerView = findViewById(R.id.recently_searched_specialities_recycler_view);

        allSpecialitiesViewsContainer = findViewById(R.id.all_specialities_views_container);
        allSpecialitiesRecyclerView = findViewById(R.id.all_specialities_recycler_view);

        specialitiesRetryButton = findViewById(R.id.specialities_retry_button);
        specialitiesProgressBar = findViewById(R.id.specialities_progress_bar);
        noRecordFoundTextView = findViewById(R.id.specialities_no_record_found_text_view);
    }

    private void setListeners() {
        specialitiesRetryButton.setOnClickListener(this);
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
        allSpecialitiesRecyclerView.setAdapter(allSpecialitiesAdapter);
    }

    private void setSpecialitiesList(NewAllSpecialitiesServerResponse newAllSpecialitiesServerResponse) {
        recentlySearchedSpecialitiesArrayList.addAll(newAllSpecialitiesServerResponse.getData().getSpecialities());
        setRecentlySearchedSpecialitiesRecyclerView(recentlySearchedSpecialitiesArrayList);

        allSpecialitiesArrayList.addAll(newAllSpecialitiesServerResponse.getData().getSpecialities());
        setAllSpeciaitiesRecyclerView(allSpecialitiesArrayList);
    }

    public void setViewsBeforeGettingSpecialitiesData() {
        parentLayout.setVisibility(View.GONE);
        specialitiesProgressBar.setVisibility(View.VISIBLE);
        specialitiesRetryButton.setVisibility(View.GONE);
        noRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingSpecialitiesData() {
        parentLayout.setVisibility(View.VISIBLE);
        specialitiesProgressBar.setVisibility(View.GONE);
        specialitiesRetryButton.setVisibility(View.GONE);
        noRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingSpecialitiesData() {
        parentLayout.setVisibility(View.GONE);
        specialitiesProgressBar.setVisibility(View.GONE);
        specialitiesRetryButton.setVisibility(View.GONE);
        noRecordFoundTextView.setVisibility(View.VISIBLE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingSpecialitiesData() {
        parentLayout.setVisibility(View.GONE);
        specialitiesProgressBar.setVisibility(View.GONE);
        specialitiesRetryButton.setVisibility(View.VISIBLE);
        noRecordFoundTextView.setVisibility(View.GONE);
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
                    Toast.makeText(SearchSpecialityActivity.this, "Tapped Recently Searched:" + position, Toast.LENGTH_SHORT).show();
                    bundle.putParcelable(Speciality.class.getCanonicalName(), recentlySearchedSpecialitiesArrayList.get(position));
                    break;
                case BaseSpecialitiesAdapter.ALL_SPECIALITIES:
                    Toast.makeText(SearchSpecialityActivity.this, "Tapped All :" + position, Toast.LENGTH_SHORT).show();
                    bundle.putParcelable(Speciality.class.getCanonicalName(), recentlySearchedSpecialitiesArrayList.get(position));
                    break;
            }
            MarhamUtils.getInstance().startActivity(SearchSpecialityActivity.this, DoctorListingActivity.class,false,bundle);
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
//        hashMap.put(AppConstants.API.API_KEYS.NEW_ID, "1");

        Call<NewAllSpecialitiesServerResponse> call = apiClient.getSpecialities(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES);
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
        }
    }

    @Override
    public void onFailure(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_ALL_SPECIALITIES:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingSpecialitiesData();
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
        }
    }


}