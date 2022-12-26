package com.marham.marhamvideocalllibrary.adapters.appointments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.appointment.Appointment;
import com.marham.marhamvideocalllibrary.utils.CircleTransform;
import com.marham.marhamvideocalllibrary.viewHolders.appointments.BaseAllVideoConsultationsViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BaseAllVideoConsultationsAdapter extends RecyclerView.Adapter<BaseAllVideoConsultationsViewHolder> {

    protected Context context;
    protected AdapterViewItemClickedListener adpaterViewItemClickedListener;
    protected List<Appointment> appointmentList;

    public BaseAllVideoConsultationsAdapter(Context context, AdapterViewItemClickedListener adapterViewItemClickedListener, List<Appointment> appointmentList) {
        this.context = context;
        this.adpaterViewItemClickedListener = adpaterViewItemClickedListener;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public BaseAllVideoConsultationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAllVideoConsultationsViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);

        setDoctorPicture(holder, appointmentList.get(position));
        holder.doctorNameTextView.setText(appointment.getDocName());
        holder.doctorSpecialityTextView.setText(appointment.getSpeciality());
        holder.doctorDegreeTextView.setText(appointment.getDocDegree());
        holder.patientNameTextView.setText(appointment.getPatientName());
        holder.dateTextView.setText(appointment.getFormattedDate());
        holder.timeTextView.setText(appointment.getFormattedTime());
        holder.appointmentStatusTextView.setText(appointment.getAppointmentSubStatusText());

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    private void setDoctorPicture(BaseAllVideoConsultationsViewHolder baseAllVideoConsultationsViewHolder, Appointment appointment) {
        int doctorPicturePlaceHolder;
        if (appointment.getGender().equals("0")) {
            doctorPicturePlaceHolder = R.drawable.f_doctor_placeholder;
        } else {
            doctorPicturePlaceHolder = R.drawable.m_doctor_placholder;
        }

        if (appointment.getDoctorImageUrl() != null && appointment.getDoctorImageUrl().length() > 0) {
            Picasso.get().load(appointment.getDoctorImageUrl())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(baseAllVideoConsultationsViewHolder.doctorPictureImageView);
        } else {
            MarhamUtils.getInstance().setBackground(context, baseAllVideoConsultationsViewHolder.doctorPictureImageView, doctorPicturePlaceHolder);

        }
    }


}
