package com.tillchen.fyberchallenge.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Offer implements Parcelable {

    private String pictureUrl;
    private String title;

    public Offer() {
    }

    protected Offer(Parcel in) {
        pictureUrl = in.readString();
        title = in.readString();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pictureUrl);
        dest.writeString(title);
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getTitle() {
        return title;
    }

}
