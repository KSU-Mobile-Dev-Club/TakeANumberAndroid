package club.dev.mobile.ksu.takeanumber.UI;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    ArrayList<Student> studentList = new ArrayList<Student>();
    StudentQueueViewModel viewModel;
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_session);

        addListView();

        viewModel = ViewModelProviders.of(this).get(StudentQueueViewModel.class);
        viewModel.getStudentQueue("-LZ5MACD6FQw9RGHsCJy").observe(
                this, new Observer<List<Student>>() {
                    @Override
                    public void onChanged(@Nullable List<Student> students) {
                        adapter.setStudentList(students);
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
    }


}
