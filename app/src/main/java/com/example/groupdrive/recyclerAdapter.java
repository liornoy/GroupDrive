package com.example.groupdrive;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupdrive.model.trip.Trip;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Trip> tripList;

    public recyclerAdapter(ArrayList<Trip> tripsList) {
        this.tripList = tripsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tripTitleText;
        private TextView tripDurationText;
        private Button joinLiveTripBtn;

        public MyViewHolder(final View view) {
            super(view);
            tripTitleText = view.findViewById(R.id.TripNameTextView2);
            tripDurationText = view.findViewById(R.id.tripduration2);
            joinLiveTripBtn = view.findViewById(R.id.button3);
            joinLiveTripBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoMaps(view);
                }
            });
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new MyViewHolder(itemView);
    }

    private void gotoMaps(View view) {
        Intent switchActivityIntent = new Intent(view.getContext(), MapsActivity.class);
        view.getContext().startActivity(switchActivityIntent);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String name = tripList.get(position).getTitle();
        String duration = tripList.get(position).getDuration();
        holder.tripTitleText.setText(name);
        holder.tripDurationText.setText(duration);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
