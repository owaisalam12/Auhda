package com.core2plus.auhda.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core2plus.auhda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobPortalFragment extends Fragment {


    public JobPortalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_portal, container, false);
    }

}
