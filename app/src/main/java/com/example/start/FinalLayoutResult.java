package com.example.start;

import android.graphics.Region;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class FinalLayoutResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ArrayList<PointF> startPoints =(ArrayList<PointF>)getIntent().getSerializableExtra("startPoints"); ;
        final ArrayList<PointF> stopPoints = (ArrayList<PointF>)getIntent().getSerializableExtra("stopPoints");
        final ArrayList<IntersectedPoints> intersectedPoints = (ArrayList<IntersectedPoints>)getIntent().getSerializableExtra("intersectedPoints");
        float[] size = getIntent().getFloatArrayExtra("lineSizes");

        setContentView(R.layout.activity_final_layout_result);
        final FinalLayoutPath canvas  = (FinalLayoutPath) findViewById(R.id.result_canvas);
        final targetPosition targetWall  = (targetPosition) findViewById(R.id.targetPosition);
        canvas.setStartPoints(startPoints);
        canvas.setStopPoints(stopPoints);
        canvas.setIntersectPoints(intersectedPoints);
        canvas.setMeasures(size);
        canvas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int xPos = (int) event.getX();
                int yPos = (int)  event.getY();
                ArrayList<Region> regions = canvas.getRegions();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.i("alaa" ,"there is a touch");
                    Log.i("region" , "SIZZZZZZEE sucess" + regions.size());
                    for(int i = 0 ;i< regions.size();i++){
                        if (regions.get(i).contains(xPos, yPos)) {
                            targetWall.setShowTarget(i , canvas.getSize());
                            Log.i("region" , "region is selected" + i);
                            break;
                        }}

                }
                return true;
            }
        });
        Log.i("alaa" , "5rrrrrrrrrrrrrrrrrrrrrraaaaaaaaaaaaaaaa");
//
    }
}
