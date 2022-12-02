package com.marham.marhamvideocallapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
import com.marham.marhamvideocalllibrary.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"Main Activity",Toast.LENGTH_SHORT).show();
        MarhamVideoCallHelper.initHelper(this);
    }
}