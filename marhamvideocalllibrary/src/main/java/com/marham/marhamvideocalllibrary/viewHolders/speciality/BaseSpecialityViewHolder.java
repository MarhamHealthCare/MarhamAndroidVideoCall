package com.marham.marhamvideocalllibrary.viewHolders.speciality;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseSpecialityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public CardView parentLayout;
    public CircleImageView specialityImageView;
    public BodyText specialityTextView;


    protected AdapterViewItemClickedListener listener;

    public BaseSpecialityViewHolder(@NonNull View view, AdapterViewItemClickedListener listener) {
        super(view);
        initGui(view);
        initVariables(listener);
        setListeners();
    }

    @Override
    public void onClick(View view) {

    }

    private void initGui(View view) {
        parentLayout = view.findViewById(R.id.parent_layout);
        specialityImageView = view.findViewById(R.id.speciality_image_view);
        specialityTextView = view.findViewById(R.id.speciality_text_view);
    }

    private void initVariables(AdapterViewItemClickedListener listener) {
        this.listener = listener;
    }

    private void setListeners() {
        parentLayout.setOnClickListener(this);
    }

}
