package com.core2plus.auhda.Fragment;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.core2plus.auhda.API.Responses.auhdaResponse;
import com.core2plus.auhda.API.RetrofitClient;
import com.core2plus.auhda.Activity.MainActivity;
import com.core2plus.auhda.Adapter.Recycleradapter;
import com.core2plus.auhda.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogFragment extends Fragment {
    List<auhdaResponse> listing;
    Recycleradapter recyclerAdapter;
    RecyclerView recyclerView;
    AVLoadingIndicatorView avLoadingIndicatorView;
    BlogFragment current;
    BlogDetailsFragment newFragment;
    private Context context;
    public BlogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blog, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_blogs);
        listing=new ArrayList<>();
        context=getActivity();
        current=new BlogFragment();
        newFragment=new BlogDetailsFragment();
        avLoadingIndicatorView=view.findViewById(R.id.loadingIndicator_blog);
        recyclerAdapter = new Recycleradapter(listing,context,this);
        avLoadingIndicatorView.setVisibility(View.VISIBLE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            current.setSharedElementReturnTransition(TransitionInflater.
                    from(getActivity()).inflateTransition(R.transition.default_transition));
            current.setExitTransition(TransitionInflater.
                    from(getActivity()).inflateTransition(android.R.transition.no_transition));
            newFragment.setSharedElementEnterTransition(TransitionInflater.
                    from(getActivity()).inflateTransition(R.transition.default_transition));
            newFragment.setEnterTransition(TransitionInflater.
                    from(getActivity()).inflateTransition(android.R.transition.no_transition));
        }

        startLoading();
        getAuhdaJobPosts();
        getActivity().setTitle("Blogs");

        return view;
    }
    private void getAuhdaJobPosts(){
        Call<List<auhdaResponse>> call= RetrofitClient.getInstance().getApi().getPosts();
        call.enqueue(new Callback<List<auhdaResponse>>() {
            @Override
            public void onResponse(Call<List<auhdaResponse>> call, Response<List<auhdaResponse>> response) {
                List<auhdaResponse> auhdaResponses=response.body();
                if(auhdaResponses!=null){
//            for(int i=0;i<auhdaResponses.size();i++){
                    for(auhdaResponse auhdaResponse : auhdaResponses){
//                if(auhdaResponses.get(i).getTitle()!=null && auhdaResponses.get(i).getContent()!=null ){
//                    String id=auhdaResponses.get(i).getId().toString();
//                    String content=auhdaResponses.get(i).getContent().getRendered().toString();
//                    String date=auhdaResponses.get(i).getDate().toString();
////                    String date2[]=date.split("T");
//                    String date2 =date.replace("T"," ");
//                    String title=auhdaResponses.get(i).getTitle().getRendered().toString();
//                    String a=(content.replace("\\n"," "));
//                    String b=(a.replace("\\r"," "));
//                    String c=(b.replace("\\t"," "));
                        //String ht=Html.fromHtml(c).toString();
                        if(auhdaResponse.getTitle()!=null && auhdaResponse.getContent()!=null ){
                            String id=auhdaResponse.getId().toString();
                            String content=auhdaResponse.getContent().getRendered().toString();
                            String date=auhdaResponse.getDate().toString();
//                    String date2[]=date.split("T");
                            String date2 =date.replace("T"," ");
                            String title=auhdaResponse.getTitle().getRendered().toString();
                            String ht= Jsoup.parse(content).text();
                            Log.v("auhdaID",id);
                            Log.v("auhdaContent",ht);
                            Log.v("auhdaDate",date2);
                            Log.v("auhdaTitle",title);
//                    listing.add(auhdaResponses);
                            listing.add(auhdaResponse);
                        }
                    }
                    avLoadingIndicatorView.setVisibility(View.GONE);
                    stopLoading();
//                recyclerAdapter = new Recycleradapter(listing, ImageLoader.getInstance());
                    RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext(), GridLayoutManager.VERTICAL, false);
                    // RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
                    //recyclerView.addItemDecoration(new GridSpacingdecoration(2, dpToPx(10), true));
                    recyclerView.setLayoutManager(recyce);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(recyclerAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<auhdaResponse>> call, Throwable t) {
                Log.v("auhdaErr",t.getMessage().toString());
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                avLoadingIndicatorView.setVisibility(View.GONE);
                stopLoading();
            }
        });

    }
    void startLoading(){
        avLoadingIndicatorView.show();
        // or avi.smoothToShow();
    }

    void stopLoading(){
        avLoadingIndicatorView.hide();
        // or avi.smoothToHide();
    }
    public void openMovieDetailFragment(int position, View view) {
        if (context instanceof MainActivity) {
            auhdaResponse auhdaResponse = listing.get(position);
            BlogDetailsFragment blogDetailsFragment = new BlogDetailsFragment();
            Bundle bundle = new Bundle();

            bundle.putString("transitionName", "transition" + position);
            String date=auhdaResponse.getDate().toString();
//                    String date2[]=date.split("T");
            String date2 =date.replace("T"," ");
            String title=auhdaResponse.getTitle().getRendered().toString();
            bundle.putString("content",Jsoup.parse(auhdaResponse.getContent().getRendered()).text());
            bundle.putString("date",date2);
            bundle.putString("title",title);

            blogDetailsFragment.setArguments(bundle);
            ((MainActivity) context).showFragmentWithTransition(this, blogDetailsFragment, "movieDetail", view, "transition" + position);
        }
    }
}
