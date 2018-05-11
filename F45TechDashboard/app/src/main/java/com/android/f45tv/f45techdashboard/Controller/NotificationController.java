package com.android.f45tv.f45techdashboard.Controller;


public class NotificationController {

    private int id;
    private String subject;
    private String source;
    private String priority;

    public NotificationController(int id, String subject, String source, String priority) {
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

    public String getSource() {
        String text = "";
        if (source.equals("1")){
            text = "Email";
        } else if (source.equals("2")){
            text = "Portal";
        } else if (source.equals("3")){
            text = "Phone";
        } else if (source.equals("7")){
            text = "Chat";
        } else if (source.equals("8")){
            text = "Mobihelp";
        } else if (source.equals("9")){
            text = "Feedback Widget";
        } else if (source.equals("10")){
            text = "Outbound Email";
        }
        return text;
    }

    public String getPriority() {
        String text = "";
        if (priority.equals("1")){
            text = "Low";
        } else if (priority.equals("2")){
            text = "Medium";
        } else if (priority.equals("3")){
            text = "High";
        } else if (priority.equals("4")){
            text = "Urgent";
        }
        return text;
    }
}
