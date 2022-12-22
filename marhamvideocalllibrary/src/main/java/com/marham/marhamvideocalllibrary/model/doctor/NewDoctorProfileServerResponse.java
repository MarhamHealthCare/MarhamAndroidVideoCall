package com.marham.marhamvideocalllibrary.model.doctor;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;

@Keep
public class NewDoctorProfileServerResponse extends ServerResponseOld {

    private DoctorProfileGenericData data;

    public DoctorProfileGenericData getData ()
    {
        return data;
    }

    public void setData (DoctorProfileGenericData data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", return_status = "+"]";
    }

}
