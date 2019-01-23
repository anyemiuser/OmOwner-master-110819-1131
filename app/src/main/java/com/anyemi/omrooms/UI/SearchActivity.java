package com.anyemi.omrooms.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anyemi.omrooms.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private ConstraintLayout areaHistoryLayout;
    private LinearLayout checkInLayout, checkOutLayOut, roomUserLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

        Toolbar toolbar = findViewById(R.id.material_search_toolbar);
        SearchView searchView = findViewById(R.id.search_material);//new SearchView(this);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        //set focus to search view
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(SearchActivity.this, "submit: "+query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Toast.makeText(SearchActivity.this, "Text Change: "+newText, Toast.LENGTH_SHORT).show();
                if(newText.length()==0){
                    //close key board
                }
                if(newText.length()>=3){
                    areaHistoryLayout.setVisibility(View.GONE);
                }else {
                    areaHistoryLayout.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(SearchActivity.this, "on Close: ", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,CalenderActivity.class);
        switch (view.getId()){
            case R.id.check_in_layout:
                intent.putExtra("check",0);
                startActivity(intent);
                break;
            case R.id.check_out_layout:
                intent.putExtra("check",1);
                startActivity(intent);
                break;
            case R.id.room_user_layout:
                intent.putExtra("check",2);
                startActivity(intent);
                break;
        }
    }

    private void init() {
        areaHistoryLayout = findViewById(R.id.constraintLayout);

        checkInLayout = findViewById(R.id.check_in_layout);
        checkInLayout.setOnClickListener(this);

        checkOutLayOut = findViewById(R.id.check_out_layout);
        checkOutLayOut.setOnClickListener(this);

        roomUserLayout = findViewById(R.id.room_user_layout);
        roomUserLayout.setOnClickListener(this);

    }
}
