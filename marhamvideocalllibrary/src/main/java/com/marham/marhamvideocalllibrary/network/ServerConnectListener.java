package com.marham.marhamvideocalllibrary.network;

import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;

public interface ServerConnectListener {
    /**
     * Called if server call was successful
     *
     * @param response
     */

    void onSuccess(ServerResponseOld response);

    /**
     * Called if server call was failed.
     *
     * @param response
     */
    void onFailure(ServerResponseOld response);

    /**
     * Called if current user session expires.
     *
     * @param response
     */
    void onSessionExpiry(ServerResponseOld response);
}
