package com.marham.marhamvideocalllibrary.model.doctor;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponse;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;

import java.util.List;

@Keep
public class DashboardDoctorServerResponse extends ServerResponse {
    private List<DoctorInfo> data;

    public List<DoctorInfo> getData() {
        return data;
    }

    public void setData(List<DoctorInfo> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "ClassPojo [data = " + data + "]";
    }

}
