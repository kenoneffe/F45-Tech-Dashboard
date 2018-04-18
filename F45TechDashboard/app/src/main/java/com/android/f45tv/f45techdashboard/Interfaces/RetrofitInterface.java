package com.android.f45tv.f45techdashboard.Interfaces;
import com.android.f45tv.f45techdashboard.Model.TicketVolumeDataModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RetrofitInterface
{
    @GET("/api/v2/tickets/?per_page=100&page=1")
    Call<List<TicketVolumeDataModel>> getTicketVolume(@Header("Authorization") String authHeader,
                                                      @Header("Cache-Control") String cacheControl,
                                                      @Header("Postman-Token") String postmanToken,
                                                      @Header("link") String linkHeader);
}
