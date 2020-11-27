package com.tillchen.fyberchallenge.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tillchen.fyberchallenge.adapters.OffersAdapter
import com.tillchen.fyberchallenge.databinding.ActivityOfferListBinding
import com.tillchen.fyberchallenge.network.APICaller

class OfferListActivity : AppCompatActivity() {

    companion object {
        const val APP_ID: String = "appId"
        const val USER_ID: String = "userId"
        const val SECURITY_TOKEN: String = "securityToken"
    }

    private lateinit var binding: ActivityOfferListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfferListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appId = intent.getStringExtra(APP_ID) ?: ""
        val userId = intent.getStringExtra(USER_ID) ?: ""
        val securityToken = intent.getStringExtra(SECURITY_TOKEN) ?: ""
        val adapter = OffersAdapter()
        binding.recyclerViewOffers.adapter = adapter
        APICaller(this, appId, userId, securityToken).getOffers(adapter)
        binding.recyclerViewOffers.layoutManager = LinearLayoutManager(this)
    }
}
