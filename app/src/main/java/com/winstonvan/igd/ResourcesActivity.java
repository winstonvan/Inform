package com.winstonvan.igd;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.winstonvan.problematicinternetuse.R;

public class ResourcesActivity extends AppCompatActivity {

    TextView resource1, resource2, resource3, resource4, resource5;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        // nav
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // resources
        resource1 = findViewById(R.id.resource1);
        resource2 = findViewById(R.id.resource2);
        resource3 = findViewById(R.id.resource3);
        resource4 = findViewById(R.id.resource4);
        resource5 = findViewById(R.id.resource5);

        // initialise links
        resource1.setMovementMethod(LinkMovementMethod.getInstance());
        resource2.setMovementMethod(LinkMovementMethod.getInstance());
        resource3.setMovementMethod(LinkMovementMethod.getInstance());
        resource4.setMovementMethod(LinkMovementMethod.getInstance());
        resource5.setMovementMethod(LinkMovementMethod.getInstance());
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
