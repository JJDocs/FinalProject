package com.example.finalproject;

import android.net.Uri;
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
import com.google.firebase.auth.UserInfo;

public class Sign_in_Activity extends Fragment {
/*
        FirebaseUser currentUser;//used to store current user of account
        FirebaseAuth mAuth;//Used for firebase authentication
        Button logout;

 */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootview = inflater.inflate(R.layout.fragment_sign_in__activity, container, false);
            // Inflate the layout for this fragment
            /*
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

             */
            return rootview;

        }
/*
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
public void checkCurrentUser() {
    // [START check_current_user]
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
        // User is signed in
    } else {
        // No user is signed in
    }
    // [END check_current_user]
}

    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
        // [END get_user_profile]
    }
    public void getProviderData() {
        // [START get_provider_data]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            }
        }
        // [END get_provider_data]
    }
}