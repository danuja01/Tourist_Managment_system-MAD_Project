package com.example.madproject.Medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.madproject.R;

public class EmptyMedId extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_med_id);
    }

    public void click(View view) {
        Intent intent = new Intent(this, CreateMedId.class);
        startActivity(intent);
    }
}