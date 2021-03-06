package com.android.f45tv.f45techdashboard.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.f45tv.f45techdashboard.Interfaces.ScheduleInterface;
import com.android.f45tv.f45techdashboard.R;

import java.util.ArrayList;

public class ScheduleController extends LinearLayout implements ScheduleInterface {

    LinearLayout morningGroup, afternoonGroup, eveningGroup;
    Context context;

    public int morningDataCount = 0;
    public int afternoonDataCount = 0;
    public int eveningDataCount = 0;




    public ScheduleController(Context context)
    {
        super(context);
        this.context = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.schedule, this);
        this.context = context;

        initViews();
    }

    public void initViews()
    {
       /* morningGroup = (LinearLayout) findViewById(R.id.scheduleGroupMorning);
        afternoonGroup = (LinearLayout) findViewById(R.id.scheduleGroupAfternoon);
        eveningGroup = (LinearLayout) findViewById(R.id.scheduleGroupEvening);*/
    }

    @Override
    public void addMorningData(ArrayList<String> onshiftNames)
    {
        int newSize = onshiftNames.size();
        if(morningGroup != null)
        {
            if(morningGroup.getChildCount() > 0)
            {
                removeAllViews();
            }

            for(int i = 0; i < newSize; i++)
            {
                TextView newTexView = new TextView(context);
                newTexView.setText(onshiftNames.get(i));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                morningGroup.addView(newTexView, layoutParams);
            }
            morningDataCount = newSize;

        }
        else
        {
            Log.v("LIXAN", "Morning Group is NULL");
        }



    }

    @Override
    public void addAfternoonData(ArrayList<String> onshiftNames)
    {
        int newSize = onshiftNames.size();
        if(afternoonGroup != null)
        {
            if(afternoonGroup.getChildCount() > 0)
            {
                removeAllViews();
            }

            for(int i = 0; i < newSize; i++)
            {
                TextView newTexView = new TextView(context);
                newTexView.setText(onshiftNames.get(i));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                afternoonGroup.addView(newTexView, layoutParams);
            }
            afternoonDataCount = newSize;
        }
        else
        {
            Log.v("LIXAN", "Afternoon Group is NULL");
        }
    }

    @Override
    public void addEveningData(ArrayList<String> onshiftNames)
    {
        int newSize = onshiftNames.size();

        if (eveningGroup != null)
        {
            if(eveningGroup.getChildCount() > 0)
            {
                eveningGroup.removeAllViews();
            }

            for (int i = 0; i < newSize; i++)
            {
                TextView newTexView = new TextView(context);
                newTexView.setText(onshiftNames.get(i));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                eveningGroup.addView(newTexView, layoutParams);
            }
            eveningDataCount = newSize;
        }
        else
        {
            Log.v("LIXAN", "Evening Group is NULL");
        }

    }
//    @Override
//    public int getMorningDataCount()
//    {
//        return morningDataCount;
//    }
//
//    @Override
//    public int getAfternoonDataCount()
//    {
//        return afternoonDataCount;
//    }
//
//    @Override
//    public int getEveningDataCount()
//    {
//        return eveningDataCount;
//    }

    @Override
    public View getLayout() {
        return this;
    }

}
