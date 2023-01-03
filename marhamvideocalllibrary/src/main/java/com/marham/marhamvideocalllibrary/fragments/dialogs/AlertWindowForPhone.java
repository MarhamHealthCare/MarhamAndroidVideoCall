package com.marham.marhamvideocalllibrary.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.adapters.gsmcall.PhoneNumbersAdapter;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.listeners.MakeCallListener;

import java.util.List;

public class AlertWindowForPhone extends Dialog implements View.OnClickListener {

    private List<String> phoneNumbers;
    private ListView listviewDoctorNumbers;
    private PhoneNumbersAdapter phoneNumbersAdapter;
    private MakeCallListener makeCallListener;
    private MyImageView crossButton;

    Context context;

    public AlertWindowForPhone(Context context, MakeCallListener makeCallListener, List<String> phoneNumbers) {
        super(context);
        this.phoneNumbers = phoneNumbers;
        this.context = context;
        this.makeCallListener = makeCallListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_show_numbers);

        listviewDoctorNumbers = findViewById(R.id.listview_doctor_numbers);
        phoneNumbersAdapter = new PhoneNumbersAdapter(context, makeCallListener, phoneNumbers);
        listviewDoctorNumbers.setAdapter(phoneNumbersAdapter);
        crossButton = findViewById(R.id.top_cross_icon);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

}
