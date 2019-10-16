package org.sairaa.omowner.Main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.Api.RetrofitClient;
import org.sairaa.omowner.BookingDetails.BookingDetailsActivity;
import org.sairaa.omowner.CheckIn.CheckInActivity;
import org.sairaa.omowner.HomeRules.HomeRulesActivity;
import org.sairaa.omowner.HotelContact.YourHotelContacts;
import org.sairaa.omowner.LoginActivity;
import org.sairaa.omowner.Model.AllBookingCount;
import org.sairaa.omowner.Model.RoomTarrif;
import org.sairaa.omowner.Model.RoomTypeCount;
import org.sairaa.omowner.Model.TotalAvailableRoom;
import org.sairaa.omowner.Model.TotalRooms;
import org.sairaa.omowner.NewBooking.BookingActivity;
import org.sairaa.omowner.Policies.PoliciesActivity;
import org.sairaa.omowner.Pricing.PriceActivity;
import org.sairaa.omowner.R;
import org.sairaa.omowner.RaiseIssue.RaiseIssueActivity;
import org.sairaa.omowner.RaiseIssue.RaiseIssueRequest;
import org.sairaa.omowner.RoomUtility.UtilityActivity;
import org.sairaa.omowner.Support.OmSupportActivity;
import org.sairaa.omowner.Utils.Constants;
import org.sairaa.omowner.Utils.ConverterUtil;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MainContract.View,
        MainBookingCountAdapter.AdapterCallback,
        RoomTariffAdapter.TariffAdapterCallback,
        AvailableRoomAdapter.AvailableRoomAdapterCallBack,
        Constants {

    MainPresenter mainPresenter;



    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    private Spinner spinner;
    private ImageView calender;
    private CalendarView calendarView;
    private MainBookingCountAdapter bookingCountAdapter;
    private AvailableRoomAdapter availableRoomAdapter;
    private RoomTariffAdapter tariffAdapter;
    private List<AllBookingCount> bookingCountList = new ArrayList<>();
    private List<RoomTarrif> roomTariffList = new ArrayList<>();
    private List<TotalAvailableRoom> availavleRoomTypeList = new ArrayList<>();

    private SharedPreferenceConfig sharedPreferenceConfig;

    private static String retrieveDate = "";
    public static String bookdrm,totalrm;

    private String day;
    private String index;
    private String date;
    private String hotelId;

    ConstraintLayout progressLayout;
    ProgressBar progressBar;
    TextView progressText;

    String category,subcategory,subsubcategory,comment;

    private TextView dateFor, eodPer, eodRoomType;

    @Override
    protected void onResume() {
        super.onResume();
        if(day!= null){
            switch (day){
                case "Today":
                    retrieveDate = ConverterUtil.getTodaysDate();
                    mainPresenter.onSpinnerSelection(sharedPreferenceConfig.readHotelId(),retrieveDate ,day);
                    index = "Today";
                    break;

                case "Tomorrow":
                    day = "Later";
                    retrieveDate = ConverterUtil.getTodaysDate();
                    mainPresenter.onSpinnerSelection(sharedPreferenceConfig.readHotelId(), retrieveDate,day);
                    break;

                case "Select Date":
                    index = "Later";
//                        day = "Later";
//                retrieveDate = ConverterUtil.getTodaysDate();
//                mainPresenter.onSpinnerSelection(sharedPreferenceConfig.readHotelId(), retrieveDate,day);
                    mainPresenter.setParameterToRetrieveBookingsCount(sharedPreferenceConfig.readHotelId(),retrieveDate,"Later");
//                        enableDateIcon();
//                        popUpCalenderView();
                    break;

                default:
//                        popUpCalenderView();
            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        init();
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        if(sharedPreferenceConfig.readPhoneNo() == null){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }else {


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_price);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
            }
            mainPresenter = new MainPresenter(this);



            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this, R.array.recent, R.layout.spinner_layout);
            adapter.setDropDownViewResource(R.layout.spinner_layout);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    day = adapterView.getSelectedItem().toString();
                    switch (day){
                        case "Today":
                            retrieveDate = ConverterUtil.getTodaysDate();
                            mainPresenter.onSpinnerSelection(sharedPreferenceConfig.readHotelId(),retrieveDate ,day);
                            index = "Today";
                            break;

                        case "Tomorrow":
                            day = "Later";
                            retrieveDate = ConverterUtil.getTodaysDate();
                            mainPresenter.onSpinnerSelection(sharedPreferenceConfig.readHotelId(), retrieveDate,day);
                            break;

                        case "Select Date":
//                        day = "Later";
                            retrieveDate = ConverterUtil.getTodaysDate();
                            mainPresenter.onSpinnerSelection(sharedPreferenceConfig.readHotelId(), retrieveDate,day);
                            index = "Later";
//                        enableDateIcon();
//                        popUpCalenderView();
                            break;

                        default:
//                        popUpCalenderView();
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            calender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainPresenter.onCalenderIconSelection();
                }
            });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.getMenu().getItem(3).setActionView(R.layout.menu_image);


            View headerView = navigationView.getHeaderView(0);
            ImageView hotelImage = headerView.findViewById(R.id.hotel_imageView);
            TextView hotelName = headerView.findViewById(R.id.hotel_name_text);
            TextView hotelPhoneNo = headerView.findViewById(R.id.hotel_phone_no);

