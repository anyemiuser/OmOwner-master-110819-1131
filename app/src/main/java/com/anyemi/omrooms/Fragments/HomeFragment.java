package com.anyemi.omrooms.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyemi.omrooms.Adapters.HotelAdapter;
import com.anyemi.omrooms.Adapters.LocationAdapter;
import com.anyemi.omrooms.Models.Hotels;
import com.anyemi.omrooms.Models.Location;
import com.anyemi.omrooms.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Location> locationList;
    ArrayList<Hotels> hotelsList;
    RecyclerView recyclerViewLocations, recyclerViewHotels;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //return inflater.inflate(R.layout.fragment_home, container, false);
//        return inflater.inflate(R.layout.fragment_home,null);
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewLocations = rootview.findViewById(R.id.locations_rv);
        recyclerViewHotels = rootview.findViewById(R.id.hotels_rv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewLocations.setLayoutManager(layoutManager);


        locationList = new ArrayList<Location>();
        locationList.add(new Location("Bangalore", R.drawable.ic_launcher_background));
        locationList.add(new Location("Bangalore", R.drawable.ic_launcher_background));
        locationList.add(new Location("Bangalore", R.drawable.ic_launcher_background));
        locationList.add(new Location("Bangalore", R.drawable.ic_launcher_background));
        locationList.add(new Location("Bangalore", R.drawable.ic_launcher_background));
        locationList.add(new Location("Bangalore", R.drawable.ic_launcher_background));

        LocationAdapter locationAdapter = new LocationAdapter(locationList, getContext());
        recyclerViewLocations.setAdapter(locationAdapter);

        hotelsList = new ArrayList<Hotels>();
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.ic_launcher_foreground));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.ic_launcher_foreground));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.ic_launcher_foreground));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.ic_launcher_foreground));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.ic_launcher_foreground));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.ic_launcher_foreground));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.ic_launcher_foreground));

        recyclerViewHotels.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        HotelAdapter hotelAdapter = new HotelAdapter(hotelsList, getContext());
        recyclerViewHotels.setAdapter(hotelAdapter);

        return rootview;
    }
}
