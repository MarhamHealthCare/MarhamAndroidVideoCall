package com.marham.marhamvideocalllibrary.model.disease;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ProgressBar;

import androidx.annotation.Keep;

@Keep
public class Diseases implements Parcelable {

    private String disease;

    private String id;

    private String spID;

    private String image;

    private String urduName;


    protected Diseases(Parcel in) {
        disease = in.readString();
        id = in.readString();
        spID = in.readString();
        image = in.readString();
        urduName = in.readString();
    }

    public static final Creator<Diseases> CREATOR = new Creator<Diseases>() {
        @Override
        public Diseases createFromParcel(Parcel in) {
            return new Diseases(in);
        }

        @Override
        public Diseases[] newArray(int size) {
            return new Diseases[size];
        }
    };

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

    public String getUrduName() {
        return urduName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(disease);
        dest.writeString(id);
        dest.writeString(spID);
        dest.writeString(image);
        dest.writeString(urduName);
    }
}
