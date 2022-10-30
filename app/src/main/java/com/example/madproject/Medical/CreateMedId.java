package com.example.madproject.Medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateMedId extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String id;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_med_id);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);
        mUser = mAuth.getCurrentUser();
        id = mUser.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("medId");


        createMedId();
    }

    public void createMedId(){
        final EditText name = findViewById(R.id.addName);
        final EditText age = findViewById(R.id.addAge);
        final EditText height = findViewById(R.id.addHeight);
        final EditText weight = findViewById(R.id.addWeight);
        final EditText bloodType = findViewById(R.id.addBloodType);
        final EditText info = findViewById(R.id.addInfo);
        Button create = findViewById(R.id.creatButton);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString().trim();
                String ageStr = age.getText().toString().trim();
                String heightStr = height.getText().toString().trim();
                String weightStr = weight.getText().toString().trim();
                String bloodTypeStr = bloodType.getText().toString().trim();
                String infoStr = info.getText().toString().trim();



                if(nameStr.isEmpty()){
                    name.setError("Name is required");
                    name.requestFocus();
                    return;
                }

                if(ageStr.isEmpty()){
                    age.setError("Age is required");
                    age.requestFocus();
                    return;
                }

                if(heightStr.isEmpty()){
                    height.setError("Height is required");
                    height.requestFocus();
                    return;
                }

                if(weightStr.isEmpty()){
                    weight.setError("Weight is required");
                    weight.requestFocus();
                    return;
                }

                if(bloodTypeStr.isEmpty()){
                    bloodType.setError("Blood type is required");
                    bloodType.requestFocus();
                    return;
                }

                if(infoStr.isEmpty()){
                    info.setError("Info is required");
                    info.requestFocus();
                    return;
                }else{
                    loader.setMessage("Creating your medical ID");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    MedIdModel model = new MedIdModel(nameStr, ageStr, heightStr, weightStr, bloodTypeStr, infoStr);
                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                loader.dismiss();
                                Intent intent = new Intent(CreateMedId.this, HealthTracker.class);
                                startActivity(intent);
                            }else{
                                Toast toast = Toast.makeText(CreateMedId.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT);
                                loader.dismiss();
                            }
                        }
                    });
                }




            }
        });


    }


}