package com.marham.marhamvideocalllibrary.activities.videocall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Rational;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.fragments.dialogs.AlertWindowConfirmationOC;
import com.marham.marhamvideocalllibrary.listeners.RuntimeAndSpecialPermissionsBottomSheetListener;
import com.marham.marhamvideocalllibrary.listeners.videocall.OCAlertBoxListener;
import com.marham.marhamvideocalllibrary.model.appointment.Appointment;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.notification.ServerResponsePushNotification;
import com.marham.marhamvideocalllibrary.model.notification.UpdateDeviceTokenServerResponse;
import com.marham.marhamvideocalllibrary.model.videoconsultation.TokenAndRoomServerResponse;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;
import com.marham.marhamvideocalllibrary.utils.permission.RuntimePermissionManager;
import com.marham.marhamvideocalllibrary.utils.videocall.CameraCapturerCompat;
import com.marham.marhamvideocalllibrary.utils.videocall.SettingsActivity;
import com.marham.marhamvideocalllibrary.utils.videocall.VibratorClass;
import com.twilio.audioswitch.AudioDevice;
import com.twilio.audioswitch.AudioSwitch;
import com.twilio.video.AudioCodec;
import com.twilio.video.CameraCapturer;
import com.twilio.video.ConnectOptions;
import com.twilio.video.EncodingParameters;
import com.twilio.video.G722Codec;
import com.twilio.video.H264Codec;
import com.twilio.video.IsacCodec;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.NetworkQualityConfiguration;
import com.twilio.video.OpusCodec;
import com.twilio.video.PcmaCodec;
import com.twilio.video.PcmuCodec;
import com.twilio.video.RemoteAudioTrack;
import com.twilio.video.RemoteAudioTrackPublication;
import com.twilio.video.RemoteDataTrack;
import com.twilio.video.RemoteDataTrackPublication;
import com.twilio.video.RemoteParticipant;
import com.twilio.video.RemoteVideoTrack;
import com.twilio.video.RemoteVideoTrackPublication;
import com.twilio.video.Room;
import com.twilio.video.TwilioException;
import com.twilio.video.Video;
import com.twilio.video.VideoCodec;
import com.twilio.video.VideoDimensions;
import com.twilio.video.VideoFormat;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;
import com.twilio.video.Vp8Codec;
import com.twilio.video.Vp9Codec;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import tvi.webrtc.VideoSink;

public class VideoCallActivity extends BaseActivity implements RuntimeAndSpecialPermissionsBottomSheetListener, Function2<List<? extends AudioDevice>, AudioDevice, Unit>, ServerConnectListener, DialogInterface.OnKeyListener {

    //Ringer Views
    private View ringerViewsContainer;
    private BodyText doctorNameTextView;
    private BodyText doctorSpecialityTextView;
    private MyButton rejectCallButton;
    private MyButton acceptCallButton;
    private ConstraintLayout parentLayout;

    private ConstraintLayout actionButtonsContainer;

    private BodyText internetStatusMessageTextView;

    private ConstraintLayout drVideoAndMicStatusViewsContainer;
    private MyImageView doctorBlurImageView;
    private MyImageView doctorVideoStatusIcon;
    private MyImageView doctorMicStatusIcon;
    private BodyText doctorVideoAndMicStatusMessageTextView;

    private ConstraintLayout patientVideoAndMicStatusViewsContainer;
    private MyImageView patientBlurImageView;
    private MyImageView patientVideoStatusIcon;
    private MyImageView patientMicStatusIcon;
    private BodyText patientVideoAndMicStatusMessageTextView;

    private MyButton switchCameraButton;
    private MyButton switchLocalVideoButton;
    private MyButton switchMicButton;
    private MyButton endCallButton;

    private ConstraintLayout noInternetViewsContainer;
    private BodyText noInternetHeadingTextView;
    private BodyText noInternetSubHeadingTextView;
    private MyImageView noInternetImageView;

    private MyButton noInternetTryAgainButton;
    private ConstraintLayout noInternetHelpButton;

    private int icon;

    private Appointment appointment;
    private String isDoctorRequestedCall = "0";
    private String gender;

    private boolean isOtherPersonMicEnabled = true;
    private boolean isOtherPersonVideoEnabled = true;

    private boolean isMyMicEnabled = true;
    private boolean isMyVideoEnabled = true;

    private ProgressBar dialogReconnectingProgressBar;
    private BodyText dialogReconnectingMessage;
    private Timer lastSeenTimer;
    private String LOG_TAG = "Marham";
    private AlertDialog.Builder builder;
    private static boolean active = false;
    private AlertDialog dialog;
    private ConnectOptions.Builder connectOptionsBuilder;
    private boolean isFirstTime;
    private RetroFit2Callback<ServerResponseOld> retroFit2Callback;
    private String uri;
    private boolean hasCallEnded;
    private boolean isUserRendered = false;


    private Ringtone ringtone;
    private boolean settingsButtonTapped;
    private AudioSwitch audioSwitch;
    private AudioDevice selectedDevice;
    private List<AudioDevice> myAudioDevices;
    //    private MyImageView videoIsPausedContainer;
    private BodyText videoIsPausedText;

    private VideoFormat videoFormat;
    private CameraCapturer cameraCapturer;
    private VideoSink localVideoView;
    //    private MyImageView patientDisabledTheVideoImageview;
    private HashMap<String, String> permissionsList;
    private NetworkQualityConfiguration configuration;

    private static final int CAMERA_MIC_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "VideoActivity";

    /*
     * Audio and video tracks can be created with names. This feature is useful for categorizing
     * tracks of participants. For example, if one participant publishes a video track with
     * ScreenCapturer and CameraCapturer with the names "screen" and "camera" respectively then
     * other participants can use RemoteVideoTrack#getName to determine which video track is
     * produced from the other participant's screen or camera.
     */
    private static final String LOCAL_AUDIO_TRACK_NAME = "mic";
    private static final String LOCAL_VIDEO_TRACK_NAME = "camera";

    /*
     * You must provide a Twilio Access Token to connect to the Video service
     */
    private static final String TWILIO_ACCESS_TOKEN = "";
    private static final String ACCESS_TOKEN_SERVER = "http://localhost:3000";
//    public static final boolean USE_TOKEN_SERVER = false;

    /*
     * Access token used to connect. This field will be set either from the console generated token
     * or the request to the token server.
     */
    private String accessToken;
    private String roomString;

    /*
     * A Room represents communication between a local participant and one or more participants.
     */
    private Room room;
    private LocalParticipant localParticipant;

    /*
     * AudioCodec and VideoCodec represent the preferred codec for encoding and decoding audio and
     * video.
     */
    private AudioCodec audioCodec;
    private VideoCodec videoCodec;

    /*
     * Encoding parameters represent the sender side bandwidth constraints.
     */
    private EncodingParameters encodingParameters;

    /*
     * A VideoView receives frames from a local or remote video track and renders them
     * to an associated view.
     */
    private VideoView primaryVideoView;
    private VideoView thumbnailVideoView;

    /*
     * Android shared preferences used for settings
     */
    private SharedPreferences preferences;

    /*
     * Android application UI elements
     */
    private CameraCapturerCompat cameraCapturerCompat;
    private LocalAudioTrack localAudioTrack;
    private LocalVideoTrack localVideoTrack;

    private FloatingActionButton connectActionFab;
    private FloatingActionButton switchCameraActionFab;
    private FloatingActionButton localVideoActionFab;
    private FloatingActionButton muteActionFab;

    private ProgressBar reconnectingProgressBar;
    private ConstraintLayout swipeRefreshLayout;
    private AlertDialog connectDialog;
    private AudioManager audioManager;
    private String remoteParticipantIdentity;
    private EncodingParameters newEncodingParameters;

    private MenuItem turnSpeakerOnMenuItem;
    //    private MyButton defaultMicButton;
    private MenuItem turnSpeakerOffMenuItem;

    private int previousAudioMode;
    private boolean previousMicrophoneMute;

