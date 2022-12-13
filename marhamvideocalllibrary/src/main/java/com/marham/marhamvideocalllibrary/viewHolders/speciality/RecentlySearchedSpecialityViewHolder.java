package com.marham.marhamvideocalllibrary.viewHolders.speciality;

import android.view.View;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.adapters.speciality.BaseSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class RecentlySearchedSpecialityViewHolder extends BaseSpecialityViewHolder{

    public RecentlySearchedSpecialityViewHolder(@NonNull View view, AdapterViewItemClickedListener listener) {
        super(view, listener);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (viewId == parentLayout.getId()) {
            listener.onAdatviewItemClicked(getAdapterPosition(), BaseSpecialitiesAdapter.RECENTLY_SEARCHED_SPECIALITIES);
        }
    }

}
