package com.marham.marhamvideocalllibrary.adapters.speciality;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.listeners.AdapterViewItemClickedListener;
import com.marham.marhamvideocalllibrary.model.disease.Diseases;
import com.marham.marhamvideocalllibrary.model.speciality.Speciality;
import com.marham.marhamvideocalllibrary.utils.CircleTransform;
import com.marham.marhamvideocalllibrary.viewHolders.speciality.BaseSpecialityViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BaseSpecialitiesAdapter extends RecyclerView.Adapter<BaseSpecialityViewHolder> implements Filterable {

    protected Context context;
    protected AdapterViewItemClickedListener listener;
    protected List<Speciality> specialityList;
    protected List<Speciality> filteredSpecialityList;

    public static final int TOP_SPECIALITIES = 200;
    public static final int RECENTLY_SEARCHED_SPECIALITIES = 201;
    public static final int ALL_SPECIALITIES = 202;

    private boolean showAll = true;

    public BaseSpecialitiesAdapter(Context context, List<Speciality> specialityList, AdapterViewItemClickedListener listener) {
        this.context = context;
        this.specialityList = specialityList;
        this.filteredSpecialityList = specialityList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseSpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_row_top_speciality, viewGroup, false);
        return new BaseSpecialityViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(BaseSpecialityViewHolder holder, int position) {
        setSpeciality(holder, position);
    }

    private void setSpeciality(BaseSpecialityViewHolder holder, int position) {
        if (filteredSpecialityList.get(position).getNewIcon() != null && filteredSpecialityList.get(position).getNewIcon().length() > 0) {
            Picasso.get().load(filteredSpecialityList.get(position).getNewIcon())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(holder.specialityImageView);
        } else {
            holder.specialityImageView.setBackgroundResource(0);
        }
        holder.specialityTextView.setText(filteredSpecialityList.get(position).getSpeciality());
    }

    @Override
    public int getItemCount() {
        if(showAll) {
            return filteredSpecialityList.size();
        }else {
            if(filteredSpecialityList.size()>=8){
                return 8;
            }else{
                return filteredSpecialityList.size();
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredSpecialityList = specialityList;
                } else {
                    List<Speciality> filteredList = new ArrayList<>();

                    for (Speciality speciality : specialityList) {

                        if (speciality.getSpeciality().toLowerCase().contains(charString)) {
                            filteredList.add(speciality);
                        }
                    }

                    filteredSpecialityList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredSpecialityList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredSpecialityList = (List<Speciality>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }
}
