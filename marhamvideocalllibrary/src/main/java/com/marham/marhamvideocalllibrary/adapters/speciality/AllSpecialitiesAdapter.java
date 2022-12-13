package com.marham.marhamvideocalllibrary.adapters.speciality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.speciality.Speciality;
import com.marham.marhamvideocalllibrary.viewHolders.speciality.BaseSpecialityViewHolder;
import com.marham.marhamvideocalllibrary.viewHolders.speciality.RecentlySearchedSpecialityViewHolder;

import java.util.List;

public class AllSpecialitiesAdapter  extends  BaseSpecialitiesAdapter{

    public AllSpecialitiesAdapter(Context context, List<Speciality> specialityList, AdapterViewItemClickedListener listener) {
        super(context, specialityList, listener);
    }

    @NonNull
    @Override
    public RecentlySearchedSpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_top_speciality, viewGroup, false);
        return new RecentlySearchedSpecialityViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(BaseSpecialityViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

}
