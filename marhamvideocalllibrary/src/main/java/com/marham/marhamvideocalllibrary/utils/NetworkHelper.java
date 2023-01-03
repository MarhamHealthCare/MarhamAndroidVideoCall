package com.marham.marhamvideocalllibrary.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NetworkHelper {

    private static NetworkHelper ourInstance;

    public static NetworkHelper getInstance() {

        if (ourInstance == null) {
            return ourInstance = new NetworkHelper();
        } else {
            return ourInstance;
        }

    }

    public void registerNetworkStateListener(Context context, BroadcastReceiver internetStrengthReceiver){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(internetStrengthReceiver, intentFilter);
    }

    ///////.........
    public String getConnectionQuality(Context context) {
        NetworkInfo info = getInfo(context);
        if (info == null || !info.isConnected()) {
            return "UNKNOWN";
        }

        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            int numberOfLevels = 5;
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
            if (level == 2)
                return "POOR";
            else if (level == 3)
                return "MODERATE";
            else if (level == 4)
                return "GOOD";
            else if (level == 5)
                return "EXCELLENT";
            else
                return "UNKNOWN";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkClass = getNetworkClass(getNetworkType(context));
            if (networkClass == 1)
                return "POOR";
            else if (networkClass == 2)
                return "GOOD";
            else if (networkClass == 3)
                return "EXCELLENT";
            else
                return "UNKNOWN";
        } else
            return "UNKNOWN";
    }

    public int getNetworkClass(int networkType) {
        try {
            return getNetworkClassReflect(networkType);
        } catch (Exception ignored) {
        }

        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case 16: // TelephonyManager.NETWORK_TYPE_GSM:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 1;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case 17: // TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                return 2;
            case TelephonyManager.NETWORK_TYPE_LTE:
            case 18: // TelephonyManager.NETWORK_TYPE_IWLAN:
                return 3;
            default:
                return 0;
        }
    }

    private int getNetworkClassReflect(int networkType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getNetworkClass = TelephonyManager.class.getDeclaredMethod("getNetworkClass", int.class);
        if (!getNetworkClass.isAccessible()) {
            getNetworkClass.setAccessible(true);
        }
        return (Integer) getNetworkClass.invoke(null, networkType);
    }

    public int getNetworkType(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected  to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return 1;
                } else return 2;
            }
        }
        return 1;
    }

    public NetworkInfo getInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

}
