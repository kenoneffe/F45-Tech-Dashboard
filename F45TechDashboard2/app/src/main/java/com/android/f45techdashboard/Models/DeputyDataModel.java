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

    }

