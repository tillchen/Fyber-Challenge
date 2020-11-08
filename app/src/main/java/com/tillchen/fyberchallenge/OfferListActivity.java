package com.tillchen.fyberchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tillchen.fyberchallenge.adapters.OffersAdapter;
import com.tillchen.fyberchallenge.models.Offer;

import java.util.ArrayList;

public class OfferListActivity extends AppCompatActivity {

    ArrayList<Offer> offers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        offers = getIntent().getParcelableArrayListExtra("offers");

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOffers);
        recyclerView.setAdapter(new OffersAdapter(offers));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}