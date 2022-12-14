package com.marham.marhamvideocalllibrary.model.filter;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class DoctorListingFilter  {

    private String id;
    private String title;
    private boolean selected = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
