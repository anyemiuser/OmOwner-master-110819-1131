package com.anyemi.omrooms.Fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.HotelAdapter;
import com.anyemi.omrooms.Adapters.LocationAdapter;
import com.anyemi.omrooms.Model.AreaUnderCity;
import com.anyemi.omrooms.Model.CityDistrictState;
import com.anyemi.omrooms.Model.CityList;
import com.anyemi.omrooms.Model.SavedHotelViewModel;
import com.anyemi.omrooms.Model.Top10Hotel;
import com.anyemi.omrooms.Model.TopHotels;
import com.anyemi.omrooms.Model.Location;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.AreaHotelsActivity;
import com.anyemi.omrooms.UI.HotelActivity;
import com.anyemi.omrooms.UI.SearchActivity;
import com.anyemi.omrooms.Utils.RecyclerTouchListener;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.anyemi.omrooms.db.RoomBooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Spinner citySpinner;
    private static List<String> cityList = new ArrayList<>();
    private static List<CityDistrictState> cityDistrictStates = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private SavedHotelViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //return inflater.inflate(R.layout.fragment_home, container, false);
//        return inflater.inflate(R.layout.fragment_home,null);
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferenceConfig = new SharedPreferenceConfig(getActivity());
        notificationButton = rootview.findViewById(R.id.notification_button);
        coordinatorLayout = rootview.findViewById(R.id.home_coordinatorLyt);

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
        citySpinner = view.findViewById(R.id.city_spinner);
        viewModel = ViewModelProviders.of(this).get(SavedHotelViewModel.class);

//        intializeHome();
        getCityList();
//        setUpSpinner();




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



            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));



    }



    private void getCityList() {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getCityList("CityList").enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, Response<CityList> response) {
                if(response.isSuccessful()){
                    CityList cityListDetails = response.body();
                    if(cityListDetails != null && cityListDetails.getMsg().equals("Successfully send") && response.code() == 200) {
                        cityDistrictStates = cityListDetails.getCity();
                        for (int i=0; i<cityDistrictStates.size();i++){
                            if(!cityList.contains(cityDistrictStates.get(i).getCity())){
                                cityList.add(cityDistrictStates.get(i).getCity());
                            }

                        }
                        setUpSpinner();
                    }else {
                        Toast.makeText(getActivity(), ""+cityListDetails.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CityList> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
    }

    private void setUpSpinner() {
        // Select City to show customer
        if(!cityList.contains("Select City")){
            cityList.add(0,"Select City");
        }

//        cityList.add("Hyderabad");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,cityList);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // If previously customer saved location/city then set spinned position to that city
        //else show "Select City" first
        citySpinner.setAdapter(arrayAdapter);
        if(sharedPreferenceConfig.readCityName() != null){
            int pos = arrayAdapter.getPosition(sharedPreferenceConfig.readCityName());
            citySpinner.setSelection(pos);
        }

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                //for the first time show select city and automaticilly run the code
                // It wont do any thing if " Select City" choosed by customer
                if(parent.getSelectedItem().toString().equals("Select City")){
                    Toast.makeText(getActivity(), "Select City", Toast.LENGTH_SHORT).show();
                    //For the first time after installation customer choose city from spinner
                }else if(sharedPreferenceConfig.readCityName()== null){

                    Toast.makeText(getActivity(), ""+parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                    CityDistrictState districtState = cityDistrictStates.get(i-1);

                    sharedPreferenceConfig.writeCityName(parent.getSelectedItem().toString());
                    sharedPreferenceConfig.writeStateName(districtState.getState());
                    sharedPreferenceConfig.writeDistrictName(districtState.getDistrict());

                    getAreaUnderCity(sharedPreferenceConfig.readCityName());

                    getTop10HotelUnderCity(sharedPreferenceConfig.readCityName());
                    cityList.remove("Select City");
                    //For Next time onwords if the last selected city get changed by user
                    //run the below code
                }else if(!sharedPreferenceConfig.readCityName().equals(parent.getSelectedItem().toString())){



                    CityDistrictState districtState = cityDistrictStates.get(i-1);


                    sharedPreferenceConfig.writeCityName(parent.getSelectedItem().toString());
                    sharedPreferenceConfig.writeStateName(districtState.getState());
                    sharedPreferenceConfig.writeDistrictName(districtState.getDistrict());

                    getAreaUnderCity(sharedPreferenceConfig.readCityName());

                    getTop10HotelUnderCity(sharedPreferenceConfig.readCityName());
                    cityList.remove("Select City");
                    //If the last time selected city  and present selected city are same
                    //then run below code
                }else {
                    if(locationList.size()== 0){
                        getAreaUnderCity(sharedPreferenceConfig.readCityName());
                    }
                    if(hotelsList.size() == 0){
                         getTop10HotelUnderCity(sharedPreferenceConfig.readCityName());
                    }
                    cityList.remove("Select City");
                        Toast.makeText(getActivity(), "You Are Searching in "+sharedPreferenceConfig.readCityName(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getTop10HotelUnderCity(String cityName) {
        hotelsList.clear();
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getTop10HotelInCity("RocomondedHotel",sharedPreferenceConfig.readStateName(),sharedPreferenceConfig.readDistrictName(),cityName)
                .enqueue(new Callback<TopHotels>() {
                    @Override
                    public void onResponse(Call<TopHotels> call, Response<TopHotels> response) {
                        if(response.isSuccessful()){
                            TopHotels topHotels = response.body();
                            if(topHotels != null && topHotels.getMsg().equals("Successfully send") && response.code() == 200) {

                                hotelsList = topHotels.getHotels();
                                Log.e(HOME_TAG, "" + topHotels.getMsg());

                                setHotelRV();

                            }
                            else{
                                Log.v(HOME_TAG, "message: "+topHotels.getMsg()+response.code());
                        }
                        }
                        else{
                            Log.v(HOME_TAG, "response :"+response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<TopHotels> call, Throwable t) {
                        Log.v(HOME_TAG, "error :"+t.toString());
                    }
                });

    }

    private void setHotelRV() {

        new findAsyncTask(viewModel).execute();



    }

    private void getAreaUnderCity(String cityName) {
        progressBarArea.setVisibility(View.VISIBLE);
        locationList.clear();
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
                if(sharedPreferenceConfig.readCityName() != null){
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }else {
                    Snackbar.make(coordinatorLayout,"Select City before you search",Snackbar.LENGTH_LONG).show();
                }

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

    @SuppressLint("StaticFieldLeak")
    private class findAsyncTask extends AsyncTask<Void,Void,Void> {

        private SavedHotelViewModel viewModel;
        findAsyncTask(SavedHotelViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(Top10Hotel hotel: hotelsList){
                boolean ss = viewModel.whetherSaved(hotel.getHotel_id());
                hotel.setSaved(ss);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerViewHotels.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            HotelAdapter hotelAdapter = new HotelAdapter(hotelsList, getActivity(),viewModel);
            recyclerViewHotels.setAdapter(hotelAdapter);
        }
    }
}
