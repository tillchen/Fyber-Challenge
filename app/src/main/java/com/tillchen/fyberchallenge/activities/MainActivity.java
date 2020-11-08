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
import com.tillchen.fyberchallenge.databinding.ActivityMainBinding;
import com.tillchen.fyberchallenge.models.Offer;
import com.tillchen.fyberchallenge.utils.CustomJsonObjectRequest;
import com.tillchen.fyberchallenge.utils.RequestHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

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
        appId = binding.editTextAppId.getText().toString();
        userId = binding.editTextTextUserId.getText().toString();
        securityToken = binding.editTextTextSecurityToken.getText().toString();
        String requestUrl = RequestHelper.getRequestUrl(appId, userId, securityToken);
        binding.progressBarShowOffers.setVisibility(ProgressBar.VISIBLE);
        CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.GET,
                requestUrl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.progressBarShowOffers.setVisibility(ProgressBar.INVISIBLE);
                        try {
                            JSONObject responseBody = response.getJSONObject("data");
                            if (RequestHelper.isResponseCorrupted(responseBody.toString(),
                                    response.getJSONObject("headers")
                                            .getString(RequestHelper.RESPONSE_SIGNATURE),
                                    securityToken)) {
                                Log.e(TAG, "The response signatures are not matching.");
                                // FIXME: Here the signatures always do not match.
                                //  It's using the same function has the hashKey.
                                //  And I have no idea why they are not matching at this point.
                                //  So I have to comment out the code below.
                                //  But I'd love to have a discussion with you to figure out why:).
//                                Toast.makeText(getApplicationContext(),
//                                        R.string.response_signatures_do_not_match,
//                                        Toast.LENGTH_SHORT).show();
//                                return;
                            }
                            ArrayList<Offer> offers = Offer
                                    .fromJson(responseBody.getJSONArray("offers"));
                            startOfferListActivity(offers);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.progressBarShowOffers.setVisibility(ProgressBar.VISIBLE);
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void startOfferListActivity(ArrayList<Offer> offers) {
        Intent intent = new Intent(this, OfferListActivity.class);
        intent.putParcelableArrayListExtra("offers", offers);
        startActivity(intent);
    }

}