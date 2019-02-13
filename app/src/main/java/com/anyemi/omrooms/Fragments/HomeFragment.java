package com.anyemi.omrooms.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.HotelAdapter;
import com.anyemi.omrooms.Adapters.LocationAdapter;
import com.anyemi.omrooms.Model.AreaUnderCity;
import com.anyemi.omrooms.Model.HotelArea;
import com.anyemi.omrooms.Model.HotelDetails;
import com.anyemi.omrooms.Model.Top10Hotel;
import com.anyemi.omrooms.Model.TopHotels;
import com.anyemi.omrooms.Models.Hotels;
import com.anyemi.omrooms.Model.Location;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.AreaHotelsActivity;
import com.anyemi.omrooms.UI.HotelActivity;
import com.anyemi.omrooms.UI.SearchActivity;
import com.anyemi.omrooms.Utils.RecyclerTouchListener;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private final String HOME_TAG = HomeFragment.class.getName();

    private static List<Location> locationList = new ArrayList<>();
    private static List<Top10Hotel> hotelsList = new ArrayList<>();
    RecyclerView recyclerViewLocations, recyclerViewHotels;
    LocationAdapter locationAdapter;
    SharedPreferenceConfig sharedPreferenceConfig;
    private ProgressBar progressBarArea;
    ImageButton notificationButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //return inflater.inflate(R.layout.fragment_home, container, false);
//        return inflater.inflate(R.layout.fragment_home,null);
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        notificationButton = rootview.findViewById(R.id.notification_button);


        notificationButton.setOnClickListener(this);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(this);


        progressBarArea = view.findViewById(R.id.area_progressBar);
        recyclerViewLocations = view.findViewById(R.id.locations_rv);
        recyclerViewHotels = view.findViewById(R.id.hotels_rv);


//        searchListRv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), searchListRv, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                HotelArea hotelArea = searchedHotelAreas.get(position);
//                Toast.makeText(SearchActivity.this, ""+hotelArea.getHotelId()+hotelArea.getHotelName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(SearchActivity.this, HotelActivity.class);
//                intent.putExtra("hotelId",hotelArea.getHotelId());
//                startActivity(intent);
////                finish();
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
        setHotelRV();
        setLocationRV();

        recyclerViewLocations.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewLocations, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Location location = locationList.get(position);
                Intent intent = new Intent(getActivity(), AreaHotelsActivity.class);
                intent.putExtra("area",location.getHotel_area());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerViewHotels.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewHotels, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Top10Hotel top10Hotel = hotelsList.get(position);
                Intent intent = new Intent(getActivity(),HotelActivity.class);
                intent.putExtra("hotelId",top10Hotel.getHotel_id());
                intent.putExtra("hotelName",top10Hotel.getHotel_name());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        if(locationList.size()== 0){
            getAreaUnderCity(sharedPreferenceConfig.readCityName());
        }
        if(hotelsList.size() == 0){
            getTop10HotelUnderCity(sharedPreferenceConfig.readCityName());
        }


    }

    private void getTop10HotelUnderCity(String cityName) {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getTop10HotelInCity("RocomondedHotel","Andhra Pradesh","visakhapatnam",cityName)
                .enqueue(new Callback<TopHotels>() {
                    @Override
                    public void onResponse(Call<TopHotels> call, Response<TopHotels> response) {
                        if(response.isSuccessful()){
                            TopHotels topHotels = response.body();
                            if(topHotels != null && topHotels.getMsg().equals("Successfully send") && response.code() == 200) {

                                hotelsList = topHotels.getHotels();

                                setHotelRV();


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TopHotels> call, Throwable t) {

                    }
                });

    }

    private void setHotelRV() {

        recyclerViewHotels.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        HotelAdapter hotelAdapter = new HotelAdapter(hotelsList, getActivity());
        recyclerViewHotels.setAdapter(hotelAdapter);

    }

    private void getAreaUnderCity(String cityName) {
        progressBarArea.setVisibility(View.VISIBLE);
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getAreasInCity("AllArea",cityName)
                .enqueue(new Callback<AreaUnderCity>() {
                    @Override
                    public void onResponse(Call<AreaUnderCity> call, Response<AreaUnderCity> response) {
                        if(response.isSuccessful()){

                            AreaUnderCity areaUnderCity = response.body();

                            if (areaUnderCity != null && areaUnderCity.getMsg().equals("Successfully send") && response.code() == 200) {
                                Log.e(HOME_TAG, "" + areaUnderCity.getMsg());
                                locationList = areaUnderCity.getHotels();
                                setLocationRV();


                            }else {
                                if (areaUnderCity != null) {
                                    Toast.makeText(getActivity(), ""+areaUnderCity.getMsg()+": "+response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            progressBarArea.setVisibility(View.GONE);
//                            locationAdapter.notifyDataSetChanged();

                        }else {
                            progressBarArea.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), ""+response.message()+" : "+response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AreaUnderCity> call, Throwable t) {
                        progressBarArea.setVisibility(View.GONE);
                        Log.e(HOME_TAG,""+t.getMessage());
                    }
                });
    }

    private void setLocationRV() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewLocations.setLayoutManager(layoutManager);
        locationAdapter = new LocationAdapter(locationList, getContext());
        recyclerViewLocations.setAdapter(locationAdapter);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.toolbar:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.notification_button:
                fragment = new NotificationsFragment();
                openNotificationFragment(fragment);
                break;
        }
    }

    public void openNotificationFragment(Fragment notifFrag){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, notifFrag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
