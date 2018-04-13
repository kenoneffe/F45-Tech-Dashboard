package com.android.f45tv.f45techdashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.f45tv.f45techdashboard.Controller.TicketVolumeController;
import com.android.f45tv.f45techdashboard.Controller.TimerController;
import android.widget.LinearLayout;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        barChart.setDrawValueAboveBar(false);
        barChart.setPinchZoom(false);
        barChart.setFocusable(true);
        barChart.setDragEnabled(true);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setHighlightPerTapEnabled(false);
        barChart.setHighlightPerDragEnabled(false);
        barChart.setDrawGridBackground(true);
        barChart.setClickable(false);
        //add the retrofit here.
        barEntries1 = new ArrayList<>();
        barEntries2 = new ArrayList<>();
        barEntries3 = new ArrayList<>();

        int[] opened =  {30,20,10,5,100};
        float[] resolved = {15,18,10,5,70};
        float[] unresolved = {15,2,0,0,30};

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
        data.setBarWidth(0.2f);
        barChart.setData(data);
        barChart.groupBars(-1,.02f, 0.01f);
        barChart.invalidate();

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyAxisValueFormatter(graphLabels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
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

        marqueeView = (TextView) findViewById(R.id.marque_scrolling_text);
        Animation marqueeAnim = AnimationUtils.loadAnimation(this, R.anim.marquee_animation);
        marqueeView.startAnimation(marqueeAnim);

        ticketVolumeController.setTicketVolumeText("69");
        ticketVolumeController.setResponseTimeText("123");
        ticketLayout.addView(ticketVolumeController);

    }
    
}

