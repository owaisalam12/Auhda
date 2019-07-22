package com.core2plus.auhda.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.core2plus.auhda.API.Responses.orderResponse;
import com.core2plus.auhda.API.Responses.productResponse;
import com.core2plus.auhda.API.RetrofitClient;
import com.core2plus.auhda.Fragment.ProductFragment;
import com.core2plus.auhda.Fragment.ProductVariationsFragment;
import com.core2plus.auhda.R;
import com.google.firebase.auth.FirebaseAuth;
//sfs
import java.util.List;

import dmax.dialog.SpotsDialog;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> implements View.OnClickListener {

    // TODO: 24-Jun-19 url
//    private static final String BASE_URL="http://192.168.137.1/food/assets/images/food_image";
    //private static final String BASE_URL = Constants.Img_URL_Available;
    // private static final String BASE_URL="http://core2plus.com/food/assets/images/";
    List<productResponse> list;
    private Context context;
    private ProductFragment fragment;
    private FirebaseAuth mAuth;
    private  String email,pass;
    private SharedPreferences sharedpreferences;
    AlertDialog b;
    private android.app.AlertDialog dialog;
    public ProductAdapter(List<productResponse> list) {
        this.list = list;
    }

    public ProductAdapter(List<productResponse> list, Context context, ProductFragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public ProductAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resumedesign_list, parent, false);
        ProductAdapter.MyHolder myHolder = new ProductAdapter.MyHolder(view);
        context = parent.getContext();
        return myHolder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.MyHolder holder, int position) {

        productResponse product = list.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // the view being shared
            holder.resumeImageView.setTransitionName("transition" + position);
        }
        if(product.getMessage()!=null) {
            int id = product.getMessage().get(position).getId();
            String image = product.getMessage().get(position).getImage();
            String level = product.getMessage().get(position).getLevel();
            String name = product.getMessage().get(position).getName();
            String price = product.getMessage().get(position).getPrice();
            List variations = product.getMessage().get(position).getVariations();

            Log.v("productAdp",Integer.toString(id));
            Log.v("productAdp",image);
            Log.v("productAdp",level);
            Log.v("productAdp",name);
            Log.v("productAdp",price);
            Log.v("productAdp",variations.toString());

//            holder.Title.setText(title);
//            holder.Date.setText(date2);
            Glide.with(context).load(image).into(holder.resumeImageView);
            holder.resumeCode.setText(name);
            holder.resumePrice.setText("Rs: "+price);
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
        TextView resumeCode, resumePrice;
        ImageView resumeImageView;
        Button resumeButton;
        ProductVariationsFragment productVariationsFragment;
        public MyHolder(View itemView) {
            super(itemView);
            resumeImageView = itemView.findViewById(R.id.designImageView);
            resumeCode = itemView.findViewById(R.id.designCodeTextView);
            resumePrice=itemView.findViewById(R.id.designPriceTextView);
            sharedpreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
            mAuth = FirebaseAuth.getInstance();
            dialog = new SpotsDialog.Builder().setContext(context)
                    .setCancelable(false)
                    .setTheme(R.style.Custom)
                    .build();
            itemView.setOnClickListener(this);
            productVariationsFragment=new ProductVariationsFragment();
        }



        @Override
        public void onClick(View v) {

////            Log.d("Recycler", "onClick " + getAdapterPosition() + " " + list.get(getAdapterPosition()).getDeals().get(getAdapterPosition()).getDealsName());
            if(list.get(getAdapterPosition()).getMessage()!=null){
                int id=list.get(getAdapterPosition()).getMessage().get(getAdapterPosition()).getId();
                String code=list.get(getAdapterPosition()).getMessage().get(getAdapterPosition()).getName();
                sharedpreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
                String pass = sharedpreferences.getString("pass", null);
                String email = sharedpreferences.getString("email", null);

                if (pass != null && email != null) {
//            Intent intent = new Intent(this, DashboardActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
                    Log.d("Recycler", "onClick " + getAdapterPosition() + " ID: " +id+ "NAME: "+code);
                    fragment.openMovieDetailFragment(getAdapterPosition(), v.findViewById(R.id.designImageView));

                }else{
                    Toast.makeText(context, "You must login first.", Toast.LENGTH_SHORT).show();
                    loginDialog(getAdapterPosition(),v.findViewById(R.id.designImageView));
                }

             
            }
        }

        private void loginDialog(final int adapterPosition, final View adapterView){
             final EditText editTextEmail,editTextPass;
             Button buttonSignIn,buttonSignUp;
             ImageView imageClose;
            TextView textViewDonthaveAccount,textViewForgetPass;

            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            final View dialogView = inflater.inflate(R.layout.activity_sign_in, null);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(false);

            mAuth = FirebaseAuth.getInstance();
            editTextEmail=dialogView.findViewById(R.id.input_email_signin);
            editTextPass=dialogView.findViewById(R.id.input_password_signin);
            buttonSignIn=dialogView.findViewById(R.id.signInButton);
            buttonSignUp=dialogView.findViewById(R.id.signUpButton2);
            imageClose=dialogView.findViewById(R.id.signInClose);
            textViewDonthaveAccount=dialogView.findViewById(R.id.donthaveAccountTextView);
            textViewForgetPass=dialogView.findViewById(R.id.forgetPassTextView);
            dialog = new SpotsDialog.Builder().setContext(context)
                    .setCancelable(false)
                    .setTheme(R.style.CustomDialog)
                    .build();


            b = dialogBuilder.create();
            b.show();
            textViewForgetPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "textViewForgetPass", Toast.LENGTH_SHORT).show();

                }
            });
            textViewDonthaveAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "textViewDonthaveAccount", Toast.LENGTH_SHORT).show();
                    b.setContentView(R.layout.activity_sign_up);

                }
            });

            buttonSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email=editTextEmail.getText().toString();
                    pass=editTextPass.getText().toString();
                    if (!validateEmail(editTextEmail,email)) {
                        return;
                    }
                    if (!validateEditTextPass(editTextPass, "Password is required")) {
                        return;
                    }
                    dialog.show();
                    //dialog.show();
