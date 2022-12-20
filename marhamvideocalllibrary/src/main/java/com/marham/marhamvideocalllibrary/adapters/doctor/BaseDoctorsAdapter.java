package com.marham.marhamvideocalllibrary.adapters.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.DoctorInfo;
import com.marham.marhamvideocalllibrary.utils.CircleTransform;
import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.BaseDoctorViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BaseDoctorsAdapter extends RecyclerView.Adapter<BaseDoctorViewHolder> {

    protected Context context;
    protected AdapterViewItemClickedListener listener;
    protected List<DoctorInfo> doctorInfoList;

    public static final int DOCTOR_DASBHBOARD_RECYCLER_VIEW = 100;
    public static final int DOCTOR_LISTING_RECYCLER_VIEW = 101;


    public BaseDoctorsAdapter(Context context, List<DoctorInfo> doctorInfoList, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.doctorInfoList = doctorInfoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseDoctorViewHolder holder, int position) {
        setDoctor(holder, doctorInfoList.get(position));
    }

    private void setDoctor(BaseDoctorViewHolder holder, DoctorInfo doctorInfo) {
        setDoctorPicture(holder, doctorInfo);
        holder.doctorNameTextView.setText(doctorInfo.getDocName());
        holder.doctorSpecialityTextView.setText(doctorInfo.getSpeciality());
        holder.doctorExperienceTextView.setText("Exp." + doctorInfo.getDocExp() + " Year(s)");


        if (doctorInfo.getRating().equals("0")) {
            holder.doctorRatingsStar.setVisibility(View.INVISIBLE);
            holder.doctorReviewsTextView.setVisibility(View.INVISIBLE);
        } else {
            holder.doctorRatingsStar.setVisibility(View.VISIBLE);
            holder.doctorReviewsTextView.setVisibility(View.VISIBLE);

            if(doctorInfo.getTotalReviews()!=null) {
                int reviewsCount = Integer.parseInt(doctorInfo.getTotalReviews());
                if (reviewsCount >= 100) {
                    holder.doctorReviewsTextView.setText("100+ reviews");
                } else {
                    holder.doctorReviewsTextView.setText(doctorInfo.getTotalReviews() + " reviews");
                }
            }else{
                holder.doctorReviewsTextView.setText(doctorInfo.getTotalReviews() + " reviews");
            }

        }
    }

    private void setDoctorPicture(BaseDoctorViewHolder holder, DoctorInfo doctorInfo) {
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
                    .into(holder.doctorPictureImageView);
        } else {
            MarhamUtils.getInstance().setBackground(context, holder.doctorPictureImageView, doctorPicturePlaceHolder);

        }
    }

    @Override
    public int getItemCount() {
        return doctorInfoList.size();
    }


}
