package org.sairaa.omowner.Main;

import org.sairaa.omowner.Model.AllBookingCount;
import org.sairaa.omowner.Model.BookingCountResponse;
import org.sairaa.omowner.Model.RoomTarrif;
import org.sairaa.omowner.Model.RoomTypeCount;
import org.sairaa.omowner.Model.TotalAvailableRoom;
import org.sairaa.omowner.Model.TotalRooms;

import java.util.List;

public interface MainContract {

    interface View{

        void setUpUI(List<AllBookingCount> allBookingCountList);

        void setUpUITariff(List<RoomTarrif> tariffList);

        void setUpUIRoomType(List<TotalAvailableRoom> roomTypeList);

        void navigateToDetailActivity();

        void disableDateIcon();

        void enableDateIcon();

        void popUpCalenderView();

        void onSignOutNavigateToLogin();

        void clearSharedPrefMobile();

        void NavigateToBookingDetailsActivity(String status, String day);

        void displayProgressBar(String progressText);

        void setProgressBar(int progress);

        void hideProgressBar(String progressText);


        void clearRecyclerView();

        void setUpEodOccupancy(List<RoomTypeCount> listRoomBooked, int totalRoom, List<TotalRooms> noofrooms);
    }

    interface UserActionsListener{

        void onSpinnerSelection(String hotelId, String dateC, String day);

        void setParameterToRetrieveBookingsCount(String hotelId, String dateC, String day);

        void signOut();

        void onCalenderIconSelection();



    }
}
