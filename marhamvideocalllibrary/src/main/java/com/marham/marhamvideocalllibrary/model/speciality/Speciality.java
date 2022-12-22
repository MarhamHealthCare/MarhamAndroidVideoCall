package com.marham.marhamvideocalllibrary.model.speciality;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.doctor.DoctorInfo;

import java.util.ArrayList;
import java.util.List;

@Keep
public class Speciality implements Parcelable{

//    public static final String BUNDLE_KEY = Locale.Category.class.getSimpleName();

    private String icon;
    private String speciality_image;
    private String specialityName;
    private String speciality;
    private String spID;
    private String newIcon;
    private String urdu_name;
    private List<Speciality> specialityList;
    private List<DoctorInfo> recentViewedDoctors = new ArrayList<>();
    private String doctorsCount;

    public Speciality() {
    }

    protected Speciality(Parcel in) {
        icon = in.readString();
        speciality_image = in.readString();
        specialityName = in.readString();
        speciality = in.readString();
        spID = in.readString();
        newIcon = in.readString();
        urdu_name = in.readString();
        specialityList = in.createTypedArrayList(Speciality.CREATOR);
        recentViewedDoctors = in.createTypedArrayList(DoctorInfo.CREATOR);
        doctorsCount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(speciality_image);
        dest.writeString(specialityName);
        dest.writeString(speciality);
        dest.writeString(spID);
        dest.writeString(newIcon);
        dest.writeString(urdu_name);
        dest.writeTypedList(specialityList);
        dest.writeTypedList(recentViewedDoctors);
        dest.writeString(doctorsCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Speciality> CREATOR = new Parcelable.Creator<Speciality>() {
        @Override
        public Speciality createFromParcel(Parcel in) {
            return new Speciality(in);
        }

        @Override
        public Speciality[] newArray(int size) {
            return new Speciality[size];
        }
    };

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSpeciality_image() {
        return speciality_image;
    }

    public void setSpeciality_image(String speciality_image) {
        this.speciality_image = speciality_image;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getSpID() {
        return spID;
    }

    public void setSpID(String spID) {
        this.spID = spID;
    }

    public String getNewIcon() {
        return newIcon;
    }

    public void setNewIcon(String newIcon) {
        this.newIcon = newIcon;
    }

    public String getUrdu_name() {
        return urdu_name;
    }

    public void setUrdu_name(String urdu_name) {
        this.urdu_name = urdu_name;
    }

    public List<Speciality> getSpecialityList() {
        return specialityList;
    }

    public void setSpecialityList(List<Speciality> specialityList) {
        this.specialityList = specialityList;
    }

    public List<DoctorInfo> getRecentViewedDoctors() {
        return recentViewedDoctors;
    }

    public void setRecentViewedDoctors(List<DoctorInfo> recentViewedDoctors) {
        this.recentViewedDoctors = recentViewedDoctors;
    }

    public String getDoctorsCount() {
        return doctorsCount;
    }

    public void setDoctorsCount(String doctorsCount) {
        this.doctorsCount = doctorsCount;
    }

    public static Parcelable.Creator<Speciality> getCREATOR() {
        return CREATOR;
    }
}
