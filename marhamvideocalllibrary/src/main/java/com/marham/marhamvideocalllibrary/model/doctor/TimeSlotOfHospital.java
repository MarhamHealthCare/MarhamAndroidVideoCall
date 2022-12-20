package com.marham.marhamvideocalllibrary.model.doctor;

import androidx.annotation.Keep;

@Keep
public class TimeSlotOfHospital {

    private String booked;

    private String doctorHospitalId;

    private String slot;

    private String timming;

    public String getBooked ()
    {
        return booked;
    }

    public void setBooked (String booked)
    {
        this.booked = booked;
    }

    public String getDoctorHospitalId ()
    {
        return doctorHospitalId;
    }

    public void setDoctorHospitalId (String doctorHospitalId)
    {
        this.doctorHospitalId = doctorHospitalId;
    }

    public String getSlot ()
    {
        return slot;
    }

    public void setSlot (String slot)
    {
        this.slot = slot;
    }

    public String getTimming ()
    {
        return timming;
    }

    public void setTimming (String timming)
    {
        this.timming = timming;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [booked = "+booked+", doctorHospitalId = "+doctorHospitalId+", slot = "+slot+", timming = "+timming+"]";
    }

}
