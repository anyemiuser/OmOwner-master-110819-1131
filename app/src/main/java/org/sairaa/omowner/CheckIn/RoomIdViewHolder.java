package org.sairaa.omowner.CheckIn;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.sairaa.omowner.Model.RoomIdAvailability;
import org.sairaa.omowner.R;

public class RoomIdViewHolder extends RecyclerView.ViewHolder {

    private TextView roomId;
    private RoomIdsAdapter.RoomIdAdapterCallback adapterCallback;

    public RoomIdViewHolder(@NonNull View itemView) {
        super(itemView);
        roomId = itemView.findViewById(R.id.room_id);
    }

    public void set(RoomIdAvailability roomIdDetails) {
        if(roomIdDetails != null){
            roomId.setText(roomIdDetails.getRoom_id());
            roomId.setTextColor(roomId.getContext().getResources().getColor(R.color.colorWhite));
            if(roomIdDetails.getAvailability().equals("0")){//18001024462
                roomId.setBackgroundColor(roomId.getContext().getResources().getColor(R.color.colorGunMetal));

            }
            else {
                roomId.setBackgroundColor(roomId.getContext().getResources().getColor(R.color.colorGreen));
                if(roomIdDetails.getAvailability().equals("2"))
                    roomId.setBackgroundColor(roomId.getContext().getResources().getColor(R.color.colorPrimary));

                roomId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), ""+roomIdDetails.getRoom_id(), Toast.LENGTH_SHORT).show();


//                        if(check){
                            if(roomIdDetails.getAvailability().equals("1")){
                                roomIdDetails.setAvailability("2");
                                roomId.setBackgroundColor(roomId.getContext().getResources().getColor(R.color.colorPrimary));
                            }else {
                                roomIdDetails.setAvailability("1");
                                roomId.setBackgroundColor(roomId.getContext().getResources().getColor(R.color.colorGreen));
                            }

                        boolean check = adapterCallback.onChooseRoom(roomIdDetails);
//                        }
                    }
                });
            }
        }



    }

    public void setAdapertCallBack(RoomIdsAdapter.RoomIdAdapterCallback mAdapterCallback) {
        this.adapterCallback = mAdapterCallback;
    }
}
