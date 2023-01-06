package com.marham.marhamvideocalllibrary.listeners.payment;

import androidx.annotation.Keep;

import com.marham.marhamvideocalllibrary.model.appointment.Appointment;

@Keep
public interface MarhamPaymentListener {
    void onPaymentRequested(Appointment appointment);
}
