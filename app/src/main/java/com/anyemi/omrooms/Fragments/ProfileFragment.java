package com.anyemi.omrooms.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anyemi.omrooms.LoginActivity;
import com.anyemi.omrooms.Model.Profile;
import com.anyemi.omrooms.Model.ProfileUpdateResponse;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.api.ApiUtils;
import com.anyemi.omrooms.api.OmRoomApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText name, email, address, phone;
    private Button update;
    private Spinner spinner;
    private String gender = null;
    private ConstraintLayout progressL;
    private ProgressBar progressBar;
    private TextView progressText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar toolbarProfile = view.findViewById(R.id.toolbar_profile);
//        return inflater.inflate(R.layout.fragment_home,null);
        toolbarProfile.inflateMenu(R.menu.account_options);
        toolbarProfile.setOnMenuItemClickListener(this::onOptionsItemSelected);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressL = view.findViewById(R.id.progress_l);
        progressL.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.progressBar3);
        progressText = view.findViewById(R.id.progressText);
        name = view.findViewById(R.id.nameE);
        email = view.findViewById(R.id.emailE);
        address = view.findViewById(R.id.addressE);
        phone = view.findViewById(R.id.phoneE);
        spinner = view.findViewById(R.id.gender_spinner);
        update = view.findViewById(R.id.update_button);
        String userId =
                new SharedPreferenceConfig(getActivity()).readPhoneNo();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                gender = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gender!= null && !name.getText().toString().trim().isEmpty()){

                    updateProfile();
                }else {
                    Toast.makeText(getActivity(), "Fill the Form Properly", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void updateProfile() {

        Profile profile = new Profile(new SharedPreferenceConfig(getActivity()).readPhoneNo(),
                name.getText().toString().trim(),
                email.getText().toString().trim(),
                gender,
                "u",
                " ",
                address.getText().toString().trim(),
                "","","","");
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        progressL.setVisibility(View.VISIBLE);
        update.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        progressText.setText("Updating Proile");
        omRoomApi.updateProfile("profileupdate",profile).enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponse> call, Response<ProfileUpdateResponse> response) {
                if(response.isSuccessful()){
                    Log.e("profileFragment success",""+new Gson().toJson(response.body()));
                    progressText.setText("Profile Updated Successfully");
                }else {
                    progressText.setText("Profile Not Updated");

                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                Log.e("profileFragment error",""+t.toString());
                progressBar.setVisibility(View.GONE);
                progressText.setText("Profile Not Updated");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_about_app:

                // Not implemented here
                return false;
            case R.id.action_logout:
                mAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(),
                        LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;

            default:
                break;
        }

        return false;
    }


}
