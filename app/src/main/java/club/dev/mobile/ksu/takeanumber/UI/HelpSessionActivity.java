package club.dev.mobile.ksu.takeanumber.UI;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.Data.StudentAdapter;
import club.dev.mobile.ksu.takeanumber.R;

import java.util.ArrayList;

public class HelpSessionActivity extends AppCompatActivity {

    ArrayList<Student> studentList = new ArrayList<Student>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_session);
        addStudent(new Student("Laurel", 4));
        addStudent(new Student("Stephen", 5));
        addStudent(new Student("Luke", 6));
        addListView();

    }

    void addStudent(Student newStudent){
        studentList.add(newStudent);
    }

    void addListView() {
        StudentAdapter studentAdapter = new StudentAdapter(this, studentList);
        ListView listview = (ListView) findViewById(R.id.help_session_lv);
        listview.setAdapter(studentAdapter);
    }


}