    private boolean disconnectedFromOnDestroy;
    private boolean isSpeakerPhoneEnabled = true;
    private boolean enableAutomaticSubscription;

    private BroadcastReceiver mReceiver;

    private boolean misInPictureInPictureMode = false;


    private Handler ringerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        allowPermissionForCallingEvenWhenTheScreenIsLocked();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
        initGui();
        setListeners();
        initVariables();
        doctorNameTextView.setText(appointment.getDoctorName());
        doctorSpecialityTextView.setText(appointment.getSpecialityName());
        if (isDoctorRequestedCall == null || isDoctorRequestedCall.equals("0")) {
            setUpCall();
        } else {
            playRingTone();
        }

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (R.id.accept_call_button == view.getId()) {
            stopRingTone();
            sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.PATIENT_ACCPETED_CALL);
            setUpCall();
        } else if (R.id.reject_call_button == viewId) {
            patientRejectedCall();
        } else if (R.id.end_call_button == viewId) {
            AlertWindowConfirmationOC dialog = new AlertWindowConfirmationOC(VideoCallActivity.this, ocAlertBoxListener, AppConstants.OCAlertBoxKeys.END_CALL, "Are you sure you want to \nend the consultation?", "No, Return back to call", "Yes, End the call", true);
            dialog.show();
        } else if (R.id.try_again_button_2 == viewId) {
            openUserPanel();
        } else if (R.id.help_button_2_container == viewId) {
            MarhamUtils.getInstance().callMarhamHelpline(this, this, parentLayout);
        }

    }


    private void patientRejectedCall() {
        stopRingTone();
        AppConstants.isCallAlreadyInProgress = false;
        sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.PATIENT_REJECTED_CALL);
        VideoCallActivity.this.finish();
    }

    private void initGui() {
        ringerViewsContainer = findViewById(R.id.ringer_views_container);
        doctorNameTextView = findViewById(R.id.doctor_name_text_view);
        doctorSpecialityTextView = findViewById(R.id.doctor_speciality_text_view);
        rejectCallButton = findViewById(R.id.reject_call_button);
        parentLayout = findViewById(R.id.parent_layout);
        acceptCallButton = findViewById(R.id.accept_call_button);

        actionButtonsContainer = findViewById(R.id.action_buttons_container);

        parentLayout = findViewById(R.id.parent_layout);

        internetStatusMessageTextView = findViewById(R.id.internet_status_message_text_view);

        drVideoAndMicStatusViewsContainer = findViewById(R.id.dr_video_and_mic_status_views_container);
        doctorBlurImageView = findViewById(R.id.doctor_blur_image_view);
        doctorVideoStatusIcon = findViewById(R.id.dr_video_status_icon);
        doctorMicStatusIcon = findViewById(R.id.dr_mic_status_icon);
        doctorVideoAndMicStatusMessageTextView = findViewById(R.id.dr_video_and_mic_status_message_text_view);

        patientVideoAndMicStatusViewsContainer = findViewById(R.id.patient_video_and_mic_status_views_container);
        patientBlurImageView = findViewById(R.id.patient_blur_image_view);
        patientVideoStatusIcon = findViewById(R.id.patient_video_status_icon);
        patientMicStatusIcon = findViewById(R.id.patient_mic_status_icon);
        patientVideoAndMicStatusMessageTextView = findViewById(R.id.patient_video_and_mic_status_message_text_view);

        switchCameraButton = findViewById(R.id.switch_camera_button);
        switchLocalVideoButton = findViewById(R.id.switch_local_video_button);
        switchMicButton = findViewById(R.id.switch_mic_button);
        endCallButton = findViewById(R.id.end_call_button);

        noInternetViewsContainer = findViewById(R.id.no_internet_views_container);
        noInternetHeadingTextView = findViewById(R.id.no_internet_heading_text_view);
        noInternetSubHeadingTextView = findViewById(R.id.no_internet_sub_heading_text_view);
        noInternetImageView = findViewById(R.id.no_internet_image_view);
        noInternetTryAgainButton = findViewById(R.id.try_again_button_2);
        noInternetHelpButton = findViewById(R.id.help_button_2_container);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        parentLayout = findViewById(R.id.parent_layout);

        primaryVideoView = findViewById(R.id.primary_video_view);
        thumbnailVideoView = findViewById(R.id.thumbnail_video_view);
        reconnectingProgressBar = findViewById(R.id.reconnecting_progress_bar);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void setListeners() {
        acceptCallButton.setOnClickListener(this);
        rejectCallButton.setOnClickListener(this);

        switchCameraButton.setVisibility(View.VISIBLE);
        switchCameraButton.setOnClickListener(switchCameraClickListener());

        switchLocalVideoButton.setVisibility(View.VISIBLE);
        switchLocalVideoButton.setOnClickListener(localVideoClickListener());

        switchMicButton.setVisibility(View.VISIBLE);
        switchMicButton.setOnClickListener(muteClickListener());

        endCallButton.setVisibility(View.VISIBLE);
        endCallButton.setOnClickListener(this);

        noInternetTryAgainButton.setOnClickListener(this);
        noInternetHelpButton.setOnClickListener(this);

    }

    private void initVariables() {
        receiveIntent();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        hasCallEnded = false;
        settingsButtonTapped = false;

    }

    public void receiveIntent() {
        appointment = (Appointment) getIntent().getSerializableExtra(Appointment.class.getCanonicalName());
        isDoctorRequestedCall = (String) getIntent().getStringExtra(getString(R.string.is_doctor_requested_call));
        if (appointment != null) {
            if (appointment.getGender() != null) {
                gender = appointment.getGender();
            } else {
            }
        } else {
        }
    }

    private void setUpCall() {
        ringerViewsContainer.setVisibility(View.GONE);

        audioCodec = getAudioCodecPreference(SettingsActivity.PREF_AUDIO_CODEC,
                SettingsActivity.PREF_AUDIO_CODEC_DEFAULT);
        videoCodec = getVideoCodecPreference(SettingsActivity.PREF_VIDEO_CODEC,
                SettingsActivity.PREF_VIDEO_CODEC_DEFAULT);
        enableAutomaticSubscription = getAutomaticSubscriptionPreference(SettingsActivity.PREF_ENABLE_AUTOMATIC_SUBSCRIPTION,
                SettingsActivity.PREF_ENABLE_AUTOMATIC_SUBSCRIPTION_DEFAULT);

        encodingParameters = getEncodingParameters();

        VibratorClass.getVibratorInstance().stopVibration();

        /*
         * Get shared preferences to read settings
         */
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        /*
         * Enable changing the volume using the up/down keys during a conversation
         */
        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

        /*
         * Needed for setting/abandoning audio focus during call
         */
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_RINGTONE | AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(isSpeakerPhoneEnabled);


        setIsCallInProgresStatus(true);
        /*
         * Check camera and microphone permissions. Needed in Android M.
         */

        handleCameraAndMicrophonePermission();
//        if (!checkPermissionForCameraAndMicrophone()) {
//            requestPermissionForCameraAndMicrophone();
//        } else {
//            createAudioAndVideoTracks();
//            presenter.getTokenFromAPI();
//        }
        startLastSeenAPILoop();
        setAudioSetting();
        /*
         * Set the initial state of the UI
         */
    }

    private void handleCameraAndMicrophonePermission() {
        if (!RuntimePermissionManager.getInstance().checkIfUserHasGrantedCameraAndMicrophonePermission(this)) {
            RuntimePermissionManager.getInstance().showRuntimeAndSpecialPermissions(this, this, this, AppConstants
                    .PERMISSIONS.PermissionTypes.CAMERA_AND_GALLERY_PERMISSION);
        } else {
            createAudioAndVideoTracks();
            getTokenFromServer();
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
                        Log.e(LOG_TAG, "Updating Last Seen");
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

    private void setVisibilityStateOfPoorConnectionContainer(boolean state) {
        if (state) {
            internetStatusMessageTextView.setVisibility(View.VISIBLE);
        } else {
            internetStatusMessageTextView.setVisibility(View.GONE);
        }
    }

    private void setIsCallInProgresStatus(boolean status) {
        AppConstants.isCallAlreadyInProgress = status;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_video_activity, menu);
//        turnSpeakerOnMenuItem = menu.findItem(R.id.menu_turn_speaker_on);
//        turnSpeakerOffMenuItem = menu.findItem(R.id.menu_turn_speaker_off);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_settings:
//                startActivity(new Intent(this, SettingsActivity.class));
//                return true;
//            case R.id.menu_turn_speaker_on:
//            case R.id.menu_turn_speaker_off:
//                boolean expectedSpeakerPhoneState = !audioManager.isSpeakerphoneOn();
//
//                audioManager.setSpeakerphoneOn(expectedSpeakerPhoneState);
//                turnSpeakerOffMenuItem.setVisible(expectedSpeakerPhoneState);
//                turnSpeakerOnMenuItem.setVisible(!expectedSpeakerPhoneState);
//                isSpeakerPhoneEnabled = expectedSpeakerPhoneState;
//
//                return true;
//            default:
//                return false;
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                createAudioAndVideoTracks();
                getTokenFromServer();
                intializeUI();
            } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (!(VideoCallActivity.this).isFinishing()) {
                    MarhamUtils.getInstance().showConfirmationMessage(VideoCallActivity.this, "Camera !", "For video call with doctor you need to allow camera permission.", "Settings", "cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MarhamUtils.getInstance().openPermissionSetting(VideoCallActivity.this);
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
                if (!((Activity) VideoCallActivity.this).isFinishing()) {
                    MarhamUtils.getInstance().showConfirmationMessage(VideoCallActivity.this, "Microphone !", "For video call with doctor you need to allow microphone permission.", "Settings", "cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MarhamUtils.getInstance().openPermissionSetting(VideoCallActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleNotificationTypeResponse((ServerResponsePushNotification) intent.getExtras().getParcelable(ServerResponsePushNotification.class.getCanonicalName()));
            hasCallEnded = true;
        }
    };


    private void handleNotificationTypeResponse(ServerResponsePushNotification notification) {
        switch (notification.getNotification_type()) {
            case AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.DOCTOR_CANCELLED_CALL:
                AppConstants.isCallAlreadyInProgress = false;
                stopRingTone();
                Toast.makeText(this, "Doctor Cancelled the call", Toast.LENGTH_SHORT).show();
                VideoCallActivity.this.finish();
                break;
            case AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.DOCTOR_ENDED_CALL:
                MarhamUtils.getInstance().startActivity(this, VideoCallEndedActivity.class, true);

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        updateDeviceToken();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(AppConstants.NOTIFICATIONS.LOCAL_NOTIFICATIONS.DOCTOR_CANCELLED_CALL));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(AppConstants.NOTIFICATIONS.LOCAL_NOTIFICATIONS.DOCTOR_ENDED_CALL));

        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        primaryVideoView = findViewById(R.id.primary_video_view);
        if (settingsButtonTapped) {
            createAudioAndVideoTracks();
            getTokenFromServer();
        } else {
        }

        if (!(ringerViewsContainer.getVisibility() == View.VISIBLE)) {
            /*
             * Update preferred audio and video codec in case changed in settings
             */

            /*
             * Get latest encoding parameters
             */
//        encodingParameters = getEncodingParameters();
            /*
             * If the local video track was released when the app was put in the background, recreate.
             */
            // Setup video format
            videoFormat = new VideoFormat(VideoDimensions.VGA_VIDEO_DIMENSIONS, 15);


            if (localVideoTrack == null && checkPermissionForCameraAndMicrophone()) {
                if (isMyVideoEnabled) {
                    createLocalVideoTrackAndPublish();
                }

            }

            /*
             * Update encoding parameters
             */
//        encodingParameters = newEncodingParameters;

            /*
             * Route audio through cached value.
             */
//        audioManager.setSpeakerphoneOn(isSpeakerPhoneEnabled);

            /*
             * Update reconnecting UI
             */


            if (icon == R.drawable.video_off) {
                disableVideo();
            }


            if (room != null) {

//            reconnectingProgressBar.setVisibility((room.getState() != Room.State.RECONNECTING) ?
//                    View.GONE :
//                    View.VISIBLE);
            }
        }


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(internetStrengthReceiver, intentFilter);
    }

    private void createLocalVideoTrackAndPublish() {
        localVideoTrack =
                LocalVideoTrack.create(
                        this, true, cameraCapturerCompat, LOCAL_VIDEO_TRACK_NAME);
        localVideoTrack.addSink(localVideoView);

        if (localParticipant != null) {
//            Toast.makeText(this, "Local Participant is not null", Toast.LENGTH_LONG).show();
            localParticipant.publishTrack(localVideoTrack);
        } else {
//            Toast.makeText(this, "Local Participant is null", Toast.LENGTH_LONG).show();
        }
    }


    private void setVideoConstraints() {
        videoFormat = new VideoFormat(VideoDimensions.VGA_VIDEO_DIMENSIONS, 15);
        localVideoTrack = LocalVideoTrack.create(this, true, cameraCapturer, videoFormat);

    }

    @Override
    protected void onPause() {

        /*
         * Release the local video track before going in the background. This ensures that the
         * camera can be used by other applications while this app is in the background.
         */
        if (localVideoTrack != null) {
            /*
             * If this local video track is being shared in a Room, unpublish from room before
             * releasing the video track. Participants will be notified that the track has been
             * unpublished.
             */
            if (localParticipant != null) {
                localParticipant.unpublishTrack(localVideoTrack);
            }

            localVideoTrack.release();
            localVideoTrack = null;
        }
        unregisterReceiver(internetStrengthReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (ringerViewsContainer.getVisibility() == View.VISIBLE) {
            sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.PATIENT_REJECTED_CALL);
        }
        stopRingTone();
        setIsCallInProgresStatus(false);

        /*
         * Always disconnect from the room before leaving the Activity togit
         * ensure any memory allocated to the Room resource is freed.
         */
//        if(android.os.Build.VERSION.SDK_INT >= 26) {
        settingsButtonTapped = false;
        if (room != null) {
            stopLastSceneAPIupdate();
            room.disconnect();
            disconnectedFromOnDestroy = true;
        } else {
//            Toast.makeText(this, "room is null", Toast.LENGTH_SHORT).show();
        }

        /*
         * Release the local audio and video tracks ensuring any memory allocated to audio
         * or video is freed.
         */

        if (localAudioTrack != null) {
            localAudioTrack.release();
            localAudioTrack = null;
        }
        if (localVideoTrack != null) {
            localVideoTrack.release();
            localVideoTrack = null;
        }
//        }else{
//        }
        if (audioSwitch != null) {
            audioSwitch.stop();
        }
        super.onDestroy();

    }

    private boolean checkPermissionForCameraAndMicrophone() {
        int resultCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int resultMic = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return resultCamera == PackageManager.PERMISSION_GRANTED &&
                resultMic == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionForCameraAndMicrophone() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
                CAMERA_MIC_PERMISSION_REQUEST_CODE);
    }


    private void createAudioAndVideoTracks() {
        // Share your microphone
        localAudioTrack = LocalAudioTrack.create(this, true, LOCAL_AUDIO_TRACK_NAME);

        // Share your camera
        cameraCapturerCompat =
                new CameraCapturerCompat(this, CameraCapturerCompat.Source.FRONT_CAMERA);
        localVideoTrack =
                LocalVideoTrack.create(this, true, cameraCapturerCompat, LOCAL_VIDEO_TRACK_NAME);
        primaryVideoView.setMirror(true);
        localVideoTrack.addSink(primaryVideoView);
        localVideoView = primaryVideoView;


    }

    private void connectToRoom(String roomName, String token) {
        configureAudio(true);
        if (token != null) {
            connectOptionsBuilder = new ConnectOptions.Builder(token)
                    .roomName(roomName).videoTracks(Collections.singletonList(localVideoTrack)).encodingParameters(new EncodingParameters(16, 0));

        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Appointment.class.getCanonicalName(), appointment);
            MarhamUtils.getInstance().startActivity((AppCompatActivity) this, WaitingAreaActivity.class, true, bundle, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            return;
        }

        /*
         * Add local audio track to connect options to share with participants.
         */
        if (localAudioTrack != null) {
            connectOptionsBuilder.audioTracks(Collections.singletonList(localAudioTrack));
        }


        /*
         * Add local video track to connect options to share with participants.
         */
        if (localVideoTrack != null) {
            connectOptionsBuilder.videoTracks(Collections.singletonList(localVideoTrack));
        }

        /*
         * Set the preferred audio and video codec for media.
         */
        connectOptionsBuilder.preferAudioCodecs(Collections.singletonList(audioCodec));
        connectOptionsBuilder.preferVideoCodecs(Collections.singletonList(videoCodec));

        /*
         * Set the sender side encoding parameters.
         */
        connectOptionsBuilder.encodingParameters(encodingParameters);

        /*
         * Toggles automatic track subscription. If set to false, the LocalParticipant will receive
         * notifications of track publish events, but will not automatically subscribe to them. If
         * set to true, the LocalParticipant will automatically subscribe to tracks as they are
         * published. If unset, the default is true. Note: This feature is only available for Group
         * Rooms. Toggling the flag in a P2P room does not modify subscription behavior.
         */
        connectOptionsBuilder.enableAutomaticSubscription(enableAutomaticSubscription);

        room = Video.connect(this, connectOptionsBuilder.build(), roomListener());
//        setDisconnectAction();
    }

    /*
     * The initial state when there is no active room.
     */
    private void intializeUI() {
        switchCameraButton.setVisibility(View.VISIBLE);
        switchMicButton.setVisibility(View.VISIBLE);
        switchLocalVideoButton.setVisibility(View.VISIBLE);
        endCallButton.setVisibility(View.VISIBLE);
    }

    /*
     * Get the preferred audio codec from shared preferences
     */
    private AudioCodec getAudioCodecPreference(String key, String defaultValue) {
        final String audioCodecName = preferences.getString(key, defaultValue);

        switch (audioCodecName) {
            case IsacCodec.NAME:
                return new IsacCodec();
            case OpusCodec.NAME:
                return new OpusCodec();
            case PcmaCodec.NAME:
                return new PcmaCodec();
            case PcmuCodec.NAME:
                return new PcmuCodec();
            case G722Codec.NAME:
                return new G722Codec();
            default:
                return new OpusCodec();
        }
    }

    /*
     * Get the preferred video codec from shared preferences
     */
    private VideoCodec getVideoCodecPreference(String key, String defaultValue) {
        final String videoCodecName = preferences.getString(key, defaultValue);

        switch (videoCodecName) {
            case Vp8Codec.NAME:
                boolean simulcast = preferences.getBoolean(SettingsActivity.PREF_VP8_SIMULCAST,
                        SettingsActivity.PREF_VP8_SIMULCAST_DEFAULT);
                return new Vp8Codec(simulcast);
            case H264Codec.NAME:
                return new H264Codec();
            case Vp9Codec.NAME:
                return new Vp9Codec();
            default:
                return new Vp8Codec();
        }
    }

    private boolean getAutomaticSubscriptionPreference(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    private EncodingParameters getEncodingParameters() {
        final int maxAudioBitrate = Integer.parseInt(
                preferences.getString(SettingsActivity.PREF_SENDER_MAX_AUDIO_BITRATE,
                        SettingsActivity.PREF_SENDER_MAX_AUDIO_BITRATE_DEFAULT));
        final int maxVideoBitrate = Integer.parseInt(
                preferences.getString(SettingsActivity.PREF_SENDER_MAX_VIDEO_BITRATE,
                        SettingsActivity.PREF_SENDER_MAX_VIDEO_BITRATE_DEFAULT));

        return new EncodingParameters(maxAudioBitrate, maxVideoBitrate);
    }

    /*
     * The actions performed during disconnect.
     */
//    private void setDisconnectAction() {
//        connectActionFab.setImageDrawable(ContextCompat.getDrawable(this,
//                R.drawable.vector_icon_call_reject));
//        connectActionFab.show();
//        connectActionFab.setOnClickListener(disconnectClickListener());
//    }

    /*
     * Creates an connect UI dialog
     */
//    private void showConnectDialog() {
//        EditText roomEditText = new EditText(this);
//        connectDialog = Dialog.createConnectDialog(roomEditText,
//                connectClickListener(roomEditText),
//                cancelConnectDialogClickListener(),
//                this);
//        connectDialog.show();
//    }

    /*
     * Called when remote participant joins the room
     */
    @SuppressLint("SetTextI18n")
    private void addRemoteParticipant(RemoteParticipant remoteParticipant) {
        /*
         * This app only displays video for one additional participant per Room
         */
        if (thumbnailVideoView.getVisibility() == View.VISIBLE) {
//            Snackbar.make(connectActionFab,
//                    "Multiple participants are not currently support in this UI",
//                    Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();

//            Toast.makeText(this,"Multiple participants are not currently support in this UI",Toast.LENGTH_LONG).show();
//            return;
        }
        remoteParticipantIdentity = remoteParticipant.getIdentity();

        /*
         * Add remote participant renderer
         */
        if (remoteParticipant.getRemoteVideoTracks().size() > 0) {
            RemoteVideoTrackPublication remoteVideoTrackPublication =
                    remoteParticipant.getRemoteVideoTracks().get(0);

            /*
             * Only render video tracks that are subscribed to
             */
            if (remoteVideoTrackPublication.isTrackSubscribed()) {
                addRemoteParticipantVideo(remoteVideoTrackPublication.getRemoteVideoTrack());

            }
        }

        /*
         * Start listening for participant events
         */
        remoteParticipant.setListener(remoteParticipantListener());
    }

    /*
     * Set primary view as renderer for participant video track
     */
    private void addRemoteParticipantVideo(VideoTrack videoTrack) {
        if (!misInPictureInPictureMode) {
            moveLocalVideoToThumbnailView();
        }
        primaryVideoView.setMirror(false);
        videoTrack.addSink(primaryVideoView);
        isUserRendered = true;
    }

    private void moveLocalVideoToThumbnailView() {
        if (thumbnailVideoView.getVisibility() == View.GONE) {
            thumbnailVideoView.setVisibility(View.VISIBLE);
            if (localAudioTrack != null) {
                if (primaryVideoView != null) {
                    if (localVideoTrack != null) {
                        localVideoTrack.removeSink(primaryVideoView);
                    }
                }
                if (localVideoTrack != null) {
                    localVideoTrack.addSink(thumbnailVideoView);
                }
            }
            localVideoView = thumbnailVideoView;
            thumbnailVideoView.setMirror(
                    cameraCapturerCompat.getCameraSource()
                            == CameraCapturerCompat.Source.FRONT_CAMERA);
        }
    }

    /*
     * Called when remote participant leaves the room
     */
    @SuppressLint("SetTextI18n")
    private void removeRemoteParticipant(RemoteParticipant remoteParticipant) {
        if (!remoteParticipant.getIdentity().equals(remoteParticipantIdentity)) {
            return;
        }

        /*
         * Remove remote participant renderer
         */
        if (!remoteParticipant.getRemoteVideoTracks().isEmpty()) {
            RemoteVideoTrackPublication remoteVideoTrackPublication =
                    remoteParticipant.getRemoteVideoTracks().get(0);

            /*
             * Remove video only if subscribed to participant track
             */
            if (remoteVideoTrackPublication.isTrackSubscribed()) {
                removeParticipantVideo(remoteVideoTrackPublication.getRemoteVideoTrack());
            }
        }
        moveLocalVideoToPrimaryView();
    }

    private void removeParticipantVideo(VideoTrack videoTrack) {
        videoTrack.removeSink(primaryVideoView);
    }

    private void moveLocalVideoToPrimaryView() {
        if (thumbnailVideoView.getVisibility() == View.VISIBLE) {
            thumbnailVideoView.setVisibility(View.GONE);
            if (localVideoTrack != null) {
                localVideoTrack.removeSink(thumbnailVideoView);
                localVideoTrack.addSink(primaryVideoView);
            }
            localVideoView = primaryVideoView;
            primaryVideoView.setMirror(
                    cameraCapturerCompat.getCameraSource()
                            == CameraCapturerCompat.Source.FRONT_CAMERA);
        }
    }

    /*
     * Room events listener
     */
    @SuppressLint("SetTextI18n")
    private Room.Listener roomListener() {
        return new Room.Listener() {
            @Override
            public void onConnected(Room room) {
//                Toast.makeText(LiveStreamingActivity2.this, String.valueOf(room.getRemoteParticipants().size()), Toast.LENGTH_LONG).show();
                localParticipant = room.getLocalParticipant();
                setTitle(room.getName());

                for (RemoteParticipant remoteParticipant : room.getRemoteParticipants()) {
                    addRemoteParticipant(remoteParticipant);
                    break;
                }
                finishScreenAfterFifteenSeconds();
                setAudioVideoViewsStatusAndVisibilityOfDoctor();
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
                configureAudio(false);
//                Toast.makeText(LiveStreamingActivity2.this, "Failed to connect", Toast.LENGTH_LONG).show();
                setVisibilityAndViewsOfNoInternetViewsContainer(View.VISIBLE, "Something went wrong!", "Don't worry,we're trying to get back to you.", 0);

            }

            @Override
            public void onDisconnected(Room room, TwilioException e) {
                localParticipant = null;
//                reconnectingProgressBar.setVisibility(View.GONE);
                VideoCallActivity.this.room = null;
                // Only reinitialize the UI if disconnect was not called from onDestroy()
                if (!disconnectedFromOnDestroy) {
                    configureAudio(false);
//                    intializeUI();
                    moveLocalVideoToPrimaryView();
                }
//                presenter.openUserPanel();

            }

            @Override
            public void onParticipantConnected(Room room, RemoteParticipant remoteParticipant) {
                addRemoteParticipant(remoteParticipant);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onParticipantDisconnected(Room room, RemoteParticipant remoteParticipant) {
                removeRemoteParticipant(remoteParticipant);

                if (!hasCallEnded) {

                } else {
                }

                if (isUserRendered && !hasCallEnded) {
                    openUserPanel();
                } else {

                }
            }


            @Override
            public void onRecordingStarted(Room room) {
                /*
                 * Indicates when media shared to a Room is being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                Log.d(TAG, "onRecordingStarted");
            }

            @Override
            public void onRecordingStopped(Room room) {
                /*
                 * Indicates when media shared to a Room is no longer being recorded. Note that
                 * recording is only available in our Group Rooms developer preview.
                 */
                Log.d(TAG, "onRecordingStopped");
            }
        };
    }

    private void finishScreenAfterFifteenSeconds() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUserRendered) {
                } else {
                    onBackPressed();
                }
            }
        }, 15000);
    }

    @SuppressLint("SetTextI18n")
    private RemoteParticipant.Listener remoteParticipantListener() {
        return new RemoteParticipant.Listener() {
            @Override
            public void onAudioTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteAudioTrackPublication remoteAudioTrackPublication) {
                Log.i(TAG, String.format("onAudioTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.isTrackEnabled(),
                        remoteAudioTrackPublication.isTrackSubscribed(),
                        remoteAudioTrackPublication.getTrackName()));
            }

            @Override
            public void onAudioTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteAudioTrackPublication remoteAudioTrackPublication) {
                Log.i(TAG, String.format("onAudioTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.isTrackEnabled(),
                        remoteAudioTrackPublication.isTrackSubscribed(),
                        remoteAudioTrackPublication.getTrackName()));
            }

            @Override
            public void onDataTrackPublished(RemoteParticipant remoteParticipant,
                                             RemoteDataTrackPublication remoteDataTrackPublication) {
                Log.i(TAG, String.format("onDataTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.isTrackEnabled(),
                        remoteDataTrackPublication.isTrackSubscribed(),
                        remoteDataTrackPublication.getTrackName()));
            }

            @Override
            public void onDataTrackUnpublished(RemoteParticipant remoteParticipant,
                                               RemoteDataTrackPublication remoteDataTrackPublication) {
                Log.i(TAG, String.format("onDataTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.isTrackEnabled(),
                        remoteDataTrackPublication.isTrackSubscribed(),
                        remoteDataTrackPublication.getTrackName()));
            }

            @Override
            public void onVideoTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteVideoTrackPublication remoteVideoTrackPublication) {
                Log.i(TAG, String.format("onVideoTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.isTrackEnabled(),
                        remoteVideoTrackPublication.isTrackSubscribed(),
                        remoteVideoTrackPublication.getTrackName()));
//                videoIsPausedContainer.setVisibility(View.GONE);
            }

            @Override
            public void onVideoTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteVideoTrackPublication remoteVideoTrackPublication) {
                Log.i(TAG, String.format("onVideoTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.isTrackEnabled(),
                        remoteVideoTrackPublication.isTrackSubscribed(),
                        remoteVideoTrackPublication.getTrackName()));
//                videoIsPausedContainer.setVisibility(View.VISIBLE);
//                Glide.with(LiveStreamingActivity2.this).load(presenter.getAppointment().getDoctorImageUrl()).circleCrop().into(videoIsPausedContainer);
            }

            @Override
            public void onAudioTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteAudioTrackPublication remoteAudioTrackPublication,
                                               RemoteAudioTrack remoteAudioTrack) {
                Log.i(TAG, String.format("onAudioTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrack: enabled=%b, playbackEnabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrack.isEnabled(),
                        remoteAudioTrack.isPlaybackEnabled(),
                        remoteAudioTrack.getName()));

            }

            @Override
            public void onAudioTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                 RemoteAudioTrack remoteAudioTrack) {
                Log.i(TAG, String.format("onAudioTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrack: enabled=%b, playbackEnabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrack.isEnabled(),
                        remoteAudioTrack.isPlaybackEnabled(),
                        remoteAudioTrack.getName()));
            }

            @Override
            public void onAudioTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                       TwilioException twilioException) {
                Log.i(TAG, String.format("onAudioTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));

                Toast.makeText(VideoCallActivity.this, "Trouble loading your audio !\nPlease check your internet connect..", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onDataTrackSubscribed(RemoteParticipant remoteParticipant,
                                              RemoteDataTrackPublication remoteDataTrackPublication,
                                              RemoteDataTrack remoteDataTrack) {
                Log.i(TAG, String.format("onDataTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrack.isEnabled(),
                        remoteDataTrack.getName()));
            }

            @Override
            public void onDataTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                RemoteDataTrackPublication remoteDataTrackPublication,
                                                RemoteDataTrack remoteDataTrack) {
                Log.i(TAG, String.format("onDataTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrack.isEnabled(),
                        remoteDataTrack.getName()));
            }

            @Override
            public void onDataTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                      RemoteDataTrackPublication remoteDataTrackPublication,
                                                      TwilioException twilioException) {
                Log.i(TAG, String.format("onDataTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
            }

            @Override
            public void onVideoTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteVideoTrackPublication remoteVideoTrackPublication,
                                               RemoteVideoTrack remoteVideoTrack) {
                Log.i(TAG, String.format("onVideoTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrack.isEnabled(),
                        remoteVideoTrack.getName()));
                addRemoteParticipantVideo(remoteVideoTrack);
            }

            @Override
            public void onVideoTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                 RemoteVideoTrack remoteVideoTrack) {
                Log.i(TAG, String.format("onVideoTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrack.isEnabled(),
                        remoteVideoTrack.getName()));
                removeParticipantVideo(remoteVideoTrack);
            }

            @Override
            public void onVideoTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                       TwilioException twilioException) {
                Log.i(TAG, String.format("onVideoTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
                Toast.makeText(VideoCallActivity.this, "\"Trouble loading your video !\", \"Please check your internet connect..\"", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAudioTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteAudioTrackPublication remoteAudioTrackPublication) {
                isOtherPersonMicEnabled = true;
                setAudioVideoViewsStatusAndVisibilityOfDoctor();
            }

            @Override
            public void onAudioTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteAudioTrackPublication remoteAudioTrackPublication) {
                isOtherPersonMicEnabled = false;
                setAudioVideoViewsStatusAndVisibilityOfDoctor();

            }

            @Override
            public void onVideoTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteVideoTrackPublication remoteVideoTrackPublication) {
                isOtherPersonVideoEnabled = true;
                setAudioVideoViewsStatusAndVisibilityOfDoctor();
            }

            @Override
            public void onVideoTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteVideoTrackPublication remoteVideoTrackPublication) {
                isOtherPersonVideoEnabled = false;
                setAudioVideoViewsStatusAndVisibilityOfDoctor();

            }
        };
    }

    private void setAudioVideoViewsStatusAndVisibilityOfDoctor() {
        if (!isOtherPersonMicEnabled && !isOtherPersonVideoEnabled) {
            doctorVideoAndMicStatusMessageTextView.setVisibility(View.VISIBLE);
            doctorVideoAndMicStatusMessageTextView.setText(appointment.getDoctorName() + " has disabled video and mic");
            doctorBlurImageView.setVisibility(View.VISIBLE);
            doctorVideoStatusIcon.setVisibility(View.VISIBLE);
            doctorMicStatusIcon.setVisibility(View.VISIBLE);
        } else if (!isOtherPersonMicEnabled && isOtherPersonVideoEnabled) {
            doctorVideoAndMicStatusMessageTextView.setVisibility(View.VISIBLE);
            doctorVideoAndMicStatusMessageTextView.setText(appointment.getDoctorName() + " has disabled mic");
            doctorBlurImageView.setVisibility(View.GONE);
            doctorVideoStatusIcon.setVisibility(View.GONE);
            doctorMicStatusIcon.setVisibility(View.VISIBLE);
        } else if (isOtherPersonMicEnabled && !isOtherPersonVideoEnabled) {
            doctorVideoAndMicStatusMessageTextView.setVisibility(View.VISIBLE);
            doctorVideoAndMicStatusMessageTextView.setText(appointment.getDoctorName() + " has disabled video");
            doctorBlurImageView.setVisibility(View.VISIBLE);
            doctorVideoStatusIcon.setVisibility(View.VISIBLE);
            doctorMicStatusIcon.setVisibility(View.GONE);
        } else {
            doctorVideoAndMicStatusMessageTextView.setVisibility(View.GONE);
            doctorBlurImageView.setVisibility(View.GONE);
            doctorVideoStatusIcon.setVisibility(View.GONE);
            doctorMicStatusIcon.setVisibility(View.GONE);
        }
    }

    private void setAudioVideoViewsStatusAndVisibilityOfPatient() {
        if (!isMyMicEnabled && !isMyVideoEnabled) {
            patientVideoAndMicStatusMessageTextView.setVisibility(View.VISIBLE);
            patientVideoAndMicStatusMessageTextView.setText("Your video and mic are disabled");
            patientBlurImageView.setVisibility(View.VISIBLE);
            patientVideoStatusIcon.setVisibility(View.VISIBLE);
            patientMicStatusIcon.setVisibility(View.VISIBLE);
        } else if (!isMyMicEnabled && isMyVideoEnabled) {
            patientVideoAndMicStatusMessageTextView.setVisibility(View.VISIBLE);
            patientVideoAndMicStatusMessageTextView.setText("Your mic is disabled");
            patientBlurImageView.setVisibility(View.GONE);
            patientVideoStatusIcon.setVisibility(View.GONE);
            patientMicStatusIcon.setVisibility(View.VISIBLE);
        } else if (isMyMicEnabled && !isMyVideoEnabled) {
            patientVideoAndMicStatusMessageTextView.setVisibility(View.VISIBLE);
            patientVideoAndMicStatusMessageTextView.setText("Your video is disabled");
            patientBlurImageView.setVisibility(View.VISIBLE);
            patientVideoStatusIcon.setVisibility(View.VISIBLE);
            patientMicStatusIcon.setVisibility(View.GONE);
        } else {
            patientVideoAndMicStatusMessageTextView.setVisibility(View.GONE);
            patientBlurImageView.setVisibility(View.GONE);
            patientVideoStatusIcon.setVisibility(View.GONE);
            patientMicStatusIcon.setVisibility(View.GONE);
        }
    }


    DialogInterface.OnClickListener onClickListener = (dialogInterface, i) -> {
        if (dialogInterface != null) {
            dialogInterface.cancel();
        }
    };

    private DialogInterface.OnClickListener connectClickListener(final EditText roomEditText) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*
                 * Connect to room
                 */
