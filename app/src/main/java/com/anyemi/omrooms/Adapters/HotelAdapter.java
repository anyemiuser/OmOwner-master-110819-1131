package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyemi.omrooms.Models.Hotels;
import com.anyemi.omrooms.R;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyHotelViewHolder> {


    private ArrayList<Hotels> hotels;
    private Context context;

    public HotelAdapter(ArrayList<Hotels> hotels, Context context) {
        this.hotels = hotels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHotelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotels_items, viewGroup, false);
        return new MyHotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHotelViewHolder myHotelViewHolder, int i) {

        Hotels hotel = hotels.get(i);
        myHotelViewHolder.hotelsTextView.setText(hotel.getHotelName());
        myHotelViewHolder.hotelsImageView.setImageResource(hotel.getHotelImage());

    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class MyHotelViewHolder extends RecyclerView.ViewHolder {

        TextView hotelsTextView;
        ImageView hotelsImageView;

        public MyHotelViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelsTextView = itemView.findViewById(R.id.hotels_name);
            hotelsImageView = itemView.findViewById(R.id.hotels_image);
        }
    }
}
