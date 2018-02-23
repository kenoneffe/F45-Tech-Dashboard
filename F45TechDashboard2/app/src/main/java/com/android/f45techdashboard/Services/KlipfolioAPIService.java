package com.android.f45techdashboard.Services;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.f45techdashboard.Models.Constants;
import com.android.f45techdashboard.Models.KlifolioDataModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LeakSun on 11/21/2017.
 */

public class KlipfolioAPIService extends AsyncTask<String, String, String> {


    private KlifolioDataModel klifolioDataModel;

    public void getKlipfolioData(String url)
    {
        new KlipfolioAPIService().execute(url);
    }


    @Override
    protected String doInBackground(String... strings) {


        StringBuilder sbuilder = new StringBuilder();
        String data;
        URL apiURL;
        HttpURLConnection apiCon;
        InputStream inputStream = null;
        BufferedReader bufferedReader;


        try {

            String apiKey = "b14f4a3baa6b948bdaf976f97a6bb98ae74c53e0";
            apiURL = new URL(strings[0]);
            apiCon = (HttpURLConnection) apiURL.openConnection();
            apiCon.addRequestProperty("kf-api-key", apiKey);
            apiCon.setRequestMethod("GET");
            apiCon.connect();

            inputStream = apiCon.getInputStream();



            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while((data = bufferedReader.readLine()) != null)
            {
                sbuilder.append(data);
                sbuilder.append("\n");
            }


            apiCon.disconnect();
            bufferedReader.close();


            KlifolioDataModel klifolioDataModel = new Gson().fromJson(sbuilder.toString(), KlifolioDataModel.class);
            Constants.klifolioDataModel = klifolioDataModel;

            Log.e("Result", sbuilder.toString());
            Log.e("Result1", klifolioDataModel.toString());

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return String.valueOf(klifolioDataModel);
    }
}
