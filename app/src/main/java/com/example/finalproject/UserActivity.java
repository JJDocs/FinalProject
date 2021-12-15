package com.example.finalproject;

import static com.example.finalproject.R.id.fragmentContainer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends Fragment {


    public UserActivity() {
        // Required empty public constructor
    }


    Button btnLogOut;
    FirebaseAuth mAuth;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_user__activity, container, false);

        // Inflate the layout for this fragment
        btnLogOut = rootview.findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();

        btnLogOut.setOnClickListener(view ->{
            mAuth.signOut();
            Fragment fragment = new LogInActivity();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(fragmentContainer, fragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        });

        return rootview;
    }

    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            Fragment fragment = new LogInActivity();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(fragmentContainer, fragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
    }
}