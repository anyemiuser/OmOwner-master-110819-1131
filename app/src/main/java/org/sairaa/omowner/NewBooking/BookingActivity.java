package org.sairaa.omowner.NewBooking;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.Collection.CollectionActivity;
import org.sairaa.omowner.NewBooking.Adapter.RoomTypeAdapter;
import org.sairaa.omowner.NewBooking.Model.Booking;
import org.sairaa.omowner.NewBooking.Model.BookingModel;
import org.sairaa.omowner.NewBooking.Model.BookingResponse;
import org.sairaa.omowner.NewBooking.Model.DiscountDetail;
import org.sairaa.omowner.NewBooking.Model.HotelDetails;
import org.sairaa.omowner.NewBooking.Model.ProfileDetails;
import org.sairaa.omowner.NewBooking.Model.RoomDetails;
import org.sairaa.omowner.NewBooking.Model.RoomPriceOnDate;
import org.sairaa.omowner.NewBooking.Model.RoomTypePrice;
import org.sairaa.omowner.NewBooking.Model.RoomsGuest;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.AllUtil;
import org.sairaa.omowner.Utils.ConverterUtil;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener, RoomTypeAdapter.RoomTypeAdapterCallback {

    private static final String TAG_BOOKING_ACTIVITY = BookingActivity.class.getName();
    private TextView checkInDate;
    private TextView checkOutDate;
    private TextView rooms;
    private TextView guests;
    private TextView nights;
    private Button bookRoom;
    private ImageView successImage;

    BookingViewModel bookingViewModel;
    private ViewPager viewPager;
    private AlertDialog alertDialog;
    private View view, viewX;
    private TextView roomDialogeCount, guestDialogCount;

    private SharedPreferenceConfig sharedPreferenceConfig;
    private int noOfRoom = 1;

    private RecyclerView roomRv;

    public static List<BookingModel> modelsForBooking = new ArrayList<>();
    public static List<DiscountDetail> discountDetails = new ArrayList<>();
    private int guestCount = 0;
    private RoomTypeAdapter adapter;
    private List<RoomTypePrice> roomDetails = new ArrayList<>();

    private ProgressBar progressBar;
    private TextView progressStatus;

    int getORRegister = 0; // 0 for get and 1 for register
    String gender = "";
    private String phoneNumber= "";

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        sharedPreferenceConfig = new SharedPreferenceConfig(this);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("New Booking");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);

        initialize();

        phoneNumber = getIntent().getStringExtra("phoneNo");

        bookingViewModel.init();

        this.getLifecycle().addObserver(bookingViewModel);
        //default date
        String dateS = ConverterUtil.parseDateToddMMMyyyy(ConverterUtil.getTodaysDate());
        bookingViewModel.setCheckIn(dateS);
        bookingViewModel.setChekoutOut(ConverterUtil.getDefaultCheckOutDateToNextDay(dateS));
        bookingViewModel.setNoOfDays(1);
//        checkProperDateInput(1,ConverterUtil.parseDateToddMMMyyyy(ConverterUtil.getTodaysDate()));

        bookingViewModel.setDefault();



        bookingViewModel.getRoomGuestList().observe(this, roomsGuests -> {
            if (roomsGuests != null) {
                assignValuesToRGView(roomsGuests);
                retrieveFreshRoomDetails();
            }
        });

        bookingViewModel.getIsUploading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
