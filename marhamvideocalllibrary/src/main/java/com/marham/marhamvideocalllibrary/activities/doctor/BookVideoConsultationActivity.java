package com.marham.marhamvideocalllibrary.activities.doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.adapters.hospital.HospitalDayAndDateAdapter;
import com.marham.marhamvideocalllibrary.adapters.hospital.HospitalTimeSlotsAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyButton;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.appointment.Appointment;
import com.marham.marhamvideocalllibrary.model.doctor.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.doctor.TimeSlotOfHospital;
import com.marham.marhamvideocalllibrary.model.doctor.TimeSlotsOfHospitalContainer;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.hospital.Days;
import com.marham.marhamvideocalllibrary.model.hospital.Hospital;
import com.marham.marhamvideocalllibrary.model.hospital.HospitalAvailableDaysAndDateServerResponse;
import com.marham.marhamvideocalllibrary.model.videoconsultation.BookConsultationServerResponse;
import com.marham.marhamvideocalllibrary.model.videoconsultation.VideoConsultanceModel;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;
import com.marham.marhamvideocalllibrary.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class BookVideoConsultationActivity extends BaseActivity implements ServerConnectListener {

    private ConstraintLayout parentLayout;
    private CircleImageView doctorPictureImageView;
    private BodyText doctorNameTextView;
    private BodyText doctorSpecialityTextView;
    private BodyText doctorDegreesTextView;
    private BodyText doctorExperienceTextView;

    private BodyText doctorReviewsTextView;
    private MyImageView doctorRatingsStar;
    private BodyText doctorFeeTextView;

    private RecyclerView hospitalDayAndDateRecyclerView;

    private RecyclerView timeSlotsRecyclerViewRecyclerView;
    private MyButton timeSlotsRetryButton;
    private ProgressBar timeSlotsProgressBar;
    private BodyText timeSlotsNoRecordFoundTextView;

    private BodyText dateTimeDayTextView;

    private MyButton confirmBookingButton;

    private DoctorInfo doctorInfo;
    private Hospital hospital;
    private TimeSlotsOfHospitalContainer timeSlotsOfHospitalContainer;
    private String selectedDate = "";
    private String selectedDay = "";
    private String selectedTime = "";

    private List<Days> daysList = new ArrayList<>();
    boolean isHospitalDaysAndTimeSlotsRequest = true;

    private List<TimeSlotOfHospital> allSlots = new ArrayList<>();
    private List<TimeSlotOfHospital> firstSlots = new ArrayList<>();
    private List<TimeSlotOfHospital> secondSlots = new ArrayList<>();
    private List<TimeSlotOfHospital> thirdSlots = new ArrayList<>();

    private VideoConsultanceModel videoConsultanceModel;

    private RetroFit2Callback<ServerResponseOld> retroFit2Callback;

    public static final int HOSPITAL_DAY_AND_DATE_ADAPTER = 0;
    public static final int HOSPITAL_TIME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_video_consultation);
        initVariables();
        initializeViews();
        setListeners();
        fetchData();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (R.id.confirm_booking_button == viewId) {
            if (hasUserEnteredRequiredInformation()) {
                bookOnlineConsultation(selectedDate);
            }
        }
    }

    private void initVariables() {
        receiveIntent();
    }

    protected void initializeViews() {
        super.initializeViews();
        parentLayout = findViewById(R.id.parent_layout);
        doctorPictureImageView = findViewById(R.id.doctor_picture_image_view);
        doctorNameTextView = findViewById(R.id.doctor_name_text_view);
        doctorSpecialityTextView = findViewById(R.id.doctor_speciality_text_view);
        doctorDegreesTextView = findViewById(R.id.doctor_degrees_text_view);
        doctorExperienceTextView = findViewById(R.id.doctor_experience_text_view);
        doctorReviewsTextView = findViewById(R.id.doctor_reviews_text_view);
        doctorRatingsStar = findViewById(R.id.doctor_ratings_stars);
        doctorFeeTextView = findViewById(R.id.doctor_fee_text_view);

        hospitalDayAndDateRecyclerView = findViewById(R.id.hospital_day_and_date_recycler_view);

        timeSlotsRecyclerViewRecyclerView = findViewById(R.id.time_slots_recycler_view_recycler_view);
        timeSlotsProgressBar = findViewById(R.id.time_slots_progress_bar);
        timeSlotsRetryButton = findViewById(R.id.time_slots_retry_button);
        timeSlotsNoRecordFoundTextView = findViewById(R.id.time_slots_no_record_found_text_view);

        dateTimeDayTextView = findViewById(R.id.date_time_day_text_view);

        confirmBookingButton = findViewById(R.id.confirm_booking_button);

    }

    private void setListeners() {
        confirmBookingButton.setOnClickListener(this);
    }

    private void receiveIntent() {
        Bundle bundle = getIntent().getExtras();
        doctorInfo = bundle.getParcelable(DoctorInfo.class.getCanonicalName());
        hospital = bundle.getParcelable(Hospital.class.getCanonicalName());
    }

    private void fetchData() {
        getHospitalAvailableDaysAndDates(hospital.getHospitalID(), "");
    }

    private void setDoctor(DoctorInfo doctorInfo) {
        setDoctorPicture(doctorInfo);
        doctorNameTextView.setText(doctorInfo.getDocName());
        doctorSpecialityTextView.setText(doctorInfo.getSpeciality());
        doctorDegreesTextView.setText(doctorInfo.getDocDegree());
        doctorExperienceTextView.setText("Exp." + doctorInfo.getDocExp() + " Year(s)");
        doctorFeeTextView.setText("Rs. " + hospital.getDocFee());
    }

    private void setDoctorPicture(DoctorInfo doctorInfo) {
        //TODO: Get Doctor Gender Here
//        int doctorPicturePlaceHolder;
//        if (doctorInfo.getGender().equals("0")) {
//            doctorPicturePlaceHolder = R.drawable.f_doctor_placeholder;
//        } else {
//            doctorPicturePlaceHolder = R.drawable.m_doctor_placholder;
//        }

        if (doctorInfo.getDocPic() != null && doctorInfo.getDocPic().length() > 0) {
            Picasso.get().load(doctorInfo.getDocPic())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(doctorPictureImageView);
        } else {
            MarhamUtils.getInstance().setBackground(this, doctorPictureImageView, R.drawable.m_doctor_placholder);

        }
    }

    public void setHospitalDayAndDateRecyclerView(List<Days> daysList) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hospitalDayAndDateRecyclerView.setLayoutManager(layoutManager);
        HospitalDayAndDateAdapter hospitalDayAndDateAdapter = new HospitalDayAndDateAdapter(this, daysList, adapterViewItemClickedListener);
        hospitalDayAndDateRecyclerView.setAdapter(hospitalDayAndDateAdapter);
    }

    public void setHospitalTimeSlotsRecyclerView(List<TimeSlotOfHospital> morningList) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        timeSlotsRecyclerViewRecyclerView.setLayoutManager(layoutManager);
        HospitalTimeSlotsAdapter hospitalTimeSlotsAdapter = new HospitalTimeSlotsAdapter(this, allSlots, adapterViewItemClickedListener);
        timeSlotsRecyclerViewRecyclerView.setAdapter(hospitalTimeSlotsAdapter);
    }

    public void setViewsBeforeCallingAPI() {
        parentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsAfterCallingAPI() {
        parentLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileCallingAPI() {
        parentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileCallingAPI() {
        parentLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    public void setViewsBeforeGettingTimeSlots() {
        timeSlotsRecyclerViewRecyclerView.setVisibility(View.INVISIBLE);
        timeSlotsProgressBar.setVisibility(View.VISIBLE);
        timeSlotsRetryButton.setVisibility(View.GONE);
        timeSlotsNoRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingTimeSlots() {
        timeSlotsRecyclerViewRecyclerView.setVisibility(View.VISIBLE);
        timeSlotsProgressBar.setVisibility(View.GONE);
        timeSlotsRetryButton.setVisibility(View.GONE);
        timeSlotsNoRecordFoundTextView.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingTimeSlots() {
        timeSlotsRecyclerViewRecyclerView.setVisibility(View.INVISIBLE);
        timeSlotsProgressBar.setVisibility(View.GONE);
        timeSlotsRetryButton.setVisibility(View.GONE);
        timeSlotsNoRecordFoundTextView.setVisibility(View.VISIBLE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingTimeSlots() {
        timeSlotsRecyclerViewRecyclerView.setVisibility(View.INVISIBLE);
        timeSlotsProgressBar.setVisibility(View.GONE);
        timeSlotsProgressBar.setVisibility(View.VISIBLE);
        timeSlotsNoRecordFoundTextView.setVisibility(View.GONE);
    }

    private final AdapterViewItemClickedListener adapterViewItemClickedListener = new AdapterViewItemClickedListener() {
        @Override
        public void onAdatviewItemClicked(int position) {

        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID) {
            switch (requestID) {
                case BookVideoConsultationActivity.HOSPITAL_DAY_AND_DATE_ADAPTER:
                    updateSelectedHospital(position);
                    hospitalDayAndDateRecyclerView.getAdapter().notifyDataSetChanged();
                    getHospitalAvailableDaysAndDates(hospital.getHospitalID(), selectedDate);
                    break;

                case BookVideoConsultationActivity.HOSPITAL_TIME:
                    updateSelectedTimeSlot(position);
                    timeSlotsRecyclerViewRecyclerView.getAdapter().notifyDataSetChanged();
                    setDateTimeAndDayTextView();
                    break;
            }

        }

        @Override
        public void onAdatviewItemClicked(int position, int requestID, String s) {

        }
    };

    private void setDoctorAndHospitalInformation(TimeSlotsOfHospitalContainer timeSlotsOfHospitalContainer) {
        String dlID = doctorInfo.getDlID();
        doctorInfo = timeSlotsOfHospitalContainer.getDoctorInfo();
        doctorInfo.setDlID(dlID);
        setDoctor(doctorInfo);
        if (timeSlotsOfHospitalContainer.getHospital() != null) {
            hospital = timeSlotsOfHospitalContainer.getHospital();
        }
    }

    private void setDaysAndTimeSlotsViewsAndVariables(TimeSlotsOfHospitalContainer timeSlotsOfHospitalContainer) {
        if ((timeSlotsOfHospitalContainer.getDays() != null && !timeSlotsOfHospitalContainer.getDays().isEmpty())) {
            setDaysListAndRecyclerView(timeSlotsOfHospitalContainer);
            setTimeSlotsListAndRecyclerView(timeSlotsOfHospitalContainer);
        } else {
            Toast.makeText(this, "No days registered against this doctor", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDaysListAndRecyclerView(TimeSlotsOfHospitalContainer timeSlotsOfHospitalContainer) {
        if (daysList.isEmpty()) {
            daysList = timeSlotsOfHospitalContainer.getDays();
            updateSelectedHospital(0);
            setHospitalDayAndDateRecyclerView(daysList);
        }
    }

    private void setTimeSlotsListAndRecyclerView(TimeSlotsOfHospitalContainer timeSlotsOfHospitalContainer) {
        clearAllTimeSlotsLists();
        addAllTimeSlots(timeSlotsOfHospitalContainer);
        addTimeSlotsToAllSlots();
        checkIfListContainsItemsOrNotAndRespondAccordingly();

    }

    private void clearAllTimeSlotsLists() {
        firstSlots.clear();
        secondSlots.clear();
        thirdSlots.clear();
    }

    private void addAllTimeSlots(TimeSlotsOfHospitalContainer timeSlotsOfHospitalContainer) {
        if (timeSlotsOfHospitalContainer.getMorning() != null && !timeSlotsOfHospitalContainer.getMorning().isEmpty()) {
            firstSlots.addAll(timeSlotsOfHospitalContainer.getMorning());
        }
        if (timeSlotsOfHospitalContainer.getAfternoon() != null && !timeSlotsOfHospitalContainer.getAfternoon().isEmpty()) {
            secondSlots.addAll(timeSlotsOfHospitalContainer.getAfternoon());
        }

        if (timeSlotsOfHospitalContainer.getEvening() != null && !timeSlotsOfHospitalContainer.getEvening().isEmpty()) {
            thirdSlots.addAll(timeSlotsOfHospitalContainer.getEvening());
        }
    }

    private void addTimeSlotsToAllSlots() {
        allSlots.addAll(firstSlots);
        allSlots.addAll(secondSlots);
        allSlots.addAll(thirdSlots);
    }

    private void checkIfListContainsItemsOrNotAndRespondAccordingly() {
        if (allSlots.isEmpty()) {
            setViewsIncaseNoRecordFoundWhileGettingTimeSlots();
        } else {
            updateSelectedTimeSlot(0);
            setHospitalTimeSlotsRecyclerView(allSlots);
        }
    }

    private void updateSelectedHospital(int selectedPosition) {
        selectedDate = daysList.get(selectedPosition).getFormattedDate();
        selectedDay = daysList.get(selectedPosition).getDay();
        selectedTime = "";
        for (int i = 0; i < daysList.size(); i++) {
            daysList.get(i).setSelected(i == selectedPosition);
        }
    }

    private void updateSelectedTimeSlot(int selectedPosition) {
        selectedTime = allSlots.get(selectedPosition).getSlot();
        for (int i = 0; i < allSlots.size(); i++) {
            allSlots.get(i).setSelected(i == selectedPosition);
        }
    }

    private void setDateTimeAndDayTextView() {
        dateTimeDayTextView.setText(String.format("%s %s (%s)", selectedDate, selectedTime, selectedDay));
    }

    private boolean hasUserEnteredRequiredInformation() {
        boolean areFieldsValid;
        if (selectedDate.equals("") && selectedTime.equals("")) {
            MarhamUtils.getInstance().showMessage(this, "Plese select Date and Time", Toast.LENGTH_LONG);
            areFieldsValid = false;
        } else if (selectedDate.equals("")) {
            MarhamUtils.getInstance().showMessage(this, "Plese select Date", Toast.LENGTH_LONG);
            areFieldsValid = false;
        } else if (selectedTime.equals("")) {
            MarhamUtils.getInstance().showMessage(this, "Plese select Time", Toast.LENGTH_LONG);
            areFieldsValid = false;
        } else {
            MarhamUtils.getInstance().showMessage(this, "Booking Appointment", Toast.LENGTH_LONG);
            areFieldsValid = true;
        }
        return areFieldsValid;
    }

    public void getHospitalAvailableDaysAndDates(String hospitalID, String date) {
        isHospitalDaysAndTimeSlotsRequest = date.equals("");

        if (isHospitalDaysAndTimeSlotsRequest) {
            setViewsBeforeCallingAPI();
        } else {
            setViewsBeforeGettingTimeSlots();
        }
        APIClient apiClient = new APIClient();
        String loggedInUserId = MarhamVideoCallHelper.getInstance().getUserId();
        String fireBaseToken = MarhamVideoCallHelper.getInstance().getFireBaseToken();
        String deviceType = "1";

        Call<HospitalAvailableDaysAndDateServerResponse> call;
        call = apiClient.getHospitalAvailableDaysAndDates(hospitalID, date, loggedInUserId, fireBaseToken, deviceType, AppConstants.HOSPITAL_TYPE.IN_CLINIC, doctorInfo.getDlID(), "en", "0");

        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES);
        call.enqueue(retroFit2Callback);
    }

    public void bookOnlineConsultation(String date) {
        setViewsBeforeCallingAPI();
        videoConsultanceModel = new VideoConsultanceModel();
        videoConsultanceModel.setLoggedInUserId(MarhamVideoCallHelper.getInstance().getUserId());

        videoConsultanceModel.setPatientName(MarhamVideoCallHelper.getInstance().getUserName());
        videoConsultanceModel.setPhone(MarhamVideoCallHelper.getInstance().getUserPhoneNumber());
        videoConsultanceModel.setDate(date);
        videoConsultanceModel.setTime(selectedTime);
        videoConsultanceModel.setdId(doctorInfo.getDlID());
        videoConsultanceModel.setSpId(doctorInfo.getSubspID());
        videoConsultanceModel.setDocFee(doctorInfo.getDocFee());
        videoConsultanceModel.setAgreedForBooking("1");
        videoConsultanceModel.setUserId(MarhamVideoCallHelper.getInstance().getUserId());

        videoConsultanceModel.setIsCallMyDoctor("");

        videoConsultanceModel.setPromoCode("");
        videoConsultanceModel.setProgramId(AppConstants.PROGRAM_ID.ONLINE_CONSULTATION);

        APIClient apiClient = new APIClient();
        Call<BookConsultationServerResponse> call = apiClient.bookOnlineConsultation(videoConsultanceModel);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.BOOK_ONLINE_CONSULTATION);
        call.enqueue(retroFit2Callback);
    }

    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    if (isHospitalDaysAndTimeSlotsRequest) {
                        setViewsAfterCallingAPI();
                    } else {
                        setViewsAfterGettingTimeSlots();
                    }
                    HospitalAvailableDaysAndDateServerResponse hospitalAvailableDaysAndDateServerResponse = (HospitalAvailableDaysAndDateServerResponse) response;
                    timeSlotsOfHospitalContainer = hospitalAvailableDaysAndDateServerResponse.getData();

                    setDoctorAndHospitalInformation(timeSlotsOfHospitalContainer);
                    setDaysAndTimeSlotsViewsAndVariables(timeSlotsOfHospitalContainer);
                    setDateTimeAndDayTextView();
                } else {
                    if (isHospitalDaysAndTimeSlotsRequest) {
                        setViewsIncaseNoRecordFoundWhileCallingAPI();
                    } else {
                        setViewsIncaseNoRecordFoundWhileGettingTimeSlots();
                    }
                    MarhamUtils.getInstance().showAPIResponseMessage(this, doctorInfo.getDocName() + " is not available now please choose another doctor.\nThank you");
                }
                break;
            case AppConstants.API.API_END_POINT_NUMBER.BOOK_ONLINE_CONSULTATION:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    MarhamUtils.getInstance().showAPIResponseMessage(this, "Appointment Booked");
                    Appointment appointment = new Appointment();
                    MarhamVideoCallHelper.getInstance().getMarhamPaymentListener().onPaymentRequested(appointment);
                    finish();
                } else {
                    setViewsIncaseNoRecordFoundWhileCallingAPI();
                    MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
                }
                break;
        }

    }

    @Override
    public void onFailure(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES:
                MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
                if (isHospitalDaysAndTimeSlotsRequest) {
                    setViewsIncaseOfInternetFailureOrUnExpectedResultWhileCallingAPI();
                    finish();
                } else {
                    setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingTimeSlots();
                }
                break;
            case AppConstants.API.API_END_POINT_NUMBER.BOOK_ONLINE_CONSULTATION:
                MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileCallingAPI();
                break;
        }

    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES:
                MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
                if (isHospitalDaysAndTimeSlotsRequest) {
                    setViewsIncaseOfInternetFailureOrUnExpectedResultWhileCallingAPI();
                    finish();
                } else {
                    setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingTimeSlots();
                }
                break;
            case AppConstants.API.API_END_POINT_NUMBER.BOOK_ONLINE_CONSULTATION:
                MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileCallingAPI();
                break;
        }

    }
}