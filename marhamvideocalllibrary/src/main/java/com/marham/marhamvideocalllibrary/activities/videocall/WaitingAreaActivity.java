package com.marham.marhamvideocalllibrary.activities.videocall;

import static android.icu.lang.UProperty.INT_START;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.appointments.AllVideoConsultationsScreenMainActivity;
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.listeners.RuntimeAndSpecialPermissionsBottomSheetListener;
import com.marham.marhamvideocalllibrary.model.appointment.Appointment;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.notification.ServerResponsePushNotification;
import com.marham.marhamvideocalllibrary.model.notification.UpdateDeviceTokenServerResponse;
import com.marham.marhamvideocalllibrary.model.videoconsultation.TokenAndRoom;
import com.marham.marhamvideocalllibrary.model.videoconsultation.TokenAndRoomServerResponse;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;
import com.marham.marhamvideocalllibrary.utils.NetworkHelper;
import com.marham.marhamvideocalllibrary.utils.permission.RuntimePermissionManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.twilio.video.ConnectOptions;
import com.twilio.video.LocalParticipant;
import com.twilio.video.RemoteParticipant;
import com.twilio.video.Room;
import com.twilio.video.TwilioException;
import com.twilio.video.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;


public class WaitingAreaActivity extends BaseActivity implements RuntimeAndSpecialPermissionsBottomSheetListener, ServerConnectListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout disabledView;

    private ConstraintLayout parenLayout;
    private CircleImageView doctorImageView1;
    private BodyText doctorNameTextView1;

    private BodyText drSpecialityTextView1;

    private BodyText drSpecialityTextView2;

    private CircleImageView doctorImageView2;
    private BodyText doctorNameTextView2;
    private ConstraintLayout timerTextViewsContainer;

    private CircularProgressIndicator circularProgressIndicator;

    private BodyText instructionsTextView;

    private MyButton newCallButton;

    private CardView callRequestViewsContainer;
    private BodyText callStatusTextView;

    private BodyText time;
    private BodyText timeLeftTextView;
    private BodyText daysHoursMinsTextView;

    private View loadingDots;
    private MyButton endCallRequestButton;

    private MyImageView internetConnectionStatusImageView;
    private BodyText internetConnnectionStatusTextView;

    private Appointment appointment;
    private String token;

    private List<String> instructionsList = new ArrayList<>();
    private List<String> instructionsListBeforeCall = new ArrayList<>();
    private List<String> instructionsListAfterCall = new ArrayList<>();
    private int instructionsListIndexToShow = 0;

    int delayInMillis = 7000;

    private static final int CAMERA_MIC_PERMISSION_REQUEST_CODE = 1;
    private BodyText docNameInPopUp;
    private MyButton acceptCallButtonPopUp;
    private MyButton rejectCallButtonPopUp;
    private String remoteParticipantIdentity;
    private Room room;
    private String roomString;

    private RetroFit2Callback<ServerResponseOld> retroFit2Callback;
    private Timer missedCallTimer;

    public static final boolean USE_TOKEN_SERVER = false;
    private boolean isDoctorOnline = false;
    private boolean canAutoConnect = false;
    private long currentTime = 1527145082611L; //6 Seconds Behind

    private LocalParticipant localParticipant;
