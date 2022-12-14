package com.marham.marhamvideocalllibrary.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

public class MyImageView extends AppCompatImageView {

    /**
     * @param context
     */
    public MyImageView(Context context) {
        super(context);

        setBackgroundColor(0xFFFFFF);
    }

    /**
     * @param context
     * @param attrs
     */
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        System.out.println("Painting content");
        Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setColor(0x0);
        paint.setTextSize(12.0F);
        System.out.println("Drawing text");
        canvas.drawText("Hello World in custom view", 100, 100, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("Hello Android", "Got a touch event: " + event.getAction());
        return super.onTouchEvent(event);

    }

}
