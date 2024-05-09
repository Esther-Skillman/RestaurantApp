package com.example.myrestaurantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;


public class MenuActivity extends AppCompatActivity {

    private void deleteCustomerData() {
        try {
            // Write customerName and customerPhoneNumber to JSONObject
            JSONObject userData = new JSONObject();
            userData.put("customerName", "");
            userData.put("customerPhoneNumber", "");


            String filename = "userdata.json";
            FileOutputStream outputStream = new FileOutputStream(new File(getExternalFilesDir(null), filename));
            outputStream.write(userData.toString().getBytes());
            outputStream.close();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Buttons to other Activities
        Button buttonGoToBookingsActivity = findViewById(R.id.bookingsButton);
        buttonGoToBookingsActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, BookingsActivity.class);
            startActivity(intent);
        });

        Button buttonGoToBookTableActivity = findViewById(R.id.bookTableButton);
        buttonGoToBookTableActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, BookTableActivity.class);
            startActivity(intent);
        });

        Button buttonToSignOut = findViewById(R.id.signOutButton);
        buttonToSignOut.setOnClickListener(v -> {
            deleteCustomerData();
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
