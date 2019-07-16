package com.core2plus.auhda.Fragment;


import android.graphics.Movie;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.core2plus.auhda.API.Responses.auhdaResponse;
import com.core2plus.auhda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogDetailsFragment extends Fragment {

    TextView textViewTitle,textViewContent,textViewDate;
    ImageView imageView;
    public BlogDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blog_details, container, false);
        textViewTitle=view.findViewById(R.id.titleTextViewDetails);
        textViewContent=view.findViewById(R.id.contentTextViewDetails);
        textViewDate=view.findViewById(R.id.dateTextViewDetails);
        imageView=view.findViewById(R.id.imgUrl);
        Bundle b = getArguments();
        if (b != null) {
            String transitionName = b.getString("transitionName");
            String title = b.getString("title");
            String content = b.getString("content");
            String date = b.getString("date");
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
