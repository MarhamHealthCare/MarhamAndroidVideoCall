package com.marham.marhamvideocalllibrary.model.hospital;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.doctor.TimeSlotsOfHospitalContainer;

@Keep
public class HospitalAvailableDaysAndDateServerResponse extends ServerResponseOld {

    private TimeSlotsOfHospitalContainer data;

    public TimeSlotsOfHospitalContainer getData ()
    {
        return data;
    }

    public void setData (TimeSlotsOfHospitalContainer data)
    {
        this.data = data;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", return_status = "+"]";
    }


}
