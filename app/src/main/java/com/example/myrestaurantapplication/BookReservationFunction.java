package com.example.myrestaurantapplication;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class BookReservationFunction {
    private String date;
    private String tableSize;
    private String meal;
    private String seatingArea;
    private Context context;

    // Function to receive data
    public BookReservationFunction(Context context, String date, String tableSize, String meal, String seatingArea) {
        this.context = context;
        this.date = date;
        this.tableSize = tableSize;
        this.meal = meal;
        this.seatingArea = seatingArea;

        try {

            String filename = "userdata.json";
            File file = new File(context.getExternalFilesDir(null), filename);
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            inputStream.close();


            JSONObject userData = new JSONObject(stringBuilder.toString());


            String storedCustomerName = userData.getString("customerName");
            String storedPhoneNumber = userData.getString("customerPhoneNumber");

            JSONObject reservation = new JSONObject();
            reservation.put("customerName", storedCustomerName);
            reservation.put("customerPhoneNumber", storedPhoneNumber);
            reservation.put("meal", meal);
            reservation.put("seatingArea", seatingArea);
            reservation.put("tableSize", tableSize);
            reservation.put("date", "2023-11-29");

            // Use Volley to send a POST request to the API
            String url = "https://web.socem.plymouth.ac.uk/COMP2000/ReservationApi/api/Reservations";
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, reservation,
                    response -> Log.d("API_RESPONSE", "Success: " + response.toString()),
                    error -> {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorMessage = new String(error.networkResponse.data);
                            Log.e("API_ERROR", errorMessage);
                        } else {
                            Log.e("API_ERROR", "Unknown error occurred");
                        }
                    });

            // Add the request to the RequestQueue
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
