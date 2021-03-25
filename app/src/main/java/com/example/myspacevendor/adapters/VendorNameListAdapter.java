package com.example.myspacevendor.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myspacevendor.data.Vendor_Name_List;
import com.example.myspacevendor.databinding.LayoutVendorBinding;

import java.util.List;


public class VendorNameListAdapter extends RecyclerView.Adapter<VendorNameListAdapter.ViewHolder> {

    private List<Vendor_Name_List> restaurantList;
    private VendorNameInterface vendorNameInterface;

    public VendorNameListAdapter(List<Vendor_Name_List> restaurantList, VendorNameInterface vendorNameInterface) {
        this.restaurantList = restaurantList;
        this.vendorNameInterface = vendorNameInterface;
    }


    @NonNull
    @Override
    public VendorNameListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new VendorNameListAdapter.ViewHolder(LayoutVendorBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VendorNameListAdapter.ViewHolder holder, int position) {

        Vendor_Name_List vendor = restaurantList.get(position);

        holder.binding.vendorId.setText(vendor.getVendorId());
        holder.binding.vendorName.setText(vendor.getVendorName());

        holder.binding.vendorDeet.setOnClickListener(view -> {
            vendorNameInterface.onClick(vendor);
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LayoutVendorBinding binding;

        public ViewHolder(@NonNull LayoutVendorBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }


    public interface VendorNameInterface {
        void onClick(Vendor_Name_List vendorNameList);
    }
}

