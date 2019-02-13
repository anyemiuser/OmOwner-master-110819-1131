package com.anyemi.omrooms.UI;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.HotelListAdapter;
import com.anyemi.omrooms.Adapters.RoomTypeAdapter;
import com.anyemi.omrooms.Model.HotelList;
import com.anyemi.omrooms.Model.Hotels;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaHotelsActivity extends AppCompatActivity {

    private final String TAG_AREA_HOTEL = AreaHotelsActivity.class.getName();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView areaHotelsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_hotels);

        coordinatorLayout = findViewById(R.id.areaHotelConstraintL);
        areaHotelsRv = findViewById(R.id.hotel_list_rv);

        String area = getIntent().getStringExtra("area");

        Toolbar toolbar = findViewById(R.id.toolBar_area_hotel);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(area);
        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(area);
        }

        if(area != null){
            showAllHotelsUnderTheArea(area);
        }

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("This is a custom toast");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        Snackbar.make(coordinatorLayout, "Hello "+getString(R.string.app_name)+area,Snackbar.LENGTH_LONG).show();
    }

    private void showAllHotelsUnderTheArea(String area) {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getHotelListOnIndexAreaWise("SearchCiCdav","Andhra Pradesh","Visakhapatnam",
                "Visakhapatnam",area,"5","5","1","1")
                .enqueue(new Callback<HotelList>() {
                    @Override
                    public void onResponse(Call<HotelList> call, Response<HotelList> response) {
                        if(response.isSuccessful()){
                            HotelList hotelList = response.body();
                            if (hotelList != null && hotelList.getMsg().equals("Successfully send") && response.code() == 200) {
                                Log.e(TAG_AREA_HOTEL,""+hotelList.getHotels().get(0).getHotel_area()
                                +hotelList.getHotels().get(0).getHotel_name());

                                setHotelListRv(hotelList.getHotels());

                            }else {
                                Snackbar.make(coordinatorLayout, "Hello "+hotelList.getMsg()+response.message()+response.code(),Snackbar.LENGTH_LONG).show();
                            }
                        }else {
                            Snackbar.make(coordinatorLayout, "Hello "+response.message()+response.code(),Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HotelList> call, Throwable t) {

                    }
                });
        Toast.makeText(this, ""+area, Toast.LENGTH_SHORT).show();
    }

    private void setHotelListRv(List<Hotels> hotels) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(AreaHotelsActivity.this);
        areaHotelsRv.setLayoutManager(layoutManager);
        areaHotelsRv.setHasFixedSize(true);
        HotelListAdapter hotelListAdapter = new HotelListAdapter(hotels,AreaHotelsActivity.this);
        areaHotelsRv.setAdapter(hotelListAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
