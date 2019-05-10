package com.example.start;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.arch.lifecycle.ViewModelProviders.of;

public class LayOutForMeasuresActivity extends AppCompatActivity {
//    ArrayList<Float> xstart ;
//    ArrayList<Float>  ystart ;
//    ArrayList<Float>  xstop ;
//    ArrayList<Float>  ystop ;
    private Button button_back ;
    private float[] sizes ;
    private int layoutEditID = -1 ;
    //private HiitViewModel hiitViewModel ;
    //private int jumpHeight
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hiitViewModel = of(this).get(HiitViewModel.class);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lay_out_for_measures);
        Log.i("alaa" ,"wslmnnnnnnnaaa");
//        hiitViewModel.getAllProfiles().observe(LayOutForMeasuresActivity.this, new Observer<List<ProfileTableDb>>() {
//            @Override
//            public void onChanged(@Nullable List<ProfileTableDb> profiles) {
//                if(profiles.get(0).getUser_backproblems().equals("lower")) {
//                }
//                if(profiles.get(0).getUser_backproblems().equals("upper")) {
//                }
//                if(profiles.get(0).getUser_backproblems().equals("none")) {
//                }
//            }});
                final LayOutCanvasResult canvas  = (LayOutCanvasResult) findViewById(R.id.getmeasure_canvas);
        final ArrayList<PointF> startPoints =(ArrayList<PointF>)getIntent().getSerializableExtra("startPoints"); ;
        final ArrayList<PointF> stopPoints = (ArrayList<PointF>)getIntent().getSerializableExtra("stopPoints");
        final ArrayList<IntersectedPoints> intersectedPoints = (ArrayList<IntersectedPoints>)getIntent().getSerializableExtra("intersectedPoints");
        layoutEditID = getIntent().getIntExtra("layoutEditID" , -1);
        canvas.setStartPoints(startPoints);
        canvas.setStopPoints(stopPoints);

        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }});
        final Animation anime_translate = AnimationUtils.loadAnimation(this ,R.anim.anime_translate);
        Button finish = (Button)(findViewById(R.id.button_layout_next)) ;
        finish.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                boolean flag = true ;
                float[] measures = canvas.getResultMeasure() ;
                if(measures.length == 0){
                    Toast toast=Toast.makeText(getApplicationContext(),"make sure to enter all sizes",Toast.LENGTH_SHORT);
                    //toast.setMargin(50,50);
                    toast.show();
                    flag = false ;
                }
                for (int i =0 ; i < measures.length ; i++){
                    if(measures[i] == 0){
                        Toast toast=Toast.makeText(getApplicationContext(),"make sure to enter all sizes",Toast.LENGTH_SHORT);
                        //toast.setMargin(50,50);
                        toast.show();
                        flag = false ;
                        break;
                    }
                }
                if(flag){
                    v.startAnimation(anime_translate);
                Intent intent = new Intent(LayOutForMeasuresActivity.this, FinalLayoutResult.class);
                intent.putExtra("startPoints", startPoints);
                intent.putExtra("stopPoints", stopPoints);
                intent.putExtra("intersectedPoints", intersectedPoints);
                intent.putExtra("layoutEditID" , layoutEditID);
                intent.putExtra("lineSizes" , measures);

                startActivity(intent);
            }}
        });
        Log.i("alaa" ,"wslmnnnnnnnaaa " + startPoints.size());
        //getIntent().getFloatArrayExtra("Arrayxstart");

    }

}
