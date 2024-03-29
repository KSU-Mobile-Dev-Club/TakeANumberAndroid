package club.dev.mobile.ksu.takeanumber.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;
import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.R;
import club.dev.mobile.ksu.takeanumber.ViewModels.StudentQueueViewModel;

public class JoinSessionActivity extends AppCompatActivity {

    int queueLocation = -1;
    Student user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_session);

        final Button takeNumberButton = findViewById(R.id.takeNumber);
        final Button cancelButton = findViewById(R.id.cancel);
        final EditText enterNameText = findViewById(R.id.nameText);
        final EditText enterTime = findViewById(R.id.timeText);
        final TextView displayQueueLocation = findViewById(R.id.displayInQueue);
        final PopupWindow popup = new PopupWindow();
        final LinearLayout layout = new LinearLayout(this);
        final TextView time = findViewById(R.id.time);

        takeNumberButton.setEnabled(false);
        cancelButton.setEnabled(false);
        enterNameText.setEnabled(true);
        enterTime.setEnabled(true);
        displayQueueLocation.setText("");
        getSupportActionBar().setTitle(R.string.title_activity_join_session);

        // Get current help session the student wants to join
        final HelpSession testSession = new HelpSession();
        Intent intent = getIntent();
        String key = intent.getStringExtra("sessionKey");

        testSession.setFirebaseKey(key);

        final StudentQueueViewModel mViewModel = ViewModelProviders.of(this).get(StudentQueueViewModel.class);

        final Observer<List<Student>> queueObserver = new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> studentList) {

                final SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                int totalTime = 0;

                queueLocation = -1;
                if(studentList != null) {
                    for (int i = 0; i < studentList.size(); i++) {
                        totalTime += studentList.get(i).getTime();

                        if (studentList.get(i).equals(user)) {
                            if(studentList.get(i).getStatus() == 1){
                                displayQueueLocation.setText(getString(R.string.student_message_ready_for_help));

                                if (sharedSettings.getBoolean("buzz_for_notification", false)) {
                                    v.vibrate(1000);
                                }
                                ShowDialog(studentList);
                            }
                            else{
                                queueLocation = i + 1;
                                time.setText(getString(R.string.student_message_estimated_wait_time, totalTime));
                                if (queueLocation > 3) {
                                    displayQueueLocation.setText(getString(R.string.student_message_queue_location_fourth, queueLocation));
                                }
                                else if (queueLocation > 2) {
                                    displayQueueLocation.setText(getString(R.string.student_message_queue_location_third, queueLocation));
                                }
                                else if (queueLocation > 1) {
                                    displayQueueLocation.setText(getString(R.string.student_message_queue_location_second, queueLocation));
                                }
                                else {
                                    displayQueueLocation.setText(getString(R.string.student_message_queue_location_first, queueLocation));
                                }
                            }
                            break;
                        }
                    }
                    if (queueLocation < 0) {
                        displayQueueLocation.setText(getString(R.string.student_message_not_in_line));
                        time.setText(getString(R.string.student_message_estimated_wait_time, totalTime));
                    }
                }
            }
        };


        mViewModel.getStudentQueue(testSession.getFirebaseKey()).observe(this, queueObserver);

        takeNumberButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userName = enterNameText.getText().toString();
                int time = Integer.parseInt(enterTime.getText().toString());
                takeNumberButton.setEnabled(false);
                cancelButton.setEnabled(true);
                enterNameText.setEnabled(false);
                enterTime.setEnabled(false);
                user = new Student(userName, Calendar.getInstance().getTimeInMillis(), time);
                mViewModel.addStudentToQueue(user, testSession.getFirebaseKey());


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelButton.setEnabled(false);
                enterNameText.setEnabled(true);
                takeNumberButton.setEnabled(true);
                enterTime.setEnabled(true);
                displayQueueLocation.setText("");

                mViewModel.removeStudent(testSession.getFirebaseKey(), user.getFirebaseKey());
            }
        });

        enterNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (enterNameText.getText().toString().equals("") ||
                        enterTime.getText().toString().equals("")) {
                    takeNumberButton.setEnabled(false);
                } else {
                    takeNumberButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        enterTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (enterNameText.getText().toString().equals("") ||
                        enterTime.getText().toString().equals("")) {
                    takeNumberButton.setEnabled(false);
                } else {
                    takeNumberButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //Create the menu using a MenuInflater
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, so show the app settings UI
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void ShowDialog(final List<Student> studentList)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(JoinSessionActivity.this);
        builder.setMessage(getString(R.string.student_message_ready_for_help_question));
        builder.setPositiveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton(getString(R.string.action_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (studentList != null) {
                    Student s = studentList.get(studentList.size() / 2);
                    user.setDateTime(s.getDateTime() - 1);
                    user.setStatus(0);
                    user.setPressNo(true);
                }
            }
        });
        builder.create().show();
    }
}
