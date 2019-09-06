package org.sairaa.omowner.CheckIn;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.sairaa.omowner.Model.CustomerBookings;
import org.sairaa.omowner.Model.RoomCheck;
import org.sairaa.omowner.Model.RoomIdAvailability;
import org.sairaa.omowner.Model.RoomCheckInRequest;
import org.sairaa.omowner.Model.RoomIdMaster;
import org.sairaa.omowner.Model.RoomIdType;
import org.sairaa.omowner.Model.RoomTypeDetails;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import java.util.ArrayList;
import java.util.List;

public class CheckInActivity extends AppCompatActivity implements CheckInContract.View, View.OnClickListener, RoomIdsAdapter.RoomIdAdapterCallback {

    private static final String TAG_CHECKIN = CheckInActivity.class.getName();
    CheckInPresenter checkInPresenter;
    private TextView roomTypeQty;
    private RecyclerView roomIdRv;
    private Button checkIn;
    private CheckInAdapter checkInAdapter;
    CustomerBookings customerBookings;
    private SharedPreferenceConfig sharedPreferenceConfig;

    private CheckInViewModel viewModel;
    List<RoomIdMaster> masterList = new ArrayList<>();

    List<RoomCheck> roomCheckList = new ArrayList<>();
    RoomCheckInRequest checkInRequest;

    Intent resultIntent;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        ActionBar actionBar = this.getSupportActionBar();
       /* if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/


        viewModel = ViewModelProviders.of(this).get(CheckInViewModel.class);

        checkInPresenter = new CheckInPresenter(this);
//        init();

        resultIntent = getIntent();
        String asd = resultIntent.getStringExtra("bookingD");
        customerBookings = new Gson().fromJson(asd,CustomerBookings.class);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        init();

        if(customerBookings != null){
            String roomTtt = "";
            for(RoomTypeDetails roomD: customerBookings.getRoomtype()){
                roomTtt = roomTtt.concat(roomD.getRoom_type().concat(": ").concat(roomD.getBooked()).concat(" Room\n"));
            }
            roomTypeQty.setText(roomTtt);
            if (actionBar != null) {
                actionBar.setTitle("Select Room");
            }

            List<RoomIdType> roomIdType = new ArrayList<>();

            for(RoomTypeDetails roomDetails: customerBookings.getRoomtype()){
                RoomCheck roomCheck = new RoomCheck(roomDetails.getRoom_type(),Integer.parseInt(roomDetails.getBooked()));
                roomCheckList.add(roomCheck);
            }
            checkInRequest = new RoomCheckInRequest(customerBookings.getBooking_id(),sharedPreferenceConfig.readHotelId(),sharedPreferenceConfig.readPhoneNo(),roomIdType);
            checkInPresenter.setRoomForCheckin(checkInRequest,roomCheckList);
//            checkInPresenter.setToCheckRoomTypeAndCpunt(roomIdType);
        }else {
            checkIn.setVisibility(View.GONE);
            if (actionBar != null) {
                actionBar.setTitle("Rooms Status");
                actionBar.setDisplayHomeAsUpEnabled(true);
            }

         //   roomTypeQty.setText("Room Status");
        }
        Log.e(TAG_CHECKIN,""+new Gson().toJson(customerBookings));
    }

    @Override
    public void init() {
        roomTypeQty = findViewById(R.id.room_type_qty);

        roomIdRv = findViewById(R.id.room_id_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        roomIdRv.setLayoutManager(layoutManager);
        checkInAdapter = new CheckInAdapter(this);
        roomIdRv.setAdapter(checkInAdapter);

        checkIn = findViewById(R.id.check_in);
        checkIn.setOnClickListener(this);
        checkInPresenter.retrieveRooms(sharedPreferenceConfig.readHotelId());


    }

    @Override
    public void setUpRoomId(List<RoomIdAvailability> roomTypeDetails) {


//        List<RoomIdMaster> masterList = new ArrayList<>();


        List<String> roomT = new ArrayList<>();
        for (RoomIdAvailability room: roomTypeDetails) {
            if (!roomT.contains(room.getRoom_type())) {
                roomT.add(room.getRoom_type());

            }
        }
        for(String rType: roomT){
            List<RoomIdAvailability> roomAvailabilities = new ArrayList<>();
            for(RoomIdAvailability room: roomTypeDetails){
                if(room.getRoom_type().equals(rType)){
                    roomAvailabilities.add(room);
                }
            }
            masterList.add( new RoomIdMaster(rType,roomAvailabilities));
        }

        if(masterList.size()>0)
            viewModel.setRoomAndIdDetails(masterList);
            viewModel.getRoomAndIdDetails().observe(this, new Observer<List<RoomIdMaster>>() {
                @Override
                public void onChanged(@Nullable List<RoomIdMaster> roomIdMasters) {
//                    masterList.clear();
//                    if (roomIdMasters != null) {
                        masterList= roomIdMasters;
//                    }
                    checkInAdapter.updateList(masterList);
                }
            });

//        viewModel.getRoomAndIdDetails().getValue();

    }

    @Override
    public boolean onChangeRoomStatus(String roomId) {
        return false;
    }

    @Override
    public void toastSnack(String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), ""+s, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onCheckInSuccess() {
        setResult(Activity.RESULT_OK,resultIntent);
        finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.check_in:
                checkInPresenter.onCheckIn(viewModel.getRoomAndIdDetails().getValue());
                break;
        }
    }

    @Override
    public boolean onChooseRoom(RoomIdAvailability roomAvailability) {

        viewModel.updateRoomDetailsForBooking(roomAvailability);

        Log.e(TAG_CHECKIN,"data: "+new Gson().toJson(viewModel.getRoomAndIdDetails().getValue()));
        return checkInPresenter.onRoomIdClick(roomAvailability);
    }
}
