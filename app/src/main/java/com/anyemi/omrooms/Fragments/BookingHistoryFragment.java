package com.anyemi.omrooms.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anyemi.omrooms.Adapters.BookingDetailsAdapter;
import com.anyemi.omrooms.Adapters.HotelAdapter;
import com.anyemi.omrooms.Adapters.HotelListAdapter;
import com.anyemi.omrooms.Model.BookingRequest;
import com.anyemi.omrooms.Model.UpComing;
import com.anyemi.omrooms.Model.UpComingBooking;
import com.anyemi.omrooms.Model.UserRequest;
import com.anyemi.omrooms.Model.UserResponse;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.AreaHotelsActivity;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryFragment extends Fragment {
    private static final String TAG_FRAGMENTB = BookingHistoryFragment.class.getSimpleName();
    private RecyclerView upcomingRv;
    private ProgressBar progressBar;
    private TextView statusText;
    private ConstraintLayout progressLayout;
    //ViewPager viewPager;

    public BookingHistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.booking_history_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        upcomingRv = view.findViewById(R.id.upcoming_rv);
        progressLayout = view.findViewById(R.id.progress_l);

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        BookingRequest bookingRequest = new BookingRequest("u","9666235167");

        progressLayout.setVisibility(View.VISIBLE);

        omRoomApi.getUsersUpComingBooking(bookingRequest).enqueue(new Callback<UpComing>() {
            @Override
            public void onResponse(Call<UpComing> call, Response<UpComing> response) {
                if(response.isSuccessful()){
                    UpComing upComingBooing = response.body();
                    setUpcomingRv(upComingBooing.getUpcommingBooking());
                    Log.e(TAG_FRAGMENTB,"success"+new Gson().toJson(response.body()));
                    Log.e(TAG_FRAGMENTB,"success"+new Gson().toJson(upComingBooing));
                }
                progressLayout.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<UpComing> call, Throwable t) {
                Log.e(TAG_FRAGMENTB,"failed"+t.toString());
                progressLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setUpcomingRv(List<UpComingBooking> upcommingBooking) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        upcomingRv.setLayoutManager(layoutManager);
        upcomingRv.setHasFixedSize(true);
        BookingDetailsAdapter adapter = new BookingDetailsAdapter(upcommingBooking,getActivity());

        upcomingRv.setAdapter(adapter);

    }


    //    public void setCurrentItem(int pos) {
//        mViewPager.setCurrentItem(pos);
//    }

}
