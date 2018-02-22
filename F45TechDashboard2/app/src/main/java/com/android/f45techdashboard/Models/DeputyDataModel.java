package com.android.f45techdashboard.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class DeputyDataModel implements Serializable {


        @SerializedName("Id")
        public String Id;

        @SerializedName("Employee")
        public String Employee;

        @SerializedName("StartTime")
        public String StartTime;

        @SerializedName("EndTime")
        public String EndTime;

        @SerializedName("StartTimeLocalized")
        public String StartTimeLocalized;

        @SerializedName("EndTimeLocalized")
        public String EndTimeLocalized;

        @SerializedName("_DPMetaData")
        public _DPMetaData _DPMetaData = new _DPMetaData();

        public class _DPMetaData
        {
            @SerializedName("System")
            public String System;

            @SerializedName("CreatorInfo")
            public CreatorInfo CreatorInfo = new CreatorInfo();

            @SerializedName("EmployeeInfo")
            public EmployeeInfo EmployeeInfo = new EmployeeInfo();

            public class CreatorInfo
            {
                @SerializedName("Id")
                public String Id;

                @SerializedName("DisplayName")
                public String DisplayName;

                @SerializedName("Employee")
                public String Employee;
            }

            public class EmployeeInfo
            {
                @SerializedName("Id")
                public String Id;

                @SerializedName("DisplayName")
                public String DisplayName;

            }

        }

   public DeputyDataModel(String id, String employee, String startTime, String endTime, String startTimeLocalized, String endTimeLocalized, DeputyDataModel._DPMetaData _DPMetaData) {
        Id = id;
        Employee = employee;
        StartTime = startTime;
        EndTime = endTime;
        StartTimeLocalized = startTimeLocalized;
        EndTimeLocalized = endTimeLocalized;
        this._DPMetaData = _DPMetaData;
    }

    public String getId() {
        return Id;
    }

    public String getEmployee() {
        return Employee;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public String getStartTimeLocalized() {
        return StartTimeLocalized;
    }

    public String getEndTimeLocalized() {
        return EndTimeLocalized;
    }

    public DeputyDataModel._DPMetaData get_DPMetaData() {
        return _DPMetaData;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setEmployee(String employee) {
        Employee = employee;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public void setStartTimeLocalized(String startTimeLocalized) {
        StartTimeLocalized = startTimeLocalized;
    }

    public void setEndTimeLocalized(String endTimeLocalized) {
        EndTimeLocalized = endTimeLocalized;
    }

    public void set_DPMetaData(DeputyDataModel._DPMetaData _DPMetaData) {
        this._DPMetaData = _DPMetaData;
    }
}

