package com.anyemi.omrooms.Fragments;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.BookingDetailsAdapter;
import com.anyemi.omrooms.Model.BookingRequest;
import com.anyemi.omrooms.Model.CompletedBooking;
import com.anyemi.omrooms.Model.UpComingBooking;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.UI.BookingDetailActivity;
import com.anyemi.omrooms.UI.SearchActivity;
import com.anyemi.omrooms.Utils.RecyclerTouchListener;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingCompletedFragment extends Fragment {
    private static final String TAG_FRAGMENTB = BookingHistoryFragment.class.getSimpleName();
    private RecyclerView upcomingRv;
    private ProgressBar progressBar;
    private TextView progressText;
    private ConstraintLayout progressLayout;



    //ViewPager viewPager;


    public BookingCompletedFragment() {
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
        Button bookHotel = view.findViewById(R.id.book_hotel);
        bookHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferenceConfig sharedPreferenceConfig = new SharedPreferenceConfig(Objects.requireNonNull(getActivity()));

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        String statusS = "s";
        BookingRequest bookingRequest = new BookingRequest(statusS, sharedPreferenceConfig.readPhoneNo());

        progressLayout.setVisibility(View.VISIBLE);

        omRoomApi.getUsersCompletedBooking("CompletedBooking",bookingRequest).enqueue(new Callback<CompletedBooking>() {
            @Override
            public void onResponse(Call<CompletedBooking> call, Response<CompletedBooking> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    CompletedBooking completedBooking = response.body();
                    if (completedBooking != null) {
                        if(completedBooking.getStatus().equals("Success")){
                            if(completedBooking.getCompletedBooking() != null){
                                progressLayout.setVisibility(View.GONE);
                                setUpcomingRv(completedBooking.getCompletedBooking());
                                Log.e(TAG_FRAGMENTB,"success"+new Gson().toJson(response.body()));
                                Log.e(TAG_FRAGMENTB,"success"+new Gson().toJson(completedBooking));
                            }else {
                                progressText.setText(R.string.no_record);
                            }
                        }else {
                            Toast.makeText(getActivity(), ""+completedBooking.getMsg(), Toast.LENGTH_SHORT).show();
                            progressText.setText(completedBooking.getMsg());
                        }
                    }else {
                        progressText.setText(R.string.no_record);
                    }


                }



            }

            @Override
            public void onFailure(Call<CompletedBooking> call, Throwable t) {
                Log.e(TAG_FRAGMENTB,"failed"+t.toString());
                progressBar.setVisibility(View.GONE);
                progressText.setText("No Record Found");
            }
        });


    }



    private void setUpcomingRv(List<UpComingBooking> upcommingBooking) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        upcomingRv.setLayoutManager(layoutManager);
        upcomingRv.setHasFixedSize(true);
        BookingDetailsAdapter adapter = new BookingDetailsAdapter("s", upcommingBooking,getActivity());

        upcomingRv.setAdapter(adapter);

    }


    //    public void setCurrentItem(int pos) {
//        mViewPager.setCurrentItem(pos);
//    }

}
