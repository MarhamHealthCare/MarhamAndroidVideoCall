package com.marham.marhamvideocalllibrary.model.disease;

import androidx.annotation.Keep;

@Keep
public class Diseases {

    private String disease;

    private String id;

    private String spID;

    private String image;

    private String urduName;
//    private String newIcon;


    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpID() {
        return spID;
    }

    public void setSpID(String spID) {
        this.spID = spID;
    }

    @Override
    public String toString() {
        return "ClassPojo [disease = " + disease + ", id = " + id + ", spID = " + spID + "]";
    }

    public String getUrduName() {
        return urduName;
    }

}
