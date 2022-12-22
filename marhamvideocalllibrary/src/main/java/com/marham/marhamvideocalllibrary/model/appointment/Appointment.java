package com.marham.marhamvideocalllibrary.model.appointment;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.doctor.DoctorInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Keep
public class Appointment implements Serializable {

    public static final String INPROCESS = "1";
    public static final String SCHEDULE = "3";
    public static final String CANCEL = "10";
    public static final String SHOWEDUP = "5";
    public static final String INPROGRRSS = "4";
    public static final String DOCTOR_HAS_ARRIVED = "9";
    public static final String VIEW_PRESCRIPTION = "6";
    public static final String PAID = "2";
    public static final String PATIENT_HAS_ARRIVED = "8";
    public static final String VERIFYING_PAYMENT = "7";
    public static final String PENDING = "2";
    public static final String FAILURE = "3";
    public static final String UPCOMMING = "1";
    public static final String PAST_PRESCRIPTION_TAB = "1";
    public static final String SELECT_TAB_POSITION_PRESCRIPTIO = "3";
    public static String BUNDLE_KEY = "appointment";

    public static final String APPOINTMENT_STATUS_NORMAL = "1";
    public static final String APPOINTMENT_STATUS_VIDEO_CONSULTANCY = "2";

    public static final String TO_BE_SCHEDULED = "1";
    public static final String SCHEDULED = "2";
    public static final String CANCELLED = "3";
    public static final String SHOWED_UP = "8";
    public static final String PATIENT_NOT_SHOWED_UP = "10";
    public static final String DOCTOR_NOT_SHOWED_UP = "12";

    //    private String id;
    private String discount;
    private String patientPhone;

    public String getDiscount() {
        return discount;
    }

    private String appointmentSubStatusID;
    private String appointmentSubStatusTitle;
    private String appointmentSubStatusText;
    private String appointmentSubStatusButtonText;
    private String paymentStatus;
    private String walletAmount;
    private String isWalletApplied;
    private String followUp;
    private String isDirectBooking;

    private String isOnlinePaymentEnabled;

    private String _month;

    private String total;

    private String type;

    private String dID;
    private String phone;
    private String paymentReceiptUrl;
    private String time;
    private String showWallet;
    private String docName;
    private String patientName;
    private String patientEmail;
    private String catName;
    private String docExp;
    private String token;
    private String age;
    private String gender;
    private String isReviewed;
    private String hospitalID;
    private String formattedTime;
    private String fee;
    private String isCallMyDoctor;
    private String day;
    private String month;
    private String year;
    private String userID;
    private String paymentStatusId;
    private String paymentStatusTitle;
    private String patientID;
    private String date;
    private String doctorImageUrl;
    private String apptID;
    private String hospitalName;
    private Boolean hasTokenExpired = false;
    private Boolean isOccupied = false;
    private Boolean nextAvailableSlot = false; // For nextslot available feature only
    private String averageTimePerPatient;
    private String formattedDate;
    private Boolean isBetweenStartAndEndTimeTokens = true;
    private String isBookedByDoctor = "0";
    private boolean isDummyObject = false;
    private String session_id;
    private String appointment_type;
    private String apptTimeInMiliSeconds;
    private String currentTimeofServerInMiliSeconds;
    private String videoConsultationFee;
    private String doctorUserId;
    private String appointmentStatusTitle;
    private String appointmentStatusID;

    private String onlineConsultationId;
    private String canCommunicate;

    // Used in appointment list adapter
    private boolean isFirstUpcomingAppoinment = false;
    private boolean isFirstPastAppointment = false;


    private List<String> hospitalPhoneNumbersList;
    private String isAppointmentTimePassed;
    private String appointment_status;
    private String hospitalAddress;
    //    private List<String> images;
    private String speciality;
    private String docDegree;

    private String hospitalLat;
    private String hospitalLng;
    private String isUserSatisfied;

    public String getIsUserSatisfied() {
        return isUserSatisfied;
    }
//    private String patient_arrived_at;
//    private String doctor_arrived_at;


    //Patient Record Summary

    private String patient_appointment_consultant_plan;
    private String patient_appointment_investigation_notes;
    private String patient_appointment_title;
    private DoctorInfo doctor;
    private String patient_appointment_consultant_notes;
    private String id;
    private String hr_date;
    private String hr_time;
    private List<String> images;
    private ArrayList<String> imagesStringURLsThumbNails;
    private ArrayList<String> imagesStringURLs;

    private String patient_appointment_blood_pressure;
    private String patient_appointment_blood_sugar;
    private String patient_appointment_height;
    private String patient_appointment_weight;

    private String patient_arrived_at;
    private String doctor_arrived_at;


    private String patient_surgeries;
    private String patient_medications;
    private String patient_conditions;

