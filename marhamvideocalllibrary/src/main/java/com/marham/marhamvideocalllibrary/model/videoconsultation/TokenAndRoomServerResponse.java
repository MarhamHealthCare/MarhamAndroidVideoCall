package com.marham.marhamvideocalllibrary.model.videoconsultation;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;

@Keep
public class TokenAndRoomServerResponse extends ServerResponseOld {

    private TokenAndRoom data;

    public TokenAndRoom getData ()
    {
        return data;
    }

    public void setData (TokenAndRoom data)
    {
        this.data = data;
    }


}
