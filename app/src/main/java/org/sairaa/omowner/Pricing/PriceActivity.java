package org.sairaa.omowner.Pricing;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.Main.MainActivity;
import org.sairaa.omowner.Pricing.Model.PriceRequest;
import org.sairaa.omowner.Pricing.Model.PriceResponse;
import org.sairaa.omowner.Pricing.Model.RoomType;
import org.sairaa.omowner.Pricing.Model.RoomTypeAndPrice;
import org.sairaa.omowner.Pricing.Model.RoomTypeList;
import org.sairaa.omowner.Pricing.Model.RoomTypeRequest;
import org.sairaa.omowner.Pricing.Model.UpdateRoomPriceRequest;
import org.sairaa.omowner.Pricing.Model.UpdateRoomPriceResponse;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.ConverterUtil;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceActivity extends AppCompatActivity implements View.OnClickListener, RoomPriceAdapter.RoomPriceAdapterCallback {

    private static final String TAG_PRICING = PriceActivity.class.getName();
    private ImageView calender;
    private String retrieveDate;
    private RecyclerView pricingRv;
    private Button update;
    private PricingViewModel pricingViewModel;
    private ConstraintLayout progressLayout;
    private ProgressBar progressBar;
    private TextView progressTextV;
    private List<RoomTypeAndPrice> roomPriceList = new ArrayList<>();
    private RoomPriceAdapter adapter;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        initialize();

//        popUpCalenderView();
        retrieveDate = ConverterUtil.getTodaysDate();
        PriceRequest request = new PriceRequest(sharedPreferenceConfig.readHotelId(),retrieveDate);
        pricingViewModel.onRetrieveRoomPriceOnDate(request);
        pricingViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean!= null && aBoolean){
                    progressLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    pricingRv.setVisibility(View.INVISIBLE);
                    update.setVisibility(View.INVISIBLE);

                }else {
                    progressBar.setVisibility(View.GONE);
                    pricingRv.setVisibility(View.VISIBLE);
                    update.setVisibility(View.VISIBLE);
                }
            }
        });

        pricingViewModel.getLoadingText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                progressTextV.setText(s);
            }
        });



        Observer<PriceResponse> priceResponseObserver = new Observer<PriceResponse>() {
            @Override
            public void onChanged(@Nullable PriceResponse priceResponse) {
                if (priceResponse != null && priceResponse.getGetRoomPriceWithType() != null) {
                    roomPriceList = priceResponse.getGetRoomPriceWithType();
                    adapter.updateList(roomPriceList);
                    update.setVisibility(View.VISIBLE);
                    update.setText("Update");
                }else {
                    //No Price Available on that date
                    // So retrieve only room type available under the hotel and
                    // feed price details to update
                    pricingViewModel.setLoadingText("Price Not Feed For This Date");
                    roomPriceList = new ArrayList<>();
                    adapter.updateList(roomPriceList);
                    update.setText("Feed And Update");
                    retrieveRoomType();
                }
            }
        };

        pricingViewModel.getRoomPriceAndType().observe(this, priceResponseObserver);
    }

    private void initialize() {

        sharedPreferenceConfig = new SharedPreferenceConfig(this);

        Toolbar toolbar = findViewById(R.id.toolbar_price);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(ConverterUtil.getTodaysDate());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        pricingViewModel =  ViewModelProviders.of(this).get(PricingViewModel.class);

        calender = findViewById(R.id.calender_price);
        calender.setOnClickListener(this);


        pricingRv = findViewById(R.id.pricing_rv);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        pricingRv.setLayoutManager(linearLayoutManager);
        pricingRv.setHasFixedSize(true);
        adapter = new RoomPriceAdapter(roomPriceList,this);
        pricingRv.setAdapter(adapter);

        update = findViewById(R.id.update_price);
        update.setOnClickListener(this);

        progressLayout = findViewById(R.id.progress_l);
        progressBar = findViewById(R.id.progressBar);
        progressTextV = findViewById(R.id.progress_text);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calender_price:
                popUpCalenderView();
                break;
            case R.id.update_price:
                updatePriceToServer();
                break;
        }
    }

    private void updatePriceToServer() {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        if(pricingViewModel.getRoomPriceList().size()>0){
            UpdateRoomPriceRequest priceRequest= new UpdateRoomPriceRequest(sharedPreferenceConfig.readHotelId(),
                    retrieveDate,
                    sharedPreferenceConfig.readPhoneNo(),
                    pricingViewModel.getRoomPriceList());
            Log.e(TAG_PRICING,"update:"+retrieveDate+new Gson().toJson(priceRequest));
            pricingViewModel.setIsLoading(true);
            pricingViewModel.setLoadingText("Updating Room Price..\nPlease Wait...");
            omRoomApi.updateRoomPriceDetails(priceRequest).enqueue(new Callback<UpdateRoomPriceResponse>() {
                @Override
                public void onResponse(Call<UpdateRoomPriceResponse> call, Response<UpdateRoomPriceResponse> response) {
                    pricingViewModel.setIsLoading(false);
                    if(response.isSuccessful()){
                        UpdateRoomPriceResponse roomPriceResponse = response.body();
                        if(roomPriceResponse!= null){
                            if(roomPriceResponse.getStatus().equals("success") && roomPriceResponse.getMessage().equals("success update")){
                                pricingViewModel.setLoadingText("Updated Successfully");
                                pricingViewModel.setDefaultRoomPrice();
                            }
                        }else {
                            pricingViewModel.setLoadingText("Something Went Wrong");
                        }
                    }else {
                        pricingViewModel.setLoadingText(response.message());
                    }
                }

                @Override
                public void onFailure(Call<UpdateRoomPriceResponse> call, Throwable t) {
                    pricingViewModel.setIsLoading(false);
                    pricingViewModel.setLoadingText("Something Went Wrong\n"+t.getMessage());

                }
            });
        }else {
            setMessage("Kindly edit and Save");
        }

    }

    public void popUpCalenderView() {
//        mainPresenter.setParameterToRetrieveBookingsCount(hotelId,dateC,day);

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Toast.makeText(PriceActivity.this, ""+(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), Toast.LENGTH_SHORT).show();
                        String dateC = ""+year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;
                        retrieveDate = dateC;
                        Toast.makeText(PriceActivity.this, ""+dateC, Toast.LENGTH_SHORT).show();
                        updateTitle(retrieveDate);

                        PriceRequest request = new PriceRequest(new SharedPreferenceConfig(PriceActivity.this).readHotelId(),retrieveDate);

                        //set default(Clear the list) room price for update
                        pricingViewModel.setDefaultRoomPrice();

                        pricingViewModel.onRetrieveRoomPriceOnDate(request);

//                      mainPresenter.setParameterToRetrieveBookingsCount(sharedPreferenceConfig.readHotelId(),retrieveDate,"Later");

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    private void updateTitle(String retrieveDate) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(retrieveDate);
//        retrieveRoomType();
    }

    private void retrieveRoomType() {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        String hotelId = new SharedPreferenceConfig(this).readHotelId();
        RoomTypeRequest roomTypeRequest = new RoomTypeRequest(hotelId);
        omRoomApi.getRoomType(roomTypeRequest).enqueue(new Callback<RoomTypeList>() {
            @Override
            public void onResponse(Call<RoomTypeList> call, Response<RoomTypeList> response) {
                if(response.isSuccessful()){
                    RoomTypeList roomTypeList = response.body();
                    if(roomTypeList.getStatus().equals("Success")
                            && roomTypeList.getMsg().equals("Success")){
                        if(roomTypeList.getGetRoomType() != null){
                            for(RoomType roomType: roomTypeList.getGetRoomType()){
                                roomPriceList.add(new RoomTypeAndPrice(roomType.getRoom_type()));
                            }
                            adapter.updateList(roomPriceList);
                            Log.e(TAG_PRICING,""+new Gson().toJson(roomTypeList));
                        }
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<RoomTypeList> call, Throwable t) {

            }
        });
    }

    @Override
    public void saveRoomPrice(RoomTypeAndPrice roomPrice) {
        pricingViewModel.saveRoomPriceAndType(roomPrice);
    }

    @Override
    public void setMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
//                        .setAction("Action", null).show();
    }
}
