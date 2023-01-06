package com.marham.marhamvideocalllibrary.network;

import com.marham.marhamvideocalllibrary.BuildConfig;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.model.appointment.videoconsultationlisting.AllAppointmentListingServerResponse;
import com.marham.marhamvideocalllibrary.model.disease.DashboardDiseasesServerResponse;
import com.marham.marhamvideocalllibrary.model.doctor.AllDoctorResponse;
import com.marham.marhamvideocalllibrary.model.doctor.DashboardDoctorServerResponse;
import com.marham.marhamvideocalllibrary.model.doctor.NewDoctorProfileServerResponse;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.hospital.HospitalAvailableDaysAndDateServerResponse;
import com.marham.marhamvideocalllibrary.model.notification.UpdateDeviceTokenServerResponse;
import com.marham.marhamvideocalllibrary.model.patientrecord.PatientHistoryResponse;
import com.marham.marhamvideocalllibrary.model.speciality.NewAllSpecialitiesServerResponse;
import com.marham.marhamvideocalllibrary.model.user.MarhamUserServerResponse;
import com.marham.marhamvideocalllibrary.model.videoconsultation.BookConsultationServerResponse;
import com.marham.marhamvideocalllibrary.model.videoconsultation.TokenAndRoomServerResponse;
import com.marham.marhamvideocalllibrary.model.videoconsultation.VideoConsultanceModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static APIClient apiClient;

    private static final String SDK_BASE_URL_ACTION_BASED = BuildConfig.SDK_BASE_URL_ACTION_BASED;
    private static final String SDK_BASE_URL = BuildConfig.SDK_BASE_URL;

    private MarhamVideoCallEndPoints apiService;

    public static APIClient getInstance() {
        if (apiClient == null) return apiClient = new APIClient();
        else return apiClient;

    }

    public APIClient() {
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
                        ;

                        String client = MarhamVideoCallHelper.getInstance().getClient();
                        String API_KEY = MarhamVideoCallHelper.getInstance().getAPI_KEY();
                        String authToken = MarhamVideoCallHelper.getInstance().getAuthToken();

                        setAuthHeader(builder, client, API_KEY, authToken);


                        request = builder.build(); //overwrite old request
                        Response response = chain.proceed(request); //perform request, here original request will be executed
                        if (response.code() == 401) { //if unauthorized
                            synchronized (httpClient) { //perform all 401 in sync blocks, to avoid multiply token updates

                                setAuthHeader(builder, client, API_KEY, authToken);
                                request = builder.build();
                                return chain.proceed(request); //repeat request with new token

                            }
                        }

                        setAuthHeader(builder, client, API_KEY, authToken);
                        return response;
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(SDK_BASE_URL_ACTION_BASED).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        apiService = retrofit.create(MarhamVideoCallEndPoints.class);
    }


    public APIClient(String sdk) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(6000, TimeUnit.SECONDS);
        httpClient.connectTimeout(6000, TimeUnit.SECONDS);
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        httpClient.addInterceptor(logging); // <-- this is the important line!
        httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        //Build new request
                        Request.Builder builder = request.newBuilder();
                        builder.header("Accept", "application/json"); //if necessary, say to consume JSON

                        String client = MarhamVideoCallHelper.getInstance().getClient();
                        String API_KEY = MarhamVideoCallHelper.getInstance().getAPI_KEY();
                        String authToken = MarhamVideoCallHelper.getInstance().getAuthToken();

                        setAuthHeader(builder, client, API_KEY, authToken); //write current token to request

                        request = builder.build(); //overwrite old request
                        Response response = chain.proceed(request); //perform request, here original request will be executed
                        if (response.code() == 401) { //if unauthorized
                            synchronized (httpClient) { //perform all 401 in sync blocks, to avoid multiply token updates
                                //get currently stored token

                                setAuthHeader(builder, client, API_KEY, authToken);
                                ; //set auth token to updated
                                request = builder.build();
                                return chain.proceed(request); //repeat request with new token

                            }
                        }

                        setAuthHeader(builder, client, API_KEY, authToken);
                        return response;
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(SDK_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        apiService = retrofit.create(MarhamVideoCallEndPoints.class);
    }

