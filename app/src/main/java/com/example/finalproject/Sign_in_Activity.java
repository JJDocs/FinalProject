package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/*
public class Sign_in_Activity extends Fragment {

        FirebaseUser currentUser;//used to store current user of account
        FirebaseAuth mAuth;//Used for firebase authentication
        Button logout;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootview = inflater.inflate(R.layout.fragment_sign_in__activity, container, false);
            // Inflate the layout for this fragment
            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();
            logout = rootview.findViewById(R.id.logout);
            //To logout from the application
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    //After logging out send user to Login Activity to login again
                    sendToLoginActivity();
                }
            });
            return rootview;

        }

        @Override
        public void onStart() {
            super.onStart();
            //Check if user has already signed in if not send user to Login Activity
            if(currentUser==null)
            {
                sendToLoginActivity();
            }
        }

        private void sendToLoginActivity() {
            //To send user to Login Activity
            Intent loginIntent = new Intent(getActivity(),LoginActivity.class);
            startActivity(loginIntent);
        }

    */