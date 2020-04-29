package com.winstonvan.inform;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResourcesActivity extends AppCompatActivity {

    TextView resource1, resource2, resource3, resource4, resource5;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        // initialize navigation bar
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // resources
        resource1 = findViewById(R.id.resource1);
        resource2 = findViewById(R.id.resource2);
        resource3 = findViewById(R.id.resource3);
        resource4 = findViewById(R.id.resource4);
        resource5 = findViewById(R.id.resource5);

        // initialize links
        resource1.setMovementMethod(LinkMovementMethod.getInstance());
        resource2.setMovementMethod(LinkMovementMethod.getInstance());
        resource3.setMovementMethod(LinkMovementMethod.getInstance());
        resource4.setMovementMethod(LinkMovementMethod.getInstance());
        resource5.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // navigation bar listener
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
