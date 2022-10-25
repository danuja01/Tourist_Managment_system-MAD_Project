package com.example.madproject.Medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedId extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    static MedIdModel myMedId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_id);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("medId");




        getMedId();
    }

    private void getMedId() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Fragment fragment;

                if (snapshot.exists()) {

                    String name = snapshot.child("name").getValue().toString();
                    int mAge = Integer.parseInt(snapshot.child("age").getValue().toString());
                    double mHeight = Double.parseDouble(snapshot.child("height").getValue().toString());
                    double mWeight = Double.parseDouble(snapshot.child("weight").getValue().toString());
                    String mBGroup = snapshot.child("bGroup").getValue().toString();
                    String mInfo = snapshot.child("info").getValue().toString();

                    myMedId = new MedIdModel(name, mAge, mHeight, mWeight, mBGroup, mInfo);
                    Toast.makeText(MedId.this, "Medical ID exists and updated", Toast.LENGTH_SHORT).show();

                    fragment = new MedicalId();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.medicalFragment, fragment);
                    ft.commit();

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


