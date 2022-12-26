package com.marham.marhamvideocalllibrary.model.appointment.videoconsultationlisting;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.appointment.Appointment;
import com.marham.marhamvideocalllibrary.model.order.GenericeOrderObject;

import java.util.List;

@Keep
public class AllAppointmentListingData {

    private List<GenericeOrderObject> past;

    private List<Appointment> report;

    private List<GenericeOrderObject> upcoming;

    public List<GenericeOrderObject> getPast ()
    {
        return past;
    }



    public void setPast (List<GenericeOrderObject> past)
    {
        this.past = past;
    }

    public List<Appointment> getReport ()
    {
        return report;
    }

    public void setReport (List<Appointment> report)
    {
        this.report = report;
    }

    public List<GenericeOrderObject> getUpcoming ()
    {
        return upcoming;
    }

    public void setUpcoming (List<GenericeOrderObject> upcoming)
    {
        this.upcoming = upcoming;
    }

}
