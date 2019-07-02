package com.core2plus.auhda.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.core2plus.auhda.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmax.dialog.SpotsDialog;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class SignInActivity extends AppCompatActivity {
    private EditText editTextEmail,editTextPass;
    private Button buttonSignIn;
    private FirebaseAuth mAuth;
    private  String email,pass;
    private AlertDialog dialog;
    private TextView textViewDonthaveAccount,textViewForgetPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.input_email_signin);
        editTextPass=findViewById(R.id.input_password_signin);
        buttonSignIn=findViewById(R.id.signInButton);
        textViewDonthaveAccount=findViewById(R.id.donthaveAccountTextView);
        textViewForgetPass=findViewById(R.id.forgetPassTextView);
        dialog = new SpotsDialog.Builder().setContext(this)
                .setCancelable(false)
                .setTheme(R.style.CustomDialog)
                .build();

        textViewForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,ForgetPassActivity.class));

            }
        });
        textViewDonthaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));

            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=editTextEmail.getText().toString();
                pass=editTextPass.getText().toString();
                if (!validateEmail(email)) {
                    return;
                }
                if (!validateEditTextPass(editTextPass, "Password is required")) {
                    return;
                }
                dialog.show();
                signIn(email,pass);



            }
        });
    }
    private void signIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signin", "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(SignInActivity.this,MainActivity.class));

                        } else {
                            dialog.dismiss();
                            dialogError("Error",task.getException().getMessage().toString());
                            // If sign in fails, display a message to the user.
                            Log.w("signin", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private boolean validateEditText(EditText editText, String msg) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError(msg);
            editText.requestFocus();

            return false;
        } else {
        }

        return true;
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

    private boolean validateEmail(String email) {

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
    private void dialogError(String title,String msg){

        final PrettyDialog pDialog = new PrettyDialog(this);
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
}
