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
import com.marham.marhamvideocalllibrary.viewHolders.speciality.BaseSpecialityViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BaseSpecialitiesAdapter extends RecyclerView.Adapter<BaseSpecialityViewHolder> {

    protected Context context;
    protected AdapterViewItemClickedListener listener;
    protected List<Speciality> specialityList;

    public static final int TOP_SPECIALITIES = 1;
    public static final int RECENTLY_SEARCHED_SPECIALITIES = 2;
    public static final int ALL_SPECIALITIES = 3;

    public BaseSpecialitiesAdapter(Context context, List<Speciality> specialityList, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.specialityList = specialityList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseSpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_top_speciality, viewGroup, false);
        return new BaseSpecialityViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(BaseSpecialityViewHolder holder, int position) {
        setSpeciality(holder, position);
    }

    private void setSpeciality(BaseSpecialityViewHolder holder, int position) {
        if (specialityList.get(position).getNewIcon() != null && specialityList.get(position).getNewIcon().length() > 0) {
            Picasso.get().load(specialityList.get(position).getNewIcon())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(holder.specialityImageView);
        } else {
            holder.specialityImageView.setBackgroundResource(0);
        }
        holder.specialityTextView.setText(specialityList.get(position).getSpeciality());
    }

    @Override
    public int getItemCount() {
        return specialityList.size();
    }


}
