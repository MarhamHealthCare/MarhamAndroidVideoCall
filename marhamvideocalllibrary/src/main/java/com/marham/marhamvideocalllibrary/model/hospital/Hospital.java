package com.marham.marhamvideocalllibrary.model.hospital;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import java.util.List;

@Keep
public class Hospital implements Parcelable{
    public static final String BUNDLE_KEY = Hospital.class.getSimpleName();

    private String showWallet;
    private String walletAmount;
    private String isOnlinePaymentEnabled;
    //Used Values
    private boolean isSelected = false;// used in Book Doctor Appointment Screen
    private List<String> phoneNumbers;
    private String todayTimming;
    private String availableToday;
    private String availableDays;
    private String availablityInfo;
    private String hospitalName;
    private String startTime;
    private String endTime;
    private String onCall;
    private String docFee;
    private String hospitalAddress;
    private String hospitalArea;
    private String hospitalCity;
    private String monday;
    private String tuesday;
    private String hospitalType;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    private String lng;
    private String lat;
    private String distance_in_km;
    private String fix_fee;
    private String date;
    private String apptPhone;
    private String dID;
    private String doctorHospitalID;
    private String hospitalID;
    private String timeID;
    private String discount;
    private String isCallMyDoctor;
    private int visibilityState = 0;
    private String isHospitalSelected = "0";
    private String speciality_id;
    private String speciality_name;
    private String discountFee;

    protected Hospital(Parcel in) {
        showWallet = in.readString();
        walletAmount = in.readString();
        isOnlinePaymentEnabled = in.readString();
        isSelected = in.readByte() != 0;
        phoneNumbers = in.createStringArrayList();
        todayTimming = in.readString();
        availableToday = in.readString();
        availableDays = in.readString();
        availablityInfo = in.readString();
        hospitalName = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        onCall = in.readString();
        docFee = in.readString();
        hospitalAddress = in.readString();
        hospitalArea = in.readString();
        hospitalCity = in.readString();
        monday = in.readString();
        tuesday = in.readString();
        hospitalType = in.readString();
        wednesday = in.readString();
        thursday = in.readString();
        friday = in.readString();
        saturday = in.readString();
        sunday = in.readString();
        lng = in.readString();
        lat = in.readString();
        distance_in_km = in.readString();
        fix_fee = in.readString();
        date = in.readString();
        apptPhone = in.readString();
        dID = in.readString();
        doctorHospitalID = in.readString();
        hospitalID = in.readString();
        timeID = in.readString();
        discount = in.readString();
        isCallMyDoctor = in.readString();
        visibilityState = in.readInt();
        isHospitalSelected = in.readString();
        speciality_id = in.readString();
        speciality_name = in.readString();
        discountFee = in.readString();
    }

    public static final Parcelable.Creator<Hospital> CREATOR = new Parcelable.Creator<Hospital>() {
        @Override
        public Hospital createFromParcel(Parcel in) {
            return new Hospital(in);
        }

        @Override
        public Hospital[] newArray(int size) {
            return new Hospital[size];
        }
    };

    public String getShowWallet() {
        return showWallet;
    }

