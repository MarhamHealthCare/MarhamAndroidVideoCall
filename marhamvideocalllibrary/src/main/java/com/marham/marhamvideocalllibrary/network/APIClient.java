package com.marham.marhamvideocalllibrary.network;

import android.content.Context;

import com.marham.marhamvideocalllibrary.BuildConfig;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static APIClient apiClient;
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String NEW_BASE_URL = BuildConfig.NEW_BASE_URL;

    private MarhamVideoCallEndPoints apiService;

    public APIClient(int variable, Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(6000, TimeUnit.SECONDS);
        httpClient.connectTimeout(6000, TimeUnit.SECONDS);
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.addInterceptor(logging); // <-- this is the important line!
        httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String token;
                        Request request = chain.request();

                        //Build new request
                        Request.Builder builder = request.newBuilder();
                        builder.header("Accept", "application/json"); //if necessary, say to consume JSON

                        token = MarhamVideoCallHelper.getInstance().getAPI_KEY();

                        setAuthHeader(builder, token); //write current token to request

                        request = builder.build(); //overwrite old request
                        Response response = chain.proceed(request); //perform request, here original request will be executed
                        if (response.code() == 401) { //if unauthorized
                            synchronized (httpClient) { //perform all 401 in sync blocks, to avoid multiply token updates
                                token = MarhamVideoCallHelper.getInstance().getAPI_KEY();
                                //get currently stored token

                                setAuthHeader(builder, token); //set auth token to updated
                                request = builder.build();
                                return chain.proceed(request); //repeat request with new token

                            }
                        }

                        setAuthHeader(builder, token);
                        return response;
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        apiService = retrofit.create(MarhamVideoCallEndPoints.class);
    }

    private void setAuthHeader(Request.Builder builder, String token) {
        if (token != null) //Add Auth token to each request if authorized
            builder.header("Authorization", String.format("Bearer %s", token));
    }

}
