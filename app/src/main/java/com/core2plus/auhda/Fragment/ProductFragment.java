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

import com.core2plus.auhda.API.Responses.productMessage;
import com.core2plus.auhda.API.Responses.productResponse;
import com.core2plus.auhda.API.RetrofitClient;
import com.core2plus.auhda.Activity.MainActivity;
import com.core2plus.auhda.Adapter.ProductAdapter;
import com.core2plus.auhda.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private Context context;
    List<productResponse> listing;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    AVLoadingIndicatorView avLoadingIndicatorView;
    ProductFragment current;
    ProductVariationsFragment newFragment;
    public ProductFragment() {
        // Required empty public constructor
    }

    String level;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = view.findViewById(R.id.recyclerview_product);
        listing=new ArrayList<>();
        context=getActivity();
        current=new ProductFragment();
        newFragment=new ProductVariationsFragment();
        avLoadingIndicatorView=view.findViewById(R.id.loadingIndicator_product);
        productAdapter = new ProductAdapter(listing,context,this);
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
        if(getArguments()!=null) {
            level = getArguments().getString("level");
            if(level=="Entry Level"){
                getActivity().setTitle("Entry Level");
            }else if(level=="Professional Level"){
                getActivity().setTitle("Professional Level");
            }else if(level=="Executive Level"){
                getActivity().setTitle("Executive Level");
            }else{

            }
        }
        getResume(level);
       // getResumeVariations();
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
    private void getResume(String level){
        Call<productResponse> call= RetrofitClient.getInstance().getApi().getProducts(level);
        call.enqueue(new Callback<productResponse>() {
            @Override
            public void onResponse(Call<productResponse> call, Response<productResponse> response) {
                productResponse productResponses=response.body();


                if(productResponses.getMessage()!=null) {
                    for (productMessage productMessage : productResponses.getMessage()) {

                        Log.v("product", productMessage.getImage());
                        Log.v("product", productMessage.getLevel());
                        Log.v("product", productMessage.getName());
                        Log.v("product", productMessage.getPrice());
                        Log.v("product", productMessage.getId().toString());
                        Log.v("product", productMessage.getVariations().toString());

                        String image=productMessage.getImage();
                        String level=productMessage.getLevel();
                        String name=productMessage.getName();
                        String price=productMessage.getPrice();
                        int id=productMessage.getId();
                        listing.add(productResponses);



                    }
                }
                avLoadingIndicatorView.setVisibility(View.GONE);
                stopLoading();
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<productResponse> call, Throwable t) {
                Log.v("productsErr",t.getMessage().toString());
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                avLoadingIndicatorView.setVisibility(View.GONE);
                stopLoading();


            }
        });
    }





    public void openMovieDetailFragment(int position, View view) {
        if (context instanceof MainActivity) {
            productResponse jobsResponse = listing.get(position);
            ProductVariationsFragment  productVariationsFragment = new ProductVariationsFragment();
            Bundle bundle = new Bundle();

            bundle.putString("transitionName3", "transition3" + position);

            String name=jobsResponse.getMessage().get(position).getName();
            String price=jobsResponse.getMessage().get(position).getPrice();
            String level=jobsResponse.getMessage().get(position).getLevel();
            String image=jobsResponse.getMessage().get(position).getImage();
            int id=jobsResponse.getMessage().get(position).getId();
            String variations=jobsResponse.getMessage().get(position).getVariations().toString();
            bundle.putString("name",name);
            bundle.putString("price",price);
            bundle.putString("level",level);
            bundle.putString("image",image);
            bundle.putString("id",String.valueOf(id));
            bundle.putString("variations",variations);

            productVariationsFragment.setArguments(bundle);
            ((MainActivity) context).showFragmentWithTransition(this, productVariationsFragment, "movieDetail3", view, "transition3" + position);
        }
    }
}
