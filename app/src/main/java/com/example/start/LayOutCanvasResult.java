package com.example.start;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class LayOutCanvasResult extends View {
    private Paint paint ;
    private Path path ;
    int selectedLine = -1;
    private ArrayList<Region> regions = new ArrayList<>();
    private ArrayList<Integer> measure=new ArrayList<Integer>();;
    private ArrayList<Float> xstart=new ArrayList<Float>();
    private ArrayList<Float> ystart=new ArrayList<Float>();
    private ArrayList<Float> xstop=new ArrayList<Float>();
    private ArrayList<Float> ystop=new ArrayList<Float>();
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
        for(int i = 0 ; i < xstart.size();i++) {
            float right = Math.min(xstart.get(i) , xstop.get(i));
            float left = Math.max(xstart.get(i) , xstop.get(i));
            float top = Math.min(ystart.get(i) , ystop.get(i));
            float back = Math.max(ystart.get(i) , ystop.get(i));
            regions.add(new Region((int) (right - 20), (int) (top - 20), (int) (left + 20), (int) (back + 20)));
            paint.setColor(Color.RED);
            canvas.drawRect((float) (right-20) ,(float)(top-20) ,(float)(left
                  +20 ),(float)(back+20) ,paint);
            paint.setColor(Color.BLUE);

        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(regions.size() == 0) {
            createRegions(canvas);
        }
       // canvas.drawLine(10,400,400,400,paint);
        for(int i = 0 ; i < xstart.size();i++){
            if(i == selectedLine){
                paint.setColor(Color.RED);
                canvas.drawLine(xstart.get(i) , ystart.get(i) , xstop.get(i) , ystop.get(i) , paint);
                selectedLine = -1;
            }else{
                paint.setColor(Color.BLUE);
                canvas.drawLine(xstart.get(i) , ystart.get(i) , xstop.get(i) , ystop.get(i) , paint);
            }
//            paint.setColor(Color.RED);
//            canvas.drawRect((float) (xstart.get(0)-0.5) ,(float)(ystart.get(0)-0.5) ,(float)(xstop
//                    .get(0)+0.5 ),(float)(ystop.get(0)+0.5) ,paint);

        }

        Log.i("alaa" , "done for draw ");
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int xPos = (int) event.getX();
        int yPos = (int)  event.getY();

        for(int i =0;i< regions.size();i++){
//            Log.i("alaa" , "the Line you selected " + xPos);
//            Log.i("alaa" , "the Line you selected " + xstart.get(i) + "and ends" + xstop.get(i));
//            Log.i("alaa" , "the Line you selected y = " + yPos);
//            Log.i("alaa" , "the Line you selected y =" + ystart.get(i) + "and ends" + ystop.get(i));
            if(regions.get(i).contains(xPos,yPos)){
                selectedLine = i ;
                Log.i("alaa" , "the Line you region " + i);
                break;
            }
            if( (xstart.get(i) <= xPos ) && (xPos <= xstop.get(i))){
                if( (ystart.get(i) <= yPos ) && (yPos <= ystop.get(i))){
                selectedLine = i ;
                Log.i("alaa" , "the Line you foundeeeee " + i);
                break;}
            }
        }
       // return super.onTouchEvent(event);
        invalidate();
        return true ;
    }

    public void setXstart(ArrayList<Float> x){
        xstart = x;

    }
    public void setYstart(ArrayList<Float> x){
         ystart = x;
    }
    public void setXstop(ArrayList<Float> x){
         xstop = x;
    }
    public void setYstop(ArrayList<Float> x){
        ystop = x;
    }

}