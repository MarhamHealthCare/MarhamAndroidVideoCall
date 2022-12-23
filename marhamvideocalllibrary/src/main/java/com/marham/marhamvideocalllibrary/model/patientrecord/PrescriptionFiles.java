package com.marham.marhamvideocalllibrary.model.patientrecord;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class PrescriptionFiles implements Serializable {

    private String ext;

    private String file;

    private String patient_appointment_id;

    private String uploadedBy;

    public String getExt ()
    {
        return ext;
    }

    public void setExt (String ext)
    {
        this.ext = ext;
    }

    public String getFile ()
    {
        return file;
    }

    public void setFile (String file)
    {
        this.file = file;
    }

    public String getPatient_appointment_id ()
    {
        return patient_appointment_id;
    }

    public void setPatient_appointment_id (String patient_appointment_id)
    {
        this.patient_appointment_id = patient_appointment_id;
    }

    public String getUploadedBy ()
    {
        return uploadedBy;
    }

    public void setUploadedBy (String uploadedBy)
    {
        this.uploadedBy = uploadedBy;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ext = "+ext+", file = "+file+", patient_appointment_id = "+patient_appointment_id+", uploadedBy = "+uploadedBy+"]";
    }

}
