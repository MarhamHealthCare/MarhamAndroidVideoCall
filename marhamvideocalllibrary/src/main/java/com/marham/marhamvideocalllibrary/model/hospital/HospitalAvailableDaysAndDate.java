package com.marham.marhamvideocalllibrary.model.hospital;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.doctor.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.doctor.TimeSlotsOfHospitalContainer;

import java.util.List;

@Keep
public class HospitalAvailableDaysAndDate {

    private List<Hospital> hospitals;

    private TimeSlotsOfHospitalContainer thirdSlots;

    private String showWallet;

    private DoctorInfo doctor;

    private TimeSlotsOfHospitalContainer secondSlots;

    private String patientCarePackageDetails;

    private String patientCarePackageTitle;

    private TimeSlotsOfHospitalContainer firstSlots;

    private List<Days> days;

    private String patientCarePackageDescription;

    private String walletTermsAndConditions;

    private String walletAmount;

    public List<Hospital> getHospitals ()
    {
        return hospitals;
    }

    public void setHospitals (List<Hospital> hospitals)
    {
        this.hospitals = hospitals;
    }

    public TimeSlotsOfHospitalContainer getThirdSlots ()
    {
        return thirdSlots;
    }

    public void setThirdSlots (TimeSlotsOfHospitalContainer thirdSlots)
    {
        this.thirdSlots = thirdSlots;
    }

    public String getShowWallet ()
    {
        return showWallet;
    }

    public void setShowWallet (String showWallet)
    {
        this.showWallet = showWallet;
    }

    public TimeSlotsOfHospitalContainer getSecondSlots ()
    {
        return secondSlots;
    }

    public void setSecondSlots (TimeSlotsOfHospitalContainer secondSlots)
    {
        this.secondSlots = secondSlots;
    }

    public String getPatientCarePackageDetails ()
    {
        return patientCarePackageDetails;
    }

    public void setPatientCarePackageDetails (String patientCarePackageDetails)
    {
        this.patientCarePackageDetails = patientCarePackageDetails;
    }

    public String getPatientCarePackageTitle ()
    {
        return patientCarePackageTitle;
    }

    public void setPatientCarePackageTitle (String patientCarePackageTitle)
    {
        this.patientCarePackageTitle = patientCarePackageTitle;
    }

    public TimeSlotsOfHospitalContainer getFirstSlots ()
    {
        return firstSlots;
    }

    public void setFirstSlots (TimeSlotsOfHospitalContainer firstSlots)
    {
        this.firstSlots = firstSlots;
    }

    public List<Days> getDays ()
    {
        return days;
    }

    public void setDays (List<Days> days)
    {
        this.days = days;
    }

    public String getPatientCarePackageDescription ()
    {
        return patientCarePackageDescription;
    }

    public void setPatientCarePackageDescription (String patientCarePackageDescription)
    {
        this.patientCarePackageDescription = patientCarePackageDescription;
    }

    public String getWalletTermsAndConditions ()
    {
        return walletTermsAndConditions;
    }

    public void setWalletTermsAndConditions (String walletTermsAndConditions)
    {
        this.walletTermsAndConditions = walletTermsAndConditions;
    }

    public DoctorInfo getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorInfo doctor) {
        this.doctor = doctor;
    }

    public String getWalletAmount ()
    {
        return walletAmount;
    }

    public void setWalletAmount (String walletAmount)
    {
        this.walletAmount = walletAmount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [hospitals = "+hospitals+", thirdSlots = "+thirdSlots+", showWallet = "+showWallet+", secondSlots = "+secondSlots+", patientCarePackageDetails = "+patientCarePackageDetails+", patientCarePackageTitle = "+patientCarePackageTitle+", firstSlots = "+firstSlots+", days = "+days+", patientCarePackageDescription = "+patientCarePackageDescription+", walletTermsAndConditions = "+walletTermsAndConditions+", walletAmount = "+walletAmount+"]";
    }

}
