package com.anyemi.omrooms.UI;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.SearchAreaCityAdapter;
import com.anyemi.omrooms.Adapters.SearchListAdapter;
import com.anyemi.omrooms.Model.HotelArea;
import com.anyemi.omrooms.Model.HotelAreaList;
import com.anyemi.omrooms.Model.RoomsGuest;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.ConverterUtil;
import com.anyemi.omrooms.Utils.RecyclerTouchListener;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout areaHistoryLayout, searchListLayout;

    private LinearLayout checkInLayout, checkOutLayOut, roomUserLayout;

    private RecyclerView recentSearchRv, allAreaRv, searchListRv, areaRv;

    private List<HotelArea> hotelAreas = new ArrayList<>();

    private List<HotelArea> searchedHotelAreas = new ArrayList<>();

    List<HotelArea> areaList = new ArrayList<>();

    private String searchText="a";

    private List<RoomsGuest> roomsGuests = new ArrayList<>();

    SharedPreferenceConfig sharedPreferenceConfig;
    private TextView checkInDate,checkOutDate,rooms,guests,nights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
        assignValue();

        Toolbar toolbar = findViewById(R.id.material_search_toolbar);
        SearchView searchView = findViewById(R.id.search_material);//new SearchView(this);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        if(sharedPreferenceConfig.readCheckInDate() ==null){
            startCheckInCheckOutForResult();
        }

        //set focus to search view
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        setSearchRecyclerView();

        searchListRv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), searchListRv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                HotelArea hotelArea = searchedHotelAreas.get(position);
                Toast.makeText(SearchActivity.this, ""+hotelArea.getHotelId()+hotelArea.getHotelName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this,HotelActivity.class);
                intent.putExtra("hotelId",hotelArea.getHotelId());
                intent.putExtra("hotelName",hotelArea.getHotelName());
                startActivity(intent);
