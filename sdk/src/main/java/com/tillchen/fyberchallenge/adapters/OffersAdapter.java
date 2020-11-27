package com.tillchen.fyberchallenge.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tillchen.fyberchallenge.databinding.ItemOfferBinding;
import com.tillchen.fyberchallenge.models.Offer;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemOfferBinding binding;

        public ViewHolder(ItemOfferBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private final ArrayList<Offer> offers;

    public OffersAdapter(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    @NonNull
    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemOfferBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.ViewHolder holder, int position) {
        Offer offer = offers.get(position);
        holder.binding.textViewTitle.setText(offer.getTitle());
        Glide.with(holder.itemView.getContext()).load(offer.getPictureUrl())
                .into(holder.binding.imageViewThumbnail);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }
}
