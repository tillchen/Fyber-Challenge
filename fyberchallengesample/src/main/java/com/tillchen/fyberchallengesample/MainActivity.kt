package com.tillchen.fyberchallengesample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tillchen.fyberchallenge.activities.OfferListActivity
import com.tillchen.fyberchallengesample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var appId: String = ""
    var userId: String = ""
    var securityToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonShowOffers.setOnClickListener {
            sendRequest()
        }
    }

    private fun sendRequest() {
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
        startOfferListActivityForResult(appId, userId, securityToken)
    }

    private fun startOfferListActivityForResult(appId: String, userId: String, securityToken: String) {
        val intent = Intent(this, OfferListActivity::class.java)
        intent.putExtra(OfferListActivity.APP_ID, appId)
        intent.putExtra(OfferListActivity.USER_ID, userId)
        intent.putExtra(OfferListActivity.SECURITY_TOKEN, securityToken)
        startActivity(intent)
        // TODO (Till): Change to startActivityForResult to check whether it's close button or offer item.
    }
}
