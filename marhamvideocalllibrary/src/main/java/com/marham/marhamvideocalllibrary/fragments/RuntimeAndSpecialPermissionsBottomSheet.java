package com.marham.marhamvideocalllibrary.fragments;

import android.content.Context;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.marham.marhamvideocalllibrary.R;
import com.marham.marhamvideocalllibrary.customviews.BodyText;
import com.marham.marhamvideocalllibrary.customviews.MyImageView;
import com.marham.marhamvideocalllibrary.listeners.RuntimeAndSpecialPermissionsBottomSheetListener;

public class RuntimeAndSpecialPermissionsBottomSheet {

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
        initVariables();
        presenter.setContext(context);
        presenter.setPermissionType(permissionType);
        presenter.setListener(listener);
    }

    private void initializeViews(View view){
        headingTextView = view.findViewById(R.id.heading_text_view);

        imageView1 = view.findViewById(R.id.image_view_1);
        imageView2 = view.findViewById(R.id.image_view_2);

        permissionDetailTextView = view.findViewById(R.id.permission_detail_text_view);

        allowViewsContainer = view.findViewById(R.id.allow_views_container);
        denyViewsContainer = view.findViewById(R.id.deny_views_container);
    }

}
