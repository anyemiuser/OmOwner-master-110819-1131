package org.sairaa.omowner.HomeRules;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.CheckIn.CheckInContract;
import org.sairaa.omowner.Main.MainActivity;
import org.sairaa.omowner.R;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RulesListAdapter extends RecyclerView.Adapter<RulesListAdapter.ViewHolder>{

    private List<RulesListItem> list;
    private Context context;
    String tvr1,tvr2,tvr3,tvr4,tvr5,tvr6;
    String strdisallow="d",strallow="a";
    private SharedPreferenceConfig sharedPreferenceConfig;

    public RulesListAdapter(List<RulesListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rules_list_view,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        RulesListItem listItem = list.get(i);

        viewHolder.rule1.setText(listItem.getRule1());
        viewHolder.rule2.setText(listItem.getRule2());
        viewHolder.rule3.setText(listItem.getRule3());
        viewHolder.rule4.setText(listItem.getRule4());
        viewHolder.rule5.setText(listItem.getRule5());
        viewHolder.rule6.setText(listItem.getRule6());
      /*  Picasso.with(context)
                .load(listItem.imageUrl)
                .into(viewHolder.image);*/


        viewHolder.r1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){
                    tvr1="1"; //edit here
                }else{
                    tvr1="0";  //edit here
                }

            }
        });

        viewHolder.r2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){
                    tvr2="1"; //edit here
                }else{
                    tvr2="0";  //edit here
                }

            }
        });

        viewHolder.r3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){
                    tvr3="1"; //edit here
                }else{
                    tvr3="0";  //edit here
                }

            }
        });

        viewHolder.r4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){
                    tvr4="1"; //edit here
                }else{
                    tvr4="0";  //edit here
                }

            }
        });
        viewHolder.r5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){
                    tvr5="1"; //edit here
                }else{
                    tvr5="0";  //edit here
                }

            }
        });
        viewHolder.r6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if(isChecked){
                    tvr6="1"; //edit here
                }else{
                    tvr6="0";  //edit here
                }

            }
        });



        viewHolder.disallow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
                //  OmApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(OmApiInterface.class);

                Call<UpdateHotelRulesRequest> userRegisterCall = omRoomApi.updatehotelrules(new UpdateHotelRulesRequest(Integer.parseInt(sharedPreferenceConfig.readHotelId()), tvr1, tvr2, tvr3, tvr4, strdisallow));


                userRegisterCall.enqueue(new Callback<UpdateHotelRulesRequest>() {
                    @Override
                    public void onResponse(Call<UpdateHotelRulesRequest> call, retrofit2.Response<UpdateHotelRulesRequest> response) {
                        //  progressDialog.hide();
                        if (response.isSuccessful()) {
                            UpdateHotelRulesRequest dtos = response.body();
                            if (dtos != null) {
                                if (dtos.getStatus().equals("Success")) ;
                                Toast.makeText(view.getContext(), dtos.getMsg(), Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent(view.getContext(), MainActivity.class);
                        context.startActivity(intent);*/
                       /* Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);*/
                            }
                        } else {
                            Toast.makeText(view.getContext(), "Data not Found!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateHotelRulesRequest> call, Throwable t) {
                        //  progressDialog.hide();
                        Toast.makeText(view.getContext(), "Something went wrong!" + t, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



        viewHolder.allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
                //  OmApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(OmApiInterface.class);

                Call<UpdateHotelRulesAllowRequest> userRegisterCall = omRoomApi.updatehotelrulesallow(new UpdateHotelRulesAllowRequest(Integer.parseInt(sharedPreferenceConfig.readHotelId()), tvr5, tvr6, strallow));



                userRegisterCall.enqueue(new Callback<UpdateHotelRulesAllowRequest>() {
                    @Override
                    public void onResponse(Call<UpdateHotelRulesAllowRequest> call, retrofit2.Response<UpdateHotelRulesAllowRequest> response) {
                        //  progressDialog.hide();
                        if (response.isSuccessful()){
                            UpdateHotelRulesAllowRequest dtos = response.body();
                            if (dtos!=null){
                                if (dtos.getStatus().equals("Success"));
                                Toast.makeText(view.getContext(),dtos.getMsg(),Toast.LENGTH_SHORT).show();
                      /*  Intent intent = new Intent(view.getContext(), MainActivity.class);
                        context.startActivity(intent);*/
                               /* Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);*/
                            }
                        }else {
                            Toast.makeText(view.getContext(),"Data not Found!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateHotelRulesAllowRequest> call, Throwable t) {
                        //  progressDialog.hide();
                        Toast.makeText(view.getContext(),"Something went wrong!"+t,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView rule1,rule2,rule3,rule4,rule5,rule6;
        public ImageView image;
        public Button disallow,allow;
        Switch r1,r2,r3,r4,r5,r6;
        // public Button disallow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sharedPreferenceConfig = new SharedPreferenceConfig(itemView.getContext());
            rule1 = itemView.findViewById(R.id.rule1);
            rule2 = itemView.findViewById(R.id.rule2);
            rule3 = itemView.findViewById(R.id.rule3);
            rule4 = itemView.findViewById(R.id.rule4);
            rule5 = itemView.findViewById(R.id.rule5);
            rule6 = itemView.findViewById(R.id.rule6);
            disallow= itemView.findViewById(R.id.disallow);
            allow= itemView.findViewById(R.id.allow);
            r1 = itemView.findViewById(R.id.r1);
            r2 = itemView.findViewById(R.id.r2);
            r3 = itemView.findViewById(R.id.r3);
            r4 = itemView.findViewById(R.id.r4);
            r5 = itemView.findViewById(R.id.r5);
            r6 = itemView.findViewById(R.id.r6);
            /*  image = itemView.findViewById(R.id.image);*/
            if(HomeRulesActivity.str1.equals("1"))
                r1.setChecked(true);
            if(HomeRulesActivity.str2.equals("1"))
                r2.setChecked(true);
            if(HomeRulesActivity.str3.equals("1"))
                r3.setChecked(true);
            if(HomeRulesActivity.str4.equals("1"))
                r4.setChecked(true);
            if(HomeRulesActivity.str5.equals("1"))
                r5.setChecked(true);
            if(HomeRulesActivity.str6.equals("1"))
                r6.setChecked(true);

        }
    }
    /*    private void UpdateDisAllowHotelRules() {
     *//*  name = etUser.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        gender = etGender.getText().toString().trim();
        progressDialog.show();*//*
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        //  OmApiInterface apiInterface = RetrofitInstance.getRetrofitInstance().create(OmApiInterface.class);
        Call<UpdateHotelRulesRequest> userRegisterCall = omRoomApi.updatehotelrules(new UpdateHotelRulesRequest(905,"1","0","1","0","d"));
        userRegisterCall.enqueue(new Callback<UpdateHotelRulesRequest>() {
            @Override
            public void onResponse(Call<UpdateHotelRulesRequest> call, retrofit2.Response<UpdateHotelRulesRequest> response) {
                //  progressDialog.hide();
                if (response.isSuccessful()){
                    UpdateHotelRulesRequest dtos = response.body();
                    if (dtos!=null){
                        if (dtos.getStatus().equals("Success"));
                        Toast.makeText(.this,dtos.getMsg(),Toast.LENGTH_SHORT).show();
                      *//*  Intent intent = new Intent(HomeRulesActivity.this,WelComeActivity.class);
                        intent.putExtra("Name",etUser.getText().toString().trim());
                        intent.putExtra("Email",etEmail.getText().toString().trim());
                        startActivity(intent);*//*
                    }
                }else {
                    Toast.makeText(view.getContext(),"Data not Found!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateHotelRulesRequest> call, Throwable t) {
                //  progressDialog.hide();
                Toast.makeText(HomeRulesActivity.this,"Something went wrong!"+t,Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}
