package com.tillchen.fyberchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.base.Joiner;
import com.google.common.hash.Hashing;

import org.json.JSONObject;

import java.nio.charset.Charset;
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
        String securityToken = editTextSecurityToken.getText().toString();
        Map<String, String> requestParams = new TreeMap<>();
        requestParams.put("appid", appId);
        requestParams.put("uid", userId);
        requestParams.put("ip", ip);
        requestParams.put("locale", locale);
        requestParams.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000L));
        requestParams.put("offer_types", offer_types);
        String paramsConcatenated = Joiner.on("&").withKeyValueSeparator("=").join(requestParams);
        Log.i(TAG, paramsConcatenated);
        String hashKey = Hashing.sha1().hashString(paramsConcatenated + "&" + securityToken,
                Charset.defaultCharset()).toString().toLowerCase();
        String requestUrl = fyberApiOffersBaseUrl + paramsConcatenated + "&hashkey=" + hashKey;
        Log.i(TAG, requestUrl);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                requestUrl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
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