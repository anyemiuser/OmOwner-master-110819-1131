package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyemi.omrooms.Models.SavedHotels;
import com.anyemi.omrooms.R;

import java.util.ArrayList;

public class SavedHotelsAdapter extends RecyclerView.Adapter<SavedHotelsAdapter.MyViewHolder> {


    private ArrayList<SavedHotels> savedHotels;
    private Context context;

    public SavedHotelsAdapter(ArrayList<SavedHotels> savedHotels, Context context) {
        this.savedHotels = savedHotels;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_items, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        SavedHotels savedHotel = savedHotels.get(i);
        myViewHolder.savedHotelsTextView.setText(savedHotel.getSavedHotelName());
        myViewHolder.savedHotelsImageView.setImageResource(savedHotel.getSavedHotelImage());

    }

    @Override
    public int getItemCount() {
        return savedHotels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView savedHotelsTextView;
        ImageView savedHotelsImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            savedHotelsTextView = itemView.findViewById(R.id.saved_hotels_name);
            savedHotelsImageView = itemView.findViewById(R.id.saved_hotels_image);
        }
    }
}
