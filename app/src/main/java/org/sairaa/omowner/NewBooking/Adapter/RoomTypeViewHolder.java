package org.sairaa.omowner.NewBooking.Adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.sairaa.omowner.NewBooking.BookingActivity;
import org.sairaa.omowner.NewBooking.BookingViewModel;
import org.sairaa.omowner.NewBooking.Model.RoomTypePrice;
import org.sairaa.omowner.R;

class RoomTypeViewHolder extends RecyclerView.ViewHolder {
    private ImageView roomImage;
    private Button add, plus,minus;
    private LinearLayout addDeleteLayout;
    private TextView noOfRoom;
    private TextView roomType;
    private TextView payblePrice,discountPrice,basePrice,roomNightPrice;
    private RoomTypeAdapter.RoomTypeAdapterCallback adapterCallBack;
    ConstraintLayout RmTypeLl;
    BookingViewModel bookingViewModel;
    TextView noroomsleft;


    public RoomTypeViewHolder(@NonNull View itemView) {
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
        noroomsleft = itemView.findViewById(R.id.noroomleft);
        roomNightPrice = itemView.findViewById(R.id.room_night_price);
        RmTypeLl = itemView.findViewById(R.id.room_type_ll);

//      bookingViewModel = ViewModelProviders.of().get(BookingViewModel .class);
    }

    public void set(RoomTypePrice room) {

        roomType.setText(room.getRoom_type());
        if(room.getNoOfRoomSelected() == 0){
            basePrice.setText(room.getBase_Price());
            discountPrice.setText(room.getDiscounted_price());
            payblePrice.setText(room.getPayble_price());
            roomNightPrice.setText(room.getBase_Price().concat(" X ").concat(String.valueOf(room.getNoOfNights())).concat(" Night").concat(" X ").concat("1 Room"));
        }else {
            basePrice.setText(String.valueOf((int)Math.round(Double.parseDouble(room.getBase_Price()) * room.getNoOfRoomSelected())));
            discountPrice.setText(String.valueOf((int)Math.round(Double.parseDouble(room.getDiscounted_price())) * room.getNoOfRoomSelected()));
            payblePrice.setText(String.valueOf((int)Math.round(Double.parseDouble(room.getPayble_price())) * room.getNoOfRoomSelected()));
            roomNightPrice.setText(room.getBase_Price().concat(" X ").concat(String.valueOf(room.getNoOfNights())).concat(" Night ").concat(" X ").concat(String.valueOf(room.getNoOfRoomSelected())).concat(" Room"));
        }



        if(room.getNoOfRoomSelected()>0){
            addDeleteLayout.setVisibility(View.VISIBLE);
            noOfRoom.setText(String.valueOf(room.getNoOfRoomSelected()));
           add.setVisibility(View.INVISIBLE);
           // RmTypeLl.setVisibility(View.INVISIBLE);
        }else {
            add.setVisibility(View.VISIBLE);
            addDeleteLayout.setVisibility(View.INVISIBLE);
        }

       /* if(!room.isRoomAvailable()){
          // add.setVisibility(View.INVISIBLE);
            RmTypeLl.setVisibility(View.INVISIBLE);
            //noroomsleft.setText("No Rooms Left");
            noroomsleft.setVisibility(View.VISIBLE);

        }
        else {
          //  add.setVisibility(View.VISIBLE);
            RmTypeLl.setVisibility(View.VISIBLE);
            noroomsleft.setVisibility(View.INVISIBLE);
        }*/

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                room.setNoOfRoomSelected(room.getNoOfRoomSelected()+1);
                adapterCallBack.setBookedRoomCount(room, 1);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                room.setNoOfRoomSelected(room.getNoOfRoomSelected()+1);
                if(noOfRoom.getText().toString().equals(BookingActivity.strclassicroom)) {
                    Toast.makeText(view.getContext(), "Only "+BookingActivity.strclassicroom +" Rooms are Available", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    adapterCallBack.setBookedRoomCount(room, 1);
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                room.setNoOfRoomSelected(room.getNoOfRoomSelected()-1);
                adapterCallBack.setBookedRoomCount(room, -1);
            }
        });


    }

    public void setAdapertCallBack(RoomTypeAdapter.RoomTypeAdapterCallback adapterCallback) {
        this.adapterCallBack = adapterCallback;
    }
}
