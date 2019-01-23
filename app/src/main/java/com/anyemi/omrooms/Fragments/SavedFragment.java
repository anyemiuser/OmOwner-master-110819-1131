package com.anyemi.omrooms.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyemi.omrooms.Adapters.LocationAdapter;
import com.anyemi.omrooms.Adapters.SavedHotelsAdapter;
import com.anyemi.omrooms.MainActivity;
import com.anyemi.omrooms.Models.Hotels;
import com.anyemi.omrooms.Models.Location;
import com.anyemi.omrooms.Models.SavedHotels;
import com.anyemi.omrooms.R;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    ArrayList<SavedHotels> savedHotelsList;
    RecyclerView recyclerViewSavedHotels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        return inflater.inflate(R.layout.fragment_saved,null);
       //return inflater.inflate(R.layout.fragment_saved, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);

        View rootview = inflater.inflate(R.layout.fragment_saved, container, false);
        recyclerViewSavedHotels = rootview.findViewById(R.id.saved_rv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSavedHotels.setLayoutManager(layoutManager);

        SavedHotelsAdapter savedHotelsAdapter = new SavedHotelsAdapter(savedHotelsList, getActivity());
        recyclerViewSavedHotels.setAdapter(savedHotelsAdapter);

        savedHotelsList = new ArrayList<SavedHotels>();
        savedHotelsList.add(new SavedHotels("Locations", R.drawable.ic_location_placeholder));
        savedHotelsList.add(new SavedHotels("Bangalore", R.drawable.ic_launcher_background));
        savedHotelsList.add(new SavedHotels("Bangalore", R.drawable.ic_launcher_background));
        savedHotelsList.add(new SavedHotels("Bangalore", R.drawable.ic_launcher_background));
        savedHotelsList.add(new SavedHotels("Bangalore", R.drawable.ic_launcher_background));
        savedHotelsList.add(new SavedHotels("Bangalore", R.drawable.ic_launcher_background));

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbarId);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

    }
}
