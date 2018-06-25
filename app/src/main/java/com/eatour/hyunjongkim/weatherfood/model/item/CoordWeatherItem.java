package com.eatour.hyunjongkim.weatherfood.model.item;

import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class CoordWeatherItem {

    @SerializedName("lon")
    String longitude;
    @SerializedName("lat")
    String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
