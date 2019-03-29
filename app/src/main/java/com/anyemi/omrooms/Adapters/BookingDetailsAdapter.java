package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyemi.omrooms.Model.UpComingBooking;
import com.anyemi.omrooms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsAdapter.BookedHotelViewHolder> {

    private List<UpComingBooking> bookingHistoryList;
    private Context context;

    public BookingDetailsAdapter(List<UpComingBooking> bookingHistoryList, Context context) {
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
        holder.city.setText(booking.getHotel_city());
        holder.bookedTime.setText(booking.getBooking_date());
        holder.hotelName.setText(booking.getHotel_name());
        String bTime = booking.getFrom_date().concat(" ").concat(booking.getNo_of_nights_booked())
                .concat(" ").concat(booking.getTo_date());
        holder.checkInOut.setText(bTime);
        holder.cancelBook.setText("Cancel");
        holder.viewDetails.setText("View Details");
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
            cancelBook = itemView.findViewById(R.id.cancel_book);
            viewDetails = itemView.findViewById(R.id.view_details);

            hotelImage = itemView.findViewById(R.id.hotelcircleImageView);

        }
    }
}
