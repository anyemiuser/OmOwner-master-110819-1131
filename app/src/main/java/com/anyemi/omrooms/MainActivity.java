package com.anyemi.omrooms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.anyemi.omrooms.Fragments.BookingFragment;
import com.anyemi.omrooms.Fragments.HomeFragment;
import com.anyemi.omrooms.Fragments.ProfileFragment;
import com.anyemi.omrooms.Fragments.SavedFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        loadFragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.action_saved:
                        fragment = new SavedFragment();
                        break;

                    case R.id.action_bookings:
                        fragment = new BookingFragment();
                        break;
                    case R.id.action_account:
                        fragment = new ProfileFragment();
                        break;
                }
                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
