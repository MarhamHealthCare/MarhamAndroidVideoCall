package com.marham.marhamvideocalllibrary.model.doctor;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;

@Keep
public class AllDoctorResponse extends ServerResponseOld {

    private DoctorObjectWithCMD data;


    public DoctorObjectWithCMD getData ()
    {
        return data;
    }

    public void setData (DoctorObjectWithCMD data)
    {
        this.data = data;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", return_status = "+"]";
    }

}
