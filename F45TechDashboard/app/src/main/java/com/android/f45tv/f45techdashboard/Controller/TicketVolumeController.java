package com.android.f45tv.f45techdashboard.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.f45tv.f45techdashboard.Interfaces.TicketVolumeInterface;
import com.android.f45tv.f45techdashboard.R;

public class TicketVolumeController extends LinearLayout implements TicketVolumeInterface {

    Context context;
    LinearLayout linearLayout;
    TextView ticketVolume, responseTime;

    public TicketVolumeController(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate((R.layout.activity_main), this);
        this.context = context;
        initViews();
    }

    private void initViews() {
        linearLayout = findViewById(R.id.ticketsCon);
        ticketVolume = findViewById(R.id.main_ticketsToday);
        responseTime = findViewById(R.id.avgResponse);
    }

    @Override
    public void setTicketVolumeText(String text) {
        ticketVolume.setText(text+"Tickets Today");
    }

    @Override
    public void setResponseTimeText(String text) {
        responseTime.setText(text+"s Response Time.");
    }

    @Override
    public View getLayout() {
        return this;
    }
}
