package org.sairaa.omowner.RoomUtility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sairaa.omowner.R;
import org.sairaa.omowner.RoomUtility.Model.RoomUtility;

import java.util.List;

class UtilityDetailAdapter extends RecyclerView.Adapter<UtilItemViewHolder> {
    private List<RoomUtility> roomUtils;
    private Context context;
    private String roomT;
    private UtilAdapterCallback adapterCallback;
    public UtilityDetailAdapter(List<RoomUtility> roomUtils, Context context) {
        this.roomUtils = roomUtils;
        this.context = context;
        try {
            this.adapterCallback = ((UtilAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement RoomIdAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public UtilItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.utility_item, viewGroup, false);
        return new UtilItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilItemViewHolder holder, int position) {
        RoomUtility utility = roomUtils.get(position);
        holder.setAdapterCallback(adapterCallback);
        holder.set(utility,roomT);
    }

    @Override
    public int getItemCount() {
        return roomUtils.size();
    }

    public void updateList(List<RoomUtility> roomUtils, String room_type) {
        this.roomUtils = roomUtils;
        roomT = room_type;
        notifyDataSetChanged();
    }

    interface UtilAdapterCallback{
        void setUtilityStatus(RoomUtility utility, String roomType);
    }
}
