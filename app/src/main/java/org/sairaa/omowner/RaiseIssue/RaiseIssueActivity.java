package org.sairaa.omowner.RaiseIssue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.Main.MainActivity;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaiseIssueActivity extends AppCompatActivity {
    String category,subcategory,subsubcategory,comment;
    private SharedPreferenceConfig sharedPreferenceConfig;
    Button cancel,submit;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_issue);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setTitle("Raise Issue");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }




        sharedPreferenceConfig = new SharedPreferenceConfig(this);

       /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(RaiseIssueActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
        alertDialog.setTitle("Raise an Issue");*/
        final Spinner sp = findViewById(R.id.spinner);
        final Spinner sp1 = findViewById(R.id.spinner1);
        final Spinner sp3 =findViewById(R.id.spinner3);
        cancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.submit);
        final EditText textArea_information =findViewById(R.id.textArea_information);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RaiseIssueActivity.this,android.R.layout.simple_spinner_dropdown_item
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
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(RaiseIssueActivity.this,android.R.layout.simple_spinner_dropdown_item
                            ,getResources().getStringArray(R.array.Sub_Guest));
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp1.setAdapter(adapter1);


                }
                else if(selectedItem.equals("Manage Property on OM App"))

                {
                    // do your stuff
                    sp1.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(RaiseIssueActivity.this,android.R.layout.simple_spinner_dropdown_item
                            ,getResources().getStringArray(R.array.Sub_Manage));

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp1.setAdapter(adapter1);

                }
                else if(selectedItem.equals("Revenue related enquiry"))
                {
                    // do your stuff
                    sp1.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(RaiseIssueActivity.this,android.R.layout.simple_spinner_dropdown_item
                            ,getResources().getStringArray(R.array.Sub_Revenue));

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp1.setAdapter(adapter1);

                }
                else if(selectedItem.equals("General Requests and Feedback"))
                {

                    // do your stuff
                    sp1.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(RaiseIssueActivity.this,android.R.layout.simple_spinner_dropdown_item
                            ,getResources().getStringArray(R.array.Sub_General));

                    // adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp1.setAdapter(adapter1);

                }
                else  if(selectedItem.equals("Products and Services"))
                {
                    // do your stuff
                    sp1.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(RaiseIssueActivity.this,android.R.layout.simple_spinner_dropdown_item
                            ,getResources().getStringArray(R.array.Sub_General));

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp1.setAdapter(adapter1);

                }
                else  if(selectedItem.equals("Tech Issues"))
                {
                    // do your stuff
                    sp1.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(RaiseIssueActivity.this,android.R.layout.simple_spinner_dropdown_item
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
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(RaiseIssueActivity.this, android.R.layout.simple_spinner_dropdown_item
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






      submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {




                    if (!sp.getSelectedItem().toString().equals("Select Category")) {
                        category = sp.getSelectedItem().toString();
                        comment = textArea_information.getText().toString();


                        if (sp.getSelectedItem().toString().equals("Issue with OM Staff")
                                && sp.getSelectedItem().toString().equals("Value Added Services Enquiry")
                                && sp.getSelectedItem().toString().equals("Others")) {
                            if(!textArea_information.getText().toString().equals("")) {
                                IssuedRaise();
                            }
                            else
                            {
                                Toast.makeText(RaiseIssueActivity.this, "Please add comment", Toast.LENGTH_SHORT).show();
                            }
                            // Toast.makeText(MainActivity.this, "Issue Raised Successfully", Toast.LENGTH_SHORT).show();

                          //  dialogInterface.dismiss();
                        } else {
                            if (!sp1.getSelectedItem().toString().equals("Select SubCategory")) {
                                subcategory = sp1.getSelectedItem().toString();

                                if (!sp1.getSelectedItem().toString().equals("Update Owner info")) {
                                    /// if(!sp3.getSelectedItem().toString().equals("")){
                                    if(!textArea_information.getText().toString().equals("")) {
                                        IssuedRaise();
                                    }
                                    else
                                    {
                                        Toast.makeText(RaiseIssueActivity.this, "Please add comment", Toast.LENGTH_SHORT).show();
                                    }
                                    // Toast.makeText(MainActivity.this, "Issue Raised Successfully", Toast.LENGTH_SHORT).show();

                                    //dialogInterface.dismiss();
                            /*}
                            else
                            {
                                Toast.makeText(MainActivity.this,"Select Sub SubCategory", Toast.LENGTH_SHORT).show();
                            }*/
                                } else {
                                    if (!sp3.getSelectedItem().toString().equals("Select One")) {
                                        subsubcategory = sp3.getSelectedItem().toString();

                                        // Toast.makeText(MainActivity.this, "Issue Raised Successfully", Toast.LENGTH_SHORT).show();
                                        if(!textArea_information.getText().toString().equals("")) {
                                            IssuedRaise();
                                        }
                                        else
                                        {
                                            Toast.makeText(RaiseIssueActivity.this, "Please add comment", Toast.LENGTH_SHORT).show();
                                        }
                                      //  dialogInterface.dismiss();
                                    } else {
                                        Toast.makeText(RaiseIssueActivity.this, "Select Sub SubCategory", Toast.LENGTH_SHORT).show();
                                    }
                                }


                            } else {
                                Toast.makeText(RaiseIssueActivity.this, "Please Select SubCategory", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(RaiseIssueActivity.this, "Please Select Category", Toast.LENGTH_SHORT).show();

                    }
               /* }
                else
                {
                    Toast.makeText(RaiseIssueActivity.this, "Please add comment", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();

             //   dialogInterface.dismiss();
            }
        });
      /*  alertDialog.setView(mview);
        AlertDialog dialog = alertDialog.create();
        dialog.show();*/



    }

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
                        Toast.makeText(RaiseIssueActivity.this, dtos.getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RaiseIssueActivity.this, MainActivity.class);
                       /* intent.putExtra("Name", etUser.getText().toString().trim());
                        intent.putExtra("Email", etEmail.getText().toString().trim());*/
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(RaiseIssueActivity.this, "Data not Found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RaiseIssueRequest> call, Throwable t) {
                //  progressDialog.hide();
                Toast.makeText(RaiseIssueActivity.this, "Something went wrong!" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
