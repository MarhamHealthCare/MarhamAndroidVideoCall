package com.marham.marhamvideocalllibrary.network;

import android.content.Context;

import com.marham.marhamvideocalllibrary.model.ServerResponse;
import com.marham.marhamvideocalllibrary.utils.AppConstants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroFit2Callback<T> implements Callback {
    private boolean isCanceled;
    private ServerConnectListener listener;
    private Context context;
    private int requestCode;

    public RetroFit2Callback(ServerConnectListener listener, int requestCode) {
        this.listener = listener;
        this.requestCode = requestCode;
    }

    public RetroFit2Callback(ServerConnectListener listener, Context context, int requestCode) {
        this.listener = listener;
        this.context = context;
        this.requestCode = requestCode;
    }

    public RetroFit2Callback(ServerConnectListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void cancel() {
        isCanceled = true;
    }

    public boolean isCancelled() {
        return isCanceled;
    }


    private void returnErrorObject(Throwable throwable) {

        ServerResponse errorResponse = new ServerResponse();
        errorResponse.setRequestCode(requestCode);

        if (throwable instanceof IOException) {
            // Internet Unavailable
            errorResponse.setMessage(AppConstants.API.API_ERROR_MESSAGE.NETWORK_ERROR_MESSAGE);
        } else {
            // JSON parsing error
            errorResponse.setMessage(AppConstants.API.API_ERROR_MESSAGE.JSON_PARSING_MESSAGE);
        }

        listener.onFailure(errorResponse);

    }


    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) {
        if (isCancelled()) {
            return;
        }

        if (response.isSuccessful() && response.body() != null) {
            ServerResponse callResponse = (ServerResponse) response.body();
            callResponse.setRequestCode(requestCode);
            listener.onSuccess(callResponse);

        } else {
            returnErrorObject(null);
        }

    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull Throwable t) {
        returnErrorObject(t);
    }

}
