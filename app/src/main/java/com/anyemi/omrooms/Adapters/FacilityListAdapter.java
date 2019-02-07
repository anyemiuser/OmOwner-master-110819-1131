package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyemi.omrooms.Model.RoomFacility;
import com.anyemi.omrooms.R;

import java.util.List;

public class FacilityListAdapter extends RecyclerView.Adapter<FacilityListAdapter.FacilityViewHolder> {
    private List<RoomFacility> facilityList;
    private Context context;

    public FacilityListAdapter(List<RoomFacility> facilityList, Context context) {
        this.facilityList = facilityList;
        this.context = context;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.facility_items, viewGroup, false);
        return new FacilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {

        RoomFacility facility = facilityList.get(position);
        holder.facilityType.setText(facility.getFacility());
        holder.roomType.setText(facility.getRoomType());

    }

    @Override
    public int getItemCount() {
        return facilityList.size();
    }

    public class FacilityViewHolder extends RecyclerView.ViewHolder{
        private ImageView facilityImage;
        private TextView facilityType, roomType;
        public FacilityViewHolder(@NonNull View itemView) {
            super(itemView);
            facilityImage = itemView.findViewById(R.id.type_image);
            facilityType = itemView.findViewById(R.id.facility_name);
            roomType = itemView.findViewById(R.id.room_type);
        }
    }
}
