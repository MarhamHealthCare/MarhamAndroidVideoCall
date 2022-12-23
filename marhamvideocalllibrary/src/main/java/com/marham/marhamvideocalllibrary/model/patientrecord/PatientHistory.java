package com.marham.marhamvideocalllibrary.model.patientrecord;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

@Keep
public class PatientHistory implements Serializable {

    //
    private String doctorName;
    private String diagnosis;
    private String medicine;
    //
    private String date;

    private String instructions;

    private String notes;

    private String isCallMyDoctor;

    private String fee;

    private String appointmentSubStatusID;

    private String appointmentSubStatusText;

    private String formattedTime;

    private String hospitalPhone;

    private String docName;

    private String docDegree;

    private String isFree;

    private String patientConsultationLink;

    private String appointment_type;

    private String paymentId;

    private String formattedDate;

    private String catName;

    private String canCommunicate;

    private String day;

    private String appointmentType;

    private String apptTimeInMiliSeconds;

    private String apptID;

    private String docExp;

    private String isPaymentReceived;

    private String paymentTransactionType;

    private String followUpDate;

    private String patient_arrived_at;

    private String currentTimeofServerInMiliSeconds;

    private String appointmentStatusID;

    private String tests;

    private String phone;

    private String isAppointmentTimePassed;

    private String patient_email;

    private List<PrescriptionFiles> files;

    private String doctor_arrived_at;

    private String prescriptionUrl;

    private String medicines;

    private String gender;

    private String city;

    private String followUpTime;

    private String hospitalCity;

    private String[] hospitalPhoneNumbersList;

    private String doctorConsultationLink;

    private String prescriptionAddedFrom;

    private String followUp;

    private String onlineConsultationId;

    private String speciality;

    private String prescriptionAdded;

    private String extra;

    private String docPic;

    private String paymentStatus;

    private String hospitalLng;

    private String patientName;

    private String appointmentSubStatusButtonText;

    private String patientID;

    private String doctorImageUrl;

    private String isReviewed;

    private String hospitalAddress;

    private String session_id;

    private String hospitalName;

    private String hospitalLat;

    private String token;

    private String appointmentSubStatusTitle;

    private String appointmentStatusTitle;

    private String hospitalID;

    private String time;

    private String specialityId;

    private String dID;

    private String age;

    public PatientHistory(String docPic, String doctorName, String diagnosis, String medicine, String formattedDate, String speciality, String patientName) {
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.medicine = medicine;
        this.formattedDate = formattedDate;
        this.speciality = speciality;
        this.patientName = patientName;
        this.docPic = docPic;
    }

    public PatientHistory() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getIsCallMyDoctor() {
        return isCallMyDoctor;
    }

