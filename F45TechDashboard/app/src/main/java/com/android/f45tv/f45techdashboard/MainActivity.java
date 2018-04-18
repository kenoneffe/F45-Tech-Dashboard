package com.android.f45tv.f45techdashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.f45tv.f45techdashboard.Client.AuthenticationInterceptor;
import com.android.f45tv.f45techdashboard.Client.RetrofitClient;
import com.android.f45tv.f45techdashboard.Controller.TicketVolumeController;
import com.android.f45tv.f45techdashboard.Controller.TimerController;
import android.widget.LinearLayout;
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

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    List<TicketVolumeDataModel> ticketVolumeDataModels;
    String TAG = "Kyle";

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

        int[] opened =  {30,20,10,5,100,60,23,53,32,10,15,20};
        int[] resolved = {15,18,2,3,70,40,8,28,18,7,5,15};
        int[] unresolved = {15,2,8,2,30,20,15,25,16,3,10,5};

        for (int i = 0; i < opened.length; i++){
            barEntries1.add(new BarEntry(i,opened[i]));
            barEntries2.add(new BarEntry(i,resolved[i]));
            barEntries3.add(new BarEntry(i,unresolved[i]));
        }

        graphLabels = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        //graphStackLabels = new String[]{"Opened", "Solved", "Unresolved"};
        //3 barDataSets for Opened Solved and Unresolved
        BarDataSet barDataSetOpened = new BarDataSet(barEntries1, "Opened");
        barDataSetOpened.setColors(Color.BLUE);
        BarDataSet barDataSetResolved = new BarDataSet(barEntries2, "Resolved");
        barDataSetResolved.setColors(Color.GREEN);
        BarDataSet barDataSetUnresolved = new BarDataSet(barEntries3, "Unresolved");
        barDataSetUnresolved.setColors(Color.RED);

        BarData data = new BarData(barDataSetOpened,barDataSetResolved,barDataSetUnresolved);
        float barW = data.getBarWidth();
        Log.d("Bar Width", Float.toString(barW));
        data.setBarWidth(barW/3);
        data.setHighlightEnabled(false);
        barChart.setData(data);
        barChart.setVisibleXRangeMaximum(4);
        barChart.moveViewToX(0); //this moves to what index of the month
        barChart.setFitBars(true);
        barChart.groupBars(0,(barW/3)/2, 0);
        barChart.invalidate();

        //X AXIS AND Y AXIS
        XAxis xAxis = barChart.getXAxis();
        YAxis yAxis = barChart.getAxisLeft();
        xAxis.setDrawLimitLinesBehindData(false);
        xAxis.setValueFormatter(new MyAxisValueFormatter(graphLabels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(11);
        yAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(11);
        xAxis.setGranularity(1f); // restrict interval to 1 (minimum)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
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

    @Override

    protected void onStart() {
        super.onStart();

        timerController.setTimer(TimeUnit.MINUTES.toMillis(30), 1000);
        timerFrame.addView(timerController);

        marqueeView = findViewById(R.id.marque_scrolling_text);
        Animation marqueeAnim = AnimationUtils.loadAnimation(this, R.anim.marquee_animation);
        marqueeView.startAnimation(marqueeAnim);


        //retrofitclient
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofitClient.setBaseUrl("https://f45training.freshdesk.com/");
        String authHeader = "Basic V1U3Y0ZJY0lhNVZDbHE4TnM1Mjo=";
        String cacheControl = "no-cache";
        String postmanToken = "e601edd5-eb58-430f-a43a-ea74b8d6ce6c";
        String linkHeader = "https://f45training.freshdesk.com/api/v2/tickets?per_page=100&page=2/rel=next";


        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<List<TicketVolumeDataModel>> call = retrofitInterface.getTicketVolume(authHeader, cacheControl ,postmanToken,linkHeader);

        call.enqueue(new Callback<List<TicketVolumeDataModel>>() {
            @Override
            public void onResponse(Call<List<TicketVolumeDataModel>> call, Response<List<TicketVolumeDataModel>> response) {
                ArrayList<TicketVolumeDataModel> model = (ArrayList<TicketVolumeDataModel>) response.body();
                int test =+ model.size();
                if(call.isExecuted()){
                    Log.i(TAG, "call executed");
                    Log.e(TAG, "ERROR CODE :"+ String.valueOf(response.code()));
                }
                if(response.isSuccessful()){
                    Log.i(TAG, "response succesful");
                    ticketVolumeController.setTicketVolumeText(Integer.toString(test));
                } else {
                    ticketVolumeController.setTicketVolumeText("Retrieve error");
                }

            }

            @Override
            public void onFailure(Call<List<TicketVolumeDataModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
                Log.getStackTraceString(t.getCause());
                t.printStackTrace();
            }
        });

            ticketVolumeController.setResponseTimeText("123");
            ticketLayout.addView(ticketVolumeController);

    }
    
}

