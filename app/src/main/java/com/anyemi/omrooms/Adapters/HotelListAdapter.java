package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyemi.omrooms.Model.Hotels;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.AreaHotelsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.HotelViewHolder> {
    private List<Hotels> hotels;
    private Context context;
    public HotelListAdapter(List<Hotels> hotels, AreaHotelsActivity areaHotelsActivity) {
        this.hotels = hotels;
        this.context = areaHotelsActivity;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_items, viewGroup, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotels hotel = hotels.get(position);
        holder.hotelName.setText(hotel.getHotel_name());

        String nearBy = hotel.getHotel_area()+", "+hotel.getHotel_city()+", "+hotel.getHotel_district();
        holder.nearByPlace.setText(nearBy);

        String bPrice = "₹"+hotel.getHotel_low_range()+" - "+"₹"+hotel.getHotel_high_range();
        holder.discountedPriceRange.setText(bPrice);

        holder.rating.setText(hotel.getHotel_rating());
        holder.noOfRating.setText(hotel.getHotel_no_of_ratings());
        Glide.with(context)
                .load(hotel.getHotel_image_url())
                .error(R.drawable.ic_location_city)
                // read original from cache (if present) otherwise download it and decode it
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.hotelImage);


    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder{
        private TextView hotelName;
        private TextView nearByPlace;
        private TextView basePriceRange, discountedPriceRange;
        private TextView rating;
        private TextView noOfRating;
        private TextView discount;
        private ImageView hotelImage;
        private ImageView savedImage;
        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.saved_hotels_name);
            nearByPlace = itemView.findViewById(R.id.place_details);
            basePriceRange = itemView.findViewById(R.id.base_price_range);
            discountedPriceRange = itemView.findViewById(R.id.discount_price_range);
            rating = itemView.findViewById(R.id.rating_hotel);
            noOfRating = itemView.findViewById(R.id.no_of_rating);
            discount = itemView.findViewById(R.id.discount);
            hotelImage = itemView.findViewById(R.id.saved_hotels_image);
            savedImage = itemView.findViewById(R.id.saved_symbol);
        }
    }
}
