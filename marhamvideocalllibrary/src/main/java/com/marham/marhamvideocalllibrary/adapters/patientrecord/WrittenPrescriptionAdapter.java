package com.marham.marhamvideocalllibrary.adapters.patientrecord;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.activities.general.ImageViewerActivity;
import com.marham.marhamvideocalllibrary.model.patientrecord.PrescriptionFiles;
import com.marham.marhamvideocalllibrary.utils.CircleTransform;
import com.marham.marhamvideocalllibrary.viewHolders.patientrecord.WrittenPrescriptionViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WrittenPrescriptionAdapter extends RecyclerView.Adapter<WrittenPrescriptionViewHolder> {

    private Context context;
    private List<PrescriptionFiles> images;
    private List<String> files = new ArrayList<>();
    private List<String> covertedStringSharedReports = new ArrayList<>();
    private int viaMainViewPrescriptionScreen;

    public static final int VIA_MAIN_PRESCRIPTION_SCREEN = 1;

    public WrittenPrescriptionAdapter(Context context, List<PrescriptionFiles> images) {
        this.context = context;
        this.images = images;
    }

    public WrittenPrescriptionAdapter(Context context, List<String> files, int viaMainViewPrescriptionScreen) {
        this.context = context;
        this.files = files;
        this.viaMainViewPrescriptionScreen = viaMainViewPrescriptionScreen;
    }

    @NonNull
    @Override
    public WrittenPrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_written_prescription, parent, false);
        return new WrittenPrescriptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WrittenPrescriptionViewHolder holder, int position) {
        if (viaMainViewPrescriptionScreen == WrittenPrescriptionAdapter.VIA_MAIN_PRESCRIPTION_SCREEN) {
            Picasso.get().load(files.get(position))
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(holder.writtenPrescription);

            holder.parentLayout.setOnClickListener(v -> {
                ArrayList<String> imageURLs = new ArrayList<>();
                for (int i = 0; i < files.size(); i++) {
                    imageURLs.add(files.get(position));
                }
                Intent intent = new Intent(context, ImageViewerActivity.class);
                intent.putStringArrayListExtra(ImageViewerActivity.IMAGE_URL, imageURLs);
                intent.putExtra(ImageViewerActivity.POSITION, position);
                context.startActivity(intent);
            });
        } else {

            Picasso.get().load(images.get(position).getFile())
                    .transform(new CircleTransform())
                    .fit()
                    .centerCrop()
                    .into(holder.writtenPrescription);
            holder.parentLayout.setOnClickListener(v -> {
                ArrayList<String> imageURLs = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    imageURLs.add(images.get(position).getFile());
                }
                Intent intent = new Intent(context, ImageViewerActivity.class);
                intent.putStringArrayListExtra(ImageViewerActivity.IMAGE_URL, imageURLs);
                intent.putExtra(ImageViewerActivity.POSITION, position);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (viaMainViewPrescriptionScreen == WrittenPrescriptionAdapter.VIA_MAIN_PRESCRIPTION_SCREEN) {
            return files.size();
        } else {
            return images.size();
        }
    }

}
