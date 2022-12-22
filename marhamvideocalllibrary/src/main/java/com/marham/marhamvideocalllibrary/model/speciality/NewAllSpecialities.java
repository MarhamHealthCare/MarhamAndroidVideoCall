package com.marham.marhamvideocalllibrary.model.speciality;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.doctor.DoctorInfo;

import java.util.List;

@Keep
public class NewAllSpecialities {

    private List<DoctorInfo> lastViewedDoctors;

    private List<Speciality> topSpecialities;

    private List<Speciality> specialities;

    public List<DoctorInfo> getLastViewedDoctors ()
    {
        return lastViewedDoctors;
    }

    public void setLastViewedDoctors (List<DoctorInfo> lastViewedDoctors)
    {
        this.lastViewedDoctors = lastViewedDoctors;
    }

    public List<Speciality> getTopSpecialities ()
    {
        return topSpecialities;
    }

    public void setTopSpecialities (List<Speciality> topSpecialities)
    {
        this.topSpecialities = topSpecialities;
    }

    public List<Speciality> getSpecialities ()
    {
        return specialities;
    }

    public void setSpecialities (List<Speciality> specialities)
    {
        this.specialities = specialities;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lastViewedDoctors = "+lastViewedDoctors+", topSpecialities = "+topSpecialities+", specialities = "+specialities+"]";
    }

}
