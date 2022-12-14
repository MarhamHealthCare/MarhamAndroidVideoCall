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
import com.marham.marhamvideocalllibrary.viewHolders.disease.TopDiseaseViewHolder;

import java.util.List;

public class TopDiseaseAdapter extends BaseDiseaseAdapter {
    public TopDiseaseAdapter(Context context, List<Diseases> diseasesList, AdapterViewItemClickedListener listener) {
        super(context, diseasesList, listener);
    }

    @NonNull
    @Override
    public TopDiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_top_diseases, viewGroup, false);
        return new TopDiseaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(BaseDiseaseViewHolder holder, int position) {
        setDisease(holder, position);
    }


}
