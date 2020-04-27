package com.winstonvan.igd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.winstonvan.problematicinternetuse.R;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // redirect to login screen if no user logged in
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    public void goTest(View view) {
        startActivity(new Intent(getApplicationContext(), TestActivity.class));
    }

    public void goTestResults(View view) {
        startActivity(new Intent(getApplicationContext(), TestResultsActivity.class));
    }

    public void goResources(View view) {
        startActivity(new Intent(getApplicationContext(), ResourcesActivity.class));
    }

    public void goSettings(View view) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }

    public void goAbout(View view) {
        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
    }

    public void goLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}
