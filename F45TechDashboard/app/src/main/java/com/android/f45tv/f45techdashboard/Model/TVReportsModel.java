package com.android.f45tv.f45techdashboard.Model;

import com.google.gson.annotations.SerializedName;

public class TVReportsModel {

    @SerializedName("gym_id")
    private String gymId;

    @SerializedName("name")
    private String name;

    @SerializedName("version")
    private String version;

    @SerializedName("schedules")
    private String schedules;

    @SerializedName("week")
    private String week;

    @SerializedName("weekday")
    private String weekday;

    @SerializedName("pauses")
    private Integer pauses;

    @SerializedName("auto_start_count")
    private Integer autoStartCount;

    @SerializedName("failed_session_count")
    private Integer failedSessionCount;

    @SerializedName("number_of_days_modified_in_seven_days")
    private Integer numberOfDaysModifiedInSevenDays;

    @SerializedName("started_this_month_count")
    private Integer startedThisMonthCount;

    @SerializedName("last_online")
    private String lastOnline;

    @SerializedName("ios_version")
    private String iosVersion;

    @SerializedName("device")
    private String device;


    public TVReportsModel(String gymId, String name, String version, String schedules, String week, String weekday, Integer pauses, Integer autoStartCount, Integer failedSessionCount, Integer numberOfDaysModifiedInSevenDays, Integer startedThisMonthCount, String lastOnline, String iosVersion, String device) {
        super();
        this.gymId = gymId;
        this.name = name;
        this.version = version;
        this.schedules = schedules;
        this.week = week;
        this.weekday = weekday;
        this.pauses = pauses;
        this.autoStartCount = autoStartCount;
        this.failedSessionCount = failedSessionCount;
        this.numberOfDaysModifiedInSevenDays = numberOfDaysModifiedInSevenDays;
        this.startedThisMonthCount = startedThisMonthCount;
        this.lastOnline = lastOnline;
        this.iosVersion = iosVersion;
        this.device = device;
    }

    public String getGymId() {
        return gymId;
    }

    public void setGymId(String gymId) {
        this.gymId = gymId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSchedules() {
        return schedules;
    }

    public void setSchedules(String schedules) {
        this.schedules = schedules;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Integer getPauses() {
        return pauses;
    }

    public void setPauses(Integer pauses) {
        this.pauses = pauses;
    }

    public Integer getAutoStartCount() {
        return autoStartCount;
    }

    public void setAutoStartCount(Integer autoStartCount) {
        this.autoStartCount = autoStartCount;
    }

    public Integer getFailedSessionCount() {
        return failedSessionCount;
    }

    public void setFailedSessionCount(Integer failedSessionCount) {
        this.failedSessionCount = failedSessionCount;
    }

    public Integer getNumberOfDaysModifiedInSevenDays() {
        return numberOfDaysModifiedInSevenDays;
    }

    public void setNumberOfDaysModifiedInSevenDays(Integer numberOfDaysModifiedInSevenDays) {
        this.numberOfDaysModifiedInSevenDays = numberOfDaysModifiedInSevenDays;
    }

    public Integer getStartedThisMonthCount() {
        return startedThisMonthCount;
    }

    public void setStartedThisMonthCount(Integer startedThisMonthCount) {
        this.startedThisMonthCount = startedThisMonthCount;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
