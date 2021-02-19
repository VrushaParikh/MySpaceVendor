package com.example.myspacevendor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myspacevendor.data.Shop;
import com.example.myspacevendor.databinding.LayoutShopsBinding;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<Shop> restaurantList;
    private RestaurantInterface restaurantInterface;

    public ShopAdapter(List<Shop> restaurantList, RestaurantInterface restaurantInterface) {
        this.restaurantList = restaurantList;
        this.restaurantInterface = restaurantInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutShopsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Shop shop = restaurantList.get(position);

        holder.binding.shopName.setText(shop.getShopName());

        holder.itemView.setOnClickListener(view -> {
            restaurantInterface.onClick(shop);
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LayoutShopsBinding binding;

        public ViewHolder(@NonNull LayoutShopsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }


    public interface RestaurantInterface {
        void onClick(Shop shop);
    }

}