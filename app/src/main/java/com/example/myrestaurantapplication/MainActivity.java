package com.example.myrestaurantapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button buttonToLoginFragment = findViewById(R.id.loginButton);
        buttonToLoginFragment.setOnClickListener(v -> {
            // Fragment Transaction to navigate to a LoginFragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Initialise LoginFragment
            Fragment newFragment = new LoginFragment();

            fragmentTransaction.replace(R.id.fragment_container, newFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        Button buttonToSignUpFragment = findViewById(R.id.signUpButton);
        buttonToSignUpFragment.setOnClickListener(v -> {
            // Perform Fragment Transaction to navigate to a SignUpFragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Initialise SignUpFragment
            Fragment newFragment = new SignUpFragment();

            fragmentTransaction.replace(R.id.fragment_container, newFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    }