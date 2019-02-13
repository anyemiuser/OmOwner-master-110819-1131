package com.anyemi.omrooms.UI;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.FacilityListAdapter;
import com.anyemi.omrooms.Adapters.RoomTypeAdapter;
import com.anyemi.omrooms.Adapters.SearchListAdapter;
import com.anyemi.omrooms.Model.HotelAndRoomDetail;
import com.anyemi.omrooms.Model.HotelDetails;
import com.anyemi.omrooms.Model.RoomDetails;
import com.anyemi.omrooms.Model.RoomFacility;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.ConstantFields;
import com.anyemi.omrooms.Utils.ConverterUtil;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelActivity extends AppCompatActivity implements ConstantFields {

    private static final String TAG_HOTEL = HotelActivity.class.getName();
    private ImageView hotelImage, locImage;
    private TextView hotelName,nearBy,rating,ratingTitle,noOfRating;
    private RecyclerView facilityRv,roomTypeRv;
    private Button bookRoom;

    private List<String> facilityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);



        init();
        Intent intent =getIntent();
        String hotelId = intent.getStringExtra("hotelId");
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
        omRoomApi.getHotelDetails("HotelDetails",hotelId)
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
//                            Log.e("hotel area",""+hotelDetails.getHoteldetails().getHotel_area());
//                            Log.e("room type",""+hotelDetails.getHoteldetails().getRoomdetails().get(0).getRoom_type());
//                            Log.e("Response successful",""+response.toString());

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

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
