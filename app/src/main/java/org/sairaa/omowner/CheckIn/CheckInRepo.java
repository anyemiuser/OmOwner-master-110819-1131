package org.sairaa.omowner.CheckIn;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.sairaa.omowner.Model.RoomIdAvailability;
import org.sairaa.omowner.Model.RoomIdMaster;

import java.util.List;
import java.util.Objects;

public class CheckInRepo {

    private static CheckInRepo instance;

    private MutableLiveData<List<RoomIdMaster>> roomAvailabilities = new MutableLiveData<>();

    public static CheckInRepo getInstance(){
        if(instance == null){
            instance = new CheckInRepo();

        }
        return instance;
    }


    public LiveData<List<RoomIdMaster>> getRoomAvailabilities() {
        return roomAvailabilities;
    }

    public void setRoomAvailabilities(List<RoomIdMaster> roomAvailability) {
        this.roomAvailabilities.setValue(roomAvailability);

    }


    public void updateList(RoomIdAvailability roomIdAva) {
        for(RoomIdMaster roomId: Objects.requireNonNull(roomAvailabilities.getValue())){
            if(roomIdAva.getRoom_type().equals(roomId.getRoomType())){
                for(RoomIdAvailability roomIdAvailability: roomId.getRoomAvailabilityList()){
                    if(roomIdAva.getRoom_id().equals(roomIdAvailability.getRoom_id())){
                        roomIdAvailability.setAvailability(roomIdAva.getAvailability());
                    }
                }
            }
        }
    }
}
