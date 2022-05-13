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
import com.example.groupdrive.api.ApiClient;
import com.example.groupdrive.api.ApiInterface;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.model.user.User;
import com.example.groupdrive.ui.fragments.MapsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Response;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Trip> tripList;
    private String username;


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
        String date = tripList.get(position).getDate();
        String creator = tripList.get(position).getCreator();
        holder.tripTitleTextView.setText(title);
        holder.tripMeetingPointTextView.setText(startPoint);
        holder.tripDateTextView.setText(date);
        holder.creatorTextView.setText(creator);
    }


//    public void getUserName(String userGID) {
//        final CountDownLatch latch = new CountDownLatch(1);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//                    Call<User> call;
//                    String url = "api/users/" + userGID;
//                    call = apiInterface.getUser(url);
//                    Response<User> response = null;
//                    try {
//                        response = call.execute();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (response.isSuccessful() && response.body() != null) {
//                        User user = response.body();
//                        username = user.getName();
//                    } else {
//                        System.out.println("Bad Response");
//                    }
//                    latch.countDown();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();
//
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
