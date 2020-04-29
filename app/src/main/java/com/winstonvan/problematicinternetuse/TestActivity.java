package com.winstonvan.problematicinternetuse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    int index = 0;
    int PROGRESS_BAR_INCREMENT = 0;
    ProgressBar progressBar;
    Boolean leave = false;
    TextView question;
    AlertDialog.Builder alert;
    private AnswerActivity[] list = {new AnswerActivity(R.string.question1), new AnswerActivity(R.string.question2), new AnswerActivity(R.string.question3), new AnswerActivity(R.string.question4), new AnswerActivity(R.string.question5), new AnswerActivity(R.string.question6), new AnswerActivity(R.string.question7), new AnswerActivity(R.string.question8), new AnswerActivity(R.string.question9), new AnswerActivity(R.string.question10), new AnswerActivity(R.string.question11), new AnswerActivity(R.string.question12), new AnswerActivity(R.string.question13), new AnswerActivity(R.string.question14), new AnswerActivity(R.string.question15), new AnswerActivity(R.string.question16), new AnswerActivity(R.string.question17), new AnswerActivity(R.string.question18), new AnswerActivity(R.string.question19), new AnswerActivity(R.string.question20)};
    BottomNavigationView bottomNavigation;

    public TestActivity() {
        this.PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0d / list.length);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // initialize navigation bar
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // question + progress bar
        this.question = findViewById(R.id.questionTextView);
        this.progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.stronglyAgree).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (index == 1 || index == 18)
                    list[index].answer = 1;

                else
                    list[index].answer = 5;

                TestActivity.this.updateQuestion(view);
            }
        });
        findViewById(R.id.agree).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (index == 1 || index == 18)
                    list[index].answer = 2;
                else
                    list[index].answer = 4;

                TestActivity.this.updateQuestion(view);
            }
        });
        findViewById(R.id.notSure).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                list[index].answer = 3;
                TestActivity.this.updateQuestion(view);
            }
        });
        findViewById(R.id.disagree).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (index == 1 || index == 18)
                    list[index].answer = 4;
                else
                    list[index].answer = 2;

                TestActivity.this.updateQuestion(view);
            }
        });
        findViewById(R.id.stronglyDisagree).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (index == 1 || index == 18)
                    list[index].answer = 5;
                else
                    list[index].answer = 1;

                TestActivity.this.updateQuestion(view);
            }
        });
        findViewById(R.id.skipButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (index != 0) {
                    index--;
                    question.setText(list[index].question);
                    progressBar.incrementProgressBy(-PROGRESS_BAR_INCREMENT);

                    if (index == 0) findViewById(R.id.skipButton).setVisibility(Button.INVISIBLE);
                }
            }
        });
    }

    public void updateQuestion(final View view) {
        this.progressBar.incrementProgressBy(this.PROGRESS_BAR_INCREMENT); // increment bar
        this.index++;
        Log.e("Index", "" + this.index);

        if (index > 0) findViewById(R.id.skipButton).setVisibility(Button.VISIBLE);

        // check if end of test
        if (this.index > 19) {
            this.alert = new AlertDialog.Builder(this);
            this.alert.setCancelable(false);
            this.alert.setTitle("Test Complete");
            AlertDialog.Builder builder = this.alert;

            this.alert.setPositiveButton("Go Home", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.e("Input: ", "Go Home");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
            this.alert.setNegativeButton("View Result", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.e("Input: ", "View Result");
                    TestActivity.this.calculateResult(view);
                    startActivity(new Intent(getApplicationContext(), TestResultsActivity.class));
                    finish();
                }
            });
            this.alert.show();
        } else this.question.setText(this.list[this.index].question); // change question
    }

    public void viewResult(View view) {
        this.index = 0;
        startActivity(new Intent(getApplicationContext(), TestResultsActivity.class));
    }

    public void calculateResult(View view) {
        int totalScore = 0;
        double salience = 0; // 1, 7, 13
        double moodModification = 0; // 2R, 8, 14
        double tolerance = 0; // 3, 9, 15
        double withdrawalSymptoms = 0; // 4, 10, 16
        double conflict = 0; // 11, 17, 19R, 20
        double relapse = 0; // 6, 12, 18

        // calculate result
        for (int i = 0; i < 20; i++) {
            Log.e("Index " + i + ": ", "Calculating Result");

            // salience
            if (i == 0 || i == 6 || i == 12) {
                salience += list[i].answer;
                Log.e("Salience: ", "" + salience);
            }

            // mood modification
            else if (i == 1 || i == 7 || i == 13) {
                moodModification += list[i].answer;
                Log.e("Mood modification: ", "" + moodModification);
            }

            // tolerance
            else if (i == 2 || i == 8 || i == 13) {
                tolerance += list[i].answer;
                Log.e("Tolerance: ", "" + tolerance);
            }

            // withdrawal symptoms
            else if (i == 3 || i == 9 || i == 15) {
                withdrawalSymptoms += list[i].answer;
                Log.e("Withdrawal symptoms: ", "" + withdrawalSymptoms);
            }

            // conflict
            else if (i == 4 || i == 10 || i == 16 || i == 18 || i == 19) {
                conflict += list[i].answer;
                Log.e("Conflict: ", "" + conflict);
            }

            // relapse
            else if (i == 5 || i == 11 || i == 17) {
                relapse += list[i].answer;
                Log.e("Relapse: ", "" + relapse);
            } else {
            }

            totalScore += list[i].answer;
            Log.e("Total: ", totalScore + "");
        }

        // specific calculations
        salience /= 3;
        moodModification /= 3;
        tolerance /= 3;
        withdrawalSymptoms /= 3;
        conflict /= 5;
        relapse /= 3;

        // add data to database + gamer profiles
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userUID).collection("test").document("results");

        Map<String, Object> user = new HashMap<>();
        user.put("Salience", salience);
        user.put("Mood modification", moodModification);
        user.put("Tolerance", tolerance);
        user.put("Withdrawal symptoms", withdrawalSymptoms);
        user.put("Conflict", conflict);
        user.put("Relapse", relapse);
        user.put("Total score", totalScore);

        if (totalScore > 0 && totalScore < 41.37) user.put("Gamer type", "Casual");
        else if (totalScore >= 41.37 && totalScore < 57.83) user.put("Gamer type", "Regular");
        else if (totalScore >= 57.83 && totalScore < 67.34) user.put("Gamer type", "Low-risk");
        else if (totalScore >= 67.34 && totalScore < 77.43) user.put("Gamer type", "At-risk");
        else if (totalScore >= 77.43) user.put("Gamer type", "Disordered");

        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(getApplicationContext(), TestResultsActivity.class));
                Log.e("Add to database: ", "Success");
            }
        });
    }

    // navigation bar listener
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                    leave = false;
                    final MenuItem item2 = item;

                    // if test has already been started, prompt user to stay or leave
                    if (index > 0) {
                        final AlertDialog.Builder alert2;
                        alert2 = new AlertDialog.Builder(TestActivity.this);
                        alert2.setCancelable(false);
                        alert2.setTitle("Leaving this page will erase your progress. Do you wish to continue?");
                        AlertDialog.Builder builder = alert2;

                        // stay button will do nothing
                        alert2.setPositiveButton("Stay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });

                        // leave button will redirect user to specified activity
                        alert2.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
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
                            }
                        });
                        alert2.show();
                    }

                    // test hasn't been started, no prompt needed, start activity immediately
                    else {
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
                    }

                    return false;
                }
            };
}