//        if(sharedPreferenceConfig.getHotelImageUrl() != null){
//            Glide.with(this)
//                    .load(sharedPreferenceConfig.getHotelImageUrl())
////                .placeholder(circularProgressDrawable)
////                .error(R.drawable.hot)
//                    // read original from cache (if present) otherwise download it and decode it
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(hotelImage);
//        }

            if(sharedPreferenceConfig.readPhoneNo() != null){
                hotelPhoneNo.setText(sharedPreferenceConfig.readPhoneNo());
            }

            if(sharedPreferenceConfig.readHotelIName() != null){
                hotelName.setText(sharedPreferenceConfig.readHotelIName());
            }
        }

    }

    private void init() {
        dateFor = findViewById(R.id.date_looking_for);
        eodPer = findViewById(R.id.eod_per);
        eodRoomType = findViewById(R.id.eod_room_per);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progress_text);
        progressLayout = findViewById(R.id.progress_la);

        spinner = (Spinner) findViewById(R.id.spinner_tool);
        calender = findViewById(R.id.calender_tool);

        RecyclerView bookingCountRV = findViewById(R.id.recycler_booking_count);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        bookingCountRV.setLayoutManager(layoutManager);
        bookingCountAdapter = new MainBookingCountAdapter(this);
        bookingCountRV.setAdapter(bookingCountAdapter);

        RecyclerView availableRoomRV = findViewById(R.id.recycler_view_available_room);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false);
        availableRoomRV.setLayoutManager(gridLayoutManager);
        availableRoomAdapter = new AvailableRoomAdapter(this);
        availableRoomRV.setAdapter(availableRoomAdapter);

        RecyclerView roomTariffRV = findViewById(R.id.recycler_view_room_price);
        LinearLayoutManager roomLayoutManager = new LinearLayoutManager(this);
        roomTariffRV.setLayoutManager(roomLayoutManager);
        tariffAdapter = new RoomTariffAdapter(this);
        roomTariffRV.setAdapter(tariffAdapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        if(id == R.id.action_sign_out){
            mainPresenter.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        NavigationView nv= (NavigationView) findViewById(R.id.nav_view);

        Menu m=nv.getMenu();

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, BookingActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent( this, PriceActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, UtilityActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_utility) {

                boolean b=!m.findItem(R.id.nav_room).isVisible();
                //setting submenus visible state
                m.findItem(R.id.nav_room).setVisible(b);
                m.findItem(R.id.nav_guest_reviews).setVisible(b);
                m.findItem(R.id.nav_hotel_rules).setVisible(b);
                m.findItem(R.id.nav_your_hotel_Contacts).setVisible(b);
                m.findItem(R.id.nav_omsupport).setVisible(b);
                m.findItem(R.id.nav_raiseissue).setVisible(b);
                m.findItem(R.id.nav_policies).setVisible(b);
                m.findItem(R.id.nav_help).setVisible(b);
                return true;
        }else if (id == R.id.nav_raiseissue) {

            Intent intent = new Intent(this, RaiseIssueActivity.class);
            startActivity(intent);

           /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            View mview = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
            alertDialog.setTitle("Raise an Issue");
            final Spinner sp = mview.findViewById(R.id.spinner);
            final Spinner sp1 = mview.findViewById(R.id.spinner1);
            final Spinner sp3 = mview.findViewById(R.id.spinner3);
            final EditText textArea_information = mview.findViewById(R.id.textArea_information);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item
                    ,getResources().getStringArray(R.array.Main));



            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adapter);

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    if(selectedItem.equals("Guest Experience"))
                    {
                        // do your stuff
                        sp1.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item
                                ,getResources().getStringArray(R.array.Sub_Guest));
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp1.setAdapter(adapter1);


                    }
                    else if(selectedItem.equals("Manage Property on OM App"))

                    {
                        // do your stuff
                        sp1.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item
                                ,getResources().getStringArray(R.array.Sub_Manage));

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp1.setAdapter(adapter1);

                    }
                    else if(selectedItem.equals("Revenue related enquiry"))
                    {
                        // do your stuff
                        sp1.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item
                                ,getResources().getStringArray(R.array.Sub_Revenue));

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp1.setAdapter(adapter1);

                    }
                    else if(selectedItem.equals("General Requests and Feedback"))
                    {

                        // do your stuff
                        sp1.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item
                                ,getResources().getStringArray(R.array.Sub_General));

                        // adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp1.setAdapter(adapter1);

                    }
                    else  if(selectedItem.equals("Products and Services"))
                    {
                        // do your stuff
                        sp1.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item
                                ,getResources().getStringArray(R.array.Sub_General));

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp1.setAdapter(adapter1);

                    }
                    else  if(selectedItem.equals("Tech Issues"))
                    {
                        // do your stuff
                        sp1.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item
                                ,getResources().getStringArray(R.array.Sub_Tech));

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp1.setAdapter(adapter1);

                    }
                    else  if(selectedItem.equals("Value Added Services Enquiry"))
                    {
                        // do your stuff

                        sp1.setVisibility(View.GONE);

                    }
                    else  if(selectedItem.equals("Others"))
                    {
                        // do your stuff
                        sp1.setVisibility(View.GONE);

                    }


                } // to close the onItemSelected
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adapter);

            sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {

                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    if (selectedItem.equals("Update Owner info")) {
                        // do your stuff
                        sp3.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item
                                , getResources().getStringArray(R.array.Sub_sub_Update));
                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp3.setAdapter(adapter3);


                    }
                    else {
                        sp3.setVisibility(View.GONE);
                    }
                }
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });






            alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(!textArea_information.getText().toString().equals("")) {


                        if (!sp.getSelectedItem().toString().equals("Select Category")) {
                            category = sp.getSelectedItem().toString();
                            comment = textArea_information.getText().toString();


                            if (sp.getSelectedItem().toString().equals("Issue with OM Staff")
                                    && sp.getSelectedItem().toString().equals("Value Added Services Enquiry")
                                    && sp.getSelectedItem().toString().equals("Others")) {

                                IssuedRaise();
                                // Toast.makeText(MainActivity.this, "Issue Raised Successfully", Toast.LENGTH_SHORT).show();

                                dialogInterface.dismiss();
                            } else {
                                if (!sp1.getSelectedItem().toString().equals("Select SubCategory")) {
                                    subcategory = sp1.getSelectedItem().toString();

                                    if (!sp1.getSelectedItem().toString().equals("Update Owner info")) {
                                        /// if(!sp3.getSelectedItem().toString().equals("")){
                                        IssuedRaise();
                                        // Toast.makeText(MainActivity.this, "Issue Raised Successfully", Toast.LENGTH_SHORT).show();

                                        dialogInterface.dismiss();
                            *//*}
                            else
                            {
                                Toast.makeText(MainActivity.this,"Select Sub SubCategory", Toast.LENGTH_SHORT).show();
                            }*//*
                                    } else {
                                        if (!sp3.getSelectedItem().toString().equals("Select One")) {
                                            subsubcategory = sp3.getSelectedItem().toString();

                                            // Toast.makeText(MainActivity.this, "Issue Raised Successfully", Toast.LENGTH_SHORT).show();
                                            IssuedRaise();
                                            dialogInterface.dismiss();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Select Sub SubCategory", Toast.LENGTH_SHORT).show();
                                        }
                                    }


                                } else {
                                    Toast.makeText(MainActivity.this, "Please Select SubCategory", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                        {
                            Toast.makeText(MainActivity.this, "Please add comment", Toast.LENGTH_SHORT).show();
                        }
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.setView(mview);
            AlertDialog dialog = alertDialog.create();
            dialog.show();*/

        }
        else if (id == R.id.nav_omsupport) {
            Intent intent = new Intent(this, OmSupportActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_hotel_rules) {
            Intent intent = new Intent(this, HomeRulesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_your_hotel_Contacts) {
            Intent intent = new Intent(this, YourHotelContacts.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_room) {
            Intent intent = new Intent(this, CheckInActivity.class);
            startActivity(intent);

        }
       else if (id == R.id.nav_signout) {

            mainPresenter.signOut();

        }

        else if (id == R.id.nav_policies) {
            Intent intent = new Intent(this, PoliciesActivity.class);
            startActivity(intent);

        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setUpUI(List<AllBookingCount> allBookingCountList) {
        bookingCountList = allBookingCountList;
        bookingCountAdapter.updateList(bookingCountList);
        bookingCountAdapter.notifyDataSetChanged();

    }



    @Override
    public void setUpUITariff(List<RoomTarrif> tariffList) {
        roomTariffList = tariffList;
        tariffAdapter.updateList(roomTariffList);

    }

    @Override
    public void setUpUIRoomType(List<TotalAvailableRoom> roomTypeList) {
        availavleRoomTypeList = roomTypeList;
        availableRoomAdapter.updateList(availavleRoomTypeList);
    }


    @Override
    public void navigateToDetailActivity() {

    }

    @Override
    public void disableDateIcon() {
        calender.setVisibility(View.GONE);
    }

    @Override
    public void enableDateIcon() {
        calender.setVisibility(View.VISIBLE);
    }

    @Override
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

                        Toast.makeText(MainActivity.this, ""+(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), Toast.LENGTH_SHORT).show();
                        String dateC = ""+year+"-"+(monthOfYear + 1)+"-"+dayOfMonth;
                        retrieveDate = dateC;
                        Toast.makeText(MainActivity.this, ""+dateC, Toast.LENGTH_SHORT).show();
                        mainPresenter.setParameterToRetrieveBookingsCount(sharedPreferenceConfig.readHotelId(),retrieveDate,"Later");

                    }
                }, mYear, mMonth, mDay);
        String tomorrowDate = ConverterUtil.getDefaultCheckOutDateToNextDay(ConverterUtil.getTodaysDefaultDate());


//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMinDate(ConverterUtil.ConvertDateToSetOnCalender(tomorrowDate));

        datePickerDialog.show();
    }

//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.calender_main);
//        calendarView = dialog.findViewById(R.id.calendar_view);

//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month,
//                                            int dayOfMonth) {
//
//                Toast.makeText(getBaseContext(),"Selected Date is\n\n"
//                                +dayOfMonth+" : "+month+" : "+year ,
//                        Toast.LENGTH_LONG).show();
////                String date = ""+i2+"/"+(i1+1)+"/"+i;
////                Log.e("date",""+i+" "+i1+" "+i2+" "+date);
//            }
//        });
//
//        dialog.show();

    private void IssuedRaise() {
       /* name = etUser.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        gender = etGender.getText().toString().trim();*/


      //  progressDialog.show();
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        // OmRoomApi apiInterface = RetrofitClient.getClient().create(OmRoomApi.class);
        Call<RaiseIssueRequest> userRegisterCall = omRoomApi.loginUser(new RaiseIssueRequest(Integer.parseInt(sharedPreferenceConfig.readHotelId()), category, subcategory, subsubcategory, comment));
        userRegisterCall.enqueue(new Callback<RaiseIssueRequest>() {
            @Override
            public void onResponse(Call<RaiseIssueRequest> call, Response<RaiseIssueRequest> response) {
              //  progressDialog.hide();
                Log.d("res",response.toString());
                if (response.isSuccessful()) {
                    RaiseIssueRequest dtos = response.body();
                    if (dtos != null) {
                        if (dtos.getStatus().equals("Success")) ;
                        Toast.makeText(MainActivity.this, dtos.getMsg(), Toast.LENGTH_SHORT).show();
                     /*   Intent intent = new Intent(RaiseIssueActivity.this, WelComeActivity.class);
                        intent.putExtra("Name", etUser.getText().toString().trim());
                        intent.putExtra("Email", etEmail.getText().toString().trim());
                        startActivity(intent);*/
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Data not Found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RaiseIssueRequest> call, Throwable t) {
              //  progressDialog.hide();
                Toast.makeText(MainActivity.this, "Something went wrong!" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSignOutNavigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void clearSharedPrefMobile() {
        sharedPreferenceConfig.writePhoneNo(null);
    }

    @Override
    public void NavigateToBookingDetailsActivity(String status, String day) {
        Intent intent = new Intent(this, BookingDetailsActivity.class);
        intent.putExtra(Status,status);
        intent.putExtra(Day, retrieveDate);
        intent.putExtra(Index,index);
        startActivityForResult(intent,MainBookingRequestCode);
    }

    @Override
    public void displayProgressBar(String progressT) {
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressText.setText(progressT);
        progressBar.setMax(100);
    }

    @Override
    public void setProgressBar(int progress) {
        progressBar.setProgress(progress);


    }

    @Override
    public void hideProgressBar(String progressT) {

        if(progressT!= null){
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            progressText.setText(progressT);
        }else {
            progressLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void clearRecyclerView() {

    }

    @Override
    public void setUpEodOccupancy(List<RoomTypeCount> listRoomBooked, int totalRoom, List<TotalRooms> noofrooms) {
        int totalBookedRoom = 0;
        List<RoomTypeCount> listRooms = new ArrayList<>();
        for(RoomTypeCount room: listRoomBooked){
            totalBookedRoom = totalBookedRoom + Integer.parseInt(room.getBooked());

            if(listRooms.size()>0){
                RoomTypeCount roomType = null;
                for(RoomTypeCount roomT: listRooms){
                    if(roomT.getRoom_type().equals(room.getRoom_type())){
                        roomT.setBooked(String.valueOf(Integer.parseInt(roomT.getBooked())+Integer.parseInt(room.getBooked())));
                    }else {
                        roomType = room;
                    }
                }
                if(roomType!= null)
                    listRooms.add(roomType);
            }else {
                listRooms.add(room);
            }
        }

        double percen =(double)totalBookedRoom/totalRoom; //(Math.round(((totalBookedRoom*100)/totalRoom)));
        percen = percen* 100;

        bookdrm = String.valueOf(totalBookedRoom);
          totalrm = String.valueOf(totalRoom);
        eodPer.setText("EOD Occupancy: ".concat(String.valueOf(Math.round(percen))).concat("%").concat(" (")
                .concat(String.valueOf(totalBookedRoom).concat("/").concat(String.valueOf(totalRoom)).concat(" )")));
        String type ="";
        for(RoomTypeCount room: listRooms){
            type = type.concat(room.getRoom_type().concat(room.getBooked()));
            for(TotalRooms totalRooms: noofrooms){
                if(totalRooms.getRoom_type().equals(room.getRoom_type())){
                    type = type.concat("/").concat(totalRooms.getNo_of_rooms());
                }
            }
            type = type.concat("\n");
        }
//        eodRoomType.setText(type);

        dateFor.setText("You are Looking For: ".concat(retrieveDate));

    }

    @Override
    public void onMethodCallback(String bookingType) {

        Toast.makeText(this, "1"+bookingType, Toast.LENGTH_SHORT).show();
        NavigateToBookingDetailsActivity(bookingType,retrieveDate);

    }

    @Override
    public void onRoomTariffClick() {

    }

    @Override
    public void availableMethodCallback() {

    }
}
