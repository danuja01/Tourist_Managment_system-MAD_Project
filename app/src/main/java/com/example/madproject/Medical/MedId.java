package com.example.madproject.Medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedId extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    static MedIdModel myMedId;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_med_id);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();
        databaseReference = firebaseDatabase.getReference("medId").child(onlineUserId);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getMedId();
    }

    private void getMedId() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Fragment fragment;

                if (snapshot.exists()) {

                    String name = snapshot.child("name").getValue().toString();
                    String mAge = snapshot.child("age").getValue().toString();
                    String mHeight = snapshot.child("height").getValue().toString();
                    String mWeight = snapshot.child("weight").getValue().toString();
                    String mBGroup = snapshot.child("bGroup").getValue().toString();
                    String mInfo = snapshot.child("info").getValue().toString();

                    myMedId = new MedIdModel(name, mAge, mHeight, mWeight, mBGroup, mInfo);
                    Toast.makeText(MedId.this, "Medical ID exists and updated", Toast.LENGTH_SHORT).show();

                    try {
                        fragment = new MedicalId();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.medicalFragment, fragment);
                        ft.commit();
                    } catch (IllegalStateException ignored) {
                        // There's no way to avoid getting this if saveInstanceState has already been called.
                    }

                }else{
                    fragment = new EmptyMedicalId();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.medicalFragment, fragment);
                    ft.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MedId.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }


        });
    }


}


