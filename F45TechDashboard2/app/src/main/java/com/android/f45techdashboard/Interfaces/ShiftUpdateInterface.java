package com.android.f45techdashboard.Interfaces;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by LeakSun on 10/31/2017.
 */

public interface ShiftUpdateInterface {

    void addMorningData(ArrayList<String> onshiftNames);

    void addAfternoonData(ArrayList<String> onshiftNames);

    void addEveningData(ArrayList<String> onshiftNames);

    int getMorningDataCount();

    int getAfternoonDataCount();

    int getEveningDataCount();

    View getLayout();
}
