package org.sairaa.omowner.RoomUtility;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.sairaa.omowner.RoomUtility.Model.RoomUtility;
import org.sairaa.omowner.R;
import org.sairaa.omowner.RoomUtility.Model.UtilitiyDetails;
import org.sairaa.omowner.Utils.ConverterUtil;

import java.util.ArrayList;
import java.util.List;

public class UtilityMainHolder extends RecyclerView.ViewHolder {
    private TextView roomType;
    private UtilityDetailAdapter adapter;
    private List<RoomUtility> roomUtils = new ArrayList<>();
    UtilityMainHolder(@NonNull View itemView) {
        super(itemView);
        roomType = itemView.findViewById(R.id.room_type_u);

        RecyclerView utilityDRv = itemView.findViewById(R.id.utility_detail_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext());
        utilityDRv.setLayoutManager(layoutManager);
        utilityDRv.setHasFixedSize(true);
        adapter = new UtilityDetailAdapter(roomUtils, itemView.getContext());
        utilityDRv.setAdapter(adapter);


    }

    public void set(UtilitiyDetails utilList) {



        roomType.setText(utilList.getRoom_type());
        roomUtils = ConverterUtil.getUtilList(utilList);
        Log.e("room util",""+new Gson().toJson(utilList));
        adapter.updateList(roomUtils,utilList.getRoom_type());
    }
}
