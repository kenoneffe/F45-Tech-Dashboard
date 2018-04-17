package com.example.samplejsonparsing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LeakSun on 04/16/2018.
 */

public class MainActivity extends AppCompatActivity {


    TextView jsonText;
    Button getJSONbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       jsonText = findViewById(R.id.jsonTxt);
       getJSONbtn = findViewById(R.id.getBtn);

       getJSONbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               RetrofitClient retrofitClient = new RetrofitClient();

               retrofitClient.setBaseURL("https://jsonplaceholder.typicode.com/");
               RetrofitInterface retrofitInterface = retrofitClient.getClient().create(RetrofitInterface.class);

               Call<List<RetroFitDataModel>> call = retrofitInterface.getResources();
               call.enqueue(new Callback<List<RetroFitDataModel>>() {
                   @Override
                   public void onResponse(Call<List<RetroFitDataModel>> call, Response<List<RetroFitDataModel>> response) {

                       Gson gson = new Gson();
                       ArrayList<RetroFitDataModel> models = (ArrayList<RetroFitDataModel>) response.body();
                           /*JSONArray jsonArray = new JSONArray(gson.toJson(models));

                           jsonText.setMaxLines(models.size());
                           jsonText.setMovementMethod(new ScrollingMovementMethod());
                           jsonText.setText(models.get(0).id);*/
                       StringBuilder stringBuilder = new StringBuilder();

                           for(int i = 0; models.size() > i; i++){


                               RetroFitDataModel retroFitDataModel = models.get(i);

                               String string = "USER ID: " + retroFitDataModel.userID + "\n"
                                       + "ID: " + retroFitDataModel.id + "\n"
                                       + "TITLE: " + retroFitDataModel.title + "\n"
                                       + "BODY: " + retroFitDataModel.body + "\n ";

                               stringBuilder.append(string);



                           }

                       jsonText.setMaxLines(models.size());
                       jsonText.setMovementMethod(new ScrollingMovementMethod());
                       jsonText.setText(stringBuilder);

                           /*for (int i = 0; jsonArray.length() > i; i++){

                               JSONObject jsonObject = jsonArray.getJSONObject(i);
                               RetroFitDataModel model = gson.fromJson(jsonObject.toString(), RetroFitDataModel.class);

                               retroFitDataModels.add(model);
                           }*/

                   }

                   @Override
                   public void onFailure(Call<List<RetroFitDataModel>> call, Throwable t) {
                       jsonText.setText("ERROR: " + t);
                   }

               });


           }
       });


    }
}
