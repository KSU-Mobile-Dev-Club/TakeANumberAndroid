package club.dev.mobile.ksu.takeanumber;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<HelpSession> mHelpSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repository = Repository.getInstance();

        repository.getHelpSessions().observe(this, new Observer<List<HelpSession>>() {
            @Override
            public void onChanged(@Nullable List<HelpSession> helpSessions) {
                mHelpSessions = helpSessions;
            }
        });
    }
}
