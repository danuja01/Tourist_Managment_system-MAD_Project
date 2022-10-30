package com.example.travellogtodolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.travellogtodolist.Models.TravelDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TravelManage extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    ListView listView;
    private List<TravelDetails> user;
    DatabaseReference ref;
    Calendar myCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manage);

        floatingActionButton=(FloatingActionButton) findViewById(R.id.addMeeting);
        listView = (ListView)findViewById(R.id.listview);

        user = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TravelManage.this, AddTravelDetails.class);
                startActivity(intent);
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Events");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot taskDatasnap : dataSnapshot.getChildren()){

                    TravelDetails travelDetails = taskDatasnap.getValue(TravelDetails.class);
                    user.add(travelDetails);
                }

                MyAdapter adapter = new MyAdapter(TravelManage.this, R.layout.custom_event_list, (ArrayList<TravelDetails>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;
        Button button1;
        Button button2;

    }

    class MyAdapter extends ArrayAdapter<TravelDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<Map<String, String>> newList;
        List<TravelDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<TravelDetails> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_event_list, null);

                holder.COL1 = (TextView) view.findViewById(R.id.mid);
                holder.COL2 = (TextView) view.findViewById(R.id.mday);
                holder.COL3 = (TextView) view.findViewById(R.id.mstime);
                holder.COL4 = (TextView) view.findViewById(R.id.metime);
                holder.COL5 = (TextView) view.findViewById(R.id.meparty);
                holder.button1=(Button)view.findViewById(R.id.delete);
                holder.button2=(Button)view.findViewById(R.id.edit);

                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Title:- "+user.get(position).getTitle());
            holder.COL2.setText("Date:- "+user.get(position).getDate());
            holder.COL3.setText("Start Time:- "+user.get(position).getStartTime());
            holder.COL4.setText("End Time:- "+user.get(position).getEndTime());
            holder.COL5.setText("Description:- "+user.get(position).getDescription());

            System.out.println(holder);

            final String idd = user.get(position).getId();

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this event?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    FirebaseDatabase.getInstance().getReference("Events").child(idd).removeValue();
                                    Toast.makeText(myContext, "Deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_event,null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText)view1.findViewById(R.id.ttitlem);
                    final EditText editText2 = (EditText)view1.findViewById(R.id.datem);
                    final EditText editText3 = (EditText)view1.findViewById(R.id.startTimem);
                    final EditText editText4 = (EditText)view1.findViewById(R.id.endTimem);
                    final EditText editText5 = (EditText)view1.findViewById(R.id.participentm);
                    final Button button = (Button)view1.findViewById(R.id.update);


                    myCalendar = Calendar.getInstance();

                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            updateLabel1();
                        }

                        private void updateLabel1() {
                            String myFormat = "MM/dd/yyyy"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                            editText2.setText(sdf.format(myCalendar.getTime()));
                        }


                    };

                    editText2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(TravelManage.this, date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });

                    editText3.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Calendar mcurrentTime = Calendar.getInstance();
                            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                            int minute = mcurrentTime.get(Calendar.MINUTE);
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(TravelManage.this, new TimePickerDialog.OnTimeSetListener() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    editText3.setText( selectedHour + ":" + selectedMinute);
                                }
                            }, hour, minute, true);//Yes 24 hour time
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();

                        }
                    });

                    editText4.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Calendar mcurrentTime = Calendar.getInstance();
                            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                            int minute = mcurrentTime.get(Calendar.MINUTE);
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(TravelManage.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    editText4.setText( selectedHour + ":" + selectedMinute);
                                }
                            }, hour, minute, true);//Yes 24 hour time
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();

                        }
                    });

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String title = (String) snapshot.child("title").getValue();
                            String date = (String) snapshot.child("date").getValue();
                            String start = (String) snapshot.child("startTime").getValue();
                            String end = (String) snapshot.child("endTime").getValue();
                            String description = (String) snapshot.child("description").getValue();

                            editText1.setText(title);
                            editText2.setText(date);
                            editText3.setText(start);
                            editText4.setText(end);
                            editText5.setText(description);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String title = editText1.getText().toString();
                            String date = editText2.getText().toString();
                            String start =editText3.getText().toString();
                            String end = editText4.getText().toString();
                            String description = editText5.getText().toString();


                            if (title.isEmpty()){
                                editText1.setError("Title is required");
                            }else if (date.isEmpty()){
                                editText2.setError("Date is required");
                            }else if (start.isEmpty()){
                                editText3.setError("Start time is required");
                            }else if (end.isEmpty()){
                                editText4.setError("End time is required");
                            }else if (description.isEmpty()){
                                editText5.setError("Description is required");
                            }else{
//
                                HashMap map = new HashMap();
                                map.put("title",title);
                                map.put("date",date);
                                map.put("startTime",start);
                                map.put("endTime",end);
                                map.put("description",description);
                                reference.updateChildren(map);

                                Toast.makeText(TravelManage.this, "Event updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }

                        }
                    });

                }
            });



            return view;
        }

    }

}