package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalNote extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button addBtn;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId;
    private ProgressDialog loader;

    private String key = "";
    private String nTitle;
    private String nDesc;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.personal_note);

        recyclerView = findViewById(R.id.notes);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        loader = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("notes");

        addBtn = findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotes();
            }
        });
    }

    private void addNotes() {
        androidx.appcompat.app.AlertDialog.Builder myDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View myView = inflater.inflate(R.layout.insert_note, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText nTitle = myView.findViewById(R.id.nTitle);
        final EditText nDesc = myView.findViewById(R.id.nDesc);
        Button save = myView.findViewById(R.id.nAdd);
        Button cancel = myView.findViewById(R.id.nCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTitle = nTitle.getText().toString().trim();
                String mDesc = nDesc.getText().toString().trim();
                String id = reference.push().getKey();


                if(mTitle.isEmpty()){
                    nTitle.setError("Required Field");
                    return;
                }

                if( mDesc.isEmpty()){
                    nDesc.setError("Required Field");
                    return;
                } else {
                    loader.setMessage("Adding your Review");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    NoteModal model = new NoteModal(mTitle, mDesc);

                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(PersonalNote.this, "Note Added", Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(PersonalNote.this, "Failed" + error, Toast.LENGTH_SHORT).show();
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

        public void setTitle(String title) {
            TextView nTitle = mView.findViewById(R.id.vTitle);
            nTitle.setText(title);
        }

        public void setDesc(String desc) {
            TextView nDesc = mView.findViewById(R.id.vDesc);
            nDesc.setText(desc);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<NoteModal> options = new FirebaseRecyclerOptions.Builder<NoteModal>()
                .setQuery(reference, NoteModal.class)
                .build();

        FirebaseRecyclerAdapter<NoteModal, MyViewHolder> adapter = new FirebaseRecyclerAdapter<NoteModal, MyViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final NoteModal model) {
                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDescription());

                holder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        key = getRef(position).getKey();
                        nTitle = model.getTitle();
                        nDesc = model.getDescription();

                        updateNote();
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);
                return new MyViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void updateNote() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_note, null);
        myDialog.setView(view);

        final AlertDialog dialog = myDialog.create();

        final EditText upName = view.findViewById(R.id.uTitle);
        final EditText upTitle = view.findViewById(R.id.uDesc);


        upName.setText(nTitle);
        upName.setSelection(nTitle.length());

        upTitle.setText(nDesc);
        upTitle.setSelection(nDesc.length());

        Button delButton = view.findViewById(R.id.uCancel);
        Button updateButton = view.findViewById(R.id.uAdd);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nTitle = upName.getText().toString().trim();
                nDesc = upTitle.getText().toString().trim();

                NoteModal model = new NoteModal(nTitle, nDesc);

                reference.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(PersonalNote.this, "Data has been updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            String err = task.getException().toString();
                            Toast.makeText(PersonalNote.this, "update failed " + err, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PersonalNote.this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            String err = task.getException().toString();
                            Toast.makeText(PersonalNote.this, "Failed to delete Note "+ err, Toast.LENGTH_SHORT).show();
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





}


