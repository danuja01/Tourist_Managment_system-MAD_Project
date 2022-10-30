package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budgethome);
    }

    public void budgetcalclick(View v){
        Intent intent = new Intent(this, addtransaction.class);
        startActivity(intent);
    }



    public void personalnoteclick(View v){
        Intent intent = new Intent(this, PersonalNote.class);
        startActivity(intent);
    }





}