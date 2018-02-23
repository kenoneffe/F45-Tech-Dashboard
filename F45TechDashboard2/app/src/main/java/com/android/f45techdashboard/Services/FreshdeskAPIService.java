package com.android.f45techdashboard.Services;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.android.f45techdashboard.Models.FreshdeskDataModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;


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
        FreshdeskDataModel freshdeskModel;

        try {

            url = new URL(strings[0]);
            apiCon = (HttpURLConnection) url.openConnection();
            apiCon.setRequestMethod("GET");
            apiCon.setRequestProperty("Authorization", "Basic " + encUserPass);
            apiCon.connect();


            inputStream = apiCon.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            if (apiCon != null){

                while((data = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(data);
                    stringBuilder.append("\n");
                }

                apiCon.disconnect();
                bufferedReader.close();

            }
            else {
                Log.e("ERROR", "There is no Connection");
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {


            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            JSONObject jsonObject;
            LinkedList<FreshdeskDataModel> array2 = new LinkedList<>();


            for (int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);
                freshdeskModel = new Gson().fromJson(jsonObject.toString(), FreshdeskDataModel.class);
  //             freshdeskDataModelList = new Gson().fromJson(freshdeskModel.toString(), DataModelLists.class);
                array2.add(freshdeskModel);
            }

            Log.e("Error", "Run ples");



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }

}


