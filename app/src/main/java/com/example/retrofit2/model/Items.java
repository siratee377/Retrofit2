package com.example.retrofit2.model;

import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("media")
    private Media media;

    @SerializedName("link")
    private String link;

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
