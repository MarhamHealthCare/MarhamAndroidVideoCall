package com.marham.marhamvideocalllibrary.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.marham.marhamvideocalllibrary.R;

public class MarhamDashboardActivity extends AppCompatActivity {

    public static final String TAG = "Marham Video Call: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpPushNotifications();
    }

    private void setUpPushNotifications(){
        // Declare the launcher at the top of your Activity/Fragment:
        final ActivityResultLauncher<String> requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        // FCM SDK (and your app) can post notifications.
                    } else {
                        // TODO: Inform user that that your app will not show notifications.
                    }
                });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e(TAG, token);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MarhamDashboardActivity.this,token,Toast.LENGTH_SHORT).show();
                    }
                });
    }

}