package com.example.madproject.Medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.madproject.BmiCal;
import com.example.madproject.R;

public class HealthTracker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tracker);
    }

    public void clickMedId(View view){
        Intent medId = new Intent(this, EmptyMedId.class);
        startActivity(medId);
    }

    public void clickMedication(View v) {
        Intent medication = new Intent(this, Medication.class);
        startActivity(medication);
    }

    public void clickBmi(View v) {
        Intent BMI = new Intent(this, BmiCal.class);
        startActivity(BMI);
    }
}