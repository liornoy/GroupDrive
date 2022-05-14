package com.example.groupdrive.ui.adapters;

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


import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Trip> tripList;
    private String username;


    public recyclerAdapter(ArrayList<Trip> tripsList, String username) {
        this.tripList = tripsList;
        this.username = username;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tripTitleTextView, tripDateTextView, tripMeetingPointTextView, creatorTextView;
        private Button joinTripBtn, detailsTripBtn, liveTripBtn;
        public MyViewHolder(final View view) {
            super(view);
            creatorTextView = view.findViewById(R.id.textView10);
            tripTitleTextView = view.findViewById(R.id.TripTitleTextView);
            tripMeetingPointTextView = view.findViewById(R.id.TripMeetingPointTextView);
            tripDateTextView = view.findViewById(R.id.TripDateTextView);
            liveTripBtn = view.findViewById(R.id.liveTripBtn);
            joinTripBtn = view.findViewById(R.id.joinTripBtn);
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
    private void reload(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<Trip>> call;
        Response<ArrayList<Trip>> response;
        call = apiInterface.getTrips();
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
            } //TODO: OnJoinTrip
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
        String title = tripList.get(position).getTitle();
        String startPoint = tripList.get(position).getMeetingPoint();
        String date = tripList.get(position).getDate();
        String creator = tripList.get(position).getCreator();
        holder.tripTitleTextView.setText(title);
        holder.tripMeetingPointTextView.setText(startPoint);
        holder.tripDateTextView.setText(date);
        holder.creatorTextView.setText(creator);
        boolean isUserJoined = tripList.get(position).isUserJoined(username);
        if (isUserJoined){
            holder.joinTripBtn.setText("Leave");
        }
        else{
            holder.joinTripBtn.setText("Join");
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
