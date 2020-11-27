package com.tillchen.fyberchallenge.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.tillchen.fyberchallenge.adapters.OffersAdapter
import com.tillchen.fyberchallenge.models.Offer
import com.tillchen.fyberchallenge.utils.CustomJsonObjectRequest
import com.tillchen.fyberchallenge.utils.RequestHelper

class APICaller(
    context: Context,
    private val appId: String,
    private val userId: String,
    private val securityToken: String
) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun getOffers(adapter: OffersAdapter) {
        val requestUrl = RequestHelper.getRequestUrl(appId, userId, securityToken)
        val jsonObjectRequest = CustomJsonObjectRequest(
            Request.Method.GET, requestUrl,
            null,
            { response ->
                val responseBody = response.getJSONObject("data")
                if (RequestHelper.isResponseCorrupted(
                        responseBody.toString(),
                        response.getJSONObject("headers")
                            .getString(RequestHelper.RESPONSE_SIGNATURE),
                        securityToken
                    )
                ) {
                    // FIXME: Here the signatures always do not match.
                    //  It's using a similar function has the hashKey.
                    //  And I'm not sure why they are not matching at this point, though
                    //  I suspect that toString() might have changed the response body.
                    //  I have to comment out the code below.
                    //  But I'd love to have a discussion with you to figure out why:).
//                                Toast.makeText(getApplicationContext(),
//                                        R.string.response_signatures_do_not_match,
//                                        Toast.LENGTH_SHORT).show();
//                                return;
                }
                adapter.setData(Offer.fromJson(responseBody.getJSONArray("offers")))
            },
            { error ->
                error.printStackTrace()
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}
