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

import com.core2plus.auhda.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ForgetPassActivity extends AppCompatActivity {
    private EditText editTextResetPassEmail;
    private Button buttonReset;
    private FirebaseAuth mAuth;
    private String email;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        mAuth = FirebaseAuth.getInstance();
        editTextResetPassEmail=findViewById(R.id.input_email_resetPass);
        buttonReset=findViewById(R.id.resetPassButton);
        dialog = new SpotsDialog.Builder().setContext(this)
                .setCancelable(false)
                .setTheme(R.style.CustomDialog)
                .build();
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=editTextResetPassEmail.getText().toString();
                if (!validateEmail(editTextResetPassEmail,email)) {
                    return;
                }
                dialog.show();
                resetPass(email);

            }
        });

    }
    private void resetPass(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            dialogSuccess(new Intent(ForgetPassActivity.this,SignInActivity.class),"Check your email","We just sent you an email with a link to reset your password.");
                            Log.d("reset", "Email sent.");
                        }else{
                            dialog.dismiss();
                            dialogError("Error",task.getException().getMessage().toString());

                        }
                    }
                });
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
    private void dialogSuccess(final Intent intent, String title, String msg){

        final PrettyDialog pDialog = new PrettyDialog(this);
        pDialog.setCancelable(false);
        pDialog

                .setIcon(R.drawable.pdlg_icon_success)
                .setIconTint(R.color.pdlg_color_green)
                .setAnimationEnabled(false)
                .setTitle(title)
                .setMessage(msg)

                .addButton(
                        "OK",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                startActivity(intent);
                                pDialog.dismiss();
                            }
                        }
                )
                .show();
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
