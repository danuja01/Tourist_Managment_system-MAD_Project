package com.example.madproject.Medical;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        medName.setText(myMedId.getName());
        age.setText(Integer.toString(myMedId.getAge()));
        height.setText(Double.toString(myMedId.getHeight()) + " cm");
        weight.setText(Double.toString(myMedId.getWeight()) + " kg");
        bGroup.setText(myMedId.getbGroup());
        info.setText(myMedId.getInfo());

        return view;
    }
}