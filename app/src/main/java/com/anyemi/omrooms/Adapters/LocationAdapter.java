package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyemi.omrooms.Models.Location;
import com.anyemi.omrooms.R;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {


    private ArrayList<Location> locations;
    private Context context;

    public LocationAdapter(ArrayList<Location> locations, Context context) {
        this.locations = locations;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_items, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Location location = locations.get(i);
        myViewHolder.locationsTextView.setText(location.getLocationName());
        myViewHolder.locationsImageView.setImageResource(location.getLocationImage());

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView locationsTextView;
        ImageView locationsImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            locationsTextView = itemView.findViewById(R.id.locations_tv);
            locationsImageView = itemView.findViewById(R.id.locations_image);
        }
    }
}
