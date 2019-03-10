package com.example.start;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class LayOutForMeasuresActivity extends AppCompatActivity {
    ArrayList<Float> xstart ;
    ArrayList<Float>  ystart ;
    ArrayList<Float>  xstop ;
    ArrayList<Float>  ystop ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lay_out_for_measures);
        Log.i("alaa" ,"wslmnnnnnnnaaa");
          LayOutCanvasResult canvas = (LayOutCanvasResult) findViewById(R.id.getmeasure_canvas);
        xstart = (ArrayList<Float>) getIntent().getSerializableExtra("Arrayxstart");
        ystart = (ArrayList<Float>) getIntent().getSerializableExtra("Arrayystart");
        xstop =(ArrayList<Float>) getIntent().getSerializableExtra("Arrayxstop");
        ystop = (ArrayList<Float>) getIntent().getSerializableExtra("Arrayystop");
        canvas.setXstart(xstart);
        canvas.setYstart(ystart);
        canvas.setXstop(xstop);
        canvas.setYstop(ystop);
        Log.i("alaa" ,"wslmnnnnnnnaaa " + xstart.size());
        //getIntent().getFloatArrayExtra("Arrayxstart");

    }

}
