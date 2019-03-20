package com.example.start;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
///canvas for taking measures
public class LayOutCanvasResult extends View {
    private Paint paint ;
    private Path path ;
    private int selectedLine = -1;
   private boolean checkHandler = false ;
    private float[] resultMeasure ;
    private ArrayList<Region> regions = new ArrayList<>();
    private   ArrayList<PointF> startPoints = new ArrayList<PointF>();
    private  ArrayList<PointF> stopPoints = new ArrayList<PointF>();

    public LayOutCanvasResult(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        Log.i("alaa" , "done for constructor");
    }
    protected void createRegions(Canvas canvas){
        for(int i = 0 ; i < startPoints.size();i++) {
            float right = Math.min(startPoints.get(i).getX() , stopPoints.get(i).getX());
            float left = Math.max(startPoints.get(i).getX() , stopPoints.get(i).getX());
            float top = Math.min(startPoints.get(i).getY() , stopPoints.get(i).getY());
            float back = Math.max(startPoints.get(i).getY() , stopPoints.get(i).getY());
            regions.add(new Region((int) (right - 16), (int) (top - 16), (int) (left + 16), (int) (back + 16)));
            paint.setColor(Color.RED);
            canvas.drawRect((float) (right-16) ,(float)(top-16) ,(float)(left
                  +16 ),(float)(back+16) ,paint);
            paint.setColor(Color.BLUE);

        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(regions.size() == 0) {
            createRegions(canvas);
            resultMeasure = new float[startPoints.size()];
        }
       // canvas.drawLine(10,400,400,400,paint);
        for(int i = 0 ; i < startPoints.size();i++){
            if(i == selectedLine){
                paint.setColor(Color.RED);
                canvas.drawLine(startPoints.get(i).getX() , startPoints.get(i).getY() , stopPoints.get(i).getX() , stopPoints.get(i).getY() , paint);
            }else{
                paint.setColor(Color.BLUE);
                canvas.drawLine(startPoints.get(i).getX() , startPoints.get(i).getY() , stopPoints.get(i).getX() , stopPoints.get(i).getY() , paint);
            }
//            paint.setColor(Color.RED);
//            canvas.drawRect((float) (xstart.get(0)-0.5) ,(float)(ystart.get(0)-0.5) ,(float)(xstop
//                    .get(0)+0.5 ),(float)(ystop.get(0)+0.5) ,paint);

        }

        Log.i("alaa" , "done for draw ");
    }


//onTouch we get to know which line was selected
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int xPos = (int) event.getX();
        int yPos = (int)  event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = 0; i < regions.size(); i++) {
//            Log.i("alaa" , "the Line you selected " + xPos);
//            Log.i("alaa" , "the Line you selected " + xstart.get(i) + "and ends" + xstop.get(i));
//            Log.i("alaa" , "the Line you selected y = " + yPos);
//            Log.i("alaa" , "the Line you selected y =" + ystart.get(i) + "and ends" + ystop.get(i));
                if (regions.get(i).contains(xPos, yPos)) {
                    selectedLine = i;
                      Log.i("alaa" , "the Line you region " + i +" and " + selectedLine);
                    checkHandler = true ;
                    break;
                }
//            if( (xstart.get(i) <= xPos ) && (xPos <= xstop.get(i))){
//                if( (ystart.get(i) <= yPos ) && (yPos <= ystop.get(i))){
//                selectedLine = i ;
//                Log.i("alaa" , "the Line you foundeeeee " + i);
//                break;}
//            }

            }
            if(checkHandler) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        final EditText text = new EditText(getContext());

                        builder.setTitle("Set your measurments")
                                .setMessage("the measure of distance is  for line " + selectedLine + " :").setView(text);
                        builder.setPositiveButton("DONE",
                                null);


                        final AlertDialog alert = builder.create();
                        alert.show();
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Boolean flag = (text.getText().toString().trim().isEmpty());
                                // if EditText is empty disable closing on possitive button
                                if (!flag) {

                                    float num = Float.parseFloat(text.getText().toString());
                                    if(selectedLine != -1) {
                                        resultMeasure[selectedLine] = num;
                                    }
                                   // Log.i("alaa", "mabrook  " + num);
                                    for(int i = 0 ; i < resultMeasure.length ; i++){
                                        Log.i("alaa", "yrabbbb  " + resultMeasure[i]);
                                    }
                                    selectedLine = -1 ;
                                    alert.dismiss();


                                }

                            }
                        });


                    }
                }, 500);
   checkHandler = false;
            }
            invalidate();
            return true ;
        }
       // return super.onTouchEvent(event);

        return false ;
    }

//    public void setXstart(ArrayList<Float> x){
//        xstart = x;
//
//    }
//    public void setYstart(ArrayList<Float> x){
//         ystart = x;
//    }
//    public void setXstop(ArrayList<Float> x){
//         xstop = x;
//    }
//    public void setYstop(ArrayList<Float> x){
//        ystop = x;
//    }
    public void setStartPoints(ArrayList<PointF> x){
        startPoints = x ;
    }
    public void setStopPoints(ArrayList<PointF> x){
        stopPoints = x ;
    }

    public float[] getResultMeasure(){
        return resultMeasure ;
    }

}