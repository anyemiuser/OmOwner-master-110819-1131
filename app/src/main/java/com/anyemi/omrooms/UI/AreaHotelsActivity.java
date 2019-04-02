package com.anyemi.omrooms.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.HotelAdapter;
import com.anyemi.omrooms.Adapters.HotelListAdapter;
import com.anyemi.omrooms.Helper.RGuest;
import com.anyemi.omrooms.Model.HotelList;
import com.anyemi.omrooms.Model.Hotels;
import com.anyemi.omrooms.Model.RoomsGuest;
import com.anyemi.omrooms.Model.SavedHotelViewModel;
import com.anyemi.omrooms.Model.Top10Hotel;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.ConverterUtil;
import com.anyemi.omrooms.Utils.RecyclerTouchListener;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaHotelsActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG_AREA_HOTEL = AreaHotelsActivity.class.getName();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView areaHotelsRv;
    private List<Hotels> hotelListDetails = new ArrayList<>();

    private LinearLayout checkInLayout, checkOutLayOut, roomUserLayout;
    private TextView checkInDate,checkOutDate,rooms,guests,nights;
    SharedPreferenceConfig sharedPreferenceConfig;
//    private List<RoomsGuest> roomsGuests = new ArrayList<>();

    private String area = null;
    private SavedHotelViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_hotels);

        viewModel = ViewModelProviders.of(this).get(SavedHotelViewModel.class);
        Log.e(TAG_AREA_HOTEL,"On Create");

        init();
        assignValue(null,null);

        coordinatorLayout = findViewById(R.id.areaHotelConstraintL);
        areaHotelsRv = findViewById(R.id.hotel_list_rv);

        area = getIntent().getStringExtra("area");

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

        areaHotelsRv.addOnItemTouchListener(new RecyclerTouchListener(this, areaHotelsRv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                findPosition(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));




        //Custome Toast
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.custom_toast,
//                (ViewGroup) findViewById(R.id.custom_toast_container));
//
//        TextView text = (TextView) layout.findViewById(R.id.text);
//        text.setText("This is a custom toast");
//
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();

//        Snackbar.make(coordinatorLayout, "Hello "+getString(R.string.app_name)+area,Snackbar.LENGTH_LONG).show();
    }

    private void findPosition(int position) {
        RecyclerView.ViewHolder holder = areaHotelsRv.findViewHolderForAdapterPosition(position);
        if (holder != null) {
//            ImageView imageView = holder.itemView.findViewById(R.id.saved_symbol);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(AreaHotelsActivity.this, "saved clicked", Toast.LENGTH_SHORT).show();
//
//                }
//            });
            LinearLayout layout = holder.itemView.findViewById(R.id.linear_saved_item);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Hotels hotel = hotelListDetails.get(position);
                    Intent intent = new Intent(AreaHotelsActivity.this,HotelActivity.class);
                    intent.putExtra("hotelId",hotel.getHotel_id());
                    intent.putExtra("hotelName",hotel.getHotel_name());
                    startActivityForResult(intent,1);
                    Toast.makeText(AreaHotelsActivity.this, "Layout clicked", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void init() {
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

    private void assignValue(String cIn, String cOut) {
        if(sharedPreferenceConfig.readCheckInDate() != null){

            if(RGuest.roomsGuests.size() == 0){
                RGuest.roomsGuests.add(new RoomsGuest(1,2));
                sharedPreferenceConfig.writeNoOfRooms(1);
                sharedPreferenceConfig.writeNoOfGuests(2);

            }

            boolean isChanged = false;
            if(cIn != null && cOut != null){

                if(cIn.equals(sharedPreferenceConfig.readCheckInDate())
                        && cOut.equals(sharedPreferenceConfig.readCheckOutDate())){
                    Log.e(TAG_AREA_HOTEL," date NOT CHANGED");
                }else {
                    Log.e(TAG_AREA_HOTEL,"date CHANGED");
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
            if(RGuest.roomsGuests.size()>0){
                for(int i=0;i<RGuest.roomsGuests.size();i++){
                    guestCount = guestCount + RGuest.roomsGuests.get(i).getGuests();
                }
                roomsCount = RGuest.roomsGuests.size();
            }
            if(roomsCount>0){
                if(roomsCount != sharedPreferenceConfig.readNoOfRooms()){
                    Log.e(TAG_AREA_HOTEL,"Room changed");
                    isChanged = true;
                }else {
                    Log.e(TAG_AREA_HOTEL,"Room  Not changed");
                }
                sharedPreferenceConfig.writeNoOfRooms(roomsCount);

            }else {
                sharedPreferenceConfig.writeNoOfRooms(1);

            }
            rooms.setText(String.valueOf(sharedPreferenceConfig.readNoOfRooms()).concat(" Rooms"));

            if(guestCount>0){
                if(guestCount != sharedPreferenceConfig.readNoOfGuests()){
                    Log.e(TAG_AREA_HOTEL,"Guest changed");
                    isChanged = true;
                }else {
                    Log.e(TAG_AREA_HOTEL,"Guest Not changed");
                }


                sharedPreferenceConfig.writeNoOfGuests(guestCount);
            }else {
                if(sharedPreferenceConfig.readNoOfGuests() == 0){
                    sharedPreferenceConfig.writeNoOfGuests(1);
                }

            }
            guests.setText(String.valueOf(sharedPreferenceConfig.readNoOfGuests()).concat(" Guests"));

            if(isChanged){
                showAllHotelsUnderTheArea(area);
                Log.e(TAG_AREA_HOTEL,"Queried resh data: showAllHotelsUnderTheArea(area");
                isChanged = false;
            }
        }


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
                                hotelListDetails.clear();
                                hotelListDetails = hotelList.getHotels();
                                setHotelListRv();

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

    private void setHotelListRv() {

        new findAsyncTask(viewModel).execute();
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
        if(RGuest.roomsGuests.size()>0){
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable)RGuest.roomsGuests);
            resultIntent.putExtra("BUNDLE",args);
        }

//        startActivity(intent);
//        resultIntent.putExtra("roomG",new Gson().toJson(roomsGuests));
        setResult(Activity.RESULT_OK,resultIntent);
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(AreaHotelsActivity.this,CalenderActivity.class);
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

            String cIn = null;
            String cOut = null;
            if (data != null) {
                cIn = data.getStringExtra("checkIn");
                cOut = data.getStringExtra("checkOut");
            }

            Bundle args = data.getBundleExtra("BUNDLE");

            if(args!=null){
                RGuest.roomsGuests = (List<RoomsGuest>) args.getSerializable("ARRAYLIST");
            }

            String checkInDate = sharedPreferenceConfig.readCheckInDate();
            if(cOut != null){
                assignValue(cIn,cOut);
                Toast.makeText(this, ""+checkInDate+sharedPreferenceConfig.readCheckOutDate()+sharedPreferenceConfig.readNoOfRooms()+sharedPreferenceConfig.readNoOfGuests(), Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, ""+cIn+"he", Toast.LENGTH_SHORT).show();



        }

    }

    @SuppressLint("StaticFieldLeak")
    private class findAsyncTask extends AsyncTask<Void,Void,Void> {

        private SavedHotelViewModel viewModel;
        findAsyncTask(SavedHotelViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for(Hotels hotels: hotelListDetails){
                boolean ss = viewModel.whetherSaved(hotels.getHotel_id());
                hotels.setSaved(ss);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LinearLayoutManager layoutManager = new LinearLayoutManager(AreaHotelsActivity.this);
            areaHotelsRv.setLayoutManager(layoutManager);
            areaHotelsRv.setHasFixedSize(true);
            HotelListAdapter hotelListAdapter = new HotelListAdapter(hotelListDetails,AreaHotelsActivity.this, viewModel);
            areaHotelsRv.setAdapter(hotelListAdapter);

        }
    }
}
