package com.tillchen.fyberchallenge.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Offer{

    private String pictureUrl;
    private String title;

    public Offer() {
    }

    public static Offer fromJson(JSONObject jsonObject) {
        Offer offer = new Offer();
        try {
            offer.pictureUrl = jsonObject.getJSONObject("thumbnail").getString("lowres");
            offer.title = jsonObject.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return offer;
    }

    public static ArrayList<Offer> fromJson(JSONArray jsonArray) {
        ArrayList<Offer> offers = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject offerJson;
            try {
                offerJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            Offer offer = Offer.fromJson(offerJson);
            if (offer != null) {
                offers.add(offer);
            }
        }
        return offers;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getTitle() {
        return title;
    }

}
