package com.example.myrestaurantapplication;

import android.content.Context;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ApiRequest {

    public interface ApiResponseCallback {
        void onResponse(JSONArray bookingData);
        void onError(String error);
    }

    //Retrieve data from API
    public static void getData(Context context, ApiResponseCallback callback) {
        RequestQueue myRequestQueue = Volley.newRequestQueue(context);
        String url = "https://web.socem.plymouth.ac.uk/COMP2000/ReservationApi/api/Reservations";

        JsonArrayRequest myArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    callback.onResponse(response);
                },
                error -> {
                    callback.onError(error.getMessage());
                });

        myRequestQueue.add(myArrayRequest);
    }
}

