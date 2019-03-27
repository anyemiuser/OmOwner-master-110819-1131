package com.anyemi.omrooms.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyemi.omrooms.Model.BookingRequest;
import com.anyemi.omrooms.Model.UpComing;
import com.anyemi.omrooms.Model.UserRequest;
import com.anyemi.omrooms.Model.UserResponse;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryFragment extends Fragment {
    private static final String TAG_FRAGMENTB = BookingHistoryFragment.class.getSimpleName();

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
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        BookingRequest bookingRequest = new BookingRequest("9","9666235167");

        omRoomApi.getUsersUpComingBooking(bookingRequest).enqueue(new Callback<UpComing>() {
            @Override
            public void onResponse(Call<UpComing> call, Response<UpComing> response) {
                Log.e(TAG_FRAGMENTB,"success"+new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<UpComing> call, Throwable t) {
                Log.e(TAG_FRAGMENTB,"failed"+t.toString());
            }
        });
    }

    //    public void setCurrentItem(int pos) {
//        mViewPager.setCurrentItem(pos);
//    }

}
