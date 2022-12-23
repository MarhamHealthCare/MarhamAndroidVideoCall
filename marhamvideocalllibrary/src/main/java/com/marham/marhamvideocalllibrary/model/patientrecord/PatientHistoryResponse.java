package com.marham.marhamvideocalllibrary.model.patientrecord;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;

import java.util.List;

@Keep
public class PatientHistoryResponse extends ServerResponseOld {

    private List<PatientHistory> data;


    public List<PatientHistory> getData ()
    {
        return data;
    }

    public void setData (List<PatientHistory> data)
    {
        this.data = data;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", return_status = "+", message = "+"]";
    }

}
