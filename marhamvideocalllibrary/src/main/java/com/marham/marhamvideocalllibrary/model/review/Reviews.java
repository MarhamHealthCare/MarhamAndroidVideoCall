package com.marham.marhamvideocalllibrary.model.review;

import androidx.annotation.Keep;

import java.io.Serializable;
@Keep
public class Reviews implements Serializable {

    private String hospitalType;

    private String date;

    private String hospitalEnvironment;

    private String staffScore;

    private String docFeedback;

    private String appointment_id;

    private String waitingTime;

    private String created_at;

    private String hospitalName;

    private String experience;

    private String type;

    private String reviewerName;

    private String approved;

    private String overallExperience;

    private String appointmentDuration;

    private String reviewID;

    private String appointmentDate;

    private String dID;

    private String easyToUse;

    private String diagnosisSatisfied;

    public String getEasyToUse() {
        return easyToUse;
    }

    public void setEasyToUse(String easyToUse) {
        this.easyToUse = easyToUse;
    }

    public String getDiagnosisSatisfied() {
        return diagnosisSatisfied;
    }

    public void setDiagnosisSatisfied(String diagnosisSatisfied) {
        this.diagnosisSatisfied = diagnosisSatisfied;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getHospitalEnvironment ()
    {
        return hospitalEnvironment;
    }

    public void setHospitalEnvironment (String hospitalEnvironment)
    {
        this.hospitalEnvironment = hospitalEnvironment;
    }

    public String getDocFeedback ()
    {
        return docFeedback;
    }

    public void setDocFeedback (String docFeedback)
    {
        this.docFeedback = docFeedback;
    }

    public String getAppointment_id ()
    {
        return appointment_id;
    }

    public void setAppointment_id (String appointment_id)
    {
        this.appointment_id = appointment_id;
    }

    public String getWaitingTime ()
    {
        return waitingTime;
    }

    public void setWaitingTime (String waitingTime)
    {
        this.waitingTime = waitingTime;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getHospitalName ()
    {
        return hospitalName;
    }

    public void setHospitalName (String hospitalName)
    {
        this.hospitalName = hospitalName;
    }

    public String getExperience ()
    {
        return experience;
    }

    public void setExperience (String experience)
    {
        this.experience = experience;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getdID() {
        return dID;
    }

    public String getStaffScore() {
        return staffScore;
    }

    public void setStaffScore(String staffScore) {
        this.staffScore = staffScore;
    }

    public void setdID(String dID) {
        this.dID = dID;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getReviewerName ()
    {
        return reviewerName;
    }

    public void setReviewerName (String reviewerName)
    {
        this.reviewerName = reviewerName;
    }

    public String getApproved ()
    {
        return approved;
    }

    public void setApproved (String approved)
    {
        this.approved = approved;
    }

    public String getOverallExperience ()
    {
        return overallExperience;
    }

    public void setOverallExperience (String overallExperience)
    {
        this.overallExperience = overallExperience;
    }

    public String getAppointmentDuration ()
    {
        return appointmentDuration;
    }

    public void setAppointmentDuration (String appointmentDuration)
    {
        this.appointmentDuration = appointmentDuration;
    }

    public String getReviewID ()
    {
        return reviewID;
    }

    public void setReviewID (String reviewID)
    {
        this.reviewID = reviewID;
    }

    public String getAppointmentDate ()
    {
        return appointmentDate;
    }

    public void setAppointmentDate (String appointmentDate)
    {
        this.appointmentDate = appointmentDate;
    }

    public String getDID ()
    {
        return dID;
    }

    public void setDID (String dID)
    {
        this.dID = dID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [date = "+date+", hospitalEnvironment = "+hospitalEnvironment+", docFeedback = "+docFeedback+", appointment_id = "+appointment_id+", waitingTime = "+waitingTime+", created_at = "+created_at+", hospitalName = "+hospitalName+", experience = "+experience+", type = "+type+", reviewerName = "+reviewerName+", approved = "+approved+", overallExperience = "+overallExperience+", appointmentDuration = "+appointmentDuration+", reviewID = "+reviewID+", appointmentDate = "+appointmentDate+", dID = "+dID+"]";
    }

}
