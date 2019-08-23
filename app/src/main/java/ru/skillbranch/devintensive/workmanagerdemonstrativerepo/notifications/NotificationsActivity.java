package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.R;

import static android.support.v4.app.NotificationCompat.PRIORITY_DEFAULT;

public class NotificationsActivity extends AppCompatActivity {

    /*
    1. Notification Channel >= Oreo
    2. Notification Builder
    3. Notification Manager
     */

    private static final String CHANNEL_ID = "rodni_dev";
    private static final String CHANNEL_NAME = "Rodni developer";
    private static final String CHANNEL_DESC = "Rodni developer Notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //i need notification channel if a device has version greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            //if a channel already created this method does nothing
            manager.createNotificationChannel(channel);
        }

        findViewById(R.id.button_send_simple_notif).setOnClickListener(v -> {
            displayNotification();
        });
    }

    //this method will display Notification
    private void displayNotification() {
        //we need notification builder to set an icon, a title, a desc and a priority
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message_white_24dp)
                .setContentTitle("Notification")
                .setContentText("Test notification")
                .setPriority(PRIORITY_DEFAULT);

        //i will use this object to display the notification
        NotificationManagerCompat notifManager = NotificationManagerCompat.from(this);
        //this method will display the notification
        notifManager.notify(1, builder.build());
    }
}
