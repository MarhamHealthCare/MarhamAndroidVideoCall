package com.marham.marhamvideocalllibrary.adapters.appointments;

import android.content.Context;
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

import java.text.MessageFormat;
import java.util.List;

public class BaseAllVideoConsultationsAdapter extends RecyclerView.Adapter<BaseAllVideoConsultationsViewHolder> {

    public static final int PREVIOUS_APPOINTMENTS = 300;
    public static final int UPCOMING_APPOINTMENTS = 301;

    protected Context context;
    protected AdapterViewItemClickedListener adpaterViewItemClickedListener;
    protected List<Appointment> appointmentList;
    protected int adapterType;
    protected Appointment appointment;

    public BaseAllVideoConsultationsAdapter(Context context, AdapterViewItemClickedListener adapterViewItemClickedListener2, List<Appointment> appointmentList, int adapterType) {
        this.context = context;
        this.adpaterViewItemClickedListener = adapterViewItemClickedListener2;
        this.appointmentList = appointmentList;
        this.adapterType = adapterType;
    }

    @NonNull
    @Override
    public BaseAllVideoConsultationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAllVideoConsultationsViewHolder holder, int position) {
        appointment = appointmentList.get(position);

        setDoctorPicture(holder, appointmentList.get(position));
        holder.doctorNameTextView.setText(appointment.getDocName());
        holder.doctorSpecialityTextView.setText(appointment.getSpeciality());
        holder.patientNameTextView.setText(MessageFormat.format("Patient: {0}", appointment.getPatientName()));
        holder.dateTextView.setText(appointment.getFormattedDate());
        holder.timeTextView.setText(appointment.getFormattedTime());
        holder.appointmentStatusTextView.setText(appointment.getAppointmentSubStatusTitle());

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    private void setDoctorPicture(BaseAllVideoConsultationsViewHolder baseAllVideoConsultationsViewHolder, Appointment appointment) {
        int doctorPicturePlaceHolder;
        if (appointment.getGender() != null && appointment.getGender().equals("0")) {
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
