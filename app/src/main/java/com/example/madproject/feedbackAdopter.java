package com.example.madproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class feedbackAdopter extends FirebaseRecyclerAdapter<feedbackModel,feedbackAdopter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public feedbackAdopter(@NonNull FirebaseRecyclerOptions<feedbackModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull feedbackModel model) {
        holder.additional.setText(model.getAdditional());
        holder.destination.setText(model.getDestination());
        holder.enjoyable.setText(model.getEnjoyable());
        holder.rate.setText(model.getRate());
        Glide.with(holder.img.getContext())
                .load(model.getTurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_update_feedback))
                        .setExpanded(true,2000)
                        .create();


                View view = dialogPlus.getHolderView();
                EditText destination = view.findViewById(R.id.editTxtDestination);
                EditText additional = view.findViewById(R.id.editTxtAdditional);
                EditText rate = view.findViewById(R.id.editTxtExperience);
                EditText turl = view.findViewById(R.id.editTxtImageURL);
                EditText enjoyable = view.findViewById(R.id.editTxtEnjoyable);

                Button update = view.findViewById(R.id.btnUpdate);

                destination.setText(model.getDestination());
                additional.setText(model.getAdditional());
                rate.setText(model.getRate());
                enjoyable.setText(model.getEnjoyable());
                turl.setText(model.getTurl());

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("destination",destination.getText().toString());
                        map.put("enjoyable",enjoyable.getText().toString());
                        map.put("rate",rate.getText().toString());
                        map.put("additional",additional.getText().toString());
                        map.put("turl",turl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("feedbacks")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.destination.getContext(),"Feedback Updated Successfully",Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.destination.getContext(),"Error in Updating",Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.destination.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted date Can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("feedbacks")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.destination.getContext(),"Cancelled",Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView additional,destination,enjoyable,rate,turl;

        Button btnEdit,btnDelete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.img1);
            destination = (TextView)itemView.findViewById(R.id.destinationtext);
            enjoyable = (TextView)itemView.findViewById(R.id.enjoyabletext);
            rate = (TextView)itemView.findViewById(R.id.ratetext);
            additional = (TextView)itemView.findViewById(R.id.additionaltext);

            btnEdit=(Button)itemView.findViewById(R.id.btnEdit);
            btnDelete=(Button)itemView.findViewById(R.id.btnDelete);
        }
    }
}

