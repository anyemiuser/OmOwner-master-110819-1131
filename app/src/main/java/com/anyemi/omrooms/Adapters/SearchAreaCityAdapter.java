package com.anyemi.omrooms.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyemi.omrooms.Model.HotelArea;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.SearchActivity;

import java.util.List;

public class SearchAreaCityAdapter extends RecyclerView.Adapter<SearchAreaCityAdapter.HotelAreaViewHolder> {
    private List<HotelArea> hotelAreas;
    private Context context;

    public SearchAreaCityAdapter(List<HotelArea> hotelAreas, SearchActivity searchActivity) {
        this.hotelAreas = hotelAreas;
        this.context = searchActivity;
    }

    @NonNull
    @Override
    public HotelAreaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_area_hotel_item, viewGroup, false);
        return new HotelAreaViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HotelAreaViewHolder holder, int position) {
        HotelArea hotelArea = hotelAreas.get(position);
        holder.hotelName.setText(hotelArea.getHotelarea());
        String areaCity = hotelArea.getHotelCity()+","+hotelArea.getHotelPin();
        holder.areaCityName.setText(areaCity);

    }

    @Override
    public int getItemCount() {
        return hotelAreas.size();
    }

    public class HotelAreaViewHolder extends RecyclerView.ViewHolder{
        ImageView iconType;
        TextView hotelName, areaCityName, noOfHotels;
        public HotelAreaViewHolder(@NonNull View itemView) {
            super(itemView);
            iconType = itemView.findViewById(R.id.image_type);
            hotelName = itemView.findViewById(R.id.hotels_name);
            areaCityName = itemView.findViewById(R.id.area_city);
            noOfHotels = itemView.findViewById(R.id.no_of_hotel);
        }
    }
}
