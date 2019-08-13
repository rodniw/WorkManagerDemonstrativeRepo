package ru.skillbranch.devintensive.workmanagerdemonstrativerepo;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class SingleWorkActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();

        textView = findViewById(R.id.work_status_tv);

        findViewById(R.id.single_work_btn).setOnClickListener(v -> {
            WorkManager.getInstance().enqueue(request);
        });

        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        textView.append("Status: " + workInfo.getState().name());
                    }
                });
    }
}
