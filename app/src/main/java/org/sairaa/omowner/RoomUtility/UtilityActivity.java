package org.sairaa.omowner.RoomUtility;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.sairaa.omowner.R;
import org.sairaa.omowner.RoomUtility.Model.RoomUtility;
import org.sairaa.omowner.RoomUtility.Model.UtilitiyDetails;
import org.sairaa.omowner.RoomUtility.Model.UtilityRequest;
import org.sairaa.omowner.Utils.ConverterUtil;
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UtilityActivity extends AppCompatActivity implements View.OnClickListener, UtilityDetailAdapter.UtilAdapterCallback {

    private static final String TAG_UTILITY = UtilityActivity.class.getName();
    private RecyclerView utilityRv;
    private Button update;
    private UtilityViewModel utilityViewModel;
    private ConstraintLayout progressLayout;
    private ProgressBar progressBar;
    private TextView progressTextV;
    private UtilityAdapter adapter;
    private List<UtilitiyDetails> utilitiyDetails = new ArrayList<>();

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility);

        initialize();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setTitle("Amenity Details");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        UtilityRequest utilityRequest = new UtilityRequest(new SharedPreferenceConfig(this).readHotelId());
        utilityViewModel.retrieveUtilityDetails(utilityRequest);

        utilityViewModel.getUtilityDetails().observe(this, new Observer<List<UtilitiyDetails>>() {
            @Override
            public void onChanged(@Nullable List<UtilitiyDetails> utilitiyDetails) {
                Log.e(TAG_UTILITY,""+new Gson().toJson(utilitiyDetails));
                updateUi(utilitiyDetails);

            }
        });

        utilityViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean != null && aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        utilityViewModel.getLoadingText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                progressTextV.setText(s);
            }
        });

    }

    private void updateUi(List<UtilitiyDetails> utilD) {
        utilitiyDetails = utilD;
        adapter.updateList(utilitiyDetails);

    }

    private void initialize() {
        utilityViewModel = ViewModelProviders.of(this).get(UtilityViewModel.class);

        utilityRv = findViewById(R.id.utility_rv);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        utilityRv.setLayoutManager(linearLayoutManager);
        utilityRv.setHasFixedSize(true);
        adapter = new UtilityAdapter(utilitiyDetails,this);
        utilityRv.setAdapter(adapter);
        update = findViewById(R.id.update_utility);
        update.setOnClickListener(this);

        progressLayout = findViewById(R.id.progress_l_utility);
        progressBar = findViewById(R.id.progressBar);
        progressTextV = findViewById(R.id.progress_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update_utility:
                utilityViewModel.updateUtility(new SharedPreferenceConfig(this).readHotelId());
                break;
        }
    }

    @Override
    public void setUtilityStatus(RoomUtility utility, String roomType) {
        ConverterUtil.updateUtilityDetails(Objects.requireNonNull(utilityViewModel.getUtilityDetails().getValue()),utility,roomType);
    }
}
