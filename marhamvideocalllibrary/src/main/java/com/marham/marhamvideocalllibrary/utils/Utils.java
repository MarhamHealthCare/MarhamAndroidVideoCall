package com.marham.marhamvideocalllibrary.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class Utils {

    private static Utils ourInstance;

    public static Utils getInstance() {
        if (ourInstance == null) {
            return ourInstance = new Utils();
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

    public void showAPIResponseMessage(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
