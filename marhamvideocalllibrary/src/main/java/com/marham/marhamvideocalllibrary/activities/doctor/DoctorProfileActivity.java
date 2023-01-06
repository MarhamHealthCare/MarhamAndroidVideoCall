package com.marham.marhamvideocalllibrary.activities.doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.MarhamVideoCallHelper;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.general.BaseActivity;
import com.marham.marhamvideocalllibrary.adapters.doctor.DoctorExperienceAdapter;
import com.marham.marhamvideocalllibrary.adapters.doctor.DoctorReviewsAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.model.doctor.DoctorExperience;
import com.marham.marhamvideocalllibrary.model.doctor.DoctorInfo;
import com.marham.marhamvideocalllibrary.model.doctor.DoctorProfileGenericData;
import com.marham.marhamvideocalllibrary.model.doctor.NewDoctorProfileServerResponse;
import com.marham.marhamvideocalllibrary.model.general.ServerResponseOld;
import com.marham.marhamvideocalllibrary.model.hospital.Hospital;
import com.marham.marhamvideocalllibrary.model.review.Reviews;
import com.marham.marhamvideocalllibrary.network.APIClient;
import com.marham.marhamvideocalllibrary.network.RetroFit2Callback;
import com.marham.marhamvideocalllibrary.network.ServerConnectListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;
import com.marham.marhamvideocalllibrary.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class DoctorProfileActivity extends BaseActivity implements ServerConnectListener {

    private ScrollView scrollView;
    private BodyText doctorNameTextView;
    private BodyText doctorSpecialityTextView;
    private CircleImageView doctorPictureImageView;
    private BodyText doctorDegreesTextView;
    private BodyText doctorCategoryAndExperienceTextView;

    private BodyText yearsOfExperienceTextView;
    private BodyText doctorSatisfactionTextView;
    private BodyText reviewsTextView;

    private ConstraintLayout aboutDoctorViewsContainer;
    private BodyText aboutDoctorHeadingTextView;
    private BodyText aboutDoctorTextView;

    private ConstraintLayout doctorExperiencesViewsContainer;
    private RecyclerView doctorExperiencesRecyclerView;

    private ConstraintLayout doctorReviewsViewsContainer;
    private RecyclerView doctorReviewsRecyclerView;

    private ConstraintLayout bookVideoConsultationViewsContainer;

    private DoctorProfileGenericData doctorProfileGenericData;
    private DoctorInfo doctorInfo;
    private List<Hospital> hospitalList = new ArrayList<>();
    private List<DoctorExperience> doctorExperienceList = new ArrayList<>();
    private List<Reviews> reviewsList = new ArrayList<>();
    private RetroFit2Callback<NewDoctorProfileServerResponse> retroFit2Callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        intializeViews();
        initializeVariables();
        setListeners();
        getDoctorDetails(doctorInfo.getDlID());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (viewId == R.id.retry_button) {
            getDoctorDetails(doctorInfo.getDlID());
        } else if (viewId == R.id.book_consultation_views_container) {
            openBookVideConsultationScreen();
        }
    }

    private void intializeViews() {
        super.initializeViews();
        scrollView = findViewById(R.id.scroll_view);
        doctorNameTextView = findViewById(R.id.doctor_name_text_view);
        doctorSpecialityTextView = findViewById(R.id.doctor_speciality_text_view);
        doctorPictureImageView = findViewById(R.id.doctor_picture_image_view);
        doctorDegreesTextView = findViewById(R.id.doctor_degrees_text_view);
        doctorCategoryAndExperienceTextView = findViewById(R.id.doctor_category_and_experience_text_view);
        yearsOfExperienceTextView = findViewById(R.id.years_of_experience_text_view);
        doctorSatisfactionTextView = findViewById(R.id.doctor_satisfaction_text_view);
        reviewsTextView = findViewById(R.id.reviews_text_view);
        aboutDoctorViewsContainer = findViewById(R.id.about_doctor_views_container);
        aboutDoctorHeadingTextView = findViewById(R.id.about_doctor_heading_text_view);
        aboutDoctorTextView = findViewById(R.id.about_doctor_text_view);
        doctorExperiencesViewsContainer = findViewById(R.id.doctor_experiences_views_container);
        doctorExperiencesRecyclerView = findViewById(R.id.doctor_experiences_recycler_view);
        doctorReviewsViewsContainer = findViewById(R.id.reviews_views_container);
        doctorReviewsRecyclerView = findViewById(R.id.doctor_reviews_recycler_view);
        bookVideoConsultationViewsContainer = findViewById(R.id.book_consultation_views_container);
    }

    private void initializeVariables() {
        receiveIntent();
    }

    private void setListeners() {
        bookVideoConsultationViewsContainer.setOnClickListener(this);
    }

    private void receiveIntent() {
        Bundle bundle = getIntent().getExtras();
        doctorInfo = bundle.getParcelable(DoctorInfo.class.getCanonicalName());

    }

    private void extractDoctorsData(NewDoctorProfileServerResponse response) {
        NewDoctorProfileServerResponse newDoctorProfileServerResponse = response;
        doctorProfileGenericData = newDoctorProfileServerResponse.getData();
        if (newDoctorProfileServerResponse.getData().getDetails() != null) {
            doctorInfo = newDoctorProfileServerResponse.getData().getDetails();
        }

        setHospitals(newDoctorProfileServerResponse);
        setHeader(doctorProfileGenericData);
        statsViewsContainer(doctorProfileGenericData);
        setAboutMe(doctorProfileGenericData);
        setExperience(doctorProfileGenericData);
        setDoctorReviews(doctorProfileGenericData);
    }

    private void setHospitals(NewDoctorProfileServerResponse newDoctorProfileServerResponse) {
        if (newDoctorProfileServerResponse.getData().getHospitals() != null && !newDoctorProfileServerResponse.getData().getHospitals().isEmpty()) {
            hospitalList = newDoctorProfileServerResponse.getData().getHospitals();
        }
    }

    private void setHeader(DoctorProfileGenericData doctorProfileGenericData) {
        if (doctorProfileGenericData.getDetails().getDocName() != null) {
            doctorNameTextView.setText(doctorProfileGenericData.getDetails().getDocName());
        }

        if (doctorProfileGenericData.getDetails().getSpeciality() != null) {
            doctorSpecialityTextView.setText(doctorProfileGenericData.getDetails().getSpeciality());
        }

        if (doctorInfo.getDocPic() != null && doctorInfo.getDocPic().length() > 0) {
            Picasso.get().load(doctorInfo.getDocPic())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(doctorPictureImageView);
        } else {
            MarhamUtils.getInstance().setBackground(this, doctorPictureImageView, R.drawable.m_doctor_placholder);
        }

        if (doctorProfileGenericData.getDetails().getDocDegree() != null) {
            doctorDegreesTextView.setText(doctorProfileGenericData.getDetails().getDocDegree());
        }

        if (doctorProfileGenericData.getDetails().getCatName() != null) {
            doctorCategoryAndExperienceTextView.setText(doctorProfileGenericData.getDetails().getCatName());
        }

        if (doctorProfileGenericData.getDetails().getDocExp() != null && !doctorProfileGenericData.getDetails().getDocExp().equals("") && !doctorProfileGenericData.getDetails().getDocExp().equals("0")) {
            doctorCategoryAndExperienceTextView.append("with over " + doctorProfileGenericData.getDetails().getDocExp() + " year(s) of experience");
        }

    }

    private void statsViewsContainer(DoctorProfileGenericData doctorProfileGenericData) {
        if (doctorProfileGenericData.getDetails().getDocExp() != null && !doctorProfileGenericData.getDetails().getDocExp().equals("") && !doctorProfileGenericData.getDetails().getDocExp().equals("0")) {
            yearsOfExperienceTextView.setText(doctorProfileGenericData.getDetails().getDocExp());
        }

        if (doctorProfileGenericData.getDetails().getSatisfactionScore() != null && !doctorProfileGenericData.getDetails().getSatisfactionScore().equals("") && !doctorProfileGenericData.getDetails().getSatisfactionScore().equals("0")) {
            doctorSatisfactionTextView.setText(doctorProfileGenericData.getDetails().getSatisfactionScore() + "%");
        }

        if (doctorProfileGenericData.getDetails().getTotalReviews() != null && !doctorProfileGenericData.getDetails().getTotalReviews().equals("") && !doctorProfileGenericData.getDetails().getTotalReviews().equals("0")) {
            reviewsTextView.setText(doctorProfileGenericData.getDetails().getTotalReviews());
        }

    }

    private void setAboutMe(DoctorProfileGenericData doctorProfileGenericData) {
        if (doctorProfileGenericData.getExtendedInfo() != null) {
            if (doctorProfileGenericData.getExtendedInfo().getAboutMe() != null && !doctorProfileGenericData.getExtendedInfo().getAboutMe().isEmpty()) {
                aboutDoctorViewsContainer.setVisibility(View.VISIBLE);
                aboutDoctorHeadingTextView.setText("About " + doctorProfileGenericData.getDetails().getDocName());
                aboutDoctorTextView.setText(doctorProfileGenericData.getExtendedInfo().getAboutMe());
            } else {
                aboutDoctorViewsContainer.setVisibility(View.GONE);
            }
        } else {
            aboutDoctorViewsContainer.setVisibility(View.GONE);
        }
    }

    private void setExperience(DoctorProfileGenericData doctorProfileGenericData) {
        if (doctorProfileGenericData.getDoctorExperiences() != null && !doctorProfileGenericData.getDoctorExperiences().isEmpty()) {
            doctorExperiencesViewsContainer.setVisibility(View.VISIBLE);
            setDoctorWorkExperienceRecyclerView(doctorProfileGenericData.getDoctorExperiences());
        } else {
            doctorExperiencesViewsContainer.setVisibility(View.GONE);
        }
    }

    private void setDoctorWorkExperienceRecyclerView(List<DoctorExperience> doctorExperienceList) {
        doctorExperiencesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        doctorExperiencesRecyclerView.setAdapter(new DoctorExperienceAdapter(this, doctorExperienceList, null));
        doctorExperiencesRecyclerView.setNestedScrollingEnabled(false);
    }

    private void setDoctorReviews(DoctorProfileGenericData doctorProfileGenericData) {
        if (doctorProfileGenericData.getReviews() != null && !doctorProfileGenericData.getReviews().isEmpty()) {
            doctorExperiencesViewsContainer.setVisibility(View.VISIBLE);
            setDoctorReviewsRecyclerView(doctorProfileGenericData.getReviews());
        } else {
            doctorExperiencesViewsContainer.setVisibility(View.GONE);
        }
    }

    private void setDoctorReviewsRecyclerView(List<Reviews> reviewsList) {
        doctorReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        doctorReviewsRecyclerView.setAdapter(new DoctorReviewsAdapter(this, reviewsList));
        doctorReviewsRecyclerView.setNestedScrollingEnabled(false);
    }

    public void setViewsBeforeGettingDoctorsDetails() {
        scrollView.setVisibility(View.GONE);
        bookVideoConsultationViewsContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsAfterGettingDoctorsDetails() {
        scrollView.setVisibility(View.VISIBLE);
        bookVideoConsultationViewsContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseNoRecordFoundWhileGettingDoctorsDetails() {
        scrollView.setVisibility(View.GONE);
        bookVideoConsultationViewsContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorsDetails() {
        scrollView.setVisibility(View.GONE);
        bookVideoConsultationViewsContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    private Hospital getVideoConsultationHospital() {
        if (hospitalList != null && hospitalList.size() > 0) {
            for (int i = 0; i < hospitalList.size(); i++) {
                if (hospitalList.get(i).getHospitalType().equals(AppConstants.HOSPITAL_TYPE.VIDEO_CONSULTATION)) {
                    return hospitalList.get(i);
                }
            }
        } else {
            Toast.makeText(this, "Could not Book Video Consultation", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void openBookVideConsultationScreen() {
        Hospital hospital = getVideoConsultationHospital();
        if (hospital != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Hospital.class.getCanonicalName(), hospital);
            bundle.putParcelable(DoctorInfo.class.getCanonicalName(), doctorInfo);
            MarhamUtils.getInstance().startActivity(this, BookVideoConsultationActivity.class, true, bundle);
        } else {
            Toast.makeText(this, "Could not Book Video Consultation", Toast.LENGTH_SHORT).show();
        }
    }

    public void getDoctorDetails(String dlID) {
        setViewsBeforeGettingDoctorsDetails();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.API.API_KEYS.DOCTOR_ID_KEY, dlID);
        hashMap.put(AppConstants.API.API_KEYS.PAGE_KEY, "0");
        hashMap.put(AppConstants.API.API_KEYS.LOGGED_IN_USER_ID_KEY, MarhamVideoCallHelper.getInstance().getUserId());
        hashMap.put(AppConstants.API.API_KEYS.DEVICE_TOKEN_KEY, MarhamVideoCallHelper.getInstance().getFireBaseToken());
        hashMap.put(AppConstants.API.API_KEYS.DEVICE_TYPE_KEY, AppConstants.API.DEVICE_TYPE.ANDROID);
        hashMap.put(AppConstants.API.API_KEYS.LANGUAGE_KEY, AppConstants.API.LANGUAGE.ENGLISH);
        hashMap.put(AppConstants.API.API_KEYS.APPLICATION_TYPE_KEY, AppConstants.API.APPLICATION_TYPE.TELENOR);

        Call<NewDoctorProfileServerResponse> call;
        APIClient apiClient = new APIClient();
        call = apiClient.getDoctorDetail(hashMap);
        retroFit2Callback = new RetroFit2Callback<>(this, this, AppConstants.API.API_END_POINT_NUMBER.GET_DOCTORS_DETAILS);
        call.enqueue(retroFit2Callback);
    }

    @Override
    public void onSuccess(ServerResponseOld response) {
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_DOCTORS_DETAILS:
                if (response.getReturn_status().equals(AppConstants.API.API_CALL_STATUS.SUCCESS_ACTION_BASED_APIS)) {
                    setViewsAfterGettingDoctorsDetails();
                    extractDoctorsData((NewDoctorProfileServerResponse) response);
                } else {
                    setViewsIncaseNoRecordFoundWhileGettingDoctorsDetails();
                    MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
                    finish();
                }
                break;
        }
    }

    @Override
    public void onFailure(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_DOCTORS_DETAILS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorsDetails();
                break;
        }
    }

    @Override
    public void onSessionExpiry(ServerResponseOld response) {
        MarhamUtils.getInstance().showAPIResponseMessage(this, response.getMessage());
        switch (response.getRequestCode()) {
            case AppConstants.API.API_END_POINT_NUMBER.GET_DOCTORS_DETAILS:
                setViewsIncaseOfInternetFailureOrUnExpectedResultWhileGettingDoctorsDetails();
                break;
        }
    }
}