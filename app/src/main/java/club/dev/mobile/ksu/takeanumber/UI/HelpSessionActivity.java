package club.dev.mobile.ksu.takeanumber.UI;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.Data.StudentAdapter;
import club.dev.mobile.ksu.takeanumber.R;
import club.dev.mobile.ksu.takeanumber.ViewModels.HelpSessionViewModel;
import club.dev.mobile.ksu.takeanumber.ViewModels.StudentQueueViewModel;

import java.util.ArrayList;
import java.util.List;

public class HelpSessionActivity extends AppCompatActivity {

    List<Student> studentList = new ArrayList<Student>();
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
        adapter= new StudentAdapter(this);
        ListView listview = findViewById(R.id.help_session_lv);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Student student = studentList.get(position);
                if(student.getStatus() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HelpSessionActivity.this);
                    builder.setMessage("Are You Sure?");
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            student.setStatus(1);
                            viewModel.updateStudent(sessionKey, student);
                        }
                    });
                    builder.create().show();
                }
                else if(student.getStatus() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(HelpSessionActivity.this);
                        builder.setMessage("Are You Sure?");
                        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
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




}
