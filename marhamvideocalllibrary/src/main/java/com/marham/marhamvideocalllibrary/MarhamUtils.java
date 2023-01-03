package com.marham.marhamvideocalllibrary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.marham.marhamvideocalllibrary.fragments.dialogs.AlertWindowForPhone;
import com.marham.marhamvideocalllibrary.listeners.MakeCallListener;
import com.marham.marhamvideocalllibrary.listeners.RuntimeAndSpecialPermissionsBottomSheetListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;
import com.marham.marhamvideocalllibrary.utils.permission.RuntimePermissionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MarhamUtils {

    private static MarhamUtils ourInstance;

    //Call Marham Helpline
    private AppCompatActivity activity;
    private Context context;
    private View view;

    public static MarhamUtils getInstance() {
        if (ourInstance == null) {
            return ourInstance = new MarhamUtils();
        } else {
            return ourInstance;
        }
    }

    //Dialogs
    public void showConfirmationMessage(Context context, String title, String message, String confirmText, String cancelText, DialogInterface.OnClickListener positiveButtonClickListener, DialogInterface.OnClickListener negativeButtonClickListener, boolean allowTouchOutside) {
        if (context != null) {
            if (!allowTouchOutside) {
                new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(confirmText, positiveButtonClickListener).setNegativeButton(cancelText, negativeButtonClickListener).show().setCanceledOnTouchOutside(false);
            } else {
                new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(confirmText, positiveButtonClickListener).setNegativeButton(cancelText, negativeButtonClickListener).show();
            }
        }
    }

    public void showConfirmationMessage(Context context, String title, String message, String confirmText, String cancelText, DialogInterface.OnClickListener positiveButtonClickListener, DialogInterface.OnClickListener negativeButtonClickListener) {
        if (context != null) {
            new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton(confirmText, positiveButtonClickListener).setNegativeButton(cancelText, negativeButtonClickListener).show();
        }
    }

    public void showAPIResponseMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(Context context, String message, int length) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    //Background
    public void setBackground(Context context, View view, int drawable) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context, drawable));
        } else {
            view.setBackground(ContextCompat.getDrawable(context, drawable));
        }
    }


    //Navigation
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

    public void startActivity(Activity activity, Class c, Boolean killCurrentActivity, Bundle bundle, int flag) {
        Intent intent = new Intent(activity, c);
        intent.putExtras(bundle);
        intent.addFlags(flag);
        activity.startActivity(intent);
        if (killCurrentActivity) {
            activity.finish();
        }
    }

    //Permissions
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

    //Logs
    public void generateLog(String log) {
        Log.d(AppConstants.TAG, log);
    }

    //Unique IDs
    public String getUniqueDeviceID() {
        String uniqueID = MarhamVideoCallHelper.getInstance().getDeviceID();
        if (uniqueID == null) {
            uniqueID = UUID.randomUUID().toString();
            MarhamVideoCallHelper.getInstance().setDeviceID(uniqueID);
        }
        return uniqueID;
    }

    //Call Marham HelpLine
    private void setCallRelatedVariables(AppCompatActivity activity, Context context, View view) {
        this.activity = activity;
        this.context = context;
        this.view = view;
    }

    private void resetCallRelatedVariables() {
        this.activity = null;
        this.context = null;
        this.view = null;
    }

    public void callMarhamHelpline(AppCompatActivity activity, Context context, View view) {
        if (!RuntimePermissionManager.getInstance().checkIfUserHasGrantedCallPermission(context)) {
            setCallRelatedVariables(activity, context, view);
            RuntimePermissionManager.getInstance().showRuntimeAndSpecialPermissions(activity, context, runtimeAndSpecialPermissionsBottomSheetListener, AppConstants.PERMISSIONS.PermissionTypes.CALL_GSM);
        } else {
            makeCall(activity, context, view);
        }
    }

    public void callMarhamHelpline(AppCompatActivity activity, Context context, View view, RuntimeAndSpecialPermissionsBottomSheetListener listener) {
        if (!RuntimePermissionManager.getInstance().checkIfUserHasGrantedCallPermission(context)) {
            setCallRelatedVariables(activity, context, view);
            RuntimePermissionManager.getInstance().showRuntimeAndSpecialPermissions(activity, context, listener, AppConstants.PERMISSIONS.PermissionTypes.CALL_GSM);
        } else {
            makeCall(activity, context, view);
        }
    }

    private void makeCall(AppCompatActivity activity, Context context, View view) {
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Dexter.withContext(context).withPermission(Manifest.permission.CALL_PHONE).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    MarhamUtils.getInstance().showMarhamHelpLineNumbersPopUp(context);
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    Snackbar snackbar = Snackbar.make(view, R.string.msg_on_call_permission_denied, Snackbar.LENGTH_LONG).setAction("Settings", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MarhamUtils.getInstance().openPermissionSetting(activity);
                        }
                    });
                    snackbar.show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        } else {
            MarhamUtils.getInstance().showMarhamHelpLineNumbersPopUp(activity.getApplicationContext());
        }
    }

    public void showMarhamHelpLineNumbersPopUp(Context context) {
        if (!((Activity) context).isFinishing()) {
            AlertWindowForPhone alertWindowForPhone = new AlertWindowForPhone(context, makeCallListener, getMarhamHelplingNumbers(context));
            alertWindowForPhone.show();
        }
    }

    public List<String> getMarhamHelplingNumbers(Context context) {
        List<String> list = new ArrayList<>();
        list.add("+92311-1222398");
        list.add("+92423-2591427");
        return list;
    }

    public void callPhoneNumber(Context context, String number) {
        if (isValidPhoneNumber(number)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(intent);
        }
    }

    public boolean isValidPhoneNumber(String number) {
        return !number.isEmpty() && (number.length() >= 8 && number.length() <= 18);
    }

    private MakeCallListener makeCallListener = new MakeCallListener() {
        @Override
        public void callPhoneNumber(Context context, String phone, int position) {
            MarhamUtils.this.callPhoneNumber(context, phone);
        }
    };

    private RuntimeAndSpecialPermissionsBottomSheetListener runtimeAndSpecialPermissionsBottomSheetListener = new RuntimeAndSpecialPermissionsBottomSheetListener() {
        @Override
        public void onTapAllow(int permissionType) {
            switch (permissionType) {
                case AppConstants.PERMISSIONS.PermissionTypes.CALL_GSM:
                    makeCall(MarhamUtils.this.activity, MarhamUtils.this.context, MarhamUtils.this.view);
                    resetCallRelatedVariables();
                    break;
            }
        }

        @Override
        public void onTapDeny(int permissionType) {
            switch (permissionType) {
                case AppConstants.PERMISSIONS.PermissionTypes.CALL_GSM:
                    resetCallRelatedVariables();
                    break;
            }
        }
    };

    //Color
    public int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(id);
        }
    }

    //
    public void openNotifcationSettingsScreen(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
//for Android 5-7
        intent.putExtra("app_package", context.getPackageName());
        intent.putExtra("app_uid", context.getApplicationInfo().uid);

// for Android 8 and above
        intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        context.startActivity(intent);

    }

}
