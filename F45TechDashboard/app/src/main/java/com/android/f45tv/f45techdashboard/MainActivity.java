package com.android.f45tv.f45techdashboard;

import android.graphics.Color;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.f45tv.f45techdashboard.Client.RetrofitClient;
import com.android.f45tv.f45techdashboard.Controller.TicketVolumeController;
import com.android.f45tv.f45techdashboard.Controller.TimerController;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.f45tv.f45techdashboard.Interfaces.RetrofitInterface;
import com.android.f45tv.f45techdashboard.Model.TicketVolumeDataModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.wealdtech.utils.StringUtils;

import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.internal.http2.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

import static java.text.DateFormat.getDateTimeInstance;

/**
 * Created by LeakSun on 04/04/2018.
 */

public class MainActivity extends AppCompatActivity {

    TextView marqueeView;
    BarChart barChart;
    String[] graphLabels;
    List<BarEntry> barEntries1;
    List<BarEntry> barEntries2;
    List<BarEntry> barEntries3;
    TextView tv;
    CountDownTimer countDownTimer;
    TicketVolumeController ticketVolumeController;
    TimerController timerController;
    FrameLayout timerFrame;
    LinearLayout ticketLayout;
    Integer tickets = 0;
    List<TicketVolumeDataModel> ticketVolumeDataModels;
    String TAG = "Kyle";
    int page = 1;
    BarDataSet barDataSetOpened;
    BarDataSet barDataSetResolved;
    BarDataSet barDataSetUnresolved;
    BarData data;
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    long responseTime = 0;
    float barW;
    String headerString = "";
    String pageNum = "1";
    int janO = 0 , janR = 0, janU = 0;
    int febO = 0 , febR = 0, febU = 0;
    int marO = 0 , marR = 0, marU = 0;
    int aprilO = 0 , aprilR = 0, aprilU = 0;
    boolean isComplete = false;

    Handler handler = new Handler();
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ticket Volume Controller
        ticketVolumeController = new TicketVolumeController(this);
        ticketLayout = findViewById(R.id.ticketFrame);
        //Timer Controller
        timerFrame = findViewById(R.id.timerFrame);
        timerController = new TimerController(this);

