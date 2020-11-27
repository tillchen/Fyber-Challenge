package com.tillchen.fyberchallenge.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.tillchen.fyberchallenge.adapters.OffersAdapter;
import com.tillchen.fyberchallenge.databinding.ActivityOfferListBinding;
import com.tillchen.fyberchallenge.models.Offer;

import java.util.ArrayList;

public class OfferListActivity extends AppCompatActivity {

    ActivityOfferListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOfferListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Offer> offers = getIntent().getParcelableArrayListExtra("offers");

        binding.recyclerViewOffers.setAdapter(new OffersAdapter(offers));
        binding.recyclerViewOffers.setLayoutManager(new LinearLayoutManager(this));
    }
}