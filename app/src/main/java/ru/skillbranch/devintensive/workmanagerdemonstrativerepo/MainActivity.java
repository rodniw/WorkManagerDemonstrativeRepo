package ru.skillbranch.devintensive.workmanagerdemonstrativerepo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.work.OneTimeWorkRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
    }
}
