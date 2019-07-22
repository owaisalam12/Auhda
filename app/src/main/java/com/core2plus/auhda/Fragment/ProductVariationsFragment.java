package com.core2plus.auhda.Fragment;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.core2plus.auhda.API.Responses.variationMessage;
import com.core2plus.auhda.API.Responses.variationsResponse;
import com.core2plus.auhda.API.RetrofitClient;
import com.core2plus.auhda.R;
import com.travijuu.numberpicker.library.NumberPicker;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductVariationsFragment extends Fragment {


    public ProductVariationsFragment() {
        // Required empty public constructor
    }
    Spinner spinnerColors;
    ImageView imageView;
    ArrayList<String> arrayListColors;
    ArrayAdapter<String> variations_adapter;
    Map<String, String> HashMap_Atrributes;
    Map<String, String> HashMap_Name;
    Map<String, String> HashMap_Price;
    Map<String, Integer> HashMap_productId;
    AVLoadingIndicatorView avLoadingIndicatorView;
    Button bookNowButton;
    NumberPicker quantityPicker;
    String imageLoadedUrl;
    String id,name,price,level;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_variations, container, false);
        imageView=view.findViewById(R.id.designImageView);
        spinnerColors =view.findViewById(R.id.resumeColors_spinner);
        avLoadingIndicatorView=view.findViewById(R.id.loadingIndicator_variations);
        arrayListColors=new ArrayList<>();
        bookNowButton=view.findViewById(R.id.resumeBookNowButton);
        quantityPicker=view.findViewById(R.id.resumeQuantity_picker);
        HashMap_Atrributes = new LinkedHashMap<String, String>();
        HashMap_Name = new LinkedHashMap<String, String>();
        HashMap_Price = new LinkedHashMap<String, String>();
        HashMap_productId = new LinkedHashMap<String, Integer>();
        variations_adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, arrayListColors);
        spinnerColors.setAdapter(variations_adapter);

        Bundle b = getArguments();
        if (b != null) {
            String transitionName = b.getString("transitionName3");
             name = b.getString("name");
             price = b.getString("price");
             level = b.getString("level");
            String image = b.getString("image");
             id = b.getString("id");
            String variations = b.getString("variations");

//            auhdaResponse auhdaResponse = (auhdaResponse) b.getSerializable("movie");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setTransitionName(transitionName);

            }
            Glide.with(getContext()).load(image).into(imageView);
            getResumeVariations(Integer.parseInt(id));
//            if(arrayListColors.isEmpty()||arrayListColors==null||arrayListColors.size()<0){
//                arrayListColors.add("Unavailable");
            imageLoadedUrl=image;
           // Log.v("imageLoadedUrl", image);
//                Log.v("unava", "color not available");
//                //variations_adapter.notifyDataSetChanged();
//            }


        }

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String color="";
                int product_id;
                String resumeCode;
                String price2;

                int quantity=quantityPicker.getValue();

                if(spinnerColors.getSelectedItem()!=null){
                     color = spinnerColors.getSelectedItem().toString();

                }

                if(!HashMap_Name.isEmpty()){
                     resumeCode = HashMap_Name.get(imageLoadedUrl);
                }else{
                    resumeCode=name;
                }
                if(!HashMap_Price.isEmpty()){
                    price2=HashMap_Price.get(imageLoadedUrl);
                }else{
                    price2=price;
                }
                if(!HashMap_productId.isEmpty()){
                     product_id=HashMap_productId.get(imageLoadedUrl);

                }else{
                    product_id=Integer.parseInt(id);
                }

                Log.v("imageLoadedUrl2",String.valueOf(quantity));
                Log.v("imageLoadedUrl2",String.valueOf(resumeCode));
                Log.v("imageLoadedUrl2",String.valueOf(price2));
                Log.v("imageLoadedUrl2",String.valueOf(product_id));

                Bundle args = new Bundle();
                args.putString("color", color);
                args.putInt("quantity", quantity);
                args.putInt("product_id", product_id);
                args.putString("price", price2);
                args.putString("resumeCode", resumeCode);



                goToFragment(new BookNowFragment(),args);
            }
        });
       spinnerColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String attribute = spinnerColors.getSelectedItem().toString();
               if(attribute!=null) {
                   final String url = HashMap_Atrributes.get(attribute);
                   avLoadingIndicatorView.setVisibility(View.VISIBLE);
                   startLoading();
                   Glide.with(getContext()).load(url).addListener(new RequestListener<Drawable>() {
                       @Override
                       public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                           avLoadingIndicatorView.setVisibility(View.GONE);
                           stopLoading();
                           return false;
                       }

                       @Override
                       public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                           imageLoadedUrl=url;
                           avLoadingIndicatorView.setVisibility(View.GONE);
                           stopLoading();
                           return false;
                       }
                   })

                           .into(imageView);

               }else{

               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });



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
    private void getResumeVariations(int productId){
        Call<variationsResponse> call= RetrofitClient.getInstance().getApi().getProductVariations(productId);
        call.enqueue(new Callback<variationsResponse>() {
            @Override
            public void onResponse(Call<variationsResponse> call, Response<variationsResponse> response) {
                variationsResponse variationsResponse=response.body();
                if(variationsResponse.getMessage()!=null) {
                    for (variationMessage variationMessage : variationsResponse.getMessage()) {
                        Log.v("productVarF", variationMessage.getId().toString());
                        Log.v("productVarF", variationMessage.getPrice());
                        Log.v("productVarF", variationMessage.getAttributes().getKey());
                        Log.v("productVarF", variationMessage.getAttributes().getValue());
                        Log.v("productVarF", variationMessage.getImage().getUrl());
                        String myString=variationMessage.getAttributes().getValue();
                        if(myString!=null || !myString.isEmpty()||myString.length()>0){
                            String upperString = myString.substring(0,1).toUpperCase() + myString.substring(1);
                            HashMap_Atrributes.put(upperString,variationMessage.getImage().getUrl());
//                            HashMap_Atrributes.put(upperString,variationMessage.getImage().getUrl());
//                            HashMap_Name.put(upperString,variationMessage.getImage().getName());
//                            HashMap_Price.put(upperString,variationMessage.getPrice());
//                            HashMap_productId.put(variationMessage.getImage().getName(),variationMessage.getId());

                            HashMap_Name.put(variationMessage.getImage().getUrl(),variationMessage.getImage().getName());
                            HashMap_Price.put(variationMessage.getImage().getUrl(),variationMessage.getPrice());
                            HashMap_productId.put(variationMessage.getImage().getUrl(),variationMessage.getId());


                            arrayListColors.add(upperString);
                            variations_adapter.notifyDataSetChanged();
                        }



                    }
                }





            }

            @Override
            public void onFailure(Call<variationsResponse> call, Throwable t) {

            }
        });

//        if((spinnerColors.getCount()<1) ||spinnerColors.getAdapter()==null ){
//            //spinnerColors.setEnabled(false);
//            arrayListColors.add("Unavailable");
//            Log.v("unava", "color not available");
//            variations_adapter.notifyDataSetChanged();
//            spinnerColors.setSelection(0);
//        }

    }
    private void goToFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).addToBackStack(null).commit();
    }

}
