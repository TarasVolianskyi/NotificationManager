package com.example.notificationmanager;

import android.util.Log;

public class ThreadRun extends Thread {
    MainActivity mainActivity = new MainActivity();

    public Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                while (true) {

                    int time = mainActivity.getPeriodOfTimeInSeconds();
                    sleep(time);
                    Log.d("lg", 3 + "");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

}
