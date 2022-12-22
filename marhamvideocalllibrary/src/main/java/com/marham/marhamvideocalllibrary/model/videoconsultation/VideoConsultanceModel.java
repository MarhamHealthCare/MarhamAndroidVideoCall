package com.marham.marhamvideocalllibrary.model.videoconsultation;

import android.net.Uri;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class VideoConsultanceModel implements Serializable {


    private String userId;
    private String dId;
    private String spId;
    private int day;
    private String promoCode;
    private int month;
    private Uri evidenceFile;
    private String isFree;


    private int year;
    private String problem;
    private String uri;

    private String agreedForBooking;

    private String patientId;
    private String isCallMyDoctor;
    private String patientName;
    private String time;
    private String date;
    private String phone;

    private String docFee;
    private String payment_method_id;

    private String loggedInUserId;

    private String appType = "1";
    private String deviceType = "1";
    private boolean isDateSelected;
    private boolean isHospitalSelected;
    private String hospitalID;
    private String programId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getSpId() {
        return spId;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }


    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAgreedForBooking() {
        return agreedForBooking;
    }

    public void setAgreedForBooking(String agreedForBooking) {
        this.agreedForBooking = agreedForBooking;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getPatientID() {
        return patientId;
    }

    public void setPatientID(String patientID) {
        this.patientId = patientID;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDateSelected(boolean dateSelected) {
        isDateSelected = dateSelected;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setDocFee(String docFee) {
        this.docFee = docFee;
    }

    public String getDocFee() {
        return docFee;
    }

    public void setLoggedInUserId(String loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalID = hospitalId;
    }

    public void setHospitalSelected(boolean hospitalSelected) {
        isHospitalSelected = hospitalSelected;
    }

    public String getIsCallMyDoctor() {
        return isCallMyDoctor;
    }

    public void     setIsCallMyDoctor(String isCallMyDoctor) {
        this.isCallMyDoctor = isCallMyDoctor;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

}
