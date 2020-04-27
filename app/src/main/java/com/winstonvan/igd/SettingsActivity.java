package com.winstonvan.igd;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.winstonvan.problematicinternetuse.R;

public class SettingsActivity extends AppCompatActivity {
    FirebaseUser user;
    TextView mSettingsEmailAddress;
    EditText mCurrentEmail, mNewEmail, mConfirmNewEmail, mCurrentPassword, mNewPassword, mConfirmNewPassword;
    Button mSaveEmail, mSavePassword;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mSettingsEmailAddress = findViewById(R.id.settingsEmailAddress);

        // nav
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // email settings
        mCurrentEmail = findViewById(R.id.currentEmailField);
        mNewEmail = findViewById(R.id.newEmailField);
        mConfirmNewEmail = findViewById(R.id.confirmNewEmailField);

        // password settings
        mCurrentPassword = findViewById(R.id.currentPasswordField);
        mNewPassword = findViewById(R.id.newPasswordField);
        mConfirmNewPassword = findViewById(R.id.confirmNewPasswordField);

        // buttons
        mSaveEmail = findViewById(R.id.emailSave);
        mSavePassword = findViewById(R.id.passwordSave);


        // get user's profile
        if (user != null) {
            String email = user.getEmail();
            mSettingsEmailAddress.setText("" + email);

        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }

        mSaveEmail.setOnClickListener(new View.OnClickListener() {
            final String currentEmail = mCurrentEmail.getText().toString().trim();
            final String newEmail = mNewEmail.getText().toString().trim();
            final String confirmNewEmail = mConfirmNewEmail.getText().toString().trim();


            @Override
            public void onClick(View v) {
                Log.e("Current email: ", currentEmail);
                Log.e("New email: ", newEmail);
                Log.e("Confirm new email: ", confirmNewEmail);
                Toast.makeText(SettingsActivity.this, currentEmail, Toast.LENGTH_SHORT).show();

                if (!Utils.isEmailValid(currentEmail) || !Utils.isEmailValid(newEmail) || !Utils.isEmailValid(confirmNewEmail)) {
                    if (!Utils.isEmailValid(currentEmail))
                        mCurrentEmail.setError("Current email address is invalid.");

                    if (!Utils.isEmailValid(newEmail))
                        mNewEmail.setError("New email address is invalid.");

                    if (!Utils.isEmailValid(confirmNewEmail))
                        mConfirmNewEmail.setError("Confirm new email address is invalid.");

                    else if (!newEmail.equals(confirmNewEmail) && ((!TextUtils.isEmpty(newEmail)) && (!TextUtils.isEmpty(confirmNewEmail))))
                        mConfirmNewEmail.setError("Confirm new email address does not match.");

                    return;
                }
            }
        });

        mSavePassword.setOnClickListener(new View.OnClickListener() {
            final String currentPassword = mCurrentPassword.getText().toString().trim();
            final String newPassword = mNewPassword.getText().toString().trim();
            final String confirmNewPassword = mConfirmNewPassword.getText().toString().trim();

            @Override
            public void onClick(View v) {
                if (!Utils.isPasswordValid(currentPassword) || !Utils.isPasswordValid(newPassword) || !Utils.isPasswordValid(confirmNewPassword)) {
                    if (!Utils.isPasswordValid(currentPassword))
                        mCurrentPassword.setError("Current email is invalid.");

                    if (!Utils.isPasswordValid(newPassword))
                        mNewPassword.setError("New password is invalid.");

                    if (!Utils.isEmailValid(confirmNewPassword))
                        mConfirmNewPassword.setError("Confirm new password is invalid.");

                    else if (!Utils.isConfirmPasswordValid(newPassword, confirmNewPassword))
                        mConfirmNewPassword.setError("Confirm new password does not match.");

                    return;
                }
            }
        });
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.navigation_test)
                    {
                        startActivity(new Intent(getApplicationContext(), TestActivity.class));
                    }
                    else if (item.getItemId() == R.id.navigation_results)
                    {
                        startActivity(new Intent(getApplicationContext(), TestResultsActivity.class));
                    }
                    else if (item.getItemId() == R.id.navigation_home)
                    {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else if (item.getItemId() == R.id.navigation_resources)
                    {
                        startActivity(new Intent(getApplicationContext(), ResourcesActivity.class));
                    }
                    else if (item.getItemId() == R.id.navigation_settings)
                    {
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    }
                    return false;
                }
            };
}
