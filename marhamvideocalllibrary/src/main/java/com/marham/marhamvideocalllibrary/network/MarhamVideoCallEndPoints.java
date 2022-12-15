package com.marham.marhamvideocalllibrary.network;

import com.marham.marhamvideocalllibrary.model.ServerResponse;
import com.marham.marhamvideocalllibrary.model.disease.DashboardDiseasesServerResponse;
import com.marham.marhamvideocalllibrary.model.doctor.AllDoctorResponse;
import com.marham.marhamvideocalllibrary.model.doctor.DashboardDoctorServerResponse;
import com.marham.marhamvideocalllibrary.model.doctor.DoctorListingFiltersServerResponse;
import com.marham.marhamvideocalllibrary.model.speciality.NewAllSpecialitiesServerResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
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

    @FormUrlEncoded
    @POST("index.php?action=getallspecialities")
    Call<NewAllSpecialitiesServerResponse> getAllSpecialities(@FieldMap HashMap<String, String> info);

    @FormUrlEncoded
    @POST("index.php?action=getallspecialities")
    Call<NewAllSpecialitiesServerResponse> getDoctorListingFilters(@FieldMap HashMap<String, String> info);

    @FormUrlEncoded
    @POST("index.php?action=drlisting")
    Call<AllDoctorResponse> getAllDoctors(@Field("area") String area, @Field("lat") String latitude, @Field("lng") String longitude, @Field("city") String city, @Field("name") String name, @Field("id") String i, @Field("page") int pageNumber, @Field("feeRange") String fee, @Field("gender") String gender, @Field("loggedInUserId") String loggedInUserId, @Field("devicetoken") String devicetoken, @Field("deviceType") String deviceType, @Field("search_filter") String searchFilter, @Field("isCmd") String isCmd, @Field("discount") String discount, @Field("availability") String availability, @Field("hospitalType") String hospitalType, @Field("new") String neww, @Field("language") String language, @Field("corporateListing") String corporateListing);

}
