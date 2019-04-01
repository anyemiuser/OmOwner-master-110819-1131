package com.anyemi.omrooms.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.BookingDetailsAdapter;
import com.anyemi.omrooms.Model.BookingRequest;
import com.anyemi.omrooms.Model.CanceledBooking;
import com.anyemi.omrooms.Model.UpComingBooking;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingCancelledFragment  extends Fragment {
    private static final String TAG_FRAGMENTB = BookingCancelledFragment.class.getSimpleName();
    private RecyclerView upcomingRv;
    private ProgressBar progressBar;
    private TextView progressText;
    private ConstraintLayout progressLayout;
    //ViewPager viewPager;


    public BookingCancelledFragment() {
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
        progressBar = view.findViewById(R.id.progressBar3);
        progressText = view.findViewById(R.id.progressText);

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        BookingRequest bookingRequest = new BookingRequest("c","9666235167");
        progressLayout.setVisibility(View.VISIBLE);

        omRoomApi.getUsersCanceledBooking("CanellledBooking",bookingRequest).enqueue(new Callback<CanceledBooking>() {
            @Override
            public void onResponse(Call<CanceledBooking> call, Response<CanceledBooking> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    CanceledBooking canceledBooking = response.body();
                    Log.e(TAG_FRAGMENTB,"success cancel"+new Gson().toJson(response.body()));
                    if (canceledBooking != null) {
                        if(canceledBooking.getStatus().equals("Success")){
                            if(canceledBooking.getCanellledBooking() != null){
                                progressLayout.setVisibility(View.GONE);
                                setUpcomingRv(canceledBooking.getCanellledBooking());
//                                Log.e(TAG_FRAGMENTB,"success"+new Gson().toJson(response.body()));
//                                Log.e(TAG_FRAGMENTB,"success"+new Gson().toJson(canceledBooking));
                            }else {
                                progressText.setText("No Record Found");
                            }
                        }else {
                            Toast.makeText(getActivity(), ""+canceledBooking.getMsg(), Toast.LENGTH_SHORT).show();
                            progressText.setText(canceledBooking.getMsg());
                        }
                    }else {
                        progressText.setText("No Record Found");
                    }


                }



            }

            @Override
            public void onFailure(Call<CanceledBooking> call, Throwable t) {
                Log.e(TAG_FRAGMENTB,"failed"+t.toString());
                progressBar.setVisibility(View.GONE);
                progressText.setText("No Record Found");
            }
        });
//        omRoomApi.getUsersUpComingBooking("CanellledBooking",bookingRequest).enqueue(new Callback<UpComing>() {
//            @Override
//            public void onResponse(Call<UpComing> call, Response<UpComing> response) {
//                if(response.isSuccessful()){
//                    UpComing upComingBooing = response.body();
//                    setUpcomingRv(upComingBooing.getUpcommingBooking());
//                    Log.e(TAG_FRAGMENTB,"success"+new Gson().toJson(response.body()));
//                    Log.e(TAG_FRAGMENTB,"success"+new Gson().toJson(upComingBooing));
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<UpComing> call, Throwable t) {
//                Log.e(TAG_FRAGMENTB,"failed"+t.toString());
//            }
//        });
    }

    private void setUpcomingRv(List<UpComingBooking> upcommingBooking) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        upcomingRv.setLayoutManager(layoutManager);
        upcomingRv.setHasFixedSize(true);
        BookingDetailsAdapter adapter = new BookingDetailsAdapter("c", upcommingBooking,getActivity());

        upcomingRv.setAdapter(adapter);

    }


    //    public void setCurrentItem(int pos) {
//        mViewPager.setCurrentItem(pos);
//    }

}

