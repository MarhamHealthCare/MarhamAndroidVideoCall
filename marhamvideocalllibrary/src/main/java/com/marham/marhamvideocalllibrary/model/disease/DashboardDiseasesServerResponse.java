package com.marham.marhamvideocalllibrary.model.disease;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.general.ServerResponse;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;

import java.util.List;

@Keep
public class DashboardDiseasesServerResponse extends ServerResponse {
    private List<Diseases> data;

    public List<Diseases> getData() {
        return data;
    }
}
