package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class create_feedback extends AppCompatActivity {

    EditText destination,enjoyable,additional,turl;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btnAdd;
    Intent intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feedback);
        destination = (EditText)findViewById(R.id.txtDestination);
        enjoyable = (EditText)findViewById(R.id.txtEnjoyable);
        additional = (EditText)findViewById(R.id.txtAdditional);
        turl = (EditText)findViewById(R.id.txtURL);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        int checkID = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton)findViewById(checkID);
        btnAdd = (Button)findViewById(R.id.btnSave);

        intent2= getIntent();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });


    }

    private void insertData(){

        if (destination.length()==0){
            Toast.makeText(create_feedback.this, "Please fill the Destination", Toast.LENGTH_SHORT).show();
        }
        else if (enjoyable.length()==0){
            Toast.makeText(create_feedback.this, "Please fill the Enjoyable Part", Toast.LENGTH_SHORT).show();
        }
        else if (additional.length()==0){
            Toast.makeText(create_feedback.this, "Please fill the Additional Comment", Toast.LENGTH_SHORT).show();
        }
        else {
            Map<String, Object> map = new HashMap<>();
            map.put("destination", destination.getText().toString());
            map.put("enjoyable", enjoyable.getText().toString());
            map.put("additional", additional.getText().toString());
            map.put("turl", turl.getText().toString());
            map.put("rate", radioButton.getText().toString());

            FirebaseDatabase.getInstance().getReference().child("feedbacks").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(create_feedback.this, "Feedback Inserted Successfully", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }


}