package com.marham.marhamvideocalllibrary.model.order;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.appointment.Appointment;

@Keep
public class GenericeOrderObject {

    private Appointment onlineConsultation;

    private String programId;

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public Appointment getOnlineConsultation ()
    {
        return onlineConsultation;
    }

    public void setOnlineConsultation (Appointment onlineConsultation)
    {
        this.onlineConsultation = onlineConsultation;
    }

}
