package com.marham.marhamvideocallapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;

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
            MarhamVideoCallHelper.getInstance().setClient("telenor").setAPIKEY("$2y$10$UTa82jp.SycYYIc0wUn9r.2aqWEluFknWhki2Aooh3taGNsry3oA6").setFirebaseToken("fRHe-d--TkqGplJG_tgPJP:APA91bE9v2J6hop0mcaVx7wLYzT7n1iQRtTlNBaw-wxoEk9Wo4VvAlsnx0FJYYPEM8-q8CibWuo7Lpz4CczqalmSOKUsxS-mqQ4yDc4u-ZT5zg9fCuev56SYfRcq53Tt1tH94VHvHrhe").setUserPhoneNumber("+923334794867").setUserName("Wazzah");
            MarhamVideoCallHelper.getInstance().launchBookingFlow(this);
        }
    }

}