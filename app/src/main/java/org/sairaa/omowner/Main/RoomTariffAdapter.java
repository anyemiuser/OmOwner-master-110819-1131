package org.sairaa.omowner.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sairaa.omowner.Model.AllBookingCount;
import org.sairaa.omowner.Model.RoomTarrif;
import org.sairaa.omowner.R;

import java.util.ArrayList;
import java.util.List;

class RoomTariffAdapter extends RecyclerView.Adapter<RoomTariffHolder> {
    private List<RoomTarrif> roomTariffList = new ArrayList<>();
    private Context context;
    private TariffAdapterCallback tariffAdapterCallback;

    void updateList(List<RoomTarrif> tarrifList){
        roomTariffList.clear();
        roomTariffList= tarrifList;
        notifyDataSetChanged();
    }

    RoomTariffAdapter(Context context) {
        this.context = context;
//        this.allBookingCountList = bookingList;
        try {
            this.tariffAdapterCallback = ((TariffAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement RoomIdAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public RoomTariffHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.room_tariff_layout, viewGroup, false);
        return new RoomTariffHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomTariffHolder holder, int position) {

        RoomTarrif tarrif = roomTariffList.get(position);
        holder.set(tarrif);

    }

    @Override
    public int getItemCount() {
        return roomTariffList.size();
    }

    public static interface TariffAdapterCallback{
        void onRoomTariffClick();
    }
}
