package com.marham.marhamvideocalllibrary.adapters.disease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.viewHolders.disease.AllDiseaseViewHolder;
import com.marham.marhamvideocalllibrary.viewHolders.disease.BaseDiseaseViewHolder;

import java.util.List;

public class AllDiseaseAdapter extends BaseDiseaseAdapter {

    public AllDiseaseAdapter(Context context, List<Diseases> diseasesList, AdapterViewItemClickedListener listener) {
        super(context, diseasesList, listener);
    }

    @NonNull
    @Override
    public AllDiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        super.onCreateViewHolder(viewGroup,viewType);
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_all_disease, viewGroup, false);
        return new AllDiseaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(BaseDiseaseViewHolder holder, int position) {
        setDisease(holder, position);
        AllDiseaseViewHolder allDiseaseViewHolder = (AllDiseaseViewHolder) holder;
        allDiseaseViewHolder.diseaseDescriptionTextView.setText("Custom Description");

    }


}
