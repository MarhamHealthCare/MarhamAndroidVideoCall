package com.marham.marhamvideocalllibrary.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.marham.marhamvideocalllibrary.R;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base_main);
    }

    protected void initializeTopBar() {
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (R.id.back_button == viewId) {
            finish();
        }
    }
}