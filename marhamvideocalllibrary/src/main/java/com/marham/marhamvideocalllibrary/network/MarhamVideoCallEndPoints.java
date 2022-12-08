package com.marham.marhamvideocalllibrary.network;

import com.marham.marhamvideocalllibrary.model.ServerResponse;
import com.marham.marhamvideocalllibrary.model.disease.DashboardDiseasesServerResponse;
import com.marham.marhamvideocalllibrary.model.doctor.DashboardDoctorServerResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface MarhamVideoCallEndPoints {
    @FormUrlEncoded
    @POST("index.php?action=login")
    Call<ServerResponse> login(@FieldMap HashMap<String, String> info);

    @GET("doctor/get-top-doctors")
    Call<DashboardDoctorServerResponse> getDashboardDoctors(@QueryMap HashMap<String, String> info);

    @FormUrlEncoded
    @POST("index.php?action=get-dashboard-diseases")
    Call<DashboardDiseasesServerResponse> getDashboardSpecialitiesWithDiseases(@FieldMap HashMap<String, String> hashMap);

}
