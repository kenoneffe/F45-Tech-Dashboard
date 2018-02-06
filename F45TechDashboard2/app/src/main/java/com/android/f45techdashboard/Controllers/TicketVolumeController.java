package com.android.f45techdashboard.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.f45techdashboard.Interfaces.TicketVolumeInterface;
import com.android.f45techdashboard.R;

/**
 * Created by LeakSun on 11/01/2017.
 */

public class TicketVolumeController extends LinearLayout implements TicketVolumeInterface {

    Context context;

    LinearLayout linearLayout;
    TextView ticketVolume, responseTime;



    public TicketVolumeController(Context context)
    {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fragment_ticket_volume, this);
        this.context = context;

        initViews();

    }


    private void initViews()
    {
        linearLayout = (LinearLayout) findViewById(R.id.ticketVolumeCon);
    }

    @Override
    public void setTicketVolumeText(String text) {
        ticketVolume = (TextView) findViewById(R.id.ticketVolume);
        ticketVolume.setText(text + "Tickets Today");

    }

    @Override
    public void setResponseTimeText(String text) {
        responseTime = (TextView) findViewById(R.id.responseTime);
        responseTime.setText(text + "s Response Time.");

    }

    @Override
    public View getLayout() {
        return this;
    }
}
