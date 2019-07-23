package com.core2plus.auhda.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.core2plus.auhda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HRFragment extends Fragment {


    public HRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hr, container, false);
        getActivity().setTitle("Auhda HR Consultancy");
        return view;
    }

}
