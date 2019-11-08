package com.example.retrofit2.model;

import com.google.gson.annotations.SerializedName;

public class Media {

    @SerializedName("m")
    private String m;

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }
}
