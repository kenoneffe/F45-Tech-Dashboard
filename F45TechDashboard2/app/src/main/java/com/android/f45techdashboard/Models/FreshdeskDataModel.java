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

    public FreshdeskDataModel(String created_at, String updated_at) {
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
