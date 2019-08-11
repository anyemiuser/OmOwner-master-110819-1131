package org.sairaa.omowner.RoomUtility;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.sairaa.omowner.R;
import org.sairaa.omowner.RoomUtility.Model.RoomUtility;
import org.sairaa.omowner.Utils.ConverterUtil;


public class UtilItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView utilityImage;
    private TextView utilityName;
    private Switch switchV;
    private UtilityDetailAdapter.UtilAdapterCallback adapterCallback;
    public UtilItemViewHolder(@NonNull View itemView) {
        super(itemView);
        utilityImage = itemView.findViewById(R.id.utility_image);
        utilityName = itemView.findViewById(R.id.desc);
        switchV = itemView.findViewById(R.id.switch_on_off);
    }

    public void set(RoomUtility utility, String roomT) {
        utilityImage.setImageResource(ConverterUtil.getFacilityImageResourceId(utility.getFacility()));
        utilityName.setText(utility.getFacility());
        switchV.setChecked(ConverterUtil.getChecked(utility.getOnOff()));
        switchV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    utility.setOnOff("1");
                }else {
                    utility.setOnOff("0");
                }
                adapterCallback.setUtilityStatus(utility,roomT);
            }
        });
    }

    public void setAdapterCallback(UtilityDetailAdapter.UtilAdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }
}
