package org.sairaa.omowner.BookingDetails;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.CancelForm.CancelformRequest;
import org.sairaa.omowner.Model.CustomerBookings;
import org.sairaa.omowner.Model.RoomTypeDetails;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.Constants;
import org.sairaa.omowner.Utils.ConverterUtil;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import android.support.design.widget.BottomSheetDialog;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsHolder extends RecyclerView.ViewHolder implements Constants {
    private TextView bookingId;
    private TextView bookingTime;
    private TextView customerName;
    private TextView phoneNo;
    private TextView fromDate;
    private TextView noNights;
    private TextView toDate;
    private TextView paymentStatus;
    private TextView paidAmount;
    private TextView totalAmount;
    private TextView balance;
    private Button collectAmount;
    private TextView roomTypeAnaNo;
    private TextView guests;
    private BottomSheetDialog bottomSheetDialog;
    private SharedPreferenceConfig sharedPreferenceConfig;


    private ConstraintLayout checkInOutLayout;
    //Layout for short and details
    private ConstraintLayout headerLayout, detailsLayout;
    private ImageView downArrow, upArrow;

    private LinearLayout checkInLayout;
    private LinearLayout checkOutLayout;
    private LinearLayout manageLayout;

    private TextView checkInDate;
    private TextView checkInTime;
    private TextView checkOutDate;
    private TextView checkOutTime;
    private Button extend;
    private Button checkInOut;
    private Button cancel;
    String reason="asd";

    private BookingDetailsAdapter.BookingAdapterCallback mAdapterCallback;

    public BookingDetailsHolder(@NonNull View itemView) {
        super(itemView);

        bookingId = itemView.findViewById(R.id.booking_id);
        bookingTime = itemView.findViewById(R.id.booking_time);
        sharedPreferenceConfig = new SharedPreferenceConfig(itemView.getContext());


        customerName = itemView.findViewById(R.id.customer_name);
        phoneNo = itemView.findViewById(R.id.customer_phone_no);
        fromDate = itemView.findViewById(R.id.from_date);
        noNights = itemView.findViewById(R.id.no_nights);
        toDate = itemView.findViewById(R.id.to_date);
        paymentStatus = itemView.findViewById(R.id.payment_status);
        paidAmount = itemView.findViewById(R.id.paid_amount);
        totalAmount = itemView.findViewById(R.id.total_amount);
        balance = itemView.findViewById(R.id.to_be_paid);
        collectAmount = itemView.findViewById(R.id.collection_button);
        roomTypeAnaNo = itemView.findViewById(R.id.room_type_and_no);
        guests = itemView.findViewById(R.id.total_guests);

        checkInOutLayout = itemView.findViewById(R.id.check_in_check_out);
        checkInLayout = itemView.findViewById(R.id.check_in_layout);
        checkOutLayout = itemView.findViewById(R.id.check_out_layout);
        manageLayout = itemView.findViewById(R.id.manage_layout);

        checkInDate = itemView.findViewById(R.id.check_in_date);
        checkInTime = itemView.findViewById(R.id.check_in_time);
        checkOutDate = itemView.findViewById(R.id.check_out_date);
        checkOutTime = itemView.findViewById(R.id.check_out_time);
        extend = itemView.findViewById(R.id.extend);
        checkInOut = itemView.findViewById(R.id.check_in_view);
        cancel = itemView.findViewById(R.id.cancel_booking);

        headerLayout = itemView.findViewById(R.id.con_head_layout);
        detailsLayout = itemView.findViewById(R.id.con_details_layout);
        downArrow = itemView.findViewById(R.id.down_arrow);
        upArrow = itemView.findViewById(R.id.up_arrow);

    }

    public void set(CustomerBookings bookings) {


        String type = mAdapterCallback.getBookingTypeCheckInOrOut();


        bookingId.setText("Booking Id:".concat(bookings.getBooking_id()));
        bookingTime.setText("On:".concat(bookings.getBooking_date()));
        customerName.setText(bookings.getUser_name());
        phoneNo.setText(bookings.getPhone_no());

        downArrow.setVisibility(View.VISIBLE);

        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downArrow.setVisibility(View.INVISIBLE);
                upArrow.setVisibility(View.VISIBLE);
                detailsLayout.setVisibility(View.VISIBLE);
                double balanceAmount = 0.0;
                double paidAmountR = 0.0;
                if(ConverterUtil.getCheckInCheckOutType(type).equals(CompletedInActiveButton)){
                    //completed and inactive the check in and check out button
                    checkInOut.setVisibility(View.GONE);
                    extend.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    collectAmount.setVisibility(View.GONE);

                }else if(ConverterUtil.getCheckInCheckOutType(type).equals(CancelledInActiveButton)){
                    //completed and inactive the check in and check out button
                    checkInOut.setVisibility(View.GONE);
                    extend.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    collectAmount.setVisibility(View.GONE);

                }else {
                    checkInOut.setText(ConverterUtil.getCheckInCheckOutType(type));
                    if(ConverterUtil.getCheckInCheckOutType(type).equals(UpcomingCheckIn)){
                        cancel.setVisibility(View.VISIBLE);
                    }else {
                        cancel.setVisibility(View.GONE);
                    }
                }

                fromDate.setText(bookings.getFrom_date());
                noNights.setText(bookings.getNo_of_nights_booked().concat("N"));
                toDate.setText(bookings.getTo_date());

                //payment status
                String payStatus = "";
                switch (bookings.getPayment_status()){
                    case "n":
                        payStatus = payStatus.concat(" Not Paid");
                        break;
                    case "p":
                        payStatus = payStatus.concat(" Paid");
                        break;

                    default:
                        payStatus = payStatus.concat(" Paritallly paid");

                }
                paymentStatus.setText(payStatus);


                //paid amount and balance
                balanceAmount = Double.parseDouble(bookings.getPrice_to_be_paid());
                if(bookings.getPaidamount() == null){
                    paidAmountR = 0;
                }else {
                    paidAmountR = Double.parseDouble(bookings.getPaidamount());
                    balanceAmount = balanceAmount - paidAmountR;
                }
                paidAmount.setText(String.valueOf(Math.round(paidAmountR)));
                totalAmount.setText(bookings.getPrice_to_be_paid());
                balance.setText(String.valueOf(Math.round(balanceAmount)));

                if(bookings.getChecked_in_date() == null){
                    checkInLayout.setVisibility(View.GONE);
                }else {
                    checkInLayout.setVisibility(View.VISIBLE);
                    checkInDate.setText("".concat("Check In Date: ").concat(bookings.getChecked_in_date()));
                    checkInTime.setText("".concat("Check In Time: ").concat(bookings.getChecked_in_time()));
                }

                if(bookings.getChecked_out_date() == null){
                    checkOutLayout.setVisibility(View.GONE);
                }else {
                    checkOutLayout.setVisibility(View.VISIBLE);
                    checkOutDate.setText("".concat("Check Out Date: ").concat(bookings.getChecked_out_date()));
                    checkOutTime.setText("".concat("Check Out Time: ").concat(bookings.getChecked_out_time()));
                }

                if(bookings.getRoomtype() != null){
//            List<RoomTypeDetails> roomTypeDetails =  bookings.getRoomtype();
                    String roomTypeNo = "";
                    int noGuest = 0;
                    for (RoomTypeDetails roomD: bookings.getRoomtype())   {
                        roomTypeNo = roomTypeNo.concat(roomD.getRoom_type()).concat(" ").concat(roomD.getBooked().concat("\n"));

                        noGuest = noGuest+ Integer.parseInt(roomD.getTotalguests());
                    }

                    roomTypeAnaNo.setText(roomTypeNo);
                    guests.setText("No of Persons: ".concat(String.valueOf(noGuest)));
                }


                double finalBalanceAmount = balanceAmount;
                collectAmount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     //   mAdapterCallback.setPaymentRequest(finalBalanceAmount);
                        mAdapterCallback.getActionAndBookingList(CollectAmount,bookings);
                    }
                });

                extend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdapterCallback.getActionAndBookingList(Extend,bookings);
                    }
                });

                checkInOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdapterCallback.getActionAndBookingList(CheckInOut,bookings);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                     // bottom alertbox
                        Context context=v.getContext();
                        LayoutInflater inflater;
                        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        View view = inflater.inflate (R.layout.cancel_bottom_nav, null);

                        Button close = view.findViewById(R.id.close);
                        Button cancel = view.findViewById(R.id.cancelbooking);
                        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.radio_group);
                        RadioButton r1,r2,r3,r4,r5;
                        r1 = view.findViewById(R.id.changeinplan);
                        r2 = view.findViewById(R.id.bymistake);
                        r3 = view.findViewById(R.id.differenthotel);
                        r4 = view.findViewById(R.id.betterdeal);
                        r5 = view.findViewById(R.id.other);

                        final Dialog mBottomSheetDialog = new Dialog (context);
                        mBottomSheetDialog.setContentView (view);
                        mBottomSheetDialog.setCancelable (true);
                        mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
                        mBottomSheetDialog.show ();

                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                                if(r1.isChecked()){
                                    reason = "Change in plan";
                                }
                                else if (r2.isChecked()){
                                    reason = "Booking created by mistake";
                                }
                                else if (r3.isChecked()){
                                    reason = "Want to book a different hotel";
                                }
                                else if (r4.isChecked()){
                                    reason = "Found a better deal";
                                }
                                else {
                                    reason = "Others";
                                }
                            }
                        });

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(v.getContext(),"Clicked Backup",Toast.LENGTH_SHORT).show();
                                mBottomSheetDialog.dismiss();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {






if(reason.equals("asd")){
    Toast.makeText(context, "Choose reason", Toast.LENGTH_SHORT).show();
}
else {


    int bookid = Integer.parseInt(bookings.getBooking_id());
    OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
    //OmApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(OmApiInterface.class);
  //  Call<CancelformRequest> userRegisterCall = omRoomApi.canceluser(new CancelformRequest(Integer.parseInt(sharedPreferenceConfig.readHotelId()), sharedPreferenceConfig.readHotelIName(), bookid, "Want to book a different hotel"));
    Call<CancelformRequest> userRegisterCall = omRoomApi.canceluser(new CancelformRequest(Integer.parseInt(sharedPreferenceConfig.readHotelId()), sharedPreferenceConfig.readPhoneNo(), bookid, reason));
    userRegisterCall.enqueue(new Callback<CancelformRequest>() {
        @Override
        public void onResponse(Call<CancelformRequest> call, Response<CancelformRequest> response) {
            //  progressDialog.hide();
            if (response.isSuccessful()) {
                CancelformRequest dtos = response.body();
                if (dtos != null) {
                    if (dtos.getStatus().equals("Success")) ;
                    mAdapterCallback.cancelBooking(bookings.getBooking_id());
                    Toast.makeText(itemView.getContext(), dtos.getMsg(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(itemView.getContext(), "Data not Found!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<CancelformRequest> call, Throwable t) {
            // progressDialog.hide();
            Toast.makeText(itemView.getContext(), "Something went wrong!" + t, Toast.LENGTH_SHORT).show();
        }
    });


}



                             //   registerUser();
                               // mAdapterCallback.cancelBooking(bookings.getBooking_id());
                              //Toast.makeText(v.getContext(),"Cancelled Successfully",Toast.LENGTH_SHORT).show();
                                mBottomSheetDialog.dismiss();
                            }
                        });

                       // openBottomSheet(view);
                       // mAdapterCallback.cancelBooking(bookings.getBooking_id());

                    }
                });
            }
        });

        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downArrow.setVisibility(View.VISIBLE);
                upArrow.setVisibility(View.INVISIBLE);
                detailsLayout.setVisibility(View.GONE);
            }
        });

        detailsLayout.setVisibility(View.GONE);
    }

    public void setAdapertCallBack(BookingDetailsAdapter.BookingAdapterCallback mAdapterCallback) {
        this.mAdapterCallback = mAdapterCallback;
    }

   /* private void registerUser() {
       *//* name = etUser.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        gender = etGender.getText().toString().trim();*//*
      //  progressDialog.show();

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
       // OmApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(OmApiInterface.class);
        Call<CancelformRequest> userRegisterCall = omRoomApi.canceluser(new CancelformRequest(Integer.parseInt(sharedPreferenceConfig.readHotelId()),Integer.parseInt(sharedPreferenceConfig.readHotelIName()),475,"SuryaKommana"));
        userRegisterCall.enqueue(new Callback<CancelformRequest>() {
            @Override
            public void onResponse(Call<CancelformRequest> call, Response<CancelformRequest> response) {
              //  progressDialog.hide();
                if (response.isSuccessful()){
                    CancelformRequest dtos = response.body();
                    if (dtos!=null){
                        if (dtos.getStatus().equals("Success"));
                      Toast.makeText(itemView.getContext(),dtos.getMsg(),Toast.LENGTH_SHORT).show();
                       *//*   Intent intent = new Intent(RaiseIssueActivity.this,WelComeActivity.class);
                        intent.putExtra("Name",etUser.getText().toString().trim());
                        intent.putExtra("Email",etEmail.getText().toString().trim());
                        startActivity(intent);*//*

                    }
                }else {
                  //  Toast.makeText(,"Data not Found!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CancelformRequest> call, Throwable t) {
               // progressDialog.hide();
               // Toast.makeText(RaiseIssueActivity.this,"Something went wrong!"+t,Toast.LENGTH_SHORT).show();
            }
        });
    }*/





   /* private  void openBottomSheet(View v) {
        //View view = activity.getLayoutInflater ().inflate (R.layout.bottom_sheet, null);
        // View view = inflater.inflate( R.layout.bottom_sheet, null );
        Context context=v.getContext();
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate (R.layout.cancel_bottom_nav, null);
        Button close = view.findViewById(R.id.close);
        Button cancel = view.findViewById(R.id.cancelbooking);
      *//*  TextView txtBackup = (TextView)view.findViewById(R.id.txt_backup);
        TextView txtDetail = (TextView)view.findViewById(R.id.txt_detail);
        TextView txtOpen = (TextView)view.findViewById(R.id.txt_open);
        final TextView txtUninstall = (TextView)view.findViewById( R.id.txt_backup);*//*

        final Dialog mBottomSheetDialog = new Dialog (context);
        mBottomSheetDialog.setContentView (view);
        mBottomSheetDialog.setCancelable (true);
        mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
        mBottomSheetDialog.show ();


        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             //   Toast.makeText(v.getContext(),"Clicked Backup",Toast.LENGTH_SHORT).show();
                mAdapterCallback.cancelBooking(bookings.getBooking_id());
                mBottomSheetDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(),"Cancelled Successfully",Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });


    }*/

}