package com.example.myspacevendor.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myspacevendor.data.Booking;
import com.example.myspacevendor.databinding.LayoutViewTokenBinding;

import java.util.List;


public class ViewHistoryAdapter extends RecyclerView.Adapter<ViewHistoryAdapter.ViewHolder> {

    private List<Booking> bookingList;
    private BookingInterface bookingInterface;

    public ViewHistoryAdapter(List<Booking> restaurantList, BookingInterface bookingInterface) {
        this.bookingList = restaurantList;
        this.bookingInterface = bookingInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutViewTokenBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Booking booking = bookingList.get(position);
        holder.binding.tvCustName.setText(booking.getUserName());

        if (booking.isVerified() == 0) {
            holder.binding.tvStatus.setText("Pending");
        } else if (booking.isVerified() == 1) {
            holder.binding.tvStatus.setText("Verified");
        } else if (booking.isDone() == 1) {
            holder.binding.tvStatus.setText("Status Closed");
        }

        holder.binding.tvStatus.setText(booking.getShopName());
        holder.binding.tvSlotStart.setText("Slot Start : " + booking.getSlotStart());
        holder.binding.tvSlotEnd.setText("Slot End : " + booking.getSlotEnd());

        holder.binding.btnShowQR.setOnClickListener(view -> {
            bookingInterface.onClick(booking);
        });

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        LayoutViewTokenBinding binding;

        public ViewHolder(@NonNull LayoutViewTokenBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }


    public interface BookingInterface {
        void onClick(Booking booking);
    }

}



