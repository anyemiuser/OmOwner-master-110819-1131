package com.anyemi.omrooms.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.Adapters.FacilityListAdapter;
import com.anyemi.omrooms.Adapters.RoomTypeAdapter;
import com.anyemi.omrooms.Helper.RGuest;
import com.anyemi.omrooms.Model.Booking;
import com.anyemi.omrooms.Model.BookingModel;
import com.anyemi.omrooms.Model.BookingResponse;
import com.anyemi.omrooms.Model.DiscountDetail;
import com.anyemi.omrooms.Model.HotelAndRoomDetail;
import com.anyemi.omrooms.Model.HotelDetails;
import com.anyemi.omrooms.Model.PaymentRequestModel;
import com.anyemi.omrooms.Model.RoomDetails;
import com.anyemi.omrooms.Model.RoomFacility;
import com.anyemi.omrooms.Model.RoomsGuest;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.ConstantFields;
import com.anyemi.omrooms.Utils.ConverterUtil;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.anyemi.omrooms.payment.Constants;
import com.anyemi.omrooms.payment.PaymentModeActivityNew;
import com.anyemi.omrooms.payment.PaymentModesActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelActivity extends AppCompatActivity implements ConstantFields, View.OnClickListener {

    private static final String TAG_HOTEL = HotelActivity.class.getName();
    private ImageView hotelImage, locImage;
    private TextView hotelName,nearBy,rating,ratingTitle,noOfRating;
    private RecyclerView facilityRv,roomTypeRv;
    private Button bookRoom;

    private List<String> facilityList = new ArrayList<>();

    private LinearLayout checkInLayout, checkOutLayOut, roomUserLayout;
    private TextView checkInDate,checkOutDate,rooms,guests,nights;
    SharedPreferenceConfig sharedPreferenceConfig;
//    private List<RoomsGuest> roomsGuests = new ArrayList<>();
    public static List<BookingModel> modelsForBooking = new ArrayList<>();
    public static List<DiscountDetail> discountDetails = new ArrayList<>();

    private String hotelId;

    private HotelDetails hotelDetails;

    private Booking booking;

    private int noOfRoomsBooked = 0;

    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    private TextView messageT;

    private ProgressBar roomRvProgress;
    private TextView roomAvailStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        builder = new AlertDialog.Builder(this);
//        builder.setTitle("Select Your Booking Procedure");
        builder.setCancelable(false);
        View aView = getLayoutInflater().inflate(R.layout.progress_alert, null);
        messageT = aView.findViewById(R.id.progress_message);
        builder.setView(aView);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        alertDialog.setCancelable(false);

        init();
        assignValue(null,null);

        Intent intent =getIntent();
        hotelId = intent.getStringExtra("hotelId");
        String hotelName = intent.getStringExtra("hotelName");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setTitle(hotelName);
        }

        if(hotelId != null){
            showHotelDetails(hotelId);
        }
    }



    private void showHotelDetails(String hotelId) {
        Toast.makeText(this, ""+hotelId, Toast.LENGTH_SHORT).show();

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        roomRvProgress.setVisibility(View.VISIBLE);
        roomAvailStatus.setText("Checking Room Availability..");
        omRoomApi.getHotelDetails("HotelDetails",
                hotelId,
                ConverterUtil.changeDateFormate(sharedPreferenceConfig.readCheckInDate()),
                ConverterUtil.changeDateFormate(sharedPreferenceConfig.readCheckOutDate()),
                String.valueOf(sharedPreferenceConfig.readNoOfRooms()))
                .enqueue(new Callback<HotelDetails>() {
                    @Override
                    public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
                        roomRvProgress.setVisibility(View.GONE);
                        if(response.isSuccessful()){

                            hotelDetails = null;
                            hotelDetails = response.body();

                            if (hotelDetails != null && hotelDetails.getMsg().equals("Successfully send") && response.code() == 200) {
                                List<RoomFacility> facilities = ConverterUtil.checkFacilityAvailable(hotelDetails.getHoteldetails().getRoomdetails());
                                setDataToUI(hotelDetails.getHoteldetails());
                                Gson ggg = new Gson();

                                Log.e(TAG_HOTEL,""+ggg.toJson(hotelDetails));
                                Log.e(TAG_HOTEL,""+hotelDetails.getHoteldetails().getHotel_name());
//                                Log.e(TAG_HOTEL,""+hotelDetails.getHoteldetails().getRoomdetails().get(0).getRoom_prices().get(0).getAvailable_rooms());
                                setFacilityRv(facilities);
                                int hotelIds = Integer.parseInt(hotelDetails.getHoteldetails().getHotel_id());
//                                booking.setUser_id(sharedPreferenceConfig.readPhoneNo());
                                modelsForBooking.clear();
                                discountDetails.clear();
                                if(hotelDetails.getHoteldetails().getRoomdetails()!= null && hotelDetails.getHoteldetails().getRoomdetails().size() > 0){
                                    List<RoomDetails> listWithNoRoomPrice = new ArrayList<>();
                                    for(int i = 0;i<hotelDetails.getHoteldetails().getRoomdetails().size();i++){

                                        RoomDetails roomDetails = hotelDetails.getHoteldetails().getRoomdetails().get(i);

                                        String checkInD = ConverterUtil.changeDateFormate(sharedPreferenceConfig.readCheckInDate());
                                        String checkOutD = ConverterUtil.changeDateFormate(sharedPreferenceConfig.readCheckOutDate());

                                        if(roomDetails.getRoom_prices() != null && roomDetails.getRoom_prices().size()>0){
                                            BookingModel bookingModel = new BookingModel(hotelIds,
                                                    roomDetails.getRoom_type(),
                                                    0,
                                                    roomDetails.getRoom_prices().size(),
                                                    checkInD,
                                                    checkOutD,
                                                    "u",
                                                    "u",
                                                    String.valueOf(sharedPreferenceConfig.readNoOfGuests()),"0");
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
                                        roomAvailStatus.setText("Select Your Room Type >>");
                                        setRoomTypeRv(hotelDetails.getHoteldetails().getRoomdetails(),modelsForBooking);
                                    }
                                    else{
                                        setRoomTypeRvToNull();
                                        roomAvailStatus.setText("Rooms Not Available for the selected date");
                                        Toast.makeText(HotelActivity.this, "Rooms Not Available", Toast.LENGTH_SHORT).show();
                                    }

                                }else {
                                    Toast.makeText(HotelActivity.this, "Rooms Not Available", Toast.LENGTH_SHORT).show();
                                }

                            }else {

                                Toast.makeText(HotelActivity.this, ""+hotelDetails.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }else{


                            Log.e("Response not successful",""+response.toString());
                        }


                    }

                    @Override
                    public void onFailure(Call<HotelDetails> call, Throwable t) {
                        Log.e("error",""+t.getMessage());
                        roomRvProgress.setVisibility(View.GONE);
                        roomAvailStatus.setText("Something Went Wrong !!!");

                    }
                });
    }




    private void setRoomTypeRv(List<RoomDetails> roomdetails, List<BookingModel> modelsForBooking) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(HotelActivity.this);
        roomTypeRv.setLayoutManager(layoutManager);
        roomTypeRv.setHasFixedSize(true);
        RoomTypeAdapter roomTypeAdapter = new RoomTypeAdapter(roomdetails,HotelActivity.this,sharedPreferenceConfig.readNoOfRooms(),modelsForBooking);
        roomTypeRv.setAdapter(roomTypeAdapter);
    }
    private void setRoomTypeRvToNull(){
        roomTypeRv.setAdapter(null);

    }

    private void setFacilityRv(List<RoomFacility> facilities) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(HotelActivity.this, LinearLayoutManager.HORIZONTAL,false );
        facilityRv.setLayoutManager(layoutManager);
        facilityRv.setHasFixedSize(true);
        FacilityListAdapter facilityListAdapter = new FacilityListAdapter(facilities,HotelActivity.this);
        facilityRv.setAdapter(facilityListAdapter);

    }

    private void setDataToUI(HotelAndRoomDetail hotel) {

        Glide.with(this)
                .load(hotel.getHotel_image_url())
                .error(R.drawable.ic_location_city)
                // read original from cache (if present) otherwise download it and decode it
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(hotelImage);
        hotelName.setText(hotel.getHotel_name());
        String nearByPlace = hotel.getHotel_area()+", "+hotel.getHotel_city()+", "+hotel.getHotel_district();
        nearBy.setText(nearByPlace);
        double ratingHotel = 5;
        try{
            ratingHotel = Double.parseDouble(hotel.getHotel_rating());
        }catch (NullPointerException e){
            ratingHotel = 5;
        }

        rating.setText(new DecimalFormat("##.#").format(ratingHotel));
        ratingTitle.setText(ConverterUtil.getRatingText(ratingHotel));
        noOfRating.setText(hotel.getHotel_no_of_ratings().concat(" Review"));

    }

    private void init() {
//        private ImageView hotelImage;
//        private TextView hotelName,nearBy,rating,ratingTitle,noOfRating;
//        private RecyclerView facilityRv,roomTypeRv;
//        private Button bookRoom;
        hotelImage = findViewById(R.id.hotel_image);
        locImage = findViewById(R.id.location_placeholder);
        hotelName = findViewById(R.id.hotel_name_h);
        nearBy = findViewById(R.id.hotel_address);
        rating = findViewById(R.id.rating);
        ratingTitle = findViewById(R.id.rating_title);
        noOfRating = findViewById(R.id.no_of_ratings);
        bookRoom = findViewById(R.id.book_room);
        bookRoom.setOnClickListener(this);

        facilityRv = findViewById(R.id.facility_rv);
        roomTypeRv = findViewById(R.id.room_types_rv);

        checkInLayout = findViewById(R.id.check_in_layout);
        checkInLayout.setOnClickListener(this);

        checkOutLayOut = findViewById(R.id.check_out_layout);
        checkOutLayOut.setOnClickListener(this);

        roomUserLayout = findViewById(R.id.room_user_layout);
        roomUserLayout.setOnClickListener(this);

        sharedPreferenceConfig = new SharedPreferenceConfig(this);

        checkInDate = findViewById(R.id.check_in_date);
        checkOutDate = findViewById(R.id.check_out_date);
        nights = findViewById(R.id.no_of_nights);
        rooms = findViewById(R.id.no_of_rooms);
        guests = findViewById(R.id.no_of_user);

        roomRvProgress = findViewById(R.id.room_progress_bar);
        roomAvailStatus = findViewById(R.id.room_available_status);

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent resultIntent = getIntent();
        resultIntent.putExtra("checkIn",sharedPreferenceConfig.readCheckInDate());
        resultIntent.putExtra("checkOut",sharedPreferenceConfig.readCheckOutDate());
        resultIntent.putExtra("rooms",sharedPreferenceConfig.readNoOfRooms());
        resultIntent.putExtra("guests",sharedPreferenceConfig.readNoOfGuests());
//        ArrayList<Object> object = new ArrayList<Object>();
//        Intent intent = new Intent(Current.class, Transfer.class);
        if(RGuest.roomsGuests.size()>0){
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable)RGuest.roomsGuests);
            resultIntent.putExtra("BUNDLE",args);
        }

