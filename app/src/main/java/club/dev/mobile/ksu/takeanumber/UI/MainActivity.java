package club.dev.mobile.ksu.takeanumber.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import club.dev.mobile.ksu.takeanumber.Firebase.Repository;
import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.R;
import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.ViewModels.HelpSessionViewModel;

public class MainActivity extends AppCompatActivity {
    private List<HelpSession> mHelpSessions;
    private List<Student> mStudents;
    private HelpSessionViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(HelpSessionViewModel.class);

        mViewModel.getHelpSessions().observe(this, new Observer<List<HelpSession>>() {
            @Override
            public void onChanged(@Nullable List<HelpSession> helpSessions) {
                mHelpSessions = helpSessions;
            }
        });


    }
}