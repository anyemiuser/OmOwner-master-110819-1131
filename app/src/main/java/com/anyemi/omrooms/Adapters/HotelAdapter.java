package com.anyemi.omrooms.Adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Model.SavedHotelViewModel;
import com.anyemi.omrooms.Model.Top10Hotel;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.HotelActivity;
import com.anyemi.omrooms.db.BookingDao;
import com.anyemi.omrooms.db.RoomBooking;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyHotelViewHolder> {


    private List<Top10Hotel> hotels;
    private Context context;

    private SavedHotelViewModel viewModel;

    public HotelAdapter(List<Top10Hotel> hotels, Context context, SavedHotelViewModel viewModel) {
        this.hotels = hotels;
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public MyHotelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recommended_hotel, viewGroup, false);
        return new MyHotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHotelViewHolder holder, int position) {

        Top10Hotel hotel = hotels.get(position);
        holder.hotelsTextView.setText(hotel.getHotel_name());
//        myHotelViewHolder.hotelsImageView.setImageResource(hotel.getHotelImage());
        Glide.with(context)
                .load(hotel.getHotel_image_url())
                .error(R.drawable.ic_location_city)
                // read original from cache (if present) otherwise download it and decode it
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.hotelsImageView);
        long rating = 0;
//        hotel.setHotel_rating("null");
        if(hotel.getHotel_rating() != null && hotel.getHotel_rating().length()>0){
            try {
                rating = Long.parseLong(hotel.getHotel_rating());
            }catch (NumberFormatException e){
                rating =1;
            }

        }

        String rate= null;
        if(rating>=4.5){
            rate = String.valueOf(rating)+" Excellent";
        }else if(rating>=4 && (rating < 4.5)){
            rate = String.valueOf(rating)+" Very Good";
        }else {
            rate = String.valueOf(rating)+" Good";
        }
        holder.rating.setText(rate);
        String range = "₹"+hotel.getHotel_low_range()+" - "+"₹"+hotel.getHotel_high_range();
        holder.priceRange.setText(range);

        holder.area.setText(hotel.getHotel_area());

//        new findAsyncTask(viewModel, holder,hotel.getHotel_id()).execute(hotel.getHotel_id());

//        boolean isSaved = viewModel.whetherSaved(hotel.getHotel_id());
//        if(isSaved){
//            holder.saveImage.setImageResource(R.drawable.bhim);
//        }else {
//            holder.saveImage.setImageResource(R.drawable.declined);
//        }
        if(hotel.isSaved()){
            holder.saveImage.setImageResource(R.drawable.ic_saved_love);
        }else {
            holder.saveImage.setImageResource(R.drawable.ic_favorite_black);
        }
        holder.saveImage.setOnClickListener(view -> {

            RoomBooking hotelSaved = new RoomBooking(hotel.getHotel_id(),
                    hotel.getHotel_name(),
                    hotel.getHotel_area(),
                    hotel.getHotel_low_range(),
                    hotel.getHotel_high_range(),
                    hotel.getHotel_rating(),
                    hotel.getHotel_image_url());

            if(hotel.isSaved()){
                holder.saveImage.setImageResource(R.drawable.ic_favorite_black);
                hotel.setSaved(false);
                viewModel.delete(hotelSaved);
                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
            }else {
                holder.saveImage.setImageResource(R.drawable.ic_saved_love);
                hotel.setSaved(true);
//                    Top10Hotel top10Hotel = hotelsList.get(position);

                viewModel.insert(hotelSaved);
                Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
            }

            notifyDataSetChanged();
        });

        holder.linearLayout.setOnClickListener(view -> navigateToHotelActivity(hotel.getHotel_id(),hotel.getHotel_name()));
        holder.hotelsImageView.setOnClickListener(view -> navigateToHotelActivity(hotel.getHotel_id(),hotel.getHotel_name()));


    }

    private void navigateToHotelActivity(String hotelId, String hotelName) {

                Intent intent = new Intent(context, HotelActivity.class);
                intent.putExtra("hotelId",hotelId);
                intent.putExtra("hotelName",hotelName);
                context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class MyHotelViewHolder extends RecyclerView.ViewHolder {

        TextView hotelsTextView,rating,priceRange,area;
        ImageView hotelsImageView;
        ImageView saveImage;
        LinearLayout linearLayout;

        public MyHotelViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linearLayoutH);

            hotelsTextView = itemView.findViewById(R.id.hotels_name);
            hotelsImageView = itemView.findViewById(R.id.hotels_image);
            rating = itemView.findViewById(R.id.hotel_rating);
            priceRange = itemView.findViewById(R.id.price_range);
            area = itemView.findViewById(R.id.area_name);

            saveImage = itemView.findViewById(R.id.save_icon);
        }
    }


}
