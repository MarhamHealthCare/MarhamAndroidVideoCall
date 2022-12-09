package com.marham.marhamvideocalllibrary;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.activities.MarhamDashboardActivity;

@Keep
public class MarhamVideoCallHelper {

    private String API_KEY;
    private String fireBaseToken;
    private String phoneNumber;
    private String name;
    private int colorPrimary;

    private static MarhamVideoCallHelper utils;

    public static MarhamVideoCallHelper getInstance() {
        if (utils == null) return utils = new MarhamVideoCallHelper();
        else return utils;
    }

    public MarhamVideoCallHelper setAPIKEY(String API_KEY) {
        this.API_KEY = API_KEY;
        return this;
    }

    public MarhamVideoCallHelper setFirebaseToken(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
        return this;
    }

    public MarhamVideoCallHelper setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public MarhamVideoCallHelper setName(String name) {
        this.name = name;
        return this;
    }

    public void launchBookingFlow(Context context) {
        Intent intent = new Intent(context, MarhamDashboardActivity.class);
        context.startActivity(intent);
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public void setColorPrimary(int colorPrimary) {
        this.colorPrimary = colorPrimary;
    }
}
