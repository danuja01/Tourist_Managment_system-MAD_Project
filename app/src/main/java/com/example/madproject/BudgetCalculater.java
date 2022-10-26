package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BudgetCalculater extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budgetcal);
    }

    public void addtransclick(View v){
        Intent intent = new Intent(this, addtransaction.class);
        startActivity(intent);
    }
}