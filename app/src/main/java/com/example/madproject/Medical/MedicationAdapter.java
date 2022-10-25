package com.example.madproject.Medical;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MedicationAdapter extends FirebaseRecyclerAdapter<MedicationModel, MedicationAdapter.myViewHolder > {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MedicationAdapter(@NonNull FirebaseRecyclerOptions<MedicationModel> options) {
        super(options);
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new myViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MedicationModel model) {
        holder.medItm.setText(model.getName());
        holder.time.setText(model.getTime());
        holder.period.setText(model.getPeriod());
        System.out.println();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView medItm,period,time;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            medItm = itemView.findViewById(R.id.medItm);
            period = itemView.findViewById(R.id.period);
            time = itemView.findViewById(R.id.time);
        }
    }
}
