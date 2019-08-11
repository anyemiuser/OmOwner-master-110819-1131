package org.sairaa.omowner.Main;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.sairaa.omowner.Model.RoomTarrif;
import org.sairaa.omowner.R;

class RoomTariffHolder extends RecyclerView.ViewHolder {

    private TextView roomType;
    private TextView discount;
    private RadioButton min, avg, max;
    private TextView minT, avgT, maxT;

    public RoomTariffHolder(@NonNull View itemView) {
        super(itemView);

        roomType = itemView.findViewById(R.id.room_type_text);
        min = itemView.findViewById(R.id.min_radio);
        avg = itemView.findViewById(R.id.avg_radio);
        max = itemView.findViewById(R.id.max_radio);
        minT = itemView.findViewById(R.id.min_price_text);
        avgT = itemView.findViewById(R.id.avg_price_text);
        maxT = itemView.findViewById(R.id.max_price_text);
        discount = itemView.findViewById(R.id.discount_text);

    }

    public void set(RoomTarrif tarrif) {
        if (tarrif != null) {
            roomType.setText(tarrif.getRoom_type());

            min.setText(tarrif.getMin_price());
            min.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            avg.setText(tarrif.getAvg_price());
            avg.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            max.setText(tarrif.getMax_price());
            max.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            if (tarrif.getMin_status().equals("1")) {
                min.setChecked(true);
            }

                if (tarrif.getAvg_status().equals("1"))
                    avg.setChecked(true);
                if (tarrif.getMax_status().equals("1")) {
                    max.setChecked(true);
                }
                discount.setText(tarrif.getDiscount().concat("%"));

                Double discountPercent = Double.parseDouble(tarrif.getDiscount());

                Double minimumPrice = Double.parseDouble(tarrif.getMin_price());
                minimumPrice = minimumPrice - ((minimumPrice * discountPercent) / 100);
                minT.setText(String.valueOf((int) Math.round(minimumPrice)));

                Double avgPrice = Double.parseDouble(tarrif.getAvg_price());
                avgPrice = avgPrice - ((avgPrice * discountPercent) / 100);
                avgT.setText(String.valueOf((int) Math.round(avgPrice)));

                Double maxPrice = Double.parseDouble(tarrif.getMax_price());
                maxPrice = maxPrice - ((maxPrice * discountPercent) / 100);
                maxT.setText(String.valueOf((int) Math.round(maxPrice)));




        }
    }
}
