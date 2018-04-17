package com.android.f45tv.f45techdashboard.Client;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static String BASE_URL;
    public static Retrofit retrofit = null;

    public void setBaseUrl(String url){
        BASE_URL = url;
    }

    public static String getBaseUrl(){
        return BASE_URL;
    }

    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
