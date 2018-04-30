package com.android.f45tv.f45techdashboard.Model;

import com.google.gson.annotations.SerializedName;

public class TicketVolumeDataModel {

    public Object[] attachments;
    public String[] cc_emails;
    public String company_id;
    public CustomFields custom_fields;
    public Boolean deleted;
    public String description;
    public String description_text;
    public String due_by;
    public String email;
    public String email_config_id;
    public String facebook_id;
    public String fr_due_by;
    public Boolean fr_escalated;
    public String[] fwd_emails;
    public String group_id;
    public String id;
    public Boolean is_escalated;
    public String name;
    public String phone;
    public String priority;
    public String product_id;
    public String[] reply_cc_emails;
    public String requester_id;
    public String responder_id;
    public String source;
    public Boolean spam;
    public String status;
    public String subject;
    public String[] tags;
    public String[] to_emails;
    public String twitter_id;
    public String type;
    public String created_at;
    public String updated_at;

    public class CustomFields{
        public String system;
        public String issue;
        public String department;
        public String responded_by;
    }
}

