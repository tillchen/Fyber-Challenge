package com.tillchen.fyberchallenge.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tillchen.fyberchallenge.R;
import com.tillchen.fyberchallenge.models.Offer;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public ImageView imageViewThumbNail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageViewThumbNail = itemView.findViewById(R.id.imageViewThumbnail);
        }
    }

    private ArrayList<Offer> offers;

    public OffersAdapter(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    @NonNull
    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View offerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer, parent, false);
        return new ViewHolder(offerView);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.ViewHolder holder, int position) {
        Offer offer = offers.get(position);
        holder.textViewTitle.setText(offer.getTitle());
        Glide.with(holder.itemView.getContext()).load(offer.getPictureUrl())
                .into(holder.imageViewThumbNail);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }
}