//    private long apptTime = currentTime + 86400000; //1 day added
    private long apptTime = currentTime + 8640;// 8 Secs

    long patientWaitingTime = currentTime - apptTime;
    long maxWaitingTime = 600000L;//10 Minutes

    private CountDownTimer timerBeforeCall;
    private Timer waitingForPatientTimer;
    private Timer lastSeenTimer;

    private MyImageView backBtn;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private String timerText;
    private String daysHoursMinsText;
    private String leftText;
    private MediaPlayer mMediaPlayer;
    private boolean denyButtonTapped;

    private AlertDialog.Builder alertDialog;
    private HashMap<String, String> permissionsList = new HashMap<>();
    private boolean showOptionsDialog = false;

    private Runnable runnableCode;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_area);
        initGui();
        initVariables();
        setListeners();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void requestPermissionForCameraAndMicrophone() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                CAMERA_MIC_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                getTokenFromServer();
            } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                denyButtonTapped = true;
                if (!((Activity) WaitingAreaActivity.this).isFinishing()) {
                    MarhamUtils.getInstance().showConfirmationMessage(WaitingAreaActivity.this, "Camera !", "For video call with doctor you need to allow camera permission.", "Settings", "cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MarhamUtils.getInstance().openPermissionSetting(WaitingAreaActivity.this);
                            dialog.dismiss();
                            finish();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }, false);
                }

            } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                denyButtonTapped = true;
                if (!((Activity) WaitingAreaActivity.this).isFinishing()) {
                    MarhamUtils.getInstance().showConfirmationMessage(WaitingAreaActivity.this, "Microphone !", "For video call with doctor you need to allow microphone permission.", "Settings", "cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MarhamUtils.getInstance().openPermissionSetting(WaitingAreaActivity.this);
                            dialog.dismiss();
                            finish();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();

                        }
                    }, false);
                }
            }
        }
        permissionsList = MarhamUtils.getInstance().getPermissionsList(this);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleNotificationTypeResponse((ServerResponsePushNotification) intent.getExtras().getParcelable(ServerResponsePushNotification.class.getCanonicalName()));
        }
    };

    private void handleNotificationTypeResponse(ServerResponsePushNotification notification) {
        switch (notification.getNotification_type()) {
            case AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.DOCTOR_REQUESTED_CALL:
                playRingTone();
                break;

            case AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.DOCTOR_ACCEPTED_CALL:
                stopRingTone();
                startLiveStreamingActivity();
                break;

            case AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.DOCTOR_REJECTED_CALL:
                AppConstants.isCallAlreadyInProgress = false;
                Toast.makeText(this, "Doctor Rejected the call or He/she is already on call", Toast.LENGTH_SHORT).show();
                onEndCallRequest();
                break;

        }
    }

    private void startLiveStreamingActivity() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Appointment.class.getCanonicalName(), appointment);
        MarhamUtils.getInstance().startActivity(this, VideoCallActivity.class, true, bundle);
    }

    private void onEndCallRequest() {
        hideCallRequestViewsContainer();
        stopRingTone();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDeviceToken();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(AppConstants.NOTIFICATIONS.LOCAL_NOTIFICATIONS.DOCTOR_REQUESTED_CALL));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(AppConstants.NOTIFICATIONS.LOCAL_NOTIFICATIONS.DOCTOR_ACCEPTED_CALL));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(AppConstants.NOTIFICATIONS.LOCAL_NOTIFICATIONS.DOCTOR_REJECTED_CALL));
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            MarhamUtils.getInstance().showConfirmationMessage(this, "Notifications Permission Required !", "Please enable notifications for the call to start.", "Settings", "Cancel", goToSettingsClickerListener, cancelClickListener);
        } else {
            startLastSeenAPILoop();
            if (denyButtonTapped) {
            } else {
                handleCameraAndMicrophonePermission();
            }
        }
        NetworkHelper.getInstance().registerNetworkStateListener(this, internetStrengthReceiver);
    }

    private void handleCameraAndMicrophonePermission() {
        if (!RuntimePermissionManager.getInstance().checkIfUserHasGrantedCameraAndMicrophonePermission(this)) {
            RuntimePermissionManager.getInstance().showRuntimeAndSpecialPermissions(this, this, this, AppConstants
                    .PERMISSIONS.PermissionTypes.CAMERA_AND_GALLERY_PERMISSION);
        } else {
            getTokenFromServer();
        }
    }

    @Override
    protected void onPause() {
        AppConstants.isCallAlreadyInProgress = false;
        denyButtonTapped = false;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

        if (room != null) {
            room.disconnect();
        }
        if (alertDialog != null) {
            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
        }

        stopUpdateTextLoop();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (alertDialog != null) {
            alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
        }
        stopAllTimers();
        super.onDestroy();
    }

    public void onClick(View view) {
        int viewId = view.getId();

        if (R.id.back_button == viewId) {
            onBackPressed();
        }
//        else if (R.id.reject_call_button_pop_up == viewId) {
//            dialog.dismiss();
//            onCallRejected();
//            dialog.setCanceledOnTouchOutside(true);
//        } else if (R.id.accept_call_button_pop_up == viewId) {
//            dialog.dismiss();
//            onCallAccepted();
//            dialog.setCanceledOnTouchOutside(true);
//        }
        else if (R.id.new_call_button == viewId) {
            if (callRequestViewsContainer.getVisibility() == View.VISIBLE) {
            } else {
                if (canAutoConnect) {
                    AppConstants.isCallAlreadyInProgress = true;
                    showCallRequestViewsContainer();
                    startMissedCallTimer();
                } else {
                    Toast.makeText(this, "You will be able to call doctor once your appointment time starts", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (R.id.end_call_request_button == viewId) {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            AppConstants.isCallAlreadyInProgress = false;
            onEndCallRequestByDoctor();
            sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.MISSED_CALL);
        }

    }

    private void startMissedCallTimer() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRingTone();
                sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.MISSED_CALL);
                callRequestViewsContainer.setVisibility(View.GONE);
                handler.removeCallbacksAndMessages(null);
                if (appointment.getIsCallMyDoctor() != null && !appointment.getIsCallMyDoctor().isEmpty()) {
                    if (appointment.getIsCallMyDoctor().equals("1") && appointment.getAppointmentSubStatusID().equals(Appointment.SCHEDULE)) {
                        handleAlertBoxWhenDoctorFailedToPickUpTheCall();
                    } else {
                        handleAlertBoxWhenDoctorFailedToPickUpTheCall();
                    }
                } else {
                    handleAlertBoxWhenDoctorFailedToPickUpTheCall();
                }
            }
        }, 30000);
    }

    public void showConfirmationMessage(Context context, String title, String message, String
            confirmText, String cancelText, DialogInterface.OnClickListener
                                                positiveButtonClickListener, DialogInterface.OnClickListener negativeButtonClickListener,
                                        boolean allowTouchOutside) {
        if (context != null) {
            alertDialog = new AlertDialog.Builder(this);
            if (!allowTouchOutside) {
                alertDialog.setTitle(title).setMessage(message).setPositiveButton(confirmText, positiveButtonClickListener).setNegativeButton(cancelText, negativeButtonClickListener).show().setCanceledOnTouchOutside(false);
            } else {
                alertDialog.setTitle(title).setMessage(message).setPositiveButton(confirmText, positiveButtonClickListener).setNegativeButton(cancelText, negativeButtonClickListener).show();
            }
        }
    }

    private void handleAlertBoxWhenDoctorFailedToPickUpTheCall() {
        int unicode = 0x1F60A;
        String emoji = String.valueOf(Character.toChars(unicode));
        if (!((Activity) WaitingAreaActivity.this).isFinishing()) {
            showConfirmationMessage(WaitingAreaActivity.this, "Doctor didn't pick up your call ?", "Let us Help you " + emoji, "Call our Helpline", "", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MarhamUtils.getInstance().callMarhamHelpline(WaitingAreaActivity.this, WaitingAreaActivity.this, swipeRefreshLayout);
                    dialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }, true);
        }
        disabledView.setVisibility(View.GONE);
    }

    private void onEndCallRequestByDoctor() {
        hideCallRequestViewsContainer();
        stopRingTone();
        sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.PATIENT_CANCELED_CALL);
    }


    private void hideCallRequestViewsContainer() {
        disabledView.setVisibility(View.GONE);
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        callRequestViewsContainer.setVisibility(View.GONE);
    }

    private void showCallRequestViewsContainer() {
        disabledView.setVisibility(View.VISIBLE);
        callRequestViewsContainer.setVisibility(View.VISIBLE);
        playRingTone();
        sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.PATIENT_REQUEST_CALL);
    }

    private void stopAllTimers() {
        stopTimerBeforeCallTime();
        stopTimerAfterCallTime();
        stopLastSceneAPIupdate();
    }

    private void initGui() {
        backBtn = findViewById(R.id.back_button);

        disabledView = findViewById(R.id.disabled_view);

        parenLayout = findViewById(R.id.parent_layout);
        doctorImageView1 = findViewById(R.id.doctor_image_view_1);
        doctorNameTextView1 = findViewById(R.id.doctor_name_text_view_1);
        drSpecialityTextView1 = findViewById(R.id.doctor_speciality_text_view_1);

        drSpecialityTextView2 = findViewById(R.id.doctor_speciality_text_view_2);

        doctorImageView2 = findViewById(R.id.doctor_image_view_2);
        doctorNameTextView2 = findViewById(R.id.doctor_name_text_view_2);
        timerTextViewsContainer = findViewById(R.id.timer_text_views_container);

        newCallButton = findViewById(R.id.new_call_button);

        instructionsTextView = findViewById(R.id.instructions_text_view);

        callRequestViewsContainer = findViewById(R.id.call_request_views_container);
        callStatusTextView = findViewById(R.id.call_status_text_view);
        loadingDots = findViewById(R.id.loading_dots);
        endCallRequestButton = findViewById(R.id.end_call_request_button);

        timeLeftTextView = findViewById(R.id.time_left_text_view);
        daysHoursMinsTextView = findViewById(R.id.days_hours_mins_text_view);
        time = findViewById(R.id.time);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        circularProgressIndicator = findViewById(R.id.circular_progress_indicator);

        callRequestViewsContainer = findViewById(R.id.call_request_views_container);

        internetConnectionStatusImageView = findViewById(R.id.internet_connection_status_image_view);
        internetConnnectionStatusTextView = findViewById(R.id.internet_connnection_status_text_view);


        swipeRefreshLayout.setColorSchemeColors(MarhamUtils.getInstance().getColor(this, R.color.colorPrimary));
        swipeRefreshLayout.setEnabled(false);

        try {
            doctorNameTextView1.setText(appointment.getDocName());
            doctorNameTextView2.setText(appointment.getDocName());
        } catch (Exception e) {
            doctorNameTextView1.setText(getString(R.string.doctor));
            doctorNameTextView2.setText(getString(R.string.doctor));
        }

    }

    private void initVariables() {
        receiveIntent();
        if (appointment != null && appointment.getApptTimeInMiliSeconds() != null) {
//            apptTime = Long.parseLong(appointment.getApptTimeInMiliSeconds());
        } else {
            WaitingAreaActivity.this.finish();
        }
        denyButtonTapped = false;

        instructionsListBeforeCall.add("You cannot call doctor before scheduled time.\nThis service is not for emergency use.");
        instructionsListAfterCall.add("Don't worry! We're here to help.");
        instructionsListAfterCall.add("You can call our helpline if anything goes wrong.");
        instructionsListAfterCall.add("The doctor might be on another call. Please wait for 10-15 minutes.");
    }

    public void receiveIntent() {
        appointment = (Appointment) getIntent().getSerializableExtra(Appointment.class.getCanonicalName());
    }

    private void setListeners() {
        backBtn.setOnClickListener(this);
//        holder.getHelplineButton().setOnClickListener(this);
//        holder.getNewCallButtonContainer().setOnClickListener(this);
        newCallButton.setOnClickListener(this);
        endCallRequestButton.setOnClickListener(this);
    }

    public void setRoomData(TokenAndRoom tokenAndRoom) {
        token = tokenAndRoom.getToken();
//        currentTime = Long.parseLong(tokenAndRoom.getCurrent_time_miliseconds());
        connectToRoom(tokenAndRoom.getRoom());
        swipeRefreshLayout.setRefreshing(false);
    }

    public void setInstructionsTextViewTextColor(int color) {
        instructionsTextView.setTextColor(ContextCompat.getColor(this, color));
    }

    public void updateInstructionsTextViewText(String text, int typeface) {
        instructionsTextView.setTypeface(null, typeface);
        instructionsTextView.setText(text);
    }

    public void identifyScenarioAndTakeAction() {
        setDoctorInfo();
        if (apptTime < currentTime) {
            canAutoConnect = true;
            setInstructionListAfterCallTime();
            showTimerAfterCallTime();
        } else if (apptTime > currentTime) {
            setInstructionListBeforeCallTime();
            setViewsBeforeCalling();
            showTimerBeforeCallTime();
        }
        startUpdateTextLoop();
    }

    private void setViewsBeforeCalling() {
        circularProgressIndicator.setVisibility(View.VISIBLE);
        timerTextViewsContainer.setVisibility(View.VISIBLE);
        newCallButton.setVisibility(View.GONE);

    }

    private void setDoctorInfo() {
        doctorNameTextView1.setText(appointment.getDoctorName());
        doctorNameTextView2.setText(appointment.getDoctorName());
        doctorNameTextView1.setText(appointment.getSpeciality());
        doctorNameTextView2.setText(appointment.getSpeciality());

        Picasso.get().load(appointment.getDoctorImageUrl()).into(doctorImageView1, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                doctorImageView1.setImageResource(R.drawable.m_doctor_placholder);
            }
        });
        Picasso.get().load(appointment.getDoctorImageUrl()).into(doctorImageView2, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                doctorImageView2.setImageResource(R.drawable.m_doctor_placholder);
            }
        });

        time.setText(appointment.getFormattedTime() + ", " + appointment.getFormattedDate());
    }

    private void showTimerBeforeCallTime() {
        circularProgressIndicator.setProgress(100);
        circularProgressIndicator.setMax(100);
        long millisToGo = apptTime - currentTime;

        if (timerBeforeCall == null) {
            timerBeforeCall = new CountDownTimer(millisToGo, 1000) {

                @Override
                public void onTick(long millis) {
                    int seconds = (int) (millis / 1000) % 60;
                    int minutes = (int) ((millis / (1000 * 60)) % 60);
                    int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
                    int days = (int) (millis / (60 * 60 * 24 * 1000));

                    if (days > 0) {
                        timerText = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
                        daysHoursMinsText = "Days   Hrs  Min   Sec";
                        leftText = "Left";
                    } else if (hours > 0) {
                        timerText = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                        daysHoursMinsText = "Hrs  Min  Sec";
                        leftText = "Left";
                    } else if (minutes > 0) {
                        timerText = String.format("%02d:%02d", minutes, seconds);
                        daysHoursMinsText = "Min Sec";
                        leftText = "Left";
                    } else {
                        timerText = String.format("%02d", seconds);
                        daysHoursMinsText = "Sec";
                        leftText = "Left";
                    }
                    updateInfoViews(timerText, daysHoursMinsText, leftText);
                }

                @Override
                public void onFinish() {
                    currentTime = apptTime;
                    setInstructionListAfterCallTime();
                    showTimerAfterCallTime();
                    canAutoConnect = true;
                    autoConnectCallWhenDoctorHasArrived();
                }
            }.start();
        }
    }

    private void stopTimerBeforeCallTime() {
        if (timerBeforeCall != null) {
            timerBeforeCall.cancel();
        }
    }

    private void showTimerAfterCallTime() {
        patientWaitingTime = currentTime - apptTime;

        circularProgressIndicator.setVisibility(View.VISIBLE);

        timerTextViewsContainer.setVisibility(View.GONE);
        newCallButton.setVisibility(View.VISIBLE);

        drSpecialityTextView1.setText(appointment.getCatName());
        drSpecialityTextView2.setText(appointment.getCatName());

        autoConnectCallWhenDoctorHasArrived();
    }

    private void stopTimerAfterCallTime() {
        if (waitingForPatientTimer != null) {
            waitingForPatientTimer.cancel();
        }
    }

    private void startLastSeenAPILoop() {
        lastSeenTimer = new Timer();
        lastSeenTimer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e(AppConstants.TAG, "Updating Last Seen");
                        updateLastSeenTimeOfUser();
                    }

                });
            }

        }, 0, 30000);

    }

    private void stopLastSceneAPIupdate() {
        if (lastSeenTimer != null) {
            lastSeenTimer.cancel();
        }
    }

    private void updateInfoViews(String timerText, String daysHoursSecsText, String leftText) {
        if (timerText != null) {
            timeLeftTextView.setText(timerText);
        }
        if (daysHoursSecsText != null) {
            daysHoursMinsTextView.setText(daysHoursSecsText);
        }
//        if (leftText != null) {
//            timeLeftTextView.setText(leftText);
//        }
    }

    private void playRingTone() {
        try {
            mMediaPlayer = MediaPlayer.create(WaitingAreaActivity.this, R.raw.ring_call);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.start();

        } catch (Exception e) {
        }
    }

    private void stopRingTone() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }

    }

    public void showLeaveButton() {
//        infoTextView.setText(R.string.waited_too_long);
//        leaveButton.setVisibility(View.VISIBLE);
    }

    public void hideLeaveButton() {
//        infoTextView.setText(R.string.leaving_call);
//        leaveButton.setVisibility(View.GONE);
    }

    public void startLiveStreamingActivity(String token) {
        appointment.setToken(token);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Appointment.class.getCanonicalName(), appointment);
        bundle.putString("roomKey", roomString);
        bundle.putString("token", token);
        MarhamUtils.getInstance().startActivity(this, VideoCallActivity.class, true, bundle);

    }

    public void openUserPanelActivity() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Appointment.class.getCanonicalName(), appointment);
        MarhamUtils.getInstance().startActivity(this, AllVideoConsultationsScreenMainActivity.class, false, bundle, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public void makeCallButtonClickable() {
        newCallButton.setEnabled(true);
    }

//    private void showCallRequestPopUp() {
//        initDialogBox();
//        builder.setView(view);
//        dialog = builder.create();
//        dialog.show();
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
//            dialog.setCancelable(false);
//            return true;
//        });
//    }

    private void initDialogBox() {
//        builder = new AlertDialog.Builder(WaitingAreaActivity.this);
//        view = LayoutInflater.from(WaitingAreaActivity.this).inflate(R.layout.call_request_layout, null);
//        docNameInPopUp = view.findViewById(R.id.doc_name_in_pop_up);
//        acceptCallButtonPopUp = view.findViewById(R.id.accept_call_button_pop_up);
//        rejectCallButtonPopUp = view.findViewById(R.id.reject_call_button_pop_up);
//        acceptCallButtonPopUp.setOnClickListener(this);
//        rejectCallButtonPopUp.setOnClickListener(this);
//        docNameInPopUp.setText(presenter.getAppointment().getDoctorName() + " is calling...");

    }

    private void onCallAccepted() {
        if (dialog != null) {
            dialog.dismiss();
        }
//        holder.getInfoTextView().setText("Connecting ...");
        stopRingTone();
        callRequestViewsContainer.setVisibility(View.GONE);
        stopAllTimers();
        sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.PATIENT_ACCPETED_CALL);
        startLiveStreamingActivity(token);
    }


    private void onCallRejected() {
        stopRingTone();
        sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.PATIENT_REJECTED_CALL);
        callRequestViewsContainer.setVisibility(View.GONE);
    }

    private void onCallCancelled() {
        stopRingTone();
        callRequestViewsContainer.setVisibility(View.GONE);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private DialogInterface.OnClickListener goToSettingsClickerListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            MarhamUtils.getInstance().openNotifcationSettingsScreen(WaitingAreaActivity.this);
            finish();

        }
    };

    private DialogInterface.OnClickListener cancelClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finish();
        }
    };

    private void setViewsWhenDoctorIsOnline() {
        isDoctorOnline = true;

    }

    /// Alert Window Click Listener
    private DialogInterface.OnClickListener leaveCallListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            stopAllTimers();
            hideLeaveButton();
        }
    };

    private DialogInterface.OnClickListener positiveButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            MarhamUtils.getInstance().openPermissionSetting(WaitingAreaActivity.this);
            finish();

        }
    };

    private DialogInterface.OnClickListener negativeButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finish();
        }
    };

    @SuppressLint("SetTextI18n")
    private Room.Listener roomListener() {
        return new Room.Listener() {
            @Override
            public void onConnected(Room room) {

                identifyScenarioAndTakeAction();
                if (room.getRemoteParticipants().size() == 1) {
                    localParticipant = room.getLocalParticipant();
                    setViewsWhenDoctorIsOnline();
                    autoConnectCallWhenDoctorHasArrived();
                }


            }

            @Override
            public void onParticipantReconnecting(@NonNull Room room, @NonNull RemoteParticipant remoteParticipant) {
                Room.Listener.super.onParticipantReconnecting(room, remoteParticipant);
            }

            @Override
            public void onReconnecting(@NonNull Room room, @NonNull TwilioException twilioException) {
//                reconnectingProgressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onReconnected(@NonNull Room room) {
//                reconnectingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onConnectFailure(Room room, TwilioException e) {
            }

            @Override
            public void onDisconnected(Room room, TwilioException e) {
                localParticipant = null;
//                setViewsBeforeCalling();
//                identifyScenarioAndTakeAction();
            }

            @Override
            public void onParticipantConnected(Room room, RemoteParticipant remoteParticipant) {
//                Toast.makeText(WaitingAreaActivity.this, "another one is connected", Toast.LENGTH_SHORT).show();
                remoteParticipantIdentity = remoteParticipant.getIdentity();
                setViewsWhenDoctorIsOnline();
                autoConnectCallWhenDoctorHasArrived();
//                Toast.makeText(WaitingAreaActivity.this, "partcipant connected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onParticipantDisconnected(Room room, RemoteParticipant remoteParticipant) {
                removeRemoteParticipant(remoteParticipant);
//                identifyScenarioAndTakeAction();
                setViewsWhenDoctorIsOffline();
            }

            @Override
            public void onRecordingStarted(Room room) {
//                /*
//                 * Indicates when media shared to a Room is being recorded. Note that
//                 * recording is only available in our Group Rooms developer preview.
//                 */
//                Log.d(TAG, "onRecordingStarted");
            }

            @Override
            public void onRecordingStopped(Room room) {
//                /*
//                 * Indicates when media shared to a Room is no longer being recorded. Note that
//                 * recording is only available in our Group Rooms developer preview.
//                 */
//                Log.d(TAG, "onRecordingStopped");
            }
        };
    }

    private void autoConnectCallWhenDoctorHasArrived() {
        if (canAutoConnect && isDoctorOnline) {
            startLiveStreamingActivity();
        }
    }

    private void setViewsWhenDoctorIsOffline() {
        isDoctorOnline = false;
    }

    private void connectToRoom(String roomName) {
        swipeRefreshLayout.setRefreshing(true);
        ConnectOptions.Builder connectOptionsBuilder = new ConnectOptions.Builder(token)
                .roomName(roomName);
        room = Video.connect(this, connectOptionsBuilder.build(), roomListener());
    }

    private void removeRemoteParticipant(RemoteParticipant remoteParticipant) {
        if (!remoteParticipant.getIdentity().equals(remoteParticipantIdentity)) {
            return;
        }
    }

    @Override
    public void onBackPressed() {
        if (callRequestViewsContainer.getVisibility() == View.VISIBLE) {
            AppConstants.isCallAlreadyInProgress = false;
            onEndCallRequestByDoctor();
            sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.MISSED_CALL);
        }
        if (room != null) {
            room.disconnect();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(Appointment.class.getCanonicalName(), appointment);
        MarhamUtils.getInstance().startActivity(this, AllVideoConsultationsScreenMainActivity.class, true, bundle, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

    }

    private BroadcastReceiver internetStrengthReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (NetworkHelper.getInstance().getConnectionQuality(context)) {
                case "POOR":
                    setVisibilityStateOfPoorConnectionContainer(true);
                    break;

                case "MODERATE":
                    setVisibilityStateOfPoorConnectionContainer(true);
                    break;

                case "GOOD":
                    setVisibilityStateOfPoorConnectionContainer(false);
                    break;

                case "EXCELLENT":
                    setVisibilityStateOfPoorConnectionContainer(false);
                    break;

                case "UNKNOWN":
                    setVisibilityStateOfPoorConnectionContainer(true);
                    break;
            }
        }
    };

    private void setVisibilityStateOfPoorConnectionContainer(boolean state) {

        Spannable word = new SpannableString("Internet Connection is ");
        word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        internetConnnectionStatusTextView.setText(word);

        if (state) {
            internetConnectionStatusImageView.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.wifi_strength_bad_icon));

            Spannable wordTwo = new SpannableString("poor");
            wordTwo.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            internetConnnectionStatusTextView.append(wordTwo);

        } else {
            internetConnectionStatusImageView.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.wifi_strength_good_icon));

            Spannable wordTwo = new SpannableString("good");
            wordTwo.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            internetConnnectionStatusTextView.append(wordTwo);
        }

        Spannable wordThree = new SpannableString(" for Video Consultation");
        wordThree.setSpan(new ForegroundColorSpan(Color.BLACK), 0, wordThree.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        internetConnnectionStatusTextView.append(wordThree);

    }

    @Override
    public void onTapAllow(int permissionType) {
        switch (permissionType) {
            case AppConstants.PERMISSIONS.PermissionTypes.CAMERA_AND_GALLERY_PERMISSION:
                requestPermissionForCameraAndMicrophone();
                break;
        }
    }

    @Override
    public void onTapDeny(int permissionType) {
        switch (permissionType) {
            case AppConstants
                    .PERMISSIONS.PermissionTypes.CAMERA_AND_GALLERY_PERMISSION:
                finish();
                break;
        }
    }

    public void setViewsBeforeCallingAPI() {
        swipeRefreshLayout.setRefreshing(true);
    }

    public void setViewsAfterCallingAPI() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void setViewsIfNoRecordFoundWhileCallingAPI() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void setViewsIncaseInternetFailureOrUnExpectedResponseWhileCallingAPI() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void startUpdateTextLoop() {
        runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                Log.d("Handlers", "Called on main thread");
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object

                if (instructionsListIndexToShow >= instructionsList.size()) {
                    instructionsListIndexToShow = 0;
                }
                instructionsTextView.setText(instructionsList.get(instructionsListIndexToShow));
                instructionsListIndexToShow = instructionsListIndexToShow + 1;

                handler.postDelayed(this, delayInMillis);

            }
        };
// Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
    }

    public void setInstructionListBeforeCallTime() {
        instructionsTextView.setText("");
        instructionsList.clear();
        instructionsList.addAll(instructionsListBeforeCall);
    }

    public void setInstructionListAfterCallTime() {
        instructionsTextView.setText("");
        instructionsList.clear();
        instructionsList.addAll(instructionsListAfterCall);
    }

    public void stopUpdateTextLoop() {
        handler.removeCallbacks(runnableCode);
    }

    public void getTokenFromServer() {
        setViewsBeforeCallingAPI();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.APPLICATION_TYPE_KEY, AppConstants.API.APPLICATION_TYPE.TELENOR);
        hashMap.put(AppConstants.API.API_KEYS.ONLINE_CONSULATATION_ID_KEY, appointment.getOnlineConsultationId());
        APIClient apiClient = new APIClient();
        Call<TokenAndRoomServerResponse> call = apiClient.getToken(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_ONLINE_CONSULTATION_TOKEN);
        call.enqueue(retroFit2Callback);
    }

    public void updateDeviceToken() {
        APIClient apiClient = new APIClient();
        Call<UpdateDeviceTokenServerResponse> call;
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.UPDATE_DEVICE_TOKEN);
        call = apiClient.updateDeviceToken(MarhamVideoCallHelper.getInstance().getUserId(), MarhamVideoCallHelper.getInstance().getFireBaseToken(), AppConstants.API.DEVICE_TYPE.ANDROID, AppConstants.API.APPLICATION_TYPE.TELENOR, MarhamUtils.getInstance().getUniqueDeviceID());
        call.enqueue(retroFit2Callback);
    }

    public void sendOnlineConsultationSignal(String notificationType) {
        HashMap<String, String> info = new HashMap<>();
        info.put(AppConstants.API.API_KEYS.APPLICATION_TYPE_KEY, AppConstants.API.APPLICATION_TYPE.TELENOR);
        info.put(AppConstants.API.API_KEYS.ONLINE_CONSULATATION_ID_KEY, appointment.getOnlineConsultationId());
        info.put(AppConstants.API.API_KEYS.NOTIFICATION_TYPE_KEY, notificationType);
        info.put(AppConstants.API.API_KEYS.SEND_TO_PATIENT_KEY, "0");
        info.put(AppConstants.API.API_KEYS.SEND_TO_DOCTOR_KEY, "1");

        APIClient apiClient = new APIClient();
        Call<ServerResponseOld> call = apiClient.sendOnlineConsultationSignal(info);
        retroFit2Callback = new RetroFit2Callback<>(this, AppConstants.API.API_END_POINT_NUMBER.SEND_ONLINE_CONSULTATION_NOTIFICATION_SIGNAL);
        call.enqueue(retroFit2Callback);
    }

    public void updateLastSeenTimeOfUser() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.ONLINE_CONSULATATION_ID_KEY, appointment.getOnlineConsultationId());
        hashMap.put(AppConstants.API.API_KEYS.APPLICATION_TYPE_KEY, AppConstants.API.APPLICATION_TYPE.TELENOR);

        APIClient apiClient = new APIClient();
        Call<ServerResponseOld> call = apiClient.updateLastSeenTimeOfUser(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.UPDATE_LAST_SEEN_OF_USER);
        call.enqueue(retroFit2Callback);
    }

    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_ONLINE_CONSULTATION_TOKEN:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    setViewsAfterCallingAPI();
                    TokenAndRoomServerResponse tokenAndRoomServerResponse = (TokenAndRoomServerResponse) response;
                    setRoomData(tokenAndRoomServerResponse.getData());
                    makeCallButtonClickable();
                } else {
                    setViewsIfNoRecordFoundWhileCallingAPI();
                }
                break;

            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_DEVICE_TOKEN:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    MarhamUtils.getInstance().generateLog("Device Token Updated!");
                } else {
                    MarhamUtils.getInstance().generateLog("Device Token Not Updated!");
                }

            case AppConstants.API.API_END_POINT_NUMBER.SEND_ONLINE_CONSULTATION_NOTIFICATION_SIGNAL:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    MarhamUtils.getInstance().generateLog("Consultation Signal Sent");
                } else {
                    MarhamUtils.getInstance().generateLog("Consultation Signal Not Sent");
                }
                break;

            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_LAST_SEEN_OF_USER:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    MarhamUtils.getInstance().generateLog("Updated last seen of user");
                } else {
                    MarhamUtils.getInstance().generateLog("Could not update last seen of user");
                }
                break;

        }
    }

    @Override
    public void onFailure(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING:
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_DEVICE_TOKEN:
                MarhamUtils.getInstance().generateLog("Device Token Not Updated!");
                break;
            case AppConstants.API.API_END_POINT_NUMBER.SEND_ONLINE_CONSULTATION_NOTIFICATION_SIGNAL:
                MarhamUtils.getInstance().generateLog("Consultation Signal Not Sent");
                break;
            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_LAST_SEEN_OF_USER:
                MarhamUtils.getInstance().generateLog("Could not update last seen of user");
                break;
        }

    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_VC_DOCTORS_LISTING:
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_DEVICE_TOKEN:
                MarhamUtils.getInstance().generateLog("Device Token Not Updated!");
                break;
            case AppConstants.API.API_END_POINT_NUMBER.SEND_ONLINE_CONSULTATION_NOTIFICATION_SIGNAL:
                MarhamUtils.getInstance().generateLog("Consultation Signal Not Sent");
                break;
            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_LAST_SEEN_OF_USER:
                MarhamUtils.getInstance().generateLog("Could not update last seen of user");
                break;
        }
    }
}