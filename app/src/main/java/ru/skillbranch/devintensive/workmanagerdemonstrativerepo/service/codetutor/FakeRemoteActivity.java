package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.service.codetutor;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.skillbranch.devintensive.workmanagerdemonstrativerepo.R;

public class FakeRemoteActivity extends AppCompatActivity {

    public static final int GET_RANDOM_NUMBER_FLAG = 0;
    private boolean isBound;
    private int randomNumberValue;

    private Button bind;
    private Button unbind;
    private Button random;
    private TextView randomNumber;

    private Intent serviceIntent;

    Messenger randomNumberRequestMessenger, randomNumberReceiveMessenger;

    class RecieveRandomNumberHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            randomNumberValue = 0;
            switch (msg.what) {
                case GET_RANDOM_NUMBER_FLAG:
                    randomNumberValue = msg.arg1;
                    randomNumber.setText("Random Number: " + randomNumberValue);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    ServiceConnection randomNumberServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            randomNumberRequestMessenger = new Messenger(iBinder);
            randomNumberReceiveMessenger = new Messenger(new RecieveRandomNumberHandler());
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            randomNumberRequestMessenger = null;
            randomNumberReceiveMessenger = null;
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_remote);

        bind = findViewById(R.id.bind_service_btn_tut_remote);
        unbind = findViewById(R.id.unbind_service_btn_tut_remote);
        random = findViewById(R.id.random_number_btn_tut_remote);
        randomNumber = findViewById(R.id.random_number_text_remote);

        //inside setComponent method we need to define our service package and service class from another app
        //and here i passed package names of this app just for the example
        serviceIntent = new Intent().setComponent(new ComponentName(
                "ru.skillbranch.devintensive.workmanagerdemonstrativerepo.service.codetutor",
                "ru.skillbranch.devintensive.workmanagerdemonstrativerepo.service.codetutor.MyService"
        ));

        bind.setOnClickListener(view -> bindToRemoteService());
        unbind.setOnClickListener(view -> unbindToRemoteService());
        random.setOnClickListener(view -> fetchRandomNumber());
    }

    private void bindToRemoteService() {
        bindService(serviceIntent, randomNumberServiceConnection, BIND_AUTO_CREATE);
        Toast.makeText(getApplicationContext(), "bound", Toast.LENGTH_SHORT).show();
    }

    private void unbindToRemoteService() {
        unbindService(randomNumberServiceConnection);
        isBound = false;
        Toast.makeText(this, "unbound", Toast.LENGTH_SHORT).show();
    }

    private void fetchRandomNumber() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        randomNumberServiceConnection = null;
    }
}
