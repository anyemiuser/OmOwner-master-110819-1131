package com.anyemi.omrooms.UI;

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
import com.anyemi.omrooms.Model.HotelDetails;
import com.anyemi.omrooms.Model.RoomDetails;
import com.anyemi.omrooms.Model.RoomFacility;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.ConstantFields;
import com.anyemi.omrooms.Utils.ConverterUtil;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelActivity extends AppCompatActivity implements ConstantFields {

    private ImageView hotelImage, locImage;
    private TextView hotelName,nearBy,rating,ratingTitle,noOfRating;
    private RecyclerView facilityRv,roomTypeRv;
    private Button bookRoom;

    private List<String> facilityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
//            actionbar.setTitle(category);
        }

        init();

        String hotelId = getIntent().getStringExtra("hotelId");
        if(hotelId != null){
            showHotelDetails(hotelId);
        }
    }



    private void showHotelDetails(String hotelId) {
        Toast.makeText(this, ""+hotelId, Toast.LENGTH_SHORT).show();

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getHotelDetails("HotelDetails","1")
                .enqueue(new Callback<HotelDetails>() {
                    @Override
                    public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
                        if(response.isSuccessful()){
                            HotelDetails hotelDetails = response.body();

                            Log.e("hotel area",""+hotelDetails.getHoteldetails().getHotel_area());
                            Log.e("room type",""+hotelDetails.getHoteldetails().getRoomdetails().get(0).getRoom_type());
                            Log.e("Response successful",""+response.toString());
                            List<RoomFacility> facilities = ConverterUtil.checkFacilityAvailable(hotelDetails.getHoteldetails().getRoomdetails());
                            setFacilityRv(facilities);
                            setRoomTypeRv(hotelDetails.getHoteldetails().getRoomdetails());
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

    private void init() {
//        private ImageView hotelImage;
//        private TextView hotelName,nearBy,rating,ratingTitle,noOfRating;
//        private RecyclerView facilityRv,roomTypeRv;
//        private Button bookRoom;
        hotelImage = findViewById(R.id.hotel_image);
        locImage = findViewById(R.id.location_image);
        hotelName = findViewById(R.id.hotels_name);
        nearBy = findViewById(R.id.near_by_area_text);
        rating = findViewById(R.id.rating);
        ratingTitle = findViewById(R.id.rating_title);
        noOfRating = findViewById(R.id.no_of_rating);
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
