package com.android.f45tv.f45techdashboard.Controller;

public class TVReportsController {

    private int gym_id;
    private String name;
    private String last_online;

    public TVReportsController(int gym_id, String name, String last_online) {
        this.gym_id = gym_id;
        this.name = name;
        this.last_online = last_online;
    }

    public int getGym_id() {
        return gym_id;
    }

    public String getName() {
        return name;
    }

    public String getLast_online() {
        return last_online;
    }
}