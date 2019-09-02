package org.sairaa.omowner.Pricing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.sairaa.omowner.Pricing.Model.RoomTypeAndPrice;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.ConverterUtil;

class PricingViewHolder extends RecyclerView.ViewHolder {

    private CheckBox minC,avgC,maxC;
    private EditText minP,avgP,maxP;
    private EditText discount;
    private TextView roomType;
    private ImageView save, edit;
    private RoomPriceAdapter.RoomPriceAdapterCallback adapterCallback;
    TextView tvsave , tvedit;


    public PricingViewHolder(@NonNull View itemView) {
        super(itemView);
        minC = itemView.findViewById(R.id.min_check);
        avgC = itemView.findViewById(R.id.avg_check);
        maxC = itemView.findViewById(R.id.max_check);

        minP = itemView.findViewById(R.id.min_price);
        avgP = itemView.findViewById(R.id.avg_price);
        maxP = itemView.findViewById(R.id.max_price);

        discount = itemView.findViewById(R.id.discount_per);
        roomType = itemView.findViewById(R.id.room_t);

        save = itemView.findViewById(R.id.save_i);
        edit = itemView.findViewById(R.id.edit_i);

        tvsave = itemView.findViewById(R.id.tv_save);
        tvedit = itemView.findViewById(R.id.tv_edit);
    }

    public void set(RoomTypeAndPrice roomTypeAndPrice) {

        roomType.setText(roomTypeAndPrice.getRoom_type());

        minC.setChecked(ConverterUtil.getChecked(roomTypeAndPrice.getMin_status()));
        avgC.setChecked(ConverterUtil.getChecked(roomTypeAndPrice.getAvg_status()));
        maxC.setChecked(ConverterUtil.getChecked(roomTypeAndPrice.getMax_status()));
//
        minP.setText(roomTypeAndPrice.getMin_price());
        avgP.setText(roomTypeAndPrice.getAvg_price());
        maxP.setText(roomTypeAndPrice.getMax_price());
        discount.setText(roomTypeAndPrice.getDiscount());

        if(roomTypeAndPrice.getMin_price() == null
                && roomTypeAndPrice.getAvg_price() == null
                    && roomTypeAndPrice.getMax_price() == null){
            edit.setVisibility(View.INVISIBLE);
            save.setVisibility(View.VISIBLE);
            tvedit.setVisibility(View.INVISIBLE);
            tvsave.setVisibility(View.VISIBLE);
            enableAll();


        }else {
            disableAll();
            tvedit.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            save.setVisibility(View.INVISIBLE);
            tvedit.setVisibility(View.VISIBLE);
        }

        minC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //0 for minC
                if(b)
                setCheckedToOneCheckBox(0,b);
            }
        });
        avgC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                setCheckedToOneCheckBox(1,b);
            }
        });
        maxC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                setCheckedToOneCheckBox(2,b);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvsave.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
                tvedit.setVisibility(View.INVISIBLE);
                enableAll();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!minP.getText().toString().trim().isEmpty()
                    && !avgP.getText().toString().trim().isEmpty()
                        && !maxP.getText().toString().trim().isEmpty()){

                        if(minC.isChecked() || avgC.isChecked() || maxC.isChecked()){
                            if(!discount.getText().toString().trim().isEmpty()){
                                if(Integer.parseInt(discount.getText().toString().trim())> 0
                                        && Integer.parseInt(discount.getText().toString().trim()) < 80){

                                        RoomTypeAndPrice roomP =
                                                new RoomTypeAndPrice(roomTypeAndPrice.getRoom_type(),
                                                        minP.getText().toString().trim(),
                                                        avgP.getText().toString().trim(),
                                                        maxP.getText().toString().trim(),
                                                        ConverterUtil.getCheckedString(minC.isChecked()),
                                                        ConverterUtil.getCheckedString(avgC.isChecked()),
                                                        ConverterUtil.getCheckedString(maxC.isChecked()),
                                                        discount.getText().toString().trim());
                                        adapterCallback.saveRoomPrice(roomP);

                                        save.setVisibility(View.INVISIBLE);
                                        edit.setVisibility(View.VISIBLE);
                                    tvsave.setVisibility(View.INVISIBLE);
                                    tvedit.setVisibility(View.VISIBLE);

                                        disableAll();

                                }else{
                                    adapterCallback.setMessage("Discount should be in Between for 0 and 80");
                                }

                            }else {
                                adapterCallback.setMessage("Set percentage of discount: "+roomTypeAndPrice.getRoom_type());
                            }

                        }else {
                            adapterCallback.setMessage("Tick Which Price to show to customer: "+roomTypeAndPrice.getRoom_type());
                        }

                }else {
                    adapterCallback.setMessage("Fill Price Details for: "+roomTypeAndPrice.getRoom_type());
                }


            }
        });
    }

    private void setCheckedToOneCheckBox(int i, boolean b) {
        switch (i){
            case 0:
                // o for minC check box
                minC.setChecked(true);
                avgC.setChecked(false);
                maxC.setChecked(false);
                break;
            case 1:
                //1 for avgC check box
                minC.setChecked(false);
                avgC.setChecked(true);
                maxC.setChecked(false);
                break;
            case 2:
                // 2 for maxC check box
                minC.setChecked(false);
                avgC.setChecked(false);
                maxC.setChecked(true);
                break;
        }
    }


    private void disableAll() {
        minC.setEnabled(false);
        avgC.setEnabled(false);
        maxC.setEnabled(false);
        minP.setEnabled(false);
        avgP.setEnabled(false);
        maxP.setEnabled(false);
        discount.setEnabled(false);
    }

    private void enableAll() {
        minC.setEnabled(true);
        avgC.setEnabled(true);
        maxC.setEnabled(true);
        minP.setEnabled(true);
        avgP.setEnabled(true);
        maxP.setEnabled(true);
        discount.setEnabled(true);
    }

    public void setAdapertCallBack(RoomPriceAdapter.RoomPriceAdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }
}
