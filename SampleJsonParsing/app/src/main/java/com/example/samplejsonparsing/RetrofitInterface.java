package com.example.samplejsonparsing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by LeakSun on 04/16/2018.
 */

public interface RetrofitInterface {

    @GET("/posts/")
    Call<List<RetroFitDataModel>> getResources();
}
