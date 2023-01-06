package com.marham.marhamvideocalllibrary.activities.payment;

import android.os.Bundle;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.appointments.AllVideoConsultationsScreenMainActivity;
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.model.appointment.Appointment;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;

import java.util.HashMap;

import retrofit2.Call;

public class PaymentActivity extends BaseActivity implements ServerConnectListener {

    private Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initVariables();
        informOurServerThatPaymentIsDone(appointment.getFee());
    }

    private void initVariables() {
        receiveIntent();
    }

    public void receiveIntent() {
        appointment = (Appointment) getIntent().getSerializableExtra(Appointment.class.getCanonicalName());
    }

    public void informOurServerThatPaymentIsDone(String finalAmountForOurServer) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.ORDER_ID_KEY, appointment.getOnlineConsultationId());
        hashMap.put(AppConstants.API.API_KEYS.PROGRAM_ID_KEY, AppConstants.PROGRAM_ID.ONLINE_CONSULTATION);
        //TODO: REPLACE TELENOR PAYMENT ID KEY
        hashMap.put(AppConstants.API.API_KEYS.PAYMENT_METHOD_ID_KEY, "36");
        hashMap.put(AppConstants.API.API_KEYS.USER_ID_KEY, MarhamVideoCallHelper.getInstance().getUserId());
        hashMap.put(AppConstants.API.API_KEYS.ORDER_AMOUNT_KEY, String.valueOf(finalAmountForOurServer));
        hashMap.put(AppConstants.API.API_KEYS.BIN_LOCKED_KEY, "0");
        hashMap.put(AppConstants.API.API_KEYS.WALLET_APPLIED_KEY, "0");
        hashMap.put(AppConstants.API.API_KEYS.APPLICATION_TYPE_KEY, AppConstants.API.APPLICATION_TYPE.TELENOR);
        hashMap.put(AppConstants.API.API_KEYS.DEVICE_TYPE_KEY, AppConstants.API.DEVICE_TYPE.ANDROID);

        APIClient apiClient = new APIClient();
        Call<ServerResponseOld> call = apiClient.saveOrderTransaction(hashMap);
        RetroFit2Callback<ServerResponseOld> retroFit2Callback;
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.SAVE_ORDER_TRANSACTION);
        call.enqueue(retroFit2Callback);
    }

    private void onTransactionFailed() {
        MarhamUtils.getInstance().generateLog("Could not save transaction");
        finish();
    }

    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.SAVE_ORDER_TRANSACTION:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    MarhamUtils.getInstance().generateLog("Transaction Saved");
                    MarhamUtils.getInstance().startActivity(this, AllVideoConsultationsScreenMainActivity.class, true);
                } else {
                    onTransactionFailed();
                }
                break;
        }
    }

    @Override
    public void onFailure(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.SAVE_ORDER_TRANSACTION:
                onTransactionFailed();
                break;
        }

    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.SAVE_ORDER_TRANSACTION:
                onTransactionFailed();
                break;
        }
    }
}