package com.marham.marhamvideocalllibrary;

import android.content.Context;
import android.content.Intent;

public class MarhamVideoCallHelper {

    private String API_KEY;
    private String fireBaseToken;

    static MarhamVideoCallHelper utils = null;

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

    public void launchBookingFlow(Context context, Class c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getFireBaseToken() {
        return fireBaseToken;
    }
}
