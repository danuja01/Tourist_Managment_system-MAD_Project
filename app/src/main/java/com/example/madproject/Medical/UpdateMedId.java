package com.example.madproject.Medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madproject.Medical.MedId;
import com.example.madproject.Medical.MedIdModel;
import com.example.madproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateMedId extends AppCompatActivity {


    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private ProgressDialog loader;

    EditText eName, eAge, eHeight, eWeight, eBloodType, eInfo;

    private String id = "";
    private String name;
    private String age;
    private String height;
    private String weight;
    private String bGroup;
    private String info;

    private Button updateBtn;

    MedIdModel myMedId = MedId.myMedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_med_id);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        id = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference("medId");

        eName = findViewById(R.id.updateAddName);
        eAge = findViewById(R.id.updateAddAge);
        eHeight = findViewById(R.id.updateAddHeight);
        eWeight = findViewById(R.id.updateAddWeight);
        eBloodType = findViewById(R.id.updateAddBloodType);
        eInfo = findViewById(R.id.updateAddInfo);

        updateBtn = findViewById(R.id.updateMedButton);

    }

    @Override
    protected void onStart() {
        super.onStart();

        name = myMedId.getName();
        age = myMedId.getAge();
        height = myMedId.getHeight();
        weight = myMedId.getWeight();
        bGroup = myMedId.getbGroup();
        info = myMedId.getInfo();

        eName.setText(name);
        eName.setSelection(name.length());

        eAge.setText(age);
        eAge.setSelection(age.length());

        eHeight.setText(height);
        eHeight.setSelection(height.length());

        eWeight.setText(weight);
        eWeight.setSelection(weight.length());

        eBloodType.setText(bGroup);
        eBloodType.setSelection(bGroup.length());

        eInfo.setText(info);
        eInfo.setSelection(info.length());

        update();
    }

    public void update() {

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader = new ProgressDialog(UpdateMedId.this);
                loader.setMessage("Updating...");
                loader.setCanceledOnTouchOutside(false);
                loader.show();

                name = eName.getText().toString().trim();
                age = eAge.getText().toString().trim();
                height = eHeight.getText().toString().trim();
                weight = eWeight.getText().toString().trim();
                bGroup = eBloodType.getText().toString().trim();
                info = eInfo.getText().toString().trim();

                MedIdModel model = new MedIdModel(name, age, height, weight, bGroup, info);

                reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            loader.dismiss();
                            Toast.makeText(UpdateMedId.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            loader.dismiss();
                            Toast.makeText(UpdateMedId.this, "Failed to update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }
}