package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.service;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
