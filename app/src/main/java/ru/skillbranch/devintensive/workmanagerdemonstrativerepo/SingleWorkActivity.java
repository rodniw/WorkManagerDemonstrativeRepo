package ru.skillbranch.devintensive.workmanagerdemonstrativerepo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static ru.skillbranch.devintensive.workmanagerdemonstrativerepo.MyWorker.KEY_OUTPUT_DESC;

public class SingleWorkActivity extends AppCompatActivity {

    public static final String KEY_TASK_DESC = "key_task_desc";

    private TextView textView;

    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new Data.Builder()
                .putString(KEY_TASK_DESC, "data sending")
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(data)
                .build();

        textView = findViewById(R.id.work_status_tv);

        findViewById(R.id.single_work_btn).setOnClickListener(v -> {
            WorkManager.getInstance().enqueue(request);
        });

        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId())
                .observe(this, workInfo -> {
                    if (workInfo != null) {
                        if (workInfo.getState().isFinished()) {
                            textView.append(
                                    "\n Output: \n" +
                                    workInfo.getOutputData().getString(KEY_OUTPUT_DESC));
                        }
                        textView.append("Status: " + workInfo.getState().name());
                    }
                });
    }
}
