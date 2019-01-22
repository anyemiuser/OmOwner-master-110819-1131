package com.anyemi.omrooms;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anyemi.omrooms.Model.UserRequest;
import com.anyemi.omrooms.Model.UserResponse;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;

    private ConstraintLayout phoneLayout, codeLayout;

    private EditText codeT;
    private Button signIn;

    private String phoneNumber ="";

    private SharedPreferenceConfig sharedPreferenceConfig;

    private UserResponse userResponse;

    private Boolean isSucess = false;

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
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyCode(codeT.getText().toString().trim());
            }
        });

        mAuth = FirebaseAuth.getInstance();

        EditText phoneNo = findViewById(R.id.phone_no);
        findViewById(R.id.go_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneNumber = phoneNo.getText().toString().trim();
                if(phoneNumber.length()==10){
                    sendVeificationCode(phoneNo.getText().toString().trim());
                    phoneLayout.setVisibility(View.GONE);
                    codeLayout.setVisibility(View.VISIBLE);
                }else if(phoneNumber.length()==0){
                    Toast.makeText(LoginActivity.this,"Fill phone number",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this,"Phone number should be 10 digit",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void verifyCode(String code){
        Log.e("verification id",""+verificationId);
        if(verificationId != null){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,code);
//        Log.e("verification id",""+verificationId);
            signInWithCredential(credential);
        }else {
            Toast.makeText(this, "verify code", Toast.LENGTH_SHORT).show();
        }

    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            registerOnSuccess(phoneNumber);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }else {
                            Toast.makeText(LoginActivity.this,"Unsuccesful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registerOnSuccess(String phoneNumber) {

        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        UserRequest user = new UserRequest(phoneNumber);

        omRoomApi.postUserRegister(user).enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){

                    userResponse = response.body();
                    Log.e("retrofit",userResponse.getStatus()+" "+userResponse.getMsg()+" "+userResponse.getMobile_number());

                    if(userResponse.getStatus().equals("sucess")){

//                        sharedPreferenceConfig.writePhoneNo(phoneNumber);
                        Toast.makeText(LoginActivity.this,"Registered Succesfully",Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);

                    }else {
                        Toast.makeText(LoginActivity.this, "Not Registered Yet", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("error",""+t.getMessage());

            }
        });

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
        }
    } ;
}
