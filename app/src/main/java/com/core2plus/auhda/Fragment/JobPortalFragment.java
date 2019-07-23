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
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.core2plus.auhda.API.Responses.jobsResponse;
import com.core2plus.auhda.API.RetrofitClient;
import com.core2plus.auhda.Activity.MainActivity;
import com.core2plus.auhda.Adapter.JobsAdapter;
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
public class JobPortalFragment extends Fragment {

    private Context context;
    List<jobsResponse> listing;
    JobsAdapter jobsAdapter;
    RecyclerView recyclerView;
    AVLoadingIndicatorView avLoadingIndicatorView;
    JobPortalFragment current;
    JobDetailsFragment newFragment;

    public JobPortalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_portal, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_job);
        listing=new ArrayList<>();
        context=getActivity();
        current=new JobPortalFragment();
        newFragment=new JobDetailsFragment();
        avLoadingIndicatorView=view.findViewById(R.id.loadingIndicator_job);
        jobsAdapter = new JobsAdapter(listing,context,this);
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
        getAuhdaJobs();
        getActivity().setTitle("Job Portal");

        return view;
    }
    void startLoading(){
        avLoadingIndicatorView.show();
        // or avi.smoothToShow();
    }

    void stopLoading(){
        avLoadingIndicatorView.hide();
        // or avi.smoothToHide();
    }

    private void getAuhdaJobs(){
        Call<List<jobsResponse>> call = RetrofitClient.getInstance().getApi().getJobs();
        call.enqueue(new Callback<List<jobsResponse>>() {
            @Override
            public void onResponse(Call<List<jobsResponse>> call, Response<List<jobsResponse>> response) {
                List<jobsResponse> jobsResponses=response.body();
                if(jobsResponses!=null){
//            for(int i=0;i<auhdaResponses.size();i++){
                    for(jobsResponse jobsResponse : jobsResponses){
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
                        if(jobsResponse.getTitle()!=null && jobsResponse.getContent()!=null ){
                            String id=jobsResponse.getId().toString();
                            String content=jobsResponse.getContent().getRendered().toString();
                            String date=jobsResponse.getDate().toString();
//                    String date2[]=date.split("T");
                            String date2 =date.replace("T"," ");
                            String title=jobsResponse.getTitle().getRendered().toString();
                            String ht= Jsoup.parse(content).text();
                            Log.v("jobsID",id);
                            Log.v("jobsContent",ht);
                            Log.v("jobsDate",date2);
                            Log.v("jobsTitle",title);

                            listing.add(jobsResponse);
                        }
                    }
                    avLoadingIndicatorView.setVisibility(View.GONE);
                    stopLoading();
//                    RecyclerView.LayoutManager recyce = new LinearLayoutManager(getContext(), GridLayoutManager.HORIZONTAL, false);
//
                    recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(jobsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<jobsResponse>> call, Throwable t) {
                Log.v("jobsErr",t.getMessage().toString());
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                avLoadingIndicatorView.setVisibility(View.GONE);
                stopLoading();
            }
        });

    }
    public void openMovieDetailFragment(int position, View view) {
        if (context instanceof MainActivity) {
            jobsResponse jobsResponse = listing.get(position);
            JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();
            Bundle bundle = new Bundle();

            bundle.putString("transitionName2", "transition2" + position);
            String date=jobsResponse.getDate().toString();
//                    String date2[]=date.split("T");
            String date2 =date.replace("T"," ");
            String title=jobsResponse.getTitle().getRendered().toString();
            bundle.putString("content2",Jsoup.parse(jobsResponse.getContent().getRendered()).text());
            bundle.putString("date2",date2);
            bundle.putString("title2",title);

            jobDetailsFragment.setArguments(bundle);
            ((MainActivity) context).showFragmentWithTransition(this, jobDetailsFragment, "movieDetail2", view, "transition2" + position);
        }
    }
}
