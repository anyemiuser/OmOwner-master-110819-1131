package com.anyemi.omrooms.Adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyemi.omrooms.Models.SavedHotels;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.db.RoomBooking;

import java.util.ArrayList;

public class SavedHotelsAdapter extends PagedListAdapter<RoomBooking,SavedHotelsAdapter.MyViewHolder> {


//    private ArrayList<SavedHotels> savedHotels;
    private Context context;
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

    public SavedHotelsAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        RoomBooking savedHotel = getItem(position);
        if (savedHotel != null){
            myViewHolder.savedHotelsTextView.setText(savedHotel.getHotel_name());
//            myViewHolder.savedHotelsImageView.setImageResource(savedHotel.getHotel_image_url());
        }


    }

//    @Override
//    public int getItemCount() {
//        return savedHotels.size();
//    }

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
