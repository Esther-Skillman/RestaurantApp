package com.example.myrestaurantapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class BookingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        // Read data from external storage
        readDataFromExternalStorage();
    }

    private void readDataFromExternalStorage() {
        try {
            // Get the booking ID passed from the previous activity
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("BookingID")) {
                String bookingId = intent.getStringExtra("BookingID");

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

                // Parse the JSON data
                JSONObject userData = new JSONObject(stringBuilder.toString());

                // Get customerName and customerPhoneNumber
                String storedCustomerName = userData.getString("customerName");
                String storedPhoneNumber = userData.getString("customerPhoneNumber");

                // Call the ApiRequest to fetch data
                ApiRequest.getData(this, new ApiRequest.ApiResponseCallback() {
                    @Override
                    public void onResponse(JSONArray bookingData) {
                        runOnUiThread(() -> {
                            boolean matchFound = false; // Flag to track if a match is found

                            for (int i = 0; i < bookingData.length(); i++) {
                                try {
                                    JSONObject booking = bookingData.getJSONObject(i);
                                    String customerName = booking.getString("customerName");
                                    String customerPhoneNumber = booking.getString("customerPhoneNumber");

                                    if (customerName.equals(storedCustomerName) && customerPhoneNumber.equals(storedPhoneNumber)) {
                                        String id = booking.getString("id");

                                        if (id.equals(bookingId)) { // Check if the ID matches the passed ID
                                            // Retrieve other details for the matched booking
                                            String meal = booking.getString("meal");
                                            String seatingArea = booking.getString("seatingArea");
                                            int tableSize = booking.getInt("tableSize");
                                            String date = booking.getString("date");

                                            // Update UI with retrieved data


                                            TextView textViewMeal = findViewById(R.id.meal);
                                            textViewMeal.setText("Meal: " + meal);

                                            TextView textViewSeatingArea = findViewById(R.id.seatingArea);
                                            textViewSeatingArea.setText("Seating Area: " + seatingArea);

                                            TextView textViewTableSize = findViewById(R.id.tableSize);
                                            textViewTableSize.setText("Table Size: " + String.valueOf(tableSize));

                                            TextView textViewDate = findViewById(R.id.date);
                                            textViewDate.setText("Date: " + date);

                                            matchFound = true;
                                            break;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (!matchFound) {
                                // Case where no match is found
                            }
                        });
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
