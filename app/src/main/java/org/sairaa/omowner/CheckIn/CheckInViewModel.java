package org.sairaa.omowner.CheckIn;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.sairaa.omowner.Model.RoomIdAvailability;
import org.sairaa.omowner.Model.RoomIdMaster;

import java.util.List;

public class CheckInViewModel extends AndroidViewModel {

    private CheckInRepo checkInRepo;

    public CheckInViewModel(@NonNull Application application) {
        super(application);
        checkInRepo = CheckInRepo.getInstance();
    }

    public LiveData<List<RoomIdMaster>> getRoomAndIdDetails (){
        return checkInRepo.getRoomAvailabilities();
    }

    public void setRoomAndIdDetails(List<RoomIdMaster> roomAndIdDetails){
        checkInRepo.setRoomAvailabilities(roomAndIdDetails);
    }

    public void updateRoomDetailsForBooking(RoomIdAvailability roomIdAvailability){
        checkInRepo.updateList(roomIdAvailability);
    }

}
