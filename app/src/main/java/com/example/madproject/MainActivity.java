package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.madproject.Medical.EmptyMedId;
import com.example.madproject.Medical.HealthTracker;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
    }


    public void click(View v){
        Intent intent = new Intent(this, HealthTracker.class);
        startActivity(intent);
    }
}