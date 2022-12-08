package com.marham.marhamvideocalllibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import java.util.List;
@Keep
public class DoctorInfo implements Parcelable{
    public static final String BUNDLE_KEY = DoctorInfo.class.getSimpleName();


    //Used Values in DoctorAdapter
    private String doctorHospitalID;
    private List<DoctorInfo> cmdList;
    private List<DoctorInfo> priseWiseDoctorsList;
    private String apptPhone;
    private String availablityInfo;
    private String consultationTimingType;
    private String onPanel;
    private String mostBooked;
    private String similarSpecialities;
    private String rating;
    private String dlID;
    private String docPic;
    private String docPicThumbnail;
    private String speciality;
    private String subSpeciality;
    private String docName;
    private String cmdDocFee;
    private String isPmcVerified;
    private String schedular;
    private String hospitalAddress;
    private String hospitalArea;
    private String hospitalCity;
    private String extProfile;
    private String totalReviews;
    private String distance_in_km;
    private String videoConsultationDoctorHospitalID;
    private String availableToday;
    private String hasSchedularSettingChanged;
    private String fix_fee;
    private String showMarhamPhone;
    private List<String> marhamHelpLinePhoneList;
    private String video_consultation;
    private String docFee;
    private String docPic450X450;

    //Used Values in next screen
    private String docDegree;
    private String userID;
    private List<String> apptPhoneList;

    //Unused Values
    private String timeID;
    private String monday;
    private String tuesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    private String startTime;
    private String endTime;
    private String catName;
    private String Address;
    private String cmdDiscount;
    private String aboutMe;
    private String catID;
    private String docExp;
    //
    private String hospitalType;
    ///
    private String discount;
    ///

    private String gender;
    private String hasConnect;
    private String hospitalID;
    private String hospitalName;
    private String lat;
    private String lng;
    private String physician;
    private String relief;
    private String spID;
    private String subspID;
    private String surgen;
    private String wednesday;
    private String onCall;
    private String videoConsultationFee;

    ////
    private String isCallMyDoctor;
    ///

    private List<String> areasOfInterest;
    private List<Hospital> hospitals;
    private String averageWaitingTime;
    private String averageAppointmentTime;
    private String staffScore;
    private String satisfactionScore;

    private String walletAmount;
    private String showWallet;

    private String diagnosisScore;
    private String isInHouseDoctor;
    private String isDoctorSelected = "0";
    private String speciality_name;
    private String is_ext_profile;
    private String discountFee;

    public DoctorInfo() {
    }

    protected DoctorInfo(Parcel in) {
        doctorHospitalID = in.readString();
        cmdList = in.createTypedArrayList(DoctorInfo.CREATOR);
        priseWiseDoctorsList = in.createTypedArrayList(DoctorInfo.CREATOR);
        apptPhone = in.readString();
        availablityInfo = in.readString();
        consultationTimingType = in.readString();
        onPanel = in.readString();
        mostBooked = in.readString();
        similarSpecialities = in.readString();
        rating = in.readString();
        dlID = in.readString();
        docPic = in.readString();
        docPicThumbnail = in.readString();
        speciality = in.readString();
        subSpeciality = in.readString();
        docName = in.readString();
        cmdDocFee = in.readString();
        isPmcVerified = in.readString();
        schedular = in.readString();
        hospitalAddress = in.readString();
        hospitalArea = in.readString();
        hospitalCity = in.readString();
        extProfile = in.readString();
        totalReviews = in.readString();
        distance_in_km = in.readString();
        videoConsultationDoctorHospitalID = in.readString();
        availableToday = in.readString();
        hasSchedularSettingChanged = in.readString();
        fix_fee = in.readString();
        showMarhamPhone = in.readString();
        marhamHelpLinePhoneList = in.createStringArrayList();
        video_consultation = in.readString();
        docFee = in.readString();
        docPic450X450 = in.readString();
        docDegree = in.readString();
        userID = in.readString();
        apptPhoneList = in.createStringArrayList();
        timeID = in.readString();
        monday = in.readString();
        tuesday = in.readString();
        thursday = in.readString();
        friday = in.readString();
        saturday = in.readString();
        sunday = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        catName = in.readString();
        Address = in.readString();
        cmdDiscount = in.readString();
        aboutMe = in.readString();
        catID = in.readString();
        docExp = in.readString();
        hospitalType = in.readString();
        discount = in.readString();
        gender = in.readString();
        hasConnect = in.readString();
        hospitalID = in.readString();
        hospitalName = in.readString();
        lat = in.readString();
        lng = in.readString();
        physician = in.readString();
        relief = in.readString();
        spID = in.readString();
        subspID = in.readString();
        surgen = in.readString();
        wednesday = in.readString();
        onCall = in.readString();
        videoConsultationFee = in.readString();
        isCallMyDoctor = in.readString();
        areasOfInterest = in.createStringArrayList();
        hospitals = in.createTypedArrayList(Hospital.CREATOR);
        averageWaitingTime = in.readString();
        averageAppointmentTime = in.readString();
        staffScore = in.readString();
        satisfactionScore = in.readString();
        walletAmount = in.readString();
        showWallet = in.readString();
        diagnosisScore = in.readString();
        isInHouseDoctor = in.readString();
        isDoctorSelected = in.readString();
        speciality_name = in.readString();
        is_ext_profile = in.readString();
        discountFee = in.readString();
    }

