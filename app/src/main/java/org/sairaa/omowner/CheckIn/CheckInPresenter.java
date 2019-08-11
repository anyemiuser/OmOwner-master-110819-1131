package org.sairaa.omowner.CheckIn;

import android.util.Log;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.gson.Gson;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.Model.RoomAvailabilityRequest;
import org.sairaa.omowner.Model.RoomAvailabilityResponse;
import org.sairaa.omowner.Model.RoomCheck;
import org.sairaa.omowner.Model.RoomIdAvailability;
import org.sairaa.omowner.Model.RoomCheckInRequest;
import org.sairaa.omowner.Model.RoomIdCheckInResponse;
import org.sairaa.omowner.Model.RoomIdMaster;
import org.sairaa.omowner.Model.RoomIdType;
import org.sairaa.omowner.Utils.AllUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInPresenter implements CheckInContract.UserActionsListener {

    private static final String TAG_CHECKIN_PRESENTER = CheckInPresenter.class.getName();
    CheckInContract.View checkInVew;

    private RoomCheckInRequest checkInRequest;

    private List<RoomCheck> roomCheckList;

    private List<RoomIdAvailability> roomIdAvailabilityList;

    public CheckInPresenter(CheckInContract.View checkInVew) {
        this.checkInVew = checkInVew;
    }

    @Override
    public boolean onRoomIdClick(RoomIdAvailability roomId) {

        return true;
    }

    @Override
    public void onCheckIn(List<RoomIdMaster> value) {

        checkInRequest.getRoomIdType().clear();

        int noOfRoom = 0;
        for(RoomCheck check: roomCheckList){
            noOfRoom = noOfRoom+ check.getNoOfRoom();
        }

        int clickRoom = 0;
        for(RoomIdMaster master: value){
            for(RoomIdAvailability roomAva: master.getRoomAvailabilityList()){
                if(roomAva.getAvailability().equals("2")){
                    clickRoom++;
                }
            }
        }

        if(noOfRoom == clickRoom){
            for(RoomIdMaster master: value){
                for(RoomIdAvailability roomAva: master.getRoomAvailabilityList()){
                    if(roomAva.getAvailability().equals("2")){
                        checkInRequest.getRoomIdType().add(new RoomIdType(roomAva.getRoom_type(),roomAva.getRoom_id()));
                    }
                }
            }

            List<RoomCheck> roomCheckList2 = new ArrayList<>();
            Log.e(TAG_CHECKIN_PRESENTER,"request list:"+new Gson().toJson(checkInRequest));
            roomCheckList2.clear();
            for(RoomIdType roomTypeId: checkInRequest.getRoomIdType()){

                if(roomCheckList2.size()>0){
                    boolean isNew = true;
                    for(RoomCheck check: roomCheckList2){
                        if(check.getRoomType().equals(roomTypeId.getRoom_type())){
                            check.setNoOfRoom(check.getNoOfRoom()+1);
                            isNew = false;
                        }

                    }
                    if(isNew){
                        roomCheckList2.add(new RoomCheck(roomTypeId.getRoom_type(),1));
                    }

                }else {
                    roomCheckList2.add(new RoomCheck(roomTypeId.getRoom_type(),1));
                }


            }
            Log.e(TAG_CHECKIN_PRESENTER," check list 1: "+new Gson().toJson(roomCheckList));
            Log.e(TAG_CHECKIN_PRESENTER," check list 2: "+new Gson().toJson(roomCheckList2));

            if(AllUtil.compareList(roomCheckList,roomCheckList2)){
                OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
                checkInVew.toastSnack("Please Wait..");
                omRoomApi.doCheckIn(checkInRequest).enqueue(new Callback<RoomIdCheckInResponse>() {
                    @Override
                    public void onResponse(Call<RoomIdCheckInResponse> call, Response<RoomIdCheckInResponse> response) {
                        if(response.isSuccessful()){
                            RoomIdCheckInResponse checkInResponse = response.body();
                            if (checkInResponse != null && checkInResponse.getStatus().equals("Success")) {
                                Log.e(TAG_CHECKIN_PRESENTER, "" + new Gson().toJson(checkInResponse));
                                checkInVew.toastSnack(checkInResponse.getMsg()+" "+"Successfully Checked in");
                                checkInVew.onCheckInSuccess();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RoomIdCheckInResponse> call, Throwable t) {
                        Log.e(TAG_CHECKIN_PRESENTER,"error:"+t.toString());
                    }
                });
            }else {
                checkInVew.toastSnack("Select Room type according to booking ." );
            }



        }else {
            checkInVew.toastSnack("You need to select only "+noOfRoom+" room." );
        }

    }

    @Override
    public void setRoomForCheckin(RoomCheckInRequest checkInRequest, List<RoomCheck> roomCheckList) {
        this.checkInRequest = checkInRequest;
        this.roomCheckList = roomCheckList;

    }


    @Override
    public void retrieveRooms(String hotelId) {
        RoomAvailabilityRequest request = new RoomAvailabilityRequest(hotelId);
        Log.e(TAG_CHECKIN_PRESENTER,"request: "+new Gson().toJson(request));
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getCheckInDetails(request).enqueue(new Callback<RoomAvailabilityResponse>() {
            @Override
            public void onResponse(Call<RoomAvailabilityResponse> call, Response<RoomAvailabilityResponse> response) {
                if(response.isSuccessful()){
                    RoomAvailabilityResponse checkInResponse = response.body();
                    Log.e(TAG_CHECKIN_PRESENTER,"Success"+new Gson().toJson(checkInResponse));
                    if(checkInResponse.getRoomlist() != null){
                        roomIdAvailabilityList = checkInResponse.getRoomlist();
                        checkInVew.setUpRoomId(roomIdAvailabilityList);
                    }

                }
//                checkInVew.setUpRoomId();


                Log.e(TAG_CHECKIN_PRESENTER,"Success1"+new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<RoomAvailabilityResponse> call, Throwable t) {
                Log.e(TAG_CHECKIN_PRESENTER,"error:"+t.toString());

            }
        });


    }
}
