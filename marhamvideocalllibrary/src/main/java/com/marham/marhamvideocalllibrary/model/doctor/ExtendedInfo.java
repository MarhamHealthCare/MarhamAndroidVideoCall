package com.marham.marhamvideocalllibrary.model.doctor;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.List;

@Keep
public class ExtendedInfo implements Serializable {

    private String docDetails;
    private String interview;
    private String pmdc;
    private String profileUrl;
    private List<String> picturesThumbnails;
    private List<String> pictures;
    private List<DoctorExperience> doctorExperience;
    private String aboutMe;

    public List<String> getPicturesThumbnails()
    {
        return picturesThumbnails;
    }

    public void setPicturesThumbnails(List<String> picturesThumbnails)
    {
        this.picturesThumbnails = picturesThumbnails;
    }

    public String getDocDetails()
    {
        return docDetails;
    }

    public void setDocDetails(String docDetails)
    {
        this.docDetails = docDetails;
    }

    public String getInterview()
    {
        return interview;
    }

    public void setInterview(String interview)
    {
        this.interview = interview;
    }

    public String getPmdc()
    {
        return pmdc;
    }

    public void setPmdc(String pmdc)
    {
        this.pmdc = pmdc;
    }

    public List<String> getPictures()
    {
        return pictures;
    }

    public void setPictures(List<String> pictures)
    {
        this.pictures = pictures;
    }

    public List<DoctorExperience> getDoctorExperience()
    {
        return doctorExperience;
    }

    public void setDoctorExperience(List<DoctorExperience> doctorExperience)
    {
        this.doctorExperience = doctorExperience;
    }

    public String getProfileUrl()
    {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl)
    {
        this.profileUrl = profileUrl;
    }

    public String getAboutMe()
    {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe)
    {
        this.aboutMe = aboutMe;
    }


}
