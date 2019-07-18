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
public class ResumeDesignEntryFragment extends Fragment {

    Button buttonOrderEntry,buttonOrderPro,buttonOrderExec;
    public ResumeDesignEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resume_design_entry, container, false);
        buttonOrderEntry=view.findViewById(R.id.orderButtonResumeEntry);
//        buttonOrderPro=view.findViewById(R.id.orderButtonResumePro);
//        buttonOrderExec=view.findViewById(R.id.orderButtonResumeExec);
        // buttonOrderEntry.setOnClickListener(this);
//        buttonOrderPro.setOnClickListener(this);
//        buttonOrderExec.setOnClickListener(this);

        buttonOrderEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(), "resume: Entry clicked!", Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                args.putString("level", "Entry Level");
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
