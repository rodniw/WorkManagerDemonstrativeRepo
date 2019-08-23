package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.iid.FirebaseInstanceId;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.R;

import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;

public class FirebaseAuthNotifActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "rodni_dev";
    private static final String CHANNEL_NAME = "Rodni developer";
    private static final String CHANNEL_DESC = "Rodni developer Notifications";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_auth_notif);
        textView = findViewById(R.id.firebase_token_text_view);

        //i need notification channel if a device has version greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            //if a channel already created this method does nothing
            manager.createNotificationChannel(channel);
        }

        //this will return a task and i will listening to it
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult().getToken();
                        textView.setText(token);
                    } else {
                        textView.setText(task.getException().toString());
                    }
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
