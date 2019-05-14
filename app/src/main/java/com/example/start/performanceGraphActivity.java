package com.example.start;

import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static android.arch.lifecycle.ViewModelProviders.of;

public class performanceGraphActivity extends AppCompatActivity {
    ArrayList<BarEntry> BARENTRY;
    ArrayList<String> BarEntryLabels;
    ArrayList<BarEntry> BarEntry2;
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<Integer> heartRate = new ArrayList<Integer>();
    HiitViewModel hiitViewModel;
    private boolean viewHeartValue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiitViewModel = of(this).get(HiitViewModel.class);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_performance_graph);

        Log.i("performance", "in graph activity the size is " + date.size());
        //setBorderColor(int color)
        final Animation anime_alpha = AnimationUtils.loadAnimation(this, R.anim.alpha_button);
        //return back to menu
        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                v.startAnimation(anime_alpha);
                finish();
            }
        });
        Log.i("pervalue","heartrate");
        hiitViewModel.getAllPerformance().observe(performanceGraphActivity.this, new Observer<List<PerformanceTableDB>>() {
            @Override
            public void onChanged(@Nullable List<PerformanceTableDB> performances) {
                BarChart chart = (BarChart) findViewById(R.id.chart);
                chart.setBorderColor(0xfff);

                BARENTRY = new ArrayList<>();

                BarEntryLabels = new ArrayList<String>();

                // AddValuesToBARENTRY();
                Log.i("date", "pp 2 " + date.size());
                //  AddValuesToBarEntryLabels();
                for (int i = 0; i < performances.size(); i++) {
                    float heartRate = performances.get(i).getUser_heartRate();
                    String date = performances.get(i).getDate();
                    AddValuesToBARENTRY(heartRate, i);
                    AddValuesToBarEntryLabels(date);
                }


                BarDataSet Bardataset = new BarDataSet(BARENTRY, "Heart Rate");
                BarData BARDATA = new BarData(BarEntryLabels, Bardataset);
                //chart.setBorderWidth(70);
                chart.setDescription("heartRate");
                Bardataset.setColor(Color.WHITE);



                chart.setData(BARDATA);
                chart.setVisibleXRangeMaximum(5);
                chart.animateY(3000);

            }

        });
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_value);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if ((R.id.heart_radio_btn) == checkedId) {
                    viewHeartValue = true;
                    checkView();
                } else {

                    viewHeartValue = false;
                    checkView();
                }
            }
        });




    }


    public void checkView(){
        if(viewHeartValue) {
            Log.i("pervalue","heartrate");
            hiitViewModel.getAllPerformance().observe(performanceGraphActivity.this, new Observer<List<PerformanceTableDB>>() {
                @Override
                public void onChanged(@Nullable List<PerformanceTableDB> performances) {
                    BarChart chart = (BarChart) findViewById(R.id.chart);
                    chart.setBorderColor(0xfff);

                    BARENTRY = new ArrayList<>();

                    BarEntryLabels = new ArrayList<String>();

                    // AddValuesToBARENTRY();
                    Log.i("date", "pp 2 " + date.size());
                    //  AddValuesToBarEntryLabels();
                    for (int i = 0; i < performances.size(); i++) {
                        float heartRate = performances.get(i).getUser_heartRate();
                        String date = performances.get(i).getDate();
                        AddValuesToBARENTRY(heartRate, i);
                        AddValuesToBarEntryLabels(date);
                    }


                    BarDataSet Bardataset = new BarDataSet(BARENTRY, "Heart Rate");
                    BarData BARDATA = new BarData(BarEntryLabels, Bardataset);
                    //chart.setBorderWidth(70);
                    chart.setDescription("heartRate");
                    Bardataset.setColor(Color.WHITE);



                    chart.setData(BARDATA);
                    chart.setVisibleXRangeMaximum(5);
                    chart.animateY(3000);

                }

            });
        }else {
            hiitViewModel.getAllPerformance().observe(performanceGraphActivity.this, new Observer<List<PerformanceTableDB>>() {
                @Override
                public void onChanged(@Nullable List<PerformanceTableDB> performances) {
                    BarChart chart = (BarChart) findViewById(R.id.chart);
                    chart.setBorderColor(0xfff);

                    BARENTRY = new ArrayList<>();

                    BarEntryLabels = new ArrayList<String>();


                    Log.i("date", "pp 2 " + date.size());
                    //  AddValuesToBarEntryLabels();
                    for (int i = 0; i < performances.size(); i++) {
                        int score = performances.get(i).getUser_score();
                        String date = performances.get(i).getDate();
                        AddValuesToBARENTRY(score, i);
                        AddValuesToBarEntryLabels(date);
                    }


                    BarDataSet Bardataset = new BarDataSet(BARENTRY, "Points");
                    BarData BARDATA = new BarData(BarEntryLabels, Bardataset);
                    chart.setDescription("Points");
                    Bardataset.setColor(Color.WHITE);



                    chart.setData(BARDATA);
                    chart.setVisibleXRangeMaximum(5);
                    chart.animateY(3000);

                }

            });}
    }
public void AddValuesToBARENTRY(float x , int i) {

    //BARENTRY.clear();
    Log.i("performance " , " hhhhhhhhhhhhhhhhhhh = " + x);
    BARENTRY.add(new BarEntry(x, i));



}
    public void AddValuesToPoints() {

        //BARENTRY.clear();
       // Log.i("performance " , " hhhhhhhhhhhhhhhhhhh = " + x);
        BARENTRY.add(new BarEntry(20, 0));
        BARENTRY.add(new BarEntry(30, 1));
        BARENTRY.add(new BarEntry(40, 2));

    }
    public void AddValuesToBarPoints(){


        BarEntryLabels.add("alaa");
        BarEntryLabels.add("alaa");
        BarEntryLabels.add("alaa");

    }
    public void AddValuesToBarEntryLabels(String date){

        Log.i("performance " , " hhhhhhhhhhhhhhhhhhh = " + date);
        BarEntryLabels.add(date);


    }
}