package com.marham.marhamvideocalllibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.activities.dashboard.MarhamDashboardActivity;
import com.marham.marhamvideocalllibrary.activities.payment.PaymentActivity;
import com.marham.marhamvideocalllibrary.listeners.payment.MarhamPaymentListener;
import com.marham.marhamvideocalllibrary.model.appointment.Appointment;
import com.marham.marhamvideocalllibrary.model.user.MarhamUser;

@Keep
public class MarhamVideoCallHelper {

    private String client;
    private String API_KEY;
    private String fireBaseToken;
    private String userPhoneNumber;
    private String userName;
    private String deviceID;
    private MarhamUser marhamUser;
    private MarhamPaymentListener marhamPaymentListener;

    private static MarhamVideoCallHelper utils;

    public static MarhamVideoCallHelper getInstance() {
        if (utils == null) return utils = new MarhamVideoCallHelper();
        else return utils;
    }

    public MarhamVideoCallHelper setClient(String client) {
        this.client = client;
        return this;
    }

    public MarhamVideoCallHelper setAPIKEY(String API_KEY) {
        this.API_KEY = API_KEY;
        return this;
    }

    public MarhamVideoCallHelper setFirebaseToken(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
        return this;
    }

    public MarhamVideoCallHelper setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        return this;
    }

    public MarhamVideoCallHelper setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public MarhamVideoCallHelper setMarhamPaymentListener(MarhamPaymentListener marhamPaymentListener) {
        this.marhamPaymentListener = marhamPaymentListener;
        return this;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getClient() {
        return client;
    }


    public String getAuthToken() {
        if (marhamUser != null) {
            return marhamUser.getToken();
        }else{
            return "";
        }
    }

    public MarhamUser getMarhamUser() {
        return marhamUser;
    }

    public void setMarhamUser(MarhamUser marhamUser) {
        this.marhamUser = marhamUser;
    }

    public void launchBookingFlow(Context context) {
        Intent intent = new Intent(context, MarhamDashboardActivity.class);
        context.startActivity(intent);
    }

    public void openPaymentScreen(Activity activity,Appointment appointment){
        Bundle bundle = new Bundle();
        bundle.putSerializable(Appointment.class.getCanonicalName(), appointment);
        MarhamUtils.getInstance().startActivity(activity, PaymentActivity.class, false, bundle);
    }

    public String getUserId() {
        return marhamUser.getId();
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public MarhamPaymentListener getMarhamPaymentListener() {
        return marhamPaymentListener;
    }

}
