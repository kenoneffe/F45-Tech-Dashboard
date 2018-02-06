package com.android.f45techdashboard.Interfaces;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LeakSun on 11/22/2017.
 */

public class BasicAuthFreshdeskInterceptor implements Interceptor {



    private String creds;


    public  BasicAuthFreshdeskInterceptor(String userName, String password)
    {
        this.creds = Credentials.basic(userName, password);
    }






    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        Request authReq = request.newBuilder().header("Authorization", creds).build();

        return chain.proceed(authReq);
    }
}
