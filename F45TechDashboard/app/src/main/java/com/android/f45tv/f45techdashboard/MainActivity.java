package com.android.f45tv.f45techdashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by LeakSun on 04/04/2018.
 */

public class MainActivity extends AppCompatActivity {

    TextView marqueeView;
    BarChart barChart;
<<<<<<< HEAD
    String[] graphLabels;
    ArrayList<BarEntry> barEntries;
=======



    String[] graphLabels;
    ArrayList<BarEntry> barEntries;

>>>>>>> 06116c82195968a29a0f86fa03d8851b5cef24e1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        graphLabels = new String[]{" ","Opened", "Solved", "Unresolved"};

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

    public class MyAxisValueFormatter implements IAxisValueFormatter{
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


<<<<<<< HEAD
=======

>>>>>>> 06116c82195968a29a0f86fa03d8851b5cef24e1
    @Override
    protected void onStart() {
        super.onStart();

        marqueeView = (TextView) findViewById(R.id.marque_scrolling_text);
        Animation marqueeAnim = AnimationUtils.loadAnimation(this, R.anim.marquee_animation);
        marqueeView.startAnimation(marqueeAnim);

    }
<<<<<<< HEAD
=======

>>>>>>> 06116c82195968a29a0f86fa03d8851b5cef24e1
}
