package org.sairaa.omowner.Main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.sairaa.omowner.Model.AllBookingCount;
import org.sairaa.omowner.Model.RoomTypeCount;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.Constants;
import org.sairaa.omowner.Utils.ConverterUtil;

public class BookingCountHolder extends RecyclerView.ViewHolder implements View.OnClickListener, Constants {

    private TextView bookingType;
    private TextView noOfRoom;
    private TextView noOfBooking;
    private TextView roomType;
    private TextView toCheckOut;
    private ProgressBar progressBar;
    private Button viewDetails;

    private MainBookingCountAdapter.AdapterCallback mAdapterCallback;


    public BookingCountHolder(@NonNull View itemView) {
        super(itemView);
        bookingType = itemView.findViewById(R.id.type_header);
//        bookingType.setOnClickListener((View.OnClickListener) itemView.getContext());

        noOfRoom = itemView.findViewById(R.id.no_of_room);
        noOfBooking = itemView.findViewById(R.id.no_of_bookings);



    }

    public void set(final AllBookingCount eachTypeBooking) {

        bookingType.setText(ConverterUtil.getBookingTypeString(eachTypeBooking.getBookingType()));

//        String roomT = "";
//        for(RoomTypeCount roomTypeCount : eachTypeBooking.getRoomTypeCountList()){
//            if(roomTypeCount.getBooked()!= null){
//                roomT = roomT.concat(roomTypeCount.getRoom_type().concat(": ").concat(roomTypeCount.getBooked()).concat("\n"));
//            }
//
//        }
//        roomType.setText(roomT);
        String noRooms = "";
        String noBookings="";
//        switch (eachTypeBooking.getBookingType()){
//            case upComingType:
//                noRooms = "Total No Of Rooms Booked: ";
//                break;
//
//            case inHouseType:
//                noRooms = "Total No Of Occupied Rooms: ";
////                progressBar.setVisibility(View.GONE);
//                break;
//
//
//            case completedType:
//                noRooms = "Checked Out Rooms: ";
////                progressBar.setVisibility(View.VISIBLE);
//                break;
//
//        }
        noRooms = noRooms.concat(String.valueOf(eachTypeBooking.getNoOfRoomBooked()));//.concat(" / ").concat(String.valueOf(eachTypeBooking.getTotalRooms()));
        noOfRoom.setText(noRooms);
        noOfBooking.setText("Total No Of Bookings: ".concat(String.valueOf(eachTypeBooking.getNoOfBookings())));



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapterCallback.onMethodCallback(eachTypeBooking.getBookingType());
            }
        });
    }

    public void setAdapertCallBack(MainBookingCountAdapter.AdapterCallback mAdapterCallback) {

        this.mAdapterCallback = mAdapterCallback;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.type_header:
////                mAdapterCallback.onMethodCallback(R.id.type_header);
//                break;
//            case R.id.progress_bar_room_occupancy:
////                mAdapterCallback.onMethodCallback(R.id.progress_bar_room_occupancy);
//                break;
        }

    }
}
