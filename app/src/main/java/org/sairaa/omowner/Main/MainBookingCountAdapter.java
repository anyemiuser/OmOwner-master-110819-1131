package org.sairaa.omowner.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sairaa.omowner.Model.AllBookingCount;
import org.sairaa.omowner.R;

import java.util.ArrayList;
import java.util.List;

public class MainBookingCountAdapter extends RecyclerView.Adapter<BookingCountHolder> {

    private List<AllBookingCount> allBookingCountList = new ArrayList<>();
    private Context context;
    private AdapterCallback mAdapterCallback;

    void updateList(List<AllBookingCount> bookingList){
        allBookingCountList.clear();
        allBookingCountList= bookingList;
//        for(int i= 0;i<bookingList.size();i++){
//            notifyItemChanged(i);
//        }
        notifyDataSetChanged();
    }
    MainBookingCountAdapter(Context context) {
        this.context = context;
//        this.allBookingCountList = bookingList;
        try {
            this.mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement RoomIdAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public BookingCountHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.booking_details_eod, viewGroup, false);
        return new BookingCountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingCountHolder holder, int posotion) {

        AllBookingCount eachTypeBooking = allBookingCountList.get(posotion);
        holder.set(eachTypeBooking);

        holder.setAdapertCallBack(mAdapterCallback);


    }

    @Override
    public int getItemCount() {
        return allBookingCountList.size();
    }

    public static interface AdapterCallback {
        void onMethodCallback(String type_header);
    }
}
