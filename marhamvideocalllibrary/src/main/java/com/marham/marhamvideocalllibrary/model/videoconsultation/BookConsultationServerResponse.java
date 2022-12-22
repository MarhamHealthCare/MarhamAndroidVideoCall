package com.marham.marhamvideocalllibrary.model.videoconsultation;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.ServerResponse;
import com.marham.marhamvideocalllibrary.model.appointment.Appointment;

@Keep
public class BookConsultationServerResponse extends ServerResponse {

    private String show_limit_popup;
    private Appointment data;

    public Appointment getData() {
        return data;
    }

    public String getShow_limit_popup() {
        return show_limit_popup;
    }

}
