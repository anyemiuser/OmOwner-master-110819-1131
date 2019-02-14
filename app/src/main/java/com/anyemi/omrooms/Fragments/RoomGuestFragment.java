package com.anyemi.omrooms.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyemi.omrooms.Adapters.RoomGuestAdapter;
import com.anyemi.omrooms.Model.RoomsGuest;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.CalenderActivity;

import java.util.ArrayList;

public class RoomGuestFragment extends Fragment {


    RecyclerView recyclerViewRoomsGuest;

    private OnFragmentInteractionListener mListener;
    public RoomGuestFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //return inflater.inflate(R.layout.room_guest_fragment, container, false);

        View rootView = inflater.inflate(R.layout.room_guest_fragment, container,false);
        recyclerViewRoomsGuest = rootView.findViewById(R.id.rooms_guest_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewRoomsGuest.setLayoutManager(layoutManager);

//        roomsGuests = new ArrayList<RoomsGuest>();
        if(CalenderActivity.roomsGuests.size() == 0){
            CalenderActivity.roomsGuests.add(new RoomsGuest(1,2));
        }

//        roomsGuests.add(new RoomsGuest(1,3));

        RoomGuestAdapter roomGuestAdapter = new RoomGuestAdapter(CalenderActivity.roomsGuests, getActivity());
        recyclerViewRoomsGuest.setAdapter(roomGuestAdapter);
        return rootView;
    }





    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        CalenderActivity.roomsGuests.add(new RoomsGuest(1,2));
//        CalenderActivity.roomsGuests.add(new RoomsGuest(1,3));
        CalenderActivity.rooms=1;
        CalenderActivity.guests = 2;



    }
    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {

            if (mListener != null ) {
                mListener.onFragmentInteraction("Select Room & Guest");
            }
        }else{
            // fragment is not visible
        }
    }
}
