package com.anyemi.omrooms.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.anyemi.omrooms.LoginActivity;
import com.anyemi.omrooms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;

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
