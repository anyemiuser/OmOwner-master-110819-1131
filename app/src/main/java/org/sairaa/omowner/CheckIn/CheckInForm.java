package org.sairaa.omowner.CheckIn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.sairaa.omowner.BookingDetails.BookingDetailContract;
import org.sairaa.omowner.BookingDetails.BookingDetailsActivity;
import org.sairaa.omowner.BookingDetails.BookingDetailsPresenter;
import org.sairaa.omowner.Model.CustomerBookingDetailsRequest;
import org.sairaa.omowner.Model.CustomerBookings;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.ConverterUtil;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.sairaa.omowner.Utils.Constants.completedType;
import static org.sairaa.omowner.Utils.Constants.inHouseType;
import static org.sairaa.omowner.Utils.Constants.upComingType;

public class CheckInForm extends AppCompatActivity implements  BookingDetailContract.View{
    private static final String TAG_BOOKING_DETAIL = BookingDetailsActivity.class.getName();
    Button bt_proofbutton,bt_photoupload,checkinsubmit,checkincancel;
    TextView etbookid,etname,etnumber;
    Spinner sp_addressproof;
    ImageView uploadphoto,uploadproof;
    ActionBar actionBar;
    private String hotelId;
    BookingDetailsPresenter bookingPresenter;
    private String status, day,index;
    CustomerBookings list;
    private SharedPreferenceConfig sharedPreferenceConfig;

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_form);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();



        if(actionBar!= null) {
            actionBar.setTitle("CheckIn");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        bookingPresenter = new BookingDetailsPresenter(this);
        bt_photoupload = findViewById(R.id.bt_photoupload);
        bt_proofbutton  = (Button) findViewById(R.id.bt_proofupload);
        checkinsubmit = findViewById(R.id.checkinsubmit);
        checkincancel = findViewById(R.id.checkincancel);

        etbookid = findViewById(R.id.et_bookid);
        etname = findViewById(R.id.et_name);
        etnumber = findViewById(R.id.et_number);

        uploadphoto = findViewById(R.id.photoupload);
        uploadproof = findViewById(R.id.proofupload);
        sp_addressproof = findViewById(R.id.Spinner_address);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("bookingiD");
        String name = bundle.getString("bookingname");
        String number = bundle.getString("bookingphonenumber");
         status = bundle.getString("bookingiD");
         day = bundle.getString("bookingname");
         index = bundle.getString("bookingphonenumber");


        etbookid.setText(id);
        etname.setText(name);
        etnumber.setText(number);

        checkinsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etbookid.getText().toString().equals(""))
                {
                    if(!etname.getText().toString().equals(""))
                    {
                        if(!etnumber.getText().toString().equals("")){

                            if( etnumber.getText().toString().length() == 10 ){


                                if (uploadproof.getDrawable()!=null) {
                                    if (uploadphoto.getDrawable()!=null) {
                                        /*Intent it = new Intent(CheckInForm.this, CheckInActivity.class);
                                        startActivity(it);*/
                                        if(ConverterUtil.isDateToday(list.getFrom_date())
                                                || ConverterUtil.checkCurrentDateIsLessThenSaved(ConverterUtil.parseDateToddMMMyyyy(list.getFrom_date()))) {
                                            // Intent checkInIntent = new Intent(this, CheckInForm.class);
                                            Intent checkInIntent = new Intent(CheckInForm.this, CheckInActivity.class);
                                            checkInIntent.putExtra("bookingD", "" + new Gson().toJson(list));
                                            startActivityForResult(checkInIntent, 9);
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(CheckInForm.this, "Upload Photo", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else
                                {
                                    Toast.makeText(CheckInForm.this, "Upload Proof", Toast.LENGTH_SHORT).show();
                                }


                            }

                            else
                            {
                                Toast.makeText(CheckInForm.this, "Phone number must be 10 digits....", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(CheckInForm.this, "Phone number Can't be Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(CheckInForm.this, "Name can't be empty ", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CheckInForm.this, "booking Id can't be empty ", Toast.LENGTH_SHORT).show();
                }


            }
        });



        bt_photoupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 6);
            }
        });


        checkincancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        bt_proofbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 5);
            }
        });


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(CheckInForm.this,android.R.layout.simple_spinner_dropdown_item
                ,getResources().getStringArray(R.array.Addressproof));

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_addressproof.setAdapter(adapter1);





    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CheckInRequestCode && resultCode == RESULT_OK)  {
            refreshViewAfterCheckOut(false);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    String PathHolder = data.getData().getPath();
                    Toast.makeText(CheckInForm.this, PathHolder, Toast.LENGTH_LONG).show();
                }
                break;
            case 6:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri1 = data.getData();
                        final InputStream imageStream1 = getContentResolver().openInputStream(imageUri1);
                        final Bitmap selectedImage1 = BitmapFactory.decodeStream(imageStream1);

                        uploadphoto.setImageBitmap(selectedImage1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(CheckInForm.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(CheckInForm.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                }
                break;
            case 5:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        uploadproof.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(CheckInForm.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(CheckInForm.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                }
                break;
            case 9:
                if(resultCode == RESULT_OK)  {
                refreshViewAfterCheckOut(false);
            }
        }
    }


    @Override
    public void setUpTitle(String title) {

    }

    @Override
    public void displayProgressBar(String progressText) {

    }

    @Override
    public void setProgressBar(int progress) {

    }

    @Override
    public void hideProgressBar(String progressText) {

    }

    @Override
    public void setUpRV(List<CustomerBookings> bookings) {

    }

    @Override
    public void clearRecyclerView() {

    }

    public void refreshViewAfterCheckOut(boolean isCancel) {
        if(status!= null && day != null){

            if(status.equals(upComingType)){
                // if checked in then refresh the list to show checked in list to get visual confirmation

                //if Booking is cancelled, Refresh with upcoming only
                //else refresh with in house
                if(!isCancel){
                    status = inHouseType;
                }
            }else if(status.equals(inHouseType)  )  {
                status = completedType;
            }
            CustomerBookingDetailsRequest request = new CustomerBookingDetailsRequest(hotelId,status,day,index);
            bookingPresenter.retrieveBookingDetails(request);
        }
    }

    @Override
    public void toastMessage(String msg) {

    }


    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}