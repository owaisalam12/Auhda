package com.core2plus.auhda.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.core2plus.auhda.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {


    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_us, container, false);
        Element call = new Element();
        call.setTitle("+971552691957");
        call.setIconDrawable(R.drawable.ic_call_black);
        Element whatsapp = new Element();
        whatsapp.setTitle("0334 3043970");
        whatsapp.setIconDrawable(R.drawable.whatsapp_logo);
        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .setImage(R.drawable.oa_logo)
                .addItem(call)
                .addItem(whatsapp)
                .addGroup("Connect with us")
                .addEmail("hrauhda@gmail.com")
                .addWebsite("http://www.auhda.com")
                .addFacebook("auhda")
                .addPlayStore("com.core2plus.oalam.foodstudio")
                .create();

        return aboutPage;
    }

}
