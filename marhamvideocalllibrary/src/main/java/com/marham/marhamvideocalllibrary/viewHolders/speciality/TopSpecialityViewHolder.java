package com.marham.marhamvideocalllibrary.viewHolders.speciality;

import android.view.View;

import androidx.annotation.NonNull;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.adapters.speciality.BaseSpecialitiesAdapter;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

public class TopSpecialityViewHolder extends BaseSpecialityViewHolder {

    public BodyText specialityDescriptionTextView;

    public TopSpecialityViewHolder(@NonNull View view, AdapterViewItemClickedListener listener) {
        super(view, listener);
        initGui(view);
    }

    private void initGui(View view) {
        specialityDescriptionTextView = view.findViewById(R.id.speciality_description_text_view);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int viewId = view.getId();
        if (viewId == parentLayout.getId()) {
            listener.onAdatviewItemClicked(getAdapterPosition(), BaseSpecialitiesAdapter.TOP_SPECIALITIES);
        }
    }


}
