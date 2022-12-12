package com.marham.marhamvideocalllibrary.viewHolders.disease;

import android.view.View;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.adapters.disease.BaseDiseaseAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class AllDiseaseViewHolder extends BaseDiseaseViewHolder {

    public BodyText diseaseDescriptionTextView;

    public AllDiseaseViewHolder(@NonNull View view, AdapterViewItemClickedListener listener) {
        super(view, listener);
        initGui(view);
    }

    private void initGui(View view) {
        diseaseDescriptionTextView = view.findViewById(R.id.disease_description_text_view);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (viewId == parentLayout.getId()) {
            listener.onAdatviewItemClicked(getAdapterPosition(), BaseDiseaseAdapter.ALL_DISEASES);
        }
    }

}
