package com.marham.marhamvideocalllibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

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

    public void startActivity(Activity activity, Class c, Boolean killCurrentActivity) {
        Intent intent = new Intent(activity, c);
        activity.startActivity(intent);
        if (killCurrentActivity) {
            activity.finish();
        }
    }

    public void startActivity(Activity activity, Class c, Boolean killCurrentActivity, Bundle bundle) {
        Intent intent = new Intent(activity, c);
        activity.startActivity(intent);
        intent.putExtras(bundle);
        if (killCurrentActivity) {
            activity.finish();
        }
    }

}
