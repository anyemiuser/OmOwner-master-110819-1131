package com.anyemi.omrooms.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anyemi.omrooms.R;

public class AreaHotelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_hotels);

        String area = getIntent().getStringExtra("area");
        if(area != null){
            showAllHotelsUnderTheArea(area);
        }
    }

    private void showAllHotelsUnderTheArea(String area) {
        Toast.makeText(this, ""+area, Toast.LENGTH_SHORT).show();
    }
}