    private String patient_appointment_treatment_plan;

    private ArrayList<String> picturesUploadedByDoctorThumbnailsList;
    private ArrayList<String> picturesUploadedByDoctorList;


    private ArrayList<String> picturesUploadedByPatientThumbnailsList;
    private ArrayList<String> picturesUploadedByPatientList;
    private String uri;
    private String appointmentType;

    private String specialityName;
    private String specialityId;
    private String isReferred;
    private String walletVisible;


    public void generateListOfPicturesUploadedByDoctorAndPatient() {

        if (picturesUploadedByDoctorThumbnailsList == null) {
            picturesUploadedByDoctorThumbnailsList = new ArrayList<>();
        } else {
            picturesUploadedByPatientThumbnailsList.clear();
        }

        if (picturesUploadedByDoctorList == null) {
            picturesUploadedByDoctorList = new ArrayList<>();
        } else {
            picturesUploadedByDoctorList.clear();
        }

        if (picturesUploadedByPatientThumbnailsList == null) {
            picturesUploadedByPatientThumbnailsList = new ArrayList<>();
        } else {
            picturesUploadedByPatientThumbnailsList.clear();
        }

        if (picturesUploadedByPatientList == null) {
            picturesUploadedByPatientList = new ArrayList<>();
        } else {
            picturesUploadedByPatientList.clear();
        }
    }


