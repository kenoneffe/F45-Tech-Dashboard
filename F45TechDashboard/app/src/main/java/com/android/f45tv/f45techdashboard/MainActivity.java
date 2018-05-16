package com.android.f45tv.f45techdashboard;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.f45tv.f45techdashboard.Client.RetrofitClient;
import com.android.f45tv.f45techdashboard.Controller.NotificationAdapter;
import com.android.f45tv.f45techdashboard.Controller.NotificationController;
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
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by LeakSun on 04/04/2018.
 * Developed and Modified by Kyle & Keno.
 */
public class MainActivity extends AppCompatActivity {

    Future futureProcess;
    String lastonlineString;
    LinearLayout loadingscreen;
    Boolean isDirectoryCreated;
    Boolean isFileCreated;
    TextView marqueeView;
    View loadingView;
    View barChartView;
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
    Integer ticketsv2 = 0;
    String TAG = "Kyle";
    BarDataSet barDataSetOpened;
    BarDataSet barDataSetResolved;
    BarDataSet barDataSetUnresolved;
    BarData data;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat getYear = new SimpleDateFormat("yyyy");
    SimpleDateFormat getMonth = new SimpleDateFormat("MM");
    SimpleDateFormat getDay = new SimpleDateFormat("dd");
    Date date = new Date();
    String currentYear = getYear.format(date); // get current year
    String currentMonth = getMonth.format(date); // get current month
    String currentDay = getDay.format(date); // get current day
    String headerString = "";
    String pageNum = "1";
    int prevPage = 1;
    int prevPageNotif = 1;
    long responseTime = 0;
    long responseTime2 = 0;
    float barW;
    int count = 0;
    int page = 1;
    int pageNotif = 1;
    int janO = 0, janR = 0, janU = 0;
    int febO = 0, febR = 0, febU = 0;
    int marO = 0, marR = 0, marU = 0;
    int aprilO = 0, aprilR = 0, aprilU = 0;
    int mayO = 0, mayR = 0, mayU = 0;
    int junO = 0, junR = 0, junU = 0;
    int julO = 0, julR = 0, julU = 0;
    int augO = 0, augR = 0, augU = 0;
    int sepO = 0, sepR = 0, sepU = 0;
    int octO = 0, octR = 0, octU = 0;
    int novO = 0, novR = 0, novU = 0;
    int decO = 0, decR = 0, decU = 0;
    boolean isComplete = false;
    boolean isCompleteUpdate = false;
    boolean firstRunThrough = false;
    Handler handler = new Handler();
    Handler handler2 = new Handler();
    Handler handler3 = new Handler();
    Runnable runnable;
    Runnable runnable2;
    Runnable runnable3;
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    List<NotificationController> notificationList;
    long timeleft;
    boolean doubleBackToExitPressedOnce = false;

    ProgressBar progressBar;

    //Schedule Declarations
    ScheduleManager shiftManager;
    ScheduleController controller;

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    ExecutorService executorService2 = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.spin_kit);
        loadingscreen = findViewById(R.id.loading_screen);
        //notification
        notificationList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(this, notificationList);
        recyclerView.setAdapter(adapter);

        //loading view
        loadingView = findViewById(R.id.loading_layout);
        //loadingView.setVisibility(View.VISIBLE);
        barChartView = findViewById(R.id.chart);
        //barChartView.setVisibility(View.GONE);
        //Schedule
        shiftManager = new ScheduleManager();
        controller = new ScheduleController(this);
        //time
        timerController = new TimerController(this);
        timerFrame = findViewById(R.id.timerFrame);
        timerFrame.addView(timerController);
        //Ticket Volume Controller
        ticketVolumeController = new TicketVolumeController(this);
        ticketLayout = findViewById(R.id.ticketFrame);
        //Methods
        makeGraphFuture();
        startFreshdeskRequest();
        ticketLayout.addView(ticketVolumeController); // AFTER FOR LOOP
        //Marquee
        marqueeView = findViewById(R.id.marque_scrolling_text);
        marqueeView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        marqueeView.setSelected(true);
        marqueeView.setSingleLine(true);
        //Deputy
