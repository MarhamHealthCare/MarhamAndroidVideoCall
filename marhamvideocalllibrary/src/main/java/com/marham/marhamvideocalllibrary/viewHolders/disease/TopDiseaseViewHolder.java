package com.marham.marhamvideocalllibrary.viewHolders.disease;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.MarhamDashboardActivity;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopDiseaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public CardView parentLayout;
    public CircleImageView topDiseaseImageView;
    public BodyText topDiseaseTextView;

    private AdapterViewItemClickedListener listener;

    public TopDiseaseViewHolder(@NonNull View view, AdapterViewItemClickedListener listener) {
        super(view);
        initGui(view);
        initVariables(listener);
        setListeners();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == parentLayout.getId()) {
            listener.onAdatviewItemClicked(getAdapterPosition(), MarhamDashboardActivity.TOP_DISEASES_RECYCLER_VIEW);
        }
    }

    private void initGui(View view) {
        parentLayout = view.findViewById(R.id.parent_layout);
        topDiseaseImageView = view.findViewById(R.id.top_disease_image_view);
        topDiseaseTextView = view.findViewById(R.id.top_disease_text_view);
    }

    private void initVariables(AdapterViewItemClickedListener listener) {
        this.listener = listener;
    }

    private void setListeners() {
        parentLayout.setOnClickListener(this);
    }


}
