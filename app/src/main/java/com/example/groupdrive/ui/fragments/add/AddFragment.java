package com.example.groupdrive.ui.fragments.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.groupdrive.R;
import com.example.groupdrive.model.trip.Trip;
import com.example.groupdrive.viewmodel.TripViewModel;

import java.sql.Date;


public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "add_trip_title_et";
    private static final String DURATION = "add_trip_duration_et";
    private static final String DATE = "add_trip_date_et";

    private Trip mItem;
    private EditText mTitleEt;
    private EditText mDurationEt;
    private EditText mDateEt;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Try to get all new meal arguments passed from previous fragments
        try {
            mTitleEt = (EditText) getView().findViewById(R.id.add_trip_title_et);
            mDateEt = (EditText) getView().findViewById(R.id.add_trip_date_et);
            mDurationEt = (EditText) getView().findViewById(R.id.add_trip_duration_et);

            String title = mTitleEt.getText().toString();
            String duration = mDurationEt.getText().toString();
            Date date = Date.valueOf(mDateEt.getText().toString());

            mItem = new Trip(title, duration, date);
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TripViewModel tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        view.findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItem != null) {
                    tripViewModel.insert(mItem);
                    Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.fragmentContainerView);
                }
            }
        });
        return view;
    }
}