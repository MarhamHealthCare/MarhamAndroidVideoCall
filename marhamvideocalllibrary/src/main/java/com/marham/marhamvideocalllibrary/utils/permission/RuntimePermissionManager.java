package com.marham.marhamvideocalllibrary.utils.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.marham.marhamvideocalllibrary.fragments.RuntimeAndSpecialPermissionsBottomSheet;
import com.marham.marhamvideocalllibrary.listeners.RuntimeAndSpecialPermissionsBottomSheetListener;

public class RuntimePermissionManager {

    private static RuntimePermissionManager ourInstance;

    public static RuntimePermissionManager getInstance() {
        if (ourInstance == null) {
            return ourInstance = new RuntimePermissionManager();
        } else {
            return ourInstance;
        }
    }

    public void showRuntimeAndSpecialPermissions(FragmentActivity activity, Context context, RuntimeAndSpecialPermissionsBottomSheetListener listener, int permissionType) {
        RuntimeAndSpecialPermissionsBottomSheet runtimeAndSpecialPermissionsBottomSheet = new RuntimeAndSpecialPermissionsBottomSheet(context, listener, permissionType);
        runtimeAndSpecialPermissionsBottomSheet.setCancelable(false);
        runtimeAndSpecialPermissionsBottomSheet.show(activity.getSupportFragmentManager(), "runtimeAndSpecialPermissionsBottomSheet");
    }

    public boolean checkIfUserHasGrantedCameraAndMicrophonePermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkIfUserHasGrantedCallPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

}
