package com.android.f45techdashboard.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LeakSun on 11/22/2017.
 */

public class KlifolioDataModel implements Serializable {

    @SerializedName("gym_id")
    private String gymIdentification;

    @SerializedName("name")
    private String gymName;

    @SerializedName("version")
    private String gymVersion;

    @SerializedName("schedules")
    private String gymSchedules;

    @SerializedName("week")
    private String gymWeek;

    @SerializedName("weekday")
    private String gymWeekday;

    @SerializedName("pauses")
    private String gymPauses;

    @SerializedName("auto_start_count")
    private String gymAutostartcount;

    @SerializedName("failed_session_count")
    private String gymFailedsessioncount;

    @SerializedName("number_of_days_modified_in_seven_days")
    private String gymNumberofdays;

    @SerializedName("started_this_month_count")
    private String gymStartedthismonth;

    @SerializedName("last_online")
    private String gymLastonline;

    @SerializedName("ios_version")
    private String gymIosVersion;

    @SerializedName("device")
    private String gymdevice;




}
