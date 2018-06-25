package com.eatour.hyunjongkim.weatherfood.model;

import com.eatour.hyunjongkim.weatherfood.model.item.CoordWeatherItem;
import com.eatour.hyunjongkim.weatherfood.model.item.WeatherWeatherItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@org.parceler.Parcel
public class WeatherModel {

    @SerializedName("coord")
    CoordWeatherItem coordWeatherItem;
    @SerializedName("weather")
    List<WeatherWeatherItem> weatherWeatherItem;

    public CoordWeatherItem getCoordWeatherItem() {
        return coordWeatherItem;
    }

    public void setCoordWeatherItem(CoordWeatherItem coordWeatherItem) {
        this.coordWeatherItem = coordWeatherItem;
    }

    public List<WeatherWeatherItem> getWeatherWeatherItem() {
        return weatherWeatherItem;
    }

    public void setWeatherWeatherItem(List<WeatherWeatherItem> weatherWeatherItem) {
        this.weatherWeatherItem = weatherWeatherItem;
    }
}
