package org.sairaa.omowner.Policies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.sairaa.omowner.R;

public class TandCFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)   {
        return inflater.inflate(R.layout.fragment_tandc, viewGroup, false);
    }
}