//    private void setAuthHeader(Request.Builder builder, String authToken) {
//        if (authToken != null) {//Add Auth token to each request if authorized
//            builder.header("Authorization", String.format("Bearer %s", authToken));
//        }
//    }

    private void setAuthHeader(Request.Builder builder, String client, String API_KEY, String authToken) {

        if (client != null) {
            builder.header("sdkclient", client);
        }

        if (API_KEY != null) {
            builder.header("sdkclientkey", API_KEY);
        }

        if (authToken != null) {//Add Auth token to each request if authorized
            builder.header("Authorization", String.format("Bearer %s", authToken));
        }
    }

    public Call<DashboardDoctorServerResponse> getDashboardDoctos(HashMap<String, String> info) {
        return apiService.getDashboardDoctors(info);
    }

    public Call<DashboardDiseasesServerResponse> getDashboardSpecialitiesWithDiseases(HashMap<String, String> hashMap) {
        return apiService.getDashboardSpecialitiesWithDiseases(hashMap);
    }

    public Call<NewAllSpecialitiesServerResponse> getSpecialities(HashMap<String, String> info) {
        return apiService.getAllSpecialities(info);
    }

//    public Call<NewAllSpecialitiesServerResponse> getDoctorListingFilters(HashMap<String, String> info) {
//        return apiService.getDoctorListingFilters(info);
//    }

    public Call<AllDoctorResponse> getAllDoctors(String area, String latitude, String longitude, String city, String doctorName, String specialityId, int pageNumber, String fee, String gender, String loggedInUserId, String token, String deviceType, String s, String isCmd, String discount, String availability, String hospitalType, String neww, String language, String corporateListing) {
        return apiService.getAllDoctors(area, latitude, longitude, city, doctorName, specialityId, pageNumber, fee, gender, loggedInUserId, token, deviceType, s, isCmd, discount, availability, hospitalType, neww, language, corporateListing);
    }

    public Call<NewDoctorProfileServerResponse> getDoctorDetail(HashMap<String, String> hashMap) {
        return apiService.getDoctorDetail(hashMap);
    }

    public Call<HospitalAvailableDaysAndDateServerResponse> getHospitalAvailableDaysAndDates(String hospitalID, String date, String loggedInUserId, String token, String deviceType, String hospitalType, String dID, String language, String inHouseDoctorRequest) {
        return apiService.appointmentDateAndTime(hospitalID, date, loggedInUserId, token, deviceType, hospitalType, dID, language, inHouseDoctorRequest);
    }

    public Call<BookConsultationServerResponse> bookOnlineConsultation(VideoConsultanceModel videoConsultanceModel) {
        return apiService.bookOnlineConsultation(videoConsultanceModel);
    }

    public Call<MarhamUserServerResponse> getUserDetails(HashMap<String, String> hashMap) {
        return apiService.getUserDetails(hashMap);
    }

    public Call<PatientHistoryResponse> getPastPrescription(HashMap<String, String> hashMap) {
        return apiService.getPastPrescription(hashMap);
    }

    public Call<AllAppointmentListingServerResponse> getPatientOrderAndAppointmentList(HashMap<String, String> info) {
        return apiService.getPatientOrderAndAppointmentList(info);
    }

    public Call<TokenAndRoomServerResponse> getToken(HashMap<String, String> hashMap) {
        return apiService.getToken(hashMap);
    }

    public Call<UpdateDeviceTokenServerResponse> updateDeviceToken(String userID, String devicetoken, String deviceType, String appType, String deviceID) {
        return apiService.updateDeviceToken(userID, devicetoken, deviceType, appType, deviceID);
    }

    public Call<ServerResponseOld> sendOnlineConsultationSignal(HashMap<String, String> info) {
        return apiService.sendOnlineConsultationSignal(info);
    }

    public Call<ServerResponseOld> updateLastSeenTimeOfUser(HashMap<String, String> info) {
        return apiService.updateLastSeenTimeOfUser(info);
    }

    public Call<ServerResponseOld> updateUserAppointmentSubStatus(HashMap<String, String> hashMap) {
        return apiService.updateUserAppointmentSubStatus(hashMap);
    }

    public Call<ServerResponseOld> saveOrderTransaction(HashMap<String, String> hashMap) {
        return apiService.saveOrderTransaction(hashMap);
    }

}
