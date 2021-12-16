package com.example.finalproject;

import static com.example.finalproject.R.id.bottomNavigation;
import static com.example.finalproject.R.id.fragmentContainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

@SuppressWarnings("ALL")
public class LogInActivity extends Fragment {
    public LogInActivity() {
        // Required empty public constructor
    }
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 0;
    private static final String TAG = "GoogleActivity";

    TextInputEditText etLoginEmail;
    TextInputEditText etLoginPassword;
    TextView tvRegisterHere;
    TextView tvSwitchAccount;
    Button btnLogin;
   private SignInButton googleSignIn;
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_log_in__activity, container, false);
        BottomNavigationView bottomNavigationView = getActivity().findViewById(bottomNavigation);
        bottomNavigationView.setVisibility(View.GONE);

        etLoginEmail = rootview.findViewById(R.id.etLoginEmail);
        etLoginPassword = rootview.findViewById(R.id.etLoginPass);
        tvRegisterHere = rootview.findViewById(R.id.tvRegisterHere);
        tvSwitchAccount = rootview.findViewById(R.id.switchAccount);
        btnLogin = rootview.findViewById(R.id.btnLogin);
        googleSignIn = rootview.findViewById(R.id.googleButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext().getApplicationContext(),gso);
        mAuth = FirebaseAuth.getInstance();

        googleSignIn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                signIn();
                                            }
                                        });

                btnLogin.setOnClickListener(view -> {
                    loginUser();
                });

        tvSwitchAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchGoogleAccount();
            }
        });

        tvRegisterHere.setOnClickListener(view -> {
            Fragment fragment = new RegisterActivity();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(fragmentContainer, fragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        });

        return rootview;
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    // [END signin]
    private void switchGoogleAccount() {
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    private void updateUI(FirebaseUser user) {

    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Fragment fragment = new Home_Activity();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(fragmentContainer, fragment, "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                            BottomNavigationView bottomNavigationView = getActivity().findViewById(bottomNavigation);
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]


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
                        BottomNavigationView bottomNavigationView = getActivity().findViewById(bottomNavigation);
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(getContext(), "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
