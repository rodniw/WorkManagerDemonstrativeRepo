package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.workmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.R;

import static ru.skillbranch.devintensive.workmanagerdemonstrativerepo.workmanager.SingleWorkActivity.KEY_TASK_DESC;

public class MyWorker extends Worker {

    public static final String KEY_OUTPUT_DESC = "key_output_desc";

    private Data dataInput;
    private Data dataOutput;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        dataInput = getInputData();
        String desc = dataInput.getString(KEY_TASK_DESC);
        displayNotification("work", desc);

        dataOutput = new Data.Builder()
                .putString(KEY_OUTPUT_DESC, "Task finished successfully")
                .build();

        setOutputData(dataOutput);

        return Result.SUCCESS;
    }

    private void displayNotification(String task, String description) {

        NotificationManager manager =
                (NotificationManager) getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "rodniw",
                    "rodniw",
                    NotificationManager.IMPORTANCE_DEFAULT);

            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "rodniw")
                .setContentTitle(task)
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_launcher);

        manager.notify(1, builder.build());
    }

}
