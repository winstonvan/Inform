package com.winstonvan.igd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.winstonvan.problematicinternetuse.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView mBackButton, mSubmitButton;
    EditText mEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // initialize components by ID
        mBackButton = findViewById(R.id.forgotPasswordBackButton);
        mSubmitButton = findViewById(R.id.forgotPasswordSubmit);
        mEmailAddress = findViewById(R.id.forgotPasswordEmailAddress);

        // initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        // back button listener
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return;
            }
        });

        // submit button listener
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forgotPasswordEmail = mEmailAddress.getText().toString().trim();

                if (!Utils.isEmailValid(forgotPasswordEmail)) {
                    mEmailAddress.setError("Invalid email address.");
                    return;
                }

                // send password reset to Firebase
                mAuth.sendPasswordResetEmail(forgotPasswordEmail).addOnCompleteListener(ForgotPasswordActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            return;
                        }
                    }
                });
            }
        });
    }
}
