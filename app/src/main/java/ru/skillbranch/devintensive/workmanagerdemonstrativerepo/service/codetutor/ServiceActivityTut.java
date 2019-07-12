package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.service.codetutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.R;

public class ServiceActivityTut extends AppCompatActivity {

    private Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_tut);

        start = findViewById(R.id.start_btn_tut);
        stop = findViewById(R.id.stop_btn_tut);

        start.setOnClickListener(view -> startService(new Intent(this, MyService.class)));
        stop.setOnClickListener(view -> stopService(new Intent(this, MyService.class)));
    }
}
