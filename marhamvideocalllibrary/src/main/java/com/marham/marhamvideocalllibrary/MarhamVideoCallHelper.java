package com.marham.marhamvideocalllibrary;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.marham.marhamvideocalllibrary.activities.MarhamDashboardActivity;

public class MarhamVideoCallHelper {

    public static void initHelper(Context context) {
        Toast.makeText(context, "MarhamVideoCallHelper Initialized ...", Toast.LENGTH_SHORT).show();
        launchBookingFlow(context, MarhamDashboardActivity.class);
    }

    public static void launchBookingFlow(Context context, Class c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);


    }

}
