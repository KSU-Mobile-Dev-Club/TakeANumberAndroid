package club.dev.mobile.ksu.takeanumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JoinSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_session);

        final Button b = findViewById(R.id.takeNumber);
        final Button c = findViewById(R.id.cancel);

        b.setEnabled(true);
        c.setEnabled(false);

        b.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                b.setEnabled(false);
                c.setEnabled(true);
            }
        });

        c.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                c.setEnabled(false);
                b.setEnabled(true);
            }
        });

    }
}
