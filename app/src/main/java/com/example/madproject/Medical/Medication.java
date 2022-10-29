package com.example.madproject.Medical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Medication extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId;

    private ProgressDialog loader;

    private String key = "";
    private String medicationName;
    private String time;
    private String period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_medication);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.medTools);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        loader = new ProgressDialog(this);

        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("medication").child(onlineUserId);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });


    }

    public void addTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View myView = inflater.inflate(R.layout.input_medication, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText medication = myView.findViewById(R.id.medcineName);
        final EditText time = myView.findViewById(R.id.medicationTime);
        final RadioButton am = myView.findViewById(R.id.am);
        final RadioButton pm = myView.findViewById(R.id.pm);
        Button save = myView.findViewById(R.id.mediSubmit);
        Button cancel = myView.findViewById(R.id.mediCancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mMedication = medication.getText().toString().trim();
                String mTime = time.getText().toString().trim();
                String id = reference.push().getKey();
                String period = "";

                if (am.isChecked()) {
                    period = "Morning";
                    mTime = mTime + " am";
                } else if (pm.isChecked()) {
                    period = "Night";
                    mTime = mTime + " pm";
                }

                if (mMedication.isEmpty()) {
                    medication.setError("Required Field..");
                    return;
                }

                if (mTime.isEmpty()) {
                    time.setError("Required Field..");
                    return;
                } else {
                    loader.setMessage("Adding your data");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    MedicationModel model = new MedicationModel(mMedication, period, mTime, id);
                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Medication.this, "Medicine Added", Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(Medication.this, "Failed" + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setMedication(String medication) {
            TextView medicationName = mView.findViewById(R.id.medItm);
            medicationName.setText(medication);
        }

        public void setTime(String time) {
            TextView medicationTime = mView.findViewById(R.id.time);
            medicationTime.setText(time);
        }

        public void setPeriod(String period) {
            TextView medicationPeriod = mView.findViewById(R.id.period);
            medicationPeriod.setText(period);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<MedicationModel> options = new FirebaseRecyclerOptions.Builder<MedicationModel>()
                .setQuery(reference, MedicationModel.class)
                .build();

        FirebaseRecyclerAdapter<MedicationModel, MyViewHolder> adapter = new FirebaseRecyclerAdapter<MedicationModel, MyViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final MedicationModel model) {
                holder.setMedication(model.getName());
                holder.setTime(model.getTime());
                holder.setPeriod(model.getPeriod());



                holder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        key = getRef(position).getKey();
                        medicationName = model.getName();
                        time = model.getTime();
                        period = model.getPeriod();

                        updateTask();
                    }
                });


            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void updateTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_medication, null);
        myDialog.setView(view);

        final AlertDialog dialog = myDialog.create();

        final EditText uName = view.findViewById(R.id.updateMedicineName);
        final EditText uTime = view.findViewById(R.id.updateMedicationTime);
        final RadioButton uAm = view.findViewById(R.id.uAm);
        final RadioButton uPm = view.findViewById(R.id.uPm);


        String subTime = time.substring(0, 5);

        uName.setText(medicationName);
        uName.setSelection(medicationName.length());

        uTime.setText(subTime);
        uTime.setSelection(subTime.length());

        if (period.equals("Morning")) {
            uAm.setChecked(true);
        } else if (period.equals("Night")) {
            uPm.setChecked(true);
        }

        Button delButton = view.findViewById(R.id.updateMediCancel);
        Button updateButton = view.findViewById(R.id.updateMediSubmit);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicationName = uName.getText().toString().trim();
                time = uTime.getText().toString().trim();

                if (uAm.isChecked()) {
                    period = "Morning";
                    time = time + " am";
                } else if (uPm.isChecked()) {
                    period = "Night";
                    time = time + " pm";
                }


                MedicationModel model = new MedicationModel(medicationName, period, time, key);

                reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(Medication.this, "Data has been updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            String err = task.getException().toString();
                            Toast.makeText(Medication.this, "update failed " + err, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.dismiss();

            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Medication.this, "Medicine deleted successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            String err = task.getException().toString();
                            Toast.makeText(Medication.this, "Failed to delete Medicine "+ err, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.dismiss();
            }

        });

        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    public void clickAdd(View v) {
        Intent intent = new Intent(this, MedicationForm.class);
        startActivity(intent);
    }


}