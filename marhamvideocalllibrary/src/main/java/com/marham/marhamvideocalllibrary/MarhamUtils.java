package com.marham.marhamvideocalllibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.marham.marhamvideocalllibrary.utils.AppConstants;

import java.util.HashMap;

public class MarhamUtils {

    private static MarhamUtils ourInstance;

    public static MarhamUtils getInstance() {
        if (ourInstance == null) {
            return ourInstance = new MarhamUtils();
        } else {
            return ourInstance;
        }
    }

    public void setBackground(Context context, View view, int drawable) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context, drawable));
        } else {
            view.setBackground(ContextCompat.getDrawable(context, drawable));
        }
    }

    public void showAPIResponseMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(Context context, String message, int length) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Activity activity, Class c, Boolean killCurrentActivity) {
        Intent intent = new Intent(activity, c);
        activity.startActivity(intent);
        if (killCurrentActivity) {
            activity.finish();
        }
    }

    public void startActivity(Activity activity, Class c, Boolean killCurrentActivity, Bundle bundle) {
        Intent intent = new Intent(activity, c);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        if (killCurrentActivity) {
            activity.finish();
        }
    }

    public void openPermissionSetting(AppCompatActivity activity) {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    public HashMap<String, String> getPermissionsList(Context context) {
        HashMap<String, String> permissionsHashMap = new HashMap<>();
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
            permissionsHashMap.put(context.getResources().getString(R.string.microphone), AppConstants.PERMISSIONS.PERMISSION_ALLOWED);
        } else {
            permissionsHashMap.put(context.getResources().getString(R.string.microphone), AppConstants.PERMISSIONS.PERMISSION_NOT_ALLOWED);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            permissionsHashMap.put(context.getResources().getString(R.string.camera), AppConstants.PERMISSIONS.PERMISSION_ALLOWED);
        } else {
            permissionsHashMap.put(context.getResources().getString(R.string.camera), AppConstants.PERMISSIONS.PERMISSION_NOT_ALLOWED);
        }
        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            permissionsHashMap.put(context.getResources().getString(R.string.notification), AppConstants.PERMISSIONS.PERMISSION_ALLOWED);
        } else {
            permissionsHashMap.put(context.getResources().getString(R.string.notification), AppConstants.PERMISSIONS.PERMISSION_NOT_ALLOWED);
        }
        return permissionsHashMap;
    }

}
