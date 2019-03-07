package club.dev.mobile.ksu.takeanumber.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Queue;

import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.Data.StudentAdapter;
import club.dev.mobile.ksu.takeanumber.R;

import java.util.ArrayList;
import java.util.List;

public class HelpSessionActivity extends AppCompatActivity {

    //Queue<Student> studentList = new Queue<Student>();
    ArrayList<Student> studentList = new ArrayList<Student>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //ArrayAdapter<Students> itemsAdapter = new ArrayAdapter<Students>(this, android.R.layout.student_view , items);

    void addStudent(Student newStudent){
        studentList.add(newStudent);
    }

    void addListView(String name , long dateTime , int status) {

        StudentAdapter studentAdapter = new StudentAdapter(this, studentList);
        ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(studentAdapter);
    }


}
