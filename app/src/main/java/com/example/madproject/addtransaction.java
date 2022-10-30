package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class addtransaction extends AppCompatActivity {

    EditText Income,Expense,Discount;
    TextView Final;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtransaction);

        getIntent();
        Income = findViewById(R.id.income);
        Expense = findViewById(R.id.expense);
        Discount = findViewById(R.id.discount);
    }

    public void Calculate(View view){
        Double i = Double.parseDouble(String.valueOf(Income.getText()));
        Double e = Double.parseDouble(String.valueOf(Expense.getText()));
        Double d = Double.parseDouble(String.valueOf(Discount.getText()));

        Double balance = i - (e*(d/100));
        Final = findViewById(R.id.finalAmount);
        Final.setText(String.valueOf(balance) + "/=");
    }




}