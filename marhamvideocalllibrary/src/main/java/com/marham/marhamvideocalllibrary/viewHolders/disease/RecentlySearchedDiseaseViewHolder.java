package com.marham.marhamvideocalllibrary.viewHolders.disease;

import android.view.View;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.adapters.disease.BaseDiseaseAdapter;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class RecentlySearchedDiseaseViewHolder extends BaseDiseaseViewHolder {

    public RecentlySearchedDiseaseViewHolder(@NonNull View view, AdapterViewItemClickedListener listener) {
        super(view, listener);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (viewId == parentLayout.getId()) {
            listener.onAdatviewItemClicked(getAdapterPosition(), BaseDiseaseAdapter.RECENTLY_SEARCHED_DISEASES);
        }
    }


}
