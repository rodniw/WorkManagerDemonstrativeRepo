package ru.skillbranch.devintensive.workmanagerdemonstrativerepo.service.codetutor;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int MIN=0;
    private final int MAX=100;

    public static final int GET_COUNT=0;

    private class RandomNumberRequestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_COUNT: Message  messageSendRandomNumber = Message.obtain(null,GET_COUNT);
                    messageSendRandomNumber.arg1=getRandomNumber();
                    try{
                        //will return messanger and i will send my message there
                        msg.replyTo.send(messageSendRandomNumber);
                    }catch (RemoteException e){
                    }
            }
            super.handleMessage(msg);
        }
    }

    class MyServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private Messenger randomNumberMessenger = new Messenger(new RandomNumberRequestHandler());

    private IBinder binder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //to make randomNumberMessenger workable we need to execute this commented piece of code
        //return randomNumberMessenger.getBinder();
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mIsRandomGeneratorOn =true;
        new Thread(this::startRandomNumberGenerator).start();
        Toast.makeText(getApplicationContext(), "onStartCommand", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //stop generator
        stopRandomNumberGenerator();
    }

    private void startRandomNumberGenerator(){
        while (mIsRandomGeneratorOn){
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber = new Random().nextInt(MAX)+MIN;
                }
            }catch (InterruptedException e){
            }

        }
    }

    private void stopRandomNumberGenerator(){
        mIsRandomGeneratorOn =false;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public int getRandomNumber(){
        return mRandomNumber;
    }
}
