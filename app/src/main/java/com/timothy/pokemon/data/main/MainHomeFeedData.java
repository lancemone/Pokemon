package com.timothy.pokemon.data.main;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.timothy.common.data.base.BaseData;

public class MainHomeFeedData extends BaseData implements Parcelable {

    public String id;

    public String imageUrl;

    public MainHomeFeedData(){}

    protected MainHomeFeedData(Parcel in) {
        id = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<MainHomeFeedData> CREATOR = new Creator<MainHomeFeedData>() {
        @Override
        public MainHomeFeedData createFromParcel(Parcel in) {
            return new MainHomeFeedData(in);
        }

        @Override
        public MainHomeFeedData[] newArray(int size) {
            return new MainHomeFeedData[size];
        }
    };

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imageUrl);
    }
}
