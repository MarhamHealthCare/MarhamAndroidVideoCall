package com.marham.marhamvideocalllibrary.adapters.speciality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.speciality.Speciality;
import com.marham.marhamvideocalllibrary.utils.CircleTransform;
import com.marham.marhamvideocalllibrary.viewHolders.speciality.TopSpecialityViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopSpecialitiesAdapter extends RecyclerView.Adapter<TopSpecialityViewHolder> {

    private Context context;
    private AdapterViewItemClickedListener listener;
    private List<Speciality> specialityList;

    private final int DEFAULT = 1;
    private final int VIEW_ALL = 2;

    public TopSpecialitiesAdapter(Context context, List<Speciality> specialityList, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.specialityList = specialityList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopSpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_top_speciality, viewGroup, false);
        return new TopSpecialityViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(TopSpecialityViewHolder holder, int position) {
        setSpeciality(holder, position);
    }

    private void setSpeciality(TopSpecialityViewHolder holder, int position) {
        if (specialityList.get(position).getNewIcon() != null && specialityList.get(position).getNewIcon().length() > 0) {
            Picasso.get().load(specialityList.get(position).getNewIcon())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(holder.topSpecialityImageView);
        } else {
            holder.topSpecialityImageView.setBackgroundResource(0);
        }
        holder.topSpecialityTextView.setText(specialityList.get(position).getSpeciality());
    }

    private void setViewAllView(TopSpecialityViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return specialityList.size();
    }


}
