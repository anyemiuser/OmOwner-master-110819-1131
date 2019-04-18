package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyemi.omrooms.Model.BookingModel;
import com.anyemi.omrooms.Model.DiscountDetail;
import com.anyemi.omrooms.Model.RoomDetails;
import com.anyemi.omrooms.Model.RoomPriceOnDate;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.HotelActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.RoomViewHolder> {
    private List<RoomDetails> roomDetails;
    private Context context;
    private int noOfRoom;
    private int noOfRoomBooked = 0;
    private List<BookingModel> modelsForBooking;



    public RoomTypeAdapter(List<RoomDetails> roomdetails, HotelActivity hotelActivity, int noOfRoom, List<BookingModel> modelsForBooking) {
        this.roomDetails = roomdetails;
        context= hotelActivity;
        this.noOfRoom = noOfRoom;
        this.modelsForBooking = modelsForBooking;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_type_item, viewGroup, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        final BookingModel bookingModel = new BookingModel();
//        int noOfRoomBooked = 0;

        RoomDetails room =roomDetails.get(position);

        bookingModel.setRoom_type(room.getRoom_type());

        List<RoomPriceOnDate> roomPriceOnDates = room.getRoom_prices();
        if(roomPriceOnDates != null){
            int noODays = roomPriceOnDates.size();
            double roomPrice = 0.00;
            //total discount on room on selected dates
            double discountPrice = 0.00;

            double basePrice = 0.00;
            for(int i=0; i<roomPriceOnDates.size();i++){
                try{
                    double discountpercentage;
                    // price of each room before discount
                    double onDatePrice;
                    //discount price on each date
                    double dPrice;

                    double bPrice;


                    onDatePrice = Double.parseDouble(roomPriceOnDates.get(i).getPrice());

                    basePrice = basePrice+onDatePrice;

                    discountpercentage = Double.parseDouble(roomPriceOnDates.get(i).getDiscount());
                    dPrice = (onDatePrice*(discountpercentage/100));
                    discountPrice= discountPrice+dPrice;

                    onDatePrice = onDatePrice - dPrice;

                    roomPrice=roomPrice+onDatePrice;

                }catch (NumberFormatException e){

                }

            }

            int noOfRoomInEach = HotelActivity.modelsForBooking.get(position).getNo_of_room_booked();
            if(noOfRoomInEach == 0){
                noOfRoomInEach = 1;
            }
            HotelActivity.modelsForBooking.get(position).setPrice_to_be_paid(String.valueOf(roomPrice));

            holder.roomType.setText(room.getRoom_type().concat(" >>"));

            holder.basePrice.setText(String.valueOf((int)Math.round(basePrice*noOfRoomInEach)));
            holder.basePrice.setPaintFlags(holder.discountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.payblePrice.setText(String.valueOf((int)Math.round(roomPrice*noOfRoomInEach)));

            holder.discountPrice.setText(String.valueOf((int)Math.round(discountPrice*noOfRoomInEach)));

            HotelActivity.discountDetails.get(position).setBasePrice((int)Math.round(basePrice*noOfRoomInEach));
            HotelActivity.discountDetails.get(position).setPaybalePrice((int)Math.round(roomPrice*noOfRoomInEach));
            HotelActivity.discountDetails.get(position).setDisCountPrice((int)Math.round(discountPrice*noOfRoomInEach));


            holder.roomNightPrice.setText(String.valueOf((int)Math.round(roomPrice))
                    .concat(" X ")
                    .concat(String.valueOf(roomPriceOnDates.size()))
                    .concat(" Night").concat(" X ").concat(String.valueOf(noOfRoomInEach))
                    .concat("Room"));

            holder.add.setOnClickListener(view -> {

                if(noOfRoomBooked< noOfRoom){
//                    noOfRoomBooked++;
                    holder.addDeleteLayout.setVisibility(View.VISIBLE);
                    holder.add.setVisibility(View.GONE);
                    int c = HotelActivity.modelsForBooking.get(position).getNo_of_room_booked()+1;
                    HotelActivity.modelsForBooking.get(position).setNo_of_room_booked(c);
//                    bookingModel.setNo_of_room_booked(bookingModel.getNo_of_room_booked()+1);
//                int noOfRoomBooked = ((HotelActivity)context).addRoomsForBooking(position);
                    noOfRoomBooked++;
                    holder.noOfRoom.setText(String.valueOf(HotelActivity.modelsForBooking.get(position).getNo_of_room_booked()));
                    if(noOfRoomBooked == noOfRoom){

                    }

                }

//                notifyDataSetChanged();

            });

            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    if(context instanceof HotelActivity){
//                        int noOfRoomBooked = ((HotelActivity)context).addRoomsForBooking(position);

                        if(noOfRoomBooked < noOfRoom){
                            //activate book room
                            //call api from Hotel activity;
                            int c = HotelActivity.modelsForBooking.get(position).getNo_of_room_booked()+1;
                            HotelActivity.modelsForBooking.get(position).setNo_of_room_booked(c);
//                    bookingModel.setNo_of_room_booked(bookingModel.getNo_of_room_booked()+1);
//                int noOfRoomBooked = ((HotelActivity)context).addRoomsForBooking(position);
                            noOfRoomBooked++;
                            holder.noOfRoom.setText(String.valueOf(HotelActivity.modelsForBooking.get(position).getNo_of_room_booked()));
//                            noOfRoomBooked++;
//                            bookingModel.setNo_of_room_booked(bookingModel.getNo_of_room_booked()+1);
//                            holder.noOfRoom.setText(String.valueOf(bookingModel.getNo_of_room_booked()));
                            if(noOfRoomBooked == noOfRoom){
//                                ((HotelActivity)context).activateBooking(bookingModel);
                            }
                        }else {
                            //Toast you have selected only 2 rooms
                        }

                        notifyDataSetChanged();


//                    }
                }
            });

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    int noOfRoomBooked = ((HotelActivity)context).removeRoomsForBooking(position);

                    if(HotelActivity.modelsForBooking.get(position).getNo_of_room_booked()>0){
                        noOfRoomBooked--;
                        int c = HotelActivity.modelsForBooking.get(position).getNo_of_room_booked()-1;
                        HotelActivity.modelsForBooking.get(position).setNo_of_room_booked(c);
//                        bookingModel.setNo_of_room_booked(bookingModel.getNo_of_room_booked()-1);
                        holder.noOfRoom.setText(String.valueOf(HotelActivity.modelsForBooking.get(position).getNo_of_room_booked()));
                        if(HotelActivity.modelsForBooking.get(position).getNo_of_room_booked() == 0){
                            holder.addDeleteLayout.setVisibility(View.GONE);
                            holder.add.setVisibility(View.VISIBLE);
                            //disable minus button
                        }
                    }else {
                        //Toast you have selected only 2 rooms
                    }

                    notifyDataSetChanged();
                }
            });
        }
        Glide.with(context)
                .load(room.getRoom_master_image_url())
                .error(R.drawable.ic_location_city)
                // read original from cache (if present) otherwise download it and decode it
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.roomImage);



    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        private ImageView roomImage;
        private Button add, plus,minus;
        private LinearLayout addDeleteLayout;
        private TextView noOfRoom;
        private TextView roomType;
        private TextView payblePrice,discountPrice,basePrice,roomNightPrice;
        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.room_image);

            add = itemView.findViewById(R.id.add);

            addDeleteLayout = itemView.findViewById(R.id.add_layout);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            noOfRoom = itemView.findViewById(R.id.add_no_of_room);
            roomType = itemView.findViewById(R.id.room_type);
            basePrice = itemView.findViewById(R.id.base_price);
            payblePrice = itemView.findViewById(R.id.payble_price);
            discountPrice = itemView.findViewById(R.id.discount_price);

            roomNightPrice = itemView.findViewById(R.id.room_night_price);
        }
    }
}
