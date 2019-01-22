package com.anyemi.omrooms.UI;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.anyemi.omrooms.R;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.material_search_toolbar);
        searchView = findViewById(R.id.search_material);//new SearchView(this);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (getSupportActionBar() != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        //set focus to search view
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
