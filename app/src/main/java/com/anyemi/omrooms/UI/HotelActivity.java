package com.anyemi.omrooms.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.FacilityListAdapter;
import com.anyemi.omrooms.Adapters.RoomTypeAdapter;
import com.anyemi.omrooms.Model.HotelAndRoomDetail;
import com.anyemi.omrooms.Model.HotelDetails;
import com.anyemi.omrooms.Model.RoomDetails;
import com.anyemi.omrooms.Model.RoomFacility;
import com.anyemi.omrooms.Model.RoomsGuest;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.ConstantFields;
import com.anyemi.omrooms.Utils.ConverterUtil;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelActivity extends AppCompatActivity implements ConstantFields, View.OnClickListener {

    private static final String TAG_HOTEL = HotelActivity.class.getName();
    private ImageView hotelImage, locImage;
    private TextView hotelName,nearBy,rating,ratingTitle,noOfRating;
    private RecyclerView facilityRv,roomTypeRv;
    private Button bookRoom;

    private List<String> facilityList = new ArrayList<>();

    private LinearLayout checkInLayout, checkOutLayOut, roomUserLayout;
    private TextView checkInDate,checkOutDate,rooms,guests,nights;
    SharedPreferenceConfig sharedPreferenceConfig;
    private List<RoomsGuest> roomsGuests = new ArrayList<>();

    private String hotelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);



        init();
        assignValue(null,null);

        Intent intent =getIntent();
        hotelId = intent.getStringExtra("hotelId");
        String hotelName = intent.getStringExtra("hotelName");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(hotelName);
        }

        if(hotelId != null){
            showHotelDetails(hotelId);
        }
    }



    private void showHotelDetails(String hotelId) {
        Toast.makeText(this, ""+hotelId, Toast.LENGTH_SHORT).show();

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getHotelDetails("HotelDetails",hotelId,"2019-01-12","2019-01-16","2")
                .enqueue(new Callback<HotelDetails>() {
                    @Override
                    public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
                        if(response.isSuccessful()){
                            HotelDetails hotelDetails = response.body();

                            if (hotelDetails != null && hotelDetails.getMsg().equals("Successfully send") && response.code() == 200) {
                                List<RoomFacility> facilities = ConverterUtil.checkFacilityAvailable(hotelDetails.getHoteldetails().getRoomdetails());
                                setDataToUI(hotelDetails.getHoteldetails());
                                Log.e(TAG_HOTEL,""+hotelDetails.getHoteldetails().getHotel_name());
                                setFacilityRv(facilities);
                                setRoomTypeRv(hotelDetails.getHoteldetails().getRoomdetails());
                            }else {
                                Toast.makeText(HotelActivity.this, ""+hotelDetails.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Log.e("Response not successful",""+response.toString());
                        }


                    }

                    @Override
                    public void onFailure(Call<HotelDetails> call, Throwable t) {
                        Log.e("error",""+t.getMessage());

                    }
                });
    }




    private void setRoomTypeRv(List<RoomDetails> roomdetails) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(HotelActivity.this);
        roomTypeRv.setLayoutManager(layoutManager);
        roomTypeRv.setHasFixedSize(true);
        RoomTypeAdapter roomTypeAdapter = new RoomTypeAdapter(roomdetails,HotelActivity.this);
        roomTypeRv.setAdapter(roomTypeAdapter);
    }

    private void setFacilityRv(List<RoomFacility> facilities) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(HotelActivity.this, LinearLayoutManager.HORIZONTAL,false );
        facilityRv.setLayoutManager(layoutManager);
        facilityRv.setHasFixedSize(true);
        FacilityListAdapter facilityListAdapter = new FacilityListAdapter(facilities,HotelActivity.this);
        facilityRv.setAdapter(facilityListAdapter);

    }

    private void setDataToUI(HotelAndRoomDetail hotel) {

        Glide.with(this)
                .load(hotel.getHotel_image_url())
                .error(R.drawable.ic_location_city)
                // read original from cache (if present) otherwise download it and decode it
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(hotelImage);
        hotelName.setText(hotel.getHotel_name());
        String nearByPlace = hotel.getHotel_area()+", "+hotel.getHotel_city()+", "+hotel.getHotel_district();
        nearBy.setText(nearByPlace);
        ratingTitle.setText(hotel.getHotel_rating());
        noOfRating.setText(hotel.getHotel_no_of_ratings());

    }

    private void init() {
//        private ImageView hotelImage;
//        private TextView hotelName,nearBy,rating,ratingTitle,noOfRating;
//        private RecyclerView facilityRv,roomTypeRv;
//        private Button bookRoom;
        hotelImage = findViewById(R.id.hotel_image);
        locImage = findViewById(R.id.location_placeholder);
        hotelName = findViewById(R.id.hotel_name_h);
        nearBy = findViewById(R.id.hotel_address);
        rating = findViewById(R.id.rating);
        ratingTitle = findViewById(R.id.rating_title);
        noOfRating = findViewById(R.id.no_of_ratings);
        bookRoom = findViewById(R.id.book_room);

        facilityRv = findViewById(R.id.facility_rv);
        roomTypeRv = findViewById(R.id.room_types_rv);

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

    @Override
    public boolean onSupportNavigateUp() {
        Intent resultIntent = getIntent();
        resultIntent.putExtra("checkIn",sharedPreferenceConfig.readCheckInDate());
        resultIntent.putExtra("checkOut",sharedPreferenceConfig.readCheckOutDate());
        resultIntent.putExtra("rooms",sharedPreferenceConfig.readNoOfRooms());
        resultIntent.putExtra("guests",sharedPreferenceConfig.readNoOfGuests());
//        ArrayList<Object> object = new ArrayList<Object>();
//        Intent intent = new Intent(Current.class, Transfer.class);
        if(roomsGuests.size()>0){
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable)roomsGuests);
            resultIntent.putExtra("BUNDLE",args);
        }

