package com.marham.marhamvideocallapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.activities.MarhamDashboardActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Main Activity", Toast.LENGTH_SHORT).show();
        MarhamVideoCallHelper.getInstance().setAPIKEY("").setFirebaseToken("");
        MarhamVideoCallHelper.getInstance().launchBookingFlow(this, MarhamDashboardActivity.class);
    }
}