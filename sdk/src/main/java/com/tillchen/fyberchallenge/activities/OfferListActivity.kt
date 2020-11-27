package com.tillchen.fyberchallenge.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tillchen.fyberchallenge.adapters.OffersAdapter
import com.tillchen.fyberchallenge.databinding.ActivityOfferListBinding
import com.tillchen.fyberchallenge.models.Offer

class OfferListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOfferListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfferListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val offers = intent.getParcelableArrayListExtra<Offer>("offers")
        binding.recyclerViewOffers.adapter = OffersAdapter(offers)
        binding.recyclerViewOffers.layoutManager = LinearLayoutManager(this)
    }
}