//        startActivity(intent);
//        resultIntent.putExtra("roomG",new Gson().toJson(roomsGuests));
        setResult(Activity.RESULT_OK,resultIntent);
//        roomsGuests.clear();
        onBackPressed();
        return true;
    }

    private void assignValue(String cIn, String cOut) {
        if(sharedPreferenceConfig.readCheckInDate() != null){

            boolean isChanged = false;
            if(cIn != null && cOut != null){

                if(cIn.equals(sharedPreferenceConfig.readCheckInDate())
                        && cOut.equals(sharedPreferenceConfig.readCheckOutDate())){
                    Log.e(TAG_HOTEL," date NOT CHANGED");
                }else {
                    Log.e(TAG_HOTEL,"date CHANGED");
                    sharedPreferenceConfig.writeCheckInDate(cIn);
                    sharedPreferenceConfig.writeCheckOutDate(cOut);
                    isChanged = true;
                }

            }
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
                if(roomsCount != sharedPreferenceConfig.readNoOfRooms()){
                    Log.e(TAG_HOTEL,"Room changed");
                    isChanged = true;
                }else {
                    Log.e(TAG_HOTEL,"Room  Not changed");
                }
                sharedPreferenceConfig.writeNoOfRooms(roomsCount);

            }else {
                sharedPreferenceConfig.writeNoOfRooms(1);

            }
            rooms.setText(String.valueOf(sharedPreferenceConfig.readNoOfRooms()).concat(" Rooms"));

            if(guestCount>0){
                if(guestCount != sharedPreferenceConfig.readNoOfGuests()){
                    Log.e(TAG_HOTEL,"Guest changed");
                    isChanged = true;
                }else {
                    Log.e(TAG_HOTEL,"Guest Not changed");
                }


                sharedPreferenceConfig.writeNoOfGuests(guestCount);
            }else {
                if(sharedPreferenceConfig.readNoOfGuests() == 0){
                    sharedPreferenceConfig.writeNoOfGuests(1);
                }

            }
            guests.setText(String.valueOf(sharedPreferenceConfig.readNoOfGuests()).concat(" Guests"));

            if(isChanged){
                showHotelDetails(hotelId);
                Log.e(TAG_HOTEL,"Queried resh data: showHotelDetails(hotelId)");
                isChanged = false;
            }
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(HotelActivity.this,CalenderActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();

            String cIn = null;
            String cOut = null;
            if (data != null) {
                cIn = data.getStringExtra("checkIn");
                cOut = data.getStringExtra("checkOut");
            }

            Bundle args = data.getBundleExtra("BUNDLE");
            roomsGuests = new ArrayList<>();
            if(args!=null){
                roomsGuests = (List<RoomsGuest>) args.getSerializable("ARRAYLIST");
            }


            String checkInDate = sharedPreferenceConfig.readCheckInDate();
            if(cOut != null){
                assignValue(cIn,cOut);
                Toast.makeText(this, ""+checkInDate+sharedPreferenceConfig.readCheckOutDate()+sharedPreferenceConfig.readNoOfRooms()+sharedPreferenceConfig.readNoOfGuests(), Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, ""+cIn+"he", Toast.LENGTH_SHORT).show();
        }

    }
}
