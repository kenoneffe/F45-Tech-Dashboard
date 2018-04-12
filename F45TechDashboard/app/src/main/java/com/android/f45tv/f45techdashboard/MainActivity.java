package com.android.f45tv.f45techdashboard;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
<<<<<<< HEAD
import android.widget.TextView;

import com.android.f45tv.f45techdashboard.Controller.TicketVolumeController;


import com.android.f45tv.f45techdashboard.Controller.TimerController;

=======
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.f45tv.f45techdashboard.Controller.TicketVolumeController;
import com.android.f45tv.f45techdashboard.Controller.TimerController;
>>>>>>> 876edbcce9cabdc7b5b6077751a0233d4fc34158
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Created by LeakSun on 04/04/2018.
 */

public class MainActivity extends AppCompatActivity {

    TextView marqueeView;
    BarChart barChart;
    String[] graphLabels;
    ArrayList<BarEntry> barEntries;
<<<<<<< HEAD

    TextView tv;
    CountDownTimer countDownTimer;
    TicketVolumeController ticketVolumeController;


=======
    TextView tv;
    CountDownTimer countDownTimer;
    TicketVolumeController ticketVolumeController;
>>>>>>> 876edbcce9cabdc7b5b6077751a0233d4fc34158
    TimerController timerController;
    FrameLayout timerFrame;
    LinearLayout ticketLayout;


<<<<<<< HEAD








=======
>>>>>>> 876edbcce9cabdc7b5b6077751a0233d4fc34158
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        //Marquee

        //Ticket Volume Controller
        ticketVolumeController = new TicketVolumeController(this);

        //Graph






        timerFrame = (FrameLayout) findViewById(R.id.timerFrame);
        timerController = new TimerController(this);


        barChart = (BarChart) findViewById(R.id.chart);
=======
        //Ticket Volume Controller
        ticketVolumeController = new TicketVolumeController(this);
        ticketLayout = findViewById(R.id.ticketFrame);
        //Timer Controller
        timerFrame = findViewById(R.id.timerFrame);
        timerController = new TimerController(this);
        //Graph
        barChart = findViewById(R.id.chart);
>>>>>>> 876edbcce9cabdc7b5b6077751a0233d4fc34158
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setFocusable(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setDrawGridBackground(true);
        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 40f));
        barEntries.add(new BarEntry(2, 44f));
        barEntries.add(new BarEntry(3, 35f));

        graphLabels = new String[]{" ", "Opened", "Solved", "Unresolved"};

        BarDataSet barDataSet = new BarDataSet(barEntries, "Cells");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);

        barChart.setData(data);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyAxisValueFormatter(graphLabels));
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

<<<<<<< HEAD




    public class MyAxisValueFormatter implements IAxisValueFormatter{

=======
    public class MyAxisValueFormatter implements IAxisValueFormatter {

>>>>>>> 876edbcce9cabdc7b5b6077751a0233d4fc34158
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
<<<<<<< HEAD
=======

>>>>>>> 876edbcce9cabdc7b5b6077751a0233d4fc34158
    protected void onStart() {
        super.onStart();

        timerController.setTimer(TimeUnit.MINUTES.toMillis(30), 1000);
        timerFrame.addView(timerController);

<<<<<<< HEAD
        marqueeView = (TextView) findViewById(R.id.marque_scrolling_text);
        Animation marqueeAnim = AnimationUtils.loadAnimation(this, R.anim.marquee_animation);
        marqueeView.startAnimation(marqueeAnim);

    }


=======

        marqueeView = findViewById(R.id.marque_scrolling_text);
        Animation marqueeAnim = AnimationUtils.loadAnimation(this, R.anim.marquee_animation);
        marqueeView.startAnimation(marqueeAnim);

        //onStart set Ticket Volume and Response Time
        ticketVolumeController.setTicketVolumeText("69");
        ticketVolumeController.setResponseTimeText("123");
        ticketLayout.addView(ticketVolumeController);
    }
>>>>>>> 876edbcce9cabdc7b5b6077751a0233d4fc34158

}