    public void setIsCallMyDoctor(String isCallMyDoctor) {
        this.isCallMyDoctor = isCallMyDoctor;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getAppointmentSubStatusID() {
        return appointmentSubStatusID;
    }

    public void setAppointmentSubStatusID(String appointmentSubStatusID) {
        this.appointmentSubStatusID = appointmentSubStatusID;
    }

    public String getAppointmentSubStatusText() {
        return appointmentSubStatusText;
    }

    public void setAppointmentSubStatusText(String appointmentSubStatusText) {
        this.appointmentSubStatusText = appointmentSubStatusText;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getHospitalPhone() {
        return hospitalPhone;
    }

    public void setHospitalPhone(String hospitalPhone) {
        this.hospitalPhone = hospitalPhone;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocDegree() {
        return docDegree;
    }

    public void setDocDegree(String docDegree) {
        this.docDegree = docDegree;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getPatientConsultationLink() {
        return patientConsultationLink;
    }

    public void setPatientConsultationLink(String patientConsultationLink) {
        this.patientConsultationLink = patientConsultationLink;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCanCommunicate() {
        return canCommunicate;
    }

    public void setCanCommunicate(String canCommunicate) {
        this.canCommunicate = canCommunicate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getApptTimeInMiliSeconds() {
        return apptTimeInMiliSeconds;
    }

    public void setApptTimeInMiliSeconds(String apptTimeInMiliSeconds) {
        this.apptTimeInMiliSeconds = apptTimeInMiliSeconds;
    }

    public String getApptID() {
        return apptID;
    }

    public void setApptID(String apptID) {
        this.apptID = apptID;
    }

    public String getDocExp() {
        return docExp;
    }

    public void setDocExp(String docExp) {
        this.docExp = docExp;
    }

    public String getIsPaymentReceived() {
        return isPaymentReceived;
    }

    public void setIsPaymentReceived(String isPaymentReceived) {
        this.isPaymentReceived = isPaymentReceived;
    }

    public String getPaymentTransactionType() {
        return paymentTransactionType;
    }

    public void setPaymentTransactionType(String paymentTransactionType) {
        this.paymentTransactionType = paymentTransactionType;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getPatient_arrived_at() {
        return patient_arrived_at;
    }

    public void setPatient_arrived_at(String patient_arrived_at) {
        this.patient_arrived_at = patient_arrived_at;
    }

    public String getCurrentTimeofServerInMiliSeconds() {
        return currentTimeofServerInMiliSeconds;
    }

    public void setCurrentTimeofServerInMiliSeconds(String currentTimeofServerInMiliSeconds) {
        this.currentTimeofServerInMiliSeconds = currentTimeofServerInMiliSeconds;
    }

    public String getAppointmentStatusID() {
        return appointmentStatusID;
    }

    public void setAppointmentStatusID(String appointmentStatusID) {
        this.appointmentStatusID = appointmentStatusID;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsAppointmentTimePassed() {
        return isAppointmentTimePassed;
    }

    public void setIsAppointmentTimePassed(String isAppointmentTimePassed) {
        this.isAppointmentTimePassed = isAppointmentTimePassed;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

    public List<PrescriptionFiles> getFiles() {
        return files;
    }

    public void setFiles(List<PrescriptionFiles> files) {
        this.files = files;
    }

    public String getDoctor_arrived_at() {
        return doctor_arrived_at;
    }

    public void setDoctor_arrived_at(String doctor_arrived_at) {
        this.doctor_arrived_at = doctor_arrived_at;
    }

    public String getPrescriptionUrl() {
        return prescriptionUrl;
    }

    public void setPrescriptionUrl(String prescriptionUrl) {
        this.prescriptionUrl = prescriptionUrl;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFollowUpTime() {
        return followUpTime;
    }

    public void setFollowUpTime(String followUpTime) {
        this.followUpTime = followUpTime;
    }

    public String getHospitalCity() {
        return hospitalCity;
    }

    public void setHospitalCity(String hospitalCity) {
        this.hospitalCity = hospitalCity;
    }

    public String[] getHospitalPhoneNumbersList() {
        return hospitalPhoneNumbersList;
    }

    public void setHospitalPhoneNumbersList(String[] hospitalPhoneNumbersList) {
        this.hospitalPhoneNumbersList = hospitalPhoneNumbersList;
    }

    public String getDoctorConsultationLink() {
        return doctorConsultationLink;
    }

    public void setDoctorConsultationLink(String doctorConsultationLink) {
        this.doctorConsultationLink = doctorConsultationLink;
    }

    public String getPrescriptionAddedFrom() {
        return prescriptionAddedFrom;
    }

    public void setPrescriptionAddedFrom(String prescriptionAddedFrom) {
        this.prescriptionAddedFrom = prescriptionAddedFrom;
    }

    public String getFollowUp() {
        return followUp;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    public String getOnlineConsultationId() {
        return onlineConsultationId;
    }

    public void setOnlineConsultationId(String onlineConsultationId) {
        this.onlineConsultationId = onlineConsultationId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPrescriptionAdded() {
        return prescriptionAdded;
    }

    public void setPrescriptionAdded(String prescriptionAdded) {
        this.prescriptionAdded = prescriptionAdded;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getDocPic() {
        return docPic;
    }

    public void setDocPic(String docPic) {
        this.docPic = docPic;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getHospitalLng() {
        return hospitalLng;
    }

    public void setHospitalLng(String hospitalLng) {
        this.hospitalLng = hospitalLng;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAppointmentSubStatusButtonText() {
        return appointmentSubStatusButtonText;
    }

    public void setAppointmentSubStatusButtonText(String appointmentSubStatusButtonText) {
        this.appointmentSubStatusButtonText = appointmentSubStatusButtonText;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorImageUrl() {
        return doctorImageUrl;
    }

    public void setDoctorImageUrl(String doctorImageUrl) {
        this.doctorImageUrl = doctorImageUrl;
    }

    public String getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(String isReviewed) {
        this.isReviewed = isReviewed;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalLat() {
        return hospitalLat;
    }

    public void setHospitalLat(String hospitalLat) {
        this.hospitalLat = hospitalLat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppointmentSubStatusTitle() {
        return appointmentSubStatusTitle;
    }

    public void setAppointmentSubStatusTitle(String appointmentSubStatusTitle) {
        this.appointmentSubStatusTitle = appointmentSubStatusTitle;
    }

    public String getAppointmentStatusTitle() {
        return appointmentStatusTitle;
    }

    public void setAppointmentStatusTitle(String appointmentStatusTitle) {
        this.appointmentStatusTitle = appointmentStatusTitle;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(String specialityId) {
        this.specialityId = specialityId;
    }

    public String getdID() {
        return dID;
    }

    public void setdID(String dID) {
        this.dID = dID;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
