package com.core2plus.auhda.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core2plus.auhda.API.Responses.jobsResponse;
import com.core2plus.auhda.Fragment.JobPortalFragment;
import com.core2plus.auhda.Fragment.JobDetailsFragment;
import com.core2plus.auhda.R;

import org.jsoup.Jsoup;

import java.util.List;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.MyHolder> implements View.OnClickListener {
    // TODO: 24-Jun-19 url
//    private static final String BASE_URL="http://192.168.137.1/food/assets/images/food_image";
    //private static final String BASE_URL = Constants.Img_URL_Available;
    // private static final String BASE_URL="http://core2plus.com/food/assets/images/";
    List<jobsResponse> list;
    private Context context;
    private JobPortalFragment fragment;
    public JobsAdapter(List<jobsResponse> list) {
        this.list = list;
    }

    public JobsAdapter(List<jobsResponse> list, Context context, JobPortalFragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public JobsAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wp_jobslist, parent, false);
        JobsAdapter.MyHolder myHolder = new JobsAdapter.MyHolder(view);
        context = parent.getContext();
        return myHolder;
    }

    @Override
    public void onBindViewHolder(JobsAdapter.MyHolder holder, int position) {

        jobsResponse product = list.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // the view being shared
            holder.imageView.setTransitionName("transition" + position);
        }
        if(product.getTitle()!=null && product.getContent()!=null ) {
            String id = product.getId().toString();
            String content = product.getContent().getRendered().toString();
            String date = product.getDate();
            String title = product.getTitle().getRendered();

            String date2 = date.replace("T", " ");
            String content2 = Jsoup.parse(content).text();


            holder.Title.setText(title);
            holder.Date.setText(date2);
        }
        // BlurImage.withContext(context).blurFromUri(BASE_URL+image1)
        //       .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        Log.d("Recycler", "onClick " + v.toString());
    }




    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Title, Date, Type;
        ImageView imageView;
        JobDetailsFragment jobDetailsFragment;
        public MyHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.jobTitleTextView);
            Date = itemView.findViewById(R.id.jobDateTextView);
            imageView=itemView.findViewById(R.id.jobImgUrl);
            itemView.setOnClickListener(this);
            jobDetailsFragment=new JobDetailsFragment();
        }



        @Override
        public void onClick(View v) {


//            Log.d("Recycler", "onClick " + getAdapterPosition() + " " + list.get(getAdapterPosition()).getDeals().get(getAdapterPosition()).getDealsName());
            if(list.get(getAdapterPosition()).getContent()!=null){
                String content=list.get(getAdapterPosition()).getContent().getRendered();
                String id=list.get(getAdapterPosition()).getId().toString();

                String content2= Jsoup.parse(content).text();

                Log.d("Recycler", "onClick " + getAdapterPosition() + " ID: " +id+" Content: "+content2);

                fragment.openMovieDetailFragment(getAdapterPosition(), v.findViewById(R.id.jobImgUrl));


            }
        }
    }

}
