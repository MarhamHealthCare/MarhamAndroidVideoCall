package com.marham.marhamvideocalllibrary.activities.general;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyButton;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView backButton;
    protected ProgressBar progressBar;
    protected MyButton retryButton;
    protected BodyText noRecordFoundTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initializeViews() {
        initializeTopBar();
        progressBar = findViewById(R.id.progress_bar);
        retryButton = findViewById(R.id.retry_button);
        noRecordFoundTextView = findViewById(R.id.no_record_found_text_view);
        setListeners();
    }

    private void setListeners() {
        if (retryButton != null) {
            retryButton.setOnClickListener(this);
        }
    }

    private void initializeTopBar() {
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