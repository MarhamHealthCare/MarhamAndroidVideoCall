package com.marham.marhamvideocalllibrary;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.activities.MarhamDashboardActivity;

@Keep
public class MarhamVideoCallHelper {

    private String API_KEY;
    private String fireBaseToken = "fRHe-d--TkqGplJG_tgPJP:APA91bE9v2J6hop0mcaVx7wLYzT7n1iQRtTlNBaw-wxoEk9Wo4VvAlsnx0FJYYPEM8-q8CibWuo7Lpz4CczqalmSOKUsxS-mqQ4yDc4u-ZT5zg9fCuev56SYfRcq53Tt1tH94VHvHrhe";
    private String phoneNumber;
    private String name;
    private String userId = "140060";

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }
}