    public static final Parcelable.Creator<DoctorInfo> CREATOR = new Parcelable.Creator<DoctorInfo>() {
        @Override
        public DoctorInfo createFromParcel(Parcel in) {
            return new DoctorInfo(in);
        }

        @Override
        public DoctorInfo[] newArray(int size) {
            return new DoctorInfo[size];
        }
    };

    public String getDoctorHospitalID() {
        return doctorHospitalID;
    }

    public void setDoctorHospitalID(String doctorHospitalID) {
        this.doctorHospitalID = doctorHospitalID;
    }

    public List<DoctorInfo> getCmdList() {
        return cmdList;
    }

    public void setCmdList(List<DoctorInfo> cmdList) {
        this.cmdList = cmdList;
    }

    public List<DoctorInfo> getPriseWiseDoctorsList() {
        return priseWiseDoctorsList;
    }

    public void setPriseWiseDoctorsList(List<DoctorInfo> priseWiseDoctorsList) {
        this.priseWiseDoctorsList = priseWiseDoctorsList;
    }

    public String getApptPhone() {
        return apptPhone;
    }

    public void setApptPhone(String apptPhone) {
        this.apptPhone = apptPhone;
    }

    public String getAvailablityInfo() {
        return availablityInfo;
    }

    public void setAvailablityInfo(String availablityInfo) {
        this.availablityInfo = availablityInfo;
    }

    public String getConsultationTimingType() {
        return consultationTimingType;
    }

    public void setConsultationTimingType(String consultationTimingType) {
        this.consultationTimingType = consultationTimingType;
    }

    public String getOnPanel() {
        return onPanel;
    }

    public void setOnPanel(String onPanel) {
        this.onPanel = onPanel;
    }

    public String getMostBooked() {
        return mostBooked;
    }

    public void setMostBooked(String mostBooked) {
        this.mostBooked = mostBooked;
    }

    public String getSimilarSpecialities() {
        return similarSpecialities;
    }

