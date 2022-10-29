package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class Registration extends AppCompatActivity {

    private EditText email,pass;
    private Button regBtn;
    private TextView regQ;
    private FirebaseAuth fAuth;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        fAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        email = findViewById(R.id.regEmail);
        pass = findViewById(R.id.regPassword);
        regBtn = findViewById(R.id.regBtn);
        regQ = findViewById(R.id.logQ);

        regQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regEmail = email.getText().toString().trim();
                String regPass = pass.getText().toString().trim();

                if(TextUtils.isEmpty(regEmail)){
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(regPass)){
                    pass.setError("Password is required");
                    return;
                }else{
                    loader.setMessage("Registration in progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    fAuth.createUserWithEmailAndPassword(regEmail,regPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registration.this, Login.class);
                                startActivity(intent);
                                finish();
                            }else{
                                String error = ((FirebaseAuthException) task.getException()).getErrorCode();
                                switch (error) {
                                    case "ERROR_INVALID_EMAIL":
                                        Toast.makeText(Registration.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        Toast.makeText(Registration.this, "Email already in use", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "ERROR_WEAK_PASSWORD":
                                        Toast.makeText(Registration.this, "Password is too weak", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        String err = task.getException().getMessage();
                                        Toast.makeText(Registration.this, "Registration Failed: " + err, Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                            loader.dismiss();
                        }
                    });
                }




            }
        });
    }
}