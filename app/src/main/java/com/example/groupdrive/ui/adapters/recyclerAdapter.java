package com.example.groupdrive.ui.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupdrive.R;
import com.example.groupdrive.api.ApiClient;
import com.example.groupdrive.api.ApiInterface;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.ui.fragments.MapsActivity;
import com.example.groupdrive.ui.fragments.ViewTripDetails;


import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    public static final String TAG = "DEBUG"; //tag for logcat
    private ArrayList<Trip> tripList;
    private String username;
    private String creator;


    public recyclerAdapter(ArrayList<Trip> tripsList, String username, String creator) {
        this.tripList = tripsList;
        this.username = username;
        this.creator = creator;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tripTitleTextView, tripDateTextView, tripMeetingPointTextView, creatorTextView, participantsCount;
        private Button joinTripBtn, detailsTripBtn, liveTripBtn,delBtn;
        public MyViewHolder(final View view) {
            super(view);
            participantsCount = view.findViewById(R.id.trip_participants_count);
            creatorTextView = view.findViewById(R.id.textView10);
            tripTitleTextView = view.findViewById(R.id.TripTitleTextView);
            tripMeetingPointTextView = view.findViewById(R.id.TripMeetingPointTextView);
            tripDateTextView = view.findViewById(R.id.TripDateTextView);
            liveTripBtn = view.findViewById(R.id.liveTripBtn);
            joinTripBtn = view.findViewById(R.id.joinTripBtn);
            detailsTripBtn = view.findViewById(R.id.detailsTripBtn);
            delBtn = view.findViewById(R.id.trip_delete_btn);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new MyViewHolder(itemView);
    }

    private void gotoMaps(View view,String tripID) {
        Intent switchActivityIntent = new Intent(view.getContext(), MapsActivity.class);
        switchActivityIntent.putExtra("tripID", tripID);
        switchActivityIntent.putExtra("username", this.username);
        view.getContext().startActivity(switchActivityIntent);
    }
    private void gotoTripDetails(View view, Trip trip){
        Intent switchActivityIntent = new Intent(view.getContext(), ViewTripDetails.class);
        switchActivityIntent.putExtra("tripTitle", trip.getTitle());
        switchActivityIntent.putExtra("tripDesc", trip.getDescription());
        switchActivityIntent.putExtra("tripDate", trip.getDate());
        switchActivityIntent.putExtra("tripLocation", trip.getMeetingPoint());
        switchActivityIntent.putExtra("tripWazeLink", trip.getMeetingPointWazeUrl());
        switchActivityIntent.putExtra("tripCreator",trip.getCreator());
        switchActivityIntent.putExtra("username", this.username);
        view.getContext().startActivity(switchActivityIntent);
    }
    private void reload(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<Trip>> call;
        Response<ArrayList<Trip>> response;
        call = apiInterface.getTrips(creator);
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("call.execute failed");
            return;
        }
        if (response.isSuccessful() && response.body() != null) {
        } else {
            System.out.println("Bad Response");
        }
        this.tripList = response.body();
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String tripID = tripList.get(position).getId();
        holder.liveTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMaps(view,tripID);
            }
        });
        holder.joinTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                String requestURL = "/api/trips/"+tripID+"/join";
                Call<String> call;
                call = apiInterface.joinTrip(requestURL,username);
                Response<String> response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("call.execute failed");
                    return;
                }
                if (response.isSuccessful() ) {
                    System.out.println("Joined successfully!");
                    reload();
                } else {
                    System.out.println("Bad Response");
                }
            }
        });
        holder.detailsTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoTripDetails(v,tripList.get(position));
            }
        });
        Trip trip = tripList.get(position);
        String title = trip.getTitle();
        String startPoint = trip.getMeetingPoint();
        String date = trip.getDate();
        String creator = trip.getCreator();
        holder.tripTitleTextView.setText(title);
        if (trip.getTripFull()==true){
            holder.participantsCount.setText("TRIP IS FULL");

        }
        else {
            if (trip.getMaxGroupSize() == -1){
                holder.participantsCount.setText("unlimited");
            }
            else{
            holder.participantsCount.setText(trip.getParticipants().size() + " out of " + trip.getMaxGroupSize());
            }
        }
        holder.tripMeetingPointTextView.setText(startPoint);
        holder.tripDateTextView.setText(date);
        holder.creatorTextView.setText(creator);
        if (username.equals(creator)) {
            holder.delBtn.setVisibility(View.VISIBLE);
        } else{
            holder.delBtn.setVisibility(View.INVISIBLE);
        }
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "user "+username+"clicked on delete trip: "+title);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                                String requestURL = "/api/trips/"+tripID;
                                Call<String> call;
                                call = apiInterface.deleteTrip(requestURL,username);
                                Response<String> response = null;
                                try {
                                    response = call.execute();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "delete trip call.execute failed");
                                    System.out.println("call.execute failed");
                                    return;
                                }
                                if (response.isSuccessful() ) {
                                    Log.d(TAG,"Deleted trip successfully!");
                                    System.out.println("Deleted trip successfully!");
                                    reload();
                                } else {
                                    Log.d(TAG,"Bad Response from delete trip call");
                                    System.out.println("Bad Response");
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
        boolean isUserJoined = tripList.get(position).isUserJoined(username);
        if (isUserJoined){
            holder.joinTripBtn.setText("Leave");
            if (trip.getCreator().equals(username)){
                holder.joinTripBtn.setAlpha(.5f);
                holder.joinTripBtn.setClickable(false);
            }
        }
        else{
            holder.joinTripBtn.setText("Join");
            if (trip.getTripFull() == true) {
                holder.joinTripBtn.setAlpha(.5f);
                holder.joinTripBtn.setClickable(false);
            }
            else{
                holder.joinTripBtn.setAlpha(1f);
                holder.joinTripBtn.setClickable(true);
            }
        }
        if (isUserJoined && tripList.get(position).isTripToday()){
            holder.liveTripBtn.setAlpha(1f);
            holder.liveTripBtn.setClickable(true);
        }else{
            holder.liveTripBtn.setAlpha(.5f);
            holder.liveTripBtn.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
