package com.marham.marhamvideocalllibrary.activities.doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.BaseActivity;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.model.filter.DoctorListingFilter;

import java.util.ArrayList;
import java.util.List;

public class DoctorListingActivity extends BaseActivity {

    private RecyclerView filterRecyclerView;
    private MyButton filtersTryAgainButton;
    private ProgressBar filtersProgressBar;

    private List<DoctorListingFilter> doctorListingFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_listing);
        initializeViews();
        initVariables();
        setListeners();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (R.id.filters_retry_button == viewId) {
            Toast.makeText(this,"Filters Retry Button",Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews(){
        filterRecyclerView = findViewById(R.id.filters_recycler_view);
        filtersTryAgainButton = findViewById(R.id.filters_retry_button);
        filtersProgressBar = findViewById(R.id.filters_progress_bar);
    }

    private void initVariables(){
        DoctorListingFilter allDoctorsFilter = new DoctorListingFilter();
        allDoctorsFilter.setId("1");
        allDoctorsFilter.setTitle("All");

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
}