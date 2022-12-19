package com.marham.marhamvideocalllibrary.model.doctor;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.Hospital;
import com.marham.marhamvideocalllibrary.model.review.Reviews;

import java.io.Serializable;
import java.util.List;

@Keep
public class DoctorProfileGenericData implements Serializable {

    private List<Hospital> hospitals;

    private List<Reviews> reviews;

    private List<DoctorExperience> doctorExperiences;

    private ExtendedInfo extendedInfo;

    private DoctorInfo details;

    public List<Hospital> getHospitals ()
    {
        return hospitals;
    }

    public void setHospitals (List<Hospital> hospitals)
    {
        this.hospitals = hospitals;
    }

    public List<Reviews> getReviews ()
    {
        return reviews;
    }

    public void setReviews (List<Reviews> reviews)
    {
        this.reviews = reviews;
    }

    public  List<DoctorExperience> getDoctorExperiences ()
    {
        return doctorExperiences;
    }

    public void setDoctorExperiences ( List<DoctorExperience> doctorExperiences)
    {
        this.doctorExperiences = doctorExperiences;
    }

    public ExtendedInfo getExtendedInfo ()
    {
        return extendedInfo;
    }

    public void setExtendedInfo (ExtendedInfo extendedInfo)
    {
        this.extendedInfo = extendedInfo;
    }

    public DoctorInfo getDetails ()
    {
        return details;
    }

    public void setDetails (DoctorInfo details)
    {
        this.details = details;
    }


}
