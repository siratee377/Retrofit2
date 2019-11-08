package com.example.retrofit2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Feeds {

    @SerializedName("items")
    private List<Items> items;

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