//                Toast.makeText(BookingActivity.this, ""+aBoolean, Toast.LENGTH_SHORT).show();
                Log.e("test",""+aBoolean);
            }
        });

        bookingViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    progressBar.setVisibility(VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        bookingViewModel.getLoadingText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                progressStatus.setText(s);
            }
        });

        bookingViewModel.getCheckInDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                checkInDate.setText(s);
                retrieveFreshRoomDetails();
                successImage.setVisibility(GONE);
                roomRv.setVisibility(VISIBLE);
                bookRoom.setVisibility(VISIBLE);
            }
        });

        bookingViewModel.getCheckOutDate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                checkOutDate.setText(s);
                retrieveFreshRoomDetails();
                successImage.setVisibility(GONE);
                roomRv.setVisibility(VISIBLE);
                bookRoom.setVisibility(VISIBLE);
            }
        });

        bookingViewModel.getNoOfDays().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                nights.setText(String.valueOf(integer).concat("N"));

            }
        });

        bookingViewModel.getHotelDetails().observe(this, new Observer<HotelDetails>() {
            @Override
            public void onChanged(@Nullable HotelDetails hotelDetails) {
                if(hotelDetails!= null){
                    retrieveRoomDetails(hotelDetails);
                    new SharedPreferenceConfig(BookingActivity.this).setHotelImageUrl(hotelDetails.getHotel_images_url());
                }else {
//                    bookingViewModel.clearHotelDetails();
                }

            }
        });


        adapter = new RoomTypeAdapter(this,bookingViewModel.getRoomTypesAvailable().getValue());
        roomRv.setAdapter(adapter);

        bookingViewModel.getRoomTypesAvailable().observe(this, new Observer<List<RoomTypePrice>>() {
            @Override
            public void onChanged(@Nullable List<RoomTypePrice> roomTypePrices) {
                adapter.updateList(roomTypePrices);
                adapter.notifyDataSetChanged();

            }
        });

    }

    private void retrieveFreshRoomDetails() {
        bookingViewModel.onRetrieveHotelDetails("HotelDetails",
                sharedPreferenceConfig.readHotelId(),
                ConverterUtil.parseDateToyyyymmdd(bookingViewModel.getCheckInDate().getValue()),
                ConverterUtil.parseDateToyyyymmdd(bookingViewModel.getCheckOutDate().getValue()),
                String.valueOf(noOfRoom));
    }

    private void assignValuesToRGView(List<RoomsGuest> roomsGuests) {
        rooms.setText(String.valueOf(roomsGuests.size()).concat(" Roooms"));
        noOfRoom = roomsGuests.size();
        guestCount = 0;
        for(RoomsGuest roomsG: roomsGuests){
            guestCount = guestCount + roomsG.getGuests();
        }
        guests.setText(String.valueOf(guestCount).concat(" Guests"));
        roomDialogeCount.setText(String.valueOf(roomsGuests.size()));
        guestDialogCount.setText(String.valueOf(guestCount));

    }

    private void initialize() {

        progressBar = findViewById(R.id.room_progress_bar);
        progressStatus = findViewById(R.id.room_available_status);

        // Alert Dialogue view
        view = getLayoutInflater().inflate(R.layout.room_guest_layout, null);
        viewX = getLayoutInflater().inflate(R.layout.user_profile_details,null);

        roomDialogeCount = (TextView)view.findViewById(R.id.room_count);
        guestDialogCount = (TextView)view.findViewById(R.id.guest_count);

        LinearLayout checkInLayout = findViewById(R.id.check_in_layout);
        checkInLayout.setOnClickListener(this);

        LinearLayout checkOutLayOut = findViewById(R.id.check_out_layout);
        checkOutLayOut.setOnClickListener(this);

        LinearLayout roomUserLayout = findViewById(R.id.room_user_layout);
        roomUserLayout.setOnClickListener(this);

        checkInDate = findViewById(R.id.check_in_date);
        checkOutDate = findViewById(R.id.check_out_date);
        nights = findViewById(R.id.no_of_nights);
        rooms = findViewById(R.id.no_of_rooms);
        guests = findViewById(R.id.no_of_user);

        bookRoom = findViewById(R.id.book_room);
        bookRoom.setOnClickListener(this);
        successImage = findViewById(R.id.success_image);
        successImage.setVisibility(GONE);

        roomRv = findViewById(R.id.room_rv_details);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        roomRv.setLayoutManager(linearLayoutManager);
        roomRv.setHasFixedSize(true);



//        viewPager = findViewById(R.id.view_pager);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.check_in_layout:
                popUpCalenderDialog(1,ConverterUtil.getTodaysDefaultDate());
                break;
            case R.id.check_out_layout:
                popUpCalenderDialog(2,ConverterUtil.getDefaultCheckOutDateToNextDay(bookingViewModel.getCheckInDate().getValue()));
                break;
            case R.id.room_user_layout:


                popUpRoomGuestDialog();
                assignValuesToRGView(Objects.requireNonNull(bookingViewModel.getRoomGuestList().getValue()));
                Toast.makeText(this, "ok"+bookingViewModel.getRoomGuestList().getValue().size(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.book_room:

                List<BookingModel> bookingModels = new ArrayList<>();
                List<RoomTypePrice> roomT = Objects.requireNonNull(bookingViewModel.getRoomTypesAvailable().getValue());

                int roomCount = 0;
                for(RoomTypePrice roomP: roomT){
                    roomCount = roomCount +roomP.getNoOfRoomSelected();
                }
                if(roomCount == noOfRoom){
                    takeUserDetailsAndBookRoom();
                }else {
                    ToastSnackMessage("Select minimum "+noOfRoom+ " Rooms");
                }

//


                break;

            case R.id.room_up:
                Toast.makeText(this, "up", Toast.LENGTH_SHORT).show();
                RoomsGuest roomsGuest = new RoomsGuest(1,2,0);
                bookingViewModel.insertNewRooomGuest(roomsGuest);
                break;
            case R.id.room_down:
                Toast.makeText(this, "down", Toast.LENGTH_SHORT).show();

                bookingViewModel.removeRoomGuests();
                break;
            case R.id.cancel_dialog:
                alertDialog.dismiss();
                break;
            case R.id.set_room_guests:
//                retrieveRoomDetails();
//                bookingViewModel.onRetrieveHotelDetails("HotelDetails",
//                        sharedPreferenceConfig.readHotelId(),
//                        ConverterUtil.parseDateToyyyymmdd(bookingViewModel.getCheckInDate().getValue()),
//                        ConverterUtil.parseDateToyyyymmdd(bookingViewModel.getCheckOutDate().getValue()),
//                        String.valueOf(noOfRoom));
                alertDialog.dismiss();

        }

    }

    private void takeUserDetailsAndBookRoom() {
        getORRegister = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(viewX.getParent() != null) {
            ((ViewGroup)viewX.getParent()).removeView(viewX); // <- fix
        }
        viewX = getLayoutInflater().inflate(R.layout.user_profile_details, null);
        builder.setView(viewX);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        EditText phoneNo = viewX.findViewById(R.id.phone_no_v);
        if(phoneNumber != null && !phoneNumber.isEmpty()){
            phoneNo.setText(phoneNumber);
        }
        phoneNo.setEnabled(true);
        Button getRegister = viewX.findViewById(R.id.get_register);
        EditText name = viewX.findViewById(R.id.name_text);
        EditText email = viewX.findViewById(R.id.email_text);
        EditText address = viewX.findViewById(R.id.address);
        Spinner spinner = viewX.findViewById(R.id.spinner);
        ProgressBar userLoadingProgress = viewX.findViewById(R.id.user_data_progress);
        LinearLayout userDateLayout = viewX.findViewById(R.id.details_layout);
        userDateLayout.setVisibility(GONE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                gender = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setSelection(1);
            }
        });



        Observer<String> userExistanceObserver = new Observer<String>(){
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    if(s.equals("exits")){
                        //                    bookingViewModel.updateProfileDetails();
                        getORRegister = 1;
                        getRegister.setText("Retrieve");
                        phoneNo.setEnabled(false);
                        userDateLayout.setVisibility(VISIBLE);
                    }else if(s.equals("registered")){
                        getORRegister = 2;
                        getRegister.setText("Update");
                        phoneNo.setEnabled(false);
                        userDateLayout.setVisibility(VISIBLE);
                    }else {
                        getORRegister = 0;
                        getRegister.setText("Get Or Register");
                        phoneNo.setEnabled(true);
                        userDateLayout.setVisibility(GONE);
                    }
                }
            }
        };

        bookingViewModel.getIsUserExistance().observe(this, userExistanceObserver);

        Observer<Boolean> isLoadingObserver = new Observer<Boolean>(){

            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean!= null){
                    if(aBoolean){
                        userLoadingProgress.setVisibility(VISIBLE);
                        getRegister.setVisibility(View.INVISIBLE);
                    }else {
                        userLoadingProgress.setVisibility(GONE);
                        getRegister.setVisibility(VISIBLE);
                    }
                }


            }
        };

        bookingViewModel.getIsUserDataUploading().observe(this,isLoadingObserver);


        Observer<ProfileDetails> profileObserver = new Observer<ProfileDetails>() {
            @Override
            public void onChanged(@Nullable ProfileDetails profileDetails) {
                if(profileDetails != null){
                    Log.e(TAG_BOOKING_ACTIVITY,"Profile:"+new Gson().toJson(profileDetails));
                    if(profileDetails.getUser_gender() != null){
                        name.setText(profileDetails.getUser_name());
                        email.setText(profileDetails.getUser_email_id());
                        address.setText(profileDetails.getUser_address());
                        switch (profileDetails.getUser_gender().toLowerCase()){
                            case "m":
                                spinner.setSelection(1);
                                break;
                            case "f":
                                spinner.setSelection(2);
                                break;
                            default:
                                spinner.setSelection(3);
                        }
                        getORRegister = 3;
                        getRegister.setText("Book");
                    }else {
                        getRegister.setText("Update");
                        getORRegister = 2;
                    }

                }
            }
        };

        bookingViewModel.getProfile().observe(this, profileObserver);

        viewX.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                phoneNo.setEnabled(true);
                getORRegister = 0;
                bookingViewModel.getProfile().removeObserver(profileObserver);
                bookingViewModel.getIsUserExistance().removeObserver(userExistanceObserver);
                bookingViewModel.getIsUserDataUploading().removeObserver(isLoadingObserver);
                bookingViewModel.setUserExistance("new user");
                bookingViewModel.setDefaultProfile();
                bookingViewModel.setIsUserLoadingDefault();
            }
        });

        getRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!phoneNo.getText().toString().trim().isEmpty()){
                    if(phoneNo.getText().toString().trim().length() == 10 ){
                        findUserStatusAndRegisterOrRetrieve(getORRegister, phoneNo.getText().toString().trim());
                        switch (getORRegister){
                            case 0:
                                //register new user or check user exist or not
                                bookingViewModel.findUserStatus(phoneNo.getText().toString().trim());
                                break;
                            case 1:
                                bookingViewModel.retrieveAndBook(phoneNo.getText().toString().trim());
                                break;
                            case 2:
                                //update
                                String nameX = name.getText().toString().trim();
                                String emailX=email.getText().toString().trim();
                                String addressX = address.getText().toString().trim();


                                if(!nameX.isEmpty()){
                                    if(!gender.isEmpty() && !gender.equals("Select Gender")){
                                        ProfileDetails profileDetails = new ProfileDetails(
                                                phoneNo.getText().toString().trim(),
                                                nameX,
                                                emailX,
                                                gender,
                                                "u",
                                                " ",
                                                addressX,
                                                "","","","");
                                        if(!emailX.isEmpty()){
                                            if(AllUtil.isValidEmail(emailX)){
                                                bookingViewModel.updateProfileDetails(profileDetails);
                                            }else {
                                                ToastSnackMessage("Enter Valid Email");
                                            }
                                        }else {
                                            bookingViewModel.updateProfileDetails(profileDetails);
                                        }
                                    }else {
                                        Toast.makeText(BookingActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
                                    }


                                }else {
                                    Toast.makeText(BookingActivity.this,"Enter User Name",Toast.LENGTH_LONG).show();
                                }

                                break;
                                default:
                                    alertDialog.dismiss();
                                    bookRooms(phoneNo.getText().toString().trim());
                                    phoneNo.setEnabled(true);
                                    getORRegister = 0;
                                    bookingViewModel.getProfile().removeObserver(profileObserver);
                                    bookingViewModel.getIsUserExistance().removeObserver(userExistanceObserver);
                                    bookingViewModel.setUserExistance("new user");
                                    bookingViewModel.setDefaultProfile();
                                    bookingViewModel.setIsUserLoadingDefault();


                        }

                    }else {
                        ToastSnackMessage("Enter 10 Digit Phone Number");
                    }
                }else {
                    ToastSnackMessage("Phone Number Can't be Empty");
                }

            }
        });


    }

    private void findUserStatusAndRegisterOrRetrieve(int getORRegister, String phoneNo) {

    }

    private void retrieveUserDetails(String phoneNo) {

    }

    private void bookRooms(String userId) {

        List<BookingModel> bookingModels = new ArrayList<>();
        List<RoomTypePrice> roomT = Objects.requireNonNull(bookingViewModel.getRoomTypesAvailable().getValue());

        int roomCount = 0;
        for(RoomTypePrice roomP: roomT){
            roomCount = roomCount +roomP.getNoOfRoomSelected();
        }
        if(roomCount == noOfRoom){
            int noOfNights = 0;
            noOfNights = Objects.requireNonNull(bookingViewModel.getNoOfDays().getValue());
            int noOfGuest = 0;

            for(RoomTypePrice room:roomT){
                if(room.getNoOfRoomSelected()>0){
                    BookingModel bookingModel = new BookingModel(Integer.parseInt(sharedPreferenceConfig.readHotelId()),
                            room.getRoom_type(),
                            room.getNoOfRoomSelected(),
                            noOfNights,
                            ConverterUtil.parseDateToyyyymmdd(bookingViewModel.getCheckInDate().getValue()),
                            ConverterUtil.parseDateToyyyymmdd(bookingViewModel.getCheckOutDate().getValue()),
                            "u",
                            "r",
                            String.valueOf(room.getNoOfRoomSelected()*2),
                            String.valueOf((int)Math.round(Double.parseDouble(room.getPayble_price())) * room.getNoOfRoomSelected()));

                    bookingModels.add(bookingModel);
                }
            }

            Log.e(TAG_BOOKING_ACTIVITY,""+new Gson().toJson(bookingModels));

            Booking booking = new Booking(userId,bookingModels);
            selectPaymentMode(booking);
            Log.e(TAG_BOOKING_ACTIVITY,""+new Gson().toJson(booking));


        }else {
            ToastSnackMessage("Select minimum "+noOfRoom+ " Rooms");
        }
    }

    private void selectPaymentMode(Booking booking) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Your Booking Procedure");
        builder.setCancelable(false);
        View mView = getLayoutInflater().inflate(R.layout.alert_layout, null);
        builder.setView(mView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        mView.findViewById(R.id.cancel_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setCancelable(true);
                alertDialog.dismiss();
            }
        });
        mView.findViewById(R.id.pay_at_hotel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setCancelable(true);
                alertDialog.dismiss();
                booking.setTransaction_id(null);
                booking.setTransaction_status(null);
                Log.e(TAG_BOOKING_ACTIVITY,"pay at hotel: "+new Gson().toJson(booking));
                bookRoomsWithPaymentStatus(booking);

            }
        });
        mView.findViewById(R.id.payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setCancelable(true);
                alertDialog.dismiss();

//                int id = Integer.parseInt(sharedPreferenceConfig.readPhoneNo());
                Double totalPrice =0.00;
                for(int i= 0; i<modelsForBooking.size();i++){
                    totalPrice = totalPrice+ modelsForBooking.get(i).getNo_of_room_booked()*Double.parseDouble(modelsForBooking.get(i).getPrice_to_be_paid());
                }

//                PaymentRequestModel paymentRequestModel = new PaymentRequestModel();
//
//                paymentRequestModel.setMobile_number(sharedPreferenceConfig.readPhoneNo());
//                paymentRequestModel.setEmi_ids( booking.getUser_id());
////                paymentRequestModel.setTotal_amount( String.valueOf(totalPrice));
//                paymentRequestModel.setTotal_amount( String.valueOf(1));
//
////                    Intent intent = new Intent(HotelActivity.this,PaymentActivity.class);
//                Intent intent = new Intent(HotelActivity.this, PaymentModeActivityNew.class);
//                intent.putExtra(Constants.PAYMENT_REQUEST_MODEL,new Gson().toJson(paymentRequestModel));
//                intent.putExtra("user_id",sharedPreferenceConfig.readPhoneNo());
                Intent intent = new Intent(BookingActivity.this, CollectionActivity.class);
                startActivityForResult(intent,5);

            }
        });
    }

    private void bookRoomsWithPaymentStatus(Booking booking) {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        bookingViewModel.setIsLoading(true);
        bookingViewModel.setLoadingText("Loading...");
        omRoomApi.bookRooms(booking).enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookingResponse> call, @NonNull Response<BookingResponse> response) {
                if(response.isSuccessful()){
                    BookingResponse bookingResponse = response.body();
                    if(bookingResponse!= null){
                        if(bookingResponse.getStatus().equals("Success") && bookingResponse.getMsg().equals("Successfully Booked")){
                            bookingViewModel.setLoadingText("Your Booking Is Successful\nYour Booking Id Is: "+bookingResponse.getBooking_id());
                            roomRv.setVisibility(View.GONE);
                            bookRoom.setVisibility(View.GONE);
                            findViewById(R.id.success_image).setVisibility(VISIBLE);
                        }
                        Log.e(TAG_BOOKING_ACTIVITY,"success"+new Gson().toJson(bookingResponse));
                    }else{
                        bookingViewModel.setLoadingText(""+response.message());
                    }
                }else {
                    Log.e(TAG_BOOKING_ACTIVITY,"success"+response.toString());
                    bookingViewModel.setLoadingText(""+response.message());
                }
                bookingViewModel.setIsLoading(false);

            }

            @Override
            public void onFailure(@NonNull Call<BookingResponse> call, @NonNull Throwable t) {
                Log.e(TAG_BOOKING_ACTIVITY,"success"+t.toString());
                bookingViewModel.setIsLoading(false);
                bookingViewModel.setLoadingText("Something Went Wrong: "+t.getMessage());
            }
        });
    }

    private void retrieveRoomDetails(HotelDetails hotelDetails) {
        if (hotelDetails != null && hotelDetails.getMsg().equals("Successfully send")) {

            roomDetails.clear();
//            adapter.updateList(null);
//            adapter.notifyDataSetChanged();

            List<RoomDetails> roomD = hotelDetails.getHoteldetails().getRoomdetails();
            if(roomD!= null){
                for(RoomDetails room:roomD){
                    if(room.getRoom_prices()!= null){
                        RoomTypePrice roomT = new RoomTypePrice();

                        roomT.setRoom_type(room.getRoom_type());
                        roomT.setRoom_master_image_url(room.getRoom_master_image_url());
                        roomT.setNoOfNights(room.getRoom_prices().size());
                        double basePrice = 0.00;
                        double discountPrice = 0.00;
                        double payblePrice = 0.00;
                        boolean isRoomAvailable = true ;
                        for(RoomPriceOnDate roomPrice: room.getRoom_prices()){
                            basePrice = basePrice+ Double.parseDouble(roomPrice.getPrice());
                            discountPrice = discountPrice + (Double.parseDouble(roomPrice.getPrice()) * Double.parseDouble(roomPrice.getDiscount()) / 100);
                            if(roomPrice.getAvailable_rooms() == 0){
                                isRoomAvailable = false;
                            }
                            Log.e(TAG_BOOKING_ACTIVITY,""+new Gson().toJson(roomPrice));

                        }
                        payblePrice = basePrice-discountPrice;
                        roomT.setBase_Price(String.valueOf((int)Math.round(basePrice)));
                        roomT.setDiscounted_price(String.valueOf((int)Math.round(discountPrice)));
                        roomT.setPayble_price(String.valueOf((int)Math.round(payblePrice)));
                        roomT.setNoOfRoomSelected(0);
                        roomT.setRoomAvailable(isRoomAvailable);
                        roomDetails.add(roomT);
                    }


                }
            }


            if(roomDetails.size()>0){
//                adapter.updateList(roomDetails);
//                adapter.notifyDataSetChanged();
                bookingViewModel.setRoomTypeList(roomDetails);
            }else {
                // Clear List
                bookingViewModel.setRoomTypeList(new ArrayList<>());
                bookingViewModel.setLoadingText("No Rooms Available");

            }
//            List<RoomUtility> facilities = ConverterUtil.checkFacilityAvailable(hotelDetails.getHoteldetails().getRoomdetails());
//            setDataToUI(hotelDetails.getHoteldetails());
//            Gson ggg = new Gson();
//
//            Log.e(TAG_HOTEL,""+ggg.toJson(hotelDetails));
//            Log.e(TAG_HOTEL,""+hotelDetails.getHoteldetails().getHotel_name());
////                                Log.e(TAG_HOTEL,""+hotelDetails.getHoteldetails().getRoomdetails().get(0).getRoom_prices().get(0).getAvailable_rooms());
//            setFacilityRv(facilities);
            int hotelIds = Integer.parseInt(hotelDetails.getHoteldetails().getHotel_id());
//                                booking.setUser_id(sharedPreferenceConfig.readPhoneNo());


            modelsForBooking.clear();
            discountDetails.clear();
            if(hotelDetails.getHoteldetails().getRoomdetails()!= null && hotelDetails.getHoteldetails().getRoomdetails().size() > 0){
                List<RoomDetails> listWithNoRoomPrice = new ArrayList<>();
                for(int i = 0;i<hotelDetails.getHoteldetails().getRoomdetails().size();i++){

                    RoomDetails roomDetails = hotelDetails.getHoteldetails().getRoomdetails().get(i);

                    String checkInD = ConverterUtil.parseDateToyyyymmdd(bookingViewModel.getCheckInDate().getValue());
                    String checkOutD = ConverterUtil.parseDateToyyyymmdd(bookingViewModel.getCheckOutDate().getValue());

                    if(roomDetails.getRoom_prices() != null && roomDetails.getRoom_prices().size()>0){
                        BookingModel bookingModel = new BookingModel(hotelIds,
                                roomDetails.getRoom_type(),
                                0,
                                roomDetails.getRoom_prices().size(),
                                checkInD,
                                checkOutD,
                                "u",
                                "u",
                                String.valueOf(guestCount),"0");
                        modelsForBooking.add(bookingModel);
                        DiscountDetail discountDetail = new DiscountDetail(roomDetails.getRoom_type());
                        discountDetails.add(discountDetail);

                    }else {
                        //if rooms are not available add to the list and then remove these from master list.
                        listWithNoRoomPrice.add(hotelDetails.getHoteldetails().getRoomdetails().get(i));
                    }

                }
                if(modelsForBooking.size()>0){
                    //removing from master list.
                    for(RoomDetails roomDetail: listWithNoRoomPrice){
                        hotelDetails.getHoteldetails().getRoomdetails().remove(roomDetail);
                    }
//                    roomAvailStatus.setText("Select Your Room Type >>");
//                    setRoomTypeRv(hotelDetails.getHoteldetails().getRoomdetails(),modelsForBooking);
//                    roomDetails = hotelDetails.getHoteldetails().getRoomdetails();
//                    adapter.updateList(roomDetails);
//                    adapter.notifyDataSetChanged();
                }
                else{
//                    setRoomTypeRvToNull();
//                    roomAvailStatus.setText("Rooms Not Available for the selected date");
//                    Toast.makeText(HotelActivity.this, "Rooms Not Available", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "Rooms Not Available", Toast.LENGTH_SHORT).show();
            }

        }else {

            Toast.makeText(this, ""+hotelDetails.getMsg(), Toast.LENGTH_SHORT).show();
        }

    }

    private void popUpRoomGuestDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(view.getParent() != null) {
            ((ViewGroup)view.getParent()).removeView(view); // <- fix
        }
        view = getLayoutInflater().inflate(R.layout.room_guest_layout, null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        view.findViewById(R.id.room_up).setOnClickListener(this);
        view.findViewById(R.id.room_down).setOnClickListener(this);
        view.findViewById(R.id.cancel_dialog).setOnClickListener(this);
        view.findViewById(R.id.set_room_guests).setOnClickListener(this);
        roomDialogeCount = (TextView)view.findViewById(R.id.room_count);
        guestDialogCount = (TextView)view.findViewById(R.id.guest_count);
    }

    private void popUpCalenderDialog(int checkInOut, String todaysDefaultDate) {

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

                        Toast.makeText(BookingActivity.this, ""+(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), Toast.LENGTH_SHORT).show();
                        String dateC = ""+year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;

                        checkProperDateInput(checkInOut,ConverterUtil.parseDateToddMMMyyyy(dateC));


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(ConverterUtil.ConvertDateToSetOnCalender(todaysDefaultDate));


        datePickerDialog.show();
    }

    private void checkProperDateInput(int checkInOut, String dateS) {
        switch (checkInOut){
            case 1:

                bookingViewModel.setCheckIn(dateS);
                int noDay = ConverterUtil.noOfDays(dateS,bookingViewModel.getCheckOutDate().getValue());
                if(noDay>0){
//                    bookingViewModel.setChekoutOut(ConverterUtil.getDefaultCheckOutDateToNextDay(dateS));
                    bookingViewModel.setNoOfDays(noDay);
                }else {
                    bookingViewModel.setChekoutOut(ConverterUtil.getDefaultCheckOutDateToNextDay(dateS));
                    noDay = ConverterUtil.noOfDays(dateS,bookingViewModel.getCheckOutDate().getValue());
                    bookingViewModel.setNoOfDays(noDay);
                }


                break;
            case 2:

                int noOfDay = ConverterUtil.noOfDays(bookingViewModel.getCheckInDate().getValue(),dateS);
                if( noOfDay > 0){
                    bookingViewModel.setChekoutOut(dateS);
                    bookingViewModel.setNoOfDays(noOfDay);
                }else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Select Higher Date Then Check In",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                break;
        }
    }

    @Override
    public void setBookedRoomCount(RoomTypePrice roomTypePrice, int i) {

        List<RoomTypePrice> roomT = Objects.requireNonNull(bookingViewModel.getRoomTypesAvailable().getValue());

        int roomCount = 0;
        for(RoomTypePrice roomP: roomT){
            roomCount = roomCount +roomP.getNoOfRoomSelected();
        }


        if(i>0){
            if(roomCount< noOfRoom){
                for(RoomTypePrice roomP: roomT){
                    if(roomTypePrice.getRoom_type().equals(roomP.getRoom_type())){
                        roomP.setNoOfRoomSelected(roomTypePrice.getNoOfRoomSelected()+i);
                    }
                }

//            bookingViewModel.setRoomTypeList(roomT);
            }else {
                ToastSnackMessage("You have Selected Only "+noOfRoom+ " Rooms");
            }
        }else if(i< 0){
            if(roomCount>0){
                for(RoomTypePrice roomP: roomT){
                    if(roomTypePrice.getRoom_type().equals(roomP.getRoom_type())){
                        roomP.setNoOfRoomSelected(roomTypePrice.getNoOfRoomSelected()+i);
                    }
                }

//            bookingViewModel.setRoomTypeList(roomT);
            }else {
                ToastSnackMessage("Number of room cannot be zero");
            }
        }

    }

    private void ToastSnackMessage(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
}
