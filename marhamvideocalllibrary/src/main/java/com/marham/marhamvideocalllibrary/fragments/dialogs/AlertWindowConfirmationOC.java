package com.marham.marhamvideocalllibrary.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.listeners.videocall.OCAlertBoxListener;

public class AlertWindowConfirmationOC extends Dialog implements View.OnClickListener {

    private BodyText headingTextView;
    private MyButton cancelButton;
    private MyButton proceedButton;

    private String headingText, cancelButtonText, proceedButtonText;
    private Context context;
    private OCAlertBoxListener listener;
    private int requestType;
    private boolean selfDismiss;


    public AlertWindowConfirmationOC(Context context, OCAlertBoxListener listener, int requestType, String headingText, String cancelButtonText, String proceedButtonText, boolean selfDismiss) {
        super(context);

        this.headingText = headingText;
        this.cancelButtonText = cancelButtonText;
        this.proceedButtonText = proceedButtonText;
        this.context = context;
        this.listener = listener;
        this.requestType = requestType;
        this.selfDismiss = selfDismiss;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_window_confirmation_oc);
        initGui();
        setListeners();

    }

    private void initGui() {
        headingTextView = findViewById(R.id.heading_text_view);
        cancelButton = findViewById(R.id.cancel_button);
        proceedButton = findViewById(R.id.proceed_button);

        headingTextView.setText(headingText);
        cancelButton.setText(cancelButtonText);
        proceedButton.setText(proceedButtonText);
    }

    private void setListeners() {
        cancelButton.setOnClickListener(this);
        proceedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (R.id.cancel_button == viewId) {
            this.dismissDialog();
            listener.onCancelled(requestType);
        } else if (R.id.proceed_button == viewId) {
            this.dismissDialog();
            listener.onProcees(requestType);
        }

    }

    private void dismissDialog() {
        if (selfDismiss) {
            dismiss();
        }
    }

}
