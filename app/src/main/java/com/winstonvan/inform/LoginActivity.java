package com.winstonvan.inform;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button mLoginButton;
    EditText mEmailAddress, mPassword;
    CheckBox mShowPassword;
    TextView mForgotPassword, mNotRegisteredYet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize components by ID
        mLoginButton = findViewById(R.id.loginButton);
        mEmailAddress = findViewById(R.id.loginEmailAddress);
        mPassword = findViewById(R.id.loginPassword);
        mShowPassword = findViewById(R.id.loginShowPassword);
        mForgotPassword = findViewById(R.id.loginForgotPassword);
        mNotRegisteredYet = findViewById(R.id.loginNotRegisteredYet);

        // start Firebase instance
        mAuth = FirebaseAuth.getInstance();


        // check if user already logged in
        if (mAuth.getCurrentUser() != null) { // if current user is not null (logged in), go to main activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return;
        }

        // show password listener
        mShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                // check if button is checked
                if (isChecked) {
                    mShowPassword.setText(R.string.hidePassword);// change text
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());// show password
                } else {
                    mShowPassword.setText(R.string.showPassword);// change text
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());// hide password
                }
            }
        });

        // sign up button listener
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginEmail = mEmailAddress.getText().toString().trim();
                String loginPassword = mPassword.getText().toString().trim();

                // field validation
                if (!Utils.isEmailValid(loginEmail) || !Utils.isPasswordValid(loginPassword)){
                    if (!Utils.isEmailValid(loginEmail))
                        mEmailAddress.setError("Email address is invalid.");

                    if (!Utils.isPasswordValid(loginPassword))
                        mPassword.setError("Password is invalid.");
                    Toast.makeText(LoginActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                    return; // return if email invalid
                }

                // log user in
                mAuth.signInWithEmailAndPassword(loginEmail, loginPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
            }
        });

        // forgot password listener
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
                return;
            }
        });

        // not a member yet listener
        mNotRegisteredYet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                return;
            }
        });
    }
}
