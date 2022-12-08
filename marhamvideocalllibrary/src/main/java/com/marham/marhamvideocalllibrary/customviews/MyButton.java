package com.marham.marhamvideocalllibrary.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.marham.marhamvideocalllibrary.listeners.OnEnableListener;

public class MyButton extends AppCompatButton {

    private OnEnableListener _enableListner;
    private String fontName;

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context, attrs);

    }

    private void initialize(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            String packageName = "http://schemas.android.com/apk/res-auto";
            fontName = attrs.getAttributeValue(packageName, "fontNameTextView");
            setFont(context);
        }

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (_enableListner != null) {
            _enableListner.onEnable(enabled);
        }
    }


    public void setOnEnableListner(OnEnableListener enableListner) {
        _enableListner = enableListner;
    }

    public void setFont(Context context) {
//        if (fontName == null || fontName.length() == 0) {
//            fontName = context.getString(R.string.body_text);
//        }
//        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
//
//
//        setTypeface(tf);
    }

}