//        loadingscreen.setVisibility(View.GONE);
        startKlipfolio();
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
    protected void onRestart() {
        super.onRestart();
        timeleft = timerController.getTimeleft();
        Log.d(TAG, "onResume: millis " + timeleft);
        timerController.setTimer(timeleft, 1000);
        Toast.makeText(this, "On Resume ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timerController.pauseCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerController.pauseCount();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private Future makeGraphFuture(){
        return executorService2.submit(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                Log.d(TAG, "future: thread name " + threadName);
                makeGraph();
            }
        });
    }

    private Future updateTicketsFuture() {
        return executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    updateTickets();
//                    updateNotifications();
                    Log.d(TAG, "future: tickets " + tickets);
                    Log.d(TAG, "future: ticketsv2 " + ticketsv2);
                    String threadName = Thread.currentThread().getName();
                    Log.d(TAG, "future: thread name " + threadName);
                    if (ticketsv2 <= tickets) {
                        ticketsv2 = 0;
                        Log.d(TAG, "future: tickets " + tickets);
                        Log.d(TAG, "future: ticketsv2 " + ticketsv2);
                    } else {
                        tickets = ticketsv2;
                        ticketsv2 = 0;
                        Log.d(TAG, "future: This is the updated number of tickets today: " + Integer.toString(tickets));
                        ticketVolumeController.setTicketVolumeText(Integer.toString(tickets));
                        long avgResponseTime = responseTime2 / tickets;
                        Log.i(TAG, "future: This is the updated average response time: " + avgResponseTime);
                        ticketVolumeController.setResponseTimeText(Long.toString(avgResponseTime));
                    }
                    futureProcess = updateTicketsFuture();
                    Log.d("THREAD", "running futureThread: " + futureProcess);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
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

        //CHECKING FOR FILE DIRECTORY
        File directory = new File("/mnt/internal_sd/F45Dashboard/");
        String filename = "barData.txt";
        final File file = new File(directory, filename);
        isFileCreated = file.exists();
        isDirectoryCreated = directory.exists();


        if (!(isDirectoryCreated && isFileCreated)) {
            dateString = currentYear + "-01-01T00:00:00Z";
        } else {
            if (formatter.format(date).contains(currentYear + "-" + currentMonth)) {
                dateString = currentYear + "-" + currentMonth + "-01T00:00:00Z";
            }
        }


        final String finalDateString = dateString;

        runnable = new Runnable() {
            @Override
            public void run() {
                Call<List<TicketVolumeDataModel>> call = retrofitInterface.getTicketVolume(authHeader, cacheControl, postmanToken, page, 100, finalDateString);
                Log.d(TAG, "On loop start, page number is :" + page);
                Log.d(TAG, "headerString: " + headerString);
                if (headerString == null) {
                    Log.d(TAG, "headerString: null");
                }
                else if (headerString.isEmpty()) {
                    Log.d("HERE", "ENTER HERE");
                    call.enqueue(new Callback<List<TicketVolumeDataModel>>() {
                        @Override
                        public void onResponse(Call<List<TicketVolumeDataModel>> call, Response<List<TicketVolumeDataModel>> response) {
                            Headers header = response.headers();
                            //Log.d(TAG, header.toString());
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
                            Headers headers = response.headers();
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
                                page = Integer.parseInt(pageNum);
                                Log.e(TAG, "This is the prev/current page number: " + prevPage);
                                Log.e(TAG, "This is the next page number " + page);
                                //TO AVOID DUPLICATES
                                if (prevPage != page && prevPage < page) {
                                    prevPage = page;
                                    Log.e(TAG, "PROCEED");
                                    if (tickets != null) {
                                        for (int i = 0; i < model.size(); i++) {
                                            //TICKETS FOR TODAY
                                            //getting current date time so we can get tickets for today only
                                            TicketVolumeDataModel tvdm = model.get(i);
                                            TicketVolumeDataModel.CustomFields a = tvdm.custom_fields;
                                            if (model.get(i).created_at.contains(formatter.format(date)) && a.department != null && a.department.equals("Tech Systems")) {
                                                NotificationController reference = new NotificationController(Integer.parseInt(model.get(i).id), model.get(i).subject, model.get(i).source, model.get(i).priority);
                                                int arraySize = notificationList.size();
                                                Log.d(TAG, "onStartNotifications: array size " + arraySize);
                                                Log.d(TAG, "onStartNotifications: not empty");
                                                Log.d(TAG, "onStartNotifications: count is " + count);
                                                if (count > arraySize) {
                                                    count = 0;
                                                }
                                                boolean isDup = isDuplicate(notificationList, reference);
                                                if (!isDup) {
                                                    Log.d(TAG, "!isDup: adding reference " + reference.getId() + " to NOTIFICATIONLIST");
                                                    notificationList.add(reference);
                                                    count++;
                                                    adapter.notifyDataSetChanged();
                                                    adapter.setNotificationList(notificationList);
                                                } else {
                                                    Log.d(TAG, "isDup: skipped due to duplicate entry...");
                                                }
                                                Log.d(TAG, "Adding:" + "index: " + i + " subject: " + model.get(i).subject + " source: " + Integer.parseInt(model.get(i).source) + " priority: " + Integer.parseInt(model.get(i).priority));
                                            }

                                            if (model.get(i).created_at.contains(formatter.format(date))) {
                                                if (a.department != null && a.department.equals("Tech Systems")) { //&& a.department.equals("Tech Systems")
                                                    tickets += 1;
                                                }
                                            }
                                            Log.d(TAG, "This is the number of tickets today: " + Integer.toString(tickets));
                                            ticketVolumeController.setTicketVolumeText(Integer.toString(tickets));
                                            //END TICKETS TODAY
                                            /*
                                             * URL: https://stackoverflow.com/questions/3514639/android-java-how-to-subtract-two-times
                                             * Response Time
                                             * */
                                            if (model.get(i).created_at.contains(formatter.format(date))) {
                                                if (a.department != null) {
                                                    try {
                                                        Date updated_at = dateFormat.parse(model.get(i).updated_at);
                                                        Date created_at = dateFormat.parse(model.get(i).created_at);
                                                        long diff = updated_at.getTime() - created_at.getTime();
                                                        long rT = diff / 1000;
                                                        responseTime += rT;
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                        Log.e(TAG, "onResponse: error in parsing created at");
                                                    }
                                                }
                                            }
                                            long avgResponseTime = 1;
                                            if (tickets != 0) {
                                                avgResponseTime = responseTime / tickets; //change to tickets
                                            }

                                            Log.i(TAG, "onResponse: This is the average response time: " + avgResponseTime);
                                            ticketVolumeController.setResponseTimeText(Long.toString(avgResponseTime));
                                            // END OF RESPONSE TIME
                                        }


                                        //BARCHART DATA
                                        for (int i = 0; i < model.size(); i++) {
                                            if (!(isDirectoryCreated && isFileCreated)) {
                                                if (model.get(i).created_at.contains(currentYear + "-01")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            janO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            janU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            janR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-02")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            febO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            febU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            febR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-03")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            marO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            marU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            marR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-04")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            aprilO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            aprilU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            aprilR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-05")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            mayO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            mayU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            mayR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-06")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            junO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            junU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            junR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-07")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            julO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            julU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            julR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-08")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            augO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            augU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            augR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-09")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            sepO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            sepU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            sepR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-10")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            octO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            octU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            octR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-11")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            novO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            novU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            novR += 1;
                                                            break;
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-12")) {
                                                    switch (model.get(i).status) {
                                                        case "2":
                                                            decO += 1;
                                                            break;
                                                        case "3":
                                                        case "6":
                                                            decU += 1;
                                                            break;
                                                        case "4":
                                                        case "5":
                                                            decR += 1;
                                                            break;
                                                    }
                                                }
                                            } else {
                                                if (model.get(i).created_at.contains(currentYear + "-01") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        janO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        janU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        janR += 1;
                                                        Log.i(TAG, "ADDING: JAN");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-02") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        febO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        febU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        febR += 1;
                                                        Log.i(TAG, "ADDING: FEB");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-03") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        marO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        marU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        marR += 1;
                                                        Log.i(TAG, "ADDING: MAR");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-04") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        aprilO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        aprilU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        aprilR += 1;
                                                        Log.i(TAG, "ADDING: APR");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-05") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        mayO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        mayU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        mayR += 1;
                                                        Log.i(TAG, "ADDING: MAY");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-06") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        junO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        junU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        junR += 1;
                                                        Log.i(TAG, "ADDING: JUN");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-07") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        julO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        julU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        julR += 1;
                                                        Log.i(TAG, "ADDING: JUL");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-08") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        augO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        augU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        augR += 1;
                                                        Log.i(TAG, "ADDING: AUG");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-09") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        sepO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        sepU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        sepR += 1;
                                                        Log.i(TAG, "ADDING: SEP");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-10") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        octO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        octU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        octR += 1;
                                                        Log.i(TAG, "ADDING: OCT");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-11") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        novO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        novU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        novR += 1;
                                                        Log.i(TAG, "ADDING: NOV");
                                                    }
                                                } else if (model.get(i).created_at.contains(currentYear + "-12") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    if ("2".equals(model.get(i).status)) {
                                                        decO += 1;

                                                    } else if ("3".equals(model.get(i).status) || "6".equals(model.get(i).status)) {
                                                        decU += 1;

                                                    } else if ("4".equals(model.get(i).status) || "5".equals(model.get(i).status)) {
                                                        decR += 1;
                                                        Log.i(TAG, "ADDING: DEC");
                                                    }
                                                }
                                            }
                                        }
                                        //https://kodejava.org/how-do-i-read-file-using-fileinputstream/
                                        // Get the temporary directory. We'll read the data.txt file
                                        // from this directory.

                                        Log.i(TAG, "BEFORE TEXT:" + mayO + " " + mayR + " " + mayU);

                                        StringBuilder builder = new StringBuilder();
                                        FileInputStream fis = null;
                                        try {
                                            // Create a FileInputStream to read the file.
                                            fis = new FileInputStream(file);

                                            int data;
                                            // Read the entire file data. When -1 is returned it
                                            // means no more content to read.
                                            while ((data = fis.read()) != -1) {
                                                builder.append((char) data);
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (fis != null) {
                                                try {
                                                    fis.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        // Print the content of the file
                                        String textfile = builder.toString();
                                        try {
                                            JSONObject jsonObj = new JSONObject(textfile);
                                            for (int i = 0; i < model.size(); i++) {
                                                if (model.get(i).created_at.contains(currentYear + "-01") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {

                                                } else if (model.get(i).created_at.contains(currentYear + "-02") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-03") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-04") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                    marO = (Integer) jsonObj.getJSONObject("marchData").get("Open");
                                                    marU = (Integer) jsonObj.getJSONObject("marchData").get("Unresolved");
                                                    marR = (Integer) jsonObj.getJSONObject("marchData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-05") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                    marO = (Integer) jsonObj.getJSONObject("marchData").get("Open");
                                                    marU = (Integer) jsonObj.getJSONObject("marchData").get("Unresolved");
                                                    marR = (Integer) jsonObj.getJSONObject("marchData").get("Resolved");
                                                    aprilO = (Integer) jsonObj.getJSONObject("aprilData").get("Open");
                                                    aprilU = (Integer) jsonObj.getJSONObject("aprilData").get("Unresolved");
                                                    aprilR = (Integer) jsonObj.getJSONObject("aprilData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-06") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                    marO = (Integer) jsonObj.getJSONObject("marchData").get("Open");
                                                    marU = (Integer) jsonObj.getJSONObject("marchData").get("Unresolved");
                                                    marR = (Integer) jsonObj.getJSONObject("marchData").get("Resolved");
                                                    aprilO = (Integer) jsonObj.getJSONObject("aprilData").get("Open");
                                                    aprilU = (Integer) jsonObj.getJSONObject("aprilData").get("Unresolved");
                                                    aprilR = (Integer) jsonObj.getJSONObject("aprilData").get("Resolved");
                                                    mayO = (Integer) jsonObj.getJSONObject("mayData").get("Open");
                                                    mayU = (Integer) jsonObj.getJSONObject("mayData").get("Unresolved");
                                                    mayR = (Integer) jsonObj.getJSONObject("mayData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-07") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                    marO = (Integer) jsonObj.getJSONObject("marchData").get("Open");
                                                    marU = (Integer) jsonObj.getJSONObject("marchData").get("Unresolved");
                                                    marR = (Integer) jsonObj.getJSONObject("marchData").get("Resolved");
                                                    aprilO = (Integer) jsonObj.getJSONObject("aprilData").get("Open");
                                                    aprilU = (Integer) jsonObj.getJSONObject("aprilData").get("Unresolved");
                                                    aprilR = (Integer) jsonObj.getJSONObject("aprilData").get("Resolved");
                                                    mayO = (Integer) jsonObj.getJSONObject("mayData").get("Open");
                                                    mayU = (Integer) jsonObj.getJSONObject("mayData").get("Unresolved");
                                                    mayR = (Integer) jsonObj.getJSONObject("mayData").get("Resolved");
                                                    junO = (Integer) jsonObj.getJSONObject("juneData").get("Open");
                                                    junU = (Integer) jsonObj.getJSONObject("juneData").get("Unresolved");
                                                    junR = (Integer) jsonObj.getJSONObject("juneData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-08") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                    marO = (Integer) jsonObj.getJSONObject("marchData").get("Open");
                                                    marU = (Integer) jsonObj.getJSONObject("marchData").get("Unresolved");
                                                    marR = (Integer) jsonObj.getJSONObject("marchData").get("Resolved");
                                                    aprilO = (Integer) jsonObj.getJSONObject("aprilData").get("Open");
                                                    aprilU = (Integer) jsonObj.getJSONObject("aprilData").get("Unresolved");
                                                    aprilR = (Integer) jsonObj.getJSONObject("aprilData").get("Resolved");
                                                    mayO = (Integer) jsonObj.getJSONObject("mayData").get("Open");
                                                    mayU = (Integer) jsonObj.getJSONObject("mayData").get("Unresolved");
                                                    mayR = (Integer) jsonObj.getJSONObject("mayData").get("Resolved");
                                                    junO = (Integer) jsonObj.getJSONObject("juneData").get("Open");
                                                    junU = (Integer) jsonObj.getJSONObject("juneData").get("Unresolved");
                                                    junR = (Integer) jsonObj.getJSONObject("juneData").get("Resolved");
                                                    julO = (Integer) jsonObj.getJSONObject("julyData").get("Open");
                                                    julU = (Integer) jsonObj.getJSONObject("julyData").get("Unresolved");
                                                    julR = (Integer) jsonObj.getJSONObject("julyData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-09") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                    marO = (Integer) jsonObj.getJSONObject("marchData").get("Open");
                                                    marU = (Integer) jsonObj.getJSONObject("marchData").get("Unresolved");
                                                    marR = (Integer) jsonObj.getJSONObject("marchData").get("Resolved");
                                                    aprilO = (Integer) jsonObj.getJSONObject("aprilData").get("Open");
                                                    aprilU = (Integer) jsonObj.getJSONObject("aprilData").get("Unresolved");
                                                    aprilR = (Integer) jsonObj.getJSONObject("aprilData").get("Resolved");
                                                    mayO = (Integer) jsonObj.getJSONObject("mayData").get("Open");
                                                    mayU = (Integer) jsonObj.getJSONObject("mayData").get("Unresolved");
                                                    mayR = (Integer) jsonObj.getJSONObject("mayData").get("Resolved");
                                                    junO = (Integer) jsonObj.getJSONObject("juneData").get("Open");
                                                    junU = (Integer) jsonObj.getJSONObject("juneData").get("Unresolved");
                                                    junR = (Integer) jsonObj.getJSONObject("juneData").get("Resolved");
                                                    julO = (Integer) jsonObj.getJSONObject("julyData").get("Open");
                                                    julU = (Integer) jsonObj.getJSONObject("julyData").get("Unresolved");
                                                    julR = (Integer) jsonObj.getJSONObject("julyData").get("Resolved");
                                                    augO = (Integer) jsonObj.getJSONObject("augustData").get("Open");
                                                    augU = (Integer) jsonObj.getJSONObject("augustData").get("Unresolved");
                                                    augR = (Integer) jsonObj.getJSONObject("augustData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-10") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                    marO = (Integer) jsonObj.getJSONObject("marchData").get("Open");
                                                    marU = (Integer) jsonObj.getJSONObject("marchData").get("Unresolved");
                                                    marR = (Integer) jsonObj.getJSONObject("marchData").get("Resolved");
                                                    aprilO = (Integer) jsonObj.getJSONObject("aprilData").get("Open");
                                                    aprilU = (Integer) jsonObj.getJSONObject("aprilData").get("Unresolved");
                                                    aprilR = (Integer) jsonObj.getJSONObject("aprilData").get("Resolved");
                                                    mayO = (Integer) jsonObj.getJSONObject("mayData").get("Open");
                                                    mayU = (Integer) jsonObj.getJSONObject("mayData").get("Unresolved");
                                                    mayR = (Integer) jsonObj.getJSONObject("mayData").get("Resolved");
                                                    junO = (Integer) jsonObj.getJSONObject("juneData").get("Open");
                                                    junU = (Integer) jsonObj.getJSONObject("juneData").get("Unresolved");
                                                    junR = (Integer) jsonObj.getJSONObject("juneData").get("Resolved");
                                                    julO = (Integer) jsonObj.getJSONObject("julyData").get("Open");
                                                    julU = (Integer) jsonObj.getJSONObject("julyData").get("Unresolved");
                                                    julR = (Integer) jsonObj.getJSONObject("julyData").get("Resolved");
                                                    augO = (Integer) jsonObj.getJSONObject("augustData").get("Open");
                                                    augU = (Integer) jsonObj.getJSONObject("augustData").get("Unresolved");
                                                    augR = (Integer) jsonObj.getJSONObject("augustData").get("Resolved");
                                                    sepO = (Integer) jsonObj.getJSONObject("septemberData").get("Open");
                                                    sepU = (Integer) jsonObj.getJSONObject("septemberData").get("Unresolved");
                                                    sepR = (Integer) jsonObj.getJSONObject("septemberData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-11") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                    marO = (Integer) jsonObj.getJSONObject("marchData").get("Open");
                                                    marU = (Integer) jsonObj.getJSONObject("marchData").get("Unresolved");
                                                    marR = (Integer) jsonObj.getJSONObject("marchData").get("Resolved");
                                                    aprilO = (Integer) jsonObj.getJSONObject("aprilData").get("Open");
                                                    aprilU = (Integer) jsonObj.getJSONObject("aprilData").get("Unresolved");
                                                    aprilR = (Integer) jsonObj.getJSONObject("aprilData").get("Resolved");
                                                    mayO = (Integer) jsonObj.getJSONObject("mayData").get("Open");
                                                    mayU = (Integer) jsonObj.getJSONObject("mayData").get("Unresolved");
                                                    mayR = (Integer) jsonObj.getJSONObject("mayData").get("Resolved");
                                                    junO = (Integer) jsonObj.getJSONObject("juneData").get("Open");
                                                    junU = (Integer) jsonObj.getJSONObject("juneData").get("Unresolved");
                                                    junR = (Integer) jsonObj.getJSONObject("juneData").get("Resolved");
                                                    julO = (Integer) jsonObj.getJSONObject("julyData").get("Open");
                                                    julU = (Integer) jsonObj.getJSONObject("julyData").get("Unresolved");
                                                    julR = (Integer) jsonObj.getJSONObject("julyData").get("Resolved");
                                                    augO = (Integer) jsonObj.getJSONObject("augustData").get("Open");
                                                    augU = (Integer) jsonObj.getJSONObject("augustData").get("Unresolved");
                                                    augR = (Integer) jsonObj.getJSONObject("augustData").get("Resolved");
                                                    sepO = (Integer) jsonObj.getJSONObject("septemberData").get("Open");
                                                    sepU = (Integer) jsonObj.getJSONObject("septemberData").get("Unresolved");
                                                    sepR = (Integer) jsonObj.getJSONObject("septemberData").get("Resolved");
                                                    octO = (Integer) jsonObj.getJSONObject("octoberData").get("Open");
                                                    octU = (Integer) jsonObj.getJSONObject("octoberData").get("Unresolved");
                                                    octR = (Integer) jsonObj.getJSONObject("octoberData").get("Resolved");
                                                } else if (model.get(i).created_at.contains(currentYear + "-12") && model.get(i).created_at.contains(currentYear + "-" + currentMonth)) {
                                                    janO = (Integer) jsonObj.getJSONObject("januaryData").get("Open");
                                                    janU = (Integer) jsonObj.getJSONObject("januaryData").get("Unresolved");
                                                    janR = (Integer) jsonObj.getJSONObject("januaryData").get("Resolved");
                                                    febO = (Integer) jsonObj.getJSONObject("februaryData").get("Open");
                                                    febU = (Integer) jsonObj.getJSONObject("februaryData").get("Unresolved");
                                                    febR = (Integer) jsonObj.getJSONObject("februaryData").get("Resolved");
                                                    marO = (Integer) jsonObj.getJSONObject("marchData").get("Open");
                                                    marU = (Integer) jsonObj.getJSONObject("marchData").get("Unresolved");
                                                    marR = (Integer) jsonObj.getJSONObject("marchData").get("Resolved");
                                                    aprilO = (Integer) jsonObj.getJSONObject("aprilData").get("Open");
                                                    aprilU = (Integer) jsonObj.getJSONObject("aprilData").get("Unresolved");
                                                    aprilR = (Integer) jsonObj.getJSONObject("aprilData").get("Resolved");
                                                    mayO = (Integer) jsonObj.getJSONObject("mayData").get("Open");
                                                    mayU = (Integer) jsonObj.getJSONObject("mayData").get("Unresolved");
                                                    mayR = (Integer) jsonObj.getJSONObject("mayData").get("Resolved");
                                                    junO = (Integer) jsonObj.getJSONObject("juneData").get("Open");
                                                    junU = (Integer) jsonObj.getJSONObject("juneData").get("Unresolved");
                                                    junR = (Integer) jsonObj.getJSONObject("juneData").get("Resolved");
                                                    julO = (Integer) jsonObj.getJSONObject("julyData").get("Open");
                                                    julU = (Integer) jsonObj.getJSONObject("julyData").get("Unresolved");
                                                    julR = (Integer) jsonObj.getJSONObject("julyData").get("Resolved");
                                                    augO = (Integer) jsonObj.getJSONObject("augustData").get("Open");
                                                    augU = (Integer) jsonObj.getJSONObject("augustData").get("Unresolved");
                                                    augR = (Integer) jsonObj.getJSONObject("augustData").get("Resolved");
                                                    sepO = (Integer) jsonObj.getJSONObject("septemberData").get("Open");
                                                    sepU = (Integer) jsonObj.getJSONObject("septemberData").get("Unresolved");
                                                    sepR = (Integer) jsonObj.getJSONObject("septemberData").get("Resolved");
                                                    octO = (Integer) jsonObj.getJSONObject("octoberData").get("Open");
                                                    octU = (Integer) jsonObj.getJSONObject("octoberData").get("Unresolved");
                                                    octR = (Integer) jsonObj.getJSONObject("octoberData").get("Resolved");
                                                    novO = (Integer) jsonObj.getJSONObject("novemberData").get("Open");
                                                    novU = (Integer) jsonObj.getJSONObject("novemberData").get("Unresolved");
                                                    novR = (Integer) jsonObj.getJSONObject("novemberData").get("Resolved");
                                                }
//                                                    decO = (Integer) jsonObj.getJSONObject("decemberData").get("Open");
//                                                    decU = (Integer) jsonObj.getJSONObject("decemberData").get("Unresolved");
//                                                    decR = (Integer) jsonObj.getJSONObject("decemberData").get("Resolved");
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Log.i(TAG, "AFTER TEXT:" + mayO + " " + mayR + " " + mayU);
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
        };
        handler.postDelayed(runnable, 1500);
    }

    protected void checkComplete() throws IOException {
        if (!isComplete) {
            Log.d("HERE", "ISCOMPLETE: " + isComplete + " RUNNING POST DELAY");
            handler.postDelayed(runnable, 1500);
        } else {
            page = 1;
            prevPage = 1;
            handler.removeCallbacks(runnable);
            Log.d("HERE", "ISCOMPLETE: " + isComplete + " STOPPING POST DELAY");
            if (!firstRunThrough) {
                loadingscreen.setVisibility(View.GONE);
//                startKlipfolio();
                timerController.setTimer(TimeUnit.MINUTES.toMillis(30), 1000);
                int[] opened = {janO, febO, marO, aprilO, mayO, junO, julO, augO, sepO, octO, novO, decO};
                int[] resolved = {janR, febR, marR, aprilR, mayR, junR, julR, augR, sepR, octR, novR, decR};
                int[] unresolved = {janU, febU, marU, aprilU, mayU, junU, julU, augU, sepU, octU, novU, decU};

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
                loadingView.setVisibility(View.GONE);
                barChartView.setVisibility(View.VISIBLE);
                barChart.invalidate();
                barChart.refreshDrawableState();
                barW = data.getBarWidth();
                data.setBarWidth(barW / 3);
                data.setHighlightEnabled(false);
                if (formatter.format(date).contains(currentYear + "-01") || formatter.format(date).contains(currentYear + "-02") || formatter.format(date).contains(currentYear + "-03") || formatter.format(date).contains(currentYear + "-04")) {
                    barChart.moveViewToX(0);  // jan feb mar april
                } else if (formatter.format(date).contains(currentYear + "-05")) { //may
                    barChart.moveViewToX(1);
                } else if (formatter.format(date).contains(currentYear + "-06")) { //jun
                    barChart.moveViewToX(2);
                } else if (formatter.format(date).contains(currentYear + "-07")) { //jul
                    barChart.moveViewToX(3);
                } else if (formatter.format(date).contains(currentYear + "-08")) { //aug
                    barChart.moveViewToX(4);
                } else if (formatter.format(date).contains(currentYear + "-09")) { //sep
                    barChart.moveViewToX(5);
                } else if (formatter.format(date).contains(currentYear + "-10")) { //oct
                    barChart.moveViewToX(6);
                } else if (formatter.format(date).contains(currentYear + "-11")) { //nov
                    barChart.moveViewToX(7);
                } else if (formatter.format(date).contains(currentYear + "-12")) { //dec
                    barChart.moveViewToX(8);
                }

                barChart.setVisibleXRangeMaximum(4);
                barChart.setFitBars(true);
                barChart.groupBars(0, (barW / 3) / 2, 0);
                firstRunThrough = true;
            }
            JsonObject dataObject = new JsonObject(); // Parent Object
            JsonObject janObject = new JsonObject(); // Child Object
            janObject.addProperty("Open", janO);
            janObject.addProperty("Unresolved", janU);
            janObject.addProperty("Resolved", janR);
            JsonObject febObject = new JsonObject(); // Child Object
            febObject.addProperty("Open", febO);
            febObject.addProperty("Unresolved", febU);
            febObject.addProperty("Resolved", febR);
            JsonObject marObject = new JsonObject(); // Child Object
            marObject.addProperty("Open", marO);
            marObject.addProperty("Unresolved", marU);
            marObject.addProperty("Resolved", marR);
            JsonObject aprObject = new JsonObject(); // Child Object
            aprObject.addProperty("Open", aprilO);
            aprObject.addProperty("Unresolved", aprilU);
            aprObject.addProperty("Resolved", aprilR);
            JsonObject mayObject = new JsonObject(); // Child Object
            mayObject.addProperty("Open", mayO);
            mayObject.addProperty("Unresolved", mayU);
            mayObject.addProperty("Resolved", mayR);
            JsonObject junObject = new JsonObject(); // Child Object
            junObject.addProperty("Open", junO);
            junObject.addProperty("Unresolved", junU);
            junObject.addProperty("Resolved", junR);
            JsonObject julObject = new JsonObject(); // Child Object
            julObject.addProperty("Open", julO);
            julObject.addProperty("Unresolved", julU);
            julObject.addProperty("Resolved", julR);
            JsonObject augObject = new JsonObject(); // Child Object
            augObject.addProperty("Open", augO);
            augObject.addProperty("Unresolved", augU);
            augObject.addProperty("Resolved", augR);
            JsonObject sepObject = new JsonObject(); // Child Object
            sepObject.addProperty("Open", sepO);
            sepObject.addProperty("Unresolved", sepU);
            sepObject.addProperty("Resolved", sepR);
            JsonObject octObject = new JsonObject(); // Child Object
            octObject.addProperty("Open", octO);
            octObject.addProperty("Unresolved", octU);
            octObject.addProperty("Resolved", octR);
            JsonObject novObject = new JsonObject(); // Child Object
            novObject.addProperty("Open", novO);
            novObject.addProperty("Unresolved", novU);
            novObject.addProperty("Resolved", novR);
            JsonObject decObject = new JsonObject(); // Child Object
            decObject.addProperty("Open", decO);
            decObject.addProperty("Unresolved", decU);
            decObject.addProperty("Resolved", decR);
            //Adding Child to Parent
            dataObject.add("januaryData", janObject);
            dataObject.add("februaryData", febObject);
            dataObject.add("marchData", marObject);
            dataObject.add("aprilData", aprObject);
            dataObject.add("mayData", mayObject);
            dataObject.add("juneData", junObject);
            dataObject.add("julyData", julObject);
            dataObject.add("augustData", augObject);
            dataObject.add("septemberData", sepObject);
            dataObject.add("octoberData", octObject);
            dataObject.add("novemberData", novObject);
            dataObject.add("decemberData", decObject);

            String content = dataObject.toString();
            Log.d(TAG, "checkComplete: " + content);

            // Android not creating file
            // https://stackoverflow.com/questions/20202966/android-not-creating-file
            File directory = new File("/mnt/internal_sd/F45Dashboard/");
            String filename = "barData.txt";

            isDirectoryCreated = directory.exists();
            if (!isDirectoryCreated) {
                directory.mkdirs();
                directory.createNewFile();
                Log.d(TAG, "directory created..");
            }

            if (isDirectoryCreated) {
                File file = new File(directory, filename);
                isFileCreated = file.exists();
                if (!isFileCreated) {
                    file.createNewFile();
                    file.canWrite();
                    file.canRead();
                    Log.d(TAG, "file created.. @ " + file.getPath());
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
                } else {
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
                    Log.d(TAG, "file rewritten.. @ " + file.getPath());
                }

            }
            page = 1;
            updateTicketsFuture();
        }

    }

    protected void updateTickets() {

        Log.d(TAG, "updateTickets: UPDATING");
        //retrofitclient
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.setBaseUrl("https://f45training.freshdesk.com/");
        final String authHeader = "Basic V1U3Y0ZJY0lhNVZDbHE4TnM1Mjo=";
        final String cacheControl = "no-cache";
        final String postmanToken = "e601edd5-eb58-430f-a43a-ea74b8d6ce6c";
        final RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        final String dateString = currentYear + "-" + currentMonth + "-01T00:00:00Z";
        runnable2 = new Runnable() {
            @Override
            public void run() {
                Call<List<TicketVolumeDataModel>> call = retrofitInterface.getTicketVolume(authHeader, cacheControl, postmanToken, page, 100, dateString);
                Log.d(TAG, "OnUpdate: On loop start, page number is :" + page);
                Log.d(TAG, "OnUpdate: headerString: " + headerString);
                if (headerString.isEmpty()) {
                    Log.d("HERE", "ENTER HERE");
                    call.enqueue(new Callback<List<TicketVolumeDataModel>>() {
                        @Override
                        public void onResponse(Call<List<TicketVolumeDataModel>> call, Response<List<TicketVolumeDataModel>> response) {
                            Headers header = response.headers();
                            //Log.d(TAG, header.toString());
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
                            Headers headers = response.headers();
                            if (headers.get("link") == null) {
                                isComplete = true;
                                page = 1;
                                Log.d(TAG, "is updating finished? " + isCompleteUpdate);
                            } else {
                                headerString = headers.get("link");
                                String result = headerString.substring(headerString.indexOf("?") + 1, headerString.indexOf("&"));
                                pageNum = result.substring(result.lastIndexOf('=') + 1);
                                Log.e(TAG, "OnUpdate: This is the previous page number: " + page);
                                page = Integer.parseInt(pageNum);
                                Log.e(TAG, "OnUpdate: This is the next page number " + page);
                                //TO AVOID DUPLICATES
                                if (prevPage != page && prevPage < page) {
                                    prevPage = page;
                                    Log.e(TAG, "OnUpdate: PROCEED");
                                    for (int i = 0; i < model.size(); i++) {
                                        //TICKETS FOR TODAY
                                        TicketVolumeDataModel tvdm = model.get(i);
                                        TicketVolumeDataModel.CustomFields a = tvdm.custom_fields;
                                        if (model.get(i).created_at.contains(formatter.format(date)) && a.department != null && a.department.equals("Tech Systems")) {
                                            ticketsv2 += 1;
                                        }
                                        //END TICKETS TODAY
                                        if (model.get(i).created_at.contains(formatter.format(date)) && a.department != null && a.department.equals("Tech Systems")) {
                                            try {
                                                Date updated_at = dateFormat.parse(model.get(i).updated_at);
                                                Date created_at = dateFormat.parse(model.get(i).created_at);
                                                long diff = updated_at.getTime() - created_at.getTime();
                                                long rT = diff / 1000;
                                                responseTime2 += rT;
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                                Log.e(TAG, "onResponse: error in parsing created at");
                                            }
                                        }
                                        if (model.get(i).created_at.contains(formatter.format(date)) && a.department != null && a.department.equals("Tech Systems")) {
                                            NotificationController reference = new NotificationController(Integer.parseInt(model.get(i).id), model.get(i).subject, model.get(i).source, model.get(i).priority);
                                            Log.d(TAG, "onStartNotifications: array size " + notificationList.size());
                                            Log.d(TAG, "onStartNotifications: not empty");
                                            Log.d(TAG, "onStartNotifications: count is " + count);
                                            Boolean isDup = isDuplicate(notificationList, reference);
                                            Log.d(TAG, "onStartNotifications: isDup " + isDup);
                                            if (!isDup) {
                                                Log.d(TAG, "!isDup: adding reference " + reference.getId() + " to NOTIFICATIONLIST");
                                                notificationList.add(0,reference);
                                                count++;
                                                adapter.notifyDataSetChanged();
                                                adapter.setNotificationList(notificationList);
                                            } else {
                                                Log.d(TAG, "isDup: skipped due to duplicate entry...");
                                            }
//                                        Log.d(TAG, "Adding:" + "index: " + i + " subject: " + model.get(i).subject + " source: " + Integer.parseInt(model.get(i).source) + " priority: " + Integer.parseInt(model.get(i).priority));
                                        }
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
                    Log.d("HERE", "ISCOMPLETE UPDATE: " + isCompleteUpdate);
                }
                try {
                    checkComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        //handler2.postDelayed(runnable2, 5000);
        handler2.post(runnable2);
    }

//    protected void startDeputyRequest() {
//        RetrofitClient retrofitClientD = new RetrofitClient();
//        retrofitClientD.setBaseUrl("https://a3c3f816065445.as.deputy.com/");
//        final String authHeaderD = "Bearer ffc0b18fb4ffd88c70dd523cb38259e5";
//        final String cacheControlD = "no-cache";
//        final String postmanTokenD = "ac5c988a-6c35-48e4-a491-67d301b1fa12";
//        final RetrofitInterface retrofitInterfaceD = RetrofitClient.getClient().create(RetrofitInterface.class);
//
//        Call<List<ScheduleDataModel>> call = retrofitInterfaceD.getSchedule(authHeaderD, cacheControlD, postmanTokenD);
//        call.enqueue(new Callback<List<ScheduleDataModel>>() {
//            @Override
//            public void onResponse(Call<List<ScheduleDataModel>> call, Response<List<ScheduleDataModel>> response) {
//
//                ArrayList<ScheduleDataModel> model
//                        = (ArrayList<ScheduleDataModel>) response.body();
//                Log.d(TAG, "startDeputyRequest: " + model.get(0).Employee);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<ScheduleDataModel>> call, Throwable t) {
//                Log.e(TAG, "onFailure: at startDeputyRequest", t);
//
//            }
//        });
//    }

    protected void startKlipfolio() {
        Log.d(TAG, "startKlipfolio: has started");
        RetrofitClient retrofitClientK = new RetrofitClient();
        retrofitClientK.setBaseUrl("http://matrix.f45.info/");
        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ResponseBody> call = retrofitInterface.getAllTVReports();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                runnable3 = new Runnable() {
                    @Override
                    public void run() {
                        String text;
                        ArrayList<String> arrayList = new ArrayList<>();
                        ResponseBody responseBody = response.body();
                        try {
                            text = responseBody.string();
                            JSONObject jsonObject = new JSONObject(text);
                            for (int i = 0; i < jsonObject.length(); i++) {
                                JSONObject object = jsonObject.getJSONObject(Integer.toString(i));
                                if (object.get("last_online").toString().contains("seconds")) {
                                    lastonlineString = object.getString("last_online");
                                    String lastOLResult = (lastonlineString.substring(0, 1));
                                    if (Integer.parseInt(lastOLResult) < 59) {
                                        Log.d(TAG, "LESS" + "Name: " + object.getString("name") + "LastOL" + object.getString("last_online"));
                                        arrayList.add(object.getString("name") + " | ");
                                    }
                                } else {
                                    Log.d(TAG, "MInutes" + "Name: " + object.getString("name") + "LastOL" + object.getString("last_online"));
                                }
                            }
                            Log.d(TAG, "KLIP: " + jsonObject.get("0"));
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(TAG, "KLIP err: ", e);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            String joined = TextUtils.join(" ", arrayList);
                            marqueeView.setText(joined);
                        }
                    }
                };
                handler3.postDelayed(runnable3, TimeUnit.MINUTES.toMillis(30));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "KLIP err: ", t);
            }
        });
    }

//    public void updateNotifications() {
//
//        Log.d(TAG, "updateNotifications: UPDATING");
//        //retrofitclient
//        RetrofitClient retrofitClient = new RetrofitClient();
//        retrofitClient.setBaseUrl("https://f45training.freshdesk.com/");
//        final String authHeader = "Basic V1U3Y0ZJY0lhNVZDbHE4TnM1Mjo=";
//        final String cacheControl = "no-cache";
//        final String postmanToken = "e601edd5-eb58-430f-a43a-ea74b8d6ce6c";
//        final RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
//        final String dateString = currentYear + "-" + currentMonth + "-01T00:00:00Z";
//        runnable3 = new Runnable() {
//            @Override
//            public void run() {
//                Call<List<TicketVolumeDataModel>> call = retrofitInterface.getTicketVolume(authHeader, cacheControl, postmanToken, pageNotif, 100, dateString);
//                call.enqueue(new Callback<List<TicketVolumeDataModel>>() {
//                    @Override
//                    public void onResponse(Call<List<TicketVolumeDataModel>> call, Response<List<TicketVolumeDataModel>> response) {
//                        ArrayList<TicketVolumeDataModel> model = (ArrayList<TicketVolumeDataModel>) response.body();
//                        Log.d(TAG, "onStartNotifications - pageNotif:"+pageNotif);
//
//                        Headers headers = response.headers();
//                        if (headers.get("link") == null) {
//                            isComplete = true;
//                            pageNotif = 1;
//                            Log.d(TAG, "is updating finished? " + isCompleteUpdate);
//                        } else {
//                            headerString = headers.get("link");
//                            String result = headerString.substring(headerString.indexOf("?") + 1, headerString.indexOf("&"));
//                            pageNum = result.substring(result.lastIndexOf('=') + 1);
//                            Log.e(TAG, "OnUpdate: This is the previous pageNotif number: " + pageNotif);
//                            pageNotif = Integer.parseInt(pageNum);
//                            Log.e(TAG, "OnUpdate: This is the next pageNotif number " + pageNotif);
//                            //TO AVOID DUPLICATES
//                            boolean isDup;
//                            if (prevPageNotif != pageNotif && prevPageNotif < pageNotif) {
//                                prevPageNotif = pageNotif;
//                                Log.e(TAG, "OnUpdate: PROCEED");
//                                for (int i = 0; i < model.size(); i++) {
//                                    TicketVolumeDataModel tvdm = model.get(i);
//                                    TicketVolumeDataModel.CustomFields a = tvdm.custom_fields;
//                                    int arraySize = notificationList.size();
//                                    if (model.get(i).created_at.contains(formatter.format(date)) && a.department != null && a.department.equals("Tech Systems")) {
//                                        NotificationController reference = new NotificationController(Integer.parseInt(model.get(i).id), model.get(i).subject, model.get(i).source, model.get(i).priority);
//                                        Log.d(TAG, "onStartNotifications: array size " + arraySize);
//                                        Log.d(TAG, "onStartNotifications: not empty");
//                                        Log.d(TAG, "onStartNotifications: count is " + count);
//                                        isDup = isDuplicate(notificationList, reference);
//                                        Log.d(TAG, "onStartNotifications: isDup " + isDup);
//                                        if (!isDup) {
//                                            Log.d(TAG, "!isDup: adding reference " + reference.getId() + " to NOTIFICATIONLIST");
//                                            notificationList.add(0,reference);
//                                            count++;
//                                            adapter.notifyDataSetChanged();
//                                            adapter.setNotificationList(notificationList);
//                                        } else {
//                                            Log.d(TAG, "isDup: skipped due to duplicate entry...");
//                                        }
////                                        Log.d(TAG, "Adding:" + "index: " + i + " subject: " + model.get(i).subject + " source: " + Integer.parseInt(model.get(i).source) + " priority: " + Integer.parseInt(model.get(i).priority));
//                                    }
//                                }
//                                Log.e(TAG, "notificationlist size :" + notificationList.size());
//                            }
//                        }
//
//
//                    }
//                    @Override
//                    public void onFailure(Call<List<TicketVolumeDataModel>> call, Throwable t) {
//                        Log.e(TAG, "onFailure: onNotif", t);
//                    }
//                });
//
//            }
//        };
//        handler3.post(runnable3);
//    }

    private boolean isDuplicate(List<NotificationController> sourceList, NotificationController newItem) {
        boolean isDup = false;
        for (NotificationController ticketModel : sourceList) {
            Log.e(TAG, "isDuplicate: perModel "+ticketModel.getId()+ " vs "+ newItem.getId());
            if (ticketModel.getId() == newItem.getId()) {
                Log.d(TAG, "isDuplicate: " + newItem.getId() + " is in the list");
                isDup = true; //found dup
            }
//            else {
//                Log.d(TAG, "isDuplicate: " + newItem.getId() + " is NOT in the list");
//                isDup = false; //!found dup
//            }
        }
        Log.d(TAG, "isDuplicate: end of for loop");
        return isDup; //!found dup
    }
}

