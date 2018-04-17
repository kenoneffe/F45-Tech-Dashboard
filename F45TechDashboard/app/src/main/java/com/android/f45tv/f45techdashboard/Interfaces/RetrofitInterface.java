package com.android.f45tv.f45techdashboard.Interfaces;

import com.android.f45tv.f45techdashboard.Model.TicketVolumeDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface
{
    @GET("/tickets/")
    Call<List<TicketVolumeDataModel>> getTicketVolume();
}
