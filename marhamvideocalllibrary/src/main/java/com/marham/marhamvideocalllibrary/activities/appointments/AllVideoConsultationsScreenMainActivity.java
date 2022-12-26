package com.marham.marhamvideocalllibrary.activities.appointments;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.model.appointment.videoconsultationlisting.AllAppointmentListingServerResponse;
import com.marham.marhamvideocalllibrary.model.doctor.NewDoctorProfileServerResponse;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;

import java.util.HashMap;

import retrofit2.Call;

public class AllVideoConsultationsScreenMainActivity extends BaseActivity implements ServerConnectListener {

    private ConstraintLayout parentLayout;

    private ConstraintLayout upcomingAppointmentsViewsContainer;
    private RecyclerView upcomingAppointmentsRecyclerView;

    private ConstraintLayout previousAppointmentsViewsContainer;
    private RecyclerView previousAppointmentsRecyclerView;

    //TODO: Replace with new ServerResponse
    private RetroFit2Callback<ServerResponseOld> retroFit2Callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_video_consultations_screen_main);
        initializeViews();
        getUserAppointments();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    protected void initializeViews() {
        super.initializeViews();
        parentLayout = findViewById(R.id.parent_layout);

        upcomingAppointmentsViewsContainer = findViewById(R.id.upcoming_appointments_views_container);
        upcomingAppointmentsRecyclerView = findViewById(R.id.upcoming_appointment_recycler_view);

        previousAppointmentsViewsContainer = findViewById(R.id.previous_appointments_views_container);
        previousAppointmentsRecyclerView = findViewById(R.id.previous_appointment_recycler_view);

    }

    public void setViewsBeforeGettingDoctorsDetails() {
        parentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingDoctorsDetails() {
        parentLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingDoctorsDetails() {
        parentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorsDetails() {
        parentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    public void getUserAppointments() {
        APIClient apiClient = new APIClient();

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.USER_ID_KEY, MarhamVideoCallHelper.getInstance().getUserId());
        hashMap.put(AppConstants.API.API_KEYS.REPORTS_KEY,"0");
        hashMap.put(AppConstants.API.API_KEYS.PROGRAM_ID_KEY,AppConstants.PROGRAM_ID.ONLINE_CONSULTATION);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_USER_APPOINTMENT);
        Call<AllAppointmentListingServerResponse> call = apiClient.getPatientOrderAndAppointmentList(hashMap);
        call.enqueue(retroFit2Callback);
    }


    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_USER_APPOINTMENT:
//                if (response.getReturn_status().equals(MyKeys.getInstance().SUCCESS)) {
//
//                    int current_page = getCurrent_page();
//
//                    AllAppointmentListingServerResponse allAppointmentsServerResponse = (AllAppointmentListingServerResponse) response;
//                    if (current_page == 1) {
//                        view.setViewsAfterGettingAppointments();
//                    } else {
////                        if(fragmentType==AppConstants.UPCOMMING) {
////                            upcomingAppointsmentsList.remove(upcomingAppointsmentsList.size() - 1);
////                        }else if(fragmentType==AppConstants.PAST){
////                            pastAppointmentsList.remove(pastAppointmentsList.size() - 1);
////                        }
//                    }
//
//                    if (fragmentType == AppConstants.UPCOMMING) {
//                        if (allAppointmentsServerResponse.getData().getUpcoming() != null && allAppointmentsServerResponse.getData().getUpcoming().size() > 0) {
//                            upcomingAppointsmentsList.addAll(allAppointmentsServerResponse.getData().getUpcoming());
//                            view.updateAppointmentRecyclerView(upcomingAppointsmentsList);
//                        } else {
//                            view.setViewsIfNoRecordWhileGettingAppointments();
//                        }
//                    } else if (fragmentType == AppConstants.PAST) {
//                        if (allAppointmentsServerResponse.getData().getPast() != null && allAppointmentsServerResponse.getData().getPast().size() > 0) {
//                            pastAppointmentsList.addAll(allAppointmentsServerResponse.getData().getPast());
//                            view.updateAppointmentRecyclerView(pastAppointmentsList);
//                        } else {
//                            view.setViewsIfNoRecordWhileGettingAppointments();
//                        }
//                    }
//                } else if (response.getReturn_status().equals(MyKeys.getInstance().FAILED)) {
//                    if (currentPage == 1) {
//                        view.setViewsIfNoRecordWhileGettingAppointments();
//                    } else {
//                        userAppointmentList.remove(userAppointmentList.size() - 1);
//                        view.removeItemInAppointmentRecyclerView(userAppointmentList);
//                    }
//
//                }
                break;

        }

    }

    @Override
    public void onFailure(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_USER_APPOINTMENT:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorsDetails();
                break;
        }

    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_USER_APPOINTMENT:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorsDetails();
                break;
        }
    }
}