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

public class LayoutCanvas extends View {
    private Paint paint ;
    private Path path ;
    private ArrayList<Integer> measure=new ArrayList<Integer>();;
    private ArrayList<Float> xstart=new ArrayList<Float>();
    private ArrayList<Float> ystart=new ArrayList<Float>();
    private ArrayList<Float> xstop=new ArrayList<Float>();
    private ArrayList<Float> ystop=new ArrayList<Float>();
    public LayoutCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        Log.i("alaa" , "done for constructor");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i = 0 ; i < xstart.size();i++){
            canvas.drawLine(xstart.get(i) , ystart.get(i) , xstop.get(i) , ystop.get(i) , paint);
        }

        Log.i("alaa" , "done for draw ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("alaa" , "done for 5ra");
        float xPos = event.getX();
        float yPos = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xstart.add(xPos) ;
                ystart.add(yPos);
                Log.i("alaa" , "action down ");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.i("alaa" , "ACTION_MOVE  ");
                return true;
            case MotionEvent.ACTION_UP:
                xstop.add(xPos) ;
                ystop.add(yPos);
                Log.i("alaa" , "ACTION_UP  ");
                break;
            default:
                return false;

        }
        invalidate();


        return true ;
    }
   public ArrayList<Float> getXstart(){
        return xstart ;
   }
    public ArrayList<Float> getYstart(){
        return ystart ;
    }
    public ArrayList<Float> getXstop(){
        return xstop ;
    }
    public ArrayList<Float> getYstop(){
        return ystop ;
    }
}

