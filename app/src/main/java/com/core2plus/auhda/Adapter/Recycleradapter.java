package com.core2plus.auhda.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core2plus.auhda.API.Responses.auhdaResponse;
import com.core2plus.auhda.R;

import org.jsoup.Jsoup;

import java.util.List;

public class Recycleradapter extends RecyclerView.Adapter<Recycleradapter.MyHolder> implements View.OnClickListener {
    // TODO: 24-Jun-19 url
//    private static final String BASE_URL="http://192.168.137.1/food/assets/images/food_image";
    //private static final String BASE_URL = Constants.Img_URL_Available;
    // private static final String BASE_URL="http://core2plus.com/food/assets/images/";
    List<auhdaResponse> list;
    private Context context;

    public Recycleradapter(List<auhdaResponse> list) {
        this.list = list;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wp_list, parent, false);
        MyHolder myHolder = new MyHolder(view);
        context = parent.getContext();
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        auhdaResponse product = list.get(position);

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
        TextView Title, Date;

        public MyHolder(View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.TitleTextView);
            Date = itemView.findViewById(R.id.dateTextView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {


//            Log.d("Recycler", "onClick " + getAdapterPosition() + " " + list.get(getAdapterPosition()).getDeals().get(getAdapterPosition()).getDealsName());
            if(list.get(getAdapterPosition()).getContent()!=null){
                String content=list.get(getAdapterPosition()).getContent().getRendered();
                String id=list.get(getAdapterPosition()).getId().toString();

                String content2= Jsoup.parse(content).text();

            Log.d("Recycler", "onClick " + getAdapterPosition() + " ID: " +id+" Content: "+content2);
        }
        }
    }

}
