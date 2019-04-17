package club.dev.mobile.ksu.takeanumber.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import club.dev.mobile.ksu.takeanumber.Data.HelpSessionAdapter;
import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.R;
import club.dev.mobile.ksu.takeanumber.ViewModels.HelpSessionViewModel;

public class MainActivity extends AppCompatActivity {

    List<HelpSession> sessionsList = new ArrayList<HelpSession>();
    HelpSessionAdapter adapter;
    ListView listView;
    HelpSessionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                String[] colors = {"Student", "TA"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Are you a TA or a student?");
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

        adapter = new HelpSessionAdapter(this, R.layout.list_item);

        listView.setAdapter(adapter);
    }
}
