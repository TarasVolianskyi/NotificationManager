package com.example.notificationmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Служба создана",
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
        myToast();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();
        myToast();
    }

    ///////
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        myToast();

        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void myToast() {

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(3000);
              //  Toast.makeText(this, "service  ", Toast.LENGTH_SHORT).show();
                Log.d("loggd",""+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Toast.makeText(this, "service finish 678", Toast.LENGTH_SHORT).show();

    }
}
