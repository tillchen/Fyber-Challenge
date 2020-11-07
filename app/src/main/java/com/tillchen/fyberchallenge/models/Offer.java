package com.tillchen.fyberchallenge.models;

public class Offer {
    private String pictureUrl;
    private String title;

    public Offer(String pictureUrl, String title) {
        this.pictureUrl = pictureUrl;
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
