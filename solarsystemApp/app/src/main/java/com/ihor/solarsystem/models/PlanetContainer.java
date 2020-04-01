package com.ihor.solarsystem.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlanetContainer implements Parcelable {

    public static final Creator<PlanetContainer> CREATOR = new Creator<PlanetContainer>() {
        @Override
        public PlanetContainer createFromParcel(Parcel in) {
            return new PlanetContainer(in);
        }

        @Override
        public PlanetContainer[] newArray(int size) {
            return new PlanetContainer[size];
        }
    };
    @JsonProperty("page")
    private long mPage;
    @JsonProperty("items")
    private List<Planet> mItems;

    @SuppressWarnings("unused")
    public PlanetContainer() {
    }

    @SuppressWarnings("WeakerAccess")
    protected PlanetContainer(Parcel in) {
        mPage = in.readLong();
        mItems = in.createTypedArrayList(Planet.CREATOR);
    }

    public long getPage() {
        return mPage;
    }

    @SuppressWarnings("unused")
    public void setPage(long page) {
        mPage = page;
    }

    public List<Planet> getItems() {
        return mItems;
    }

    @SuppressWarnings("unused")
    public void setItems(List<Planet> items) {
        mItems = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mPage);
        parcel.writeTypedList(mItems);
    }
}
