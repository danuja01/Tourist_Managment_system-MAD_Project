package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class view_feedbacks extends AppCompatActivity {

    Intent intent3;
    RecyclerView recyclerView;
    feedbackAdopter feedbackAdopter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedbacks);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<feedbackModel> options =
                new FirebaseRecyclerOptions.Builder<feedbackModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("feedbacks"), feedbackModel.class)
                        .build();
        feedbackAdopter = new feedbackAdopter(options);
        recyclerView.setAdapter(feedbackAdopter);

        intent3=getIntent();


    }


    @Override
    protected void onStart() {
        super.onStart();
        feedbackAdopter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        feedbackAdopter.stopListening();
    }
}