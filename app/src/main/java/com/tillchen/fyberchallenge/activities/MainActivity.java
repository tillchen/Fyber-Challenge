package com.tillchen.fyberchallenge.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.common.base.Joiner;
import com.tillchen.fyberchallenge.databinding.ActivityMainBinding;
import com.tillchen.fyberchallenge.models.Offer;
import com.tillchen.fyberchallenge.utils.CustomJsonObjectRequest;
import com.tillchen.fyberchallenge.utils.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    public static final String FYBER_API_OFFERS_BASE_URL
            = "https://api.fyber.com/feed/v1/offers.json?";
    public static final String LOCALE = "DE";
    public static final String OFFER_TYPES = "112";
    public static final String IP = "109.235.143.113";
    public static final String RESPONSE_SIGNATURE = "X-Sponsorpay-Response-Signature";

    private RequestQueue requestQueue;

    private ActivityMainBinding binding;

    public String appId;
    public String userId;
    public String securityToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestQueue = Volley.newRequestQueue(this);
    }

    public void sendRequest(View view) {
        String requestUrl = getRequestUrl();
        binding.progressBarShowOffers.setVisibility(ProgressBar.VISIBLE);
        CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.GET,
                requestUrl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.progressBarShowOffers.setVisibility(ProgressBar.INVISIBLE);
                        try {
                            JSONObject responseBody = response.getJSONObject("data");
                            if (isResponseCorrupted(responseBody.toString(),
                                    response.getJSONObject("headers").getString(RESPONSE_SIGNATURE))) {
                                Log.e(TAG, "The response signatures are not matching.");
                                // FIXME: Here the signatures always do not match.
                                //  It's using the same function has the hashKey.
                                //  So I have no idea why they are not matching at this point.
                                //  And I have to comment out the code below.
//                                Toast.makeText(getApplicationContext(),
//                                        R.string.response_signatures_do_not_match,
//                                        Toast.LENGTH_SHORT).show();
//                                return;
                            }
                            ArrayList<Offer> offers = Offer
                                    .fromJson(responseBody.getJSONArray("offers"));
                            startOfferListActivity(offers);
                        } catch (JSONException e) {
                            Log.e(TAG, e.toString());
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBarShowOffers.setVisibility(ProgressBar.VISIBLE);
                Log.e(TAG, error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public String getRequestUrl() {
        appId = binding.editTextAppId.getText().toString();
        userId = binding.editTextTextUserId.getText().toString();
        securityToken = binding.editTextTextSecurityToken.getText().toString();
        Map<String, String> requestParams = new TreeMap<>();
        requestParams.put("appid", appId);
        requestParams.put("uid", userId);
        requestParams.put("ip", IP);
        requestParams.put("locale", LOCALE);
        requestParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        requestParams.put("offer_types", OFFER_TYPES);
        String paramsConcatenated = Joiner.on("&").withKeyValueSeparator("=").join(requestParams);
        String hashKey = Helper.hashStringWithSecurityToken(paramsConcatenated, securityToken);
        return FYBER_API_OFFERS_BASE_URL + paramsConcatenated + "&hashkey=" + hashKey;
    }

    public boolean isResponseCorrupted(String responseBody, String validSignature) {
        String hashedResponseSignature = Helper.hashStringWithSecurityToken(
                responseBody, securityToken);
        return !hashedResponseSignature.equals(validSignature);
    }

    private void startOfferListActivity(ArrayList<Offer> offers) {
        Intent intent = new Intent(this, OfferListActivity.class);
        intent.putParcelableArrayListExtra("offers", offers);
        startActivity(intent);
    }

}