    public void setSimilarSpecialities(String similarSpecialities) {
        this.similarSpecialities = similarSpecialities;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDlID() {
        return dlID;
    }

    public void setDlID(String dlID) {
        this.dlID = dlID;
    }

    public String getDocPic() {
        return docPic;
    }

    public void setDocPic(String docPic) {
        this.docPic = docPic;
    }

    public String getDocPicThumbnail() {
        return docPicThumbnail;
    }

    public void setDocPicThumbnail(String docPicThumbnail) {
        this.docPicThumbnail = docPicThumbnail;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getSubSpeciality() {
        return subSpeciality;
    }

    public void setSubSpeciality(String subSpeciality) {
        this.subSpeciality = subSpeciality;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getCmdDocFee() {
        return cmdDocFee;
    }

    public void setCmdDocFee(String cmdDocFee) {
        this.cmdDocFee = cmdDocFee;
    }

    public String getIsPmcVerified() {
        return isPmcVerified;
    }

    public void setIsPmcVerified(String isPmcVerified) {
        this.isPmcVerified = isPmcVerified;
    }

    public String getSchedular() {
        return schedular;
    }

    public void setSchedular(String schedular) {
        this.schedular = schedular;
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

    public String getExtProfile() {
        return extProfile;
    }

    public void setExtProfile(String extProfile) {
        this.extProfile = extProfile;
    }

    public String getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(String totalReviews) {
        this.totalReviews = totalReviews;
    }

    public String getDistance_in_km() {
        return distance_in_km;
    }

    public void setDistance_in_km(String distance_in_km) {
        this.distance_in_km = distance_in_km;
    }

    public String getVideoConsultationDoctorHospitalID() {
        return videoConsultationDoctorHospitalID;
    }

    public void setVideoConsultationDoctorHospitalID(String videoConsultationDoctorHospitalID) {
        this.videoConsultationDoctorHospitalID = videoConsultationDoctorHospitalID;
    }

    public String getAvailableToday() {
        return availableToday;
    }

    public void setAvailableToday(String availableToday) {
        this.availableToday = availableToday;
    }

    public String getHasSchedularSettingChanged() {
        return hasSchedularSettingChanged;
    }

    public void setHasSchedularSettingChanged(String hasSchedularSettingChanged) {
        this.hasSchedularSettingChanged = hasSchedularSettingChanged;
    }

    public String getFix_fee() {
        return fix_fee;
    }

    public void setFix_fee(String fix_fee) {
        this.fix_fee = fix_fee;
    }

    public String getShowMarhamPhone() {
        return showMarhamPhone;
    }

    public void setShowMarhamPhone(String showMarhamPhone) {
        this.showMarhamPhone = showMarhamPhone;
    }

    public List<String> getMarhamHelpLinePhoneList() {
        return marhamHelpLinePhoneList;
    }

    public void setMarhamHelpLinePhoneList(List<String> marhamHelpLinePhoneList) {
        this.marhamHelpLinePhoneList = marhamHelpLinePhoneList;
    }

    public String getVideo_consultation() {
        return video_consultation;
    }

    public void setVideo_consultation(String video_consultation) {
        this.video_consultation = video_consultation;
    }

    public String getDocFee() {
        return docFee;
    }

    public void setDocFee(String docFee) {
        this.docFee = docFee;
    }

    public String getDocPic450X450() {
        return docPic450X450;
    }

    public void setDocPic450X450(String docPic450X450) {
        this.docPic450X450 = docPic450X450;
    }

    public String getDocDegree() {
        return docDegree;
    }

    public void setDocDegree(String docDegree) {
        this.docDegree = docDegree;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<String> getApptPhoneList() {
        return apptPhoneList;
    }

    public void setApptPhoneList(List<String> apptPhoneList) {
        this.apptPhoneList = apptPhoneList;
    }

    public String getTimeID() {
        return timeID;
    }

    public void setTimeID(String timeID) {
        this.timeID = timeID;
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

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCmdDiscount() {
        return cmdDiscount;
    }

    public void setCmdDiscount(String cmdDiscount) {
        this.cmdDiscount = cmdDiscount;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getDocExp() {
        return docExp;
    }

    public void setDocExp(String docExp) {
        this.docExp = docExp;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHasConnect() {
        return hasConnect;
    }

    public void setHasConnect(String hasConnect) {
        this.hasConnect = hasConnect;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPhysician() {
        return physician;
    }

    public void setPhysician(String physician) {
        this.physician = physician;
    }

    public String getRelief() {
        return relief;
    }

    public void setRelief(String relief) {
        this.relief = relief;
    }

    public String getSpID() {
        return spID;
    }

    public void setSpID(String spID) {
        this.spID = spID;
    }

    public String getSubspID() {
        return subspID;
    }

    public void setSubspID(String subspID) {
        this.subspID = subspID;
    }

    public String getSurgen() {
        return surgen;
    }

    public void setSurgen(String surgen) {
        this.surgen = surgen;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getOnCall() {
        return onCall;
    }

    public void setOnCall(String onCall) {
        this.onCall = onCall;
    }

    public String getVideoConsultationFee() {
        return videoConsultationFee;
    }

    public void setVideoConsultationFee(String videoConsultationFee) {
        this.videoConsultationFee = videoConsultationFee;
    }

    public String getIsCallMyDoctor() {
        return isCallMyDoctor;
    }

    public void setIsCallMyDoctor(String isCallMyDoctor) {
        this.isCallMyDoctor = isCallMyDoctor;
    }

    public List<String> getAreasOfInterest() {
        return areasOfInterest;
    }

    public void setAreasOfInterest(List<String> areasOfInterest) {
        this.areasOfInterest = areasOfInterest;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public String getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public void setAverageWaitingTime(String averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    public String getAverageAppointmentTime() {
        return averageAppointmentTime;
    }

    public void setAverageAppointmentTime(String averageAppointmentTime) {
        this.averageAppointmentTime = averageAppointmentTime;
    }

    public String getStaffScore() {
        return staffScore;
    }

    public void setStaffScore(String staffScore) {
        this.staffScore = staffScore;
    }

    public String getSatisfactionScore() {
        return satisfactionScore;
    }

    public void setSatisfactionScore(String satisfactionScore) {
        this.satisfactionScore = satisfactionScore;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getShowWallet() {
        return showWallet;
    }

    public void setShowWallet(String showWallet) {
        this.showWallet = showWallet;
    }

    public String getDiagnosisScore() {
        return diagnosisScore;
    }

    public void setDiagnosisScore(String diagnosisScore) {
        this.diagnosisScore = diagnosisScore;
    }

    public String getIsInHouseDoctor() {
        return isInHouseDoctor;
    }

    public void setIsInHouseDoctor(String isInHouseDoctor) {
        this.isInHouseDoctor = isInHouseDoctor;
    }

    public String getIsDoctorSelected() {
        return isDoctorSelected;
    }

    public void setIsDoctorSelected(String isDoctorSelected) {
        this.isDoctorSelected = isDoctorSelected;
    }

    public String getSpeciality_name() {
        return speciality_name;
    }

    public void setSpeciality_name(String speciality_name) {
        this.speciality_name = speciality_name;
    }

    public String getIs_ext_profile() {
        return is_ext_profile;
    }

    public void setIs_ext_profile(String is_ext_profile) {
        this.is_ext_profile = is_ext_profile;
    }

    public String getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(String discountFee){
        this.discountFee = discountFee;
    }

    public static Parcelable.Creator<DoctorInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(doctorHospitalID);
        dest.writeTypedList(cmdList);
        dest.writeTypedList(priseWiseDoctorsList);
        dest.writeString(apptPhone);
        dest.writeString(availablityInfo);
        dest.writeString(consultationTimingType);
        dest.writeString(onPanel);
        dest.writeString(mostBooked);
        dest.writeString(similarSpecialities);
        dest.writeString(rating);
        dest.writeString(dlID);
        dest.writeString(docPic);
        dest.writeString(docPicThumbnail);
        dest.writeString(speciality);
        dest.writeString(subSpeciality);
        dest.writeString(docName);
        dest.writeString(cmdDocFee);
        dest.writeString(isPmcVerified);
        dest.writeString(schedular);
        dest.writeString(hospitalAddress);
        dest.writeString(hospitalArea);
        dest.writeString(hospitalCity);
        dest.writeString(extProfile);
        dest.writeString(totalReviews);
        dest.writeString(distance_in_km);
        dest.writeString(videoConsultationDoctorHospitalID);
        dest.writeString(availableToday);
        dest.writeString(hasSchedularSettingChanged);
        dest.writeString(fix_fee);
        dest.writeString(showMarhamPhone);
        dest.writeStringList(marhamHelpLinePhoneList);
        dest.writeString(video_consultation);
        dest.writeString(docFee);
        dest.writeString(docPic450X450);
        dest.writeString(docDegree);
        dest.writeString(userID);
        dest.writeStringList(apptPhoneList);
        dest.writeString(timeID);
        dest.writeString(monday);
        dest.writeString(tuesday);
        dest.writeString(thursday);
        dest.writeString(friday);
        dest.writeString(saturday);
        dest.writeString(sunday);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(catName);
        dest.writeString(Address);
        dest.writeString(cmdDiscount);
        dest.writeString(aboutMe);
        dest.writeString(catID);
        dest.writeString(docExp);
        dest.writeString(hospitalType);
        dest.writeString(discount);
        dest.writeString(gender);
        dest.writeString(hasConnect);
        dest.writeString(hospitalID);
        dest.writeString(hospitalName);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(physician);
        dest.writeString(relief);
        dest.writeString(spID);
        dest.writeString(subspID);
        dest.writeString(surgen);
        dest.writeString(wednesday);
        dest.writeString(onCall);
        dest.writeString(videoConsultationFee);
        dest.writeString(isCallMyDoctor);
        dest.writeStringList(areasOfInterest);
        dest.writeTypedList(hospitals);
        dest.writeString(averageWaitingTime);
        dest.writeString(averageAppointmentTime);
        dest.writeString(staffScore);
        dest.writeString(satisfactionScore);
        dest.writeString(walletAmount);
        dest.writeString(showWallet);
        dest.writeString(diagnosisScore);
        dest.writeString(isInHouseDoctor);
        dest.writeString(isDoctorSelected);
        dest.writeString(speciality_name);
        dest.writeString(is_ext_profile);
        dest.writeString(discountFee);
    }

}
