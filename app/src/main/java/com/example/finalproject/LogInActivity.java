package com.example.finalproject;

import static com.example.finalproject.R.id.fragmentContainer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends Fragment {
    public LogInActivity() {
        // Required empty public constructor
    }
    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;

    FirebaseAuth mAuth;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_log_in__activity, container, false);

        etLoginEmail = rootview.findViewById(R.id.etLoginEmail);
        etLoginPassword = rootview.findViewById(R.id.etLoginPass);
        tvRegisterHere = rootview.findViewById(R.id.tvRegisterHere);
        btnLogin = rootview.findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });
        tvRegisterHere.setOnClickListener(view ->{
            Fragment fragment = new RegisterActivity();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(fragmentContainer, fragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        });
   return rootview;
    }


    private void loginUser(){
        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            etLoginEmail.setError("Email cannot be empty");
            etLoginEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            etLoginPassword.setError("Password cannot be empty");
            etLoginPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getActivity(), "User logged in successfully", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new Home_Activity();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(fragmentContainer, fragment, "findThisFragment")
                                .addToBackStack(null)
                                .commit();
                    }else{
                        Toast.makeText(getContext(), "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
