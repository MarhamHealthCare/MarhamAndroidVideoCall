package com.marham.marhamvideocalllibrary.adapters;

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
import com.marham.marhamvideocalllibrary.utils.Utils;
import com.marham.marhamvideocalllibrary.viewHolders.DashboardDoctorViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DashboardDoctorsAdapter extends RecyclerView.Adapter<DashboardDoctorViewHolder> {

    private Context context;
    private AdapterViewItemClickedListener listener;
    private List<DoctorInfo> doctorInfoList;

    public DashboardDoctorsAdapter(Context context, List<DoctorInfo> doctorInfoList, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.doctorInfoList = doctorInfoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DashboardDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_top_doctor, viewGroup, false);
        return new DashboardDoctorViewHolder(view, listener, context);
    }

    @Override
    public void onBindViewHolder(DashboardDoctorViewHolder holder, int position) {
        setDoctor(holder, doctorInfoList.get(position));
    }

    private void setDoctor(DashboardDoctorViewHolder holder, DoctorInfo doctorInfo) {
        setDoctorPicture(holder, doctorInfo);
        holder.doctorNameTextView.setText(doctorInfo.getDocName());
        holder.doctorDegreesTextView.setText(doctorInfo.getSpeciality());
        holder.doctorExperienceTextView.setText("Exp." + doctorInfo.getDocExp() + " Year(s)");


        if (doctorInfo.getRating().equals("0")) {
            holder.doctorRatingsStar.setVisibility(View.INVISIBLE);
            holder.doctorReviewsTextView.setVisibility(View.INVISIBLE);
            holder.doctorReviewsTextView.setText(doctorInfo.getTotalReviews() + " reviews(s)");

        } else {
            holder.doctorRatingsStar.setVisibility(View.VISIBLE);
            holder.doctorReviewsTextView.setVisibility(View.VISIBLE);
            holder.doctorReviewsTextView.setText(doctorInfo.getTotalReviews() + " reviews(s)");
        }


    }

    private void setDoctorPicture(DashboardDoctorViewHolder holder, DoctorInfo doctorInfo) {
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
            Utils.getInstance().setBackground(context, holder.doctorPictureImageView, doctorPicturePlaceHolder);

        }
    }

    @Override
    public int getItemCount() {
        return doctorInfoList.size();
    }


}
