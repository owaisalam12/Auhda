package com.core2plus.auhda.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.core2plus.auhda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeDesignExecFragment extends Fragment {

    Button buttonOrderExec;
    public ResumeDesignExecFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resume_design_exec, container, false);
        buttonOrderExec=view.findViewById(R.id.orderButtonResumeExec);
        buttonOrderExec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "resume: Exec clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
