package com.example.start;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class targetPosition extends View {
    private Paint paint ;
    private int targetIndex = -1 ;
    private int targetSize = -1 ;
    private boolean showTarget = false;
    private int user_height = -1 ;
    private String backProblems = "" ;
    private int targetwallposition = 0;
    public targetPosition(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8f);

        Log.i("alaa" , "done for constructor");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(showTarget){
            paint.setTextSize(80);
            if(backProblems.equals("none")){
                canvas.drawLine(506, 180, 506, 600, paint);
                canvas.drawLine(506, 180, 793, 273, paint);
                canvas.drawLine(793, 273, 781, 739, paint);
                canvas.drawLine(506, 600, 781, 739, paint);
                canvas.drawLine(506, 600, 781, 739, paint);
                canvas.drawLine(506, 600, 231, 739, paint);
                canvas.drawLine(506, 180, 219, 273, paint);
                canvas.drawLine(231, 739, 219, 273, paint);
                canvas.drawText(String.valueOf(user_height), 506 , 400, paint);
                targetwallposition = 0 ;
            }
            if(backProblems.equals("upper")){
                //middle line

                canvas.drawLine(506, 80, 506, 840, paint);
                canvas.drawLine(506, 80, 793, 173, paint);
                canvas.drawLine(793, 173, 781, 900, paint);
                canvas.drawLine(506, 840, 781, 900, paint);
                canvas.drawLine(506, 840, 781, 900, paint);
                canvas.drawLine(506, 840, 231, 900, paint);
                canvas.drawLine(506, 80, 219, 173, paint);
                canvas.drawLine(231, 900, 219, 173, paint);
                canvas.drawText(String.valueOf(user_height-110), 506 , 500, paint);
                canvas.drawText(String.valueOf(60), 506 , 730, paint);
                targetwallposition = 50 ;
                //canvas.drawText(String.valueOf(user_height-100), 506 , 400, paint);
            }
            if(backProblems.equals("lower")) {
                canvas.drawLine(506, 80, 506, 750, paint);
                canvas.drawLine(506, 80, 793, 173, paint);
                canvas.drawLine(793, 173, 781, 839, paint);
                canvas.drawLine(506, 750, 781, 839, paint);
                canvas.drawLine(506, 750, 781, 839, paint);
                canvas.drawLine(506, 750, 231, 839, paint);
                canvas.drawLine(506, 80, 219, 173, paint);
                canvas.drawLine(231, 839, 219, 173, paint);
                canvas.drawText(String.valueOf(user_height-120), 506 , 300, paint);
                canvas.drawText(String.valueOf(user_height-60), 506 , 600, paint);
                targetwallposition = -90 ;
            }

        paint.setColor(Color.BLUE);


        for (int i = 1; i < 50; i += 5) {
            canvas.drawCircle(506, 250+targetwallposition, i, paint);
        }

        for (int i = 1; i < 50; i += 5) {
            canvas.drawCircle(506, 550+targetwallposition, i, paint);
        }
        paint.setColor(Color.GREEN);


            paint.setTextSize(50);
            if((targetIndex != -1) && (targetSize != 0)) {

                canvas.drawText(Integer.toString(targetIndex +1 + targetSize), 506, 250+targetwallposition, paint);
                canvas.drawText(Integer.toString(targetIndex +1 ), 506, 550+targetwallposition, paint);
            }
//        canvas.drawRect((float) 456, (float) 500, (float) 556, (float) 600, paint);
//        canvas.drawRect((float) 456, (float) 200, (float) 556, (float) 300, paint);
            paint.setColor(Color.RED);
    }}
    public void setShowTarget(int index , int regionSize, int height  , String bProblems){
        showTarget = true ;
        targetIndex = index ;
        targetSize = regionSize ;
        user_height =  height ;
        backProblems = bProblems;
        invalidate();
    }
}

