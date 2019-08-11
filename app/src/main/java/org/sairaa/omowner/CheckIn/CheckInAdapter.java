package org.sairaa.omowner.CheckIn;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sairaa.omowner.Model.RoomIdMaster;
import org.sairaa.omowner.R;

import java.util.ArrayList;
import java.util.List;

public class CheckInAdapter extends RecyclerView.Adapter<CheckInViewHolder> {

    private List<RoomIdMaster> roomIdMasterList = new ArrayList<>();

    private Context context;

    public CheckInAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CheckInViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkin_holder_layout,viewGroup,false);

        return new CheckInViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInViewHolder holder, int position) {
        RoomIdMaster roomIdMaster = roomIdMasterList.get(position);
        holder.set(roomIdMaster);

    }

    @Override
    public int getItemCount() {
        return roomIdMasterList.size();
    }

    public void updateList(List<RoomIdMaster> roomAvailabilities) {
//        roomIdMasterList.clear();
        roomIdMasterList=roomAvailabilities;
        notifyDataSetChanged();
    }
}
