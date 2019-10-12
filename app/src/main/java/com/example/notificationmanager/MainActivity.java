package com.example.notificationmanager;

import android.app.Notification;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;
    private SeekBar seekBar;
    private TextView textViewSeekbar;
    private Button btnStart;
    private Button btnPause;
    private LinearLayout layoutStart;
    private ArrayList <String> arrayList1;
    private Constanta constanta;
    private ListView listView;
    private ArrayList <MyDataModel> list;
    private MyArrayAdapter adapter;
    private TextView tvTest;
    private LinearLayout mainLayout;
    private VideoView videoView;
    private FrameLayout frameLayout;
    private ImageView imageViewStartPlay;
private  MyTask mt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void videoViewStart() {
        frameLayout = findViewById(R.id.frame_layout_main_activity);
        frameLayout.isClickable();
        frameLayout.setOnClickListener(this);

        videoView = findViewById(R.id.video_view);
        videoView.setMediaController(null);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        // videoView.setOnClickListener(this);
    }

    private void initView() {
        notificationManager = NotificationManagerCompat.from(this);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextMessage = findViewById(R.id.edit_text_message);
        tvTest = findViewById(R.id.tv_test_text);
        btnStart = findViewById(R.id.btn_start_main_activity);
        btnPause = findViewById(R.id.btn_pause_main_activity);
        layoutStart = findViewById(R.id.layout_start_main_activit);
        mainLayout = findViewById(R.id.mainLayout);
        // btnStart.setOnClickListener(this);
        imageViewStartPlay = findViewById(R.id.iv_spart_play_activity_main);
        imageViewStartPlay.setOnClickListener(this);

        //initSeekBarView();
        //initAnimationBackground();
        videoViewStart();
    }


    private void initAnimationBackground() {
        AnimationDrawable animationDrawable = (AnimationDrawable) btnStart
                .getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();


    }

    private void initSeekBarView() {
        textViewSeekbar = findViewById(R.id.txtViewseek);
        seekBar = findViewById(R.id.seekBar2);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textViewSeekbar.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void sendOnChannel1(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.prague, options);

        Notification notification = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)

                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))

                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_SOCIAL)
                .build();

        notificationManager.notify(1, notification);
    }

    private void RunNotification(View v) {
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        Notification notification = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getWordsFromDBToNotification())
                .setContentText("text text")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("sub title this"))
                .build();
        notificationManager.notify(3, notification);

    }

    public void sendOnChannel2(View v) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, "channel2")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }

    @Override
    public void onClick(View v) {
        mt = new MyTask();
        mt.execute();
    }


    private void startvidos() {
        videoView.start();
        // count();
        // RunNotification(v);
        imageViewStartPlay.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);

    }

    public void sleap(int timeSec) {
        try {
            Thread.sleep(timeSec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void count() {
        int nmb;

        for (nmb = 1; nmb <= 100; nmb++) {
            tvTest.setText(nmb + "j");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//проблема в тому що вибиває тут помилку і не хоче запускати цикл
        }
        // Toast.makeText(this, "yyyy", Toast.LENGTH_SHORT).show();

    }

    private void start() {
        //sleap(2);
        layoutStart.setVisibility(View.VISIBLE);
        //               connectToDB();
        //      new GetDataTask().execute();
        //              visibilityForListView();
        //              unvisibilityForStartbutton();
    }

    private void unvisibilityForStartbutton() {
        btnStart.setVisibility(View.GONE);
        btnPause.setVisibility(View.GONE);
    }

    private void visibilityForListView() {
        listView.setVisibility(View.VISIBLE);
        float weightLayout = 1.0f;
        listView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, weightLayout));

    }

    public void connectToDB() {
        list = new ArrayList <>();
        adapter = new MyArrayAdapter(this, list);
        listView = (ListView) findViewById(R.id.list_of_words_main_activity);
        listView.setAdapter(adapter);
        Toast toast = Toast.makeText(getApplicationContext(), "Click on FloatingActionButton to Load JSON", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    class GetDataTask extends AsyncTask <Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            x = list.size();

            if (x == 0)
                jIndex = 0;
            else
                jIndex = x;

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Hey Wait Please..." + x);
            dialog.setMessage("I am getting your JSON");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {


            JSONObject jsonObject = JSONParser.getDataFromWeb();

            try {

                if (jsonObject != null) {

                    if (jsonObject.length() > 0) {

                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);


                        int lenArray = array.length();
                        if (lenArray > 0) {
                            for (; jIndex < lenArray; jIndex++) {


                                MyDataModel model = new MyDataModel();


                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString(Keys.KEY_NAME);
                                String country = innerObject.getString(Keys.KEY_COUNTRY);

                                model.setName(name);
                                model.setCountry(country);
//                                arrayList1.add(name + " - " + country);

                                list.add(model);

                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if (list.size() > 0) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private String getWordsFromDBToNotification() {
        int randomNum1 = getRandomNumberInRange(0, 2999);
        int randomNum2 = getRandomNumberInRange(0, 2999);
        int randomNum3 = getRandomNumberInRange(0, 2999);

        arrayList1 = new ArrayList <>();



       /* arrayList1.add(0,"s2222df");
        arrayList1.add(1,"sdf");
        arrayList1.add(2,"sdf333");
        arrayList1.add(3,"sdf");
        arrayList1.add(4,"sdf");*/

        String string1 = "dsfsdf";//arrayList1.get(2);
        String string2 = " dsfsf";//arrayList1.get(3);
        String string3 = " l";//arrayList1.get(4);
        String res = string1 + string2 + string3;

        return res;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    class MyTask extends AsyncTask <Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startvidos();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.MILLISECONDS.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            frameLayout.setVisibility(View.GONE);
            layoutStart.setVisibility(View.VISIBLE);


        }
    }

}
