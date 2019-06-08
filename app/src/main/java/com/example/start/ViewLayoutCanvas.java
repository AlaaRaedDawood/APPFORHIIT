package com.example.start;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class ViewLayoutCanvas extends View {
    private Paint paint;
    private int createRegion = 0 ;
    private int targetIndex = -1 ;
    ArrayList<PointF> intersectPoints = new ArrayList<PointF>();
    private ArrayList<PointF> startPoints = new ArrayList<PointF>();
    private ArrayList<PointF> stopPoints = new ArrayList<PointF>();
    ArrayList<Region> regions = new ArrayList<Region>();
    private ArrayList<PathLine> pathLines = new ArrayList<PathLine>();


    public ViewLayoutCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8f);

        Log.i("alaa", "done for constructor");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (startPoints.size() != 0) {
            Log.i("alaa", "number of paths are " + intersectPoints.size());
            if (intersectPoints.size() != 0) {

                for (int i = 0; i < intersectPoints.size(); i++) {

                    String index = Integer.toString(i + 1);
                    int d = 20;

                    paint.setTextSize(70);
                    paint.setColor(Color.GREEN);
                    canvas.drawText(index, intersectPoints.get(i).getX() + d, intersectPoints.get(i).getY(), paint);

                    if(targetIndex == i){
                        paint.setColor(Color.RED);
                    }else {
                        paint.setColor(Color.GRAY);
                    }

                    for (int radius = 0; radius < 41; radius++) {
                        canvas.drawCircle(intersectPoints.get(i).getX(), intersectPoints.get(i).getY(), radius, paint);
                    }
                    if (createRegion == 0) {
                        regions.add(new Region((int) (intersectPoints.get(i).getX() - 40), (int) (intersectPoints.get(i).getY() - 40), (int) (intersectPoints.get(i).getX() + 40), (int) (intersectPoints.get(i).getY() + 40)));
                    }
                    //Log.i("alaa", "the x = " + resultPoints.get(i).getX() + " y = " + resultPoints.get(i).getY());

                }
                targetIndex = -1 ;
                createRegion ++ ;
            }
        }
        for (int i = 0; i < startPoints.size(); i++) {
            paint.setColor(Color.BLUE);
            canvas.drawLine(startPoints.get(i).getX(), startPoints.get(i).getY(), stopPoints.get(i).getX(), stopPoints.get(i).getY(), paint);
        }


    }
    public void setStartPoints(ArrayList<PointF> startPoints){
           this.startPoints = startPoints ;
    }
    public void setStopPoints(ArrayList<PointF> stopPoints){
        this.stopPoints = stopPoints ;
    }
    public void setIntersectPoints (ArrayList<PointF> intersectPoints){

        this.intersectPoints = intersectPoints ;
    }
    public ArrayList<Region> getRegions() {
        return regions;
    }
    public void setTargetIndex(int i){
        targetIndex = i;
        invalidate();

    }
}