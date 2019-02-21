package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyemi.omrooms.Model.RoomDetails;
import com.anyemi.omrooms.Model.RoomPriceOnDate;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.HotelActivity;

import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.RoomViewHolder> {
    List<RoomDetails> roomDetails;
    Context context;


    public RoomTypeAdapter(List<RoomDetails> roomdetails, HotelActivity hotelActivity) {
        this.roomDetails = roomdetails;
        context= hotelActivity;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_type_item, viewGroup, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {

        RoomDetails room =roomDetails.get(position);
        List<RoomPriceOnDate> roomPriceOnDates = room.getRoom_prices();
        if(roomPriceOnDates != null){
            float roomPrice = 0;
            for(int i=0; i<roomPriceOnDates.size();i++){
                try{
                    roomPrice=roomPrice+Float.parseFloat(roomPriceOnDates.get(i).getPrice());

                }catch (NumberFormatException e){

                }

            }
            holder.price.setText(String.valueOf(roomPrice));
        }

    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private ImageView roomImage;
        private TextView price;
        private Button add;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.room_image);
            price = itemView.findViewById(R.id.price);
            add = itemView.findViewById(R.id.add);
        }
    }
}
