package com.eatour.hyunjongkim.weatherfood.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageInfoModel {

    private String keyword;

    private String imageUrl;

    private Integer iconIdUrl;

    public ImageInfoModel() {
    }

    public ImageInfoModel(String keyword, String imageUrl, Integer iconIdUrl) {
        this.keyword = keyword;
        this.imageUrl = imageUrl;
        this.iconIdUrl = iconIdUrl;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getIconIdUrl() {
        return iconIdUrl;
    }

    public void setIconIdUrl(Integer iconIdUrl) {
        this.iconIdUrl = iconIdUrl;
    }
}
