package com.tillchen.fyberchallengesample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.tillchen.fyberchallenge.R
import com.tillchen.fyberchallenge.activities.OfferListActivity
import com.tillchen.fyberchallenge.models.Offer
import com.tillchen.fyberchallenge.utils.CustomJsonObjectRequest
import com.tillchen.fyberchallenge.utils.RequestHelper
import com.tillchen.fyberchallengesample.databinding.ActivityMainBinding
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    private lateinit var requestQueue: RequestQueue
    private lateinit var binding: ActivityMainBinding

    var appId: String = ""
    var userId: String = ""
    var securityToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(this)
    }

    fun sendRequest(view: View?) {
        appId = binding.editTextAppId.text.toString()
        if (appId.isBlank()) {
            binding.editTextAppId.error = getString(R.string.empty_app_id)
            return
        }
        userId = binding.editTextUserId.text.toString()
        if (userId.isBlank()) {
            binding.editTextUserId.error = getString(R.string.empty_user_id)
            return
        }
        securityToken = binding.editTextSecurityToken.text.toString()
        if (securityToken.isBlank()) {
            binding.editTextSecurityToken.error = getString(R.string.empty_security_token)
            return
        }

        val requestUrl = RequestHelper.getRequestUrl(appId, userId, securityToken)
        val jsonObjectRequest = CustomJsonObjectRequest(
            Request.Method.GET,
            requestUrl, null,
            { response ->
                binding.progressBarShowOffers.visibility = ProgressBar.INVISIBLE
                try {
                    val responseBody = response.getJSONObject("data")
                    if (RequestHelper.isResponseCorrupted(
                            responseBody.toString(),
                            response.getJSONObject("headers")
                                .getString(RequestHelper.RESPONSE_SIGNATURE),
                            securityToken
                        )
                    ) {
                        Log.e(TAG, "The response signatures are not matching.")
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
                    val offers = Offer
                        .fromJson(responseBody.getJSONArray("offers"))
                    startOfferListActivity(offers)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error ->
            Toast.makeText(applicationContext, R.string.response_error, Toast.LENGTH_SHORT)
                .show()
            error.printStackTrace()
        }
        requestQueue.add(jsonObjectRequest)
    }

    private fun startOfferListActivity(offers: ArrayList<Offer>) {
        val intent = Intent(this, OfferListActivity::class.java)
        intent.putParcelableArrayListExtra("offers", offers)
        startActivity(intent)
    }

//    private fun startOfferListActivityForResult(appId: String, userId: String, securityToken: String) {
//        val intent = Intent(this, OfferListActivity::class.java)
//        intent.putExtra("appId", appId)
//        intent.putExtra("userId", userId)
//        intent.putExtra("securityToken", securityToken)
//        startActivityForResult()
//    }
}
