package com.anyemi.omrooms.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyemi.omrooms.Adapters.HotelAdapter;
import com.anyemi.omrooms.Adapters.LocationAdapter;
import com.anyemi.omrooms.Models.Hotels;
import com.anyemi.omrooms.Models.Location;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.SearchActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {

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
        locationList.add(new Location("Near By", R.drawable.ic_location_placeholder));
        locationList.add(new Location("Kailasagiri", R.drawable.location2));
        locationList.add(new Location("Simanchalam", R.drawable.location3));
        locationList.add(new Location("Rushikonda", R.drawable.location4));
        locationList.add(new Location("MVP Colony", R.drawable.location1));
        locationList.add(new Location("Jagadamba", R.drawable.location5));

        LocationAdapter locationAdapter = new LocationAdapter(locationList, getContext());
        recyclerViewLocations.setAdapter(locationAdapter);

        hotelsList = new ArrayList<Hotels>();
        hotelsList.add(new Hotels("The Fairfield Hotel", R.drawable.hotel1));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.hotel2));
        hotelsList.add(new Hotels("Hotel Novotel", R.drawable.hotel3));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.hotel5));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.hotel6));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.hotel2));
        hotelsList.add(new Hotels("The Hotel Prince", R.drawable.hotel1));

        recyclerViewHotels.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        HotelAdapter hotelAdapter = new HotelAdapter(hotelsList, getContext());
        recyclerViewHotels.setAdapter(hotelAdapter);

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
