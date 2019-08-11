package org.sairaa.omowner.NewBooking.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sairaa.omowner.NewBooking.Model.RoomTypePrice;
import org.sairaa.omowner.R;

import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeViewHolder> {

    private List<RoomTypePrice> roomDetails;
    private Context context;

    private RoomTypeAdapterCallback adapterCallback;

    public RoomTypeAdapter(Context context, List<RoomTypePrice> roomDetails) {
        this.context = context;
        this.roomDetails = roomDetails;

        try {
            this.adapterCallback = ((RoomTypeAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement RoomIdAdapterCallback.");
        }
    }

    public void updateList(List<RoomTypePrice> roomDetails){
        this.roomDetails = roomDetails;
    }

    @NonNull
    @Override
    public RoomTypeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_type_item, viewGroup, false);
        return new RoomTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomTypeViewHolder holder, int position) {
        RoomTypePrice room =roomDetails.get(position);
        holder.setAdapertCallBack(adapterCallback);
        holder.set(room);

    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    public interface RoomTypeAdapterCallback{
        void setBookedRoomCount(RoomTypePrice roomTypePrice, int i);

    }
}
