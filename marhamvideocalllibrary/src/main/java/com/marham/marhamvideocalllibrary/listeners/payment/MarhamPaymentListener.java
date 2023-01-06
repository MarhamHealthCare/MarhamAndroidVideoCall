package com.marham.marhamvideocalllibrary.listeners.payment;

import com.marham.marhamvideocalllibrary.model.appointment.Appointment;

public interface MarhamPaymentListener {
    void onPaymentRequested(Appointment appointment);
}
