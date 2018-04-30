package com.android.f45tv.f45techdashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.f45tv.f45techdashboard.Client.RetrofitClient;
import com.android.f45tv.f45techdashboard.Controller.ScheduleController;
import com.android.f45tv.f45techdashboard.Controller.TicketVolumeController;
import com.android.f45tv.f45techdashboard.Controller.TimerController;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.f45tv.f45techdashboard.Interfaces.RetrofitInterface;
import com.android.f45tv.f45techdashboard.Managers.ScheduleManager;
import com.android.f45tv.f45techdashboard.Model.ScheduleDataModel;
import com.android.f45tv.f45techdashboard.Model.TicketVolumeDataModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.JsonArray;

import org.joda.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LeakSun on 04/04/2018.
 * Developed and Modified by Kyle & Keno.
 */

public class MainActivity extends AppCompatActivity {

    private final static String januaryData = "januaryData2018.txt";
    private final static String februaryData = "februaryData2018.txt";
    private final static String marchData = "marchData2018.txt";
    private final static String aprilData = "aprilData2018.txt";
    TextView marqueeView;
    BarChart barChart;
    String[] graphLabels;
    List<BarEntry> barEntries1;
    List<BarEntry> barEntries2;
    List<BarEntry> barEntries3;
    TicketVolumeController ticketVolumeController;
    TimerController timerController;
    FrameLayout timerFrame;
    LinearLayout ticketLayout;
    Integer tickets = 0;
    String TAG = "Kyle";
    BarDataSet barDataSetOpened;
    BarDataSet barDataSetResolved;
    BarDataSet barDataSetUnresolved;
    BarData data;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String headerString = "";
    String pageNum = "1";
    long responseTime = 0;
    float barW;
    int page = 1;
    int janO = 8, janR = 10279, janU = 3;
    int febO = 160, febR = 13912, febU = 2;
    int marO = 138, marR = 13129, marU = 52;
    int aprilO = 0, aprilR = 0, aprilU = 0;
    boolean isComplete = false;
    Handler handler = new Handler();
    Runnable runnable;

