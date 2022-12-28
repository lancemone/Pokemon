package com.timothy.pokemon.data.tradition;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.timothy.common.data.base.BaseData;
import com.timothy.common.data.base.DataType;

public class TraditionColorData extends BaseData implements Parcelable {

    public String id;
    public String name;
    public String colorHex;

    public TraditionColorData(){}

    protected TraditionColorData(Parcel in) {
        id = in.readString();
        name = in.readString();
        colorHex = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(colorHex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TraditionColorData> CREATOR = new Creator<TraditionColorData>() {
        @Override
        public TraditionColorData createFromParcel(Parcel in) {
            return new TraditionColorData(in);
        }

        @Override
        public TraditionColorData[] newArray(int size) {
            return new TraditionColorData[size];
        }
    };

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getType() {
        return DataType.TRADITION_COLOR.getType();
    }

    public int getColorValue(){
        if (TextUtils.isEmpty(colorHex)){
            return Color.BLACK;
        }
        return Color.parseColor(colorHex);
    }
}
