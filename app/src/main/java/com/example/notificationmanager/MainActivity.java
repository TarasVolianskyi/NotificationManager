package com.example.notificationmanager;

import android.app.Notification;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;
    private SeekBar seekBar;
    public TextView textViewSeekbar;
    private Button btnPause;
    private LinearLayout layoutStartDialogView;
    private ArrayList<String> arrayList1;
    private Constanta constanta;
    private ListView listView;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;
    private TextView tvTest;
    private LinearLayout mainLayout;
    private VideoView videoView;
    private FrameLayout frameLayoutWithVideoInside;
    private ImageView imageViewStartPlay;
    private ImageView imageViewStartPlay2;
    private MyTask mt;
    private Button btnSecondStart;
    private TextView tvPlusNum;
    private TextView tvMinusNum;
    private int numberOfWords = 3;
    private String language = "EN";
    private int periodOfTime = 3;
    public int periodOfTimeInSeconds = 1000;

    //  private int periodOfTimeInSeconds = 900000;
    private TextView tvNunberOfWords;
    private ImageView imageViewFlagFR;
    private ImageView imageViewFlagEN;
    private ImageView imageViewFlagDE;
    private ImageView imageViewFlagES;
    private List<Integer> listOfPeriodTime;
    private List<String> finalListOfWords;
    private BaseOfWords myBaseOfWords;
    private Animation animation;
    private CountDownTimer cTimer = null;
    private Thread t;
    private ThreadRun threadRun;
    private LinearLayout linearLayoutLL;
    private View viewMainAct;
    private View viewForVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void videoViewStart() {

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
        btnPause = findViewById(R.id.btn_pause_main_activity);
        btnSecondStart = findViewById(R.id.btn_second_start);
        btnSecondStart.setOnClickListener(this);
        tvMinusNum = findViewById(R.id.tv_minus_main_activity);
        tvPlusNum = findViewById(R.id.tv_plus_main_activity);
        layoutStartDialogView = findViewById(R.id.layout_start_main_activit);
        mainLayout = findViewById(R.id.mainLayout);

        frameLayoutWithVideoInside = findViewById(R.id.frame_layout_main_activity);
        frameLayoutWithVideoInside.isClickable();
        frameLayoutWithVideoInside.setOnClickListener(this);
        // btnStart.setOnClickListener(this);

        imageViewStartPlay = findViewById(R.id.iv_spart_play_activity_main);
        imageViewStartPlay.setOnClickListener(this);
        imageViewStartPlay2 = findViewById(R.id.iv_spart_play2_activity_main);
        imageViewStartPlay2.setOnClickListener(this);
        tvNunberOfWords = findViewById(R.id.tv_num_of_words_main_activity);
        tvMinusNum.setOnClickListener(this);
        tvPlusNum.setOnClickListener(this);
        myBaseOfWords = new BaseOfWords();
        linearLayoutLL = findViewById(R.id.ll_spart_play_activity_main);
        linearLayoutLL.setOnClickListener(this);
        viewMainAct = findViewById(R.id.view_main_activity);
        //  viewForVisibility = findViewById(R.id.view_forvisib);
        //initSeekBarView();
        //initAnimationBackground();
        videoViewStart();
        initSeekBarView();
        initFlagImages();
        adMobView();

    }

    private void animation() {
        animation = new TranslateAnimation(0, 0, 0, 1050 / 2);
        animation.setDuration(1000);
        animation.setFillAfter(true);
    imageViewStartPlay.startAnimation(animation);
        frameLayoutWithVideoInside.startAnimation(animation);


      //  linearLayoutLL.startAnimation(animation);

    }
    private void animation0() {
        animation = new TranslateAnimation(0, 0, 0, -1050 / 2);
        animation.setDuration(1000);
        animation.setFillAfter(true);
    //   imageViewStartPlay.startAnimation(animation);
     //   frameLayoutWithVideoInside.startAnimation(animation);


      linearLayoutLL.startAnimation(animation);

    }


    private void animationBack() {
        animation = new TranslateAnimation(0, 0, 0, -1050 / 5);
        animation.setDuration(1000);
        animation.setFillAfter(true);
      imageViewStartPlay2.startAnimation(animation);
        imageViewStartPlay.startAnimation(animation);
        frameLayoutWithVideoInside.startAnimation(animation);


       // linearLayoutLL.startAnimation(animation);
    }

    private void adMobView() {
        MobileAds.initialize(this, "ca-app-pub-3623739700338204~6535393075");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void initPeriodOfTime() {
        Constanta myConstanta = new Constanta();
        listOfPeriodTime = new ArrayList<>();
        listOfPeriodTime.add(myConstanta.getMin15());
        listOfPeriodTime.add(myConstanta.getMin20());
        listOfPeriodTime.add(myConstanta.getMin30());
        listOfPeriodTime.add(myConstanta.getMin60());
        listOfPeriodTime.add(myConstanta.getMin90());
        listOfPeriodTime.add(myConstanta.getMin120());

    }

    private void initFlagImages() {
        imageViewFlagDE = findViewById(R.id.iv_de_language_main_activity);
        imageViewFlagEN = findViewById(R.id.iv_en_language_main_activity);
        imageViewFlagES = findViewById(R.id.iv_es_language_main_activity);
        imageViewFlagFR = findViewById(R.id.iv_fr_language_main_activity);
        imageViewFlagDE.setOnClickListener(this);
        imageViewFlagEN.setOnClickListener(this);
        imageViewFlagES.setOnClickListener(this);
        imageViewFlagFR.setOnClickListener(this);
    }

    private int setPeriodOfTime(int positionOsSeekBar) {
        initPeriodOfTime();
        return listOfPeriodTime.get(positionOsSeekBar);
    }

    private void initAnimationBackground() {
        AnimationDrawable animationDrawable = (AnimationDrawable) layoutStartDialogView
                .getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

    private void initSeekBarView() {
        textViewSeekbar = findViewById(R.id.txtViewseek);
        textViewSeekbar.setText("You will get a message every 30 minutes");
        seekBar = findViewById(R.id.seekBar2);
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.neonBlue), PorterDuff.Mode.SRC_ATOP);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekbar.setText("You will get a message every " + setPeriodOfTime(progress) / 60 + " minutes");
                periodOfTime = progress;
                periodOfTimeInSeconds = setPeriodOfTime(progress) * 10;
                //  periodOfTimeInSeconds=setPeriodOfTime(progress) ;
                Toast.makeText(MainActivity.this, "" + progress, Toast.LENGTH_SHORT).show();
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

    public void runNotification() {
        // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        Notification notification = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New words for YOU")
                .setContentText("text text" + createTextForFinalNotification())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(createTextForFinalNotification()))
                .build();
        notificationManager.notify(3, notification);
    }

    private String createTextForFinalNotification() {
        String localRes = " Amount of words - " + numberOfWords + "\n Period of time - " + periodOfTime + "\n Language - " + language;
        finalListOfWords = new ArrayList<>();
        for (int i = 0; i < numberOfWords; i++) {
            if (language.equals("EN")) {
                myBaseOfWords.baseOfLocalWordsENtoRU();
                finalListOfWords.add(myBaseOfWords.listOfWordsENtoRU.get(getRandomNumberInRange(0, myBaseOfWords.listOfWordsENtoRU.size() - 1)));
            } else if (language.equals("DE")) {
                myBaseOfWords.baseOfLocalWordsDEtoRU();

                finalListOfWords.add(myBaseOfWords.listOfWordsDEtoRU.get(getRandomNumberInRange(0, myBaseOfWords.listOfWordsDEtoRU.size() - 1)));
            } else if (language.equals("FR")) {
                myBaseOfWords.baseOfLocalWordsFRtoRU();

                finalListOfWords.add(myBaseOfWords.listOfWordsFRtoRU.get(getRandomNumberInRange(0, myBaseOfWords.listOfWordsFRtoRU.size() - 1)));
            } else if (language.equals("ES")) {
                myBaseOfWords.baseOfLocalWordsEStoRU();

                finalListOfWords.add(myBaseOfWords.listOfWordsEStoRU.get(getRandomNumberInRange(0, myBaseOfWords.listOfWordsEStoRU.size() - 1)));
            }
        }
        String result = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            result = String.join("\n", finalListOfWords);
        }
        return result;
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_spart_play_activity_main:
                initAnimationBackground();
                animation();

            //    animation0();
                mt = new MyTask();
                mt.execute();

                break;

            case R.id.iv_spart_play2_activity_main:
                Toast.makeText(this, "hellknmllooo", Toast.LENGTH_SHORT).show();

                startvidos();
                /*
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                 */
                animation0();
                animationBack();


                viewMainAct.setVisibility(View.VISIBLE);
                viewMainAct.setLayoutParams(new LinearLayout.LayoutParams(20, 1050));
                layoutStartDialogView.setVisibility(View.GONE);

                imageViewStartPlay2.setVisibility(View.GONE);
                imageViewStartPlay.setVisibility(View.GONE);
                frameLayoutWithVideoInside.setVisibility(View.VISIBLE);

                break;


            case R.id.btn_second_start:
                //   runNotification();
                //  startTimerCountDown();
                doWhileMethodForStart();
                break;
            case R.id.tv_minus_main_activity:
                minusNumberOfWords();
                // testDoWhile();
                break;
            case R.id.tv_plus_main_activity:
                plusNumberOfWords();
                // finishTimer();
                break;
            case R.id.iv_de_language_main_activity:
                changeLanguage("DE");
                break;
            case R.id.iv_fr_language_main_activity:
                changeLanguage("FR");
                break;
            case R.id.iv_en_language_main_activity:
                changeLanguage("EN");
                break;
            case R.id.iv_es_language_main_activity:
                changeLanguage("ES");
                break;
        }
    }

    private void startvidos() {
        videoView.start();
        videoView.setVisibility(View.VISIBLE);
        frameLayoutWithVideoInside.setVisibility(View.VISIBLE);
    }

    private void testDoWhile() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("loogTest", i + "");
            runNotification();
        }


    }


    private void visibilityForListView() {
        listView.setVisibility(View.VISIBLE);
        float weightLayout = 1.0f;
        listView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, weightLayout));
    }

    public void connectToDB() {
        list = new ArrayList<>();
        adapter = new MyArrayAdapter(this, list);
        listView = (ListView) findViewById(R.id.list_of_words_main_activity);
        listView.setAdapter(adapter);
        Toast toast = Toast.makeText(getApplicationContext(), "Click on FloatingActionButton to Load JSON", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public int getPeriodOfTimeInSeconds() {
        return periodOfTimeInSeconds;
    }

    class GetDataTask extends AsyncTask<Void, Void, Void> {
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
        arrayList1 = new ArrayList<>();


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

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //   viewMainAct.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
            viewMainAct.setVisibility(View.GONE);
            //  linearLayoutLL.setVisibility(View.VISIBLE);
            imageViewStartPlay.setVisibility(View.GONE);
            imageViewStartPlay2.setVisibility(View.VISIBLE);
            // linearLayoutLL.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
            //frameLayoutWithVideoInside.setVisibility(View.GONE);
            layoutStartDialogView.setVisibility(View.VISIBLE);

        }
    }

    private void plusNumberOfWords() {
        if (numberOfWords == 10) {
            Toast.makeText(this, "The maximum number of words per one notification is 10 words", Toast.LENGTH_SHORT).show();
        } else if (numberOfWords < 10) {
            numberOfWords = numberOfWords + 1;
            setTextForNumberOfWords(numberOfWords);
        }
    }

    private void minusNumberOfWords() {
        if (numberOfWords == 1) {
            Toast.makeText(this, "The minimum number of words per one notification is 1 word", Toast.LENGTH_SHORT).show();
        } else if (numberOfWords > 1) {
            numberOfWords = numberOfWords - 1;
            setTextForNumberOfWords(numberOfWords);
        }
    }

    private void changeLanguage(String langLocal) {
        language = langLocal;
        Toast.makeText(this, "YOU CHOOSED LANGUAGE - " + langLocal, Toast.LENGTH_SHORT).show();
    }

    private void setTextForNumberOfWords(int number) {
        String textWord = "";
        if (number == 1) {
            textWord = " word";
        } else if (number > 1) {
            textWord = " words";
        }
        tvNunberOfWords.setText(numberOfWords + textWord);


    }

    private void doWhileMethodForStart() {
        // startTimerCountDown();
        runNotification();
        threadRun = new ThreadRun(10);
        new Thread(threadRun).start();
    }

    public void show2Toast() {
        Toast.makeText(this, "44334", Toast.LENGTH_SHORT).show();
    }

    private void startTimerCountDown() {
        cTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                textViewSeekbar.setText("seconds remaining: " + millisUntilFinished / 1000);
                //   Toast.makeText(MainActivity.this, "seconds remaining: " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();

            }

            public void onFinish() {
                runNotification();
            }
        }.start();
    }

    private void finishTimer() {
        cTimer.cancel();
        //Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    }

}
