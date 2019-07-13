package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.service.codetutor;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.R;

public class ServiceActivityTut extends AppCompatActivity {

    private Button start;
    private Button stop;
    private Button bind;
    private Button unbind;
    private Button random;
    private TextView randomNumber;

    private MyService myService;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_tut);

        start = findViewById(R.id.start_btn_tut);
        stop = findViewById(R.id.stop_btn_tut);
        bind = findViewById(R.id.bind_service_btn_tut);
        unbind = findViewById(R.id.unbind_service_btn_tut);
        random = findViewById(R.id.random_number_btn_tut);
        randomNumber = findViewById(R.id.random_number_text);

        serviceIntent = new Intent(getApplicationContext(),MyService.class);

        start.setOnClickListener(view -> startService(serviceIntent));
        stop.setOnClickListener(view -> stopService(serviceIntent));
        bind.setOnClickListener(view -> bindService());
        unbind.setOnClickListener(view -> unbindService());
        random.setOnClickListener(view -> setRandomNumber());
    }

    private void bindService() {
        if(serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder) iBinder;
                    myService = myServiceBinder.getService();
                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    isServiceBound = false;
                }
            };
        }

        bindService(serviceIntent,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    @SuppressLint("SetTextI18n")
    private void setRandomNumber(){
        if(isServiceBound){
            randomNumber.setText("Random number: "+myService.getRandomNumber());
        }else{
            randomNumber.setText("Service not bound");
        }
    }
}
