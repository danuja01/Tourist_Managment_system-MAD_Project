package com.example.madproject.Medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.madproject.R;

public class HealthTracker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tracker);
    }

    public void click(View view){
        Intent intent = new Intent(this, EmptyMedId.class);
        startActivity(intent);
    }
}