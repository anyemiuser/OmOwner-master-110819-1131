package com.anyemi.omrooms.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anyemi.omrooms.Model.HotelDetails;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        String hotelId = getIntent().getStringExtra("hotelId");
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

                            Log.e("hotel area",""+hotelDetails.getHoteldetails().getHotel_area());
                            Log.e("room type",""+hotelDetails.getHoteldetails().getRoomdetails().get(0).getRoom_type());
                            Log.e("Response successful",""+response.toString());
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




}
