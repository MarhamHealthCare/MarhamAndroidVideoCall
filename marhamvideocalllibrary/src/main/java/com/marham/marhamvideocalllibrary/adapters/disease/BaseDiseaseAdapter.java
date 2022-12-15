package com.marham.marhamvideocalllibrary.adapters.disease;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.utils.CircleTransform;
import com.marham.marhamvideocalllibrary.viewHolders.disease.BaseDiseaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BaseDiseaseAdapter extends RecyclerView.Adapter<BaseDiseaseViewHolder> implements Filterable {

    protected final Context context;
    protected final AdapterViewItemClickedListener listener;
    private final List<Diseases> diseasesList;
    private List<Diseases> filteredDiseaseList;

    public static final int TOP_DISEASES = 100;
    public static final int RECENTLY_SEARCHED_DISEASES = 101;
    public static final int ALL_DISEASES = 102;

    public BaseDiseaseAdapter(Context context, List<Diseases> diseasesList, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.diseasesList = diseasesList;
        this.filteredDiseaseList = diseasesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseDiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseDiseaseViewHolder holder, int position) {
        setDisease(holder, position);
    }

    protected void setDisease(BaseDiseaseViewHolder holder, int position) {
        holder.diseaseTextView.setText(filteredDiseaseList.get(position).getDisease());

        if (filteredDiseaseList.get(position).getImage() != null && filteredDiseaseList.get(position).getImage().length() > 0) {
            Picasso.get().load(filteredDiseaseList.get(position).getImage())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(holder.diseaseImageView);
        } else {
            holder.diseaseImageView.setBackgroundResource(0);
        }

    }


    @Override
    public int getItemCount() {
        return filteredDiseaseList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredDiseaseList = diseasesList;
                } else {
                    List<Diseases> filteredList = new ArrayList<>();

                    for (Diseases diseases : diseasesList) {

                        if (diseases.getDisease().toLowerCase().contains(charString)) {

                            filteredList.add(diseases);
                        }
                    }

                    filteredDiseaseList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredDiseaseList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredDiseaseList = (List<Diseases>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
