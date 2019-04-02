package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyemi.omrooms.Model.UpComingBooking;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.BookingDetailActivity;
import com.google.gson.Gson;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.BookedHotelViewHolder> {

    private List<UpComingBooking> bookingHistoryList;
    private Context context;
    private String status;

    public BookingDetailsAdapter(String statusU, List<UpComingBooking> bookingHistoryList, Context context) {
        this.status = statusU;
        this.bookingHistoryList = bookingHistoryList;
        this.context = context;

    }

    @NonNull
    @Override
    public BookedHotelViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booking_history_item, viewGroup, false);
        return new BookedHotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedHotelViewHolder holder, int position) {

        UpComingBooking booking = bookingHistoryList.get(position);
        holder.city.setText(booking.getHotel_city().concat(" >>"));
        holder.bookedTime.setText(booking.getBooking_date());
        holder.hotelName.setText(booking.getHotel_name());
        String bTime = booking.getFrom_date().concat(" ").concat(booking.getNo_of_nights_booked())
                .concat("N ").concat(booking.getTo_date());
        holder.checkInOut.setText(bTime);
//        if(status.equals("u")){
//            holder.cancelBook.setText("Cancel");
//        }else if(status.equals("c")){
//            holder.cancelBook.setText("Book Again");
//        }else {
//            holder.cancelBook.setText("Book Again");
//        }

        holder.viewDetails.setText("View Details");
        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookingDetailActivity.class);
                intent.putExtra("bookingStatus",status);
                intent.putExtra("bookingDetails",new Gson().toJson(booking));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingHistoryList.size();
    }

    public class BookedHotelViewHolder extends RecyclerView.ViewHolder{

        private TextView city,bookedTime,hotelName,checkInOut,cancelBook,viewDetails;
        private CircleImageView hotelImage;

        public BookedHotelViewHolder(@NonNull View itemView) {
            super(itemView);

            city = itemView.findViewById(R.id.city_name);
            bookedTime = itemView.findViewById(R.id.booked_time);
            hotelName = itemView.findViewById(R.id.hotel_name_view);
            checkInOut = itemView.findViewById(R.id.checkin_details_time);

            viewDetails = itemView.findViewById(R.id.view_details);

            hotelImage = itemView.findViewById(R.id.hotelcircleImageView);

        }
    }
}
