package com.example.myrestaurantapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

public class BookTableActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private Button confirmDateButton;
    private String date = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_table);

        calendarView = findViewById(R.id.calendarView);
        confirmDateButton = findViewById(R.id.confirmDateButton);

        confirmDateButton.setOnClickListener(view -> {
            if (calendarView.getVisibility() == View.VISIBLE) {
                // Calendar is visible, hide it and change button text
                calendarView.setVisibility(View.GONE);
                confirmDateButton.setText("Date");

                Log.d("SelectedDate", date);
            } else {
                // Calendar is hidden, show it and change button text
                calendarView.setVisibility(View.VISIBLE);
                confirmDateButton.setText("Confirm Date");
            }
        });

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            date = year + "-" + (month + 1) + "-" + dayOfMonth;

            calendarView.setVisibility(View.GONE);
            confirmDateButton.setText("Date");
        });

        Button buttonToFindTableFragment = findViewById(R.id.findTableButton);
        buttonToFindTableFragment.setOnClickListener(v -> {
            Spinner tableSizeSpinner = findViewById(R.id.tableSizeSpinner);
            Spinner mealSpinner = findViewById(R.id.mealSpinner);
            Spinner seatingAreaSpinner = findViewById(R.id.seatingAreaSpinner);

            // Get selected values from spinners
            String tableSize = tableSizeSpinner.getSelectedItem().toString();
            String meal = mealSpinner.getSelectedItem().toString();
            String seatingArea = seatingAreaSpinner.getSelectedItem().toString();

            // Create Fragment instance and pass data using Bundle reference to https://stackoverflow.com/questions/16499385/using-bundle-to-pass-data-between-fragment-to-another-fragment-example
            FindTableFragment fragment = new FindTableFragment();
            Bundle bundle = new Bundle();
            bundle.putString("date", date);
            bundle.putString("tableSize", tableSize);
            bundle.putString("meal", meal);
            bundle.putString("seatingArea", seatingArea);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        });



    }
}
