package org.sairaa.omowner.Main;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.Model.AllBookingCount;
import org.sairaa.omowner.Model.BookingCountRequest;
import org.sairaa.omowner.Model.BookingCountResponse;
import org.sairaa.omowner.Model.CompletedCountList;
import org.sairaa.omowner.Model.InhouseCountList;
import org.sairaa.omowner.Model.RoomTypeCount;
import org.sairaa.omowner.Model.TotalAvailableRoom;
import org.sairaa.omowner.Model.TotalRooms;
import org.sairaa.omowner.Model.UpcomingCountList;
import org.sairaa.omowner.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.UserActionsListener, Constants {

    private static final String TAG_PRESENTER = MainPresenter.class.getName();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private MainContract.View mainView;

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onSpinnerSelection(String hotelId, String dateC,String day) {




        switch (day) {
            case "Today":
                mainView.disableDateIcon();
                setParameterToRetrieveBookingsCount(hotelId,dateC,day);
//                mainView.setUpUI(allBookingCountList);
                break;
            case "Tomorrow":
                mainView.disableDateIcon();
                setParameterToRetrieveBookingsCount(hotelId,dateC,day);
//                mainView.setUpUI(allBookingCountList);
                break;
            case "Select Date":
                mainView.enableDateIcon();
                mainView.popUpCalenderView();
                break;
            case "All":
                mainView.disableDateIcon();
                mainView.navigateToDetailActivity();
                break;
            default:
//                mainView.setUpUI(allBookingCountList);
                mainView.disableDateIcon();
                break;
        }

    }

    @Override
    public void setParameterToRetrieveBookingsCount(String hotelId, String dateC, String day) {

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        BookingCountRequest bookingCountRequest = new BookingCountRequest(hotelId,dateC,day);
        Log.e(TAG_PRESENTER,""+new Gson().toJson(bookingCountRequest));
        mainView.displayProgressBar("Loading..");
        mainView.clearRecyclerView();
//        omRoomApi.getUserTypeAndHotel(user).enqueue(new Callback<UserTypeResponse>() {
        omRoomApi.getBookingCountDetails(bookingCountRequest).enqueue(new Callback<BookingCountResponse>() {
            @Override
            public void onResponse(Call<BookingCountResponse> call, Response<BookingCountResponse> response) {
                if(response.isSuccessful()){
                    BookingCountResponse bookingResponse = null;
                    try{
                        bookingResponse = response.body();
                    }catch(JsonSyntaxException e){
                        Log.e(TAG_PRESENTER,"error:"+e.toString());
                    }

                    if (bookingResponse != null && bookingResponse.getStatus().equals("success") && bookingResponse.getMessage().equals("success")) {


                        mainView.hideProgressBar(null);

                        //Preparing List for RV in MainActivity
                        List<AllBookingCount> allBookingCountList = new ArrayList<>();
                        List<TotalAvailableRoom> totalAvailableRooms = new ArrayList<>();

                        int bookedRoom = 0;
                        int totalRoom = 0;
                        List<RoomTypeCount> listRoomBooked = new ArrayList<>();

                        UpcomingCountList upcomingCountList = bookingResponse.getUpcomingList();
                        InhouseCountList inhouseCountList = bookingResponse.getInhouseList();
                        CompletedCountList completedCountList = bookingResponse.getCompletedlist();

                        if(bookingResponse.getNoofrooms()!= null){
                            for(TotalRooms room: bookingResponse.getNoofrooms()){
                                totalRoom = totalRoom+ Integer.parseInt(room.getNo_of_rooms());
                            }

                        }

                        if (!upcomingCountList.getUpcomingbookings().equals("0") &&
                                upcomingCountList.getRoomtype() != null) {

                            int noOfRoomBooked = 0;
                            //                            List<RoomTypeCount> roomTypeCountList = upcomingCountList.getRoomlist();
                            for (RoomTypeCount roomTypeCount : upcomingCountList.getRoomtype()) {
                                noOfRoomBooked = noOfRoomBooked + Integer.parseInt(roomTypeCount.getBooked());
                                listRoomBooked.add(roomTypeCount);
                            }

                            AllBookingCount bookingCount = new AllBookingCount(upComingType,
                                    Integer.parseInt(upcomingCountList.getUpcomingbookings()),
                                    noOfRoomBooked, totalRoom, upcomingCountList.getRoomtype());

                            allBookingCountList.add(bookingCount);

                            bookedRoom = bookedRoom+noOfRoomBooked;


                        }

                        if (!inhouseCountList.getIncomingbookings().equals("0") &&
                                inhouseCountList.getRoomtype() != null) {

                            int noOfRoomBooked = 0;
                            //                            List<RoomTypeCount> roomTypeCountList = upcomingCountList.getRoomlist();
                            for (RoomTypeCount roomTypeCount : inhouseCountList.getRoomtype()) {
                                noOfRoomBooked = noOfRoomBooked + Integer.parseInt(roomTypeCount.getBooked());
                                listRoomBooked.add(roomTypeCount);
                            }

                            AllBookingCount bookingCount = new AllBookingCount(inHouseType,
                                    Integer.parseInt(inhouseCountList.getIncomingbookings()),
                                    noOfRoomBooked, 10, inhouseCountList.getRoomtype());

                            allBookingCountList.add(bookingCount);
                            bookedRoom = bookedRoom + noOfRoomBooked;
                        }
                        if (completedCountList != null) {
                            if (!completedCountList.getCompletedbookings().equals("0") &&
                                    completedCountList.getRoomtype() != null) {

                                int noOfRoomBooked = 0;
                                //                            List<RoomTypeCount> roomTypeCountList = upcomingCountList.getRoomlist();
                                for (RoomTypeCount roomTypeCount : completedCountList.getRoomtype()) {
                                    noOfRoomBooked = noOfRoomBooked + Integer.parseInt(roomTypeCount.getBooked());
                                    listRoomBooked.add(roomTypeCount);
                                }

                                AllBookingCount bookingCount = new AllBookingCount(completedType,
                                        Integer.parseInt(completedCountList.getCompletedbookings()),
                                        noOfRoomBooked, 10, completedCountList.getRoomtype());

                                allBookingCountList.add(bookingCount);
                            }
                        }
                        String prgText = "";
                        //set up UI
                        if (allBookingCountList.size() > 0) {
                            mainView.setUpUI(allBookingCountList);
                            mainView.setUpEodOccupancy(listRoomBooked,totalRoom,bookingResponse.getNoofrooms());
                        }else {
                            mainView.setUpUI(allBookingCountList);
                            prgText = " No Booking Found on this date";
                            mainView.hideProgressBar(prgText);
                            mainView.setUpEodOccupancy(listRoomBooked,totalRoom,bookingResponse.getNoofrooms());
                        }

                        if(bookingResponse.getTarrif() != null){
                            mainView.setUpUITariff(bookingResponse.getTarrif());
                        }else {
                            mainView.setUpUITariff(new ArrayList<>());
                            prgText = prgText.concat("\n").concat(" No Tariff data found on this date");
                            mainView.hideProgressBar(prgText);
                        }

//                        if(bookingResponse.getNoofrooms() != null){
//                            for(TotalRooms room: bookingResponse.getNoofrooms()){
////                                totalRoom = totalRoom+ Integer.parseInt(room.getNo_of_rooms());
//                                TotalAvailableRoom availableRoom = new TotalAvailableRoom(room.getNo_of_rooms(),room.getRoom_type(),100);
//                                totalAvailableRooms.add(availableRoom);
//                            }
////                            mainView.setUpUIRoomType(totalAvailableRooms);
//                        }else {
//                            mainView.hideProgressBar(" No room details found on this date");
//                        }


                    }else {
                        mainView.hideProgressBar(" No Data Found.");
                    }
                    Log.e(TAG_PRESENTER,""+new Gson().toJson(bookingResponse));


                }

            }

            @Override
            public void onFailure(Call<BookingCountResponse> call, Throwable t) {
                Log.e(TAG_PRESENTER,""+t.toString());
                mainView.hideProgressBar("Something Went Wrong..");
                mainView.setUpUI(new ArrayList<>());
                mainView.setUpUITariff(new ArrayList<>());

            }
        });


    }

    @Override
    public void signOut() {
        mAuth.signOut();
        mainView.clearSharedPrefMobile();
        mainView.onSignOutNavigateToLogin();

    }

    @Override
    public void onCalenderIconSelection() {
        mainView.popUpCalenderView();

    }
}
