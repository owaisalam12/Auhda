package com.core2plus.auhda.Fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.core2plus.auhda.API.Responses.orderResponse;
import com.core2plus.auhda.API.RetrofitClient;
import com.core2plus.auhda.R;

import dmax.dialog.SpotsDialog;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceOrderFragment extends Fragment {


    public PlaceOrderFragment() {
        // Required empty public constructor
    }
    String color,firstName,lastName,email,phone,payment,resumeCode,price;
    int quantity,product_id;
    TextView editTextResumeName,editTextResumePrice,editTextResumeSubPrice,editTextResumeVat10,editTextResumeTotal;
    Button buttonPlaceOrder;
    AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_place_order, container, false);
        editTextResumeName=view.findViewById(R.id.placeResumeNameText);
        editTextResumePrice=view.findViewById(R.id.placeResumePriceText);
        editTextResumeSubPrice=view.findViewById(R.id.placeResumeSubPriceText);
        editTextResumeVat10=view.findViewById(R.id.placeResumeVat10Text);
        editTextResumeTotal=view.findViewById(R.id.placeResumeTotalPriceText);
        buttonPlaceOrder=view.findViewById(R.id.placeResumePlaceButton);
        dialog = new SpotsDialog.Builder().setContext(getContext())
                .setCancelable(false)
                .setTheme(R.style.Custom)
                .build();
        if(getArguments()!=null){
            color=getArguments().getString("color");
            quantity=getArguments().getInt("quantity");
            product_id=getArguments().getInt("product_id");
            resumeCode=getArguments().getString("resumeCode");
            firstName=getArguments().getString("firstName");
            lastName=getArguments().getString("lastName");
            email=getArguments().getString("email");
            price=getArguments().getString("price");
            phone=getArguments().getString("phone");
            payment=getArguments().getString("payment");

        }
        editTextResumeVat10.setText("Rs 0.00");
        if(resumeCode!=null){
            editTextResumeName.setText(resumeCode+" x "+quantity);
        }
        int priceQuantity=Integer.parseInt(price)*quantity;

        if(priceQuantity>0){
            editTextResumePrice.setText("Rs "+priceQuantity+".00");
            editTextResumeSubPrice.setText("Rs "+priceQuantity+".00");
            editTextResumeTotal.setText("Rs "+priceQuantity+".00");

        }

        buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                createOrder(firstName,lastName,email,phone,payment,product_id,quantity);

            }
        });



        return view;
    }

    private void createOrder(String first_name, String last_name,String email,String phone,String payment_method,int product_id,int quantity){
        Call<orderResponse>call= RetrofitClient.getInstance().getApi().createOrder(first_name,last_name,email,phone,payment_method,product_id,quantity);
        call.enqueue(new Callback<orderResponse>() {
            @Override
            public void onResponse(Call<orderResponse> call, Response<orderResponse> response) {
                orderResponse orderResponse=response.body();
                if(orderResponse.getSuccess()!=0){
                    dialog.dismiss();
                    Log.v("order",orderResponse.getMessage());
                    //Toast.makeText(getActivity(), orderResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    dialogSuccess(new HomeFragment());

                }else{
                    dialog.dismiss();
                    dialogError("Something went wrong",orderResponse.getMessage());
                    Log.v("order",orderResponse.getMessage());
                   // Toast.makeText(getActivity(), orderResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<orderResponse> call, Throwable t) {
                dialog.dismiss();
                dialogError("Something went wrong",t.getMessage().toString());

                Log.v("order",t.getMessage().toString());
               // Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void dialogError(String title, String msg) {

        final PrettyDialog pDialog = new PrettyDialog(getContext());
        pDialog.setCancelable(false);
        pDialog

                .setIcon(R.drawable.pdlg_icon_close)
                .setIconTint(R.color.pdlg_color_red)
                .setAnimationEnabled(false)
                .setTitle(title)
                .setMessage(msg)

                .addButton(
                        "OK",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_red,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                pDialog.dismiss();
                            }
                        }
                )
                .show();
    }
    private void dialogSuccess(final Fragment fragment){

        final PrettyDialog pDialog = new PrettyDialog(getContext());
        pDialog.setCancelable(false);
        pDialog

                .setIcon(R.drawable.pdlg_icon_success)
                .setIconTint(R.color.pdlg_color_green)
                .setAnimationEnabled(false)
                .setTitle("Congratulations")
                .setMessage("Your order has been confirmed. We will contact you soon.")

                .addButton(
                        "OK",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                //startActivity(intent);
                                goToFragment(fragment);
                                pDialog.dismiss();
                            }
                        }
                )
                .show();
    }
    private void goToFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).addToBackStack(null).commit();
    }
}