//                LiveStreamingActivity2.this.connectToRoom(roomEditText.getText().toString());
            }
        };
    }

//    private View.OnClickListener connectActionClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VideoCallActivity.this.showConnectDialog();
//            }
//        };
//    }

    private DialogInterface.OnClickListener cancelConnectDialogClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                LiveStreamingActivity2.this.intializeUI();
                connectDialog.dismiss();
            }
        };

    }

    private View.OnClickListener switchCameraClickListener() {
        return v -> {
            if (cameraCapturerCompat != null) {
                CameraCapturerCompat.Source cameraSource = cameraCapturerCompat.getCameraSource();
                cameraCapturerCompat.switchCamera();
                if (thumbnailVideoView.getVisibility() == View.VISIBLE) {
                    thumbnailVideoView.setMirror(
                            cameraSource == CameraCapturerCompat.Source.BACK_CAMERA);
                } else {
                    primaryVideoView.setMirror(
                            cameraSource == CameraCapturerCompat.Source.BACK_CAMERA);
                }
            }
        };
    }

    private View.OnClickListener localVideoClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMyVideoEnabled) {
                    if (!((VideoCallActivity.this).isFinishing())) {
                        if (isMyVideoEnabled) {
                            AlertWindowConfirmationOC dialog = new AlertWindowConfirmationOC(VideoCallActivity.this, ocAlertBoxListener, AppConstants.OCAlertBoxKeys.VIDEO, "Are you sure you want to \ndisable your video?", "Cancel", "Disable", true);
                            dialog.show();
                        } else {
                            disableVideo();
                        }
                    }
                } else {
                    disableVideo();
                }
            }
        };
    }

    private void muteMic() {
        if (localAudioTrack != null) {
            isMyMicEnabled = !localAudioTrack.isEnabled();
            localAudioTrack.enable(isMyMicEnabled);
            int icon = isMyMicEnabled ?
                    R.drawable.enable_mic_blue : R.drawable.disable_mic_blue;
            switchMicButton.setBackgroundResource(icon);
            setAudioVideoViewsStatusAndVisibilityOfPatient();
        }
    }

    private void disableVideo() {
        if (!isMyVideoEnabled && localVideoTrack == null) {
            //When video is disabled and user comes back from pip mode in this localVideoTrack is null because we didn't recreate in on Resume
            createLocalVideoTrackAndPublish();
            localVideoTrack.enable(isMyVideoEnabled);
            if (localParticipant != null) {
                localParticipant.publishTrack(localVideoTrack);
            }
        }

        if (localVideoTrack != null) {
            boolean enable = !localVideoTrack.isEnabled();
            localVideoTrack.enable(enable);
            isMyVideoEnabled = enable;
            int icon = enable ? R.drawable.enable_video_blue : R.drawable.disable_video_blue;
            switchLocalVideoButton.setBackgroundResource(icon);
            if (isMyVideoEnabled) {
                patientBlurImageView.setVisibility(View.GONE);
            } else {
                patientBlurImageView.setVisibility(View.VISIBLE);
            }
        }
        setAudioVideoViewsStatusAndVisibilityOfPatient();
    }

    private View.OnClickListener muteClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localAudioTrack.isEnabled()) {
                    if (!((VideoCallActivity.this).isFinishing())) {
                        if (isMyMicEnabled) {
                            AlertWindowConfirmationOC dialog = new AlertWindowConfirmationOC(VideoCallActivity.this, ocAlertBoxListener, AppConstants.OCAlertBoxKeys.MIC, "Are you sure you want to \nmute your mic?", "Cancel", "Mute", true);
                            dialog.show();
                        } else {
                            muteMic();
                        }
                    }
                } else {
                    muteMic();
                }
            }
        };
    }

    private void configureAudio(boolean enable) {
        if (enable) {
            previousAudioMode = audioManager.getMode();
            // Request audio focus before making any device switch
            requestAudioFocus();
            /*
             * Use MODE_IN_COMMUNICATION as the default audio mode. It is required
             * to be in this mode when playout and/or recording starts for the best
             * possible VoIP performance. Some devices have difficulties with
             * speaker mode if this is not set.
             */
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            /*
             * Always disable microphone mute during a WebRTC call.
             */
            previousMicrophoneMute = audioManager.isMicrophoneMute();
            audioManager.setMicrophoneMute(false);
        } else {
            audioManager.setMode(previousAudioMode);
            audioManager.abandonAudioFocus(null);
            audioManager.setMicrophoneMute(previousMicrophoneMute);
        }
    }

    private void requestAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            AudioFocusRequest focusRequest =
                    new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                            .setAudioAttributes(playbackAttributes)
                            .setAcceptsDelayedFocusGain(true)
                            .setOnAudioFocusChangeListener(
                                    new AudioManager.OnAudioFocusChangeListener() {
                                        @Override
                                        public void onAudioFocusChange(int i) {
                                        }
                                    })
                            .build();
            audioManager.requestAudioFocus(focusRequest);
        } else {
            audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
    }

    private DialogInterface.OnClickListener goToSettingsClickerListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            MarhamUtils.getInstance().openPermissionSetting(VideoCallActivity.this);
            finish();

        }
    };

    private DialogInterface.OnClickListener cancelClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finish();
        }
    };


    public View getParentLayout() {
        return parentLayout;
    }

    public void connectToRoomForLiveStreaming(TokenAndRoomServerResponse tokenAndRoomServerResponse) {
        connectToRoom(tokenAndRoomServerResponse.getData().getRoom(), tokenAndRoomServerResponse.getData().getToken());
        intializeUI();
    }


    @Override
    public void onBackPressed() {
        if (hasCallEnded) {
            openUserPanel();
        } else {
            handleBackPressWhenCallInProgress();
        }
    }

    private void handleBackPressWhenCallInProgress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && MarhamUtils.getInstance().checkPictureInPictureModePermissionisGranted(this) && isUserRendered) {
            enablePictureInPictureMode();
        } else {
            showCallWillEndWarning();
        }
    }

    private void showCallWillEndWarning() {
        if (!((Activity) VideoCallActivity.this).isFinishing()) {
            MarhamUtils.getInstance().showConfirmationMessage(VideoCallActivity.this, "Warning!", "Going back will disconnect your\ncall. Are you sure you want to disconnect the call ?", "Cancel", "Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    openUserPanel();
                }
            }, true);
        }
    }

    private void setAudioSetting() {
        audioSwitch = new AudioSwitch(this, true);
        audioSwitch.start(this);
        audioSwitch.activate();
    }

    @Override
    public Unit invoke(List<? extends AudioDevice> audioDevices, AudioDevice audioDevice) {
        List<AudioDevice> availableAudioDevices = audioSwitch.getAvailableAudioDevices();

        AudioDevice earPieceDevice = new AudioDevice.Earpiece();
        int selectedDeviceIndex = availableAudioDevices.indexOf(earPieceDevice);

        if (selectedDeviceIndex == 0) {
            AudioDevice mAD = availableAudioDevices.get(1);
            audioSwitch.selectDevice(mAD);
        } else {
            AudioDevice mAD = availableAudioDevices.get(0);
            audioSwitch.selectDevice(mAD);
        }
        return null;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    ///////.........
    public String getConnectionQuality(Context context) {
        NetworkInfo info = getInfo(context);
        if (info == null || !info.isConnected()) {
            return "UNKNOWN";
        }

        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            int numberOfLevels = 5;
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);
            if (level == 2)
                return "POOR";
            else if (level == 3)
                return "MODERATE";
            else if (level == 4)
                return "GOOD";
            else if (level == 5)
                return "EXCELLENT";
            else
                return "UNKNOWN";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkClass = getNetworkClass(getNetworkType(context));
            if (networkClass == 1)
                return "POOR";
            else if (networkClass == 2)
                return "GOOD";
            else if (networkClass == 3)
                return "EXCELLENT";
            else
                return "UNKNOWN";
        } else
            return "UNKNOWN";
    }

    public NetworkInfo getInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    public int getNetworkClass(int networkType) {
        try {
            return getNetworkClassReflect(networkType);
        } catch (Exception ignored) {
        }

        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case 16: // TelephonyManager.NETWORK_TYPE_GSM:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 1;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case 17: // TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                return 2;
            case TelephonyManager.NETWORK_TYPE_LTE:
            case 18: // TelephonyManager.NETWORK_TYPE_IWLAN:
                return 3;
            default:
                return 0;
        }
    }

    private int getNetworkClassReflect(int networkType) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getNetworkClass = TelephonyManager.class.getDeclaredMethod("getNetworkClass", int.class);
        if (!getNetworkClass.isAccessible()) {
            getNetworkClass.setAccessible(true);
        }
        return (Integer) getNetworkClass.invoke(null, networkType);
    }

    public int getNetworkType(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected  to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return 1;
                } else return 2;
            }
        }
        return 1;
    }

    private BroadcastReceiver internetStrengthReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getConnectionQuality(context)) {
                case "POOR":
                    videoFormat = new VideoFormat(VideoDimensions.CIF_VIDEO_DIMENSIONS, 15);
                    setVisibilityStateOfPoorConnectionContainer(true);
                    internetStatusMessageTextView.setText("You have a poor internet connection");
                    setVisibilityAndViewsOfNoInternetViewsContainer(View.GONE, "No Internet Connection!", "Please check your internet connection and try again.", 0);
                    if (isMyVideoEnabled) {
                        disableVideo();
                    }
                    break;

                case "MODERATE":
                    videoFormat = new VideoFormat(VideoDimensions.VGA_VIDEO_DIMENSIONS, 30);
                    setVisibilityStateOfPoorConnectionContainer(false);
//                    internetStatusMessageTextView.setText("You have a moderate internet connection");
                    setVisibilityAndViewsOfNoInternetViewsContainer(View.GONE, "No Internet Connection!", "Please check your internet connection and try again.", 0);
                    break;

                case "GOOD":
                    videoFormat = new VideoFormat(VideoDimensions.VGA_VIDEO_DIMENSIONS, 30);
                    setVisibilityStateOfPoorConnectionContainer(false);
                    setVisibilityAndViewsOfNoInternetViewsContainer(View.GONE, "No Internet Connection!", "Please check your internet connection and try again.", 0);

                    break;

                case "EXCELLENT":
                    videoFormat = new VideoFormat(VideoDimensions.HD_720P_VIDEO_DIMENSIONS, 60);
                    setVisibilityStateOfPoorConnectionContainer(false);
                    setVisibilityAndViewsOfNoInternetViewsContainer(View.GONE, "No Internet Connection!", "Please check your internet connection and try again.", 0);

                    break;

                case "UNKNOWN":
                    videoFormat = new VideoFormat(VideoDimensions.HD_720P_VIDEO_DIMENSIONS, 60);
                    setVisibilityStateOfPoorConnectionContainer(false);
                    setVisibilityAndViewsOfNoInternetViewsContainer(View.VISIBLE, "No Internet Connection!", "Please check your internet connection and try again.", 0);


                    break;
            }
        }
    };


    public void setVisibilityAndViewsOfNoInternetViewsContainer(int visibilityStatus, String headingTextViewText, String subHeadingTextViewText, int imageId) {
        noInternetViewsContainer.setVisibility(visibilityStatus);
        noInternetHeadingTextView.setText(headingTextViewText);
        noInternetSubHeadingTextView.setText(subHeadingTextViewText);
        noInternetImageView.setImageResource(imageId);
    }

    private OCAlertBoxListener ocAlertBoxListener = new OCAlertBoxListener() {
        @Override
        public void onCancelled(int requestType) {

        }

        @Override
        public void onProcees(int requestType) {
            switch (requestType) {
                case AppConstants.OCAlertBoxKeys.VIDEO:
                    disableVideo();
                    break;
                case AppConstants.OCAlertBoxKeys.MIC:
                    muteMic();
                    break;
                case AppConstants.OCAlertBoxKeys.END_CALL:
                    onCallEnded();
                    break;

            }
        }
    };

    private void onCallEnded() {
        hasCallEnded = true;
        sendOnlineConsultationSignal(AppConstants.NOTIFICATIONS.PUSH_NOTIFICATIONS.PATIENT_ENDED_CALL);
        updateUserAppointmentSubStatus("5");
        MarhamUtils.getInstance().showLoader(this, "Ending Call", null);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MarhamUtils.getInstance().startActivity(VideoCallActivity.this, VideoCallEndedActivity.class, true);
            }
        }, 4000);

    }

    private void enablePictureInPictureMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isUserRendered) {
            final Rect sourceRectHint = new Rect();
//            final Rational aspectRatio = new Rational(9, 16);
            final Rational aspectRatio = getPipRatio();

            PictureInPictureParams params;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                params = new PictureInPictureParams.Builder()
                        .setAspectRatio(aspectRatio)
                        .setSourceRectHint(sourceRectHint)
                        .setAutoEnterEnabled(true).setSeamlessResizeEnabled(true)
                        .build();

            } else {
                params = new PictureInPictureParams.Builder()
                        .setAspectRatio(aspectRatio)
                        .setSourceRectHint(sourceRectHint)
                        .build();
            }

            enterPictureInPictureMode(params);
        } else {
            showCallWillEndWarning();
        }
    }

    public Rational getPipRatio() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return new Rational(Math.round(metrics.xdpi), Math.round(metrics.ydpi));
    }

    @Override
    protected void onUserLeaveHint() {
        if (ringerViewsContainer.getVisibility() == View.VISIBLE) {
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !hasCallEnded) {
                enablePictureInPictureMode();
            }
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        misInPictureInPictureMode = isInPictureInPictureMode;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
            if (isInPictureInPictureMode) {
                setPIPViewsVisibility(View.GONE);
            } else {
                setPIPViewsVisibility(View.VISIBLE);
                if (isMyVideoEnabled) {
                    patientBlurImageView.setVisibility(View.GONE);
                } else {
                    patientBlurImageView.setVisibility(View.VISIBLE);
                }
            }

            if (getLifecycle().getCurrentState() == Lifecycle.State.CREATED) {
                openUserPanel();
                finishAndRemoveTask();
                //when user click on Close button of PIP this will trigger, do what you want here
            } else if (getLifecycle().getCurrentState() == Lifecycle.State.STARTED) {
                //when PIP maximize this will trigger
            }
            super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        }

    }

    private void setPIPViewsVisibility(int visibilityStatus) {
        actionButtonsContainer.setVisibility(visibilityStatus);
        thumbnailVideoView.setVisibility(visibilityStatus);
        internetStatusMessageTextView.setVisibility(visibilityStatus);
        drVideoAndMicStatusViewsContainer.setVisibility(visibilityStatus);
        patientVideoAndMicStatusViewsContainer.setVisibility(visibilityStatus);
    }


    // Ringer Screen Code
    private void allowPermissionForCallingEvenWhenTheScreenIsLocked() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            Log.e("Wazzah", "Oreo");
            setTurnScreenOn(true);
            setShowWhenLocked(true);

        } else {
            Log.e("Wazzah", "Non Orea");
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        }

    }

    private void playRingTone() {
        try {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone = RingtoneManager.getRingtone(this, uri);
            ringtone.play();

        } catch (Exception e) {
            Log.e("Wazzah Ringtone", e.toString());
        }
        startTimer();
    }


    private void stopRingTone() {
        VibratorClass.getVibratorInstance().stopVibration();
        if (ringtone != null) {
            ringtone.stop();
        }
        stopTimer();
    }

    private void startTimer() {
        ringerHandler = new Handler();
        ringerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                VideoCallActivity.this.finish();
            }
        }, 30000);


    }

    private void stopTimer() {
        if (ringerHandler != null) {
            ringerHandler.removeCallbacksAndMessages(null);
        }
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
            case AppConstants.PERMISSIONS.PermissionTypes.CAMERA_AND_GALLERY_PERMISSION:
                finish();
                break;
        }
    }

    //Navigation
    public void openUserPanel() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Appointment.class.getCanonicalName(), appointment);
        MarhamUtils.getInstance().startActivity(this, VideoCallActivity.class, true, bundle);
    }

    public void sendOnlineConsultationSignal(String notificationType) {
        HashMap<String, String> info = new HashMap<>();
        APIClient apiClient = new APIClient();

        info.put(AppConstants.API.API_KEYS.APPLICATION_TYPE_KEY, AppConstants.API.APPLICATION_TYPE.TELENOR);
        info.put(AppConstants.API.API_KEYS.ONLINE_CONSULATATION_ID_KEY, appointment.getOnlineConsultationId());
        info.put(AppConstants.API.API_KEYS.NOTIFICATION_TYPE_KEY, notificationType);
        info.put(AppConstants.API.API_KEYS.SEND_TO_PATIENT_KEY, "0");
        info.put(AppConstants.API.API_KEYS.SEND_TO_DOCTOR_KEY, "1");

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

    public void getTokenFromServer() {
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

    public void updateUserAppointmentSubStatus(String appointmentSubStatus) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.APPOINTMENT_ID_KEY, appointment.getApptID());
        hashMap.put(AppConstants.API.API_KEYS.STATUS_KEY, appointmentSubStatus);
        hashMap.put(AppConstants.API.API_KEYS.LOGGED_IN_USER_ID_KEY, MarhamVideoCallHelper.getInstance().getUserId());
        APIClient apiClient = new APIClient();
        Call<ServerResponseOld> call = apiClient.updateUserAppointmentSubStatus(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.UPDATE_APPOINTMENT_SUB_STATUS);
        call.enqueue(retroFit2Callback);
    }

    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {

            case AppConstants.API.API_END_POINT_NUMBER.GET_ONLINE_CONSULTATION_TOKEN:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    TokenAndRoomServerResponse tokenAndRoomServerResponse = (TokenAndRoomServerResponse) response;
                    this.accessToken = tokenAndRoomServerResponse.getData().getToken();
                    this.roomString = tokenAndRoomServerResponse.getData().getRoom();
                    connectToRoomForLiveStreaming(tokenAndRoomServerResponse);
                } else {
                    Toast.makeText(this, response.getMessage(), Toast.LENGTH_LONG).show();
                    this.finish();
                }
                break;

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
            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_APPOINTMENT_SUB_STATUS:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    MarhamUtils.getInstance().generateLog("Appointment sub status updated");
                }
                break;
        }
    }

    @Override
    public void onFailure(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.SEND_ONLINE_CONSULTATION_NOTIFICATION_SIGNAL:
                MarhamUtils.getInstance().generateLog("Consultation Signal Not Sent");
                break;
            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_LAST_SEEN_OF_USER:
                MarhamUtils.getInstance().generateLog("Could not update last seen of user");
                break;
            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_APPOINTMENT_SUB_STATUS:
                MarhamUtils.getInstance().generateLog("Appointment sub status not updated");
                break;
        }

    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.SEND_ONLINE_CONSULTATION_NOTIFICATION_SIGNAL:
                MarhamUtils.getInstance().generateLog("Consultation Signal Not Sent");
                break;
            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_LAST_SEEN_OF_USER:
                MarhamUtils.getInstance().generateLog("Could not update last seen of user");
                break;
            case AppConstants.API.API_END_POINT_NUMBER.UPDATE_APPOINTMENT_SUB_STATUS:
                MarhamUtils.getInstance().generateLog("Appointment sub status not updated");
                break;
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return false;
    }
}