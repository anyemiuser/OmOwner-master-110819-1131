package org.sairaa.omowner.BookingDetails;

import org.sairaa.omowner.Model.CustomerBookingDetailsRequest;
import org.sairaa.omowner.Model.CustomerBookings;

import java.util.List;

public interface BookingDetailContract {
    interface View{
        void setUpTitle(String title);

        void displayProgressBar(String progressText);
        void setProgressBar(int progress);
        void hideProgressBar(String progressText);

        void setUpRV(List<CustomerBookings> bookings);

        void clearRecyclerView();

        void refreshViewAfterCheckOut(boolean isCancel);

        void toastMessage(String msg);
    }

    interface UserActionsListener{
        void getDetailsFromResultIntent();

        void retrieveBookingDetails(CustomerBookingDetailsRequest request);


        void onCheckOut(String bookingId, String cancelledBy);
    }
}
