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
        inflater.inflate((R.layout.tickets), this);
        this.context = context;
        initViews();
    }

    private void initViews() {
        linearLayout = findViewById(R.id.ticketsCon);
    }

    @Override
    public void setTicketVolumeText(String text) {
        ticketVolume = findViewById(R.id.main_ticketsToday);
        ticketVolume.setText(text+" Tickets Today");
    }

    @Override
    public void setResponseTimeText(String text) {
        responseTime = findViewById(R.id.avgResponse);
        responseTime.setText(text+"s Response Time.");
    }

    @Override
    public View getLayout() {
        return this;
    }
}