    //Schedule Declarations
    ScheduleManager shiftManager;
    ScheduleController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "45Dashboard");
        if (!mediaStorageDir.exists())
            if (!mediaStorageDir.mkdirs()) Log.d("App", "failed to create directory");

        //Schedule
        shiftManager = new ScheduleManager();
        controller = new ScheduleController(this);

        //Ticket Volume Controller
        ticketVolumeController = new TicketVolumeController(this);
        ticketLayout = findViewById(R.id.ticketFrame);

        //Timer Controller
        timerFrame = findViewById(R.id.timerFrame);
        timerController = new TimerController(this);
        timerController.setTimer(TimeUnit.SECONDS.toMillis(10), 1000);
        timerFrame.addView(timerController);

        //Methods
        makeGraph();
        startFreshdeskRequest();
        ticketLayout.addView(ticketVolumeController); // AFTER FOR LOOP
        //Marquee
        marqueeView = findViewById(R.id.marque_scrolling_text);
        Animation marqueeAnim = AnimationUtils.loadAnimation(this, R.anim.marquee_animation);
        marqueeView.startAnimation(marqueeAnim);

        //Deputy
        startDeputyRequest();

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

    /* ADDED TOAST ON ACTIVITY LIFE CYCLE */
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Created by: Kyle & Keno", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "On Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void makeGraph() {
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

    }

    protected void startFreshdeskRequest() {
        //retrofitclient
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.setBaseUrl("https://f45training.freshdesk.com/");
        final String authHeader = "Basic V1U3Y0ZJY0lhNVZDbHE4TnM1Mjo=";
        final String cacheControl = "no-cache";
        final String postmanToken = "e601edd5-eb58-430f-a43a-ea74b8d6ce6c";
        final RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        String dateString = "";

//        if(formatter.format(date).contains("2018-01")){
//            dateString = "2018-01-01T00:00:00Z";
//        }
//        else if (formatter.format(date).contains("2018-02")){
//            dateString = "2018-02-01T00:00:00Z";
//        }
//        else if (formatter.format(date).contains("2018-03")){
//            dateString = "2018-03-01T00:00:00Z";
//        }
//        else if (formatter.format(date).contains("2018-04")){
//            dateString = "2018-04-01T00:00:00Z";
//        }
//
//        final String finalDateString = dateString;

        runnable = new Runnable() {
            @Override
            public void run() {
                Call<List<TicketVolumeDataModel>> call = retrofitInterface.getTicketVolume(authHeader, cacheControl, postmanToken, page, 100, "2018-04-29T00:00:00Z");
                Log.d(TAG, "On loop start, page number is :" + page);
                Log.d(TAG, "headerString: " + headerString);
                if (headerString.isEmpty()) {
                    Log.d("HERE", "ENTER HERE");
                    call.enqueue(new Callback<List<TicketVolumeDataModel>>() {
                        @Override
                        public void onResponse(Call<List<TicketVolumeDataModel>> call, Response<List<TicketVolumeDataModel>> response) {
                            Headers header = response.headers();
                            Log.d(TAG, header.toString());
                            headerString = header.get("link");
                            Log.d(TAG, "ON RESPONSE: " + headerString);
                        }

                        @Override
                        public void onFailure(Call<List<TicketVolumeDataModel>> call, Throwable t) {
                            Log.e(TAG, "onFailure: Error ", t);
                        }
                    });
                } else {
                    //code on NOTEPAD
                    call.enqueue(new Callback<List<TicketVolumeDataModel>>() {
                        @Override
                        public void onResponse(Call<List<TicketVolumeDataModel>> call, Response<List<TicketVolumeDataModel>> response) {
                            //updated at - created at = summation of everything / model size
                            ArrayList<TicketVolumeDataModel> model = (ArrayList<TicketVolumeDataModel>) response.body();
                            Headers headers = response.headers(); // I GOT THE LINK HEADER I NEED TO UTILIZE THIS SHIT
                            Log.d(TAG, headers.toString());
                            if (headers.get("link") == null) {
                                isComplete = true;
                                try {
                                    checkComplete();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Log.d(TAG, "ISCOMPLETE: " + isComplete);
                            } else {
                                headerString = headers.get("link");
                                String result = headerString.substring(headerString.indexOf("?") + 1, headerString.indexOf("&"));
                                pageNum = result.substring(result.lastIndexOf('=') + 1);
                                Log.d(TAG, "This is the page number " + pageNum);
                                page = Integer.parseInt(pageNum);

                                if (response.isSuccessful()) {

                                    //sharedPreferences = getSharedPreferences("dataRecord", Context.MODE_PRIVATE);
                                    //getDataRecord();

                                    Log.e(TAG, "This is the current page number: " + page);
                                    Log.i(TAG, "response succesful");

                                    if (tickets != null) {
                                        for (int i = 0; i < model.size(); i++) {
                                            //getting current date time so we can get tickets for today only
                                            TicketVolumeDataModel tvdm = response.body().get(i);
                                            TicketVolumeDataModel.CustomFields a = tvdm.custom_fields;
                                            if (model.get(i).created_at.contains(formatter.format(date))) {
                                                if (a.department != null) { //&& a.department.equals("Tech Systems")
                                                    tickets += 1;
                                                    //Log.e("TEST123", "onResponse: " + "index " + i + " " + a.department);
                                                }
                                            }
                                        }
                                        Log.d(TAG, "This is the number of tickets today: " + Integer.toString(tickets));
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
                                            TicketVolumeDataModel tvdm = response.body().get(i);
                                            TicketVolumeDataModel.CustomFields a = tvdm.custom_fields;
                                            if (model.get(i).created_at.contains(formatter.format(date))) {
                                                if (a.department != null) {
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
                                        }
                                        long avgResponseTime = responseTime / tickets; //change to tickets
                                        Log.i(TAG, "onResponse: This is the average response time: " + avgResponseTime);
                                        ticketVolumeController.setResponseTimeText(Long.toString(avgResponseTime));
                                        // END OF RESPONSE TIME
                                        //BARCHART DATA
                                        for (int i = 0; i < model.size(); i++) {
//                                            Log.d(TAG, model.get(i).status);
//                                            if (model.get(i).status.equals("2") && model.get(i).created_at.contains("2018-01")) {
//                                                janO += 1;
//                                            } else if (model.get(i).status.equals("3") && model.get(i).created_at.contains("2018-01") || model.get(i).status.equals("6") && model.get(i).created_at.contains("2018-01")) {
//                                                janU += 1;
//                                            } else if (model.get(i).status.equals("4") && model.get(i).created_at.contains("2018-01") || model.get(i).status.equals("5") && model.get(i).created_at.contains("2018-01")) {
//                                                janR += 1;
//                                            }
//
//                                            if (model.get(i).status.equals("2") && model.get(i).created_at.contains("2018-02")) {
//                                                febO += 1;
//                                            } else if (model.get(i).status.equals("3") && model.get(i).created_at.contains("2018-02") || model.get(i).status.equals("6") && model.get(i).created_at.contains("2018-02")) {
//                                                febU += 1;
//                                            } else if (model.get(i).status.equals("4") && model.get(i).created_at.contains("2018-02") || model.get(i).status.equals("5") && model.get(i).created_at.contains("2018-02")) {
//                                                febR += 1;
//                                            }
//
//                                            if (model.get(i).status.equals("2") && model.get(i).created_at.contains("2018-03")) {
//                                                marO += 1;
//                                            } else if (model.get(i).status.equals("3") && model.get(i).created_at.contains("2018-03") || model.get(i).status.equals("6") && model.get(i).created_at.contains("2018-03")) {
//                                                marU += 1;
//                                            } else if (model.get(i).status.equals("4") && model.get(i).created_at.contains("2018-03") || model.get(i).status.equals("5") && model.get(i).created_at.contains("2018-03")) {
//                                                marR += 1;
//                                            }

                                            if (model.get(i).status.equals("2") && model.get(i).created_at.contains("2018-04")) {
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


                                        //END OF BARCHART DATA
                                    } else {
                                        Log.e(TAG, "tickets is null");
                                    }

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<TicketVolumeDataModel>> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage(), t);
                            Log.getStackTraceString(t.getCause());
                            t.printStackTrace();
                        }
                    });


                    Log.d("HERE", "ISCOMPLETE: " + isComplete);
                }
                try {
                    checkComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void checkComplete() throws IOException {
                if (!isComplete) {
                    Log.d("HERE", "ISCOMPLETE: " + isComplete + " RUNNING POST DELAY");
                    handler.postDelayed(runnable, 1500);
                } else {
                    Log.d("HERE", "ISCOMPLETE: " + isComplete + " STOPPING POST DELAY");


                    JSONObject dataObject = new JSONObject();
                    JsonArray janArray = new JsonArray();
                    janArray.add("Open:"+Integer.toString(janO));
                    janArray.add("Unresolved:" + Integer.toString(janU));
                    janArray.add("Resolved:" + Integer.toString(janR));
                    JsonArray febArray = new JsonArray();
                    febArray.add("Open:" + Integer.toString(febO));
                    febArray.add("Unresolved:" + Integer.toString(febU));
                    febArray.add("Resolved:" + Integer.toString(febR));
                    JsonArray marArray = new JsonArray();
                    marArray.add("Open:" + Integer.toString(marO));
                    marArray.add("Unresolved:" + Integer.toString(marU));
                    marArray.add("Resolved:" + Integer.toString(marR));
                    JsonArray aprArray = new JsonArray();
                    aprArray.add("Open:" + Integer.toString(aprilO));
                    aprArray.add("Unresolved:" + Integer.toString(aprilU));
                    aprArray.add("Resolved:" + Integer.toString(aprilR));
                    try {
                        dataObject.put("januaryData", janArray);
                        dataObject.put("februaryData", febArray);
                        dataObject.put("marchData", marArray);
                        dataObject.put("aprilData", aprArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String content = dataObject.toString();
                    Log.d(TAG, "checkComplete: "+ content);

                    // Android not creating file
                    // https://stackoverflow.com/questions/20202966/android-not-creating-file
                    File directory = new File("/mnt/internal_sd/F45Dashboard/");
                    String filename = "barData.txt";

                    boolean isDirectoryCreated = directory.exists();
                    if (!isDirectoryCreated){
                        directory.mkdirs();
                        directory.createNewFile();
                        Log.d(TAG, "directory created..");
                    }

                    if(isDirectoryCreated){
                        File file = new File(directory, filename);
                        file.createNewFile();
                        file.canWrite();
                        file.canRead();
                        Log.d(TAG, "file created.. @ "+ file.getPath());
                        FileOutputStream fos = new FileOutputStream(file);
                        FileWriter fw = new FileWriter(fos.getFD());
                        try {
                            fw.write(content);
                            fw.close();
                            Log.d(TAG, "content written..");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            fos.getFD().sync();
                            fos.close();
                        }
                    }

                    int[] opened = {janO, febO, marO, aprilO, 0, 0, 0, 0, 0, 0, 0, 0};
                    int[] resolved = {janR, febR, marR, aprilR, 0, 0, 0, 0, 0, 0, 0, 0};
                    int[] unresolved = {janU, febU, marU, aprilU, 0, 0, 0, 0, 0, 0, 0, 0};

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
                    data = new BarData(barDataSetOpened, barDataSetResolved, barDataSetUnresolved);
                    barChart.setData(data);
                    barDataSetOpened.notifyDataSetChanged();
                    barDataSetResolved.notifyDataSetChanged();
                    barDataSetUnresolved.notifyDataSetChanged();
                    barChart.invalidate();
                    barChart.refreshDrawableState();
                    barW = data.getBarWidth();
                    data.setBarWidth(barW / 3);
                    data.setHighlightEnabled(false);
                    barChart.setVisibleXRangeMaximum(4);
                    barChart.moveViewToX(0); //this moves to what index of the month
                    barChart.setFitBars(true);
                    barChart.groupBars(0, (barW / 3) / 2, 0);
                    handler.removeCallbacksAndMessages(runnable);
                }
            }

        };
        handler.postDelayed(runnable, 1500);
    }

    protected void startDeputyRequest() {
        RetrofitClient retrofitClientD = new RetrofitClient();
        retrofitClientD.setBaseUrl("https://a3c3f816065445.as.deputy.com/");
        final String authHeaderD = "Bearer ffc0b18fb4ffd88c70dd523cb38259e5";
        final String cacheControlD = "no-cache";
        final String postmanTokenD = "ac5c988a-6c35-48e4-a491-67d301b1fa12";
        final RetrofitInterface retrofitInterfaceD = RetrofitClient.getClient().create(RetrofitInterface.class);

        Call<List<ScheduleDataModel>> call = retrofitInterfaceD.getSchedule(authHeaderD, cacheControlD, postmanTokenD);
        call.enqueue(new Callback<List<ScheduleDataModel>>() {
            @Override
            public void onResponse(Call<List<ScheduleDataModel>> call, Response<List<ScheduleDataModel>> response) {

                ArrayList<ScheduleDataModel> model = (ArrayList<ScheduleDataModel>) response.body();
                Log.d(TAG, "onResponse: " + model.get(0).Employee);


            }

            @Override
            public void onFailure(Call<List<ScheduleDataModel>> call, Throwable t) {

            }
        });
    }

}

