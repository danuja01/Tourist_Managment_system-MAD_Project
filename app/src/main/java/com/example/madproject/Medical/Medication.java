package com.example.madproject.Medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.madproject.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class Medication extends AppCompatActivity {

     RecyclerView recyclerView;
    MedicationAdapter medicationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        recyclerView = findViewById(R.id.medTools);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MedicationModel> options =
                new FirebaseRecyclerOptions.Builder<MedicationModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("medication"), MedicationModel.class)
                        .build();

        medicationAdapter = new MedicationAdapter(options);
        recyclerView.setAdapter(medicationAdapter);


    }


    @Override
    protected void onStart() {
        super.onStart();
        medicationAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        medicationAdapter.stopListening();
    }


    public void clickAdd(View v) {
        Intent intent = new Intent(this, MedicationForm.class);
        startActivity(intent);
    }


}