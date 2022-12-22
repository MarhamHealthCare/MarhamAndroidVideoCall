package com.marham.marhamvideocalllibrary.model.general;

import androidx.annotation.Keep;

@Keep
public class ServerResponse extends ServerResponseOld{

    private String success;

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }


}
