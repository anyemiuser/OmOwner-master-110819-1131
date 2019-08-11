package org.sairaa.omowner.NewBooking.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.sairaa.omowner.NewBooking.Model.HotelDetails;
import org.sairaa.omowner.NewBooking.Model.ProfileDetails;
import org.sairaa.omowner.NewBooking.Model.RoomTypePrice;
import org.sairaa.omowner.NewBooking.Model.RoomsGuest;

import java.util.ArrayList;
import java.util.List;


/**
 * Singleton Pattern
 **/

public class BookingRepository {

    private static BookingRepository instance;
    private List<RoomsGuest> roomsGuests = new ArrayList<>();
    private MutableLiveData<List<RoomsGuest>> roomG = new MutableLiveData<>();
    private MutableLiveData<String> checkIn = new MutableLiveData<>();
    private MutableLiveData<String> checkOut = new MutableLiveData<>();
    private MutableLiveData<Integer> noOfDays = new MutableLiveData<>();
    private MutableLiveData<HotelDetails> hotelDetails = new MutableLiveData<>();

    private List<RoomTypePrice> roomTypePrices = new ArrayList<>();
    private MutableLiveData<List<RoomTypePrice>> roomType = new MutableLiveData<>();

    private MutableLiveData<String> isUserExist = new MutableLiveData<>();

    private MutableLiveData<ProfileDetails> profile = new MutableLiveData<>();

    public static BookingRepository getInstance(){
        if(instance == null){
            instance = new BookingRepository();
        }
        return instance;
    }

    public MutableLiveData<List<RoomsGuest>> getRoomGuestList(){

        roomG.setValue(roomsGuests);
        return roomG;
    }

    public void insertRoomGuest(RoomsGuest roomGuest){
        roomsGuests.add(roomGuest);
        roomG.setValue(roomsGuests);

    }

    public void removeRoomGuest(){
        if(roomsGuests.size()>1){
            roomsGuests.remove(roomsGuests.size()-1);
            roomG.setValue(roomsGuests);
        }


    }

    public void setDefault(){
        roomsGuests.clear();
        RoomsGuest roomsGuest = new RoomsGuest(1,2,0);
        roomsGuests.add(roomsGuest);
        roomG.setValue(roomsGuests);
    }

    public void updateRoomGuest(int position, RoomsGuest roomsGuest){
        roomsGuests.get(position).setChildren(roomsGuest.getChildren());
        roomsGuests.get(position).setGuests(roomsGuest.getGuests());
        roomsGuests.get(position).setRooms(roomsGuest.getRooms());
    }

    public void setCheckIn(String checkInS){
        checkIn.setValue(checkInS);
    }

    public MutableLiveData<String> getCheckIn(){
        return checkIn;
    }

    public MutableLiveData<String> getCheckOut(){
        return checkOut;
    }

    public void setCheckOut(String nextDay) {
        checkOut.setValue(nextDay);
    }

    public void setNoOfDays(int noOfDay) {
        noOfDays.setValue(noOfDay);
    }

    public MutableLiveData<Integer> getNoOfDays(){
        return noOfDays;
    }

    public LiveData<HotelDetails> getHotelDetails() {
        return hotelDetails;
    }

    public void setHotelDetails(HotelDetails hotelD){
        hotelDetails.setValue(hotelD);
    }

    public void ClearHotelDetails(){
        hotelDetails.setValue(new HotelDetails());
    }

    public LiveData<List<RoomTypePrice>> getRoomTypeAvailable() {
        roomType.setValue(roomTypePrices);
        return roomType;
    }

    public void setRoomTypeList(List<RoomTypePrice> roomT){
        roomTypePrices.clear();
        roomTypePrices.addAll(roomT);
        roomType.setValue(roomTypePrices);
    }

    public void clearRoomTypeList(){
        roomTypePrices.clear();
        roomType.setValue(roomTypePrices);
    }

    public void setUserExistance(String msg) {
        isUserExist.setValue(msg);
    }

    public MutableLiveData<String> getIsUserExistance(){
        return isUserExist;
    }

    public void setUpdateProfile(ProfileDetails profileDetails) {
        profile.setValue(profileDetails);

    }

    public MutableLiveData<ProfileDetails> getProfile(){
        return profile;
    }
}
