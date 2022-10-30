package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class weather_condition extends AppCompatActivity {

    TextView ans,condition;
    EditText number;
    Intent i;
    RadioButton celciusButton;
    RadioButton farenhitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_condition);

        i=getIntent();
        number = findViewById(R.id.number);
        celciusButton = findViewById(R.id.Celcius);
        farenhitButton = findViewById(R.id.Farenhite);
    }

    public Double Calculate(View view){
        if (celciusButton.isChecked()) {
            if (number.getText().toString().isEmpty()) {
                Toast.makeText(weather_condition.this, "Please enter the temperature in Celcius", Toast.LENGTH_SHORT).show();
            } else {
                Double t = Double.parseDouble(String.valueOf(number.getText()));
                Double answer = (t - 32) * 5 / 9;
                ans = findViewById(R.id.viewTxtFarenhite);
                ans.setText(String.valueOf(answer) + " °F");

                condition = findViewById(R.id.condition);
                if (t < 0) {
                    condition.setText("Freezing Temprature");
                } else if (t < 10) {
                    condition.setText("Very Cold Temprature");
                } else if (t < 20) {
                    condition.setText("Cold Temprature");
                } else if (t < 30) {
                    condition.setText("Normal Temprature");
                } else {
                    condition.setText("Hot Temprature");
                }

                return answer;
            }
        }

        else if (farenhitButton.isChecked()){
            if (number.getText().toString().isEmpty()) {
                Toast.makeText(weather_condition.this, "Please enter the temperature in Farenhite", Toast.LENGTH_SHORT).show();
            } else {
                Double t = Double.parseDouble(String.valueOf(number.getText()));
                condition = findViewById(R.id.condition);

                Double answer = t * 9 / 5 + 32;
                ans = findViewById(R.id.viewTxtFarenhite);
                ans.setText(String.valueOf(answer) + " °C");

                if (answer < 0) {
                    condition.setText("Freezing Temprature");
                } else if (answer < 10) {
                    condition.setText("Very Cold Temprature");
                } else if (answer < 20) {
                    condition.setText("Cold Temprature");
                } else if (answer < 30) {
                    condition.setText("Normal Temprature");
                } else {
                    condition.setText("Hot Temprature");
                }

                return answer;

            }

        }

        else{
            Toast.makeText(weather_condition.this, "Please Select an Option", Toast.LENGTH_SHORT).show();
        }

        return 9.0;

    }



    }
