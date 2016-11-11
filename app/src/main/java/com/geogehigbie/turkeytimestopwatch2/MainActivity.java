package com.geogehigbie.turkeytimestopwatch2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean isRunning = false;
    private boolean isClickedStart = false;
    private boolean wasRunning;

    private ArrayList<String> savedStoppedTimeList = new ArrayList<String>();
    private ArrayList<TextView> savedTimeArrayList = new ArrayList<TextView>();


    private int clickCounter1 = 0;
    private int clickCounter2 = 0;
    private int clickCounter3 = 0;
    private int clickCounter4 = 0;


    private String tapInstructions = "Tap2Save";
    private String timeString;
    private String savedTimeToSend1;
    private String savedTimeToSend2;
    private String savedTimeToSend3;
    private String savedTimeToSend4;

    private long startTime;
    private long timeInMilliSeconds;
    private int hundredths;
    private int secs;
    private int mins;

    private TextView timeView;

    private Handler handler1 = new Handler();
    private Runnable runnable1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeView = (TextView) findViewById(R.id.time_view);
        timeView.setVisibility(View.VISIBLE);


        if (savedInstanceState != null) {
            startTime = savedInstanceState.getLong("startTime");
            timeInMilliSeconds = savedInstanceState.getLong("timeInMilliSeconds", timeInMilliSeconds);
            secs = savedInstanceState.getInt("secs");
            hundredths = savedInstanceState.getInt("hundredths");
            mins = savedInstanceState.getInt("mins");
            isRunning = savedInstanceState.getBoolean("isRunning");
            timeString = savedInstanceState.getString("timeString");
            isClickedStart = savedInstanceState.getBoolean("isClickedStart");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }


        if (isRunning) {
            runTimer();
            timeView.setText(timeString);
            startTime = savedInstanceState.getLong("startTime");
        }

        if (wasRunning) {
            timeView.setText(timeString);
            startTime = savedInstanceState.getLong("startTime");
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("startTime", startTime);
        savedInstanceState.putLong("timeInMilliSeconds", timeInMilliSeconds);
        savedInstanceState.putInt("secs", secs);
        savedInstanceState.putInt("hundredths", hundredths);
        savedInstanceState.putInt("mins", mins);
        savedInstanceState.putBoolean("isRunning", isRunning);
        savedInstanceState.putString("timeString", timeString);
        savedInstanceState.putBoolean("isClickedStart", isClickedStart);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }


    public void onClickStart(View view) {

        playSoundEffects();
        handler1.removeCallbacks(runnable1);
        isRunning = true;

        if (isClickedStart == false) {
            startTime = SystemClock.elapsedRealtime();
            runTimer();
        } else {
            startTime = SystemClock.elapsedRealtime() - timeInMilliSeconds;
            runTimer();
        }
        isClickedStart = true;

    }


    public void onClickStop(View view) {
        playSoundEffects();
        isRunning = false;
        isClickedStart = true;
        wasRunning = true;

    }

    public void onClickReset(View view) {
        handler1.removeCallbacks(runnable1);
        playSoundEffects();
        isRunning = false;
        wasRunning = false;
        clearTime();
        isClickedStart = false;

    }


    public void playSoundEffects() {

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click_on_sound);
        mediaPlayer.start();

    }

    public void playTurkeyGobble() {

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.turkey_gobble);
        mediaPlayer.start();
    }


    private void runTimer() {

        if (isRunning) {

            handler1 = new Handler();
            runnable1 = new Runnable() {
                @Override
                public void run() {

                    Log.d("HANDLER", "run: HANDLER IS RUNNING ");
                    runTimer();
                    timeInMilliSeconds = SystemClock.elapsedRealtime() - startTime;

                    hundredths = (int) (timeInMilliSeconds / 10) % 100;
                    secs = (int) ((timeInMilliSeconds) / 1000) % 60;
                    mins = (int) (timeInMilliSeconds) / 60000;


                    timeString = String.format("%02d:%02d:%02d", mins, secs, hundredths);
                    timeView.setText(timeString);

                }

            };
            handler1.postDelayed(runnable1, 1);
        }
    }


    public void clearTime() {
        hundredths = 0;
        secs = 0;
        mins = 0;

        TextView timeView = (TextView) findViewById(R.id.time_view);

        timeString = String.format("%02d:%02d:%02d", mins, secs, hundredths);
        timeView.setText(timeString);

    }


    public void onClickSave1(View view) {


        clickCounter1++;
        TextView savedTime1 = (TextView) findViewById(R.id.saved1);
        ImageView turkeyIcon1 = (ImageView) findViewById(R.id.turkeyIcon1);


        if (clickCounter1 % 2 == 0) {
            multipleAnimation(turkeyIcon1, clickCounter1, savedTime1);
            savedTimeToSend1 = savedTime1.getText().toString();

        } else {
            mainTimeAnnimation();


            TextView timeView = (TextView) findViewById(R.id.time_view); //This is the source of the time.
            String lastSavedTime = timeView.getText().toString();

            savedTime1.setText(lastSavedTime);
        }


    }

    public void onClickSave2(View view) {

        clickCounter2++;
        TextView savedTime2 = (TextView) findViewById(R.id.saved2);
        ImageView turkeyIcon2 = (ImageView) findViewById(R.id.turkeyIcon2);


        if (clickCounter2 % 2 == 0) {
            multipleAnimation(turkeyIcon2, clickCounter2, savedTime2);
            savedTimeToSend2 = savedTime2.getText().toString();


        } else {
            mainTimeAnnimation();


            TextView timeView = (TextView) findViewById(R.id.time_view); //This is the source of the time.
            String lastSavedTime = timeView.getText().toString();

            savedTime2.setText(lastSavedTime);
        }

    }

    public void onClickSave3(View view) {

        clickCounter3++;
        TextView savedTime3 = (TextView) findViewById(R.id.saved3);
        ImageView turkeyIcon3 = (ImageView) findViewById(R.id.turkeyIcon3);


        if (clickCounter3 % 2 == 0) {
            multipleAnimation(turkeyIcon3, clickCounter3, savedTime3);
            savedTimeToSend3 = savedTime3.getText().toString();


        } else {

            mainTimeAnnimation();


            TextView timeView = (TextView) findViewById(R.id.time_view); //This is the source of the time.
            String lastSavedTime = timeView.getText().toString();

            savedTime3.setText(lastSavedTime);

        }


    }

    public void onClickSave4(View view) {

        clickCounter4++;
        TextView savedTime4 = (TextView) findViewById(R.id.saved4);
        ImageView turkeyIcon4 = (ImageView) findViewById(R.id.turkeyIcon4);


        if (clickCounter4 % 2 == 0) {
            multipleAnimation(turkeyIcon4, clickCounter4, savedTime4);
            savedTimeToSend4 = savedTime4.getText().toString();


        } else {
            mainTimeAnnimation();

            TextView timeView = (TextView) findViewById(R.id.time_view); //This is the source of the time.
            String lastSavedTime = timeView.getText().toString();

            savedTime4.setText(lastSavedTime);
        }


    }


    public void sendEmail() {
        String savedTime = "00.00.00";

        if (clickCounter1 == 2) {
            savedTime = savedTimeToSend1;
            clickCounter1 = 0;
        }
        if (clickCounter2 == 2) {
            savedTime = savedTimeToSend2;
            clickCounter2 = 0;
        }
        if (clickCounter3 == 2) {
            savedTime = savedTimeToSend3;
            clickCounter3 = 0;
        }
        if (clickCounter4 == 2) {
            savedTime = savedTimeToSend4;
            clickCounter4 = 0;
        }


        String emailMessage = "The Big Turkey says that your time was " + savedTime + ". " +
                "Download the TurkeyTimer app at: https://play.google.com/store/apps/details?id=com.geogehigbie.TurkeyTimeStopWatch2";
        String emailSubject = "Your Big Turkey time";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("*/*");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailMessage);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);


        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }


    }


    public void soundAndAnimation(int clickCounter, TextView savedTime, ImageView imageView) {

        if (clickCounter % 2 == 0) {
            playTurkeyGobble();

            randomAnimation(imageView, savedTime);

            savedTime.setText(tapInstructions);
        } else {
            playSoundEffects();
        }
    }

    public void mainTimeAnnimation() {

        final TextView timeView = (TextView) findViewById(R.id.time_view);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.reverse_scale);
        scaleAnimation.setFillAfter(false);
        timeView.startAnimation(scaleAnimation);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation reverseGrow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grow_back);
                timeView.startAnimation(reverseGrow);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    public void multipleAnimation(final ImageView imageView, final int clickCounter, final TextView savedTime) {

        if (clickCounter % 2 == 0) {
            Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_animation);
            imageView.startAnimation(alphaAnimation);

            savedTime.startAnimation(alphaAnimation);
            savedTime.setVisibility(View.INVISIBLE);

            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    soundAndAnimation(clickCounter, savedTime, imageView);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }


    public void makeTurkeyGrow(final ImageView imageView, final TextView textView) {

        ImageView bigTurkey4 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey4.setVisibility(View.VISIBLE);
        bigTurkey4 .clearAnimation();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        bigTurkey4.setLayoutParams(layoutParams);

        Animation scaleAnimation = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.scale_animation);
        scaleAnimation.setFillAfter(false);
        bigTurkey4.startAnimation(scaleAnimation);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);

                // sendEmail();

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ImageView bigTurkey4 = (ImageView) findViewById(R.id.turkeyIconLarge1);
                Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alph_animation_long);
                bigTurkey4 .startAnimation(alphaAnimation);


                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ImageView bigTurkey4  = (ImageView) findViewById(R.id.turkeyIconLarge1);
                        bigTurkey4.setVisibility(View.INVISIBLE);

                        Animation reverseAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.reverse_alpha);
                        imageView.startAnimation(reverseAlpha);
                        textView.startAnimation(reverseAlpha);
                        imageView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        sendEmail();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    //this is added to all of the random methods

    public void provideAnimationListener(final ImageView imageView, Animation animation, final TextView textView) {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation reverseAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.reverse_alpha);
                imageView.startAnimation(reverseAlpha);
                textView.startAnimation(reverseAlpha);
                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);

                sendEmail();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void flyStraightFromLeftSmall(final ImageView imageView, final TextView textView) {

        ImageView bigTurkey1 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey1.clearAnimation();

        bigTurkey1.setMaxHeight(750);
        bigTurkey1.setMaxWidth(750);
        bigTurkey1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(-1000.0f, 2100.0f, 100.0f, 100.0f);
        animation1.setDuration(1800);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkey1.startAnimation(animation1);

        provideAnimationListener(imageView, animation1, textView);

    }

    public void flyStraightFromLeftBig(final ImageView imageView, final TextView textView) {

        ImageView bigTurkey1 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey1.clearAnimation();

        bigTurkey1.setMaxHeight(2500);
        bigTurkey1.setMaxWidth(2500);
        bigTurkey1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(-1000.0f, 2100.0f, 100.0f, 100.0f);
        animation1.setDuration(1800);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkey1.startAnimation(animation1);

        provideAnimationListener(imageView, animation1, textView);
    }


    public void flyFromLowerLeftSmall(final ImageView imageView, final TextView textView) {

        ImageView bigTurkey2 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey2.clearAnimation();
        bigTurkey2.setMaxHeight(600);
        bigTurkey2.setMaxWidth(600);
        bigTurkey2.setVisibility(View.VISIBLE);

        TranslateAnimation animation2 = new TranslateAnimation(-1000.0f, 2100.0f, 200.0f, -700.0f);
        animation2.setDuration(2000);
        animation2.setRepeatCount(0);
        animation2.setRepeatMode(0);
        animation2.setFillAfter(true);
        bigTurkey2.startAnimation(animation2);

        provideAnimationListener(imageView, animation2, textView);

    }

    public void flyFromLowerLeftBig(final ImageView imageView, final TextView textView) {

        ImageView bigTurkey2 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey2.clearAnimation();
        bigTurkey2.setMaxHeight(2600);
        bigTurkey2.setMaxWidth(2600);
        bigTurkey2.setVisibility(View.VISIBLE);

        TranslateAnimation animation2 = new TranslateAnimation(-1000.0f, 2100.0f, 200.0f, -700.0f);
        animation2.setDuration(2000);
        animation2.setRepeatCount(0);
        animation2.setRepeatMode(0);
        animation2.setFillAfter(true);
        bigTurkey2.startAnimation(animation2);

        provideAnimationListener(imageView, animation2, textView);

    }

    public void flyFromUpperLeftSmall(final ImageView imageView, final TextView textView) {

        ImageView bigTurkey3 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey3.clearAnimation();

        bigTurkey3.setMaxHeight(500);
        bigTurkey3.setMaxWidth(500);
        bigTurkey3.setVisibility(View.VISIBLE);

        TranslateAnimation animation3 = new TranslateAnimation(-1000.0f, 2100.0f, -600.0f, 900.0f);
        animation3.setDuration(2000);
        animation3.setRepeatCount(0);
        animation3.setRepeatMode(0);
        animation3.setFillAfter(true);
        bigTurkey3.startAnimation(animation3);

        provideAnimationListener(imageView, animation3, textView);
    }

    public void flyFromUpperLeftBig(final ImageView imageView, final TextView textView) {

        ImageView bigTurkey3 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey3.clearAnimation();

        bigTurkey3.setMaxHeight(3000);
        bigTurkey3.setMaxWidth(3000);
        bigTurkey3.setVisibility(View.VISIBLE);

        TranslateAnimation animation3 = new TranslateAnimation(-1000.0f, 2100.0f, -600.0f, 900.0f);
        animation3.setDuration(2000);
        animation3.setRepeatCount(0);
        animation3.setRepeatMode(0);
        animation3.setFillAfter(true);
        bigTurkey3.startAnimation(animation3);

        provideAnimationListener(imageView, animation3, textView);

    }


    public void flyFromLowerRightBig(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(3500);
        bigTurkeyBackward1.setMaxWidth(3500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -2100.0f, 600.0f, -900.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation1, textView);
    }

    public void flyFromLowerRightSmall(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(600);
        bigTurkeyBackward1.setMaxWidth(600);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -2100.0f, 600.0f, -900.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation1, textView);
    }

    public void flyFromUpperRightBig(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(3500);
        bigTurkeyBackward1.setMaxWidth(3500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -1100.0f, -900.0f, 500.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation1, textView);
    }

    public void flyFromUpperRightSmall(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(500);
        bigTurkeyBackward1.setMaxWidth(500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -1100.0f, -900.0f, 500.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation1, textView);
    }

    public void flyRightToLeftStraightBig(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(3000);
        bigTurkeyBackward1.setMaxWidth(3000);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -2100.0f, 150.0f, 150.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation1, textView);
    }

    public void flyRightToLeftStraightSmall(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(500);
        bigTurkeyBackward1.setMaxWidth(500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -2100.0f, 150.0f, 150.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation1, textView);
    }
    //combinations of animations are below

    //fly from upper right Big and fly Left Big
    public void combination1(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(3500);
        bigTurkeyBackward1.setMaxWidth(3500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -1100.0f, -900.0f, 500.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        ImageView bigTurkey3 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey3.clearAnimation();

        bigTurkey3.setMaxHeight(3000);
        bigTurkey3.setMaxWidth(3000);
        bigTurkey3.setVisibility(View.VISIBLE);

        TranslateAnimation animation3 = new TranslateAnimation(-1000.0f, 2100.0f, -600.0f, 900.0f);
        animation3.setDuration(2000);
        animation3.setRepeatCount(0);
        animation3.setRepeatMode(0);
        animation3.setFillAfter(true);
        bigTurkey3.startAnimation(animation3);

        provideAnimationListener(imageView, animation1, textView);
    }

    //fly from LowerLeft Big & Fly from Lower right Big
    public void combination2(final ImageView imageView, final TextView textView) {
        ImageView bigTurkey3 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey3.clearAnimation();

        bigTurkey3.setMaxHeight(3000);
        bigTurkey3.setMaxWidth(3000);
        bigTurkey3.setVisibility(View.VISIBLE);

        TranslateAnimation animation3 = new TranslateAnimation(-1000.0f, 2100.0f, -600.0f, 900.0f);
        animation3.setDuration(2000);
        animation3.setRepeatCount(0);
        animation3.setRepeatMode(0);
        animation3.setFillAfter(true);
        bigTurkey3.startAnimation(animation3);

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(3500);
        bigTurkeyBackward1.setMaxWidth(3500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -2100.0f, 600.0f, -900.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation3, textView);

    }

    //fly from upper right small & fly from upper left small
    public void combination3(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(500);
        bigTurkeyBackward1.setMaxWidth(500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -1100.0f, -900.0f, 500.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        ImageView bigTurkey3 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey3.clearAnimation();

        bigTurkey3.setMaxHeight(500);
        bigTurkey3.setMaxWidth(500);
        bigTurkey3.setVisibility(View.VISIBLE);

        TranslateAnimation animation3 = new TranslateAnimation(-1000.0f, 2100.0f, -600.0f, 900.0f);
        animation3.setDuration(2000);
        animation3.setRepeatCount(0);
        animation3.setRepeatMode(0);
        animation3.setFillAfter(true);
        bigTurkey3.startAnimation(animation3);

        provideAnimationListener(imageView, animation1, textView);

    }

    //fly from lower right small & fly from lower left small
    public void combination4(final ImageView imageView, final TextView textView) {

        ImageView bigTurkey2 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey2.clearAnimation();
        bigTurkey2.setMaxHeight(600);
        bigTurkey2.setMaxWidth(600);
        bigTurkey2.setVisibility(View.VISIBLE);

        TranslateAnimation animation2 = new TranslateAnimation(-1000.0f, 2100.0f, 200.0f, -700.0f);
        animation2.setDuration(2000);
        animation2.setRepeatCount(0);
        animation2.setRepeatMode(0);
        animation2.setFillAfter(true);
        bigTurkey2.startAnimation(animation2);

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(600);
        bigTurkeyBackward1.setMaxWidth(600);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -2100.0f, 600.0f, -900.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation2, textView);

    }

    // upperRightBig && lowerLeftSmall
    public void combination5(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(3500);
        bigTurkeyBackward1.setMaxWidth(3500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -1100.0f, -900.0f, 500.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        ImageView bigTurkey2 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey2.clearAnimation();
        bigTurkey2.setMaxHeight(600);
        bigTurkey2.setMaxWidth(600);
        bigTurkey2.setVisibility(View.VISIBLE);

        TranslateAnimation animation2 = new TranslateAnimation(-1000.0f, 2100.0f, 200.0f, -700.0f);
        animation2.setDuration(2000);
        animation2.setRepeatCount(0);
        animation2.setRepeatMode(0);
        animation2.setFillAfter(true);
        bigTurkey2.startAnimation(animation2);

        provideAnimationListener(imageView, animation1, textView);

    }

    //lowerLeftBig & LowerRightSmall
    public void combination6(final ImageView imageView, final TextView textView) {
        ImageView bigTurkey2 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey2.clearAnimation();
        bigTurkey2.setMaxHeight(2600);
        bigTurkey2.setMaxWidth(2600);
        bigTurkey2.setVisibility(View.VISIBLE);

        TranslateAnimation animation2 = new TranslateAnimation(-1000.0f, 2100.0f, 200.0f, -700.0f);
        animation2.setDuration(2000);
        animation2.setRepeatCount(0);
        animation2.setRepeatMode(0);
        animation2.setFillAfter(true);
        bigTurkey2.startAnimation(animation2);

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(600);
        bigTurkeyBackward1.setMaxWidth(600);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -2100.0f, 600.0f, -900.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation2, textView);
    }

    public void combination7(final ImageView imageView, final TextView textView) {

        ImageView bigTurkey2 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey2.clearAnimation();
        bigTurkey2.setMaxHeight(2600);
        bigTurkey2.setMaxWidth(2600);
        bigTurkey2.setVisibility(View.VISIBLE);

        TranslateAnimation animation2 = new TranslateAnimation(-1000.0f, 2100.0f, 200.0f, -700.0f);
        animation2.setDuration(2000);
        animation2.setRepeatCount(0);
        animation2.setRepeatMode(0);
        animation2.setFillAfter(true);
        bigTurkey2.startAnimation(animation2);

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(500);
        bigTurkeyBackward1.setMaxWidth(500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -2100.0f, 150.0f, 150.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        provideAnimationListener(imageView, animation2, textView);
    }

    public void combination8(final ImageView imageView, final TextView textView) {

        ImageView bigTurkeyBackward1 = (ImageView) findViewById(R.id.turkeyIconLargeBackWard1);
        bigTurkeyBackward1.clearAnimation();

        bigTurkeyBackward1.setMaxHeight(3500);
        bigTurkeyBackward1.setMaxWidth(3500);
        bigTurkeyBackward1.setVisibility(View.VISIBLE);

        TranslateAnimation animation1 = new TranslateAnimation(1000.0f, -2100.0f, 600.0f, -900.0f);
        animation1.setDuration(2000);
        animation1.setRepeatCount(0);
        animation1.setRepeatMode(0);
        animation1.setFillAfter(true);
        bigTurkeyBackward1.startAnimation(animation1);

        ImageView bigTurkey1 = (ImageView) findViewById(R.id.turkeyIconLarge1);
        bigTurkey1.clearAnimation();

        bigTurkey1.setMaxHeight(750);
        bigTurkey1.setMaxWidth(750);
        bigTurkey1.setVisibility(View.VISIBLE);

        TranslateAnimation animation2 = new TranslateAnimation(-1000.0f, 2100.0f, 100.0f, 100.0f);
        animation2.setDuration(1800);
        animation2.setRepeatCount(0);
        animation2.setRepeatMode(0);
        animation2.setFillAfter(true);
        bigTurkey1.startAnimation(animation2);

        provideAnimationListener(imageView, animation1, textView);
    }


    public void randomAnimation(final ImageView imageView, final TextView textView) {

        Random random = new Random();
        int number = random.nextInt(20);


        switch (number) {

            case 0:
                makeTurkeyGrow(imageView, textView);
                break;

            case 1:
                flyFromLowerLeftBig(imageView, textView);
                break;

            case 2:
                flyFromLowerLeftSmall(imageView, textView);
                break;

            case 3:
                flyFromLowerRightBig(imageView, textView);
                break;

            case 4:
                flyFromLowerRightSmall(imageView, textView);
                break;

            case 5:
                flyFromUpperLeftBig(imageView, textView);
                break;

            case 6:
                flyFromUpperLeftSmall(imageView, textView);
                break;

            case 7:
                flyFromUpperRightBig(imageView, textView);
                break;

            case 8:
                flyFromLowerRightSmall(imageView, textView);
                break;

            case 9:
                flyRightToLeftStraightBig(imageView, textView);
                break;

            case 10:
                flyRightToLeftStraightSmall(imageView, textView);
                break;

            case 11:
                flyStraightFromLeftBig(imageView, textView);
                break;

            case 12:
                flyStraightFromLeftBig(imageView, textView);
                break;

            case 13:
                combination1(imageView, textView);
                //flyFromUpperRightBig(imageView, textView);
                //flyFromUpperLeftBig(imageView, textView);
                break;

            case 14:
                combination2(imageView, textView);
                //flyFromLowerLeftBig(imageView, textView);
                //flyFromLowerRightBig(imageView, textView);
                break;

            case 15:
                combination3(imageView, textView);
                //flyFromUpperRightSmall(imageView, textView);
                //flyFromUpperLeftSmall(imageView, textView);
                break;

            case 16:
                combination4(imageView, textView);
                //flyFromLowerLeftSmall(imageView, textView);
                // flyFromLowerRightSmall(imageView, textView);
                break;

            case 17:
                combination5(imageView, textView);
                //flyFromUpperRightBig(imageView, textView);
                //flyFromUpperLeftSmall(imageView,textView);
                break;

            case 18:
                combination6(imageView, textView);
                //flyFromLowerLeftBig(imageView,textView);
                //flyFromLowerRightSmall(imageView,textView);
                break;

            case 19:
                combination7(imageView, textView);
                //flyFromLowerLeftBig(imageView, textView);
                //flyRightToLeftStraightSmall(imageView,textView);
                break;

            case 20:
                combination8(imageView, textView);
                //flyFromLowerRightBig(imageView, textView);
                //flyStraightFromLeftSmall(imageView, textView);
                break;

            default:
                makeTurkeyGrow(imageView, textView);
                break;

        }


    }
}
