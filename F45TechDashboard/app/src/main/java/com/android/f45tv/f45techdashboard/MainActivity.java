package com.android.f45tv.f45techdashboard;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
<<<<<<< HEAD
import com.android.f45tv.f45techdashboard.Controller.TicketVolumeController;
=======

import com.android.f45tv.f45techdashboard.Controller.TimerController;
>>>>>>> 5551cc286d7df642aa17f487304433d5deec6dba
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

import com.android.f45tv.f45techdashboard.R;



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
    TimerController timerController;
    FrameLayout timerFrame;








>>>>>>> 5551cc286d7df642aa17f487304433d5deec6dba

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_main);
        //Marquee
        tv = (TextView) findViewById(R.id.minutesText);
        //Ticket Volume Controller
        ticketVolumeController = new TicketVolumeController(this);

        //Graph
=======

        setContentView(R.layout.activity_main);

        //tv = (TextView) findViewById(R.id.minutesText);

        timerFrame = (FrameLayout) findViewById(R.id.timerFrame);
        timerController = new TimerController(this);

>>>>>>> 5551cc286d7df642aa17f487304433d5deec6dba
        barChart = (BarChart) findViewById(R.id.chart);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
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
    public class MyAxisValueFormatter implements IAxisValueFormatter {
=======

    public class MyAxisValueFormatter implements IAxisValueFormatter{
>>>>>>> 5551cc286d7df642aa17f487304433d5deec6dba
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
    protected void onStart() {
        super.onStart();

        timerController.setTimer(TimeUnit.MINUTES.toMillis(30), 1000);
        timerFrame.addView(timerController);
<<<<<<< HEAD
=======
    }

    /*@Override
>>>>>>> 5551cc286d7df642aa17f487304433d5deec6dba
    protected void onStart() {
        super.onStart();

        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                tv.setText("Finish");
            }
        }.start();
>>>>>>> 1c4e509335930fbcb001555033de6f3f3361dae8

        marqueeView = (TextView) findViewById(R.id.marque_scrolling_text);
        Animation marqueeAnim = AnimationUtils.loadAnimation(this, R.anim.marquee_animation);
        marqueeView.startAnimation(marqueeAnim);
    }


<<<<<<< HEAD
=======
<<<<<<< HEAD
        //onStart set Ticket Volume and Response Time
        ticketVolumeController.setTicketVolumeText("69");
        ticketVolumeController.setResponseTimeText("123");
    }
=======
    }*/
>>>>>>> 1c4e509335930fbcb001555033de6f3f3361dae8

>>>>>>> 5551cc286d7df642aa17f487304433d5deec6dba
}
