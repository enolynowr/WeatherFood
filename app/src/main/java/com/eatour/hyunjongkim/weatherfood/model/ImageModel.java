package com.eatour.hyunjongkim.weatherfood.model;

import com.eatour.hyunjongkim.weatherfood.model.item.ItemsImageItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@org.parceler.Parcel
public class ImageModel {

    @SerializedName("items")
    List<ItemsImageItem> itemsImageItem;

    public List<ItemsImageItem> getItemsImageItem() {
        return itemsImageItem;
    }

    public void setItemsImageItem(List<ItemsImageItem> itemsImageItem) {
        this.itemsImageItem = itemsImageItem;
    }
}
