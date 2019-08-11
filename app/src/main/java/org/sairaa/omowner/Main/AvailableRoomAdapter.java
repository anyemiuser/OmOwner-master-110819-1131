package org.sairaa.omowner.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sairaa.omowner.Model.TotalAvailableRoom;
import org.sairaa.omowner.Model.TotalRooms;
import org.sairaa.omowner.R;

import java.util.ArrayList;
import java.util.List;

class AvailableRoomAdapter extends RecyclerView.Adapter<AvailableRoomHolder>{

    private List<TotalAvailableRoom> totalRoomsList = new ArrayList<>();
    private Context context;
    private  AvailableRoomAdapterCallBack availableRoomAdapterCallBack;

    public void updateList(List<TotalAvailableRoom> roomsList){
        totalRoomsList.clear();
        totalRoomsList= roomsList;

        notifyDataSetChanged();
    }

    public AvailableRoomAdapter(Context context) {
        this.context = context;
        try{
            this.availableRoomAdapterCallBack = ((AvailableRoomAdapterCallBack)context);
        }catch (ClassCastException e){
            throw new ClassCastException("Activity must implement RoomIdAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public AvailableRoomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.available_room_details, viewGroup, false);
        return new AvailableRoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableRoomHolder holder, int position) {

        TotalAvailableRoom room = totalRoomsList.get(position);
        holder.set(room);

    }

    @Override
    public int getItemCount() {
        return totalRoomsList.size();
    }

    public static interface AvailableRoomAdapterCallBack{
        void availableMethodCallback();
    }
}
