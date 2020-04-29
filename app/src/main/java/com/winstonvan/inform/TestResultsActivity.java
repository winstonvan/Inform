package com.winstonvan.inform;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class TestResultsActivity extends AppCompatActivity {
    FirebaseFirestore db;
    LinearLayout layout;
    TextView mIGDScore, mTestScore, mGamerProfile, mainText;
    ProgressBar mSalienceValue, mMoodModificationValue, mToleranceValue, mWithdrawalSymptomsValue, mConflictValue, mRelapseValue;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        // initialize navigation bar
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // initialize components by ID
        mIGDScore = findViewById(R.id.igdScore);
        mTestScore = findViewById(R.id.testScore);
        mGamerProfile = findViewById(R.id.gamerProfile);
        mSalienceValue = findViewById(R.id.salienceValue);
        mMoodModificationValue = findViewById(R.id.moodModificationValue);
        mToleranceValue = findViewById(R.id.toleranceValue);
        mWithdrawalSymptomsValue = findViewById(R.id.withdrawalSymptomsValue);
        mConflictValue = findViewById(R.id.conflictValue);
        mRelapseValue = findViewById(R.id.relapseValue);

        // display "not complete" text and hide everything else if test not complete
        layout = findViewById(R.id.main);
        mainText = findViewById(R.id.mainText);

        // connect to Firebase
        db = FirebaseFirestore.getInstance();

        // get user UID
        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // open Firebase collection
        DocumentReference documentReference = db.collection("users").document(userUID).collection("test").document("results");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> map = document.getData();

                    // test not complete
                    if (!document.exists()) {
                        mainText.setVisibility(TextView.VISIBLE);
                        mainText.setText("Results will appear once test is complete.");
                        layout.setVisibility(TextView.GONE);


                    }

                    // test completed
                    else {
                        // unhide main layout
                        layout.setVisibility(TextView.VISIBLE);

                        // get fields from Firebase to display
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("Gamer type")) {
                                mGamerProfile.setText(entry.getValue().toString() + " gamer");
                            }
                            if (entry.getKey().equals("Total score")) {
                                mTestScore.setText(entry.getValue().toString());
                            }
                            if (entry.getKey().equals("Salience")) {
                                mSalienceValue.setProgress((int) ((((Double.parseDouble(entry.getValue().toString()))) / 5) * 100));
                            }
                            if (entry.getKey().equals("Mood modification")) {
                                mMoodModificationValue.setProgress((int) ((((Double.parseDouble(entry.getValue().toString()))) / 5) * 100));
                            }
                            if (entry.getKey().equals("Tolerance")) {
                                mToleranceValue.setProgress((int) ((((Double.parseDouble(entry.getValue().toString()))) / 5) * 100));
                            }
                            if (entry.getKey().equals("Withdrawal symptoms")) {
                                mWithdrawalSymptomsValue.setProgress((int) ((((Double.parseDouble(entry.getValue().toString()))) / 5) * 100));
                            }
                            if (entry.getKey().equals("Conflict")) {
                                mConflictValue.setProgress((int) ((((Double.parseDouble(entry.getValue().toString()))) / 5) * 100));
                            }
                            if (entry.getKey().equals("Relapse")) {
                                mRelapseValue.setProgress((int) ((((Double.parseDouble(entry.getValue().toString()))) / 5) * 100));
                            }
                        }
                    }
                }
            }
        });
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

    public void goResources(View view) {
        startActivity(new Intent(getApplicationContext(), ResourcesActivity.class));
    }
}
