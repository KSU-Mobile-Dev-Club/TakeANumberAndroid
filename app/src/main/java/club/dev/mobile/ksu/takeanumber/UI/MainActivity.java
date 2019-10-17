package club.dev.mobile.ksu.takeanumber.UI;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.HelpSessionAdapter;
import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.R;
import club.dev.mobile.ksu.takeanumber.ViewModels.HelpSessionViewModel;

public class MainActivity extends AppCompatActivity {

    private List<HelpSession> sessionsList = new ArrayList<HelpSession>();
    private HelpSessionAdapter adapter;
    private ListView listView;
    private HelpSessionViewModel viewModel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        viewModel = ViewModelProviders.of(this).get(HelpSessionViewModel.class);

        viewModel.getHelpSessions().observe(this, new Observer<List<HelpSession>>() {
            @Override
            public void onChanged(@Nullable List<HelpSession> helpSessions) {
                adapter.setHelpSessionsList(helpSessions);
                sessionsList = helpSessions;
            }
        });

        listView = findViewById(R.id.HelpSessionListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String[] colors = {getString(R.string.student), getString(R.string.ta)};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.ta_student_question));
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0)
                        {
                            Intent intent = new Intent(MainActivity.this, JoinSessionActivity.class);
                            intent.putExtra("sessionKey", sessionsList.get(position).getFirebaseKey());
                            MainActivity.this.startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(MainActivity.this, HelpSessionActivity.class);
                            intent.putExtra("sessionKey", sessionsList.get(position).getFirebaseKey());
                            MainActivity.this.startActivity(intent);
                        }
                    }
                });
                builder.create().show();
            }
        });

        // Set up floating action button
        FloatingActionButton addSessionButton = findViewById(R.id.AddSessionButton);
        addSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign in user anonymously and bring them to the page to add a help session
                // as a TA.
            }
        });

        adapter = new HelpSessionAdapter(this, R.layout.help_session_item);

        listView.setAdapter(adapter);
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
