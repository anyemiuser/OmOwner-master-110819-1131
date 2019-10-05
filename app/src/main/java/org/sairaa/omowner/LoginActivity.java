package org.sairaa.omowner;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.Login.LoginOtpRequest;
import org.sairaa.omowner.Main.MainActivity;
import org.sairaa.omowner.Model.Hotels;
import org.sairaa.omowner.Model.UserTypeRequest;
import org.sairaa.omowner.Model.UserTypeResponse;
import org.sairaa.omowner.RaiseIssue.RaiseIssueActivity;
import org.sairaa.omowner.RaiseIssue.RaiseIssueRequest;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String LOGIN_TAG = LoginActivity.class.getName();
    private ConstraintLayout phoneLayout, codeLayout;
    private SharedPreferenceConfig sharedPreferenceConfig;
    private EditText codeT;
    private EditText phoneNo;
    private Button signIn, goButton;
    String otpcode;
    String test="Exists";


    private String phoneNumber ="";
    private String verificationId;

    private FirebaseAuth mAuth;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferenceConfig= new SharedPreferenceConfig(this);

        phoneLayout = findViewById(R.id.phone_layout);
        codeLayout = findViewById(R.id.code_layout);

        codeT = findViewById(R.id.code_edit_text);
        signIn = findViewById(R.id.sign_in);
        signIn.setOnClickListener(this);

        goButton = findViewById(R.id.go_button);
        goButton.setOnClickListener(this);

        phoneNo = findViewById(R.id.phone_no);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_in:
               // verifyCode(codeT.getText().toString().trim());
                if(codeT.getText().toString().equals(otpcode)){
                    registerOnSuccess(phoneNumber);
                }
                else if(codeT.getText().toString().equals("")){
                    Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Entered Wrong OTP", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.go_button:
                phoneNumber = phoneNo.getText().toString().trim();
                if(phoneNumber.length()==10){
                   // sendVeificationCode(phoneNo.getText().toString().trim());
                    registerOnSuccess1(phoneNumber);
                    if(test.equals("Ok")){
                    SendOtp();
                        phoneLayout.setVisibility(View.GONE);
                        codeLayout.setVisibility(View.VISIBLE);
                    }else {
                        registerOnSuccess1(phoneNumber);
                    }

                }else if(phoneNumber.length()==0){
                    Toast.makeText(LoginActivity.this,"Please enter mobile number",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"Mobile number should be 10 digits",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void sendVeificationCode(String phoneNumber){
        phoneNumber="+91".concat(phoneNumber);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                codeT.setText(code);
                verifyCode(code);
            }else {
                signInWithCredential(phoneAuthCredential);
            }


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginActivity.this,""+e,Toast.LENGTH_SHORT).show();
           // Toast.makeText(LoginActivity.this,"Verfication Failed",Toast.LENGTH_SHORT).show();
        }
    } ;

    private void verifyCode(String code){
        Log.e("verification id",""+verificationId);
        if(verificationId != null){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
//        Log.e("verification id",""+verificationId);
            signInWithCredential(credential);
        }else {
            Toast.makeText(this, "verify code", Toast.LENGTH_SHORT).show();

            registerOnSuccess(phoneNumber);
        }

    }

    private void registerOnSuccess(String phoneNumber) {

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        UserTypeRequest user = new UserTypeRequest(phoneNumber);
        Log.e(LOGIN_TAG,""+new Gson().toJson(user));
        omRoomApi.getUserTypeAndHotel(user).enqueue(new Callback<UserTypeResponse>() {
            @Override
            public void onResponse(Call<UserTypeResponse> call, Response<UserTypeResponse> response) {

                if(response.isSuccessful()){
                    UserTypeResponse userTypeResponse = response.body();
                    Log.e(LOGIN_TAG,""+new Gson().toJson(userTypeResponse));
                    if (userTypeResponse != null) {
                        if(userTypeResponse.getStatus().equals("success") && userTypeResponse.getMessage().equals("success")){
                            List<Hotels> hotels = userTypeResponse.getHoteldetails();
                            sharedPreferenceConfig.writePhoneNo(phoneNumber);

                            sharedPreferenceConfig.writeUserType(userTypeResponse.getUsertype());
                            if(hotels != null){
                                if(hotels.size()>0){
                                    Hotels eachHotel = hotels.get(0);
                                    sharedPreferenceConfig.writeHotelId(eachHotel.getHotel_id());
                                    sharedPreferenceConfig.writeHotelName(eachHotel.getHotel_name());

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    notAuthorizedAndClose();
                                }

                            }else {
                                notAuthorizedAndClose();
                            }
//                            sharedPreferenceConfig.writeHotelId("901");
//                            sharedPreferenceConfig.writeHotelName("Dolphin Hotel");
//
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            finish();

                        }else {
                            notAuthorizedAndClose();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<UserTypeResponse> call, Throwable t) {
                Log.e(LOGIN_TAG,""+t.toString());
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(LoginActivity.this, "You Have Entered Wrong OTP", Toast.LENGTH_SHORT).show();
            }
        });


//        omRoomApi.postUserRegister(user).enqueue(new Callback<UserResponse>() {


    }

    private void notAuthorizedAndClose() {
        Toast.makeText(LoginActivity.this, "This mobile number not registered as a receptionist.", Toast.LENGTH_SHORT).show();
        phoneNo.setText("");

        //finish();
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            registerOnSuccess(phoneNumber);

                        }else {
                            //Toast.makeText(LoginActivity.this,"Unsuccessful"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this,"You Have Entered Wrong OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void SendOtp() {
       /* name = etUser.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        gender = etGender.getText().toString().trim();*/


        //  progressDialog.show();
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        // OmRoomApi apiInterface = RetrofitClient.getClient().create(OmRoomApi.class);
        Call<LoginOtpRequest> userotpCall = omRoomApi.loginotp(new LoginOtpRequest(phoneNo.getText().toString().trim()));
        userotpCall.enqueue(new Callback<LoginOtpRequest>() {
            @Override
            public void onResponse(Call<LoginOtpRequest> call, Response<LoginOtpRequest> response) {
                //  progressDialog.hide();
                Log.d("res",response.toString());
                if (response.isSuccessful()) {
                    LoginOtpRequest dtos = response.body();
                    if (dtos != null) {
                        if (dtos.getStatus().equals("Success")) ;
                       /* Toast.makeText(LoginActivity.this, dtos.getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                       *//* intent.putExtra("Name", etUser.getText().toString().trim());
                        intent.putExtra("Email", etEmail.getText().toString().trim());*//*
                        startActivity(intent);*/
                      /*  if(codeT.getText().toString().equals(dtos.getOtp())){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }*/
                      // codeT.setText(dtos.getOtp());
                        otpcode = dtos.getOtp();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Data not Found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginOtpRequest> call, Throwable t) {
                //  progressDialog.hide();
                Toast.makeText(LoginActivity.this, "Something went wrong!" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerOnSuccess1(String phoneNumber) {

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        UserTypeRequest user = new UserTypeRequest(phoneNumber);
        Log.e(LOGIN_TAG,""+new Gson().toJson(user));
        omRoomApi.getUserTypeAndHotel(user).enqueue(new Callback<UserTypeResponse>() {
            @Override
            public void onResponse(Call<UserTypeResponse> call, Response<UserTypeResponse> response) {

                if(response.isSuccessful()){
                    UserTypeResponse userTypeResponse = response.body();
                    Log.e(LOGIN_TAG,""+new Gson().toJson(userTypeResponse));
                    if (userTypeResponse != null) {
                        if(userTypeResponse.getStatus().equals("success") && userTypeResponse.getMessage().equals("success")){
                            List<Hotels> hotels = userTypeResponse.getHoteldetails();
                           // sharedPreferenceConfig.writePhoneNo(phoneNumber);

                           // sharedPreferenceConfig.writeUserType(userTypeResponse.getUsertype());
                            if(hotels != null){
                                if(hotels.size()>0){
                                   // Hotels eachHotel = hotels.get(0);
                                    test="Ok";
                                  /*  sharedPreferenceConfig.writeHotelId(eachHotel.getHotel_id());
                                    sharedPreferenceConfig.writeHotelName(eachHotel.getHotel_name());

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();*/
                                }else {
                                    notAuthorizedAndClose();
                                }

                            }else {
                                notAuthorizedAndClose();
                            }
//                            sharedPreferenceConfig.writeHotelId("901");
//                            sharedPreferenceConfig.writeHotelName("Dolphin Hotel");
//
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            finish();

                        }else {
                            notAuthorizedAndClose();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<UserTypeResponse> call, Throwable t) {
                Log.e(LOGIN_TAG,""+t.toString());
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(LoginActivity.this, "You Have Entered Wrong OTP", Toast.LENGTH_SHORT).show();
            }
        });


//        omRoomApi.postUserRegister(user).enqueue(new Callback<UserResponse>() {


    }


}