//                    Toast.makeText(context, "buttonSignIn", Toast.LENGTH_SHORT).show();
                    signInUser(email,pass,adapterView,adapterPosition);

                }
            });
            buttonSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    email=editTextEmail.getText().toString();
                    pass=editTextPass.getText().toString();
                    if (!validateEmail(editTextEmail,email)) {
                        return;
                    }
                    if (!validateEditTextPass(editTextPass, "Password is required")) {
                        return;
                    }
                    dialog.show();
//                    Toast.makeText(context, "buttonSignUp", Toast.LENGTH_SHORT).show();
                    signUpUser(email,pass,adapterView,adapterPosition);

                }
            });
            imageClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    b.dismiss();
                }
            });
        }
    }

    private void signUpUser(final String email, final String pass, final View view, final int adapterPosition){
        Call<orderResponse> call= RetrofitClient.getInstance().getApi().signUpUser(email,pass);
        call.enqueue(new Callback<orderResponse>() {
            @Override
            public void onResponse(Call<orderResponse> call, Response<orderResponse> response) {
                orderResponse orderResponse=response.body();
                if(orderResponse.getSuccess()!=0){
                    dialog.dismiss();
                    Log.v("user",orderResponse.getMessage());
                    //Toast.makeText(getActivity(), orderResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    dialogSuccess(email,pass,view,adapterPosition);

                }else{
                    dialog.dismiss();
                    dialogError("Something went wrong",orderResponse.getMessage());
                    Log.v("user",orderResponse.getMessage());
                    // Toast.makeText(getActivity(), orderResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<orderResponse> call, Throwable t) {
                dialog.dismiss();
                dialogError("Something went wrong",t.getMessage().toString());

                Log.v("user",t.getMessage().toString());
                // Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void signInUser(final String email, final String pass, final View view, final int adapterPosition){
        Call<orderResponse> call= RetrofitClient.getInstance().getApi().signInUser(email,pass);
        call.enqueue(new Callback<orderResponse>() {
            @Override
            public void onResponse(Call<orderResponse> call, Response<orderResponse> response) {
                orderResponse orderResponse=response.body();
                if(orderResponse.getSuccess()!=0){
                    dialog.dismiss();
                    Log.v("user",orderResponse.getMessage());
                    //Toast.makeText(getActivity(), orderResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    dialogSuccess(email,pass,view,adapterPosition);

                }else{
                    dialog.dismiss();
                    dialogError("Something went wrong",orderResponse.getMessage());
                    Log.v("user",orderResponse.getMessage());
                    // Toast.makeText(getActivity(), orderResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<orderResponse> call, Throwable t) {
                dialog.dismiss();
                dialogError("Something went wrong",t.getMessage().toString());

                Log.v("user",t.getMessage().toString());
                // Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validateEditTextPass(EditText editText, String msg) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(msg);
            editText.requestFocus();

            return false;
        }
        if (editText.getText().toString().length()<6) {
            editText.setError("Password should be at least 6 characters");
            editText.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validateEmail(EditText editTextEmail, String email) {

        if (editTextEmail.getText().toString().trim().isEmpty()) {
            //inputLayoutEmail.setError("Enter your email");
            editTextEmail.setError("Enter your email");
            editTextEmail.requestFocus();
            return false;
        } else {
            // inputLayoutEmail.setErrorEnabled(false);

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //inputLayoutEmail.setError("Valid email is required");
            editTextEmail.setError("Valid email is required");
            editTextEmail.requestFocus();
            return false;
        } else {
            //inputLayoutName.setErrorEnabled(false);
        }
        return true;

    }

    private void dialogError(String title, String msg) {

        final PrettyDialog pDialog = new PrettyDialog(context);
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
    private void dialogSuccess(final String email, final String pass, final View view, final int adapterPosition){

        final PrettyDialog pDialog = new PrettyDialog(context);
        pDialog.setCancelable(false);
        pDialog

                .setIcon(R.drawable.pdlg_icon_success)
                .setIconTint(R.color.pdlg_color_green)
                .setAnimationEnabled(false)
                .setTitle("Success")
                .setMessage("You can order now.")

                .addButton(
                        "OK",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                //startActivity(intent);
                                //goToFragment(fragment);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("email", email);
                                editor.putString("pass", pass);
                                editor.commit();
                                pDialog.dismiss();
                                b.dismiss();
                                goToVariations(adapterPosition,view);

                            }
                        }
                )
                .show();
    }
    public void goToVariations(int pos, View view2){
        fragment.openMovieDetailFragment(pos, view2);

    }

}
