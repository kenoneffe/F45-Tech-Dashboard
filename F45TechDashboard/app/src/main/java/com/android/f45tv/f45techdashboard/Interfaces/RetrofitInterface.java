package com.android.f45tv.f45techdashboard.Interfaces;

import com.android.f45tv.f45techdashboard.Model.ScheduleDataModel;
import com.android.f45tv.f45techdashboard.Model.TicketVolumeDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @GET("/api/v2/tickets/")
    Call<List<TicketVolumeDataModel>> getTicketVolume(@Header("Authorization") String authHeader,
                                                      @Header("Cache-Control") String cacheControl,
                                                      @Header("Postman-Token") String postmanToken,
                                                      @Query("page") int page,
                                                      @Query("per_page") int limit,
                                                      @Query("updated_since") String date);


    @GET("/api/v1/resource/Timesheet/")
    Call<List<ScheduleDataModel>> getSchedule(@Header("Authorization") String authHeaderD,
                                              @Header("Cache-Control") String cacheControlD,
                                              @Header("Postman-Token") String postmanTokenD);





}
