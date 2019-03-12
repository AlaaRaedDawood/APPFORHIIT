package com.example.start;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ResultLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ArrayList<PointF> startPoints =(ArrayList<PointF>)getIntent().getSerializableExtra("startPoints"); ;
        final ArrayList<PointF> stopPoints = (ArrayList<PointF>)getIntent().getSerializableExtra("stopPoints");
        final ArrayList<IntersectedPoints> intersectedPoints = (ArrayList<IntersectedPoints>)getIntent().getSerializableExtra("intersectedPoints");
        float[] size = getIntent().getFloatArrayExtra("lineSizes");

        setContentView(R.layout.activity_result_layout);
        final FinalLayoutPath canvas  = (FinalLayoutPath) findViewById(R.id.result_canvas);
        canvas.setStartPoints(startPoints);
        canvas.setStopPoints(stopPoints);
        canvas.setIntersectPoints(intersectedPoints);
        canvas.setMeasures(size);
        Log.i("alaa" , "done it with sucess");

    }
}
