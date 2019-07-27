package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.service.MusicService;

public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type", 1);
        switch (type) {
            case 0:
                context.startService(new Intent(context, MusicService.class));
                break;
        }
    }
}
