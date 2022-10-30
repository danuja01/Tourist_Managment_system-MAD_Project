package com.example.madproject.Medical;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.madproject.R;

public class MedicalId extends Fragment {

    private TextView medName, age, height, weight, bGroup, info;
    MedIdModel myMedId = MedId.myMedId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical_id, container, false);

        medName = view.findViewById(R.id.medName);
        age = view.findViewById(R.id.age);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        bGroup = view.findViewById(R.id.bGroup);
        info = view.findViewById(R.id.medInfo);

        if(myMedId != null){
            medName.setText(myMedId.getName());
            age.setText(myMedId.getAge());
            height.setText(myMedId.getHeight());
            weight.setText(myMedId.getWeight());
            bGroup.setText(myMedId.getbGroup());
            info.setText(myMedId.getInfo());
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = (Button) view.findViewById(R.id.updateMed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medId = new Intent(getActivity(), UpdateMedId.class);
                startActivity(medId);
            }
        });
    }
}