package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anyemi.omrooms.Models.RoomsGuest;
import com.anyemi.omrooms.R;

import java.util.ArrayList;

public class RoomGuestAdapter extends RecyclerView.Adapter<RoomGuestAdapter.RoomsGuestHolder> {

    private ArrayList<RoomsGuest> roomsGuests;
    private Context context;

    public RoomGuestAdapter(ArrayList<RoomsGuest> roomsGuests, Context context) {
        this.roomsGuests = roomsGuests;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomsGuestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_room_guest, viewGroup, false);
        return new RoomsGuestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsGuestHolder roomsGuestHolder, int i) {
        RoomsGuest roomsGuest = roomsGuests.get(i);
        roomsGuestHolder.setData(roomsGuest, i);
        roomsGuestHolder.setListeners();

    }

    @Override
    public int getItemCount() {
        return roomsGuests.size();
    }

    public class RoomsGuestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView hotelsNoTextView;
        Button addRoomButtonView;
        private int position;
        private RoomsGuest currentRoomObject;

        public RoomsGuestHolder(@NonNull View itemView) {
            super(itemView);

            hotelsNoTextView = itemView.findViewById(R.id.roomNo_tv);
            addRoomButtonView = itemView.findViewById(R.id.addRoom_button);
        }

        public void setData(RoomsGuest currentRoomObject, int position){
            this.hotelsNoTextView.setText(currentRoomObject.getRoomsName());
            this.position = position;
            this.currentRoomObject = currentRoomObject;

        }
        public void setListeners() {
            addRoomButtonView.setOnClickListener(RoomsGuestHolder.this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.addRoom_button:
                    addRoom(position, currentRoomObject);
                    break;
            }
        }
    }

    public void addRoom(int position, RoomsGuest currentRoom){
        roomsGuests.add(position, currentRoom);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, roomsGuests.size());
    }
}
