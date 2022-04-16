package com.example.groupdrive.ui.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupdrive.R;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.ui.fragments.MapsActivity;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Trip> tripList;

    public recyclerAdapter(ArrayList<Trip> tripsList) {
        this.tripList = tripsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tripTitleTextView, tripDateTextView, tripMeetingPointTextView, creatorTextView;
        private Button joinLiveTripBtn;

        public MyViewHolder(final View view) {
            super(view);
            creatorTextView = view.findViewById(R.id.textView10);
            tripTitleTextView = view.findViewById(R.id.TripTitleTextView);
            tripMeetingPointTextView = view.findViewById(R.id.TripMeetingPointTextView);
            tripDateTextView = view.findViewById(R.id.TripDateTextView);
            joinLiveTripBtn = view.findViewById(R.id.button3);
            joinLiveTripBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoMaps(view);
                } //TODO: OnJoinTrip
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
        String title = tripList.get(position).getTitle();
        String startPoint = tripList.get(position).getMeetingPoint();
        String date = tripList.get(position).getDateTime();
        String creator = tripList.get(position).getCreatorGID();
        holder.tripTitleTextView.setText(title);
        holder.tripMeetingPointTextView.setText(startPoint);
        holder.tripDateTextView.setText(date);
        holder.creatorTextView.setText(creator);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
