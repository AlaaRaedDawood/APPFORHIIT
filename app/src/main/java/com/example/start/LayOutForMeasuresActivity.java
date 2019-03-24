package com.example.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class LayOutForMeasuresActivity extends AppCompatActivity {
//    ArrayList<Float> xstart ;
//    ArrayList<Float>  ystart ;
//    ArrayList<Float>  xstop ;
//    ArrayList<Float>  ystop ;

    float[] sizes ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lay_out_for_measures);
        Log.i("alaa" ,"wslmnnnnnnnaaa");
        final LayOutCanvasResult canvas  = (LayOutCanvasResult) findViewById(R.id.getmeasure_canvas);
        final ArrayList<PointF> startPoints =(ArrayList<PointF>)getIntent().getSerializableExtra("startPoints"); ;
        final ArrayList<PointF> stopPoints = (ArrayList<PointF>)getIntent().getSerializableExtra("stopPoints");
        final ArrayList<IntersectedPoints> intersectedPoints = (ArrayList<IntersectedPoints>)getIntent().getSerializableExtra("intersectedPoints");
//        xstart = (ArrayList<Float>) getIntent().getSerializableExtra("Arrayxstart");
//        ystart = (ArrayList<Float>) getIntent().getSerializableExtra("Arrayystart");
//        xstop =(ArrayList<Float>) getIntent().getSerializableExtra("Arrayxstop");
//        ystop = (ArrayList<Float>) getIntent().getSerializableExtra("Arrayystop");
        canvas.setStartPoints(startPoints);
        canvas.setStopPoints(stopPoints);
//        canvas.setXstop(xstop);
//        canvas.setYstop(ystop);

//        Log.i("alaa" , "number of lines" + sizes.length);
        Button finish = (Button)(findViewById(R.id.button_layout_getmeasure_next)) ;
        finish.setOnClickListener(new View.OnClickListener() {

           boolean flag = true ;
            public void onClick(View v) {
                float[] measures = canvas.getResultMeasure() ;
                for (int i =0 ; i < measures.length ; i++){
                    if(measures[i] == 0){
                        flag = false ;
                        break;
                    }
                }
                if(flag){
                Intent intent = new Intent(LayOutForMeasuresActivity.this, FinalLayoutResult.class);
                intent.putExtra("startPoints", startPoints);
                intent.putExtra("stopPoints", stopPoints);
                intent.putExtra("intersectedPoints", intersectedPoints);
                intent.putExtra("lineSizes" , measures);

                startActivity(intent);
            }}
        });
        Log.i("alaa" ,"wslmnnnnnnnaaa " + startPoints.size());
        //getIntent().getFloatArrayExtra("Arrayxstart");

    }

}
