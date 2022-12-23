package com.marham.marhamvideocalllibrary.listeners;

import android.view.View;

public interface AdpaterViewItemClickedListenerForPrescription {

    void onItemClicked(int position, int requestId, String data);

    void onItemClicked(View view, int position, int requestId);

}
