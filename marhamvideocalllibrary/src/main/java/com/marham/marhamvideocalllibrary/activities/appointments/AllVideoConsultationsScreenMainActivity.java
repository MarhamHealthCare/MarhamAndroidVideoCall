package com.marham.marhamvideocalllibrary.activities.appointments;

import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.BaseActivity;

public class AllVideoConsultationsScreenMainActivity extends BaseActivity {

    private ConstraintLayout parentLayout;

    private ConstraintLayout upcomingAppointmentsViewsContainer;
    private RecyclerView upcomingAppointmentsRecyclerView;

    private ConstraintLayout previousAppointmentsViewsContainer;
    private RecyclerView previousAppointmentsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_video_consultations_screen_main);
        initializeViews();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    protected void initializeViews() {
        super.initializeViews();
        parentLayout = findViewById(R.id.parent_layout);

        upcomingAppointmentsViewsContainer = findViewById(R.id.upcoming_appointments_views_container);
        upcomingAppointmentsRecyclerView = findViewById(R.id.upcoming_appointment_recycler_view);

        previousAppointmentsViewsContainer = findViewById(R.id.previous_appointments_views_container);
        previousAppointmentsRecyclerView = findViewById(R.id.previous_appointment_recycler_view);

    }

}