package com.marham.marhamvideocalllibrary.utils.videocall;

import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibratorClass {

    private static VibratorClass vibratorInstance;
    private Vibrator vibrator;

    private VibratorClass() {
    }

    public static VibratorClass getVibratorInstance() {
        if (vibratorInstance == null) {
            synchronized (VibratorClass.class) {
                return vibratorInstance = new VibratorClass();
            }
        } else {
            return vibratorInstance;
        }
    }

    public void startVibration(int timeDuration, Context context) {
        vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        vibrator.cancel();
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();
                VibrationEffect ve = VibrationEffect.createOneShot(timeDuration,
                        VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(ve, audioAttributes);
            } else {
                vibrator.vibrate(timeDuration);
            }
        }
    }

    public void stopVibration() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

}
