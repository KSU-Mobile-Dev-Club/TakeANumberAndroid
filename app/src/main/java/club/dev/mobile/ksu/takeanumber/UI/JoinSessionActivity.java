package club.dev.mobile.ksu.takeanumber.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import android.os.Vibrator;

import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.Data.StudentAdapter;
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
        final TextView time = findViewById(R.id.time);

        takeNumberButton.setEnabled(false);
        cancelButton.setEnabled(false);
        enterNameText.setEnabled(true);
        enterTime.setEnabled(true);
        displayQueueLocation.setText("");

        final HelpSession testSession = new HelpSession();
        Intent intent = getIntent();
        String key = intent.getStringExtra("sessionKey");

        testSession.setFirebaseKey(key);



        final StudentQueueViewModel mViewModel = ViewModelProviders.of(this).get(StudentQueueViewModel.class);

        final Observer<List<Student>> queueObserver = new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> studentList) {

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                int totalTime = 0;

                queueLocation = -1;
                if(studentList != null) {
                    for (int i = 0; i < studentList.size(); i++) {
                        totalTime += studentList.get(i).getTime();

                        if (studentList.get(i).equals(user)) {
                            if(studentList.get(i).getStatus() == 1){
                                queueLocation = i + 1;
                                displayQueueLocation.setText("You are ready to be helped!");
                                v.vibrate(1000);
                            }
                            else{
                                queueLocation = i + 1;
                                displayQueueLocation.setText("You are " + queueLocation + " in line");
                            }
                            break;
                        }
                    }
                    if (queueLocation < 0) {
                        displayQueueLocation.setText("You are not in line.");
                        time.setText("Estimated wait time: " + totalTime + " minutes");
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
                if (enterNameText.getText().toString().equals("")) {
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
}