//        startActivity(intent);
//        resultIntent.putExtra("roomG",new Gson().toJson(roomsGuests));
        setResult(Activity.RESULT_OK,resultIntent);
//        roomsGuests.clear();
        onBackPressed();
        return true;
    }

    private void assignValue(String cIn, String cOut) {
        if(sharedPreferenceConfig.readCheckInDate() != null){

            if(RGuest.roomsGuests.size() == 0){
                RGuest.roomsGuests.add(new RoomsGuest(1,2));
                sharedPreferenceConfig.writeNoOfRooms(1);
                sharedPreferenceConfig.writeNoOfGuests(2);

            }

            boolean isChanged = false;
            if(cIn != null && cOut != null){

                if(cIn.equals(sharedPreferenceConfig.readCheckInDate())
                        && cOut.equals(sharedPreferenceConfig.readCheckOutDate())){
                    Log.e(TAG_HOTEL," date NOT CHANGED");
                }else {
                    Log.e(TAG_HOTEL,"date CHANGED");
                    sharedPreferenceConfig.writeCheckInDate(cIn);
                    sharedPreferenceConfig.writeCheckOutDate(cOut);
                    isChanged = true;
                }

            }
            checkInDate.setText(sharedPreferenceConfig.readCheckInDate());
            checkOutDate.setText(sharedPreferenceConfig.readCheckOutDate());
            String noNights = String.valueOf(ConverterUtil.noOfDays(sharedPreferenceConfig.readCheckInDate(),sharedPreferenceConfig.readCheckOutDate())).concat("N");
            nights.setText(noNights);
            int roomsCount= sharedPreferenceConfig.readNoOfRooms();
            int guestCount = 0;
            if(RGuest.roomsGuests.size()>0){
                for(int i=0;i<RGuest.roomsGuests.size();i++){
                    guestCount = guestCount + RGuest.roomsGuests.get(i).getGuests();
                }
                roomsCount = RGuest.roomsGuests.size();
            }
            if(roomsCount>0){
                if(roomsCount != sharedPreferenceConfig.readNoOfRooms()){
                    Log.e(TAG_HOTEL,"Room changed");
                    isChanged = true;
                }else {
                    Log.e(TAG_HOTEL,"Room  Not changed");
                }
                sharedPreferenceConfig.writeNoOfRooms(roomsCount);

            }else {
                sharedPreferenceConfig.writeNoOfRooms(1);

            }
            rooms.setText(String.valueOf(sharedPreferenceConfig.readNoOfRooms()).concat(" Rooms"));

            if(guestCount>0){
                if(guestCount != sharedPreferenceConfig.readNoOfGuests()){
                    Log.e(TAG_HOTEL,"Guest changed");
                    isChanged = true;
                }else {
                    Log.e(TAG_HOTEL,"Guest Not changed");
                }


                sharedPreferenceConfig.writeNoOfGuests(guestCount);
            }else {
                if(sharedPreferenceConfig.readNoOfGuests() == 0){
                    sharedPreferenceConfig.writeNoOfGuests(1);
                }

            }
            guests.setText(String.valueOf(sharedPreferenceConfig.readNoOfGuests()).concat(" Guests"));

            if(isChanged){
                showHotelDetails(hotelId);
                Log.e(TAG_HOTEL,"Queried resh data: showHotelDetails(hotelId)");
                isChanged = false;
            }
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(HotelActivity.this,CalenderActivity.class);
        switch (view.getId()){
            case R.id.check_in_layout:
                intent.putExtra("check",0);
                startActivityForResult(intent,1);
                break;
            case R.id.check_out_layout:
                intent.putExtra("check",1);
                startActivityForResult(intent,1);
                break;
            case R.id.room_user_layout:
                intent.putExtra("check",2);
                startActivityForResult(intent,1);
                break;
            case R.id.book_room:
                bookRoomsWithDetails();
                break;
        }
    }

    private void bookRoomsWithDetails() {

        booking = new Booking();
        int nroom = 0;
        for(int i= 0; i<modelsForBooking.size();i++){
            nroom = nroom+modelsForBooking.get(i).getNo_of_room_booked();
        }

        if(sharedPreferenceConfig.readNoOfRooms() == nroom){
            int startPoint = 0;

            //set no o guests in each roomtype booked
            for(int i= 0; i<modelsForBooking.size();i++){
                Log.e(TAG_HOTEL,"room details with guest"+new Gson().toJson(RGuest.roomsGuests));
//                nroom = nroom+modelsForBooking.get(i).getNo_of_room_booked();
                int noRoomBookedOnEachType= modelsForBooking.get(i).getNo_of_room_booked();
                int size = noRoomBookedOnEachType+startPoint;
                int nGuest = 0;
                for(int j = startPoint; j<size;j++){

                    nGuest = nGuest+ RGuest.roomsGuests.get(j).getGuests();
                    startPoint++;
                }

                modelsForBooking.get(i).setNo_of_guests(String.valueOf(nGuest));
            }
            //set price to be paid
            //set total price to be paid

            for(int i= 0; i<modelsForBooking.size();i++){
                Double price = modelsForBooking.get(i).getNo_of_room_booked()*Double.parseDouble(modelsForBooking.get(i).getPrice_to_be_paid());
                modelsForBooking.get(i).setPrice_to_be_paid(String.valueOf(price));

            }

            booking.setUser_id(sharedPreferenceConfig.readPhoneNo());

            booking.setBookingModels(modelsForBooking);

            bookingPriceRoomDetails(discountDetails,booking);


        }else {
            Toast.makeText(this, "Need to select "+sharedPreferenceConfig.readNoOfRooms()+" Rooms at least or Change", Toast.LENGTH_SHORT).show();
        }
        int hotelId = Integer.parseInt(hotelDetails.getHoteldetails().getHotel_id());


//        List<BookingModel> modelsForBooking = new ArrayList<>();
//
//        for(int i = 0;i<hotelDetails.getHoteldetails().getRoomdetails().size();i++){
//
//            RoomDetails roomDetails = hotelDetails.getHoteldetails().getRoomdetails().get(i);
//
//            BookingModel bookingModel = new BookingModel(hotelId,roomDetails.getRoom_type(),1,2,"2019-02-24","2019-02-26","q","u","2","2500.0");
//            modelsForBooking.add(bookingModel);
//        }










    }

    private void bookingPriceRoomDetails(List<DiscountDetail> discountDetails, Booking booking) {

        List<BookingModel> modelB = booking.getBookingModels();
        //removing roomDetails, if not added for booking.
        for(int i = 0; i<modelB.size();i++){
            BookingModel model = modelB.get(i);
            if(model.getNo_of_room_booked() == 0){
                modelB.remove(model);

                for(int j = 0;j<discountDetails.size();j++){
                    DiscountDetail detail = discountDetails.get(j);
                    if(detail.getRoomType().equals(model.getRoom_type())){
                        discountDetails.remove(detail);
                        j--;
                    }

                }
                i--;
            }
        }

        List<DiscountDetail> dDetails= discountDetails;
        for(int i = 0; i<discountDetails.size();i++){


        }

        TextView tBasePrice, tBaseStrickOut,tPayblePrice, tDiscountBenifits, roomNGP,roomD;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Booking Details");
        builder.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.alert_booking_details, null);
        builder.setView(view);
        double discount = 0;
        double basePrice = 0;
        double payblePrice= 0;
        for(DiscountDetail discountD : discountDetails){
            discount= discount+discountD.getDisCountPrice();
            basePrice = basePrice + discountD.getBasePrice();
            payblePrice = payblePrice + discountD.getPaybalePrice();
        }
        tBasePrice = (TextView)view.findViewById(R.id.original_price);
        tBasePrice.setText(getString(R.string.rupee_symbol).concat(new DecimalFormat("##.##").format(basePrice)));

        tBaseStrickOut = (TextView)view.findViewById(R.id.abase_price);
        tBaseStrickOut.setText(getString(R.string.rupee_symbol).concat(new DecimalFormat("##.##").format(basePrice)));
        tBaseStrickOut.setPaintFlags(tBaseStrickOut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        tPayblePrice = (TextView)view.findViewById(R.id.apayble_price);
        tPayblePrice.setText(getString(R.string.rupee_symbol).concat(new DecimalFormat("##.##").format(payblePrice)));

        tDiscountBenifits = (TextView)view.findViewById(R.id.discunt_benifits);
        tDiscountBenifits.setText(getString(R.string.rupee_symbol).concat(new DecimalFormat("##.##").format(discount)));

        int noOfRoom = 0;
        int noOfG= 0;
        int noNight = 0;

        String roomDetails = "";

        for(BookingModel model: booking.getBookingModels()){
            noOfRoom = noOfRoom + model.getNo_of_room_booked();
            noOfG = noOfG+ Integer.parseInt(model.getNo_of_guests());
            noNight = model.getNo_of_nights_booked();
            roomDetails = roomDetails.concat(String.valueOf(model.getNo_of_room_booked()))
                    .concat(" ")
                    .concat(model.getRoom_type())
                    .concat(" room for ")
                    .concat(model.getNo_of_guests())
                    .concat(" guest from ")
                    .concat(model.getFrom_date())
                    .concat(" to ")
                    .concat(model.getTo_date())
                    .concat(" .\n");

        }

        roomNGP = (TextView) view.findViewById(R.id.aroom_night_price);
        roomNGP.setText(String.valueOf(noOfRoom).concat(" ").concat(getString(R.string.room_for))
                .concat(String.valueOf(noOfG).concat(" ").concat(getString(R.string.guest_for))
                        .concat(String.valueOf(noNight)).concat(" ").concat(getString(R.string.night_small))));

        roomD = (TextView) view.findViewById(R.id.aroom_details);
        roomD.setText(roomDetails);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        view.findViewById(R.id.proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call for payment
                selectPaymentMode(booking);
                alertDialog.setCancelable(true);
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setCancelable(true);
                alertDialog.dismiss();
            }
        });



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
                Log.e(TAG_HOTEL,"pay at hotel: "+new Gson().toJson(booking));
                bookRooms(booking);

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

                PaymentRequestModel paymentRequestModel = new PaymentRequestModel();

                paymentRequestModel.setMobile_number(sharedPreferenceConfig.readPhoneNo());
                paymentRequestModel.setEmi_ids( booking.getUser_id());
