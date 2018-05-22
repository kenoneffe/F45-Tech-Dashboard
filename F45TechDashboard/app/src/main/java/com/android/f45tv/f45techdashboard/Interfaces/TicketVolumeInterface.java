package com.android.f45tv.f45techdashboard.Interfaces;

import android.view.View;
import com.android.f45tv.f45techdashboard.Model.TicketVolumeDataModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TicketVolumeInterface {

    void setTicketVolumeText(String text);

    void setResponseTimeText(String text);

    View getLayout();


}
