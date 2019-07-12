package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.service;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.R;

public class ServiceActivity extends AppCompatActivity {

    private Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        start = findViewById(R.id.start_btn);
        stop = findViewById(R.id.stop_btn);

        start.setOnClickListener(view -> startService(new Intent(getApplicationContext(), MusicService.class)));
        stop.setOnClickListener(view -> stopService(new Intent(getApplicationContext(), MusicService.class)));
    }
}
