package com.android.f45techdashboard.Controllers;

import android.content.Context;
import android.icu.util.TimeUnit;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import com.android.f45techdashboard.Interfaces.TimerInterface;
import com.android.f45techdashboard.R;

import java.net.URI;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by LeakSun on 11/13/2017.
 */

public class TimerController extends LinearLayout implements TimerInterface {

    Context context;
    TextView minuteText;
    LinearLayout timerFragment;
    View alertLayout;
    AppCompatActivity alertActivity;
    Handler handle;
    Runnable runnable;
    CountDownTimer countDownTimer;
    MediaPlayer audioPlay;


    public TimerController(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_timer, this);
        this.context = context;

        initComponents();
    }

    private void initComponents()
    {
        timerFragment = (LinearLayout) findViewById(R.id.timerFragment);
    }

    @Override
    public void setMinuteText(String text) {
        minuteText = (TextView) findViewById(R.id.minuteText);
        minuteText.setText(text);
    }


    @Override
    public View getLayout() {
        return this;
    }

    @Override
    public void setTimer(long timeInMillis, long interval) {

        timeInMillis = timeInMillis + 1000;

       countDownTimer = new CountDownTimer(timeInMillis, interval) {
            @Override
            public void onTick(long l) {
                String remainTime = String.format("%02d : %02d", java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(l),
                        java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(l) - java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(l)));
                setMinuteText(remainTime);
            }

            @Override
            public void onFinish() {
               new CountDownTimer(6000, 1000) {
                   @Override
                   public void onTick(long l) {
                       showAlert();
                   }

                   @Override
                   public void onFinish() {
                       hideAlert();
                       countDownTimer.start();
                   }
               }.start();
            }
        }.start();
    }

    @Override
    public void showAlert() {
        alertLayout = findViewById(R.id.alert_layout);
        alertLayout.setVisibility(View.VISIBLE);
        audioPlay = MediaPlayer.create(context, R.raw.front_desk_bells);
        try {
            audioPlay.start();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void hideAlert()
    {
        alertLayout = findViewById(R.id.alert_layout);
        alertLayout.setVisibility(View.GONE);
        audioPlay.stop();
    }


}
