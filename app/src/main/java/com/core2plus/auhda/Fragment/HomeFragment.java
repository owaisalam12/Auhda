package com.core2plus.auhda.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.core2plus.auhda.Adapter.SliderAdapter;
import com.core2plus.auhda.R;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{


    public HomeFragment() {
        // Required empty public constructor
    }
    SliderView sliderView;
    CardView cardViewResume,cardViewJob,cardViewHr,cardViewBlog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = view.findViewById(R.id.imageSlider);
        cardViewResume = view.findViewById(R.id.card_home_resume);
        cardViewJob = view.findViewById(R.id.card_home_jobPortal);
        cardViewHr = view.findViewById(R.id.card_home_HR);
        cardViewBlog = view.findViewById(R.id.card_home_blogs);

        cardViewResume.setOnClickListener(this);
        cardViewJob.setOnClickListener(this);
        cardViewBlog.setOnClickListener(this);
        cardViewHr.setOnClickListener(this);

        final SliderAdapter adapter = new SliderAdapter(getContext());
        adapter.setCount(3);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });

        getActivity().setTitle("Home");
        return view;
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.card_home_resume:
            goToFragment(new ResumeDesigningFragment());
            break;
        case R.id.card_home_jobPortal:
            goToFragment(new JobPortalFragment());

            break;
        case R.id.card_home_HR:
            goToFragment(new HRFragment());

            break;
        case R.id.card_home_blogs:
            goToFragment(new BlogFragment());

            break;
    }
    }
    private void goToFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).addToBackStack(null).commit();
    }

}
