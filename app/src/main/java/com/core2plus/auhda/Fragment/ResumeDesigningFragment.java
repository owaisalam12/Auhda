package com.core2plus.auhda.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core2plus.auhda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumeDesigningFragment extends Fragment{

    Toolbar toolbar;

   // public static PagerTabStrip pagerTabStrip;
    public static ViewPager viewPager;
    public static int int_items = 3;
    public ResumeDesigningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_resume_designing, container, false);
        getActivity().setTitle("Resume Packages");
        //goToFragment(new ResumeDesignEntryFragment(),"Entry");
        toolbar=view.findViewById(R.id.toolbar);


//        pagerTabStrip=view.findViewById(R.id.pager_header);
        viewPager=view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager(),getContext()));




        return view;
    }
    private void goToFragment(Fragment fragment, String TAG) {
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment,TAG).commit();
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.orderButtonResumeEntry:
//                Toast.makeText(getActivity(), "resume: Entry clicked!", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.orderButtonResumePro:
//                Toast.makeText(getActivity(), "resume: Pro clicked!", Toast.LENGTH_SHORT).show();
//
//                break;
//
//            case R.id.orderButtonResumeExec:
//                Toast.makeText(getActivity(), "resume: Exec clicked!", Toast.LENGTH_SHORT).show();
//
//                break;
//        }
//    }

    class MyAdapter extends FragmentPagerAdapter {
        Context mContext;

        public MyAdapter(FragmentManager fm,Context context) {
            super(fm);
            mContext = context;
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ResumeDesignEntryFragment();
                case 1:
                    return new ResumeDesignProFragment();
                case 2:
                    return new ResumeDesignExecFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Entry Level";
                case 1:
                    return "Professional Level";
                case 2:
                    return "Executive Level";
            }
            return null;
        }
    }
}
