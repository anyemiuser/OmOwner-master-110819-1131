package org.sairaa.omowner.CheckIn;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import org.sairaa.omowner.Model.RoomIdMaster;
import org.sairaa.omowner.R;

public class CheckInViewHolder extends RecyclerView.ViewHolder {
    private TextView roomTypeHeader;
    private RecyclerView idRv;
    RoomIdsAdapter roomIdsAdapter;

    public CheckInViewHolder(@NonNull View itemView) {
        super(itemView);

        roomTypeHeader = itemView.findViewById(R.id.room_type_rv_header);
        idRv = itemView.findViewById(R.id.id_rv);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(itemView.getContext(),3);
        idRv.setLayoutManager(gridLayoutManager);
        roomIdsAdapter= new RoomIdsAdapter(itemView.getContext());
        idRv.setAdapter(roomIdsAdapter);
    }


    public void set(RoomIdMaster roomIdMaster) {
        if(roomIdMaster != null){
            roomTypeHeader.setText(roomIdMaster.getRoomType());
            if(roomIdMaster.getRoomAvailabilityList() != null){
                roomIdsAdapter.update(roomIdMaster.getRoomAvailabilityList());
            }
        }

    }
}
