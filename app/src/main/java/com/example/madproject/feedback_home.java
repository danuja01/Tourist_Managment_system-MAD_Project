package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class feedback_home extends AppCompatActivity {

    Intent intent1;
    Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_home);
        intent2=getIntent();

    }

    public void createClick(View view) {
        intent1 = new Intent(feedback_home.this,create_feedback.class);
        startActivity(intent1);
    }

    public void viewAll(View view) {
        intent1 = new Intent(feedback_home.this,view_feedbacks.class);
        startActivity(intent1);
    }

    public void calculation(View view){
        intent1 = new Intent(feedback_home.this,weather_condition.class);
        startActivity(intent1);
    }


}