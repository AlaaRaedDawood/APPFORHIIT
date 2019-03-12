package com.example.start;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;

public class FinalLayoutPath extends View {
    private Paint paint ;
    private float[] measures ;
    private ArrayList<PathLine> diagnoleLines = new ArrayList<PathLine>();
    ArrayList<IntersectedPoints> intersectPoints = new ArrayList<IntersectedPoints>();
    private   ArrayList<PointF> startPoints = new ArrayList<PointF>();
    private  ArrayList<PointF> stopPoints = new ArrayList<PointF>();
    private ArrayList<PathLine> pathLines = new ArrayList<PathLine>();
    public FinalLayoutPath(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        Log.i("alaa" , "done for constructor");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(startPoints.size() != 0 ){
//            for(int i = 0 ; i < intersectPoints.size() ;i++){
//                Log.i("alaaa" , " i1 = " + intersectPoints.get(i).getIndexOfLine1() + "l2 = " + intersectPoints.get(i).getIndexOfLine2());
//            }
            checkLinePath();
            Log.i("alaa" , "number of paths are " + pathLines.size());
            if(pathLines.size() != 0) {
                checkDiagnolePath();
                Log.i("alaa" , "number of paths are " + diagnoleLines.size());
            }
        }
        for(int i = 0 ; i < startPoints.size();i++){
                canvas.drawLine(startPoints.get(i).getX() , startPoints.get(i).getY() , stopPoints.get(i).getX() , stopPoints.get(i).getY() , paint);
            }

        Log.i("alaa" , "done for draw ");
    }
    public void checkLinePath(){
        for(int i = 0 ; i< intersectPoints.size();i++){
            for(int j = i+1 ; j <intersectPoints.size();j++){
                boolean flag = false ;
                IntersectedPoints i1 = intersectPoints.get(i) ;
                IntersectedPoints i2 = intersectPoints.get(j);
                if((i1.getIndexOfLine1() == i2.getIndexOfLine1())&& (!flag)){
                    pathLines.add(new PathLine(i1.getPoint() ,i2.getPoint(),measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                    flag = true ;
                   // Log.i("alaaaaaaaaa" , "1111111111111111 "  );
                }
            if((i1.getIndexOfLine2() == i2.getIndexOfLine2()) && (!flag)){
             pathLines.add(new PathLine(i1.getPoint() ,i2.getPoint() ,measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));
               flag = true ;
              //  Log.i("alaaaaaaaaa" , "222222222222222 "  );
                  }

                if((i1.getIndexOfLine2() == i2.getIndexOfLine1()) && (!flag)){
                    pathLines.add(new PathLine(i1.getPoint() ,i2.getPoint() ,measures[i2.getIndexOfLine1()] , i2.getIndexOfLine1() ));
                   // Log.i("alaaaaaaaaa" , "33333333333333333 "  );
                    flag = true ;
                }
                if((i1.getIndexOfLine1() == i2.getIndexOfLine2()) && (!flag)){
                    pathLines.add(new PathLine(i1.getPoint() ,i2.getPoint(),measures[i2.getIndexOfLine2()] , i2.getIndexOfLine2() ));
                   // Log.i("alaaaaaaaaa" , "44444444444 "  );

                }
            }
        }
    }
      public  void checkDiagnolePath() {
          for (int i = 0; i < pathLines.size(); i++) {
              for (int j = i + 1; j < pathLines.size(); j++) {
                  PointF path1_p1 = pathLines.get(i).getPoint1();
                  PointF path1_p2 = pathLines.get(i).getPoint2();
                  PointF path2_p1 = pathLines.get(j).getPoint1();
                  PointF path2_p2 = pathLines.get(j).getPoint2();
                  float sizePath = pathLines.get(j).getSize() + pathLines.get(i).getSize();
                  boolean flag = false;
                  if ((path1_p1.equals(path2_p1)) && (!flag)) {
                      PathLine p = new PathLine(path1_p2, path2_p2, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          flag = true;
                      }
                  }
                  if ((path1_p1.equals(path2_p2)) && (!flag)) {
                      PathLine p = new PathLine(path1_p2, path2_p1, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          flag = true;
                      }
                  }
                  if ((path1_p2.equals(path2_p1)) && (!flag)) {
                      PathLine p = new PathLine(path1_p1, path2_p2, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                          flag = true;
                      }
                  }
                  if ((path1_p2.equals(path2_p2)) && (!flag)) {
                      PathLine p = new PathLine(path1_p1, path2_p1, sizePath, -1);
                      if (!(checkinDiagnole(diagnoleLines, p))) {
                          diagnoleLines.add(p);
                      }
                  }
              }
          }
      }

public boolean checkinDiagnole(ArrayList<PathLine> p , PathLine x){
        for(int i = 0 ; i < p.size() ; i++){
            if(p.get(i).checkexist(x)){
                return true ;
            }
        }
        return false ;
}

    public void setStartPoints(ArrayList<PointF> x){
        startPoints = x ;
    }
    public void setStopPoints(ArrayList<PointF> x){
        stopPoints = x ;
    }
    public void setIntersectPoints(ArrayList<IntersectedPoints> x){
        intersectPoints = x ;
    }
    public void setMeasures(float[] x){
        measures = x ;
    }


}