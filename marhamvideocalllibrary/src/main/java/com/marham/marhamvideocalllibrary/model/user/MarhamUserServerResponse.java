package com.marham.marhamvideocalllibrary.model.user;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponse;

@Keep
public class MarhamUserServerResponse extends ServerResponse {

    private MarhamUser data;

    public MarhamUser getData ()
    {
        return data;
    }

    public void setData (MarhamUser data)
    {
        this.data = data;
    }

}
