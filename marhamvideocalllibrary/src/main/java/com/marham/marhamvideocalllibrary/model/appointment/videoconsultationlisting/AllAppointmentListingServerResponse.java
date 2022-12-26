package com.marham.marhamvideocalllibrary.model.appointment.videoconsultationlisting;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
@Keep
public class AllAppointmentListingServerResponse extends ServerResponseOld {

    private AllAppointmentListingData data;


    public AllAppointmentListingData getData ()
    {
        return data;
    }

    public void setData (AllAppointmentListingData data)
    {
        this.data = data;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", return_status = "+", message = "+"]";
    }

}
