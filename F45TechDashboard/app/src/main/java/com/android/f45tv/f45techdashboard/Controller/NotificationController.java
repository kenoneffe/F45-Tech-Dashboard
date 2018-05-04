package com.android.f45tv.f45techdashboard.Controller;


public class NotificationController {

    private int id;
    private String subject;
    private int source;
    private int priority;

    public NotificationController(int id, String subject, int source, int priority) {
        this.id = id;
        this.subject = subject;
        this.source = source;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public int getSource() {
        return source;
    }

    public int getPriority() {
        return priority;
    }
}
