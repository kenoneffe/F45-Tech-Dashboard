package com.android.f45techdashboard.Managers;

import android.util.Log;

import com.android.f45techdashboard.Controllers.ShiftTableController;
import com.android.f45techdashboard.Models.DeputyDataModel;
import com.google.gson.internal.LinkedTreeMap;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by LeakSun on 10/30/2017.
 */

public class ShiftTableManager {

    private Map<String, ShiftTableController> observer;
    private Calendar cal;


    public ShiftTableManager()
    {
        observer = new LinkedTreeMap<>();
    }


    public void putObserver(String key, ShiftTableController tableController)
    {
        observer.put(key, tableController);
    }


    public void notifyObserver(ArrayList<DeputyDataModel> dataModel)
    {
        DeputyDataModel deputyDataModel;
        ArrayList<String> morningShift, afternoonShift, eveningShift;

        if (dataModel != null)
        {
            morningShift = new ArrayList<>();
            afternoonShift = new ArrayList<>();
            eveningShift = new ArrayList<>();

            for (int i = 0 ; dataModel.size() > i; i++)
            {
                deputyDataModel = dataModel.get(i);
                String timeString = deputyDataModel.StartTimeLocalized.substring((deputyDataModel.StartTimeLocalized.indexOf("T") + 1), deputyDataModel.StartTimeLocalized.indexOf("+"));
                Log.v("LIXAN", "DATE: " + timeString + " NAME: " + deputyDataModel._DPMetaData.EmployeeInfo.DisplayName);

                try {

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                    timeFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));
                    dateFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));

