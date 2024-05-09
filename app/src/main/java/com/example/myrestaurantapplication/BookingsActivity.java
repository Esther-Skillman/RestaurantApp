package com.example.myrestaurantapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.widget.Button;



public class BookingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        // Read data from external storage to retrieve customerName and customerPhoneNumber
        readDataFromExternalStorage();
    }

    private void readDataFromExternalStorage() {
        try {
            String filename = "userdata.json";
            File file = new File(getExternalFilesDir(null), filename);
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            inputStream.close();

            //Reference to https://www.digitalocean.com/community/tutorials/java-read-file-to-string

            // Parse the JSON data
            JSONObject userData = new JSONObject(stringBuilder.toString());

            // Get customer name and phone number
            String storedCustomerName = userData.getString("customerName");
            String storedPhoneNumber = userData.getString("customerPhoneNumber");

            // Call the ApiRequest to fetch data, and display each booking with date according to the uniqueness of their ID
            ApiRequest.getData(this, new ApiRequest.ApiResponseCallback() {
                @Override
                public void onResponse(JSONArray bookingData) {
                    runOnUiThread(() -> {
                        ArrayList<String> datesForUser = new ArrayList<>();

                        for (int i = 0; i < bookingData.length(); i++) {
                            try {
                                JSONObject booking = bookingData.getJSONObject(i);
                                String customerName = booking.getString("customerName");
                                String customerPhoneNumber = booking.getString("customerPhoneNumber");
                                String id = booking.getString("id");
                                String date = booking.getString("date");

                                if (customerName.equals(storedCustomerName) && customerPhoneNumber.equals(storedPhoneNumber)) {
                                    int occurrences = 0;
                                    for (int j = 0; j < bookingData.length(); j++) {
                                        JSONObject checkBooking = bookingData.getJSONObject(j);
                                        String checkCustomerName = checkBooking.getString("customerName");
                                        String checkCustomerPhoneNumber = checkBooking.getString("customerPhoneNumber");
                                        String checkId = checkBooking.getString("id");

                                        if (checkCustomerName.equals(customerName) &&
                                                checkCustomerPhoneNumber.equals(customerPhoneNumber) &&
                                                !checkId.equals(id)) {
                                            occurrences++;
                                        }
                                    }

                                    if (occurrences > 0) {

                                        datesForUser.add(date);

                                        // Create Button for each ID item
                                        int buttonId = Integer.parseInt(id);
                                        Button button = new Button(BookingsActivity.this);
                                        button.setId(buttonId);

                                        String buttonText = "Booking " + datesForUser.size() + " :" + date;
                                        button.setText(buttonText);

                                        button.setTextColor(ContextCompat.getColor(BookingsActivity.this, R.color.gold));
                                        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                        button.setBackgroundTintList(ContextCompat.getColorStateList(BookingsActivity.this, R.color.background));
                                        button.setAllCaps(false);
                                        button.setGravity(Gravity.CENTER);
                                        GradientDrawable shapeDrawable = new GradientDrawable();
                                        shapeDrawable.setShape(GradientDrawable.RECTANGLE);
                                        shapeDrawable.setStroke(3, ContextCompat.getColor(BookingsActivity.this, R.color.gold));


                                        button.setBackground(shapeDrawable);

                                        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                                                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                                ConstraintLayout.LayoutParams.WRAP_CONTENT
                                        );

                                        button.setLayoutParams(layoutParams);

                                        // Add the Button to the layout
                                        ConstraintLayout layout = findViewById(R.id.activityBookings);
                                        layout.addView(button);

                                        if (datesForUser.size() == 1) {
                                            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                                        } else {
                                            layoutParams.topToBottom = layout.getChildAt(layout.getChildCount() - 2).getId();
                                        }

                                        button.setLayoutParams(layoutParams);


                                        button.setOnClickListener(view -> {

                                            Intent intent = new Intent(BookingsActivity.this, BookingDetailsActivity.class);

                                            // Pass the booking ID to the next activity
                                            intent.putExtra("BookingID", id);


                                            startActivity(intent);
                                        });
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        // Display the error in a TextView
                        TextView errorTextView = new TextView(BookingsActivity.this);
                        errorTextView.setId(View.generateViewId());
                        errorTextView.setText("Error: " + error);
                        errorTextView.setTextColor(ContextCompat.getColor(BookingsActivity.this, R.color.white));
                        errorTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);

                        ConstraintLayout layout = findViewById(R.id.activityBookings);
                        layout.addView(errorTextView);

                        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT
                        );
                        errorTextView.setLayoutParams(layoutParams);


                        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
                        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}