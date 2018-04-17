package com.example.samplejsonparsing;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LeakSun on 04/16/2018.
 */

public class RetrofitClient {

    private String baseURL = null;


    public void setBaseURL (String url){
        baseURL = url;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public Retrofit getClient(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }


}
