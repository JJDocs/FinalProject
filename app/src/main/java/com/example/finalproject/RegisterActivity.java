package com.example.finalproject;

import static com.example.finalproject.R.id.fragmentContainer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends Fragment {
    public RegisterActivity(){

    }

    TextInputEditText etRegEmail;
    TextInputEditText etRegPassword;
    TextView tvLoginHere;
    Button btnRegister;

    FirebaseAuth mAuth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.register, container, false);
        etRegEmail = rootview.findViewById(R.id.etRegEmail);
        etRegPassword = rootview.findViewById(R.id.etRegPass);
        tvLoginHere = rootview.findViewById(R.id.tvLoginHere);
        btnRegister = rootview.findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        tvLoginHere.setOnClickListener(view -> {
            Fragment fragment = new LogInActivity();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        });

        btnRegister.setOnClickListener(view ->{
          createUser();
        });

   return rootview;
    }

    private void createUser() {
        String email = etRegEmail.getText().toString();
        String password = etRegPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new LogInActivity();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(fragmentContainer, fragment, "findThisFragment")
                                .addToBackStack(null)
                                .commit();
                    }else{
                        Toast.makeText(getContext(), "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}