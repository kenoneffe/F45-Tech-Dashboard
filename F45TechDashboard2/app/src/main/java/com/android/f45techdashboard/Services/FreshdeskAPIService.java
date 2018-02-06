package com.android.f45techdashboard.Services;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LeakSun on 11/09/2017.
 */

public class FreshdeskAPIService extends AsyncTask<String, String, String> {


    public void getFreshdeskData(String url)
    {
        new FreshdeskAPIService().execute(url);
    }



    @Override
    protected String doInBackground(String... strings) {

        StringBuilder stringBuilder = new StringBuilder();
        String data;
        HttpURLConnection apiCon;
        URL url;
        InputStream inputStream;
        BufferedReader bufferedReader;
        String userName = "WU7cFIcIa5VClq8Ns52";
        String passWord = "Welcome@12345";
        String userPass = userName + ":" + passWord;
        String encUserPass = Base64.encodeToString(userPass.getBytes(), Base64.NO_WRAP);

        try
        {

            url = new URL(strings[0]);
            apiCon = (HttpURLConnection) url.openConnection();
            apiCon.setRequestMethod("GET");
            apiCon.setRequestProperty("Authorization", "Basic " + encUserPass);
            apiCon.connect();

            Log.d("LIXAN GWAPO", "FINALLY ,,/,,");


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        return null;
    }
}

