package com.ihor.solarsystem.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ihor.solarsystem.R;
import com.ihor.solarsystem.SolarSystemApplication;

public class Planet implements Parcelable {

    @SuppressWarnings("WeakerAccess")
    public static final Creator<Planet> CREATOR = new Creator<Planet>() {
        @Override
        public Planet createFromParcel(Parcel in) {
            return new Planet(in);
        }

        @Override
        public Planet[] newArray(int size) {
            return new Planet[size];
        }
    };

    @JsonProperty("name")
    private String mName;

    @JsonProperty("image_url")
    private String mImageUrl;

    @JsonProperty("description")
    private String mDescription;

    @SuppressWarnings("unused")
    public Planet() {
    }

    @SuppressWarnings("WeakerAccess")
    protected Planet(Parcel in) {
        mName = in.readString();
        mImageUrl = in.readString();
        mDescription = in.readString();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNameText() {
        return getTextOrError(mName);
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    @SuppressWarnings("unused")
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return mDescription;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescriptionText() {
        return getTextOrError(mDescription);
    }

    private String getTextOrError(String parameter) {
        return parameter == null
                ? SolarSystemApplication.getInstance().getString(R.string.error)
                : parameter;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mImageUrl);
        parcel.writeString(mDescription);
    }
}
