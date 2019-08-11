package org.sairaa.omowner.Pricing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.sairaa.omowner.Pricing.Model.RoomTypeAndPrice;
import org.sairaa.omowner.R;

import java.util.List;
import java.util.zip.Inflater;

public class RoomPriceAdapter extends RecyclerView.Adapter<PricingViewHolder> {

    private List<RoomTypeAndPrice> roomPrice;
    private Context context;

    private RoomPriceAdapterCallback adapterCallback;

    public RoomPriceAdapter(List<RoomTypeAndPrice> roomPrice, Context context) {
        this.roomPrice = roomPrice;
        this.context = context;
        try {
            this.adapterCallback = ((RoomPriceAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement RoomIdAdapterCallback.");
        }
    }

    @NonNull
    @Override
    public PricingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.price_update, viewGroup, false);
        return new PricingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PricingViewHolder holder, int position) {
        RoomTypeAndPrice roomTypeAndPrice = roomPrice.get(position);
        holder.setAdapertCallBack(adapterCallback);
        holder.set(roomTypeAndPrice);
    }

    @Override
    public int getItemCount() {
        return roomPrice.size();
    }

    public void updateList(List<RoomTypeAndPrice> listRoomPrice) {
        this.roomPrice = listRoomPrice;
        Log.e("adapter: ",""+new Gson().toJson(roomPrice));
        notifyDataSetChanged();
    }

    public interface RoomPriceAdapterCallback{
        void saveRoomPrice(RoomTypeAndPrice roomPrice);
        void setMessage(String message);
    }
}
