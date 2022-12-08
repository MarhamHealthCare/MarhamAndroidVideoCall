package com.marham.marhamvideocalllibrary.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.marham.marhamvideocalllibrary.R;

public class MarhamDashboardActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "Marham Video Call: ";
    private CardView searchCardView;
    private ConstraintLayout myAppointmentsViewsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marham_dashboard);
        initializeViews();
        setListeners();
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
    }

    private void setListeners() {
        searchCardView.setOnClickListener(this);
        myAppointmentsViewsContainer.setOnClickListener(this);
    }


}