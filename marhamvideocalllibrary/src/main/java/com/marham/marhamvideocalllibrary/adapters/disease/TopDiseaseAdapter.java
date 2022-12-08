package com.marham.marhamvideocalllibrary.adapters.disease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.utils.CircleTransform;
import com.marham.marhamvideocalllibrary.viewHolders.disease.TopDiseaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopDiseaseAdapter extends RecyclerView.Adapter<TopDiseaseViewHolder> {

    private Context context;
    private AdapterViewItemClickedListener listener;
    private List<Diseases> diseasesList;

    private final int DEFAULT = 1;
    private final int VIEW_ALL = 2;

    public TopDiseaseAdapter(Context context, List<Diseases> diseasesList, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.diseasesList = diseasesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopDiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_top_diseases, viewGroup, false);
        return new TopDiseaseViewHolder(view, listener);

    }

    @Override
    public void onBindViewHolder(TopDiseaseViewHolder holder, int position) {
        setDisease(holder, position);
    }

    private void setDisease(TopDiseaseViewHolder holder, int position) {
        if (diseasesList.get(position).getImage() != null && diseasesList.get(position).getImage().length() > 0) {
            Picasso.get().load(diseasesList.get(position).getImage())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(holder.topDiseaseImageView);
        } else {
            holder.topDiseaseImageView.setBackgroundResource(0);
        }
        holder.topDiseaseTextView.setText(diseasesList.get(position).getDisease());

    }

    private void setViewAllView(TopDiseaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return diseasesList.size();
    }


}
