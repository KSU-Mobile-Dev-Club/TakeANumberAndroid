package club.dev.mobile.ksu.takeanumber.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.Data.StudentAdapter;
import club.dev.mobile.ksu.takeanumber.R;
import club.dev.mobile.ksu.takeanumber.ViewModels.StudentQueueViewModel;

public class TaHelpSessionActivity extends AppCompatActivity {

    List<Student> studentList = new ArrayList<>();
    StudentQueueViewModel viewModel;
    StudentAdapter adapter;
    String sessionKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_session);

        addListView();

        sessionKey = getIntent().getStringExtra("sessionKey");

        viewModel = ViewModelProviders.of(this).get(StudentQueueViewModel.class);
        viewModel.getStudentQueue(sessionKey).observe(
                this, new Observer<List<Student>>() {
                    @Override
                    public void onChanged(@Nullable List<Student> students) {
                        adapter.setStudentList(students);
                        studentList = students;
                    }
                });
    }

    void addStudent(Student newStudent){
        studentList.add(newStudent);
    }

    void addListView() {
        adapter = new StudentAdapter(this);
        ListView listview = findViewById(R.id.help_session_lv);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final Student student = studentList.get(position);
                if(student.getStatus() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TaHelpSessionActivity.this);
                    builder.setMessage(getString(R.string.ta_is_helping_question));
                    builder.setPositiveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            student.setStatus(1);
                            viewModel.updateStudent(sessionKey, student);
                        }
                    });
                    builder.create().show();
                }
                else if(student.getStatus() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TaHelpSessionActivity.this);
                        builder.setMessage(getString(R.string.ta_done_helping_question));
                        builder.setPositiveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                student.setStatus(2);
                                viewModel.removeStudent(sessionKey, student.getFirebaseKey());

                            }

                        });
                    builder.create().show();
                }
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
}
