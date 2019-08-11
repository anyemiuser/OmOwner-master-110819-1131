package org.sairaa.omowner.NewBooking;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.Gson;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.NewBooking.Model.CheckUserExistRequest;
import org.sairaa.omowner.NewBooking.Model.HotelDetails;
import org.sairaa.omowner.NewBooking.Model.ProfileDetails;
import org.sairaa.omowner.NewBooking.Model.ProfileUpdateResponse;
import org.sairaa.omowner.NewBooking.Model.RoomTypePrice;
import org.sairaa.omowner.NewBooking.Model.RoomsGuest;
import org.sairaa.omowner.NewBooking.Model.UserDetailsRequest;
import org.sairaa.omowner.NewBooking.Model.UserDetailsResponse;
import org.sairaa.omowner.NewBooking.Model.UserExistResponse;
import org.sairaa.omowner.NewBooking.Repository.BookingRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingViewModel extends ViewModel implements LifecycleObserver {

    private static final String TAG_VIEWMODEL = BookingViewModel.class.getName();
    private MutableLiveData<List<RoomsGuest>> roomGuestList;
    private BookingRepository bookingRepo;

    private MutableLiveData<Boolean> isUpload = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> loadingText = new MutableLiveData<>();

    private MutableLiveData<HotelDetails> hotelD = new MutableLiveData<>();

    private MutableLiveData<Boolean> isUserDataLoading = new MutableLiveData<>();
    private MutableLiveData<String> userDataLoadingText = new MutableLiveData<>();

    public void init(){
        if(roomGuestList != null){
            return;
        }
        isUpload.setValue(true);
        bookingRepo = BookingRepository.getInstance();
        roomGuestList = bookingRepo.getRoomGuestList();
    }

    public LiveData<List<RoomsGuest>> getRoomGuestList(){
        Log.e("asd",""+new Gson().toJson(roomGuestList.getValue()));
        return roomGuestList;

    }

    public void insertNewRooomGuest(RoomsGuest roomsGuest) {

        bookingRepo.insertRoomGuest(roomsGuest);

    }

    public void setDefault(){
        bookingRepo.setDefault();
    }

    public LiveData<Boolean> getIsUploading(){
        return isUpload;
    }

    public LiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    public void setIsLoading(Boolean value){
        isLoading.setValue(value);
    }

    public LiveData<String> getLoadingText(){
        return loadingText;
    }

    public void setLoadingText(String message){
        loadingText.setValue(message);
    }

    public void setCheckIn(String checkInS){
        bookingRepo.setCheckIn(checkInS);
    }

    public LiveData<String> getCheckInDate() {
        return bookingRepo.getCheckIn();
    }


    public void setChekoutOut(String nextDay) {
        bookingRepo.setCheckOut(nextDay);
    }

    public LiveData<String> getCheckOutDate(){
        return bookingRepo.getCheckOut();
    }

    public void setNoOfDays(int noOfDay) {
        bookingRepo.setNoOfDays(noOfDay);
    }

    public LiveData<Integer> getNoOfDays(){
        return  bookingRepo.getNoOfDays();
    }

    public void removeRoomGuests() {
        bookingRepo.removeRoomGuest();
    }

    public LiveData<HotelDetails> getHotelDetails(){
        return bookingRepo.getHotelDetails();
    }

    public void clearHotelDetails() {
        bookingRepo.clearRoomTypeList();
    }

    public void onRetrieveHotelDetails(String hotelDetails,
                                       String hotelId,
                                       String checkInDate,
                                       String checkOutDate,
                                       String noOfRoom){
        isLoading.setValue(true);
        loadingText.setValue("Loading..");
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getHotelDetails(hotelDetails,hotelId,checkInDate,checkOutDate,noOfRoom)
                .enqueue(new Callback<HotelDetails>() {
                    @Override
                    public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
                        if(response.isSuccessful()){
                            HotelDetails hotelDetails = response.body();
                            Log.e(TAG_VIEWMODEL,"success"+new Gson().toJson(hotelDetails));
                            if(hotelDetails!=null){
                                loadingText.setValue("Select Your Room >>");
                                bookingRepo.setHotelDetails(hotelDetails);
                                Log.e(TAG_VIEWMODEL,"success"+new Gson().toJson(hotelDetails));
                            }else {
                                loadingText.setValue(""+response.message());
                            }
                        }else {
                            loadingText.setValue(""+response.message());
                        }
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<HotelDetails> call, Throwable t) {
                        isLoading.setValue(false);
                        loadingText.setValue("Something Went Wrong: "+t.getMessage());
                        Log.e(TAG_VIEWMODEL,"failed"+new Gson().toJson(hotelDetails));
                    }
                });

    }


    public LiveData<List<RoomTypePrice>> getRoomTypesAvailable() {
        return  bookingRepo.getRoomTypeAvailable();
    }

    public void setRoomTypeList(List<RoomTypePrice> roomTypeList){
        bookingRepo.setRoomTypeList(roomTypeList);
    }

    public void findUserStatus(String phoneNo) {
        isUserDataLoading.setValue(true);
        userDataLoadingText.setValue("Checking Whether New User Or Not..");
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        CheckUserExistRequest user = new CheckUserExistRequest(phoneNo);
        omRoomApi.checkExistingUserOrRegister(user).enqueue(new Callback<UserExistResponse>() {
            @Override
            public void onResponse(Call<UserExistResponse> call, Response<UserExistResponse> response) {
                isUserDataLoading.setValue(false);
                if(response.isSuccessful()){
                    UserExistResponse userExistResponse = response.body();
                    if(userExistResponse!= null && !userExistResponse.getMsg().isEmpty()){
                        bookingRepo.setUserExistance(userExistResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserExistResponse> call, Throwable t) {
                isUserDataLoading.setValue(false);
                userDataLoadingText.setValue("Something went wrong");
            }
        });
    }

    public void setUserExistance(String msg){
        bookingRepo.setUserExistance(msg);
    }

    public LiveData<String> getIsUserExistance(){
        return  bookingRepo.getIsUserExistance();
    }

    public void updateProfileDetails(ProfileDetails profileDetails) {

        Log.e(TAG_VIEWMODEL,"profile update request:"+new Gson().toJson(profileDetails));
        isUserDataLoading.setValue(true);
        userDataLoadingText.setValue("Updating Profile...");
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.updateProfile(profileDetails).enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponse> call, Response<ProfileUpdateResponse> response) {
                isUserDataLoading.setValue(false);
                if(response.isSuccessful()){
                    ProfileUpdateResponse updateResponse = response.body();
                    if(updateResponse!= null){
                        if(updateResponse.getStatus().equals("Success")){
                            bookingRepo.setUpdateProfile(profileDetails);
                        }
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                isUserDataLoading.setValue(false);
                userDataLoadingText.setValue("Something Went Wrong.");
            }
        });
    }

    public void setDefaultProfile(){
        bookingRepo.setUpdateProfile(null);
    }

    public LiveData<ProfileDetails> getProfile(){
        return bookingRepo.getProfile();
    }

    public void retrieveAndBook(String phoneNo) {
        UserDetailsRequest request = new UserDetailsRequest(phoneNo);
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        omRoomApi.getUserDetails(request).enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                if(response.isSuccessful()){
                    UserDetailsResponse userDetails = response.body();
                    if (userDetails != null && userDetails.getMsg().equals("Success") && userDetails.getStatus().equals("Success")) {
                        if (userDetails.getProfileDetails() != null) {
                            bookingRepo.setUpdateProfile(userDetails.getProfileDetails().get(0));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {

            }
        });

    }

    public LiveData<Boolean> getIsUserDataUploading(){
        return isUserDataLoading;
    }

    public LiveData<String> getUserDataLoadingText(){
        return userDataLoadingText;
    }

    public void setIsUserLoadingDefault() {
        isUserDataLoading.setValue(false);
    }


}
