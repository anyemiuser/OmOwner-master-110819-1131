package com.anyemi.omrooms.Adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Model.SavedHotelViewModel;
import com.anyemi.omrooms.Models.SavedHotels;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.HotelActivity;
import com.anyemi.omrooms.Utils.ConverterUtil;
import com.anyemi.omrooms.db.RoomBooking;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SavedHotelsAdapter extends PagedListAdapter<RoomBooking,SavedHotelsAdapter.MyViewHolder> {


//    private ArrayList<SavedHotels> savedHotels;
    private Context context;
    private SavedHotelViewModel viewModel;
    private static DiffUtil.ItemCallback<RoomBooking> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<RoomBooking>() {
                @Override
                public boolean areItemsTheSame(@NonNull RoomBooking oldItem, @NonNull RoomBooking newItem) {
                    return oldItem.getHotel_id() == newItem.getHotel_id();
                }

                @Override
                public boolean areContentsTheSame(@NonNull RoomBooking oldItem, @NonNull RoomBooking newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public SavedHotelsAdapter(Context context, SavedHotelViewModel viewModel) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.viewModel = viewModel;
    }

//    public SavedHotelsAdapter(ArrayList<SavedHotels> savedHotels, Context context) {
//        this.savedHotels = savedHotels;
//        this.context = context;
//    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_items, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RoomBooking hotel = getItem(position);
        if (hotel != null){
            holder.noOfRating.setVisibility(View.GONE);
            holder.hotelName.setText(hotel.getHotel_name());

            holder.nearByPlace.setText(hotel.getHotel_area());

            String bPrice = "₹"+hotel.getHotel_low_range()+" - "+"₹"+hotel.getHotel_high_range();
            holder.discountedPriceRange.setText(bPrice);
            double ratingT = 5;
            if(hotel.getHotel_rating()!= null){
                ratingT = Double.parseDouble(hotel.getHotel_rating());
            }
            holder.rating.setText(new DecimalFormat("##.#").format(ratingT));
            holder.ratingText.setText(ConverterUtil.getRatingText(ratingT));
            holder.noOfRating.setText(hotel.getHotel_rating());
            Glide.with(context)
                    .load(hotel.getHotel_image_url())
                    .error(R.drawable.ic_location_city)
                    // read original from cache (if present) otherwise download it and decode it
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(holder.hotelImage);
//            myViewHolder.savedHotelsImageView.setImageResource(savedHotel.getHotel_image_url());
        }
        holder.savedImage.setImageResource(R.drawable.ic_saved_love);
        holder.savedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoomBooking hotelSaved = new RoomBooking(hotel.getHotel_id(),
                        hotel.getHotel_name(),
                        hotel.getHotel_area(),
                        hotel.getHotel_low_range(),
                        hotel.getHotel_high_range(),
                        hotel.getHotel_rating(),
                        hotel.getHotel_image_url());

                holder.savedImage.setImageResource(R.drawable.ic_favorite_black);
                viewModel.delete(hotelSaved);
                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HotelActivity.class);
                if (hotel != null) {
                    intent.putExtra("hotelId",hotel.getHotel_id());
                    intent.putExtra("hotelName",hotel.getHotel_name());
                    context.startActivity(intent);
                }


            }
        });

    }

//    @Override
//    public int getItemCount() {
//        return savedHotels.size();
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        private TextView hotelName;
        private TextView nearByPlace;
        private TextView basePriceRange, discountedPriceRange;
        private TextView rating, ratingText;
        private TextView noOfRating;
        private TextView discount;
        private ImageView hotelImage;
        private ImageView savedImage;
        private LinearLayout linearLayout;
        private RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelName = itemView.findViewById(R.id.saved_hotels_name);
            nearByPlace = itemView.findViewById(R.id.place_details);
            basePriceRange = itemView.findViewById(R.id.base_price_range);
            discountedPriceRange = itemView.findViewById(R.id.discount_price_range);
            rating = itemView.findViewById(R.id.rating_hotel);
            ratingText = itemView.findViewById(R.id.rating_text);
            noOfRating = itemView.findViewById(R.id.no_of_rating);
            discount = itemView.findViewById(R.id.discount);
            hotelImage = itemView.findViewById(R.id.saved_hotels_image);
            savedImage = itemView.findViewById(R.id.saved_symbol);


            linearLayout = itemView.findViewById(R.id.linear_saved_item);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
