package org.sairaa.omowner.BookingDetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sairaa.omowner.Model.CustomerBookings;
import org.sairaa.omowner.R;

import java.util.ArrayList;
import java.util.List;

public class BookingDetailsAdapter extends RecyclerView.Adapter<BookingDetailsHolder> {

    private List<CustomerBookings> customerBookingsList = new ArrayList<>();
    private Context context;

    private BookingAdapterCallback mAdapterCallback;

    public BookingDetailsAdapter(Context context) {

        this.context = context;

        try {
            this.mAdapterCallback = ((BookingAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement RoomIdAdapterCallback.");
        }
    }

    public void updateList(List<CustomerBookings> bookings)  {
//        customerBookingsList.clear();

        customerBookingsList = bookings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingDetailsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_booking_details,viewGroup,false);

        return new BookingDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingDetailsHolder holder, int position) {
        CustomerBookings bookings = customerBookingsList.get(position);
        holder.setAdapertCallBack(mAdapterCallback);
        holder.set(bookings);
    }

    @Override
    public int getItemCount() {
        return customerBookingsList.size();
    }

    public interface BookingAdapterCallback{
        void getActionAndBookingList(String bookingType, CustomerBookings list);

        void setPaymentRequest(double balanceAmount);

        String getBookingTypeCheckInOrOut();

        void cancelBooking(String booking_id, String reason);
    }
}
