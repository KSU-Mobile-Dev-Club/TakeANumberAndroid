package club.dev.mobile.ksu.takeanumber.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import club.dev.mobile.ksu.takeanumber.Firebase.Repository;
import club.dev.mobile.ksu.takeanumber.Data.HelpSession;
import club.dev.mobile.ksu.takeanumber.R;
import club.dev.mobile.ksu.takeanumber.Data.Student;
import club.dev.mobile.ksu.takeanumber.ViewModels.HelpSessionViewModel;

public class MainActivity extends AppCompatActivity {

    List<HelpSession> sessionsList = new ArrayList<HelpSession>();

    HelpSessionAdapter adapter;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionsList.add(new HelpSession("Test"));

        listView = findViewById(R.id.HelpSessionListView);

        adapter = new HelpSessionAdapter(this, R.layout.list_item, sessionsList); //Adapter(this, R.layout.list_item, sessionsList)

        listView.setAdapter(adapter);
    }
}