    public List<String> getImages() {
        return images;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public String getDoctorName() {
        return docName;
    }

    public String getIsAppointmentTimePassed() {
        return isAppointmentTimePassed;
    }

    public String getAppointment_status() {
        return this.appointment_status;
    }

    public List<String> getHospitalPhoneNumbersList() {
        return hospitalPhoneNumbersList;
    }


    public String getShowWallet() {
        return showWallet;
    }

    public void setShowWallet(String showWallet) {
        this.showWallet = showWallet;
    }

    public String getIsReviewed() {
        return isReviewed;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String get_month() {
        return _month;
    }

    public void set_month(String _month) {
        this._month = _month;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCanCommunicate() {
        return canCommunicate;
    }

    public String getPaymentReceiptUrl() {
        return paymentReceiptUrl;
    }

    public void setPaymentReceiptUrl(String paymentReceiptUrl) {
        this.paymentReceiptUrl = paymentReceiptUrl;
    }

    public String getPaymentStatusId() {
        return paymentStatusId;
    }

    public String getPaymentStatusTitle() {
        return paymentStatusTitle;
    }

    public void setCanCommunicate(String canCommunicate) {
        this.canCommunicate = canCommunicate;
    }

    public String getIsBookedByDoctor() {
        return this.isBookedByDoctor;
    }

    public void setIsBookedByDoctor(String isBookedByDoctor) {
        this.isBookedByDoctor = isBookedByDoctor;
    }

    public String getAppointmentSubStatusID() {
        return appointmentSubStatusID;
    }

    public void setAppointmentSubStatusID(String appointmentSubStatusID) {
        this.appointmentSubStatusID = appointmentSubStatusID;
    }

    public void setOnlineConsultationId(String onlineConsultationId) {
        this.onlineConsultationId = onlineConsultationId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public String getIsReferred() {
        return isReferred;
    }

    public String getIsDirectBooking() {
        return isDirectBooking;
    }

    public void setIsReferred(String isReferred) {
        this.isReferred = isReferred;
    }

    public String getSpecialityId() {
        return specialityId;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public String getIsWalletApplied() {
        return isWalletApplied;
    }

    public String getIsOnlinePaymentEnabled() {
        return isOnlinePaymentEnabled;
    }

    public String getAppointmentSubStatusTitle() {
        return appointmentSubStatusTitle;
    }

    public String getAppointmentSubStatusText() {
        return appointmentSubStatusText;
    }

    public String getAppointmentSubStatusButtonText() {
        return appointmentSubStatusButtonText;
    }

    public Boolean getHasTokenExpired() {
        return this.hasTokenExpired;
    }

    public String getDoctorUserId() {
        return doctorUserId;
    }

    public void setDoctorUserId(String doctorUserId) {
        this.doctorUserId = doctorUserId;
    }

    public void setHasTokenExpired(Boolean hasTokenExpired) {
        this.hasTokenExpired = hasTokenExpired;
    }

    public String getDocDegree() {
        return docDegree;
    }

    public Boolean getIsOccupied() {
        return this.isOccupied;
    }

    public void setIsOccupied(Boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public Boolean getNextAvailableSlot() {
        return this.nextAvailableSlot;
    }

    public void setNextAvailableSlot(Boolean nextAvailableSlot) {
        this.nextAvailableSlot = nextAvailableSlot;
    }

    public String getCatName() {
        return catName;
    }

    public String getDocExp() {
        return docExp;
    }

    public String getDoctorImageUrl() {
        return doctorImageUrl;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDID() {
        return dID;
    }

    public void setDID(String dID) {
        this.dID = dID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getFee() {
        return fee;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientEmail() {
        return this.patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public String getVideoConsultationFee() {
        return videoConsultationFee;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApptID() {
        return apptID;
    }

    public void setApptID(String apptID) {
        this.apptID = apptID;
    }

    public String getAverageTimePerPatient() {
        return averageTimePerPatient;
    }

    public void setAverageTimePerPatient(String averageTimePerPatient) {
        this.averageTimePerPatient = averageTimePerPatient;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getHospitalName() {
        return this.hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Boolean getIsBetweenStartAndEndTimeTokens() {
        return this.isBetweenStartAndEndTimeTokens;
    }

    public void setIsBetweenStartAndEndTimeTokens(Boolean isBetweenStartAndEndTimeTokens) {
        this.isBetweenStartAndEndTimeTokens = isBetweenStartAndEndTimeTokens;
    }


    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getId() {
        return id;
    }

    public boolean isDummyObject() {
        return isDummyObject;
    }

    public void setDummyObject(boolean dummyObject) {
        isDummyObject = dummyObject;
    }


    public boolean isFirstUpcomingAppoinment() {
        return isFirstUpcomingAppoinment;
    }

    public boolean isFirstPastAppointment() {
        return isFirstPastAppointment;
    }

    public void setFirstUpcomingAppoinment(boolean isFirstUpcomingAppoinment) {
        this.isFirstUpcomingAppoinment = isFirstUpcomingAppoinment;
    }

    public void setFirstPastAppointment(boolean isFirstPastAppointment) {
        this.isFirstPastAppointment = isFirstPastAppointment;
    }

    public void displayData(Context context) {
        Toast.makeText(context, "Appt ID :" + apptID + "\n" + "Doctor Id :" + dID + "\n" + "Time :" + time + "\n" + "Patient Name :" + patientName + "\n" + "Token :" + token + "\n" + "Hospital Id :" + hospitalID + "\n" + "Gender :" + gender, Toast.LENGTH_LONG).show();
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getApptTimeInMiliSeconds() {
        return apptTimeInMiliSeconds;
    }

    public String getAppointmentStatusTitle() {
        return appointmentStatusTitle;
    }

    public String getAppointmentStatusId() {
        return appointmentStatusID;
    }

    public String getHospitalLat() {
        return hospitalLat;
    }

    public String getHospitalLng() {
        return hospitalLng;
    }

    public String getOnlineConsultationId() {
        return onlineConsultationId;
    }

    public String getPatient_arrived_at() {
        return patient_arrived_at;
    }

    public String getDoctor_arrived_at() {
        return doctor_arrived_at;
    }

    //Patient Summary

    public String getPatient_appointment_blood_pressure() {
        return patient_appointment_blood_pressure;
    }

    public String getPatient_appointment_height() {
        return patient_appointment_height;
    }

    public String getPatient_appointment_weight() {
        return patient_appointment_weight;
    }

    public String getPatient_appointment_blood_sugar() {
        return patient_appointment_blood_sugar;
    }


    public String getPatient_appointment_investigation_notes() {
        return patient_appointment_investigation_notes;
    }

    public DoctorInfo getDoctor() {
        return this.doctor;
    }

    public String getIsCallMyDoctor() {
        return isCallMyDoctor;
    }

    public void setIsCallMyDoctor(String isCallMyDoctor) {
        this.isCallMyDoctor = isCallMyDoctor;
    }

    public ArrayList<String> getPicturesUploadedByDoctorThumbnailsList() {
        return picturesUploadedByDoctorThumbnailsList;
    }

    public ArrayList<String> getPicturesUploadedByDoctorList() {
        return picturesUploadedByDoctorList;
    }

    public ArrayList<String> getPicturesUploadedByPatientThumbnailsList() {
        return picturesUploadedByPatientThumbnailsList;
    }

    public ArrayList<String> getPicturesUploadedByPatientList() {
        return picturesUploadedByPatientList;
    }

    public String getUri() {
        return uri;
    }

    public String getPatient_appointment_consultant_plan() {
        return patient_appointment_consultant_plan;
    }

    public String getPatient_surgeries() {
        return patient_surgeries;
    }


    public String getPatient_medications() {
        return patient_medications;
    }

    public String getPatient_conditions() {
        return patient_conditions;
    }

    public String getPatient_appointment_treatment_plan() {
        return patient_appointment_treatment_plan;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFollowUp() {
        return followUp;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public String getWalletVisible() {
        return walletVisible;
    }

    public String getCurrentTimeofServerInMiliSeconds() {
        return currentTimeofServerInMiliSeconds;
    }

}
