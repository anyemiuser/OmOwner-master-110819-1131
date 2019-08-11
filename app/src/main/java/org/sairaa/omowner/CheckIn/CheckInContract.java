package org.sairaa.omowner.CheckIn;

import org.sairaa.omowner.Model.RoomCheck;
import org.sairaa.omowner.Model.RoomIdAvailability;
import org.sairaa.omowner.Model.RoomCheckInRequest;
import org.sairaa.omowner.Model.RoomIdMaster;

import java.util.List;

public interface CheckInContract {
    interface View{
        void init();
        void setUpRoomId(List<RoomIdAvailability> roomlist);
        boolean onChangeRoomStatus(String roomId);

        void toastSnack(String s);


        void onCheckInSuccess();

    }

    interface UserActionsListener{

        boolean onRoomIdClick(RoomIdAvailability roomAvailability);
        void onCheckIn(List<RoomIdMaster> value);
        void setRoomForCheckin(RoomCheckInRequest checkInRequest, List<RoomCheck> roomCheckList);
        void retrieveRooms(String hotelId);
    }

}
