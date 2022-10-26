package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class personalNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_note);
    }


    public void EditClick(View v){
        Intent intent = new Intent(this, updateNote.class);
        startActivity(intent);
    }

    public void addnewClick(View v){
        Intent intent = new Intent(this, AddNote.class);
        startActivity(intent);
    }



}