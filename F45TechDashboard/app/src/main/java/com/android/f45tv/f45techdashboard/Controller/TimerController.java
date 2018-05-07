package com.android.f45tv.f45techdashboard.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.f45tv.f45techdashboard.Interfaces.TimerInterface;
import com.android.f45tv.f45techdashboard.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimerController extends LinearLayout implements TimerInterface {

    Context context;
    CountDownTimer countDownTimer;
    TextView minutesText;
    LinearLayout timerFragment;
    MediaPlayer audioPlay;
    View alertLayout;
    String remainTime;
    long timeleft;





    public TimerController(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.timerview, this);
        this.context = context;

        initComponents();
    }


    private void initComponents()
    {
        timerFragment = findViewById(R.id.timerFragment);
    }

    @Override
    public void setMinuteText(String text) {
        minutesText = findViewById(R.id.minutesText);
        minutesText.setText(text);
    }

    @Override
    public View getLayout() {
        return this;
    }



    @Override
    public void setTimer(long timeInMillis, long interval) {

        timeInMillis = timeInMillis + 1000;

        countDownTimer = new CountDownTimer(timeInMillis, interval) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long l) {
                timeleft = l;
                remainTime = String.format(Locale.ENGLISH,"%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                setMinuteText(remainTime);
            }
            @Override
            public void onFinish() {
                new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long l) {
                        showAlert();
                        countDownTimer.cancel();
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
        audioPlay.start();

    }

    public void hideAlert()
    {
        alertLayout = findViewById(R.id.alert_layout);
        alertLayout.setVisibility(View.GONE);
        audioPlay.stop();

    }


    public void pauseCount(){
        countDownTimer.cancel();
    }


    public void resumeCount() {

    }

    public long getTimeleft(){
        return timeleft;
    }


}
