package com.core2plus.auhda.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.core2plus.auhda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeDesignProFragment extends Fragment {

    Button buttonOrderPro;
    public ResumeDesignProFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resume_design_pro, container, false);
        buttonOrderPro=view.findViewById(R.id.orderButtonResumePro);
        buttonOrderPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(), "resume: Pro clicked!", Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putString("level", "Professional Level");
                goToFragment(new ProductFragment(),args);

            }
        });
        return view;
    }
    private void goToFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).addToBackStack(null).commit();
    }
}
