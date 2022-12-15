package com.marham.marhamvideocalllibrary.model.doctor;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.DoctorInfo;

import java.io.Serializable;
import java.util.List;

@Keep
public class DoctorObjectWithCMD implements Serializable {

    private List<DoctorInfo> cmdDoctors;

    private List<DoctorInfo> doctors;

    private List<DoctorInfo> priceWiseDoctors;

    private List<DoctorInfo> ourCmdDoctors;

    private String specialityInUrdu;

    public List<DoctorInfo> getCmdDoctors ()
    {
        return cmdDoctors;
    }

    public List<DoctorInfo> getOurCmdDoctors() {
        return ourCmdDoctors;
    }

    public void setOurCmdDoctors(List<DoctorInfo> ourCmdDoctors) {
        this.ourCmdDoctors = ourCmdDoctors;
    }

    public void setCmdDoctors (List<DoctorInfo> cmdDoctors)
    {
        this.cmdDoctors = cmdDoctors;
    }

    public String getSpecialityInUrdu() {
        return specialityInUrdu;
    }

    public void setSpecialityInUrdu(String specialityInUrdu) {
        this.specialityInUrdu = specialityInUrdu;
    }

    public List<DoctorInfo> getDoctors ()
    {
        return doctors;
    }

    public List<DoctorInfo> getPriceWiseDoctors() {
        return priceWiseDoctors;
    }

    public void setPriceWiseDoctors(List<DoctorInfo> priceWiseDoctors) {
        this.priceWiseDoctors = priceWiseDoctors;
    }

    public void setDoctors (List<DoctorInfo> doctors)
    {
        this.doctors = doctors;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cmdDoctors = "+cmdDoctors+", doctors = "+doctors+"]";
    }

}
