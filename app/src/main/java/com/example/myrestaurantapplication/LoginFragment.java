package com.example.myrestaurantapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText editTextUsernameLogin = view.findViewById(R.id.editTextUsernameLogin);
        EditText editTextPhoneLogin = view.findViewById(R.id.editTextPhoneLogin);

        Button button = view.findViewById(R.id.loginCheck);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName = editTextUsernameLogin.getText().toString();
                String phoneNumber = editTextPhoneLogin.getText().toString();

                // Create a JSON object
                JSONObject userData = new JSONObject();
                try {
                    userData.put("customerName", customerName);
                    userData.put("customerPhoneNumber", phoneNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Write the JSON object to a file in external storage
                String filename = "userdata.json";
                FileOutputStream outputStream;

                try {
                    File file = new File(requireContext().getExternalFilesDir(null), filename);
                    outputStream = new FileOutputStream(file);
                    outputStream.write(userData.toString().getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Create an Intent to start the menu activity
                Intent intent = new Intent(requireActivity(), MenuActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}