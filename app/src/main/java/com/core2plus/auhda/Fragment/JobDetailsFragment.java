package com.core2plus.auhda.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.core2plus.auhda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobDetailsFragment extends Fragment {
    TextView textViewTitle,textViewContent,textViewDate;
    ImageView imageView;

    public JobDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_job_details, container, false);
        textViewTitle=view.findViewById(R.id.jobTitleTextViewDetails);
        textViewContent=view.findViewById(R.id.jobContentTextViewDetails);
        textViewDate=view.findViewById(R.id.jobDateTextViewDetails);
        imageView=view.findViewById(R.id.jobImgUrl);
        Bundle b = getArguments();
        if (b != null) {
            String transitionName = b.getString("transitionName2");
            String title = b.getString("title2");
            String content = b.getString("content2");
            String date = b.getString("date2");
//            auhdaResponse auhdaResponse = (auhdaResponse) b.getSerializable("movie");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setTransitionName(transitionName);
            }
            imageView.setImageResource(R.drawable.no_image_available);

//            if (auhdaResponse != null) {
//
//                textView.setText(auhdaResponse.getTitle().getRendered());
//            }
            if (title != null) {

                textViewTitle.setText(title);
            }
            if (content != null) {

                textViewContent.setText(content);
            }
            if (date != null) {

                textViewDate.setText(date);
            }
        }

        return view;
    }

}