    public void setShowWallet(String showWallet) {
        this.showWallet = showWallet;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getIsOnlinePaymentEnabled() {
        return isOnlinePaymentEnabled;
    }

    public void setIsOnlinePaymentEnabled(String isOnlinePaymentEnabled) {
        this.isOnlinePaymentEnabled = isOnlinePaymentEnabled;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getTodayTimming() {
        return todayTimming;
    }

    public void setTodayTimming(String todayTimming) {
        this.todayTimming = todayTimming;
    }

    public String getAvailableToday() {
        return availableToday;
    }

    public void setAvailableToday(String availableToday) {
        this.availableToday = availableToday;
    }

    public String getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(String availableDays) {
        this.availableDays = availableDays;
    }

    public String getAvailablityInfo() {
        return availablityInfo;
    }

    public void setAvailablityInfo(String availablityInfo) {
        this.availablityInfo = availablityInfo;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOnCall() {
        return onCall;
    }

    public void setOnCall(String onCall) {
        this.onCall = onCall;
    }

    public String getDocFee() {
        return docFee;
    }

    public void setDocFee(String docFee) {
        this.docFee = docFee;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalArea() {
        return hospitalArea;
    }

    public void setHospitalArea(String hospitalArea) {
        this.hospitalArea = hospitalArea;
    }

    public String getHospitalCity() {
        return hospitalCity;
    }

    public void setHospitalCity(String hospitalCity) {
        this.hospitalCity = hospitalCity;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getDistance_in_km() {
        return distance_in_km;
    }

    public void setDistance_in_km(String distance_in_km) {
        this.distance_in_km = distance_in_km;
    }

    public String getFix_fee() {
        return fix_fee;
    }

    public void setFix_fee(String fix_fee) {
        this.fix_fee = fix_fee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApptPhone() {
        return apptPhone;
    }

    public void setApptPhone(String apptPhone) {
        this.apptPhone = apptPhone;
    }

    public String getdID() {
        return dID;
    }

    public void setdID(String dID) {
        this.dID = dID;
    }

    public String getDoctorHospitalID() {
        return doctorHospitalID;
    }

    public void setDoctorHospitalID(String doctorHospitalID) {
        this.doctorHospitalID = doctorHospitalID;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getTimeID() {
        return timeID;
    }

    public void setTimeID(String timeID) {
        this.timeID = timeID;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getIsCallMyDoctor() {
        return isCallMyDoctor;
    }

    public void setIsCallMyDoctor(String isCallMyDoctor) {
        this.isCallMyDoctor = isCallMyDoctor;
    }

    public int getVisibilityState() {
        return visibilityState;
    }

    public void setVisibilityState(int visibilityState) {
        this.visibilityState = visibilityState;
    }

    public String getIsHospitalSelected() {
        return isHospitalSelected;
    }

    public void setIsHospitalSelected(String isHospitalSelected) {
        this.isHospitalSelected = isHospitalSelected;
    }

    public String getSpeciality_id() {
        return speciality_id;
    }

    public void setSpeciality_id(String speciality_id) {
        this.speciality_id = speciality_id;
    }

    public String getSpeciality_name() {
        return speciality_name;
    }

    public void setSpeciality_name(String speciality_name) {
        this.speciality_name = speciality_name;
    }

    public String getDiscountFee() {
        return discountFee;
    }

    public static Parcelable.Creator<Hospital> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(showWallet);
        dest.writeString(walletAmount);
        dest.writeString(isOnlinePaymentEnabled);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeStringList(phoneNumbers);
        dest.writeString(todayTimming);
        dest.writeString(availableToday);
        dest.writeString(availableDays);
        dest.writeString(availablityInfo);
        dest.writeString(hospitalName);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(onCall);
        dest.writeString(docFee);
        dest.writeString(hospitalAddress);
        dest.writeString(hospitalArea);
        dest.writeString(hospitalCity);
        dest.writeString(monday);
        dest.writeString(tuesday);
        dest.writeString(hospitalType);
        dest.writeString(wednesday);
        dest.writeString(thursday);
        dest.writeString(friday);
        dest.writeString(saturday);
        dest.writeString(sunday);
        dest.writeString(lng);
        dest.writeString(lat);
        dest.writeString(distance_in_km);
        dest.writeString(fix_fee);
        dest.writeString(date);
        dest.writeString(apptPhone);
        dest.writeString(dID);
        dest.writeString(doctorHospitalID);
        dest.writeString(hospitalID);
        dest.writeString(timeID);
        dest.writeString(discount);
        dest.writeString(isCallMyDoctor);
        dest.writeInt(visibilityState);
        dest.writeString(isHospitalSelected);
        dest.writeString(speciality_id);
        dest.writeString(speciality_name);
        dest.writeString(discountFee);
    }
}