                    String morningStarTime = "07:29:00";
                    Date morningShiftSTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(morningStarTime);
                    Calendar morningShiftSTimecal = Calendar.getInstance();
                    morningShiftSTimecal.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));
                    morningShiftSTimecal.setTime(morningShiftSTime);

                    String morningEndTime = "16:31:00";
                    Date morningETime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(morningEndTime);
                    Calendar morningShiftEtimecal = Calendar.getInstance();
                    morningShiftEtimecal.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));
                    morningShiftEtimecal.setTime(morningETime);

                    String afterNoonStartTime = "13:29:00";
                    Date afterNoonStime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(afterNoonStartTime);
                    Calendar afterNoonShiftStimecal = Calendar.getInstance();
                    afterNoonShiftStimecal.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));
                    afterNoonShiftStimecal.setTime(afterNoonStime);

                    String afternoonEndTime = "22:31:00";
                    Date afterNoonETime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(afternoonEndTime);
                    Calendar afterNoonETimeCal = Calendar.getInstance();
                    afterNoonETimeCal.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));
                    afterNoonETimeCal.setTime(afterNoonETime);

                    String eveningStartTime = "22:29:00";
                    Date eveningStime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(eveningStartTime);
                    Calendar eveningStimecal = Calendar.getInstance();
                    eveningStimecal.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));
                    eveningStimecal.setTime(eveningStime);

                    String eveningEndTime = "07:31:00";
                    Date eveningEtime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(eveningEndTime);
                    Calendar eveningEtimeCal = Calendar.getInstance();
                    eveningEtimeCal.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));
                    eveningEtimeCal.setTime(eveningEtime);

                    Date startTime = new Date(Long.parseLong(deputyDataModel.StartTime) * 1000);
                    Date endTime = new Date(Long.parseLong(deputyDataModel.EndTime) * 1000);
                    Date currentTime = new Date(Calendar.getInstance().getTimeInMillis());

                    String startTimeString = timeFormat.format(startTime);
                    Date mstartTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(startTimeString);
                    Calendar startTimeCal = Calendar.getInstance();
                    startTimeCal.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));
                    startTimeCal.setTime(mstartTime);
                    Date fstarttime = startTimeCal.getTime();

                    String endTimeString = timeFormat.format(endTime);
                    Date mendTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(endTimeString);
                    Calendar endTimeCal = Calendar.getInstance();
                    endTimeCal.setTimeZone(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+8").getID()));
                    endTimeCal.setTime(mendTime);
                    Date fendtime = endTimeCal.getTime();

                    Date dummy = dateFormat.parse("2017-11-02");


                    Log.d("LIXAN", "TIME: " + timeFormat.format(startTime) + "End TIME: " + timeFormat.format(endTime) + " Current TIME: " + timeFormat.format(currentTime) + " Current DATE: " + dateFormat.format(currentTime));

                    if(dateFormat.format(dummy).equals(dateFormat.format(startTime)))
                    {
                        if(fstarttime.after(morningShiftSTimecal.getTime()) && fendtime.before(morningShiftEtimecal.getTime()))
                        {
                            morningShift.add(deputyDataModel._DPMetaData.EmployeeInfo.DisplayName);
                            Log.v("LIXAN", deputyDataModel._DPMetaData.EmployeeInfo.DisplayName + " is in Morning Shift" + " stime: " + fstarttime + " endTIme: " + fendtime);
                        }
                        else if (fstarttime.after(afterNoonShiftStimecal.getTime()) && fendtime.before(afterNoonETimeCal.getTime()))
                        {
                            afternoonShift.add(deputyDataModel._DPMetaData.EmployeeInfo.DisplayName);
                            Log.v("LIXAN", deputyDataModel._DPMetaData.EmployeeInfo.DisplayName + " is in Afternoon Shift" + " stime: " + startTime + " endTIme: " + endTime);
                        }
                        else if (fstarttime.after(eveningStimecal.getTime()) && fendtime.before(eveningEtimeCal.getTime()))
                        {
                            eveningShift.add(deputyDataModel._DPMetaData.EmployeeInfo.DisplayName);
                            Log.v("LIXAN", deputyDataModel._DPMetaData.EmployeeInfo.DisplayName + " is in Evening Shift" + " stime: " + startTime + " endTIme: " + endTime);
                        }
                        else
                            {
                                Log.v("LIXAN", deputyDataModel._DPMetaData.EmployeeInfo.DisplayName + " has NO shift." + " stime: " + startTime + " endTIme: " + endTime);
                            }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            if (!morningShift.isEmpty())
            {
                for (Map.Entry<String, ShiftTableController> shiftTableController : observer.entrySet())
                {
                    shiftTableController.getValue().addMorningData(morningShift);
                }
            }
            else
                {
                    Log.v("LIXAN", "morning SHIFT IS EMPTY");
                }

            if (!afternoonShift.isEmpty())
            {
                for (Map.Entry<String, ShiftTableController> shiftTableController : observer.entrySet())
                {
                    shiftTableController.getValue().addAfternoonData(afternoonShift);
                }
            }
            else
                {
                    Log.v("LIXAN", "afternooon SHIFT IS EMPTY");
                }

            if (!eveningShift.isEmpty())
            {
                for (Map.Entry<String, ShiftTableController> shiftTableController : observer.entrySet())
                {
                    shiftTableController.getValue().addEveningData(eveningShift);
                }
            }
            else
                {
                    Log.v("LIXAN", "evening SHIFT IS EMPTY");
                }
            /*if(shift != null)
            {
                switch (shift)
                {
                    case "morning":
                        for (Map.Entry<String, ShiftTableController> shiftTableController : observer.entrySet())
                        {
                            shiftTableController.getValue().addMorningData(2, "LIXAN GWAPO");
                        }
                        break;
                    case "afternoon":
                        for (Map.Entry<String, ShiftTableController> shiftTableController : observer.entrySet())
                        {
                            shiftTableController.getValue().addAfternoonData(2, "LIXAN GWAPO");
                        }
                        break;
                    case "evening":
                        for (Map.Entry<String, ShiftTableController> shiftTableController : observer.entrySet())
                        {
                            shiftTableController.getValue().addEveningData(2, "LIXAN GWAPO");
                        }
                        break;
                }
            }
            else
            {
                Log.e("LIXAN", "shift variable is NULL");
            }*/

        }
        else {
            Log.d("LIXAN", "notifyObserver: dataModel IS NULL 8:::::::D");
        }


    }



}
