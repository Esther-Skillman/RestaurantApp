package com.example.myrestaurantapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button buttonGoToBookTableActivity = view.findViewById(R.id.signUpCheck);
        buttonGoToBookTableActivity.setOnClickListener(v -> {
            // Navigate to MenuActivity
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
