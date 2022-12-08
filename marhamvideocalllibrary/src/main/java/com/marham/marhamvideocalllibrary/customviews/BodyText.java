package com.marham.marhamvideocalllibrary.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.OnEnableListener;

public class BodyText extends AppCompatTextView {

    private OnEnableListener _enableListner;
    private String fontName;

    public BodyText(Context context, boolean isBold) {
        super(context);

        if (!isInEditMode()) {
            setFont(context, isBold);
        }

    }

    public BodyText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // initialize(context, attrs);

    }

//    private void initialize(Context context, AttributeSet attrs) {
//        if (!isInEditMode()) {
//            String packageName = "http://schemas.android.com/apk/res-auto";
//            fontName = attrs.getAttributeValue(packageName, "fontNameTextView");
//            boolean isBold = attrs.getAttributeBooleanValue(packageName, "fontBoldTextView", true);
//            setFont(context, isBold);
//        }
//
//    }

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

    public void setFont(Context context, boolean isBold) {

        if (fontName == null || fontName.length() == 0) {
            fontName = context.getString(R.string.body_text);
        }
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        if (isBold) {
            setTypeface(tf, Typeface.BOLD);
        } else {
            setTypeface(tf);
        }
    }

}
