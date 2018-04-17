package com.example.samplejsonparsing;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LeakSun on 04/16/2018.
 */

public class RetroFitDataModel {


    @SerializedName("userId")
    public String userID;

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("body")
    public String body;

}
