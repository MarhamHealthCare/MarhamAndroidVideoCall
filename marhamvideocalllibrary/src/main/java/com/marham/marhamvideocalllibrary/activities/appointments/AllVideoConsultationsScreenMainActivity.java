package com.marham.marhamvideocalllibrary.activities.appointments;

import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.activities.patientrecord.PrescriptionActivity;
import com.marham.marhamvideocalllibrary.activities.videocall.WaitingAreaActivity;
import com.marham.marhamvideocalllibrary.adapters.appointments.BaseAllVideoConsultationsAdapter;
import com.marham.marhamvideocalllibrary.adapters.appointments.PreviousVideoConsultationsAdapter;
import com.marham.marhamvideocalllibrary.adapters.appointments.UpcomingVideoConsultationsAdapter;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.appointment.Appointment;
import com.marham.marhamvideocalllibrary.model.appointment.videoconsultationlisting.AllAppointmentListingData;
import com.marham.marhamvideocalllibrary.model.appointment.videoconsultationlisting.AllAppointmentListingServerResponse;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.order.GenericeOrderObject;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class AllVideoConsultationsScreenMainActivity extends BaseActivity implements ServerConnectListener {

    private NestedScrollView parentLayout;

    private ConstraintLayout upcomingAppointmentsViewsContainer;
    private RecyclerView upcomingAppointmentsRecyclerView;

    private ConstraintLayout previousAppointmentsViewsContainer;
    private RecyclerView previousAppointmentsRecyclerView;

    private List<Appointment> pastAppointmentsList = new ArrayList<>();
    private List<Appointment> upcomingAppointmentsList = new ArrayList<>();

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
        int viewId = view.getId();
        if (viewId == R.id.retry_button) {
            getUserAppointments();
        }

    }

    protected void initializeViews() {
        super.initializeViews();
        parentLayout = findViewById(R.id.parent_layout);

        upcomingAppointmentsViewsContainer = findViewById(R.id.upcoming_appointments_views_container);
        upcomingAppointmentsRecyclerView = findViewById(R.id.upcoming_appointment_recycler_view);

        previousAppointmentsViewsContainer = findViewById(R.id.previous_appointments_views_container);
        previousAppointmentsRecyclerView = findViewById(R.id.previous_appointment_recycler_view);

    }

    private void setUpcomingAppointmentsRecyclerView(List<Appointment> upcomingAppointmentsList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        upcomingAppointmentsRecyclerView.setLayoutManager(linearLayoutManager);
        upcomingAppointmentsRecyclerView.setAdapter(new UpcomingVideoConsultationsAdapter(this, adapterViewItemClickedListener, upcomingAppointmentsList, BaseAllVideoConsultationsAdapter.UPCOMING_APPOINTMENTS));
        upcomingAppointmentsRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void setPastAppointmentsRecyclerView(List<Appointment> pastAppointmentsList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        previousAppointmentsRecyclerView.setLayoutManager(linearLayoutManager);
        previousAppointmentsRecyclerView.setAdapter(new PreviousVideoConsultationsAdapter(this, adapterViewItemClickedListener, pastAppointmentsList, BaseAllVideoConsultationsAdapter.PREVIOUS_APPOINTMENTS));
        previousAppointmentsRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void setUpcomingAppointmentsViews(AllAppointmentListingData allAppointmentListingData) {
        if (allAppointmentListingData.getUpcoming() != null && allAppointmentListingData.getUpcoming().size() > 0) {
            upcomingAppointmentsList.addAll(getUpcomingAppointmentsListFromGenericOrdersList(allAppointmentListingData.getUpcoming()));
            setUpcomingAppointmentsRecyclerView(upcomingAppointmentsList);
        } else {
            upcomingAppointmentsViewsContainer.setVisibility(View.GONE);
        }
    }

    private void setPreviousAppointmentsViews(AllAppointmentListingData allAppointmentListingData) {
        if (allAppointmentListingData.getPast() != null && allAppointmentListingData.getPast().size() > 0) {
            pastAppointmentsList.addAll(getPastAppointmentsListFromGenericOrdersList(allAppointmentListingData.getPast()));
            setPastAppointmentsRecyclerView(pastAppointmentsList);
        } else {
            previousAppointmentsViewsContainer.setVisibility(View.GONE);
        }
    }

    private List<Appointment> getUpcomingAppointmentsListFromGenericOrdersList(List<GenericeOrderObject> upcomingGenericOrdersList) {
        List<Appointment> upcomingAppointmentsList = new ArrayList<>();
        for (int i = 0; i < upcomingGenericOrdersList.size(); i++) {
            upcomingAppointmentsList.add(upcomingGenericOrdersList.get(i).getOnlineConsultation());
        }
        return upcomingAppointmentsList;
    }

    private List<Appointment> getPastAppointmentsListFromGenericOrdersList(List<GenericeOrderObject> pastGenericOrdersList) {
        List<Appointment> pastAppointmentsList = new ArrayList<>();
        for (int i = 0; i < pastGenericOrdersList.size(); i++) {
            pastAppointmentsList.add(pastGenericOrdersList.get(i).getOnlineConsultation());
        }
        return pastAppointmentsList;
    }

    private AdapterViewItemClickedListener adapterViewItemClickedListener = new AdapterViewItemClickedListener() {
        @Override
        public void onAdatviewItemClicked(int position) {

        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID) {
            switch (requestID) {
                case BaseAllVideoConsultationsAdapter.UPCOMING_APPOINTMENTS:
                    navigateOnAppointment(upcomingAppointmentsList.get(position));
                    break;
                case BaseAllVideoConsultationsAdapter.PREVIOUS_APPOINTMENTS:
                    navigateOnAppointment(pastAppointmentsList.get(position));
                    break;
            }
        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID, String s) {

        }
    };

    private void navigateOnAppointment(Appointment appointment) {
        switch (appointment.getAppointmentSubStatusID()) {
            case Appointment.SCHEDULE:
                openWaitingArea(appointment);
                break;

            case Appointment.INPROGRRSS:
                openWaitingArea(appointment);
                break;

            case Appointment.VIEW_PRESCRIPTION:
                openViewPrescriptionScreen(appointment);
                break;
        }

    }

    private void openWaitingArea(Appointment appointment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Appointment.class.getCanonicalName(), appointment);
        MarhamUtils.getInstance().startActivity(this, WaitingAreaActivity.class, false, bundle);
    }

    private void openViewPrescriptionScreen(Appointment appointment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Appointment.class.getCanonicalName(), appointment);
        MarhamUtils.getInstance().startActivity(this, PrescriptionActivity.class, false, bundle);
    }

    private void setViewsBeforeGettingDoctorsDetails() {
        parentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    private void setViewsAfterGettingDoctorsDetails() {
        parentLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    private void setViewsIncaseNoRecordFoundWhileGettingDoctorsDetails() {
        parentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    private void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorsDetails() {
        parentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    private void getUserAppointments() {
        setViewsBeforeGettingDoctorsDetails();
        APIClient apiClient = new APIClient();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.USER_ID_KEY, MarhamVideoCallHelper.getInstance().getUserId());
        hashMap.put(AppConstants.API.API_KEYS.REPORTS_KEY, "0");
        hashMap.put(AppConstants.API.API_KEYS.PROGRAM_ID_KEY, AppConstants.PROGRAM_ID.ONLINE_CONSULTATION);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_USER_APPOINTMENT);
        Call<AllAppointmentListingServerResponse> call = apiClient.getPatientOrderAndAppointmentList(hashMap);
        call.enqueue(retroFit2Callback);
    }


    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {

            case AppConstants.API.API_END_POINT_NUMBER.GET_USER_APPOINTMENT:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    setViewsAfterGettingDoctorsDetails();
                    AllAppointmentListingServerResponse allAppointmentsServerResponse = (AllAppointmentListingServerResponse) response;
                    setUpcomingAppointmentsViews(allAppointmentsServerResponse.getData());
                    setPreviousAppointmentsViews(allAppointmentsServerResponse.getData());
                } else {
                    setViewsIncaseNoRecordFoundWhileGettingDoctorsDetails();
                }
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