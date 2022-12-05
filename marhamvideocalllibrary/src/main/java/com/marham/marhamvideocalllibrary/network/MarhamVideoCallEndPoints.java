package com.marham.marhamvideocalllibrary.network;

import com.marham.marhamvideocalllibrary.model.ServerResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MarhamVideoCallEndPoints {
    @FormUrlEncoded
    @POST("index.php?action=login")
    Call<ServerResponse> login(@FieldMap HashMap<String, String> info);

}
