package org.sairaa.omowner.CheckIn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sairaa.omowner.Model.RoomIdAvailability;
import org.sairaa.omowner.R;

import java.util.ArrayList;
import java.util.List;

public class RoomIdsAdapter extends RecyclerView.Adapter<RoomIdViewHolder> {

    private Context context;
    List<RoomIdAvailability> roomList = new ArrayList<>();

    private RoomIdAdapterCallback mAdapterCallback;

    RoomIdsAdapter(Context context) {
        this.context = context;
//        this.allBookingCountList = bookingList;
        try {
            this.mAdapterCallback = ((RoomIdAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement RoomIdAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public RoomIdViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_id_layout,viewGroup,false);

        return new RoomIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomIdViewHolder holder, int position) {

        RoomIdAvailability roomIdDetails = roomList.get(position);
        if(roomIdDetails != null){
            holder.set(roomIdDetails);
            holder.setAdapertCallBack(mAdapterCallback);
        }

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public void update(List<RoomIdAvailability> roomAvailabilityList) {
//        roomList.clear();
        this.roomList = roomAvailabilityList;
        notifyDataSetChanged();
    }

    public interface RoomIdAdapterCallback {
        boolean onChooseRoom(RoomIdAvailability roomAvailability);

    }
}
