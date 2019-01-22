package com.anyemi.omrooms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anyemi.omrooms.Utils.SharedPreferenceConfig;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferenceConfig sharedPreferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);

//        final PathView pathView = findViewById(R.id.pathView);
//        pathView.getPathAnimator()
//                .delay(1000)
//                .duration(1000)
//                .interpolator(new AccelerateDecelerateInterpolator())
//                .start();

//        pathView.useNaturalColors();
//        pathView.setFillAfter(true);
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent i;
        if (sharedPreferenceConfig.readPhoneNo().length() == 10) {
            i = MainActivity.getStartIntent(SplashActivity.this);
        } else {
            i = LoginActivity.getStartIntent(SplashActivity.this);
        }

        // TODO :: check for the user_token here & redirect to corresponding class
        // If token is null -> LoginActivity, else SplashActivity
        new Handler().postDelayed(() -> {
            startActivity(i);
            finish();
        }, 2000);
    }
}
