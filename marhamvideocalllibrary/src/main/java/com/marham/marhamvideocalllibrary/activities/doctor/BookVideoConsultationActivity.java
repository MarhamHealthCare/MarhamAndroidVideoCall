package com.marham.marhamvideocalllibrary.activities.doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.BaseActivity;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.model.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.Hospital;
import com.marham.marhamvideocalllibrary.model.ServerResponse;
import com.marham.marhamvideocalllibrary.model.doctor.TimeSlotOfHospital;
import com.marham.marhamvideocalllibrary.model.hospital.Days;
import com.marham.marhamvideocalllibrary.model.hospital.HospitalAvailableDaysAndDateServerResponse;
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
    public CircleImageView doctorPictureImageView;
    public BodyText doctorNameTextView;
    public BodyText doctorSpecialityTextView;
    public BodyText doctorDegreesTextView;
    public BodyText doctorExperienceTextView;

    public BodyText doctorReviewsTextView;
    public MyImageView doctorRatingsStar;
    public BodyText doctorFeeTextView;

    private DoctorInfo doctorInfo;
    private Hospital hospital;
    private String formattedDate;
    private String selectedTime;

    private List<Days> daysListForVariation = new ArrayList<>();

    private List<TimeSlotOfHospital> allSlots = new ArrayList<>();
    private List<TimeSlotOfHospital> firstSlots = new ArrayList<>();
    private List<TimeSlotOfHospital> secondSlots = new ArrayList<>();
    private List<TimeSlotOfHospital> thirdSlots = new ArrayList<>();

    private RetroFit2Callback<ServerResponse> retroFit2Callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_video_consultation);
        initVariables();
        initializeViews();
        fetchData();
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
        setDoctor(doctorInfo);

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

        if (doctorInfo.getRating().equals("0")) {
            doctorRatingsStar.setVisibility(View.INVISIBLE);
            doctorReviewsTextView.setVisibility(View.INVISIBLE);
        } else {
            doctorRatingsStar.setVisibility(View.VISIBLE);
            doctorReviewsTextView.setVisibility(View.VISIBLE);

            if (doctorInfo.getTotalReviews() != null) {
                int reviewsCount = Integer.parseInt(doctorInfo.getTotalReviews());
                if (reviewsCount >= 100) {
                    doctorReviewsTextView.setText("100+ reviews");
                } else {
                    doctorReviewsTextView.setText(doctorInfo.getTotalReviews() + " reviews");
                }
            } else {
                doctorReviewsTextView.setText(doctorInfo.getTotalReviews() + " reviews");
            }

        }
    }

    private void setDoctorPicture(DoctorInfo doctorInfo) {
        int doctorPicturePlaceHolder;
        if (doctorInfo.getGender().equals("0")) {
            doctorPicturePlaceHolder = R.drawable.f_doctor_placeholder;
        } else {
            doctorPicturePlaceHolder = R.drawable.m_doctor_placholder;
        }

        if (doctorInfo.getDocPic() != null && doctorInfo.getDocPic().length() > 0) {
            Picasso.get().load(doctorInfo.getDocPic())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(doctorPictureImageView);
        } else {
            MarhamUtils.getInstance().setBackground(this, doctorPictureImageView, doctorPicturePlaceHolder);

        }
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


    public void getHospitalAvailableDaysAndDates(String hospitalID, String date) {
        setViewsBeforeCallingAPI();
        APIClient apiClient = new APIClient();
        String loggedInUserId = MarhamVideoCallHelper.getInstance().getUserId();
        String fireBaseToken = MarhamVideoCallHelper.getInstance().getFireBaseToken();
        String deviceType = "1";
        Call<HospitalAvailableDaysAndDateServerResponse> call;
        if (doctorInfo != null && doctorInfo.getIsInHouseDoctor() != null) {
            call = apiClient.getHospitalAvailableDaysAndDates(hospitalID, date, loggedInUserId, fireBaseToken, deviceType, AppConstants.HOSPITAL_TYPE.VIDEO_CONSULTATION, doctorInfo.getDlID(), "v1", doctorInfo.getIsInHouseDoctor());
        } else {
            call = apiClient.getHospitalAvailableDaysAndDates(hospitalID, date, loggedInUserId, fireBaseToken, deviceType, AppConstants.HOSPITAL_TYPE.VIDEO_CONSULTATION, doctorInfo.getDlID(), "v1", "0");
        }
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES);
        call.enqueue(retroFit2Callback);
    }


    @Override
    public void onSuccess(ServerResponse response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS)) {
                    view.setViewsAfterGettingHospitals();
                    HospitalAvailableDaysAndDateServerResponse hospitalAvailableDaysAndDateServerResponse = (HospitalAvailableDaysAndDateServerResponse) response;
                    view.setDoctorName(hospitalAvailableDaysAndDateServerResponse.getData().getDoctorInfo());

                    if (hospitalAvailableDaysAndDateServerResponse.getData().getHospital() != null) {
                        hospital = hospitalAvailableDaysAndDateServerResponse.getData().getHospital();
                    }
                    if ((hospitalAvailableDaysAndDateServerResponse.getData().getPatientCarePackageTitle() != null && !hospitalAvailableDaysAndDateServerResponse.getData().getPatientCarePackageTitle().isEmpty()) &&
                            hospitalAvailableDaysAndDateServerResponse.getData().getPatientCarePackageDescription() != null && !hospitalAvailableDaysAndDateServerResponse.getData().getPatientCarePackageDescription().isEmpty()) {
                        view.setWalletInformation(hospitalAvailableDaysAndDateServerResponse.getData());
                        slot = hospitalAvailableDaysAndDateServerResponse.getData();
                    }

//                    if (newTimeSlotsServerResponse.getData().getWalletTermsAndConditions() != null) {
//                        walletTermsAndConditions = newTimeSlotsServerResponse.getData().getWalletTermsAndConditions();
//                    }

//                    if (newTimeSlotsServerResponse.getData().getPatientCarePackageDetails() != null && !newTimeSlotsServerResponse.getData().getPatientCarePackageDescription().isEmpty()) {
//                        patientCarePackageDetail = newTimeSlotsServerResponse.getData().getPatientCarePackageDetails();
//                    }
                    if (hospitalAvailableDaysAndDateServerResponse.getData().getMorning() != null && !hospitalAvailableDaysAndDateServerResponse.getData().getMorning().isEmpty()) {
                        time = hospitalAvailableDaysAndDateServerResponse.getData().getMorning().get(0).getSlot();
                        view.setDoctorHospitalId(hospitalAvailableDaysAndDateServerResponse.getData().getMorning().get(0).getDoctorHospitalId());
//                        view.updateSelectedRecyclerView(1);
                        selectedRecyclerView = 1;
                    } else if (hospitalAvailableDaysAndDateServerResponse.getData().getAfternoon() != null && !hospitalAvailableDaysAndDateServerResponse.getData().getAfternoon().isEmpty()) {
                        time = hospitalAvailableDaysAndDateServerResponse.getData().getAfternoon().get(0).getSlot();
                        view.setDoctorHospitalId(hospitalAvailableDaysAndDateServerResponse.getData().getAfternoon().get(0).getDoctorHospitalId());
//                        view.updateSelectedRecyclerView(2);
                        selectedRecyclerView = 2;
                    } else if (hospitalAvailableDaysAndDateServerResponse.getData().getEvening() != null && !hospitalAvailableDaysAndDateServerResponse.getData().getEvening().isEmpty()) {
                        time = hospitalAvailableDaysAndDateServerResponse.getData().getEvening().get(0).getSlot();
                        view.setDoctorHospitalId(hospitalAvailableDaysAndDateServerResponse.getData().getEvening().get(0).getDoctorHospitalId());
//                        view.updateSelectedRecyclerView(3);
                        selectedRecyclerView = 3;
                    }

                    if (hospitalAvailableDaysAndDateServerResponse.getData().getDoctorInfo() != null && hospitalAvailableDaysAndDateServerResponse.getData().getHospital() != null) {
                        view.setDoctorAndHospitalInformation(hospitalAvailableDaysAndDateServerResponse.getData().getDoctorInfo(), hospitalAvailableDaysAndDateServerResponse.getData().getHospital());
                        this.revenueFee = hospitalAvailableDaysAndDateServerResponse.getData().getHospital().getDocFee();
                    }

                    if ((hospitalAvailableDaysAndDateServerResponse.getData().getDays() != null && !hospitalAvailableDaysAndDateServerResponse.getData().getDays().isEmpty())) {
                        daysList = hospitalAvailableDaysAndDateServerResponse.getData().getDays();
                        if (hospitalAvailableDaysAndDateServerResponse.getData().getDoctorInfo() != null && hospitalAvailableDaysAndDateServerResponse.getData().getDoctorInfo().getIsInHouseDoctor() != null) {
                            if (hospitalAvailableDaysAndDateServerResponse.getData().getDoctorInfo().getIsInHouseDoctor().equalsIgnoreCase("1")) {
                                Days daysListObject = null;
                                if (hospitalAvailableDaysAndDateServerResponse.getData().getDays() != null && hospitalAvailableDaysAndDateServerResponse.getData().getDays().size() > 0)
                                    daysListObject = hospitalAvailableDaysAndDateServerResponse.getData().getDays().get(0);
                                daysList.clear();
                                daysList.add(daysListObject);
                            }
                        }
                        formattedDate = daysList.get(0).getFormattedDate();
                        view.setHospitalRecyclerView(daysList);
                        if (hospitalAvailableDaysAndDateServerResponse.getData().getMorning() != null && !hospitalAvailableDaysAndDateServerResponse.getData().getMorning().isEmpty()) {
                            view.setViewsIfMorningListIsNotEmpty(newTimeSlotsServerResponse.getData().getMorning());
                            if (!hospitalAvailableDaysAndDateServerResponse.getData().getMorning().isEmpty()) {
                                morningList.addAll(hospitalAvailableDaysAndDateServerResponse.getData().getMorning());
                                view.setMorningListRecyclerView(morningList, selectedRecyclerView);
                            } else {
                                view.setViewsWhenNoTimingsFoundAgainstThatParticularDay();
                            }
                        } else {
                            view.setViewsIfMorningListIsEmpty();
                        }
                        if (hospitalAvailableDaysAndDateServerResponse.getData().getAfternoon() != null && !hospitalAvailableDaysAndDateServerResponse.getData().getAfternoon().isEmpty()) {
                            view.setViewsIfAfterNoonListIsNotEmpty(newTimeSlotsServerResponse.getData().getAfternoon());
                            if (!hospitalAvailableDaysAndDateServerResponse.getData().getAfternoon().isEmpty()) {
                                afternoonList.addAll(hospitalAvailableDaysAndDateServerResponse.getData().getAfternoon());
                                view.setafternoonListRecyclerView(afternoonList, selectedRecyclerView);
                            } else {
                                view.setViewsWhenNoTimingsFoundAgainstThatParticularDay();
                            }
                        } else {
                            view.setViewsIfAfternoonListIsEmpty();
                        }


                        if (hospitalAvailableDaysAndDateServerResponse.getData().getEvening() != null && !hospitalAvailableDaysAndDateServerResponse.getData().getEvening().isEmpty()) {
                            view.setViewsIfEveningListIsNotEmpty(hospitalAvailableDaysAndDateServerResponse.getData().getEvening());
                            if (!hospitalAvailableDaysAndDateServerResponse.getData().getEvening().isEmpty()) {
                                eveningList.addAll(hospitalAvailableDaysAndDateServerResponse.getData().getEvening());
                                view.setEveningListRecyclerView(eveningList, selectedRecyclerView);
                            } else {
                                view.setViewsWhenNoTimingsFoundAgainstThatParticularDay();
                            }
                        } else {
                            view.setViewsIfEveningListIsEmpty();
                        }

                    } else {
                        Toast.makeText(this, "No days registered against this doctor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    view.setViewsWhenNoTimeSlotsFoundAgainstThatParticularDay();
                    MarhamUtils.getInstance().showAPIResponseMessage(this, "Note", doctorInfo.getDocName() + " is not available now please choose another doctor.\nThank you.", "Ok", onClickListener);
                }
                break;
        }

    }

    @Override
    public void onFailure(ServerResponse response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES:
                MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileCallingAPI();
                break;
        }

    }

    @Override
    public void onSessionExpiry(ServerResponse response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_HOSPITAL_AVAILABLE_DAYS_AND_DATES:
                MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileCallingAPI();
                break;
        }

    }
}