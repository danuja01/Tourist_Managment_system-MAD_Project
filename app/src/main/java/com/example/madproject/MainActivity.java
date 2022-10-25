package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.madproject.Medical.HealthTracker;
import com.example.madproject.Medical.MedId;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void click(View v){
        Intent intent = new Intent(this, HealthTracker.class);
        startActivity(intent);
    }

    public void clickBtn(View v){
        Intent intent = new Intent(this, MedId.class);
        startActivity(intent);
    }
}