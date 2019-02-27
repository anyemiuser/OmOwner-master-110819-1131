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

import com.anyemi.omrooms.Adapters.SavedHotelsAdapter;
import com.anyemi.omrooms.Models.SavedHotels;
import com.anyemi.omrooms.R;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    ArrayList<SavedHotels> savedHotelsList;
    RecyclerView recyclerViewSavedHotels;
    //Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        return inflater.inflate(R.layout.fragment_saved,null);
        //return inflater.inflate(R.layout.fragment_saved, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);

        View rootview = inflater.inflate(R.layout.fragment_saved, container, false);
        recyclerViewSavedHotels = rootview.findViewById(R.id.saved_rv);
        //toolbar = rootview.findViewById(R.id.toolbar_saved);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSavedHotels.setLayoutManager(layoutManager);

        savedHotelsList = new ArrayList<SavedHotels>();
        savedHotelsList.add(new SavedHotels("The Park Hotel", R.drawable.park));
        savedHotelsList.add(new SavedHotels("Hotel Akshaya", R.drawable.akshaya));
        savedHotelsList.add(new SavedHotels("Hotel Chandra's", R.drawable.chandras));
        savedHotelsList.add(new SavedHotels("Sai priya Beach", R.drawable.saiprlya));
        savedHotelsList.add(new SavedHotels("Hotel Fortune", R.drawable.fortune));
        savedHotelsList.add(new SavedHotels("The Port Hotel", R.drawable.port));

        SavedHotelsAdapter savedHotelsAdapter = new SavedHotelsAdapter(savedHotelsList, getActivity());
        recyclerViewSavedHotels.setAdapter(savedHotelsAdapter);


//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//            }
//        });

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
