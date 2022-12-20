package com.marham.marhamvideocalllibrary.model.doctor;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.Hospital;
import com.marham.marhamvideocalllibrary.model.hospital.Days;

import java.util.List;

@Keep
public class TimeSlotsOfHospitalContainer {

    private List<TimeSlotOfHospital> afternoon;

    private List<Days> days;

    private List<TimeSlotOfHospital> evening;

    private List<TimeSlotOfHospital> morning;

    private String patientCarePackageTitle;

    private String patientCarePackageDescription;

    private String walletTermsAndConditions;

    private String patientCarePackageDetails;

    private String showWallet;

    private String walletAmount;

    private String docName;

    private DoctorInfo doctor;

    private Hospital hospitals;

    public List<TimeSlotOfHospital> getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(List<TimeSlotOfHospital> afternoon) {
        this.afternoon = afternoon;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public List<Days> getDays() {
        return days;
    }

    public void setDays(List<Days> days) {
        this.days = days;
    }

    public List<TimeSlotOfHospital> getEvening() {
        return evening;
    }

    public void setEvening(List<TimeSlotOfHospital> evening) {
        this.evening = evening;
    }

    public String getWalletTermsAndConditions() {
        return walletTermsAndConditions;
    }

    public void setWalletTermsAndConditions(String walletTermsAndConditions) {
        this.walletTermsAndConditions = walletTermsAndConditions;
    }

    public List<TimeSlotOfHospital> getMorning() {
        return morning;
    }

    public void setMorning(List<TimeSlotOfHospital> morning) {
        this.morning = morning;
    }

    public DoctorInfo getDoctorInfo() {
        return doctor;
    }

    public Hospital getHospital() {
        return hospitals;
    }

    public String getShowWallet() {
        return showWallet;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public String getPatientCarePackageTitle() {
        return patientCarePackageTitle;
    }

    public String getPatientCarePackageDescription() {
        return patientCarePackageDescription;
    }

    public String getPatientCarePackageDetails() {
        return patientCarePackageDetails;
    }


}
