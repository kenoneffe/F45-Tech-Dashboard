package com.android.f45tv.f45techdashboard.Interfaces;

import android.view.View;

import java.util.ArrayList;

public interface ScheduleInterface {


    void addMorningData(ArrayList<String> onshiftNames);

    void addAfternoonData(ArrayList<String> onshiftNames);

    void addEveningData(ArrayList<String> onshiftNames);

    View getLayout();
}
