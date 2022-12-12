package com.marham.marhamvideocalllibrary.adapters.disease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.viewHolders.disease.BaseDiseaseViewHolder;
import com.marham.marhamvideocalllibrary.viewHolders.disease.RecentlySearchedDiseaseViewHolder;

import java.util.List;

public class RecentlySearchedDiseaseAdapter extends BaseDiseaseAdapter {
    public RecentlySearchedDiseaseAdapter(Context context, List<Diseases> diseasesList, AdapterViewItemClickedListener listener) {
        super(context, diseasesList, listener);
    }

    @NonNull
    @Override
    public RecentlySearchedDiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_recent_diseases, viewGroup, false);
        return new RecentlySearchedDiseaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(BaseDiseaseViewHolder holder, int position) {
        setDisease(holder, position);
    }


}