//                finish();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        areaRv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), areaRv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                HotelArea hotelArea = searchedHotelAreas.get(position);
                Toast.makeText(SearchActivity.this, ""+hotelArea.getHotelarea()+hotelArea.getHotelName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this,AreaHotelsActivity.class);
                intent.putExtra("area",hotelArea.getHotelarea());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(SearchActivity.this, "submit: "+query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Toast.makeText(SearchActivity.this, "Text Change: "+newText, Toast.LENGTH_SHORT).show();
                if(newText.length()==0){
                    //close key board
                }
                if(newText.length()>=3){
                    areaHistoryLayout.setVisibility(View.GONE);
                    searchListLayout.setVisibility(View.VISIBLE);
                    callApiToHotelList(newText);
                }else {
                    areaHistoryLayout.setVisibility(View.VISIBLE);
                    searchListLayout.setVisibility(View.GONE);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(SearchActivity.this, "on Close: ", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }



    private void callApiToHotelList(String newText) {
        //no deed to call api if the text typed is same with the previous one.
        // and also if the typed text is similar to first 3 letter
        //else call api
        if((hotelAreas.size() != 0) && (newText.length()>=3) && newText.substring(0,3).toLowerCase().equals(searchText.toLowerCase())){
            searchedHotelAreas.clear();
            for(int i=0;i<hotelAreas.size();i++){
                if(hotelAreas.get(i).getHotelName().toLowerCase().contains(newText.toLowerCase())
                        || hotelAreas.get(i).getHotelarea().toLowerCase().contains(newText.toLowerCase())){

                    searchedHotelAreas.add(hotelAreas.get(i));
                }
            }
//            searchListRv.setAdapter(null);
            setAreaRecyclerView();
            setSearchRecyclerView();

        }else {
            OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
            omRoomApi.getHotelList("SearchCiCd",
                    sharedPreferenceConfig.readCityName(),
                    newText,
                    sharedPreferenceConfig.readCheckInDate(),
                    sharedPreferenceConfig.readCheckOutDate(),
                    String.valueOf(sharedPreferenceConfig.readNoOfRooms()))
                    .enqueue(new Callback<HotelAreaList>() {
                        @Override
                        public void onResponse(Call<HotelAreaList> call, Response<HotelAreaList> response) {
                            if(response.isSuccessful()){

                                HotelAreaList hotelAreaList = response.body();
                                if(hotelAreaList!= null && hotelAreaList.getMsg().equals("Successfully send")){
                                    searchedHotelAreas.clear();
                                    // master list (hotelAreas) contains all hotel and area list
                                    hotelAreas= hotelAreaList.getHotels();

                                    //a duplicate list of @hotelAreas is created to play with its object
                                    // especially to find areas and to do further search for the text typed with the length greater then 3
                                    searchedHotelAreas.addAll(hotelAreas);

                                    //set search recycler view ( searchHotelAreas contains complete list of hotels)
                                    setSearchRecyclerView();

                                    //get unique area list from searched list @searchHotelAreas
                                    setAreaRecyclerView();
//                        Log.e("List","ho"+hotelAreaList.getMsg()+hotelAreaList.getStatus()+hotelAreaList.getHotels().get(0).getHotelName());

                                    searchText=newText.substring(0,3);

                                }else if(hotelAreaList.getMsg().equals("No Records")){
                                    Toast.makeText(SearchActivity.this, ""+hotelAreaList.getMsg()+" Found", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(SearchActivity.this, ""+hotelAreaList.getMsg(), Toast.LENGTH_SHORT).show();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<HotelAreaList> call, Throwable t) {
                            Log.e("error",""+t.getMessage().toString());
                            Toast.makeText(SearchActivity.this, "Something Went Wrong"+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void setSearchRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        searchListRv.setLayoutManager(layoutManager);
        searchListRv.setHasFixedSize(true);
        SearchListAdapter searchListAdapter = new SearchListAdapter(searchedHotelAreas,SearchActivity.this);
        searchListRv.setAdapter(searchListAdapter);
    }

    private void setAreaRecyclerView() {
        areaList.clear();
        for (int i=0;i<searchedHotelAreas.size();i++){
            boolean isExist= true;
            for(int j=0;j<areaList.size();j++){

                if(areaList.get(j).getHotelarea().equals(searchedHotelAreas.get(i).getHotelarea())){
                    isExist = false;
//                    return;
                }
            }
            if(isExist){
                areaList.add(searchedHotelAreas.get(i));
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        areaRv.setLayoutManager(layoutManager);
        areaRv.setHasFixedSize(true);
        SearchAreaCityAdapter searchAreaCityAdapter = new SearchAreaCityAdapter(areaList,SearchActivity.this);
        areaRv.setAdapter(searchAreaCityAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();

            String cIn = data.getStringExtra("checkIn");
            String cOut = data.getStringExtra("checkOut");
            int rooms = data.getIntExtra("rooms",1);
            int guests = data.getIntExtra("guests",1);
//            String roomGS= data.getStringExtra("roomG");

            Bundle args = data.getBundleExtra("BUNDLE");
            roomsGuests = new ArrayList<>();
            if(args!=null){
                roomsGuests = (List<RoomsGuest>) args.getSerializable("ARRAYLIST");
            }

            sharedPreferenceConfig.writeCheckInDate(cIn);
            sharedPreferenceConfig.writeCheckOutDate(cOut);
            sharedPreferenceConfig.writeNoOfRooms(rooms);
            sharedPreferenceConfig.writeNoOfGuests(guests);

            String checkInDate = sharedPreferenceConfig.readCheckInDate();
            if(cOut != null){
                assignValue();
                Toast.makeText(this, ""+checkInDate+sharedPreferenceConfig.readCheckOutDate()+sharedPreferenceConfig.readNoOfRooms()+sharedPreferenceConfig.readNoOfGuests(), Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, ""+cIn+"he", Toast.LENGTH_SHORT).show();
        }

    }

    private void startCheckInCheckOutForResult() {
        Intent intent = new Intent(SearchActivity.this, CalenderActivity.class);
        startActivityForResult(intent,1);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(SearchActivity.this,CalenderActivity.class);
        switch (view.getId()){
            case R.id.check_in_layout:
                intent.putExtra("check",0);
                startActivityForResult(intent,1);
                break;
            case R.id.check_out_layout:
                intent.putExtra("check",1);
                startActivityForResult(intent,1);
                break;
            case R.id.room_user_layout:
                intent.putExtra("check",2);
                startActivityForResult(intent,1);
                break;
        }
    }

    private void init() {
        //master constraint layout
        areaHistoryLayout = findViewById(R.id.constraintLayout);

        recentSearchRv = findViewById(R.id.recent_search_rv);
        allAreaRv = findViewById(R.id.all_area_rv);

        //search constraint layout
        searchListLayout = findViewById(R.id.constraintLayoutSearch);
        searchListLayout.setVisibility(View.GONE);
        searchListRv = findViewById(R.id.search_list_rv);
        areaRv = findViewById(R.id.search_area_rv);

        checkInLayout = findViewById(R.id.check_in_layout);
        checkInLayout.setOnClickListener(this);

        checkOutLayOut = findViewById(R.id.check_out_layout);
        checkOutLayOut.setOnClickListener(this);

        roomUserLayout = findViewById(R.id.room_user_layout);
        roomUserLayout.setOnClickListener(this);

        sharedPreferenceConfig = new SharedPreferenceConfig(this);

        checkInDate = findViewById(R.id.check_in_date);
        checkOutDate = findViewById(R.id.check_out_date);
        nights = findViewById(R.id.no_of_nights);
        rooms = findViewById(R.id.no_of_rooms);
        guests = findViewById(R.id.no_of_user);



    }

    private void assignValue() {
        if(sharedPreferenceConfig.readCheckInDate() != null){
            checkInDate.setText(sharedPreferenceConfig.readCheckInDate());
            checkOutDate.setText(sharedPreferenceConfig.readCheckOutDate());
            String noNights = String.valueOf(ConverterUtil.noOfDays(sharedPreferenceConfig.readCheckInDate(),sharedPreferenceConfig.readCheckOutDate())).concat("N");
            nights.setText(noNights);
            int roomsCount= sharedPreferenceConfig.readNoOfRooms();
            int guestCount = 0;
            if(roomsGuests.size()>0){
                for(int i=0;i<roomsGuests.size();i++){
                    guestCount = guestCount + roomsGuests.get(i).getGuests();
                }
                roomsCount = roomsGuests.size();
            }
            if(roomsCount>0){
                sharedPreferenceConfig.writeNoOfRooms(roomsCount);

            }else {
                sharedPreferenceConfig.writeNoOfRooms(1);

            }
            rooms.setText(String.valueOf(sharedPreferenceConfig.readNoOfRooms()).concat(" Rooms"));

            if(guestCount>0){
                sharedPreferenceConfig.writeNoOfGuests(guestCount);
            }else {
                if(sharedPreferenceConfig.readNoOfGuests() == 0){
                    sharedPreferenceConfig.writeNoOfGuests(1);
                }

            }
            guests.setText(String.valueOf(sharedPreferenceConfig.readNoOfGuests()).concat(" Guests"));
        }

    }
}
