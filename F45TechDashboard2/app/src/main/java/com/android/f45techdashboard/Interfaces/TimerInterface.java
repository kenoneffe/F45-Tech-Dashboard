package com.android.f45techdashboard.Interfaces;

import android.view.View;

/**
 * Created by LeakSun on 11/13/2017.
 */

public interface TimerInterface {

    void setMinuteText(String text);

    void setTimer(long time, long interval);

    View getLayout();

    void showAlert();


}
