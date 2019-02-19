package club.dev.mobile.ksu.takeanumber;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<HelpSession> mHelpSessions;
    private List<Student> mStudents;
    private Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRepository = Repository.getInstance();

        mRepository.getHelpSessions().observe(this, new Observer<List<HelpSession>>() {
            @Override
            public void onChanged(@Nullable List<HelpSession> helpSessions) {
                mHelpSessions = helpSessions;
            }
        });

        HelpSession session = new HelpSession();
        session.setFirebaseKey("-LZ5MACD6FQw9RGHsCJy");

        mRepository.getStudentQueue(session).observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> students) {
                mStudents = students;
            }
        });

    }
}
