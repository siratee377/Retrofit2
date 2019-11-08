package com.example.retrofit2.model;

public class Item {

    String link;
    String media;

    public Item(String link, String media) {
        this.link = link;
        this.media = media;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getLink() {
        return link;
    }

    public String getMedia() {
        return media;
    }
}
