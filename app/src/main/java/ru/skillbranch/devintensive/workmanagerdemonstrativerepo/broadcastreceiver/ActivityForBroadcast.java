package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.R;

public class ActivityForBroadcast extends AppCompatActivity {

    private RecyclerView recycler;
    private TextView text;
    private RecyclerView.LayoutManager manager;
    private BroadcastReceiver myReceiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        text = findViewById(R.id.nothing_broadcast);

        recycler = findViewById(R.id.recycler_broadcast);
        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        recycler.setHasFixedSize(true);

        myReceiver = new MyBroadcast();
        filter = new IntentFilter("ru.skillbranch.devintensive.workmanagerdemonstrativerepo.broadcastreceiver");

        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myReceiver);
    }
}
