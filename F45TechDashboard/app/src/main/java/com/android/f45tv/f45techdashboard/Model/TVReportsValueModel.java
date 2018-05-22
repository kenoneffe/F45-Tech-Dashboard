package com.android.f45tv.f45techdashboard.Model;

import com.google.gson.annotations.SerializedName;

public class TVReportsValueModel {

    public TVReportsObject tvReportsObject;

    public class TVReportsObject{
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

        public String getGymId() {
            return gymId;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public String getSchedules() {
            return schedules;
        }

        public String getWeek() {
            return week;
        }

        public String getWeekday() {
            return weekday;
        }

        public Integer getPauses() {
            return pauses;
        }

        public Integer getAutoStartCount() {
            return autoStartCount;
        }

        public Integer getFailedSessionCount() {
            return failedSessionCount;
        }

        public Integer getNumberOfDaysModifiedInSevenDays() {
            return numberOfDaysModifiedInSevenDays;
        }

        public Integer getStartedThisMonthCount() {
            return startedThisMonthCount;
        }

        public String getLastOnline() {
            return lastOnline;
        }

        public String getIosVersion() {
            return iosVersion;
        }

        public String getDevice() {
            return device;
        }
    }
}
