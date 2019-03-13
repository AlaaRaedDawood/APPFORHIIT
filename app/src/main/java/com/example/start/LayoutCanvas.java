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
//    private ArrayList<Integer> measure=new ArrayList<Integer>();;
//    private ArrayList<Float> xstart=new ArrayList<Float>();
//    private ArrayList<Float> ystart=new ArrayList<Float>();
//    private ArrayList<Float> xstop=new ArrayList<Float>();
//    private ArrayList<Float> ystop=new ArrayList<Float>();
ArrayList<IntersectedPoints> intersect = new ArrayList<IntersectedPoints>();
    private ArrayList<Integer> donewith = new ArrayList<Integer>();
    private ArrayList<PointF> intersectPoints = new ArrayList<PointF>();
  private   ArrayList<PointF> startPoints = new ArrayList<PointF>();
    private  ArrayList<PointF> stopPoints = new ArrayList<PointF>();
    public LayoutCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
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
//
//        for(int i = 0 ; i < xstart.size();i++){
//            canvas.drawLine(xstart.get(i) , ystart.get(i) , xstop.get(i) , ystop.get(i) , paint);
//        }
//
//        Log.i("alaa" , "done for draw ");
        for(int i = 0 ; i < startPoints.size();i++){
            canvas.drawLine(startPoints.get(i).getX() , startPoints.get(i).getY() , stopPoints.get(i).getX(), stopPoints.get(i).getY() , paint);
        }

        Log.i("alaa" , "done for draw " + intersectPoints.size());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPos = event.getX();
        float yPos = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startPoints.add(new PointF(xPos,yPos));
                Log.i("alaat" , "ACTION mmmm " + xPos + "y =" + yPos);
                return true;
            case MotionEvent.ACTION_MOVE:
                // Log.i("alaat" , "motion now at x =   " + xPos + "y =" + yPos);
                for(int i = 0 ; i < startPoints.size()-1 ; i++){
                    PointF startPoint = startPoints.get(i);
                    PointF stopPoint = stopPoints.get(i);
                    boolean flagX1 = (xPos >= Math.min( startPoint.getX(),stopPoint.getX())) && (xPos <= Math.max( startPoint.getX(),stopPoint.getX()));
                    boolean flagX2 = (Math.max(stopPoint.getX(),xPos) - Math.min(stopPoint.getX(),xPos) < 14);
                    boolean flagX3 = (Math.max(startPoint.getX(),xPos) - Math.min(startPoint.getX(),xPos) < 14);
                    boolean flag1 = (Math.max(stopPoint.getY(),yPos) - Math.min(stopPoint.getY(),yPos) < 14);
                    boolean flag2 = (Math.max(startPoint.getY(),yPos) - Math.min(startPoint.getY(),yPos) < 14);

                    if(!(donewith.contains(i))){
                        if( flagX1 ||flagX2 || flagX3 ){
                            if(flag1 || flag2){
                                Log.i("alaat" , "intersection at x =   " + xPos + "y =" + yPos + "with line " + i);
                                intersectPoints.add(new PointF(xPos ,yPos));
                                donewith.add(i);
                            }}}
                }
                return true;
            case MotionEvent.ACTION_UP:
                stopPoints.add(new PointF(xPos,yPos));
                Log.i("alaat" , "ACTION_UP  " + xPos + "y5tyyyy =" + yPos);
                if(intersectPoints.size() != 0){
                    for(int i = 0 ; i < donewith.size();i++){
                        PointF intersecttP = intersectPoints.get((intersectPoints.size()-1)-i);
                        intersect.add(new IntersectedPoints(intersecttP, startPoints.size()-1 ,donewith.get((donewith.size()-1)-i)));
                    }}
                donewith.clear();
                break;
            default:
                return false;

        }
        invalidate();


        return true ;
    }
    public ArrayList<PointF> getStartPoints(){
        return startPoints ;
    }
    public ArrayList<PointF> getStopPoints(){
        return stopPoints ;
    }
    public ArrayList<IntersectedPoints> getIntersectedPoints(){
        return intersect ;
    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.i("alaa" , "done for 5ra");
//        float xPos = event.getX();
//        float yPos = event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                xstart.add(xPos) ;
//                ystart.add(yPos);
//                Log.i("alaa" , "action down ");
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                Log.i("alaa" , "ACTION_MOVE  ");
//                return true;
//            case MotionEvent.ACTION_UP:
//                xstop.add(xPos) ;
//                ystop.add(yPos);
//                Log.i("alaa" , "ACTION_UP  ");
//                break;
//            default:
//                return false;
//
//        }
//        invalidate();
//
//
//        return true ;
//    }
//   public ArrayList<Float> getXstart(){
//        return xstart ;
//   }
//    public ArrayList<Float> getYstart(){
//        return ystart ;
//    }
//    public ArrayList<Float> getXstop(){
//        return xstop ;
//    }
//    public ArrayList<Float> getYstop(){
//        return ystop ;
//    }
}

