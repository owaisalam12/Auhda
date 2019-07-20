package com.core2plus.auhda.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.core2plus.auhda.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookNowFragment extends Fragment {


    public BookNowFragment() {
        // Required empty public constructor
    }
    EditText editTextFirstName,editTextLastName,editTextEmail,editTextPhone;
    RadioGroup radioGroupPayment;
    RadioButton radioButtonPayMethod;
    String color,firstName,lastName,email,phone,payment,resumeCode,price;
    int quantity,product_id;
    Button buttonbookNow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_book_now, container, false);
        editTextFirstName=view.findViewById(R.id.booknow_firstName);
        editTextLastName=view.findViewById(R.id.booknow_lastName);
        editTextEmail=view.findViewById(R.id.booknow_email);
        editTextPhone=view.findViewById(R.id.booknow_phone);
        radioGroupPayment=view.findViewById(R.id.booknow_radioGrPayment);

        buttonbookNow=view.findViewById(R.id.booknow_NextButton);
        if(getArguments()!=null){
            color=getArguments().getString("color");
            resumeCode=getArguments().getString("resumeCode");
            price=getArguments().getString("price");
            quantity=getArguments().getInt("quantity");
            product_id=getArguments().getInt("product_id");
        }

        buttonbookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEditText(editTextFirstName,"This field is required")) {
                    return;
                }
                if (!validateEditText(editTextLastName,"This field is required")) {
                    return;
                }
                if (!validateEditText(editTextEmail,"This field is required")) {
                    return;
                }
                if (!validateEditText(editTextPhone,"This field is required")) {
                    return;
                }

                firstName=editTextFirstName.getText().toString();
                lastName=editTextLastName.getText().toString();
                email=editTextEmail.getText().toString();
                phone=editTextPhone.getText().toString();

                int selectedId = radioGroupPayment.getCheckedRadioButtonId();
                Log.v("radio",String.valueOf(selectedId));
                radioButtonPayMethod=radioGroupPayment.findViewById(selectedId);
                payment=radioButtonPayMethod.getText().toString();
                Log.v("radio2",String.valueOf(payment));

                Bundle args = new Bundle();
                args.putString("color", color);
                args.putString("price", price);
                args.putString("resumeCode", resumeCode);
                args.putInt("quantity", quantity);
                args.putInt("product_id", product_id);
                args.putString("firstName", firstName);
                args.putString("lastName", lastName);
                args.putString("email", email);
                args.putString("phone", phone);
                args.putString("payment", payment);
                Log.v("radio",String.valueOf(payment));

                goToFragment(new PlaceOrderFragment(),args);

            }
        });


        return view;
    }
    private void goToFragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, fragment).addToBackStack(null).commit();
    }
    private boolean validateEditText(EditText editText, String errMsg) {
        if (editText.getText().toString().trim().isEmpty()) {
            // inputLayoutName.setError("Enter your name");
            editText.setError(errMsg);
            requestFocus(editText);
            return false;
        } else {

        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
