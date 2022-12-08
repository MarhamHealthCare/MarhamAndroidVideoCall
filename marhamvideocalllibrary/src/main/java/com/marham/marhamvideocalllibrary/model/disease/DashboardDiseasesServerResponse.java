package com.marham.marhamvideocalllibrary.model.disease;

import com.marham.marhamvideocalllibrary.model.ServerResponse;

import java.util.List;

public class DashboardDiseasesServerResponse extends ServerResponse {
    private List<Diseases> data;

    public List<Diseases> getData() {
        return data;
    }
}
