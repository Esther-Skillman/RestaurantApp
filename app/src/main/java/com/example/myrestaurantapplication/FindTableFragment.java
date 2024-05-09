package com.example.myrestaurantapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FindTableFragment extends Fragment {
    public FindTableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_table, container, false);

        // Retrieve data from the Bundle
        Bundle args = getArguments();
        if (args != null) {
            String date = args.getString("date");
            String tableSize = args.getString("tableSize");
            String meal = args.getString("meal");
            String seatingArea = args.getString("seatingArea");

            Button submitButton = view.findViewById(R.id.bookTableButton);
            submitButton.setOnClickListener(v -> {
                BookReservationFunction bookReservationFunction = new BookReservationFunction(getContext(), date, tableSize, meal, seatingArea);
            });
        }
        //Would be code here to scan through the api for matches of the booking details to show table availability

        return view;
    }
}