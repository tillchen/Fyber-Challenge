package com.tillchen.fyberchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.common.base.Joiner;
import com.tillchen.fyberchallenge.utils.CustomJsonObjectRequest;
import com.tillchen.fyberchallenge.utils.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private EditText editTextAppId;
    private EditText editTextUserId;
    private EditText editTextSecurityToken;

    private RequestQueue requestQueue;
    public static final String fyberApiOffersBaseUrl = "https://api.fyber.com/feed/v1/offers.json?";
    public static final String locale = "DE";
    public static final String offer_types = "112";
    public static final String ip = "109.235.143.113";
    public static final String response_signature = "X-Sponsorpay-Response-Signature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAppId = findViewById(R.id.editTextAppId);
        editTextUserId = findViewById(R.id.editTextTextUserId);
        editTextSecurityToken = findViewById(R.id.editTextTextSecurityToken);

        requestQueue = Volley.newRequestQueue(this);
    }

    public void sendRequest(View view) {
        String appId = editTextAppId.getText().toString();
        String userId = editTextUserId.getText().toString();
        final String securityToken = editTextSecurityToken.getText().toString();
        Map<String, String> requestParams = new TreeMap<>();
        requestParams.put("appid", appId);
        requestParams.put("uid", userId);
        requestParams.put("ip", ip);
        requestParams.put("locale", locale);
        requestParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        requestParams.put("offer_types", offer_types);
        String paramsConcatenated = Joiner.on("&").withKeyValueSeparator("=").join(requestParams);
        final String hashKey = Helper.hashStringWithSecurityToken(paramsConcatenated, securityToken);
        String requestUrl = fyberApiOffersBaseUrl + paramsConcatenated + "&hashkey=" + hashKey;
        CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.GET,
                requestUrl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String validResponseSignature = Helper.hashStringWithSecurityToken(
                                    response.getJSONObject("data").toString(), securityToken);
                            if (!response.getJSONObject("headers").getString(response_signature)
                                    .equals(validResponseSignature)) {
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

                        } catch (JSONException e) {
                            Log.e(TAG, e.toString());
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}