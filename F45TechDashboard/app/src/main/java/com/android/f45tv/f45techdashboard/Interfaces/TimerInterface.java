package com.android.f45tv.f45techdashboard.Interfaces;

import android.view.View;

public interface TimerInterface {

    void setMinuteText(String text);

    void setTimer(long time, long interval);

    View getLayout();

    void showAlert();





}
