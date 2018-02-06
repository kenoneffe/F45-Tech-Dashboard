package com.android.f45techdashboard.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by LeakSun on 11/20/2017.
 */

public class FreshdeskDataModel implements Serializable {

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;

}
