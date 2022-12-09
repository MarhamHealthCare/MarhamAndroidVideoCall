package com.marham.marhamvideocallapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;

import com.marham.marhamvideocalllibrary.activities.MarhamDashboardActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button openMarhamAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpButton();
        Toast.makeText(this, "Main Activity", Toast.LENGTH_SHORT).show();

    }

    private void setUpButton() {
        openMarhamAppButton = findViewById(R.id.open_marham_app_button);
        openMarhamAppButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.open_marham_app_button) {
            MarhamVideoCallHelper.getInstance().setAPIKEY("").setFirebaseToken("");
            MarhamVideoCallHelper.getInstance().launchBookingFlow(this, MarhamDashboardActivity.class);
        }


    }

}