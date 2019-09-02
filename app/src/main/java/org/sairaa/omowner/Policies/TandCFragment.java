package org.sairaa.omowner.Policies;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.sairaa.omowner.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class TandCFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)   {
      View v=  inflater.inflate(R.layout.fragment_tandc, viewGroup, false);



        return v;
    }
}