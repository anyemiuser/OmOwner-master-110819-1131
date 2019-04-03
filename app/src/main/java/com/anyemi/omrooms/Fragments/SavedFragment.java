package com.anyemi.omrooms.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyemi.omrooms.Adapters.SavedHotelsAdapter;
import com.anyemi.omrooms.Model.SavedHotelViewModel;
import com.anyemi.omrooms.Models.SavedHotels;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.db.RoomBooking;

import java.util.ArrayList;
import java.util.Objects;

public class SavedFragment extends Fragment {

    private OnFragmentBackListner changeListner;

    ArrayList<SavedHotels> savedHotelsList;
    RecyclerView recyclerViewSavedHotels;
    private SavedHotelViewModel viewModel;
    //Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        return inflater.inflate(R.layout.fragment_saved,null);
        //return inflater.inflate(R.layout.fragment_saved, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);

        View rootview = inflater.inflate(R.layout.fragment_saved, container, false);
        recyclerViewSavedHotels = rootview.findViewById(R.id.saved_rv);
        Toolbar toolbar = rootview.findViewById(R.id.toolbar_saved);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        ActionBar actionbar = ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle("Saved Hotel");
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSavedHotels.setLayoutManager(layoutManager);

//        savedHotelsList = new ArrayList<SavedHotels>();
//        savedHotelsList.add(new SavedHotels("The Park Hotel", R.drawable.park));
//        savedHotelsList.add(new SavedHotels("Hotel Akshaya", R.drawable.akshaya));
//        savedHotelsList.add(new SavedHotels("Hotel Chandra's", R.drawable.chandras));
//        savedHotelsList.add(new SavedHotels("Sai priya Beach", R.drawable.saiprlya));
//        savedHotelsList.add(new SavedHotels("Hotel Fortune", R.drawable.fortune));
//        savedHotelsList.add(new SavedHotels("The Port Hotel", R.drawable.port));
//
//        SavedHotelsAdapter savedHotelsAdapter = new SavedHotelsAdapter(savedHotelsList, getActivity());
//        recyclerViewSavedHotels.setAdapter(savedHotelsAdapter);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeListner.onFragmentChange();
            }
        });

        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(SavedHotelViewModel.class);
        SavedHotelsAdapter adapter = new SavedHotelsAdapter(getActivity(),viewModel);
        viewModel.getSavedHotelList().observe(this, new Observer<PagedList<RoomBooking>>() {
            @Override
            public void onChanged(@Nullable PagedList<RoomBooking> hotelLists) {
                if(hotelLists != null){
                    adapter.submitList(hotelLists);
                    recyclerViewSavedHotels.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            changeListner = (OnFragmentBackListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentBackListner{
        public void onFragmentChange();
    }

}
