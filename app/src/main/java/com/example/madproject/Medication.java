package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.madproject.Medical.CreateMedId;

import java.util.ArrayList;

public class Medication extends AppCompatActivity {

    private ArrayList<MediItem> medList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        recyclerView = findViewById(R.id.medTools);
        medList = new ArrayList<>();

        setMedInfo();
        seAdapter();
    }

    private void seAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(medList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setMedInfo() {
        medList.add(new MediItem("Paracitamol"));
        medList.add(new MediItem("Paracitamol"));
        medList.add(new MediItem("Paracitamol"));
        medList.add(new MediItem("Paracitamol"));
        medList.add(new MediItem("Paracitamol"));
        medList.add(new MediItem("Paracitamol"));
    }

    public void click(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}