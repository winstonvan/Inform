package com.winstonvan.inform;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button mRegisterButton;
    EditText mFullName, mEmailAddress, mPassword, mConfirmPassword;
    TextView mAlreadyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialize components by ID
        mRegisterButton = findViewById(R.id.registerButton);
        mFullName = findViewById(R.id.registerFullName);
        mEmailAddress = findViewById(R.id.registerEmailAddress);
        mPassword = findViewById(R.id.registerPassword);
        mConfirmPassword = findViewById(R.id.registerConfirmPassword);
        mAlreadyUser = findViewById(R.id.registerAlreadyUser);

        // start Firebase instance
        mAuth = FirebaseAuth.getInstance();

        // check if user already logged in
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return;
        }

        // sign up button listener
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String registerEmail = mEmailAddress.getText().toString().trim();
                final String registerPassword = mPassword.getText().toString().trim();
                final String registerFullName = mFullName.getText().toString().trim();
                String registerConfirmPassword = mConfirmPassword.getText().toString().trim();

                // error checking
                if (!Utils.isEmailValid(registerEmail) || !Utils.isPasswordValid(registerPassword) ||
                        !(Utils.isConfirmPasswordValid(registerPassword, registerConfirmPassword)) || TextUtils.isEmpty(registerFullName)) {
                    if (!Utils.isEmailValid(registerEmail))
                        mEmailAddress.setError("Email address is invalid.");

                    if (!Utils.isPasswordValid(registerPassword))
                        mPassword.setError("Password is invalid.");

                    if (Utils.isConfirmPasswordValid(registerPassword, registerConfirmPassword))
                        mConfirmPassword.setError("Password does not match.");

                    if (TextUtils.isEmpty(registerFullName))
                        mFullName.setError("Name is invalid.");

                    return;
                }

                // register user
                mAuth.createUserWithEmailAndPassword(registerEmail, registerPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            db = FirebaseFirestore.getInstance();
                            String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DocumentReference documentReference = db.collection("users").document(userUID).collection("info").document("login");

                            // Create a new user with a first and last name
                            Map<String, Object> user = new HashMap<>();
                            user.put("fullName", registerFullName);
                            user.put("emailAddress", registerEmail);
                            user.put("password", registerPassword);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    Log.e("Signup: ", "Success");
                                    finish();
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("Signup: ", "Error");
                        }
                    }
                });
            }
        });

        // already user listener
        mAlreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}
