package com.anyemi.omrooms.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.SearchListAdapter;
import com.anyemi.omrooms.Model.HotelArea;
import com.anyemi.omrooms.Model.HotelAreaList;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout areaHistoryLayout, searchListLayout;

    private LinearLayout checkInLayout, checkOutLayOut, roomUserLayout;

    private RecyclerView recentSearchRv, allAreaRv, searchListRv;

    private List<HotelArea> hotelAreas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

        Toolbar toolbar = findViewById(R.id.material_search_toolbar);
        SearchView searchView = findViewById(R.id.search_material);//new SearchView(this);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
        startCheckInCheckOutForResult();
        //set focus to search view
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        searchListRv.setLayoutManager(layoutManager);
        searchListRv.setHasFixedSize(true);
        SearchListAdapter searchListAdapter = new SearchListAdapter(hotelAreas,this);
        searchListRv.setAdapter(searchListAdapter);

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
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getHotelList("SearchCiCd","visakhapatnam",newText," "," ","2")
                .enqueue(new Callback<HotelAreaList>() {
                    @Override
                    public void onResponse(Call<HotelAreaList> call, Response<HotelAreaList> response) {
                        if(response.isSuccessful()){

                            HotelAreaList hotelAreaList = response.body();
                            if(hotelAreaList.getMsg().equals("Successfully send")){
                                hotelAreas= hotelAreaList.getHotels();
//                        Log.e("List","ho"+hotelAreaList.getMsg()+hotelAreaList.getStatus()+hotelAreaList.getHotels().get(0).getHotelName());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
                                searchListRv.setLayoutManager(layoutManager);
                                searchListRv.setHasFixedSize(true);
                                SearchListAdapter searchListAdapter = new SearchListAdapter(hotelAreas,SearchActivity.this);
                                searchListRv.setAdapter(searchListAdapter);
                            }else if(hotelAreaList.getMsg().equals("No Records")){
                                Toast.makeText(SearchActivity.this, ""+hotelAreaList.getMsg()+" Found", Toast.LENGTH_SHORT).show();
                            }

                        }








                    }

                    @Override
                    public void onFailure(Call<HotelAreaList> call, Throwable t) {
                        Log.e("error",""+t.getMessage().toString());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();

            String cIn = data.getStringExtra("checkIn");
            String ee = data.getStringExtra("checkOut");
            int rooms = data.getIntExtra("rooms",1);
            int guests = data.getIntExtra("guests",1);
            if(ee != null){
                Toast.makeText(this, ""+cIn+ee+rooms+guests, Toast.LENGTH_SHORT).show();
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
                intent.putExtra("check",0);
                startActivityForResult(intent,1);
                break;
            case R.id.room_user_layout:
                intent.putExtra("check",2);
                startActivityForResult(intent,1);
                break;
        }
    }

    private void init() {
        areaHistoryLayout = findViewById(R.id.constraintLayout);

        searchListLayout = findViewById(R.id.constraintLayoutSearch);

        recentSearchRv = findViewById(R.id.recent_search_rv);
        allAreaRv = findViewById(R.id.all_area_rv);
        searchListRv = findViewById(R.id.search_list_rv);

        checkInLayout = findViewById(R.id.check_in_layout);
        checkInLayout.setOnClickListener(this);

        checkOutLayOut = findViewById(R.id.check_out_layout);
        checkOutLayOut.setOnClickListener(this);

        roomUserLayout = findViewById(R.id.room_user_layout);
        roomUserLayout.setOnClickListener(this);

    }
}
