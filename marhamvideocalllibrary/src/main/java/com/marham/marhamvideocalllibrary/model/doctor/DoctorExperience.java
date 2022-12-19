package com.marham.marhamvideocalllibrary.model.doctor;

import androidx.annotation.Keep;

@Keep
public class DoctorExperience {

    private String institute;
    private String year_from;
    private String designation;
    private String year_to;
    private String years;

    public String getInstitute()
    {
        return institute;
    }

    public void setInstitute(String institute)
    {
        this.institute = institute;
    }

    public String getYear_from()
    {
        return year_from;
    }

    public void setYear_from(String year_from)
    {
        this.year_from = year_from;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

    public String getYear_to()
    {
        return year_to;
    }

    public void setYear_to(String year_to)
    {
        this.year_to = year_to;
    }

    public String getYears()
    {
        return years;
    }

    public void setYears(String years)
    {
        this.years = years;
    }


}
