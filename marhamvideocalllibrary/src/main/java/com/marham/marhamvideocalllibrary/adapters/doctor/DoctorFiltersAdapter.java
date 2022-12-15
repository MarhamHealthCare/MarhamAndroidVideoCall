package com.marham.marhamvideocalllibrary.adapters.doctor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.filter.DoctorListingFilter;
import com.marham.marhamvideocalllibrary.viewHolders.doctor.DoctorListingFilterViewHolder;

import java.util.List;

public class DoctorFiltersAdapter extends RecyclerView.Adapter<DoctorListingFilterViewHolder> {

    private Context context;
    private AdapterViewItemClickedListener listener;
    private List<DoctorListingFilter> doctorListingFilterList;

    public DoctorFiltersAdapter(Context context, List<DoctorListingFilter> doctorListingFilterList, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.doctorListingFilterList = doctorListingFilterList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoctorListingFilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_doctor_listing_filter, viewGroup, false);
        return new DoctorListingFilterViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorListingFilterViewHolder holder, int position) {
        setFilter(holder, doctorListingFilterList.get(position));
    }

    private void setFilter(DoctorListingFilterViewHolder holder, DoctorListingFilter doctorListingFilter) {
        if(doctorListingFilter.isSelected()){
            MarhamUtils.getInstance().setBackground(context,holder.parentLayout,R.drawable.rounded_corners_color_primary_5_dp);
            holder.filterTextView.setTextColor(context.getResources().getColor(R.color.white));
            holder.filterTextView.setTypeface(null, Typeface.BOLD);
        }else{
            MarhamUtils.getInstance().setBackground(context,holder.parentLayout,R.drawable.rounded_corners_grey_5_dp);
            holder.filterTextView.setTextColor(context.getResources().getColor(R.color.black));
            holder.filterTextView.setTypeface(null, Typeface.NORMAL);
        }
        holder.filterTextView.setText(doctorListingFilter.getTitle());
    }

    @Override
    public int getItemCount() {
        return doctorListingFilterList.size();
    }


}
