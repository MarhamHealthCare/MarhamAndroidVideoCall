package com.marham.marhamvideocalllibrary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.installations.Utils;
import com.marham.marhamvideocalllibrary.MarhamUtils;
import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.listeners.RuntimeAndSpecialPermissionsBottomSheetListener;
import com.marham.marhamvideocalllibrary.utils.AppConstants;

public class RuntimeAndSpecialPermissionsBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    private BodyText headingTextView;

    private MyImageView imageView1;
    private MyImageView imageView2;

    private BodyText permissionDetailTextView;

    private ConstraintLayout allowViewsContainer;
    private ConstraintLayout denyViewsContainer;

    private RuntimeAndSpecialPermissionsBottomSheetListener listener;
    private int permissionType;
    private Context context;

    public RuntimeAndSpecialPermissionsBottomSheet(Context context, RuntimeAndSpecialPermissionsBottomSheetListener listener, int permissionType) {
        this.context = context;
        this.permissionType = permissionType;
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_runtime_and_special_permissions_bottom_sheet, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (view.getId() == R.id.allow_views_container) {
            listener.onTapAllow(permissionType);
        } else if (view.getId() == R.id.deny_views_container) {
            listener.onTapDeny(permissionType);
        }
    }

    private void initializeViews(View view) {
        headingTextView = view.findViewById(R.id.heading_text_view);

        imageView1 = view.findViewById(R.id.image_view_1);
        imageView2 = view.findViewById(R.id.image_view_2);

        permissionDetailTextView = view.findViewById(R.id.permission_detail_text_view);

        allowViewsContainer = view.findViewById(R.id.allow_views_container);
        denyViewsContainer = view.findViewById(R.id.deny_views_container);

        setUpViewsAsPerPermissionType();

    }

    private void setListeners() {
        allowViewsContainer.setOnClickListener(this);
        denyViewsContainer.setOnClickListener(this);
    }

    private void setUpViewsAsPerPermissionType() {
        switch (permissionType) {
            case AppConstants.PERMISSIONS.PermissionTypes.CAMERA_AND_GALLERY_PERMISSION:
                setUpViewsForCameraAndMicrophonePermission();
                break;
        }
    }

    private void setUpViewsForCameraAndMicrophonePermission() {
        headingTextView.setText("Allow Marham to access your Camera and Microphone?");
        MarhamUtils.getInstance().setBackground(context, imageView1, R.drawable.microphone_icon_permissions_svg);
        MarhamUtils.getInstance().setBackground(context, imageView2, R.drawable.camera_icon_permissions_svg);
        imageView1.getLayoutParams().height = 80;
        imageView1.getLayoutParams().width = 50;
        imageView2.getLayoutParams().height = 80;
        imageView2.getLayoutParams().width = 100;
        permissionDetailTextView.setText("Marham will use Camera and Microphone permissions to enable you:\n\n1. To take video consultation from doctor.");
    }


}