//                paymentRequestModel.setTotal_amount( String.valueOf(totalPrice));
                paymentRequestModel.setTotal_amount( String.valueOf(1));

//                    Intent intent = new Intent(HotelActivity.this,PaymentActivity.class);
                Intent intent = new Intent(HotelActivity.this, PaymentModeActivityNew.class);
                intent.putExtra(Constants.PAYMENT_REQUEST_MODEL,new Gson().toJson(paymentRequestModel));
//                intent.putExtra("user_id",sharedPreferenceConfig.readPhoneNo());
                startActivityForResult(intent,5);

            }
        });
    }

    private void bookRooms(Booking booking) {
        List<BookingModel> modelB = booking.getBookingModels();
        for(int i = 0; i<modelB.size();i++){
            BookingModel model = modelB.get(i);
            if(model.getNo_of_room_booked() == 0){
                modelB.remove(model);
                i--;
            }
        }
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        alertDialog = builder.create();
        alertDialog.show();
        messageT.setText("Waiting for Conformation...");
        alertDialog.setCancelable(false);

        omRoomApi.bookRooms(booking).enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if(response.isSuccessful()){
                    alertDialog.dismiss();
                    Log.e(TAG_HOTEL,"success"+response.message()+new Gson().toJson(response.body()));
                    BookingResponse bookingResponse = response.body();
                    Log.e(TAG_HOTEL,response.message()+bookingResponse.getBooking_id());
                    Intent intent = new Intent(HotelActivity.this,BookingConActivity.class);
                    intent.putExtra("bid",""+bookingResponse.getBooking_id());
                    startActivity(intent);
                    finish();
                }else {
                    alertDialog.dismiss();
                    Log.e(TAG_HOTEL,"failed: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG_HOTEL,"failed: "+t.toString()+call.request());
                alertDialog.dismiss();
            }
        });

        Log.e(TAG_HOTEL,"asd : "+new Gson().toJson(booking));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();

            String cIn = null;
            String cOut = null;
            if (data != null) {
                cIn = data.getStringExtra("checkIn");
                cOut = data.getStringExtra("checkOut");
            }

            Bundle args = data.getBundleExtra("BUNDLE");

            if(args!=null){
                RGuest.roomsGuests = (List<RoomsGuest>) args.getSerializable("ARRAYLIST");
            }


            String checkInDate = sharedPreferenceConfig.readCheckInDate();
            if(cOut != null){
                assignValue(cIn,cOut);
                Toast.makeText(this, ""+checkInDate+sharedPreferenceConfig.readCheckOutDate()+sharedPreferenceConfig.readNoOfRooms()+sharedPreferenceConfig.readNoOfGuests(), Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, ""+cIn+"he", Toast.LENGTH_SHORT).show();
        }else if(requestCode == 5 && resultCode == RESULT_OK){

            String transactionId = null;
            String status = null;
            if(data!= null){
                transactionId = data.getStringExtra("transactionId");
                status = data.getStringExtra("status");

                Log.e(TAG_HOTEL,""+transactionId);

                booking.setTransaction_id(transactionId);
                booking.setTransaction_status(status);
                Log.e(TAG_HOTEL,"check : "+new Gson().toJson(booking));
                bookRooms(booking);
            }

        }

    }

    public int addRoomsForBooking(int position) {
        noOfRoomsBooked++;
        int size = sharedPreferenceConfig.readNoOfRooms();
        return noOfRoomsBooked;

    }

    public int removeRoomsForBooking(int position) {
        noOfRoomsBooked--;
        int size = sharedPreferenceConfig.readNoOfRooms();
        return noOfRoomsBooked;
    }

    public void activateBooking() {


    }
}
