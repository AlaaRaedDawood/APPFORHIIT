package com.example.start;

import android.content.Intent;
import android.graphics.Region;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class ViewLayoutActivity extends AppCompatActivity {
    private int targetindex ;
    private int targetsize ;
    private boolean targetflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_layout);
        final ViewLayoutCanvas canvas  = (ViewLayoutCanvas) findViewById(R.id.view_canvas);
        layoutTableDB choosenLayout = (layoutTableDB)getIntent().getSerializableExtra("layoutTableDb");
        canvas.setIntersectPoints(choosenLayout.getIntersectPoints());
        canvas.setStartPoints(choosenLayout.getStartPoints());
        canvas.setStopPoints(choosenLayout.getStopPoints());
        targetsize = choosenLayout.getStartPoints().size();
        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }});
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
                            targetflag = true ;
                            targetindex = i ;
                            canvas.setTargetIndex(targetindex);
                            // targetWall.setShowTarget(i , canvas.getSize());
                            Log.i("region" , "region is selected" + i);
                            break;
                        }}
                    if(targetflag){
                        targetflag = false ;
                        Intent intent = new Intent(ViewLayoutActivity.this , TragetPositionActivity.class);
                        intent.putExtra("index" , targetindex) ;
                        intent.putExtra("size" , targetsize);
                        startActivity(intent);

                    }

                }

                return true;
            }
        });


    }

}