        timerController.setTimer(TimeUnit.SECONDS.toMillis(10), 1000);
        timerFrame.addView(timerController);
        //Graph
        barChart = findViewById(R.id.chart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setPinchZoom(true);
        barChart.setFocusable(true);
        barChart.setDragEnabled(true);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setHighlightPerTapEnabled(false);
        barChart.setHighlightPerDragEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setClickable(true);
        //add the retrofit here.
        barEntries1 = new ArrayList<>();
        barEntries2 = new ArrayList<>();
        barEntries3 = new ArrayList<>();

        graphLabels = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        //X AXIS AND Y AXIS
        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();
        xAxis.setDrawLimitLinesBehindData(false);
        xAxis.setValueFormatter(new MyAxisValueFormatter(graphLabels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(12);
        yAxis.setAxisMinimum(0);
        xAxis.setGranularity(1f); // restrict interval to 1 (minimum)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        //retrofitclient
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.setBaseUrl("https://f45training.freshdesk.com/");
        final String authHeader = "Basic V1U3Y0ZJY0lhNVZDbHE4TnM1Mjo=";
        final String cacheControl = "no-cache";
        final String postmanToken = "e601edd5-eb58-430f-a43a-ea74b8d6ce6c";
        final RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);



         runnable = new Runnable() {
            @Override
            public void run() {
                    Call<List<TicketVolumeDataModel>> call = retrofitInterface.getTicketVolume(authHeader, cacheControl, postmanToken, page,100, "2015-01-19T02:00:00Z");
                    Log.d(TAG, "On loop start, page number is :"+ page);
                    Log.d(TAG, "headerString: " + headerString);

                    if(headerString.isEmpty()){
                        Log.d("HERE", "ENTER HERE");
                        call.enqueue(new Callback<List<TicketVolumeDataModel>>() {
                            @Override
                            public void onResponse(Call<List<TicketVolumeDataModel>> call, Response<List<TicketVolumeDataModel>> response) {
                                Headers header = response.headers();
                                headerString = header.get("link");
                                Log.d(TAG, "ON RESPONSE: " + headerString);

                            }

                            @Override
                            public void onFailure(Call<List<TicketVolumeDataModel>> call, Throwable t) {

                            }
                        });
                    }
                    else{
                        //code on NOTEPAD
                        call.enqueue(new Callback<List<TicketVolumeDataModel>>() {
                            @Override
                            public void onResponse(Call<List<TicketVolumeDataModel>> call, Response<List<TicketVolumeDataModel>> response) {
                                //updated at - created at = summation of everything / model size
                                ArrayList<TicketVolumeDataModel> model = (ArrayList<TicketVolumeDataModel>) response.body();
                                Headers headers =  response.headers(); // I GOT THE LINK HEADER I NEED TO UTILIZE THIS SHIT
                                Log.d(TAG, headers.toString());
                                headerString = headers.get("link");

                                if(headerString.isEmpty()){
                                    isComplete = true;
                                    checkCOmplete();
                                    Log.d("HERE", "ISCOMPLETE: " + isComplete);
                                }
                                else{
                                    String result = headerString.substring(headerString.indexOf("?") + 1, headerString.indexOf("&"));
                                    pageNum = result.substring(result.lastIndexOf('=')+1);
                                    Log.d(TAG, "This is the page number "+pageNum);
                                    page = Integer.parseInt(pageNum);
                                    if (response.isSuccessful()) {

                                        Log.e(TAG, "This is the current page number: "+ page);
                                        Log.i(TAG, "response succesful");

                                        if (tickets != null) {
                                            for (int i = 0; i < model.size(); i++){
                                                //getting current date time so we can get tickets for today only
                                                if (model.get(i).created_at.contains(formatter.format(date))){
                                                    tickets += 1;
                                                }
                                            }
                                            Log.d(TAG, "This is the number of tickets today: "+Integer.toString(tickets));
                                            ticketVolumeController.setTicketVolumeText(Integer.toString(tickets));
                                            /*
                                             * URL: https://stackoverflow.com/questions/3514639/android-java-how-to-subtract-two-times
                                             * Response Time
                                             * int timeInSeconds = diff / 1000;
                                             * int hours, minutes, seconds;
                                             * hours = timeInSeconds / 3600;
                                             * timeInSeconds = timeInSeconds - (hours * 3600);
                                             * minutes = timeInSeconds / 60;
                                             * timeInSeconds = timeInSeconds - (minutes * 60);
                                             * seconds = timeInSeconds;
                                             * */
                                            for (int i = 0; i < model.size(); i++) {
                                                if (model.get(i).created_at.contains(formatter.format(date))) {
                                                    try {
                                                        Date updated_at = dateFormat.parse(model.get(i).updated_at);
                                                        Date created_at = dateFormat.parse(model.get(i).created_at);
                                                        long diff = updated_at.getTime() - created_at.getTime();
                                                        long rT = diff / 1000;
                                                        //Log.i(TAG, "Date #" + i + " UDPATED AT - CREATED AT = " + rT + "s");
                                                        responseTime += rT;
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                        Log.e(TAG, "onResponse: error in parsing created at");
                                                    }
                                                }
                                            }
                                            long avgResponseTime = responseTime / model.size();
                                            Log.i(TAG, "onResponse: This is the average response time: " + avgResponseTime);
                                            ticketVolumeController.setResponseTimeText(Long.toString(avgResponseTime));
                                            // END OF RESPONSE TIME
                                            //BARCHART DATA

                                            for (int i = 0; i < model.size(); i++) {
                                                //Log.d(TAG, model.get(i).status);
                                                if (model.get(i).status.equals("1") && model.get(i).created_at.contains("2018-01")|| model.get(i).status.equals("2") && model.get(i).created_at.contains("2018-01")) {
                                                    janO += 1;
                                                } else if (model.get(i).status.equals("3") && model.get(i).created_at.contains("2018-01") || model.get(i).status.equals("6") && model.get(i).created_at.contains("2018-01")) {
                                                    janU += 1;
                                                } else if (model.get(i).status.equals("4") && model.get(i).created_at.contains("2018-01") || model.get(i).status.equals("5") && model.get(i).created_at.contains("2018-01")) {
                                                    janR += 1;
                                                }

                                                if (model.get(i).status.equals("1") && model.get(i).created_at.contains("2018-02")|| model.get(i).status.equals("2") && model.get(i).created_at.contains("2018-02")) {
                                                    febO += 1;
                                                } else if (model.get(i).status.equals("3") && model.get(i).created_at.contains("2018-02") || model.get(i).status.equals("6") && model.get(i).created_at.contains("2018-02")) {
                                                    febU += 1;
                                                } else if (model.get(i).status.equals("4") && model.get(i).created_at.contains("2018-02") || model.get(i).status.equals("5") && model.get(i).created_at.contains("2018-02")) {
                                                    febR += 1;
                                                }

                                                if (model.get(i).status.equals("1") && model.get(i).created_at.contains("2018-03")|| model.get(i).status.equals("2") && model.get(i).created_at.contains("2018-03")) {
                                                    marO += 1;
                                                } else if (model.get(i).status.equals("3") && model.get(i).created_at.contains("2018-03") || model.get(i).status.equals("6") && model.get(i).created_at.contains("2018-03")) {
                                                    marU += 1;
                                                } else if (model.get(i).status.equals("4") && model.get(i).created_at.contains("2018-03") || model.get(i).status.equals("5") && model.get(i).created_at.contains("2018-03")) {
                                                    marR += 1;
                                                }

                                                if (model.get(i).status.equals("1") && model.get(i).created_at.contains("2018-04")|| model.get(i).status.equals("2") && model.get(i).created_at.contains("2018-04")) {
                                                    aprilO += 1;
                                                } else if (model.get(i).status.equals("3") && model.get(i).created_at.contains("2018-04") || model.get(i).status.equals("6") && model.get(i).created_at.contains("2018-04")) {
                                                    aprilU += 1;
                                                } else if (model.get(i).status.equals("4") && model.get(i).created_at.contains("2018-04") || model.get(i).status.equals("5") && model.get(i).created_at.contains("2018-04")) {
                                                    aprilR += 1;
                                                }
                                            }

                                            Log.i(TAG, Integer.toString(janO));
                                            Log.i(TAG, Integer.toString(janU));
                                            Log.i(TAG, Integer.toString(janR));
                                            Log.i(TAG, Integer.toString(febO));
                                            Log.i(TAG, Integer.toString(febU));
                                            Log.i(TAG, Integer.toString(febR));
                                            Log.i(TAG, Integer.toString(marO));
                                            Log.i(TAG, Integer.toString(marU));
                                            Log.i(TAG, Integer.toString(marR));
                                            Log.i(TAG, Integer.toString(aprilO));
                                            Log.i(TAG, Integer.toString(aprilU));
                                            Log.i(TAG, Integer.toString(aprilR));

                                            int[] opened = {janO, febO, marO, aprilO, 100, 60, 23, 53, 32, 10, 15, 20};
                                            int[] resolved = {janR, febR, marR, aprilR, 70, 40, 8, 28, 18, 7, 5, 15};
                                            int[] unresolved = {janU, febU, marU, aprilU, 30, 20, 15, 25, 16, 3, 10, 5};

                                            for (int i = 0; i < 11; i++) {
                                                barEntries1.add(new BarEntry(i, opened[i]));
                                                barEntries2.add(new BarEntry(i, resolved[i]));
                                                barEntries3.add(new BarEntry(i, unresolved[i]));
                                            }

                                            //graphStackLabels = new String[]{"Opened", "Solved", "Unresolved"};
                                            //3 barDataSets for Opened Solved and Unresolved
                                            barDataSetOpened = new BarDataSet(barEntries1, "Opened");
                                            barDataSetOpened.setColors(Color.BLUE);
                                            barDataSetResolved = new BarDataSet(barEntries2, "Resolved");
                                            barDataSetResolved.setColors(Color.GREEN);
                                            barDataSetUnresolved = new BarDataSet(barEntries3, "Unresolved");
                                            barDataSetUnresolved.setColors(Color.RED);
                                            barDataSetOpened.notifyDataSetChanged();
                                            barDataSetResolved.notifyDataSetChanged();
                                            barDataSetUnresolved.notifyDataSetChanged();
                                            data = new BarData(barDataSetOpened, barDataSetResolved, barDataSetUnresolved);
                                            data.notifyDataChanged();
                                            data.notifyAll();
                                            barChart.notifyAll();
                                            barChart.notifyDataSetChanged();
                                            barChart.setData(data);
                                            barChart.invalidate();
                                            barChart.refreshDrawableState();
                                            barW = data.getBarWidth();
                                            data.setBarWidth(barW / 3);
                                            data.setHighlightEnabled(false);
                                            barChart.setVisibleXRangeMaximum(4);
                                            barChart.moveViewToX(0); //this moves to what index of the month
                                            barChart.setFitBars(true);
                                            barChart.groupBars(0, (barW / 3) / 2, 0);

                                            //END OF BARCHART DATA
                                        } else {
                                            Log.e(TAG, "tickets is null");
                                        }

                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<List<TicketVolumeDataModel>> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                                Log.getStackTraceString(t.getCause());
                                t.printStackTrace();
                            }
                        });

                        Log.d("HERE", "ISCOMPLETE: " + isComplete);
                    }
                    checkCOmplete();
                }

                public void checkCOmplete(){
                        if (!isComplete) {
                            Log.d("HERE", "ISCOMPLETE: " + isComplete + " RUNNING POST DELAY");
                            handler.postDelayed(runnable, 1500);
                        }
                        else{
                            Log.d("HERE", "ISCOMPLETE: " + isComplete + " STOPPING POST DELAY");
                            handler.removeCallbacksAndMessages(runnable);
                        }
                    }

         };
        handler.postDelayed(runnable, 1500);

        /*while(!isComplete){
            Call<List<TicketVolumeDataModel>> call = retrofitInterface.getTicketVolume(authHeader, cacheControl, postmanToken, page,100, "2015-01-19T02:00:00Z");
            Log.d(TAG, "On loop start, page number is :"+ page);
            Log.d(TAG, "headerString: " + headerString);

            if(headerString.isEmpty()){
            Log.d("HERE", "ENTER HERE");
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Call<List<TicketVolumeDataModel>> call = retrofitInterface.getTicketVolume(authHeader, cacheControl, postmanToken, page,100, "2015-01-19T02:00:00Z");

                    call.enqueue(new Callback<List<TicketVolumeDataModel>>() {
                        @Override
                        public void onResponse(Call<List<TicketVolumeDataModel>> call, Response<List<TicketVolumeDataModel>> response) {
                            Headers header = response.headers();
                            headerString = header.get("link");
                            Log.d(TAG, "ON RESPONSE: " + headerString);
                        }

                        @Override
                        public void onFailure(Call<List<TicketVolumeDataModel>> call, Throwable t) {
                            Log.e(TAG, "ERROR", t);
                        }
                    });
                    Log.d(TAG, headerString);
                }
            };
            handler.postDelayed(runnable, 5000);
            handler.removeCallbacksAndMessages(runnable);

        }else{
            Log.d(TAG, headerString);
            isComplete = true;
            break;
        }
//            page++;
    }*/
        ticketLayout.addView(ticketVolumeController); // AFTER FOR LOOP
        marqueeView = findViewById(R.id.marque_scrolling_text);
        Animation marqueeAnim = AnimationUtils.loadAnimation(this, R.anim.marquee_animation);
        marqueeView.startAnimation(marqueeAnim);

    }

    public void checkCOmplete(){
        if (!isComplete) {
            Log.d("HERE", "ISCOMPLETE: " + isComplete + " RUNNING POST DELAY");
            handler.postDelayed(runnable, 5000);
        }
        else{
            Log.d("HERE", "ISCOMPLETE: " + isComplete + " STOPPING POST DELAY");
            handler.removeCallbacksAndMessages(runnable);
        }
    }


    public class MyAxisValueFormatter implements IAxisValueFormatter {


        private String[] mValues;

        public MyAxisValueFormatter(String[] mValues) {
            this.mValues = mValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            if (value >= 0) {
                if (mValues.length > (int) value) {
                    return mValues[(int) value];
                } else return "";
            } else {
                return "";
            }
        }

    }


/*
* ADDED TOAST ON ACTIVITY LIFE CYCLE
* */
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"Created by: Kyle & Keno", Toast.LENGTH_SHORT).show();





    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"On Resume", Toast.LENGTH_SHORT).show();
    }



}

