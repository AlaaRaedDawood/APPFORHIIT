package com.example.start;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class DrawLayoutActivity extends AppCompatActivity {
//     ArrayList<Float>  xstart ;
//    ArrayList<Float>  ystart ;
//    ArrayList<Float>  xstop ;
//    ArrayList<Float>  ystop ;
    private int layoutEditID = -1 ;
    private ArrayList<PointF> startPoints = new ArrayList<PointF>();
    private Button button_next ;
    private Button button_back ;
    private int createOredit = -1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_draw_layout);
        createOredit = getIntent().getIntExtra("editORcreate" ,-1);

        CoordinatorLayout constraintLayout = (CoordinatorLayout)findViewById(R.id.drawlayout_Layout);

        final LayoutCanvas canvas = (LayoutCanvas) findViewById(R.id.draw_canvas);
        if(createOredit == 0){
            final layoutTableDB currentLayout = (layoutTableDB)getIntent().getSerializableExtra("layoutTableDb");
            canvas.setStartPoints(currentLayout.getStartPoints());
            canvas.setStopPoints(currentLayout.getStopPoints());
            //canvas.setDonewithStack(currentLayout.getDoneWith());
            canvas.setIntersectedPoints(currentLayout.getIntersect());
            layoutEditID = currentLayout.getId();
        }
        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
               finish();
            }});
        final Animation anime_translate = AnimationUtils.loadAnimation(this ,R.anim.anime_translate);
       Button button_reset = (Button) findViewById(R.id.button_reset);
        button_reset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startPoints = canvas.getStartPoints();
                if(startPoints.size() != 0) {
                    canvas.resetDrawing();
                }else {
                    Toast toast=Toast.makeText(getApplicationContext(),"the layout is empty",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }});
        Button button_clear = (Button) findViewById(R.id.button_clear);
        button_clear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                canvas.clearDrawing();
            }});
        button_next = (Button) findViewById(R.id.button_layout_next);
         button_next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                startPoints = canvas.getStartPoints();
                ArrayList<PointF> stopPoints = canvas.getStopPoints();
                ArrayList<IntersectedPoints> intersectedPoints = canvas.getIntersectedPoints();
                //ArrayList<int[]> donewithStack = canvas.getDonewithStack();
                // Start NewActivity.class


                if(startPoints.size() != intersectedPoints.size()){
                    Toast toast=Toast.makeText(getApplicationContext(),"the layout must be closed path",Toast.LENGTH_SHORT);
                    //toast.setMargin(50,50);
                    toast.show();
                }

                else{
                if(intersectedPoints.size() !=  0) {
                    if(intersectedPoints.size() < 6) {
                        v.startAnimation(anime_translate);
                        Intent intent = new Intent(DrawLayoutActivity.this, LayOutForMeasuresActivity.class);
                        intent.putExtra("startPoints", startPoints);
                        intent.putExtra("stopPoints", stopPoints);
                        intent.putExtra("intersectedPoints", intersectedPoints);
                        intent.putExtra("layoutEditID", layoutEditID);
                        startActivity(intent);
                    }else {
                        Toast toast=Toast.makeText(getApplicationContext(),"intersection points mustn't exceed 5 points",Toast.LENGTH_SHORT);
                        //toast.setMargin(50,50);
                        toast.show();
                    }
                    //Log.i("alaa", "ssizzee of x is " + xstart.size());
                }else {
                    Toast toast=Toast.makeText(getApplicationContext(),"the layout has no intersection points",Toast.LENGTH_SHORT);
                    //toast.setMargin(50,50);
                    toast.show();
                }
            }}
        });





    }

}
