package com.eatour.hyunjongkim.weatherfood.model;

// 位置情報の保存
public class GeoModel {
    public static double knownLatitude;
    public static double knownLongitude;

    public static double getKnownLatitude() {
        return knownLatitude;
    }

    public static void setKnownLatitude(double knownLatitude) {
        GeoModel.knownLatitude = knownLatitude;
    }

    public static double getKnownLongitude() {
        return knownLongitude;
    }

    public static void setKnownLongitude(double knownLongitude) {
        GeoModel.knownLongitude = knownLongitude;
    }
}