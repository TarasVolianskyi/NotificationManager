package com.example.notificationmanager;

import android.util.Log;

public class ThreadRun extends Thread {

    MainActivity mainActivity = new MainActivity();
    private static final String TAG = "ThreadRun";
    int seconds;

    public ThreadRun(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        //   while (true) {
        for (int i = 0; i < seconds; i++) {
            try {
                // mainActivity.runNotification();
                // mainActivity.textViewSeekbar.setText(i+"fd");
                Thread.sleep(3000);
                Log.d(TAG, "run: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

      /*  for (int i = 0; i < seconds; i++) {
            //Toast.makeText(MainActivity.this, "444333", Toast.LENGTH_SHORT).show();
            try {
                // MainActivity     textView.setText(i+"");
                Thread.sleep(1000);
                Log.d(TAG, "run: " + 332);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

       */
    }
}
