package com.marham.marhamvideocalllibrary.network;

import com.marham.marhamvideocalllibrary.model.ServerResponse;

public interface ServerConnectListener {
    /**
     * Called if server call was successful
     *
     * @param response
     */

    void onSuccess(ServerResponse response);

    /**
     * Called if server call was failed.
     *
     * @param response
     */
    void onFailure(ServerResponse response);

    /**
     * Called if current user session expires.
     *
     * @param response
     */
    void onSessionExpiry(ServerResponse response);
}
