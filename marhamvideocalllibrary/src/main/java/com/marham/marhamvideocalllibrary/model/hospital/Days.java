package com.marham.marhamvideocalllibrary.model.hospital;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.doctor.TimeSlotsOfHospitalContainer;

@Keep
public class Days {
    private String date;

    private String month;

    private String dayOfMonth;

    private String formattedDate;

    private TimeSlotsOfHospitalContainer timeSlots;

    private String day;

    private boolean isSelected = false;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public TimeSlotsOfHospitalContainer getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(TimeSlotsOfHospitalContainer timeSlots) {
        this.timeSlots = timeSlots;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "ClassPojo [date = " + date + ", month = " + month + ", dayOfMonth = " + dayOfMonth + ", formattedDate = " + formattedDate + ", timeSlots = " + timeSlots + ", day = " + day + "]";
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
